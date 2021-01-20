package com.miaoshaproject.aspect;

import com.alibaba.fastjson.JSON;
import com.miaoshaproject.service.ExcService;
import com.miaoshaproject.service.model.ExcModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kk on 2021/1/19
 */
@Aspect
@Component
public class ExcAspect {
    @Autowired
    ExcService excService;

    /**
     * 设置操作异常切入点记录异常日志，扫描所有controller包下的操作
     */
    @Pointcut("execution(* com.miaoshaproject.controller..*.*(..))")
    public void operExceptionLogPointCut() {}

    @AfterThrowing(pointcut = "operExceptionLogPointCut()", throwing = "e")
    public void saveExc(JoinPoint joinPoint, Throwable e) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取的RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExcModel excModel = new ExcModel();
        try {
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().getName();//获取请求的类名
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            //请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            //将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            excModel.setExcRequParam(params);//请求参数
            excModel.setOperMethod(methodName);//请求方法名
            excModel.setExcName(e.getClass().getName());//异常名称
            excModel.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));//异常信息
            excModel.setOperUri(request.getRequestURI());
            excModel.setOperIp(request.getRemoteAddr());
            excModel.setOperCreateTime(DateTime.now());

            excService.saveExc(excModel);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     * 转换request请求参数
     * @param paramMap request获取的参数数组
     * @return
     */
    private Map<String, String>  converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key: paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     * @param exceptionName     异常名称
     * @param exceptionMessage  异常信息
     * @param elements          堆栈信息
     * @return
     */
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
