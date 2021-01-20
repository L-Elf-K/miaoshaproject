package com.miaoshaproject.aspect;

import com.alibaba.fastjson.JSON;
import com.miaoshaproject.annotation.LogAnnotation;

import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.LogService;
import com.miaoshaproject.service.model.LogModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by kk on 2021/1/3
 */
@Aspect //切面类注释
@Component //纳入Spring容器
public class LogAspect {
    @Autowired
    private LogService logService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 设置日志切入点 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.miaoshaproject.annotation.LogAnnotation)")
    public void operlogPointcut() {
    }
    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行，如果连接点抛出异常，则不会执行
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result 方法返回值
     * @throws Throwable
     */
    @AfterReturning(value = "operlogPointcut()", returning = "result")
    public void saveLog(JoinPoint joinPoint, Object result) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequdetAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        LogModel logModel = new LogModel();
        try{
            //从切面织入点处通过反射机制获取织入点出的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
            if (annotation != null) {
                logModel.setOperModel(annotation.operModel());
                logModel.setOperType(annotation.operType());
                logModel.setOperDesc(annotation.operDesc());
            }
            String classname = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = classname + "." + methodName;
            //请求方法
            logModel.setOperMethod(methodName);
            //请求参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            //将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            logModel.setOperRequParam(params);//请求参数
            logModel.setOperRespParam(JSON.toJSONString(result));//返回结果
            logModel.setOperIp(request.getRemoteAddr());//请求IP
            logModel.setOperUri(request.getRequestURI());//请求URL
            logModel.setOperCreateTime(DateTime.now());//请求创建时间
            logService.saveLog(logModel);
        }catch(Exception e) {
            e.printStackTrace();
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
}
