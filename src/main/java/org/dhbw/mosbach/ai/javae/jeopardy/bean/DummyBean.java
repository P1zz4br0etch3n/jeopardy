package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class DummyBean {

    private boolean dummyDataCreated = false;

    public boolean isDummyDataCreated() {
        return dummyDataCreated;
    }

    public void setDummyDataCreated(boolean dummyDataCreated) {
        this.dummyDataCreated = dummyDataCreated;
    }
}
