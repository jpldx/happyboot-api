package org.happykit.happyboot.log.aspect;

import com.google.gson.Gson;
import org.happykit.happyboot.log.annotation.WebLog;
import org.happykit.happyboot.log.model.WebLogDTO;
import org.happykit.happyboot.log.service.WebLogService;
import org.happykit.happyboot.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * web日志，切面处理类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/4
 */
@Aspect
@Component
@Profile({"dev", "test"})
public class WebLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	/**
	 * 换行符
	 */
	private static final String LINE_SEPARATOR = System.lineSeparator();

	@Resource
	private WebLogService webLogService;

	/**
	 * 以自定义 @WebLog 注解为切点
	 */
	@Pointcut("@annotation(org.happykit.happyboot.log.annotation.WebLog)")
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
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 获取 @WebLog 注解的描述信息
		String methodDescription = getAspectLogDescription(joinPoint);

		// 打印请求相关参数
		logger.info("========================================== Start ==========================================");
		// 打印请求 url
		logger.info("URL            : {}", request.getRequestURL().toString());
		// 打印描述信息
		logger.info("Description    : {}", methodDescription);
		// 打印 Http method
		logger.info("HTTP Method    : {}", request.getMethod());
		// 打印调用 controller 的全路径以及执行方法
		logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
		// 打印请求的 IP
		logger.info("IP             : {}", request.getRemoteAddr());
		// 打印请求入参
		logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
	}

	/**
	 * 在切点之后织入
	 *
	 * @throws Throwable
	 */
	@After("webLog()")
	public void doAfter() throws Throwable {
		// 接口结束后换行，方便分割查看
		logger.info("=========================================== End ===========================================" + LINE_SEPARATOR);
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
		logger.info("Response Args  : {}", new Gson().toJson(result));
		// 执行耗时
		logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

		saveLog(proceedingJoinPoint, result, startTime);
		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, Object result, long startTime) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		WebLog webLog = method.getAnnotation(WebLog.class);
		// 获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 获取请求的方法名
		String methodName = signature.getName();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		// 从获取RequestAttributes中获取HttpServletRequest的信息
		HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

		// 设置IP地址
		String ip = IpUtils.getIpAddress(request);
		long costTime = System.currentTimeMillis() - startTime;

		WebLogDTO dto = new WebLogDTO()
				.url(request.getRequestURL().toString())
				.description(webLog.description())
				.httpMethod(request.getMethod())
				.classMethod(className + "." + methodName)
				.ip(ip)
				.requestArgs(new Gson().toJson(joinPoint.getArgs()))
				.responseArgs(new Gson().toJson(result))
				.costTime(costTime);

		webLogService.saveWebLog(dto);
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
					description.append(method.getAnnotation(WebLog.class).description());
					break;
				}
			}
		}
		return description.toString();
	}

}
