package com.nettoolkit.internal.http;

public enum HttpContentType {
    JSON("application/json"),
    WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    private String mstrValue;
    private HttpContentType(String strValue) {
        mstrValue = strValue;
    }

    public String getValue() { return mstrValue; }
}

