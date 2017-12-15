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

    public static class GraphResultHit {
        private String id;
        private String label;
        private String type;
        private Object properties;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public Object getProperties() {
            return properties;
        }
        public void setProperties(Object properties) {
            this.properties = properties;
        }
    }

}
