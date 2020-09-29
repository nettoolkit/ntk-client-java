package com.nettoolkit.gatekeeper;

import com.nettoolkit.util.DevClientUtils;
import com.nettoolkit.gatekeeper.GatekeeperClient;

public class DevGatekeeperClient extends GatekeeperClient {
    protected int miPort;

    public DevGatekeeperClient(String strApiKey, boolean bUseHttps, int iPort) {
        super(strApiKey, bUseHttps);
        miPort = iPort;
    }

    @Override
    public String getHostname() {
        return DevClientUtils.getDevHostname(miPort);
    }
}
