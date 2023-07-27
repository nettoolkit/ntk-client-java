package com.nettoolkit.exception;

import java.util.ArrayList;
import java.util.List;

import com.nettoolkit.internal.StatusCode;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

/**
 * ApiV2Exception is thrown when the web API sends an error response packet for the given request.
 * Note that unlike {@link ApiException}, this exception can represent multiple errors.
 * See <a href="https://www.nettoolkit.com/docs/overview#response">the web API response</a> docs for a list of possible error codes.
 */
public class ApiV2Exception extends NetToolKitException {
    public ApiV2Exception(List<ApiError> listErrors) {
        super(buildMessage(listErrors));
    }

    protected static String buildMessage(List<ApiError> listErrors) {
        StringBuilder sb = new StringBuilder("Encountered one or more errors from the API.");
        for (ApiError error : listErrors) {
            sb.append("\n- " + getReadableStatus(error.getStatusCode().toInt()) + ": "
                + error.getMessage());
        }
        return sb.toString();
    }

    protected static String getReadableStatus(int iCode) {
        String strStatusCode;
        StatusCode statusCode = StatusCode.fromInt(iCode);

        if (statusCode != null) {
            strStatusCode = statusCode.name() + " (" + iCode + ")";
        } else {
            strStatusCode = String.valueOf(iCode);
        }

        return strStatusCode;
    }

    public static class ApiError {
        private StatusCode mStatusCode;
        private String mstrMessage;

        public ApiError(int iCode, String strMessage) {
            mStatusCode = StatusCode.fromInt(iCode);
            mstrMessage = strMessage;
        }

        public StatusCode getStatusCode() {
            return mStatusCode;
        }

        public String getMessage() {
            return mstrMessage;
        }

        public static ApiError fromJson(JSONObject jsonError) throws JSONException {
            return new ApiError(jsonError.getInt("code"), jsonError.optString("message", ""));
        }

        public static List<ApiError> listFromJson(JSONArray jsonErrors) throws JSONException {
            List<ApiError> listErrors = new ArrayList<>();
            for (int i = 0; i < jsonErrors.length(); i++) {
                listErrors.add(fromJson(jsonErrors.getJSONObject(i)));
            }
            return listErrors;
        }
    }
}

