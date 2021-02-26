package org.happykit.happyboot.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.happykit.happyboot.log.model.Log;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.util.SecurityUtils;
import org.happykit.happyboot.sys.service.SysLogService;
import org.happykit.happyboot.util.DateUtils;
import org.happykit.happyboot.util.InternetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 日志注解切面
 *
 * @author chen.xudong
 * @version 1.0
 * @see org.happykit.happyboot.log.annotation.Log
 * @since 2020/6/4
 */
@Aspect
@Component
//@Profile({"dev", "test"})
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	/**
	 * 换行符
	 */
	private static final String LINE_SEPARATOR = System.lineSeparator();

	@Resource
	private SecurityUtils securityUtils;
	@Resource
	private SysLogService sysLogService;

	/**
	 * 以自定义 @WebLog 注解为切点
	 */
	@Pointcut("@annotation(org.happykit.happyboot.log.annotation.Log)")
	public void webLog() {
	}

	/**
	 * 在切点之前织入
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 开始打印请求日志
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();

		// 获取 @WebLog 注解的描述信息
//		String methodDescription = getAspectLogDescription(joinPoint);

		// 打印请求相关参数
//		logger.info("========================================== Start ==========================================");
//		// 打印请求 url
//		logger.info("URL            : {}", request.getRequestURL().toString());
//		// 打印描述信息
//		logger.info("Description    : {}", methodDescription);
//		// 打印 Http method
//		logger.info("HTTP Method    : {}", request.getMethod());
//		// 打印调用 controller 的全路径以及执行方法
//		logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
//		// 打印请求的 IP
//		logger.info("IP             : {}", request.getRemoteAddr());
//		// 打印请求入参
//		logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
	}

	/**
	 * 在切点之后织入
	 *
	 * @throws Throwable
	 */
	@After("webLog()")
	public void doAfter() throws Throwable {
		// 接口结束后换行，方便分割查看
//		logger.info("=========================================== End ===========================================" + LINE_SEPARATOR);
	}

	/**
	 * 环绕
	 *
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("webLog()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		// 打印出参
//		logger.info("Response Args  : {}", new Gson().toJson(result));
//		// 执行耗时
//		logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

		saveLog(proceedingJoinPoint, result, startTime);
		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, Object result, long startTime) throws JsonProcessingException {
		// 请求耗时
		long costTime = System.currentTimeMillis() - startTime;

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		org.happykit.happyboot.log.annotation.Log log = method.getAnnotation(org.happykit.happyboot.log.annotation.Log.class);
		// 请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 请求方法名
		String methodName = signature.getName();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		// 从获取RequestAttributes中获取HttpServletRequest的信息
		HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);


		// 请求参数
		Object[] args = joinPoint.getArgs();
		// 过滤不能序列化的请求参数
		Stream<Object> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
		List<Object> requestArgs = stream
				.filter(arg -> (!(arg instanceof ServletRequest)
						&& !(arg instanceof ServletResponse)
						&& !(arg instanceof MultipartFile)
						&& !(arg instanceof MultipartFile[])))
				.collect(Collectors.toList());

		// 请求资源路径
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (StringUtils.isNotBlank(contextPath)) {
			requestUri = requestUri.replaceFirst(contextPath, "");
		}

		// 请求用户
		SecurityUserDetails loginUser = securityUtils.getCurrentUserDetails();

		ObjectMapper om = new ObjectMapper();
		Log entity = new Log()
				.setDescription(log.value())
				.setRequestUri(requestUri)
				.setRequestMethod(request.getMethod())
				.setRequestClass(className + "." + methodName)
				.setRequestIp(InternetUtils.getIp(request))
				.setRequestArgs(om.writeValueAsString(requestArgs))
//				.setResponseArgs(om.writeValueAsString(result)) // TODO 响应参数过大，暂不存
				.setRequestUser(loginUser != null ? loginUser.getUsername() : SecurityConstant.ANONYMOUS_USER)
				.setRequestTime(DateUtils.now())
				.setCostTime(costTime);

		sysLogService.saveLog(entity, log.type());
	}

	/**
	 * 获取切面注解的描述
	 *
	 * @param joinPoint 切点
	 * @return 描述信息
	 * @throws Exception
	 */
	public String getAspectLogDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		StringBuilder description = new StringBuilder("");
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description.append(method.getAnnotation(org.happykit.happyboot.log.annotation.Log.class).value());
					break;
				}
			}
		}
		return description.toString();
	}
}
