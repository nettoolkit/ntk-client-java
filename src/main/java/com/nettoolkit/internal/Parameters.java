package com.nettoolkit.internal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;

@SuppressWarnings("unchecked")
public class Parameters {
    private Map<String, Object> mmapParameters = new HashMap<>();

    public void put(String strKey, Boolean bValue) {
        mmapParameters.put(strKey, bValue);
    }

    public void put(String strKey, Integer iValue) {
        mmapParameters.put(strKey, iValue);
    }

    public void put(String strKey, Long lValue) {
        mmapParameters.put(strKey, lValue);
    }

    public void put(String strKey, Double dValue) {
        mmapParameters.put(strKey, dValue);
    }

    public void put(String strKey, String strValue) {
        mmapParameters.put(strKey, strValue);
    }

    public void put(String strKey, Collection<?> cValues) {
        mmapParameters.put(strKey, cValues);
    }

    public void put(String strKey, JSONObject jsonObj) {
        mmapParameters.put(strKey, jsonObj);
    }

    public void put(String strKey, JSONArray jsonArr) {
        mmapParameters.put(strKey, jsonArr);
    }

    public void put(String strKey, UUID uuid) {
        mmapParameters.put(strKey, uuid);
    }

    public String toWwwFormUrlencoded() throws ParsingException {
        StringBuilder sb = new StringBuilder();
        boolean bIsFirstParameter = true;
        for (Map.Entry<String, Object> entry : mmapParameters.entrySet()) {
            List<Object> listValues = new ArrayList<>();

            if (entry.getValue() == null) {
                listValues.add(null);
            } else if (entry.getValue() instanceof Collection) {
                listValues.addAll((Collection<Object>) entry.getValue());
            } else {
                listValues.add(entry.getValue());
            }

            for (Object value : listValues) {
                if (bIsFirstParameter) {
                    bIsFirstParameter = false;
                } else {
                    sb.append("&");
                }

                try {
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
                } catch (UnsupportedEncodingException uee) {
                    throw new ParsingException(uee, entry);
                }
            }
        }
        return sb.toString();
    }

    public JSONObject toJson() {
        return new JSONObject(mmapParameters);
    }
}

