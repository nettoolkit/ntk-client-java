package com.nettoolkit.dashboards;

import com.nettoolkit.internal.NetToolKitClient;

public class DashboardsClient extends NetToolKitClient {
    /**
     * Constructs a new GatekeeperClient which uses the given API key for requests.
     *
     * @param strApiKey The <a href="https://www.nettoolkit.com/docs/overview#authentication">NetToolKit API key</a> used to authenticate requests. All requests made by DashboardsClient will use this API key.
     */
    public DashboardsClient(String strApiKey) {
        super(strApiKey);
    }

    public DashboardsClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }

    /**
     * Creates a new request to create a channel datum. Call {@link com.nettoolkit.dashboards.CreateChannelDatumRequest#send} to execute.
     *
     * @return a new create channel datum request object
     */
    public CreateChannelDatumRequest newCreateChannelDatumRequest() {
        return new CreateChannelDatumRequest(this);
    }

    /**
     * Creates a new request to create a Channel. Call {@link com.nettoolkit.dashboards.CreateChannelRequest#send} to execute.
     *
     * @return a new create chanel request object
     */
    public CreateChannelRequest newCreateChannelRequest() {
        return new CreateChannelRequest(this);
    }

    /**
     * Creates a new request to create metrics. Call {@link com.nettoolkit.dashboards.CreateGaugeRequest#send} to execute.
     *
     * @return a new create metrics request object
     */
    public CreateMetricsRequest newCreateMetricsRequest() {
        return new CreateMetricsRequest(this);
    }

    /**
     * Creates a new request to create a gauge. Call {@link com.nettoolkit.dashboards.CreateGaugeRequest#send} to execute.
     *
     * @return a new create gauge request object
     */
    public CreateGaugeRequest newCreateGaugeRequest() {
        return new CreateGaugeRequest(this);
    }

    /**
     * Creates a new request to create logs. Call {@link com.nettoolkit.dashboards.CreateLogsRequest#send} to execute.
     *
     * @return a new create metrics request object
     */
    public CreateLogsRequest newCreateLogsRequest() {
        return new CreateLogsRequest(this);
    }

    /**
     * Creates a new request to create a log. Call {@link com.nettoolkit.dashboards.CreateLogRequest#send} to execute.
     *
     * @return a new create log request object
     */
    public CreateLogRequest newCreateLogRequest() {
        return new CreateLogRequest(this);
    }

    /**
     * Creates a new request to start a duration. Call {@link com.nettoolkit.dashboards.StartDurationRequest#send} to execute.
     *
     * @return a new start duration request object
     */
    public StartDurationRequest newStartDurationRequest() {
        return new StartDurationRequest(this);
    }

    /**
     * Creates a new request to create a duration event. Call {@link com.nettoolkit.dashboards.CreateDurationEventRequest#send} to execute.
     *
     * @return a new create duration event request object
     */
    public CreateDurationEventRequest newCreateDurationEventRequest() {
        return new CreateDurationEventRequest(this);
    }

    /**
     * Creates a new request to end a duration. Call {@link com.nettoolkit.dashboards.EndDurationRequest#send} to execute.
     *
     * @return a new end duration request object
     */
    public EndDurationRequest newEndDurationRequest() {
        return new EndDurationRequest(this);
    }
}

