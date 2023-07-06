package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiV2Response;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class StartDurationRequest extends PostRequest {
    private AttributeMap mAttributes;

    public StartDurationRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/start-duration"; }

    /**
     * Sets the signal for duration by ID.
     * <em>At least one of span ID, signal ID, or signal name is required.</em>
     *
     * @param signalId
     * @return this
     */
    public StartDurationRequest signalId(UUID signalId) {
        getParameters().put("signal_id", signalId);
        return this;
    }

    /**
     * Sets the signal for duration by name.
     * <em>At least one of span ID, signal ID, or signal name is required.</em>
     *
     * @param strSignalName
     * @return this
     */
    public StartDurationRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the signal description for duration.
     *
     * @param strSignalDescription
     * @return this
     */
    public StartDurationRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Sets the start time. By default, uses current time.
     *
     * @param time
     * @return this
     */
    public StartDurationRequest time(OffsetDateTime time) {
        getParameters().put("time", time);
        return this;
    }

    /**
     * Sets the duration span attributes.
     *
     * @param attributes
     * @return this
     */
    public StartDurationRequest attributes(AttributeMap attributes) {
        mAttributes = attributes;
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the new duration span
     * @throws NetToolKitException
     */
    public DurationSpan send() throws NetToolKitException {
        JSONObject jsonAttributes = null;
        if (mAttributes != null) {
            jsonAttributes = mAttributes.toJson();
        }
        getParameters().put("attributes", jsonAttributes);
        ApiV2Response response = getClient().sendV2(this);
        return DurationSpan.fromResponseJson(
            response.getDataJsonObject("duration_span")
        );
    }
}

