package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiV2Response;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class EndDurationRequest extends PostRequest {
    private AttributeMap mAttributes;

    public EndDurationRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/end-duration"; }

    /**
     * Sets the signal for duration by ID.
     * <em>At least one of span ID, signal ID, or signal name is required.</em>
     *
     * @param signalId
     * @return this
     */
    public EndDurationRequest signalId(UUID signalId) {
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
    public EndDurationRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the signal description for duration.
     *
     * @param strSignalDescription
     * @return this
     */
    public EndDurationRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Sets the span for this duration span by ID.
     * <em>At least one of span ID, signal ID, or signal name is required.</em>
     *
     * @param spanId
     * @return this
     */
    public EndDurationRequest spanId(UUID spanId) {
        getParameters().put("span_id", spanId);
        return this;
    }

    /**
     * Sets the end time. By default, uses current time.
     *
     * @param time
     * @return this
     */
    public EndDurationRequest time(OffsetDateTime time) {
        getParameters().put("time", time);
        return this;
    }

    /**
     * Sets the duration span failure status. If true, indicates that this span did not complete
     * successfully.
     *
     * @param bIsFailure
     * @return this
     */
    public EndDurationRequest failure(boolean bIsFailure) {
        getParameters().put("failure", bIsFailure);
        return this;
    }

    /**
     * Sets the duration span ending attributes.
     *
     * @param attributes
     * @return this
     */
    public EndDurationRequest attributes(AttributeMap attributes) {
        mAttributes = attributes;
        return this;
    }

    /**
     * Sets a string attribute.
     *
     * @param strKey
     * @param strValue
     * @return this
     */
    public EndDurationRequest attribute(String strKey, String strValue) {
        if (mAttributes == null) {
            mAttributes = new AttributeMap();
        }
        mAttributes.set(strKey, strValue);
        return this;
    }

    /**
     * Sets a number attribute.
     *
     * @param strKey
     * @param dValue
     * @return this
     */
    public EndDurationRequest attribute(String strKey, double dValue) {
        if (mAttributes == null) {
            mAttributes = new AttributeMap();
        }
        mAttributes.set(strKey, dValue);
        return this;
    }

    /**
     * Sets a boolean attribute.
     *
     * @param strKey
     * @param bValue
     * @return this
     */
    public EndDurationRequest attribute(String strKey, boolean bValue) {
        if (mAttributes == null) {
            mAttributes = new AttributeMap();
        }
        mAttributes.set(strKey, bValue);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the duration that was ended
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

