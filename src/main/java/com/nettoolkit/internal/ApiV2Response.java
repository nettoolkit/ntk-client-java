package com.nettoolkit.internal;

import java.net.http.HttpResponse;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.ResponseParsingException;
import com.nettoolkit.exception.ApiException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

public class ApiV2Response {
    private String mstrResponseBody;
    private JSONObject mjsonResponseBody;

    public ApiV2Response(HttpResponse<String> response)
            throws ParsingException, ApiException {
        mstrResponseBody = response.body();
        mjsonResponseBody = parseBody(mstrResponseBody);
    }

    public String getRawResponseBody() { return mstrResponseBody; }

    public JSONObject getJsonBody() { return mjsonResponseBody; }

    public JSONObject getData() throws ResponseParsingException {
        try {
            return mjsonResponseBody.getJSONObject("data");
        } catch (Exception e) {
            throw ResponseParsingException.expectedJsonObject(
                "body",
                "data",
                mjsonResponseBody.opt("data"),
                e,
                mjsonResponseBody
            );
        }
    }

    public JSONObject getDataJsonObject(String strKey) throws ResponseParsingException {
        JSONObject jsonData = getData();
        try {
            return jsonData.getJSONObject(strKey);
        } catch (Exception e) {
            throw ResponseParsingException.expectedJsonObject(
                "data",
                strKey,
                jsonData.opt(strKey),
                e,
                jsonData
            );
        }
    }

    public JSONArray getDataJsonArray(String strKey) throws ResponseParsingException {
        JSONObject jsonData = getData();
        try {
            return jsonData.getJSONArray(strKey);
        } catch (Exception e) {
            throw ResponseParsingException.expectedJsonArray(
                "data",
                strKey,
                jsonData.opt(strKey),
                e,
                jsonData
            );
        }
    }

    // Helpers
    protected static JSONObject parseBody(String strResponseJson)
            throws ParsingException, ApiException {
        if (strResponseJson == null || strResponseJson.length() < 1) {
            throw new ParsingException("Got empty response", strResponseJson);
        }
        try {
            JSONObject jsonBody = new JSONObject(strResponseJson);
            JSONArray jsonErrors = jsonBody.optJSONArray("errors");
            if (jsonErrors != null && jsonErrors.length() > 0) {
                throw new ApiException(ApiException.ApiError.listFromJson(jsonErrors));
            }
            return jsonBody;
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, strResponseJson);
        }
    }
}


