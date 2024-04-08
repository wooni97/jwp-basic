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
    private Reflections reflections = new Reflections("jwp-basic");
    private Map<Class<?>, Object> controllers;

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);

        return instantiateControllers(annotated);
    }

    public Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) {
        controllers = new HashMap<>();

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
