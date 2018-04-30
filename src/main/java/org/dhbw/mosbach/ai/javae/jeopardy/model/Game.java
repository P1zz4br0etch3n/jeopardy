package org.dhbw.mosbach.ai.javae.jeopardy.model;

import javax.persistence.*;

@Entity
public class Game {
    private String id;

    private String name;

    @OneToOne
    private User creator;

    @Id
    @GeneratedValue
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
