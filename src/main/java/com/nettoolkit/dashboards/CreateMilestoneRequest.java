package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateMilestoneRequest extends PostRequest {
    public CreateMilestoneRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/dashboards/durations"; }

    /**
     * Sets the duration for this milestone.
     * <em>required unless channel ID/name provided</em>
     *
     * @param durationId the duration ID
     * @return this
     */
    public CreateMilestoneRequest durationId(UUID durationId) {
        getParameters().put("duration_id", durationId);
        return this;
    }

    /**
     * Sets the duration for this milestone by channel name.
     *
     * @param strChannelName the channel name
     * @return this
     */
    public CreateMilestoneRequest channelName(String strChannelName) {
        getParameters().put("channel_name", strChannelName);
        return this;
    }

    /**
     * Sets the duration for this milestone by channel ID.
     *
     * @param channelId the channel ID
     * @return this
     */
    public CreateMilestoneRequest channelId(UUID channelId) {
        getParameters().put("channel_id", channelId);
        return this;
    }

    /**
     * Sets the milestone name.
     * <em>requires</em>
     *
     * @param strName the milestone name
     * @return this
     */
    public CreateMilestoneRequest name(String strName) {
        getParameters().put("name", strName);
        return this;
    }

    /**
     * Sets the milestone time. By default, uses current time.
     *
     * @param timestamp the milestone time
     * @return this
     */
    public CreateMilestoneRequest timestamp(OffsetDateTime timestamp) {
        getParameters().put("timestamp", timestamp);
        return this;
    }

    /**
     * Sets the milestone failure status. If true, this indicates that the previous time segment
     * ended in failure.
     *
     * @param bIsFailure the failure status
     * @return this
     */
    public CreateMilestoneRequest failure(boolean bIsFailure) {
        getParameters().put("failure", bIsFailure);
        return this;
    }

    /**
     * Sets the milestone additional values JSON.
     *
     * @param jsonAdditionalValues the additional values JSON
     * @return this
     */
    public CreateMilestoneRequest additionalValues(JSONObject jsonAdditionalValues) {
        getParameters().put("additional_values", jsonAdditionalValues);
        return this;
    }

    /**
     * Sets the milestone additional values JSON string. This is an escape hatch for situations 
     * where it's inconvenient to use the provided {@link com.nettoolkit.json.JSONObject}. If you use
     * this function, you must ensure that the given string represents a valid JSON object.
     *
     * @param strAdditionalValuesJson the additional values JSON string
     * @return this
     */
    public CreateMilestoneRequest additionalValues(String strAdditionalValuesJson) {
        getParameters().put("additional_values", strAdditionalValuesJson);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the duration this milestone was added to
     * @throws NetToolKitException
     */
    public Duration send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new Duration(response.getFirstResult());
    }
}

