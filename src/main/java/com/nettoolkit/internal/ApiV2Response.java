package com.nettoolkit.internal;

import java.net.http.HttpResponse;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.ApiV2Exception;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

public class ApiV2Response {
    private String mstrResponseBody;
    private JSONObject mjsonResponseBody;

    public ApiV2Response(HttpResponse<String> response)
            throws ParsingException, ApiV2Exception {
        mstrResponseBody = response.body();
        mjsonResponseBody = parseBody(mstrResponseBody);
    }

    public String getRawResponseBody() { return mstrResponseBody; }

    public JSONObject getJsonBody() { return mjsonResponseBody; }

    // Helpers
    protected static JSONObject parseBody(String strResponseJson)
            throws ParsingException, ApiV2Exception {
        if (strResponseJson == null || strResponseJson.length() < 1) {
            throw new ParsingException("Got empty response", strResponseJson);
        }
        try {
            JSONObject jsonBody = new JSONObject(strResponseJson);
            JSONArray jsonErrors = jsonBody.optJSONArray("errors");
            if (jsonErrors != null && jsonErrors.length() > 0) {
                throw new ApiV2Exception(ApiV2Exception.ApiError.listFromJson(jsonErrors));
            }
            return jsonBody;
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, strResponseJson);
        }
    }
}


