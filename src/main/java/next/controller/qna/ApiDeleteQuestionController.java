package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteQuestionController extends AbstractController {
    private QnaService qnaService = new QnaService();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = UserSessionUtils.getUserFromSession(request.getSession());

        try {
            qnaService.deleteQuestion(questionId, user);
            return jsonView().addObject("reuslt", Result.ok());
        } catch (RuntimeException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }

    }
}
