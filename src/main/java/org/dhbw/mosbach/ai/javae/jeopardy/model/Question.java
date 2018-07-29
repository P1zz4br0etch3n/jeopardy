package org.dhbw.mosbach.ai.javae.jeopardy.model;

import org.dhbw.mosbach.ai.javae.jeopardy.exception.ScoreNotValidException;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String answer;
    private ScoreEnum points;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points.getValue();
    }

    public void setPoints(int score) throws ScoreNotValidException {
        if (score == 100) {
            this.points = ScoreEnum.EINHUNDERT;
        } else if (score == 300) {
            this.points = ScoreEnum.DREIHUNDERT;
        } else if (score == 500) {
            this.points = ScoreEnum.FUENFHUNDERT;
        } else if (score == 800) {
            this.points = ScoreEnum.ACHTHUNDERT;
        } else if (score== 1000) {
            this.points = ScoreEnum.EINTAUSEND;
        } else {
            throw new ScoreNotValidException();
        }
    }
}
