package com.nettoolkit.exception;

import com.nettoolkit.exception.NetToolKitException;

/**
 * ApiConnectionException is thrown when the client cannot connect to the web API.
 * There should always be an underlying cause for this exceptions; for example a java.net.ConnectException or java.io.IOException.
 */
public class ApiConnectionException extends NetToolKitException {
    public ApiConnectionException(Throwable cause) {
        super(cause);
    }
}

