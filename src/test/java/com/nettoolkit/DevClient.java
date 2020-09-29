package com.nettoolkit;

public interface DevClient {
    public int getPort();
    public default String getHostname() {
        String strHostname = "devapi.nettoolkit.com";
        if (getPort() > 0) {
            strHostname += ":" + getPort();
        }
        return strHostname;
    }
}

