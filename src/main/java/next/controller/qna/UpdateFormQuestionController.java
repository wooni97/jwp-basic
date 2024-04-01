package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
            throw new IllegalArgumentException("다른 사용자가 쓴 글은 수정할 수 없습니다.");
        }

        return jspView("/qna/updateForm.jsp").addObject("question", question);
    }
}
