package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Object object = new Junit3Test();
        List<Method> methods = Arrays.asList(clazz.getMethods());
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.invoke(object);
            }
        }


    }
}
