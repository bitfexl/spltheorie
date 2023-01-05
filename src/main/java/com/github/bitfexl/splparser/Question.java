package com.github.bitfexl.splparser;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public static class Answer {
        private int answerIndex;

        private String answer;

        private boolean correct;

        public Answer(int answerIndex, String answer, boolean correct) {
            this.answerIndex = answerIndex;
            this.answer = answer;
            this.correct = correct;
        }

        public int getAnswerIndex() {
            return answerIndex;
        }

        public void setAnswerIndex(int answerIndex) {
            this.answerIndex = answerIndex;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }

    private int questionIndex;

    private String question;

    private List<Answer> answers;

    /**
     * The image (annex) as base64 png, if any.
     */
    private String image;

    public Question(int questionIndex, String question) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.answers = new ArrayList<>();
    }

    public Question(int questionIndex, String question, List<Answer> answers) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.answers = answers;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
