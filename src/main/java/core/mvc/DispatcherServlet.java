package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    @Override
    public void init() {
        handlerMappings = new ArrayList<>();

        LegacyHandlerMapping lhm = new LegacyHandlerMapping();
        lhm.initMapping();
        handlerMappings.add(lhm);

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();
        handlerMappings.add(ahm);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        Object handler = getHandler(req);

        if (handler == null) {
            throw new RuntimeException("존재하지 않는 컨트롤러");
        }

        try {
            ModelAndView mav = execute(handler, req, resp);
            render(req, resp, mav);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handler instanceof Controller) {
            return ((Controller) handler).execute(req, resp);
        } else if (handler instanceof HandlerExecution) {
            return ((HandlerExecution) handler).handle(req, resp);
        }

        return null;
    }


    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            return handlerMapping.getHandler(req);
        }

        return null;
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
