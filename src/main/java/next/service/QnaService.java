package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QnaService {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    public void deleteQuestion(long questionId, User user) {
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if (!question.isSameUser(user)) {
            throw new RuntimeException("다른 사용자의 글은 삭제가 불가능합니다");
        }

        if (question.getCountOfComment() == 0) {
            questionDao.delete(questionId);
            return;
        }

        Set<String> uniqueWriters = answers.stream()
                .map(Answer::getWriter)
                .collect(Collectors.toSet());

        if (uniqueWriters.size() == 1 && uniqueWriters.contains(question.getWriter())) {
            questionDao.delete(questionId);
            return;
        }

        throw new RuntimeException("다른 유저의 댓글이 존재하여 삭제할 수 없습니다");
    }
}
