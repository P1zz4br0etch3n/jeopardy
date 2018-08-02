package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Bean for keeping state about DummyData being created or not
 */
@Named
@ApplicationScoped
public class DummyBean {

    private boolean dummyDataCreated = false;

    /**
     * Check if DummyData is already created for this runtime
     * @return true or false
     */
    public boolean isDummyDataCreated() {
        return dummyDataCreated;
    }

    /**
     * Set DummyDataCreated
     * @param dummyDataCreated true or false
     */
    public void setDummyDataCreated(boolean dummyDataCreated) {
        this.dummyDataCreated = dummyDataCreated;
    }
}
