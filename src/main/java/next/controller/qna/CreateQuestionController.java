package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question question = new Question(request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents"));
        log.debug("question : {}", question);

        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
