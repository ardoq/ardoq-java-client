package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

public enum FieldType {
    @SerializedName("Text")
    TEXT,
    @SerializedName("Textarea")
    TEXTAREA,
    @SerializedName("DateTime")
    DATETIME,
    @SerializedName("Checkbox")
    CHECKBOX,
    @SerializedName("Number")
    NUMBER,
    @SerializedName("List")
    LIST,
    @SerializedName("url")
    URL,
    @SerializedName("email")
    EMAIL
}
