package com.cao.shoppingApp.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    // TODO: point cut
    @Pointcut("execution(com.cao.shoppingApp.DAO.)")
    public void inPlaceOrder(){TODO: point cut}

    // TODO: point cut
    @Pointcut("execution(com.cao.shoppingApp.DAO.)")
    public void inUpdateOrder(){}

}
