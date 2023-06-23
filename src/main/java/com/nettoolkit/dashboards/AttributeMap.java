package com.nettoolkit.dashboards;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.nettoolkit.json.JSONObject;

public class AttributeMap {
    private Map<String, Object> mmapKeyValues = new HashMap<>();

    public AttributeMap set(String strKey, String strValue) {
        mmapKeyValues.put(strKey, Objects.requireNonNull(strValue));
        return this;
    }

    public AttributeMap set(String strKey, double dValue) {
        mmapKeyValues.put(strKey, dValue);
        return this;
    }

    public AttributeMap set(String strKey, boolean bValue) {
        mmapKeyValues.put(strKey, bValue);
        return this;
    }

    public JSONObject toJson() {
        return new JSONObject(mmapKeyValues);
    }

    @Override
    public String toString() {
        return mmapKeyValues.toString();
    }
}
