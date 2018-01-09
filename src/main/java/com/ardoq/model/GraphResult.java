package com.ardoq.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GraphResult {

    private String status;

    @SerializedName("result")
    private List<GraphResultHit> hits;


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GraphResultHit> getHits() {
        return hits;
    }

    public void setHits(List<GraphResultHit> hits) {
        this.hits = hits;
    }

}
