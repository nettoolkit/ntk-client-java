package com.nettoolkit.exception;

import java.net.http.HttpTimeoutException;
import java.time.OffsetDateTime;
import com.nettoolkit.internal.request.BaseApiRequest;

/**
 * ApiConnectionException is thrown when the client cannot connect to the web API.
 * There should always be an underlying cause for this exceptions; for example a java.net.ConnectException or java.io.IOException.
 */
public class ApiConnectionException extends NetToolKitException {
    public ApiConnectionException(Throwable cause) {
        super(cause);
    }

    public ApiConnectionException(
        HttpTimeoutException cause,
        OffsetDateTime requestStartTime,
        BaseApiRequest request
    ) {
        super(
            request + " timed out (timeout = " + request.getClient().getTimeout() + "ms, "
                + "request_start_time = " + requestStartTime + ", request_end_time = "
                + OffsetDateTime.now() + ")",
            cause
        );
    }
}

