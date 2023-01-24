package com.nettoolkit.internal;

import java.util.Map;
import java.util.HashMap;

public enum StatusCode {
    // 1000 - 1999: success
    OK(1000, "Request OK", 200),
    UNDERSTOOD(1001, "Request understood, but not completed", 200),
    // 2000 - 2999: server error
    INTERNAL_ERROR(2000, "Something unexpected went wrong", 500),
    UNKNOWN_ERROR(2001, "Error with unclear cause", 500),
    CONNECTION_TIMEOUT(2002, "Connection to external service timed out", 408),
    SERVICE_TIMEOUT(2003, "Connection to internal service timed out", 408),
    GEOCODE_SERVER_BUSY(2004, "Geocode server is busy", 503),
    SERVICE_UNAVAILABLE(2005, "Service is temporarily unavailable", 503),
    BAD_GATEWAY(2006, "Got an invalid response from a remote host", 502),
    // 3000 - 3999: client meta error
    NOT_PERMITTED(3000, "Request not permitted", 403),
    INSUFFICIENT_CREDITS(3001, "Not enough credits to complete request", 400),
    INVALID_CREDENTIALS(3002, "Invalid credentials for request", 401),
    OVER_QUOTA(3003, "Over request quota for resource", 403),
    ACCOUNT_DATABASE_REQUIRED(3004, "Account does not have an associated database", 403),
    VALIDATION_REQUIRED(3005, "User must be validated before continuing", 403),
    TOO_MANY_REQUESTS(3007, "The client is making too many requests", 429),
    PAYMENT_FAILURE(3400, "Payment was not accepted", 403),
    MISSING_PAYMENT_METHOD(3401, "The account needs a payment method to process this request", 403),
    INVALID_PAYMENT_METHOD(3402, "The account payment method cannot be used to create charges", 403),
    // 4000 - 4999: client request error
    BAD_REQUEST(4000, "Invalid request", 400),
    INVALID_PARAMETER(4001, "Invalid parameter encountered in request", 400),
    MISSING_PARAMETER(4002, "Request missing required parameter", 400),
    // 5000 - 5999: resource error
    NOT_FOUND(5000, "No results found", 404),
    RESOURCE_DEPENDENCY_ERROR(5001,
        "Another resource depends on requested resource", 409),
    CONFLICT(5002, "Resource conflict encountered", 409),
    RESOURCE_EXPIRED(5003, "Resource has expired", 404), 
    UNKNOWN_ROUTE(5500, "No matching route found", 404);

    protected static Map<Integer, StatusCode> mmapCodes = new HashMap<>();
    static {
        for (StatusCode code : StatusCode.values()) {
            mmapCodes.put(code.toInt(), code);
        }
    }
    protected final int miCode;
    protected final String mstrDescription;
    protected final int miHttpStatusCode;
    StatusCode(int iCode, String strDescription, int iHttpStatusCode) {
        miCode = iCode;
        mstrDescription = strDescription;
        miHttpStatusCode = iHttpStatusCode;
    }
    public int toInt() { return miCode; }
    public String getDescription() { return mstrDescription; }
    public int getHttpStatusCode() { return miHttpStatusCode; }
    public static StatusCode fromInt(int iCode) {
        return mmapCodes.get(iCode);
    }
}

