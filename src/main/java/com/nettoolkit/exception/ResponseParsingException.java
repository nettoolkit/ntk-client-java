package com.nettoolkit.exception;


/**
 * ResponseParsingException is thrown when the client encounters an error while parsing a response.
 */
public class ResponseParsingException extends NetToolKitException {
    protected Object mSource;

    public ResponseParsingException(Throwable cause, Object source) {
        this(cause.getMessage(), cause, source);
    }

    public ResponseParsingException(String strMessage, Object source) {
        super(buildMessage(strMessage, source));
        mSource = source;
    }

    public ResponseParsingException(String strMessage, Throwable cause, Object source) {
        super(buildMessage(strMessage, source), cause);
        mSource = source;
    }

    public static ResponseParsingException expectedString(
        String strResource,
        String strFieldName,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return expected(strResource, strFieldName, "a valid string", actualValue, cause, source);
    }

    public static ResponseParsingException expectedUuid(
        String strResource,
        String strFieldName,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return expected(strResource, strFieldName, "a valid UUID", actualValue, cause, source);
    }

    public static ResponseParsingException expectedUnixMsTimestamp(
        String strResource,
        String strFieldName,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return expected(
            strResource,
            strFieldName,
            "a Unix millisecond timestamp",
            actualValue,
            cause,
            source
        );
    }

    public static ResponseParsingException expectedJsonObject(
        String strResource,
        String strFieldName,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return expected(strResource, strFieldName, "a JSON object", actualValue, cause, source);
    }

    public static ResponseParsingException expectedJsonArray(
        String strResource,
        String strFieldName,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return expected(strResource, strFieldName, "a JSON array", actualValue, cause, source);
    }

    public static ResponseParsingException expected(
        String strResource,
        String strFieldName,
        String strExpectedType,
        Object actualValue,
        Throwable cause,
        Object source
    ) {
        return new ResponseParsingException(
            "Expected " + strResource + "." + strFieldName + " to be " + strExpectedType + ", "
                + "but instead got " + actualValue + ".",
            cause,
            source
        );
    }

    public Object getSource() { return mSource; }

    private static String buildMessage(String strBaseMessage, Object source) {
        return "Encountered an error while parsing API response. "
            + strBaseMessage + " source = " + source;
    }
}


