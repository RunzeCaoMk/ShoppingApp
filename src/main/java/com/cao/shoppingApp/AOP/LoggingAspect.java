package com.cao.shoppingApp.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("com.cao.shoppingApp.AOP.PointCuts.inPlaceOrder()")
    public void logPlaceOrderTime(JoinPoint joinPoint){
        logger.info("From LoggingAspect.logPlaceTime in Place Order: {}: {}", System.currentTimeMillis(), joinPoint.getSignature());
    }

    @After("com.cao.shoppingApp.AOP.PointCuts.inCancelOrder()")
    public void logCancelOrderTime(JoinPoint joinPoint){
        logger.info("From LoggingAspect.logCancelOrderTime in Cancel Order: {}: {}", System.currentTimeMillis(), joinPoint.getSignature());
    }

    @After("com.cao.shoppingApp.AOP.PointCuts.inCompleteOrder()")
    public void logCompleteOrderTime(JoinPoint joinPoint){
        logger.info("From LoggingAspect.logCompleteOrderTime in Complete Order: " + System.currentTimeMillis()  + ": " + joinPoint.getSignature());
    }

}
