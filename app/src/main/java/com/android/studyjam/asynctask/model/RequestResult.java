package com.android.studyjam.asynctask.model;

import java.util.List;

public class RequestResult {

    public List<CartoonCharacter> results;

    public RequestResult() {
    }

    public List<CartoonCharacter> getResults() {
        return results;
    }

    public void setResults(List<CartoonCharacter> results) {
        this.results = results;
    }
}
