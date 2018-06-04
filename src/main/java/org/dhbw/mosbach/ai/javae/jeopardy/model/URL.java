package org.dhbw.mosbach.ai.javae.jeopardy.model;

public class URL {

    private String url;

    public URL(String url) {
        String baseUrl = "http://localhost:8080/jeopardy";
        this.url = baseUrl + "/" + url;
    }

    public String getUrl() {
        return url;
    }
}
