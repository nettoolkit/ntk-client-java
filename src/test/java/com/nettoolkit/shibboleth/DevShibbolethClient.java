package com.nettoolkit.shibboleth;

import com.nettoolkit.util.DevClientUtils;
import com.nettoolkit.shibboleth.ShibbolethClient;

public class DevShibbolethClient extends ShibbolethClient {
    protected int miPort;

    public DevShibbolethClient(String strApiKey, boolean bUseHttps, int iPort) {
        super(strApiKey, bUseHttps);
        miPort = iPort;
    }

    @Override
    public String getHostname() {
        return DevClientUtils.getDevHostname(miPort);
    }
}
