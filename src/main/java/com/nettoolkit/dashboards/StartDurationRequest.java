package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class StartDurationRequest extends PostRequest {
    public StartDurationRequest(DashboardsClient client) {
        super(client);
        getParameters().put("name", "start");
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/dashboards/durations"; }

    /**
     * Sets the channel for this duration by name.
     * <em>required unless channel ID is given</em>
     *
     * @param strChannelName the channel name
     * @return this
     */
    public StartDurationRequest channelName(String strChannelName) {
        getParameters().put("channel_name", strChannelName);
        return this;
    }

    /**
     * Sets the channel for this duration by ID.
     * <em>required unless channel name is given</em>
     *
     * @param channelId the channel ID
     * @return this
     */
    public StartDurationRequest channelId(UUID channelId) {
        getParameters().put("channel_id", channelId);
        return this;
    }

    /**
     * Sets the duration start time. By default, uses current time.
     *
     * @param timestamp the start time
     * @return this
     */
    public StartDurationRequest timestamp(OffsetDateTime timestamp) {
        getParameters().put("timestamp", timestamp);
        return this;
    }

    /**
     * Sets the duration start additional values JSON.
     *
     * @param jsonAdditionalValues the additional values JSON
     * @return this
     */
    public StartDurationRequest additionalValues(JSONObject jsonAdditionalValues) {
        getParameters().put("additional_values", jsonAdditionalValues);
        return this;
    }

    /**
     * Sets the duration start additional values JSON string. This is an escape hatch for situations 
     * where it's inconvenient to use the provided {@link com.nettoolkit.json.JSONObject}. If you use
     * this function, you must ensure that the given string represents a valid JSON object.
     *
     * @param strAdditionalValuesJson the additional values JSON string
     * @return this
     */
    public StartDurationRequest additionalValues(String strAdditionalValuesJson) {
        getParameters().put("additional_values", strAdditionalValuesJson);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return a duration object
     * @throws NetToolKitException
     */
    public Duration send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new Duration(response.getFirstResult());
    }
}

