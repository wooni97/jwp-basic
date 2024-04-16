package core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstantiateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstantiateBeans) {
            instantiateClass(clazz);
        }
    }

    /*
    * Class에 대한 빈 인스턴스를 생성하는 메소드
    * 재귀함수의 시작은 instantiateClass()에서 시작한다.
    * @Inject 어노테이션이 설정 되어있는 생성자가 존재하면 instantiateConstructor() 메소드를 통해 인스턴스를 생성하고,
    * 존재하지 않을 경우 기본 생성자로 인스턴스를 생성한다.
    * */
    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor
                = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }

        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;
    }

    /*
    * Constuctor에 대한 빈 인스턴스를 생성하는 메소드
    * 생성자의 인자로 전달할 빈이 생성되어 Map<Class<?>, Object>에 이미 존재하면 해당 빈을 활용하고
    * 존재하지 않을 경우 instantiateClass() 메소드를 통해 빈을 생성한다.
    * */
    private Object instantiateConstructor(Constructor<?> constructor) {
        // getParameterTypes() : 리플렉션에서 사용되는 메소드로, 생성자의 매개변수 타입을 가져오는 데 사용한다.
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();

        for (Class<?> clazz : parameterTypes) {
            Class<?> concreteClass =
                    BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
            Object bean = beans.get(concreteClass);
            if (bean == null) {
                bean = instantiateClass(concreteClass);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllerBeans = Maps.newHashMap();

        for (Class<?> clazz : preInstantiateBeans) {
            Annotation annotation = clazz.getAnnotation(Controller.class);

            if (annotation != null) {
                controllerBeans.put(clazz, beans.get(clazz));
            }
        }
        return controllerBeans;
    }
}
