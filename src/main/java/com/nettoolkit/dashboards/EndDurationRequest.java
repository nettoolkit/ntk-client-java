package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class EndDurationRequest extends PostRequest {
    public EndDurationRequest(DashboardsClient client) {
        super(client);
        getParameters().put("name", "end");
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/dashboards/durations"; }

    /**
     * Sets the duration to end.
     * <em>required unless channel ID/name provided</em>
     *
     * @param durationId the duration ID
     * @return this
     */
    public EndDurationRequest durationId(UUID durationId) {
        getParameters().put("duration_id", durationId);
        return this;
    }

    /**
     * Sets the duration to end by channel name.
     *
     * @param strChannelName the channel name
     * @return this
     */
    public EndDurationRequest channelName(String strChannelName) {
        getParameters().put("channel_name", strChannelName);
        return this;
    }

    /**
     * Sets the duration to end by channel ID.
     *
     * @param channelId the channel ID
     * @return this
     */
    public EndDurationRequest channelId(UUID channelId) {
        getParameters().put("channel_id", channelId);
        return this;
    }

    /**
     * Sets the end time. By default, uses current time.
     *
     * @param timestamp the end time
     * @return this
     */
    public EndDurationRequest timestamp(OffsetDateTime timestamp) {
        getParameters().put("timestamp", timestamp);
        return this;
    }

    /**
     * Sets the end failure status. If true, this indicates that this duration ended in failure.
     *
     * @param bIsFailure the failure status
     * @return this
     */
    public EndDurationRequest failure(boolean bIsFailure) {
        getParameters().put("failure", bIsFailure);
        return this;
    }

    /**
     * Sets the duration end additional values JSON.
     *
     * @param jsonAdditionalValues the additional values JSON
     * @return this
     */
    public EndDurationRequest additionalValues(JSONObject jsonAdditionalValues) {
        getParameters().put("additional_values", jsonAdditionalValues);
        return this;
    }

    /**
     * Sets the duration end additional values JSON string. This is an escape hatch for situations 
     * where it's inconvenient to use the provided {@link com.nettoolkit.json.JSONObject}. If you use
     * this function, you must ensure that the given string represents a valid JSON object.
     *
     * @param strAdditionalValuesJson the additional values JSON string
     * @return this
     */
    public EndDurationRequest additionalValues(String strAdditionalValuesJson) {
        getParameters().put("additional_values", strAdditionalValuesJson);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the duration that was ended
     * @throws NetToolKitException
     */
    public Duration send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new Duration(response.getFirstResult());
    }
}


