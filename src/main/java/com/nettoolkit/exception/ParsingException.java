package com.nettoolkit.exception;


/**
 * ParsingException is thrown when the client encounters an error while parsing a request argument or response packet.
 */
public class ParsingException extends NetToolKitException {
    protected Object mSource;

    public ParsingException(Throwable cause, Object source) {
        this(cause.getMessage(), cause, source);
    }

    public ParsingException(String strMessage, Object source) {
        super(strMessage + " source = " + source);
        mSource = source;
    }

    public ParsingException(String strMessage, Throwable cause, Object source) {
        super(strMessage + " source = " + source, cause);
        mSource = source;
    }

    public Object getSource() { return mSource; }
}

