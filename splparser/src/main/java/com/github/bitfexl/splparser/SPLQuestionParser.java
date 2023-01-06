package com.github.bitfexl.splparser;

import java.util.ArrayList;
import java.util.List;

public class SPLQuestionParser {
    private static final String START_ANSWER_CORRECT = "þ";
    private static final String START_ANSWER_WRONG = "¨";

    private int questionIndex;

    private StringBuilder currentQuestion;

    private boolean currentAnswerCorrect;

    private StringBuilder currentAnswer;


    @SuppressWarnings("StatementWithEmptyBody")
    public List<Question> parse(String text) {
        String[] lines = text.split(System.lineSeparator());
        List<Question> parsedQuestions = new ArrayList<>();

        for (int i = 0; i< lines.length; i++) {
            // 1/3: read next/first question
            if (!checkQuestion(lines[i])) {
                continue;
            }

            while (appendQuestion(lines[++i]));

            Question parsingQuestion = new Question(questionIndex, fixFormatting(currentQuestion.toString()));

            // 2/3: read answers
            for (int a=0; a<4; a++) {
                if (!checkAnswer(lines[i])) {
                    if (lines[i].isBlank()) {
                        i++;
                        a--;
                        continue;
                    }
                    throw new IllegalStateException("Expected answer but got: '" + lines[i] + "' at line " + i + ", question " + questionIndex + ".");
                }

                while (appendAnswer(lines[++i]));

                parsingQuestion.getAnswers().add(
                        new Question.Answer(a + 1, fixFormatting(currentAnswer.toString()), currentAnswerCorrect)
                );
            }

            if (parsingQuestion.getAnswers().size() != 4) {
                throw new IllegalStateException("Expected 4 answers, question: " + questionIndex + ".");
            }

            // 3/3: add to question list
            parsedQuestions.add(parsingQuestion);
        }

        return parsedQuestions;
    }

    private boolean checkQuestion(String line) {
        String[] parts = line.split(" ", 2);
        try {
            questionIndex = Integer.parseInt(parts[0]);
            currentQuestion = new StringBuilder(parts[1].trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean appendQuestion(String line) {
        if (line.startsWith(START_ANSWER_CORRECT) || line.startsWith(START_ANSWER_WRONG)) {
            return false;
        }

        boolean endsWithSentence = currentQuestion.charAt(currentQuestion.length() - 1) == '.' || line.startsWith(" ");
        currentQuestion.append(endsWithSentence ? "\n" : " ").append(line.trim());

        return true;
    }

    private boolean checkAnswer(String line) {
        if (line.startsWith(START_ANSWER_CORRECT) || line.startsWith(START_ANSWER_WRONG)) {
            currentAnswerCorrect = line.startsWith(START_ANSWER_CORRECT);
            String[] parts = line.split(" ", 2);
            currentAnswer = new StringBuilder(parts[1].trim());
            return true;
        }

        return false;
    }

    private boolean appendAnswer(String line) {
        if (line.startsWith(START_ANSWER_CORRECT) || line.startsWith(START_ANSWER_WRONG) || line.isBlank()) {
            return false;
        }

        boolean endsWithSentence = currentAnswer.charAt(currentAnswer.length() - 1) == '.';
        currentAnswer.append(endsWithSentence ? "\n" : " ").append(line.trim());

        return true;
    }

    private String fixFormatting(String s) {
        s = s.replace("  ", " ");
        return s.replace("\n ", "\n");
    }
}
