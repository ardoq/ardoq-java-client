package com.ardoq.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GraphPathResult {

    private String status;

    @SerializedName("result")
    private List<GraphPathResultHit> hits;


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<GraphPathResultHit> getHits() {
        return hits;
    }

    public void setHits(List<GraphPathResultHit> hits) {
        this.hits = hits;
    }


    public static class GraphPathResultHit {
        private List<GraphResultHit> objects;

        public List<GraphResultHit> getObjects() {
            return objects;
        }

        public void setObjects(List<GraphResultHit> objects) {
            this.objects = objects;
        }
    }

}
