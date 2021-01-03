package com.miaoshaproject.aspect;

import com.miaoshaproject.annotation.LogAnnotation;
import com.miaoshaproject.dao.LogDOMapper;
import com.miaoshaproject.dataobject.LogDO;
import com.miaoshaproject.response.CommonReturnType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
import java.util.Map;

/**
 * Created by kk on 2021/1/3
 */
@Aspect
@Component
public class LogAspect {
    @Resource
    LogDOMapper logDOMapper;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 设置日志切入点 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.miaoshaproject.annotation.LogAnnotation)")
    public void logPointcut() {

    }
    /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result 方法返回值
     * @throws Throwable
     */
    @AfterReturning(returning = "result", value = "logPointcut()")
    public void saveLog(JoinPoint joinPoint, Object result) throws Throwable {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequdetAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try{
            //将返回值转换成map集合
            CommonReturnType map = (CommonReturnType) result;
            LogDO logDO = new LogDO();
            //从切面织入点处通过反射机制获取织入点出的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
            if (annotation != null) {
                logDO.setModelName(annotation.modelName());
                logDO.setModelFunc(annotation.modelFunc());
                logDO.setLevel(annotation.modelLevel());
            }
            //操作时间
            logDO.setLogTime(Timestamp.valueOf(sdf.format(new Date())));
            logDO.setResult(map.getStatus());
            logDOMapper.insertSelective(logDO);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

















}
