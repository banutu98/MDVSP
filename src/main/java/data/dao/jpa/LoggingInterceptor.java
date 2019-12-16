package data.dao.jpa;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

    @AroundInvoke
    public Object measureTime(InvocationContext ctx) throws Exception{
        long startTime = System.nanoTime();
        try {
            return ctx.proceed();
        }finally {
            long time = System.nanoTime() - startTime;
            String clazz = ctx.getMethod().getDeclaringClass().getName();
            String method = ctx.getMethod().getName();
            System.out.println("Invocation of method " + method + " from class " + clazz + " took " + time + " nanoseconds");
        }
    }
}
