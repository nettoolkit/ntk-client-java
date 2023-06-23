package com.nettoolkit.exception;

import com.nettoolkit.internal.StatusCode;

/**
 * ApiException is thrown when the web API sends an error response packet for the given request.
 * See <a href="https://www.nettoolkit.com/docs/overview#response">the web API response</a> docs for a list of possible error codes.
 */
public class ApiException extends NetToolKitException {
    public ApiException(int iCode, String strMessage) {
        super(getStatusCode(iCode) + ": " + strMessage);
    }

    protected static String getStatusCode(int iCode) {
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

