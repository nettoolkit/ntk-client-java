package com.nettoolkit.dashboards;

import java.util.UUID;
import java.time.OffsetDateTime;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateLogRequest extends PostRequest {
    public CreateLogRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/create-log"; }

    /**
     * Sets the signal for log by ID.
     * <em>At least one of signal ID or signal name is required.</em>
     *
     * @param signalId
     * @return this
     */
    public CreateLogRequest signalId(UUID signalId) {
        getParameters().put("signal_id", signalId);
        return this;
    }

    /**
     * Sets the signal for log by name.
     * <em>At least one of signal ID or signal name is required.</em>
     *
     * @param strSignalName
     * @return this
     */
    public CreateLogRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the signal description for log.
     *
     * @param strSignalDescription
     * @return this
     */
    public CreateLogRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Sets the log time. Default is current time.
     *
     * @param time
     * @return this
     */
    public CreateLogRequest time(OffsetDateTime time) {
        getParameters().put("time", time);
        return this;
    }

    /**
     * Sets the log observed time. Default is current time.
     *
     * @param time
     * @return this
     */
    public CreateLogRequest observedTime(OffsetDateTime time) {
        getParameters().put("observed_time", time);
        return this;
    }

    /**
     * Sets the log severity.
     *
     * @param severity
     * @return this
     */
    public CreateLogRequest severity(LogSeverity severity) {
        getParameters().put("severity", severity.toNumber());
        return this;
    }

    /**
     * Sets the log severity text.
     *
     * @param strSeverityText
     * @return this
     */
    public CreateLogRequest severityText(String strSeverityText) {
        getParameters().put("severity_text", strSeverityText);
        return this;
    }

    /**
     * Sets the log body.
     *
     * @param strBody
     * @return this
     */
    public CreateLogRequest body(String strBody) {
        getParameters().put("body", strBody);
        return this;
    }

    /**
     * Sets a string attribute.
     *
     * @param strKey
     * @param strValue
     * @return this
     */
    public CreateLogRequest attribute(String strKey, String strValue) {
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
    public CreateLogRequest attribute(String strKey, double dValue) {
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
    public CreateLogRequest attribute(String strKey, boolean bValue) {
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

