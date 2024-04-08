package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.User;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private QnaService qnaService = new QnaService();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = UserSessionUtils.getUserFromSession(request.getSession());

        try {
            qnaService.deleteQuestion(questionId, user);
            return jspView("redirect:/");
        } catch (RuntimeException e) {
            return jspView("show.jsp")
                    .addObject("question", questionDao.findById(questionId))
                    .addObject("answers", answerDao.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }

    }
}
