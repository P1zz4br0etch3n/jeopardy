package org.dhbw.mosbach.ai.javae.jeopardy.model;

import org.dhbw.mosbach.ai.javae.jeopardy.exception.ScoreNotValidException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private long id;

    private String text;
    private String answer;
    private ScoreEnum score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score.getValue();
    }

    public void setScore(int score) throws ScoreNotValidException {
        if (score == 100) {
            this.score = ScoreEnum.EINHUNDERT;
        } else if (score == 300) {
            this.score = ScoreEnum.DREIHUNDERT;
        } else if (score == 500) {
            this.score = ScoreEnum.FUENFHUNDERT;
        } else if (score == 800) {
            this.score = ScoreEnum.ACHTHUNDERT;
        } else if (score== 1000) {
            this.score = ScoreEnum.EINTAUSEND;
        } else {
            throw new ScoreNotValidException();
        }
    }
}
