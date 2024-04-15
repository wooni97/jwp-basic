package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private final Reflections reflections;

    public ControllerScanner(Object ... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);

        return instantiateControllers(annotated);
    }

    public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) {
        Map<Class<?>, Object> controllers = new HashMap<>();

        annotated.forEach(aClass -> {
            try {
                controllers.put(aClass, aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return controllers;
    }
}
