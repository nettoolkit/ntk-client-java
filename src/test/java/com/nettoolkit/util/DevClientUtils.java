package com.nettoolkit.util;

public class DevClientUtils {
    private DevClientUtils() {}

    public static String getDevHostname(int iPort) {
        String strHostname = "devapi.nettoolkit.com";
        if (iPort > 0) {
            strHostname += ":" + iPort;
        }
        return strHostname;
    }
}

