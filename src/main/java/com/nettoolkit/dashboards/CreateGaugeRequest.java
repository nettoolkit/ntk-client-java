package com.nettoolkit.dashboards;

import java.util.UUID;
import java.time.OffsetDateTime;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateGaugeRequest extends PostRequest {
    public CreateGaugeRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/create-gauge"; }

    /**
     * Sets the signal for gauge by ID.
     * <em>At least one of signal ID or signal name is required.</em>
     *
     * @param signalId
     * @return this
     */
    public CreateGaugeRequest signalId(UUID signalId) {
        getParameters().put("signal_id", signalId);
        return this;
    }

    /**
     * Sets the signal for gauge by name.
     * <em>At least one of signal ID or signal name is required.</em>
     *
     * @param strSignalName
     * @return this
     */
    public CreateGaugeRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the signal description for gauge.
     *
     * @param strSignalDescription
     * @return this
     */
    public CreateGaugeRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Sets the gauge value.
     * <em>required</em>
     *
     * @param dValue
     * @return this
     */
    public CreateGaugeRequest value(double dValue) {
        getParameters().put("value", dValue);
        return this;
    }

    /**
     * Sets the gauge time. Default is current time.
     *
     * @param time
     * @return this
     */
    public CreateGaugeRequest time(OffsetDateTime time) {
        getParameters().put("time", time);
        return this;
    }

    /**
     * Sets a string attribute.
     *
     * @param strKey
     * @param strValue
     * @return this
     */
    public CreateGaugeRequest attribute(String strKey, String strValue) {
        getParameters().put(strKey, strValue);
        return this;
    }

    /**
     * Sets a number attribute.
     *
     * @param strKey
     * @param dValue
     * @return this
     */
    public CreateGaugeRequest attribute(String strKey, double dValue) {
        getParameters().put(strKey, dValue);
        return this;
    }

    /**
     * Sets a boolean attribute.
     *
     * @param strKey
     * @param bValue
     * @return this
     */
    public CreateGaugeRequest attribute(String strKey, boolean bValue) {
        getParameters().put(strKey, bValue);
        return this;
    }

    /**
     * Sends the request.
     *
     * @throws NetToolKitException
     */
    public void send() throws NetToolKitException {
        getClient().sendV2(this);
    }
}

