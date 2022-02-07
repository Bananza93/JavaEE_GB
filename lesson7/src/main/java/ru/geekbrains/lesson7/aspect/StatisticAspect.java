package ru.geekbrains.lesson7.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StatisticAspect {

    private static Map<String, Map<String, Long>> statMap;

    @PostConstruct
    public void init() {
        statMap = new HashMap<>();
    }

    @Pointcut(value = "@annotation(TrackExecutionTime)")
    private void getExecTime() {
    }

    @Around("getExecTime()")
    public Object logExecTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String clName = joinPoint.getTarget().getClass().getSimpleName();
        String sign = joinPoint.getSignature().toString();
        var valueMap = statMap.getOrDefault(clName, new HashMap<>());

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        valueMap.put(sign, System.currentTimeMillis() - start);
        statMap.put(clName, valueMap);
        return result;
    }

    public static Map<String, Map<String, Long>> getStatMap() {
        return statMap;
    }
}
