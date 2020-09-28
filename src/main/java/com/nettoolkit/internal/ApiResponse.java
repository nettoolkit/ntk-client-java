package com.nettoolkit.internal;

import java.net.http.HttpResponse;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.ApiException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

public class ApiResponse {
    private String mstrResponseBody;
    private JSONObject mjsonResponseBody;

    public ApiResponse(HttpResponse<String> response)
            throws ParsingException, ApiException {
        mstrResponseBody = response.body();
        mjsonResponseBody = parseResponse(mstrResponseBody);
    }

    public String getRawResponseBody() { return mstrResponseBody; }

    public JSONArray getResults() throws ParsingException {
        JSONArray jsonResults = mjsonResponseBody.optJSONArray("results");
        if (jsonResults == null) {
            throw new ParsingException("Missing 'results' from response", mjsonResponseBody);
        }
        return jsonResults;
    }

    public JSONObject getFirstResult() throws ParsingException {
        JSONArray jsonResults = getResults();
        if (jsonResults.length() < 1) {
            throw new ParsingException("No results", jsonResults);
        }
        return jsonResults.optJSONObject(0);
    }

    // Helpers
    protected static JSONObject parseResponse(String strResponse)
            throws ParsingException, ApiException {
        if (strResponse == null || strResponse.length() < 1) {
            throw new ParsingException("Got empty response", strResponse);
        }

        try {
            JSONObject jsonResponse = new JSONObject(strResponse);
            int iCode = jsonResponse.getInt("code");
            if (iCode >= 2000) {
                throw new ApiException(iCode, jsonResponse.optString("message"));
            }
            return jsonResponse;
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, strResponse);
        }
    }
}

