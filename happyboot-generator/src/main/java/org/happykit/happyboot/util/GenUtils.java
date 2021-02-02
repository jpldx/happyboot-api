package org.happykit.happyboot.util;

import org.happykit.happyboot.entity.ColumnEntity;
import org.happykit.happyboot.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/24
 */
public class GenUtils {
	static final String ID = "id";
	static final String CREATE_BY = "create_by";
	static final String CREATE_TIME = "create_time";
	static final String UPDATE_BY = "update_by";
	static final String UPDATE_TIME = "update_time";
	static final String VERSION = "version";
	static final String IS_DELETED = "is_deleted";

	public static String checkIsDefaultColumn(String column) {
		if (ID.equals(column) || CREATE_BY.equals(column) || CREATE_TIME.equals(column) || UPDATE_BY.equals(column)
				|| UPDATE_TIME.equals(column) || VERSION.equals(column) || IS_DELETED.equals(column)) {
			return "1";
		}
		return "0";

	}

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<String>();
		templates.add("template/DO.java.vm");
		templates.add("template/Mapper.java.vm");
		templates.add("template/Mapper.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Controller.java.vm");
		templates.add("template/Factory.java.vm");
		templates.add("template/Form.java.vm");
		templates.add("template/PageQueryParam.java.vm");
//		templates.add("template/menu.sql.vm");

//		templates.add("template/index.vue.vm");
//		templates.add("template/add-or-update.vue.vm");

		return templates;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table,
									 List<Map<String, String>> columns, ZipOutputStream zip) {
		//配置信息
		Configuration config = getConfig();
		boolean hasBigDecimal = false;
		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		//列信息
		List<ColumnEntity> columsList = new ArrayList<>();

		for (Map<String, String> column : columns) {
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			if (!hasBigDecimal && attrType.equals("BigDecimal")) {
				hasBigDecimal = true;
			}
			//是否主键
			if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
				tableEntity.setPk(columnEntity);
			}

			columnEntity.setSupperClassColumn(checkIsDefaultColumn(column.get("columnName")));
			columsList.add(columnEntity);


		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if (tableEntity.getPk() == null) {
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}


		//设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		String mainPath = config.getString("mainPath");
		mainPath = StringUtils.isBlank(mainPath) ? "com.ltframework.boot" : mainPath;
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("mainPath", mainPath);
		map.put("package", config.getString("package"));
		map.put("moduleName", config.getString("moduleName"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
		map.put("date", DateUtils.format(new Date()));
		VelocityContext context = new VelocityContext(map);
		//获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
//			//渲染模板
//			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
//			tpl.merge(context, sw);

			String f = getFileName(template, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName"));

			File file = new File(f);
			//如果不存在
			if (!file.exists()) {
				try {

					file.createNewFile();


					FileOutputStream outStream = new FileOutputStream(file);
					OutputStreamWriter writer = new OutputStreamWriter(outStream,
							"UTF-8");
					BufferedWriter sw = new BufferedWriter(writer);
					tpl.merge(context, sw);

					sw.flush();
					sw.close();
					outStream.close();

					writer.close();
				} catch (IOException e) {
					throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
				}
			}

//			try {
//				//添加到zip
//				zip.putNextEntry(new ZipEntry());
//				IOUtils.write(sw.toString(), zip, "UTF-8");
//				IOUtils.closeQuietly(sw);
//				zip.closeEntry();
//			} catch (IOException e) {
//				throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
//			}
		}
	}


	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String[] tablePrefixArray) {
		if (null != tablePrefixArray && tablePrefixArray.length > 0) {
			for (String tablePrefix : tablePrefixArray) {
				tableName = tableName.replace(tablePrefix, "");
			}
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig() {
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String moduleName) {
		String projectPath = System.getProperty("user.dir");

		String srcPath = "src" + File.separator + "main" + File.separator + "java";
		String resourcesPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "mapper";

		String entityPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator + "model" + File.separator
				+ "entity" + File.separator;

		String formPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator + "model" + File.separator
				+ "form" + File.separator;


		String factoryPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator
				+ "factory" + File.separator;

		String mapperPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator
				+ "mapper" + File.separator;

		String mapperXmlPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + resourcesPath
				+ File.separator + moduleName + File.separator;
		String servicePath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator
				+ "service" + File.separator;

		String serviceImplPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator
				+ "service" + File.separator + "impl" + File.separator;

		String controllerPath = projectPath + File.separator + "ltit-cloud-web" + File.separator + "ltit-cloud-web-admin" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator + "controller" + File.separator;


		String pageQueryParamPath = projectPath + File.separator + "ltit-cloud-smd" + File.separator + srcPath
				+ File.separator + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator + "model" + File.separator
				+ "query" + File.separator;


		String packagePath = "main" + File.separator + "java" + File.separator;

//		if (StringUtils.isNotBlank(packageName)) {
//			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
//		}

		if (template.contains("Factory.java.vm")) {
//			return packagePath + "factory" + File.separator + className + "Factory.java";

			return factoryPath + className + "Factory.java";
		}

		if (template.contains("Form.java.vm")) {
//			return packagePath + "form" + File.separator + className + "Form.java";

			return formPath + className + "Form.java";
		}

		if (template.contains("DO.java.vm")) {
//			return packagePath + "entity" + File.separator + className + "DO.java";
			return entityPath + className + "DO.java";
		}


		if (template.contains("Mapper.java.vm")) {
//			return packagePath + "mapper" + File.separator + className + "Mapper.java";

			return mapperPath + className + "Mapper.java";
		}

		if (template.contains("Service.java.vm")) {
//			return packagePath + "service" + File.separator + className + "Service.java";
			return servicePath + className + "Service.java";
		}

		if (template.contains("ServiceImpl.java.vm")) {
//			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";

			return serviceImplPath + className + "ServiceImpl.java";
		}

		if (template.contains("Controller.java.vm")) {
//			return packagePath + "controller" + File.separator + className + "Controller.java";

			return controllerPath + className + "Controller.java";
		}

		if (template.contains("Mapper.xml.vm")) {
//			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
			return mapperXmlPath + className + "Mapper.xml";
		}

		if (template.contains("PageQueryParam.java.vm")) {
//			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
			return pageQueryParamPath + className + "PageQueryParam.java";

		}

//		if (template.contains("menu.sql.vm")) {
//			return className.toLowerCase() + "_menu.sql";
//		}
//
//		if (template.contains("index.vue.vm")) {
//			return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
//					File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
//		}
//
//		if (template.contains("add-or-update.vue.vm")) {
//			return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
//					File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
//		}

		return null;
	}
}
