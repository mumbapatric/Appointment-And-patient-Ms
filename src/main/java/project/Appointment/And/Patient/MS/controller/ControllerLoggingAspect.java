package project.Appointment.And.Patient.MS.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import project.Appointment.And.Patient.MS.service.ActivityLogService;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLoggingAspect {

    private final ActivityLogService activityLogService;

    public ControllerLoggingAspect(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        String description = "Accessed method: " + className + "." + method + " with args: " + Arrays.toString(joinPoint.getArgs());
        activityLogService.log(user, description, request);
    }
}
