package core.nmvc;

import java.util.Map;

import core.di.factory.BeanFactory;
import core.di.factory.BeanScanner;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);

    private BeanScanner cf;

    @Before
    public void setup() {
        cf = new BeanScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception {
        BeanFactory beanFactory = new BeanFactory(cf.scan());
        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            logger.debug("controller : {}", controller);
        }
    }
}
