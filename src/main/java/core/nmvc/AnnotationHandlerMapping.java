package core.nmvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

public class AnnotationHandlerMapping implements HandlerMapping{
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(this.basePackage);

        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        Set<Method> methods = getRequestMappingMethods(controllers);

        for(Method method : methods) {
           RequestMapping rm = method.getAnnotation(RequestMapping.class);
           handlerExecutions.put(createHandlerKey(rm),
                   new HandlerExecution(method.getDeclaringClass(), method));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    @SuppressWarnings("unchecked")
    public Set<Method> getRequestMappingMethods(Map<Class<?>, Object> controllers) {
        Set<Method> methods = new HashSet<>();
        controllers.forEach((clazz, o) -> methods.addAll(ReflectionUtils.getAllMethods(
                clazz, ReflectionUtils.withAnnotation(RequestMapping.class))));

        return methods;
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }
}
