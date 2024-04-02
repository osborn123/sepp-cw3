package model;


public class FAQItem {
    // The question part of the FAQ item
    private String question;
    // The answer part of the FAQ item
    private String answer;

    /**
     * Constructs a new FAQItem with a specified question and answer.
     *
     * @param question The question of the FAQ item.
     * @param answer The answer to the question.
     */
    public FAQItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Retrieves the question of this FAQ item.
     *
     * @return The question of this FAQ item.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Retrieves the answer to the question of this FAQ item.
     *
     * @return The answer to the question.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Updates the question of this FAQ item.
     *
     * @param question The new question for the FAQ item.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Updates the answer of this FAQ item.
     *
     * @param answer The new answer for the question.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Returns a string representation of the FAQ item,
     * formatting it as "question: answer".
     *
     * @return A string representation of the FAQ item.
     */
    @Override
    public String toString() {
        return question + ": " + answer;
    }
}