package com.nettoolkit.exception;

import com.nettoolkit.internal.StatusCode;

/**
 * ApiException is thrown when the web API sends an error response packet for the given request.
 * See <a href="https://www.nettoolkit.com/docs/overview#response">the web API response</a> docs for a list of possible error codes.
 */
public class ApiException extends NetToolKitException {
    private StatusCode mStatusCode;

    public ApiException(int iCode, String strMessage) {
        super(getReadableStatusCode(iCode) + ": " + strMessage);
        mStatusCode = StatusCode.fromInt(iCode);
    }

    public StatusCode getStatusCode() {
        return mStatusCode;
    }

    protected static String getReadableStatusCode(int iCode) {
        String strStatusCode;
        StatusCode statusCode = StatusCode.fromInt(iCode);

        if (statusCode != null) {
            strStatusCode = statusCode.name() + " (" + iCode + ")";
        } else {
            strStatusCode = String.valueOf(iCode);
        }

        return strStatusCode;
    }
}

