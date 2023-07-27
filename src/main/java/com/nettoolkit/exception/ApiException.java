package com.nettoolkit.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.nettoolkit.internal.StatusCode;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

/**
 * ApiException is thrown when the web API sends an error response packet for the given request.
 * See <a href="https://www.nettoolkit.com/docs/overview#response">the web API response</a> docs for a list of possible error codes.
 */
public class ApiException extends NetToolKitException {
    private List<ApiError> mlistErrors;

    public ApiException(int iCode, String strMessage) {
        this(buildApiErrorsFromSingleError(iCode, strMessage));
    }

    public ApiException(List<ApiError> listErrors) {
        super(buildExceptionMessage(listErrors));
        mlistErrors = listErrors;
    }

    /**
     * Gets the status code of the first error received from the web API.
     *
     * @return status code of the first error
     */
    public StatusCode getStatusCode() {
        return mlistErrors.get(0).getStatusCode();
    }

    /**
     * Gets the message of the first error received from the web API.
     *
     * @return message of the first error
     */
    public String getErrorMessage() {
        return mlistErrors.get(0).getErrorMessage();
    }

    /**
     * Gets a list of errors received from the web API.
     *
     * @return list of errors
     */
    public List<ApiError> getApiErrors() {
        return Collections.unmodifiableList(mlistErrors);
    }

    private static String buildExceptionMessage(List<ApiError> listErrors) {
        if (listErrors.isEmpty()) {
            return "Got an API exception withouth any errors. If you see this message, please "
                + "report it at https://www.nettoolkit.com/contact.";
        }
        // If there's only one error, just return use that as the exception message.
        if (listErrors.size() < 2) {
            return listErrors.get(0).asExceptionMessage();
        }
        // There are multiple errors, build out a list of errors as the exception message.
        StringBuilder sb = new StringBuilder("Got multiple API errors.");
        for (ApiError error : listErrors) {
            sb.append("\n- " + error.asExceptionMessage());
        }
        return sb.toString();
    }

    private static List<ApiError> buildApiErrorsFromSingleError(int iCode, String strMessage) {
        JSONObject jsonError;
        try {
            jsonError = new JSONObject().put("code", iCode).put("message", strMessage);
        } catch (JSONException jsone) {
            // Just swallow this error message and set the JSON error object to null.
            // This really should never happen.
            jsonError = null;
        }
        ApiError error = new ApiError(StatusCode.fromInt(iCode), strMessage, jsonError);
        return List.of(error);
    }

    /**
     * A web API error.
     */
    public static class ApiError {
        private StatusCode mStatusCode;
        private String mstrMessage;
        private JSONObject mjsonOriginalError;

        public ApiError(StatusCode statusCode, String strMessage, JSONObject jsonOriginalError) {
            if (statusCode == null) {
                statusCode = StatusCode.UNKNOWN_ERROR;
            }
            mStatusCode = statusCode;
            mstrMessage = strMessage;
            mjsonOriginalError = jsonOriginalError;
        }

        public static ApiError fromJson(JSONObject jsonError) throws JSONException {
            StatusCode statusCode = StatusCode.fromInt(jsonError.getInt("code"));
            if (statusCode == null) {
                statusCode = StatusCode.UNKNOWN_ERROR;
            }
            return new ApiError(statusCode, jsonError.optString("message", ""), jsonError);
        }

        public static List<ApiError> listFromJson(JSONArray jsonErrors) throws JSONException {
            List<ApiError> listErrors = new ArrayList<>();
            for (int i = 0; i < jsonErrors.length(); i++) {
                listErrors.add(fromJson(jsonErrors.getJSONObject(i)));
            }
            return listErrors;
        }

        /**
         * Gets the error status code.
         *
         * @return status code
         */
        public StatusCode getStatusCode() {
            return mStatusCode;
        }

        /**
         * Gets the error message.
         *
         * @return error message
         */
        public String getErrorMessage() {
            return mstrMessage;
        }

        /**
         * Gets the original JSON object used to construct this error.
         *
         * @return original error JSON
         */
        public JSONObject getOriginalError() {
            return mjsonOriginalError;
        }

        /**
         * Gets the error as a readable string, fit for logging.
         *
         * @return the error as a readable string
         */
        public String asExceptionMessage() {
            String strStatusCode;
            if (
                mStatusCode == StatusCode.UNKNOWN_ERROR
                && mjsonOriginalError != null
                && !mjsonOriginalError.isNull("code")
            ) {
                // It's possible that this is an unrecognized status code. In these cases, we'll use
                // UNKNOWN_ERROR, but attempt to set the original status code number value. 
                strStatusCode = String.valueOf(mjsonOriginalError.optInt("code", 0));
            } else {
                strStatusCode = mStatusCode.name() + " (" + mStatusCode.toInt() + ")";
            }
            return strStatusCode + ": " + mstrMessage;
        }
    }
}

