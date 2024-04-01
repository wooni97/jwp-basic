package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question question = new Question(Long.parseLong(request.getParameter("questionId")),
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents"));

        questionDao.update(question);
        return jspView("redirect:/");
    }
}
