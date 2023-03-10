package com.cao.shoppingApp.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("execution(* com.cao.shoppingApp.DAO.OrderDAO.createNewOrder())")
    public void inPlaceOrder(){}

    @Pointcut("execution(* com.cao.shoppingApp.DAO.OrderDAO.cancelOrder(Integer))")
    public void inCancelOrder(){}

    @Pointcut("execution(* com.cao.shoppingApp.DAO.OrderDAO.completeOrder(Integer))")
    public void inCompleteOrder(){}

}
