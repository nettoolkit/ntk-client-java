package com.nettoolkit.dashboards;

import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;

public class HttpHeader {
    private String mstrKey;
    private String mstrValue;

    public HttpHeader(String strKey, String strValue) {
        mstrKey = strKey;
        mstrValue = strValue;
    }

    public String getKey() { return mstrKey; }

    public String getValue() { return mstrValue; }

    public JSONObject toJson() throws JSONException {
        return new JSONObject()
            .put("key", getKey())
            .put("value", getValue());
    }
}

