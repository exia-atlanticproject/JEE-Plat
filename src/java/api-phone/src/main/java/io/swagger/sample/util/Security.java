package io.swagger.sample.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.lang.annotation.*;

@Inherited
@InterceptorBinding
@Target( {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {

}


@Interceptor
@Security
class SecurityCheckInterceptor {

    @AroundInvoke
    public Object checkSecurity(InvocationContext context) throws Exception {
        /* check the parameters or do a generic security check before invoking the
           original method */
        Object[] params = context.getParameters();


        if (true) {
            return Response.status(401).entity("Bad credentials").build();
        }
        /* if security validation fails, you can throw an exception */

        /* invoke the proceed() method to call the original method */
        Object ret = context.proceed();

        /* perform any post method call work */
        return ret;
    }
}