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

//    @After("com.cao.shoppingApp.AOP.PointCuts.inPlaceOrder()")
//    public void logPlaceOrderTime(JoinPoint joinPoint){
//        logger.info("From LoggingAspect.logPlaceTime in Place Order: " + System.currentTimeMillis()  + ": " + joinPoint.getSignature());
//    }
//
//    @After("com.cao.shoppingApp.AOP.PointCuts.inUpdateOrder()")
//    public void logUpdateOrderTime(JoinPoint joinPoint){
//        logger.info("From LoggingAspect.logUpdateOrderTime in Update Order: " + System.currentTimeMillis()  + ": " + joinPoint.getSignature());
//    }

}
