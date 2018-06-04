package org.dhbw.mosbach.ai.javae.jeopardy.model;

public enum ScoreEnum {
    EINHUNDERT(100),
    DREIHUNDERT(300),
    FUENFHUNDERT(500),
    ACHTHUNDERT(800),
    EINTAUSEND(1000);

    private final int value;

    ScoreEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
