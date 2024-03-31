package core.mvc;

import next.controller.UserSessionUtils;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController extends AbstractController {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
        this.forwardUrl = forwardUrl;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        if (user != null) {
            return jspView(forwardUrl).addObject("name", user.getName());
        }
        return jspView(forwardUrl);
    }

}
