package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecution {
    private final Class<?> declaringClass;
    private final Method method;
    public HandlerExecution(Class<?> declaringClass, Method method) {
        this.declaringClass = declaringClass;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(declaringClass.newInstance(), request, response);
    }
}
