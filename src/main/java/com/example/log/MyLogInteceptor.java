package com.example.log;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MyLogInteceptor extends HandlerInterceptorAdapter {
    private static  final ThreadLocal<Long> THREAD_LOCAL=new ThreadLocal<Long>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handler1 = (HandlerMethod) handler;
        Method method = handler1.getMethod();
        MyLog annotation = method.getAnnotation(MyLog.class);
        if(annotation!=null){
            THREAD_LOCAL.set(System.currentTimeMillis());

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handler1 = (HandlerMethod) handler;
        Method method = handler1.getMethod();
        MyLog annotation = method.getAnnotation(MyLog.class);
        if(annotation!=null){
            long l = System.currentTimeMillis() - THREAD_LOCAL.get();
            String desc = annotation.desc();
            String s = method.getDeclaringClass().getName() + "  " + method.getName();
            System.out.println("执行耗费时间："+l );
            System.out.println("执行描述："+desc);
            System.out.println("执行的类全限定名以及执行的方法： "+s);
        }

    }
}
