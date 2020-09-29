package com.nettoolkit.dashboards;

import com.nettoolkit.util.DevClientUtils;
import com.nettoolkit.dashboards.DashboardsClient;

public class DevDashboardsClient extends DashboardsClient {
    protected int miPort;

    public DevDashboardsClient(String strApiKey, boolean bUseHttps, int iPort) {
        super(strApiKey, bUseHttps);
        miPort = iPort;
    }

    @Override
    public String getHostname() {
        return DevClientUtils.getDevHostname(miPort);
    }
}
