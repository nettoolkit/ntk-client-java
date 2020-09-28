package com.nettoolkit.exception;

import com.nettoolkit.exception.NetToolKitException;

/**
 * BadArgumentException is thrown when an invalid argument is provided to one of the client's methods.
 */
public class BadArgumentException extends NetToolKitException {
    public BadArgumentException(String strMessage, Object arg) {
        super(strMessage + " (argument = " + arg + ")");
    }
}

