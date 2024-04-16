package core.di.factory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanScanner {
    private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);
    private Set<Class<?>> preInitiatedBeans;
    private Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
        this.preInitiatedBeans = new HashSet<>();
    }

    public Set<Class<?>> scan() {
        preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(Service.class));
        preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(Repository.class));
        return preInitiatedBeans;
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedBeans) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedBeans) {
                controllers.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }

        return controllers;
    }
}
