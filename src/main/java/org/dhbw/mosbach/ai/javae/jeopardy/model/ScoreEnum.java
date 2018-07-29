package org.dhbw.mosbach.ai.javae.jeopardy.model;

public enum ScoreEnum {
    EINHUNDERT(100),
    ZWEIHUNDERT(200),
    DREIHUNDERT(300),
    VIERHUNDERT(400),
    FUENFHUNDERT(500);

    private final int value;

    ScoreEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
