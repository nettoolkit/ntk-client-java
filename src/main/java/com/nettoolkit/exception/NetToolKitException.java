package com.nettoolkit.exception;

/**
 * The base exception for the client library.
 * All other NetToolKit specific exceptions are subclasses of NetToolKitException.
 */
public class NetToolKitException extends Exception {
    public NetToolKitException(String strMessage) {
        super(strMessage);
    }

    public NetToolKitException(Throwable cause) {
        super(cause);
    }

    public NetToolKitException(String strMessage, Throwable cause) {
        super(strMessage, cause);
    }
}

