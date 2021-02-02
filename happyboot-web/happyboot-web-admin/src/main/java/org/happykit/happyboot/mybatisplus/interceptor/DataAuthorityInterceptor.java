package org.happykit.happyboot.mybatisplus.interceptor;

/**
 * @author shaoqiang
 * @date 2020/6/24
 */

import org.happykit.happyboot.mybatisplus.annotation.DataAuth;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.util.SysSecurityUtils;
import org.happykit.happyboot.util.SpringContextHolder;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 数据权限拦截器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/24
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DataAuthorityInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(DataAuthorityInterceptor.class);

    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    static String REGION_ID = ".region_id";
    static String DEPT_CODE = ".dept_code";
    static String DEPT_ID = ".dept_id";
    static String OBJ_ID = ".obj_id";

    private SysSecurityUtils sysSecurityUtils;

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final MappedStatement statement = (MappedStatement) invocation.getArgs()[MAPPED_STATEMENT_INDEX];
        final Object parameter = invocation.getArgs()[PARAMETER_INDEX];
        // 进行拦截放行判断
        // 1.只对注解拦截
        DataAuth dataAuth = getAnnotationByDelegate(statement);
        if (dataAuth == null) {
            return invocation.proceed();
        }
        // 2.只对查询sql拦截
        SqlCommandType commandType = statement.getSqlCommandType();
        if (!SqlCommandType.SELECT.equals(commandType)) {
            return invocation.proceed();
        }
        loadService();
        // 3.获取注解中的查询键值，结合登录用户获取拼接的查询条件
        if (!sysSecurityUtils.getCurrentUserAuthentication().isAuthenticated()) {
            return invocation.proceed();
        }

        SecurityUserDetails userDetails = sysSecurityUtils.getCurrentUserDetails();
        if (userDetails == null) {
            throw new Exception("获取用户登录信息失败");
        }
        long beginTime = System.currentTimeMillis();

        String originalSql = statement.getBoundSql(invocation.getArgs()[PARAMETER_INDEX]).getSql();

        // CCJSqlParserManager parserManager = new CCJSqlParserManager();
        // Select select = (Select) parserManager.parse(new StringReader(originalSql));

        Select select = (Select) CCJSqlParserUtil.parse(originalSql);
        PlainSelect plain = (PlainSelect) select.getSelectBody();

        if (dataAuth.enableDept()) {
            String authSql = dealDeptAuthSql(plain);
            if (StringUtils.isNotBlank(authSql)) {
                if (plain.getWhere() == null) {
                    plain.setWhere(CCJSqlParserUtil.parseCondExpression(authSql));
                } else {
                    plain.setWhere(new AndExpression(plain.getWhere(), CCJSqlParserUtil.parseCondExpression(authSql)));
                }
                originalSql = select.toString();
            }

        }
        if (dataAuth.enableObj()) {
            originalSql = dealObjAuthSql(originalSql);
        }
        // String authSql2 = this.dealAuthSql2(dataAuth, plain, originalSql2);

        // String authSql = this.dealAuthSql(dataAuth, plain, userDetails.getId());
        //
        // if (StringUtils.isBlank(authSql)) {
        // return invocation.proceed();
        // }
        //
        // if (plain.getWhere() == null) {
        // plain.setWhere(CCJSqlParserUtil.parseCondExpression(authSql));
        // } else {
        // plain.setWhere(new AndExpression(plain.getWhere(), CCJSqlParserUtil.parseCondExpression(authSql)));
        // }
        //
        // resetSql2Invocation(invocation, select.toString());

        resetSql2Invocation(invocation, originalSql);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        // 打印耗时的信息
        logger.info("DataAuthorityInterceptor:" + time + " :ms");
        return invocation.proceed();
    }

    /**
     * 获取数据权限注解信息
     *
     * @param mappedStatement
     * @return
     */
    private DataAuth getAnnotationByDelegate(MappedStatement mappedStatement) {
        DataAuth dataAuth = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
            final Class<?> cls = Class.forName(className);
            final Method[] methods = MethodUtils.getMethodsWithAnnotation(cls, DataAuth.class);
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.isAnnotationPresent(DataAuth.class)) {
                    dataAuth = method.getAnnotation(DataAuth.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataAuth;
    }

    /**
     * 处理组织权限数据，并返回组织权限sql
     *
     * @param dataAuth
     * @param plain
     * @param adminId
     * @return
     */
    private String dealAuthSql(DataAuth dataAuth, PlainSelect plain, Long adminId) {

        Table fromItem = (Table) plain.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        String mainTableAlias = fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();

        List<String> authSqls = new ArrayList<>();
        // 部门权限,根据部门编码右模糊查询
        // if (dataAuth.enableDept()) {
        // Set<String> objDeptCodeList = sysSecurityUtils.getCurrentObjDeptCodeList();
        // if (CollectionUtils.isNotEmpty(objDeptCodeList)) {
        // List<String> objDeptCodeSqlList =
        // objDeptCodeList.stream().map(r -> (mainTableAlias + DEPT_CODE + " like concat(" + r + ",'%')"))
        // .collect(Collectors.toList());
        // authSqls.add("(" + org.apache.commons.lang3.StringUtils.join(objDeptCodeSqlList, " or ") + ")");
        // }
        // }

        // 用户所在的单位数据权限
        if (dataAuth.enableObj()) {
            SysDeptObjDO sysDeptObj = sysSecurityUtils.getCurrentUserDept();
            if (sysDeptObj != null) {
                // authSqls.add(mainTableAlias + OBJ_ID + " in ("
                // + org.apache.commons.lang3.StringUtils.join(objIdList, ",") + ")");
            }
        }
        // 用户关联区域所看的单位数据的数据权限
        if (dataAuth.enableRegion()) {
            Set<String> deptRegionIdList = sysSecurityUtils.getCurrentDeptRegionIdList();
            if (CollectionUtils.isNotEmpty(deptRegionIdList)) {
                authSqls.add(mainTableAlias + OBJ_ID + " in ("
                        + org.apache.commons.lang3.StringUtils.join(deptRegionIdList, ",") + ")");
            }
        }

        if (CollectionUtils.isNotEmpty(authSqls)) {
            return org.apache.commons.lang3.StringUtils.join(authSqls, " AND ");
        }
        return "";
    }

    private String dealDeptAuthSql(PlainSelect plain) {
        Table fromItem = (Table) plain.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        String mainTableAlias = fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();

        // 部门权限,根据部门编码右模糊查询
        SysDeptObjDO currentDept = sysSecurityUtils.getCurrentUserDept();
        if (currentDept != null) {
            String appendSql = mainTableAlias + DEPT_ID + " = '" + currentDept.getId() + "'";
            return appendSql;
        }
        Set<String> deptIds = sysSecurityUtils.getCurrentObjDeptIdList();
        if (deptIds.size() > 0) {
            List<String> deptIdStrs = new ArrayList<>();
            for (String deptId : deptIds) {
                deptIdStrs.add("'" + deptId + "'");
            }
            String appendSql = mainTableAlias + DEPT_ID + " IN(" + StringUtils.join(deptIdStrs, ",") + ")";
            return appendSql;
        }
        return "";
    }

    private String dealObjAuthSql(String originalSql) {

        String mainTableAlias = "temp";
        List<String> authSqls = new ArrayList<>();
        Set<String> objIdList = sysSecurityUtils.getCurrentObjIdList();
        if (CollectionUtils.isNotEmpty(objIdList)) {
            authSqls.add(
                    mainTableAlias + OBJ_ID + " in (" + org.apache.commons.lang3.StringUtils.join(objIdList, ",") + ")");
        }

        if (CollectionUtils.isNotEmpty(authSqls)) {
            String ss = "SELECT * FROM (" + originalSql + ") as temp where "
                    + org.apache.commons.lang3.StringUtils.join(authSqls, " AND ");
            return ss;
        }
        return originalSql;
    }

    private String dealAuthSql2(DataAuth dataAuth, PlainSelect plain, String originalSql) {

        Table fromItem = (Table) plain.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        String mainTableAlias = fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();
        mainTableAlias = "temp";
        List<String> authSqls = new ArrayList<>();

        // 用户所在的单位数据权限
        if (dataAuth.enableObj()) {

            Set<String> objIdList = sysSecurityUtils.getCurrentObjIdList();
            if (CollectionUtils.isNotEmpty(objIdList)) {
                authSqls.add(mainTableAlias + OBJ_ID + " in ("
                        + org.apache.commons.lang3.StringUtils.join(objIdList, ",") + ")");
            }
        }
        // 用户关联区域所看的单位数据的数据权限
        // if (dataAuth.enableRegion()) {
        // Set<Long> deptRegionIdList = sysSecurityUtils.getCurrentDeptRegionIdList();
        // if (CollectionUtils.isNotEmpty(deptRegionIdList)) {
        // authSqls.add(mainTableAlias + OBJ_ID + " in ("
        // + org.apache.commons.lang3.StringUtils.join(deptRegionIdList, ",") + ")");
        // }
        // }

        if (CollectionUtils.isNotEmpty(authSqls)) {

            String ss = "SELECT * FROM (" + originalSql + ") as temp where "
                    + org.apache.commons.lang3.StringUtils.join(authSqls, " AND ");
            return ss;
        }
        return originalSql;
    }

    /**
     * 包装sql后，重置到invocation中
     *
     * @param invocation
     * @param sql
     */
    private void resetSql2Invocation(Invocation invocation, String sql) {
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        Object parameterObject = args[PARAMETER_INDEX];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        // sql替换
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
    }

    /**
     * 内部类:重置Statement 由于MappedStatement是一个全局共享的对象，因而需要复制一个对象来进行操作，防止并发访问导致错误
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 加载bean
     */
    private void loadService() {
        if (sysSecurityUtils == null) {
            sysSecurityUtils = SpringContextHolder.getBean(SysSecurityUtils.class);
        }
    }

    /**
     * 内部类：重置boundSql
     */
    public class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
