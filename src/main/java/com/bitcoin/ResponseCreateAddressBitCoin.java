package com.bitcoin;

import java.util.ArrayList;

public class ResponseCreateAddressBitCoin {
    private ArrayList<String> result;
    private String error;
    private String id;

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
