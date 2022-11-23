package com.nettoolkit.dashboards;

import java.util.UUID;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateChannelDatumRequest extends PostRequest {
    public CreateChannelDatumRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/dashboards/channel-data"; }

    /**
     * Sets the channel for this datum by name.
     * <em>required unless channel ID is given</em>
     *
     * @param strChannelName the channel name
     * @return this
     */
    public CreateChannelDatumRequest channelName(String strChannelName) {
        getParameters().put("channel_name", strChannelName);
        return this;
    }

    /**
     * Sets the channel for this datum by ID.
     * <em>required unless channel name is given</em>
     *
     * @param channelId the channel ID
     * @return this
     */
    public CreateChannelDatumRequest channelId(UUID channelId) {
        getParameters().put("channel_id", channelId);
        return this;
    }

    /**
     * Sets the datum value.
     *
     * @param dValue the numeric value
     * @return this
     */
    public CreateChannelDatumRequest value(Double dValue) {
        getParameters().put("value", dValue);
        return this;
    }

    /**
     * Sets the datum additional values JSON.
     *
     * @param jsonAdditionalValues the additional values JSON
     * @return this
     */
    public CreateChannelDatumRequest additionalValues(JSONObject jsonAdditionalValues) {
        getParameters().put("additional_values", jsonAdditionalValues);
        return this;
    }

    /**
     * Sets the datum additional values JSON string. This is an escape hatch for situations where
     * it's inconvenient to use the provided {@link com.nettoolkit.json.JSONObject}. If you use
     * this function, you must ensure that the given string represents a valid JSON object.
     *
     * @param strAdditionalValuesJson the additional values JSON string
     * @return this
     */
    public CreateChannelDatumRequest additionalValues(String strAdditionalValuesJson) {
        getParameters().put("additional_values", strAdditionalValuesJson);
        return this;
    }

    /**
     * Sets the datum text blob. This can be useful for sending arbitrary text data.
     *
     * @param strTextBlob the text blob
     * @return this
     */
    public CreateChannelDatumRequest textBlob(String strTextBlob) {
        getParameters().put("text_blob", strTextBlob);
        return this;
    }

    /**
     * Sets the datum text blob to a stack trace.
     *
     * @param throwable a throwable containing the stack trace to save
     * @return this
     */
    public CreateChannelDatumRequest stackTrace(Throwable throwable) {
        getParameters().put("text_blob", getStackTrace(throwable));
        return this;
    }

    private static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * Sends the request.
     *
     * @return a channel datum object
     * @throws NetToolKitException
     */
    public ChannelDatum send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new ChannelDatum(response.getFirstResult());
    }
}

