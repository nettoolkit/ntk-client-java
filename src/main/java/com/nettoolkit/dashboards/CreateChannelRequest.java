package com.nettoolkit.dashboards;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateChannelRequest extends PostRequest {
    public CreateChannelRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/dashboards/channels"; }

    /**
     * Sets the channel name.
     * <em>required</em>
     *
     * @param strName the channel name
     * @return this
     */
    public CreateChannelRequest name(String strName) {
        getParameters().put("name", strName);
        return this;
    }

    /**
     * Sets the channel display name.
     *
     * @param strDisplayName the channel name
     * @return this
     */
    public CreateChannelRequest displayName(String strDisplayName) {
        getParameters().put("display_name", strDisplayName);
        return this;
    }

    /**
     * Sets the channel type to SIMPLE.
     *
     * @return this
     */
    public CreateChannelRequest typeSimple() {
        getParameters().put("channel_type", ChannelType.SIMPLE.name());
        return this;
    }

    /**
     * Sets the channel type to SSL.
     *
     * @param strUrl the URL the query the SSL certificate from
     * @return this
     */
    public CreateChannelRequest typeSsl(String strUrl) throws ParsingException {
        getParameters().put("channel_type", ChannelType.SSL.name());
        JSONObject jsonPreferences = new JSONObject();
        try {
            jsonPreferences.put("url", strUrl);
        } catch (Exception e) {
            throw new ParsingException(e, strUrl);
        }
        getParameters().put("preferences", jsonPreferences);
        return this;
    }

    /**
     * Sets the channel type to PING.
     *
     * @param strHost the hostname or IP address to ping
     * @param iTimeNum time number for ping frequency
     * @param timeUnit time unit for ping frequency
     * @return this
     */
    public CreateChannelRequest typePing(String strHost, int iTimeNum, TimeUnit timeUnit)
            throws ParsingException {
        getParameters().put("channel_type", ChannelType.PING.name());
        JSONObject jsonPreferences = new JSONObject();
        try {
            jsonPreferences.put("domain", strHost);
        } catch (Exception e) {
            throw new ParsingException(e, strHost);
        }
        try {
            jsonPreferences.put("time", iTimeNum);
        } catch (Exception e) {
            throw new ParsingException(e, iTimeNum);
        }
        try {
            jsonPreferences.put("time_unit", timeUnit);
        } catch (Exception e) {
            throw new ParsingException(e, timeUnit);
        }
        getParameters().put("preferences", jsonPreferences);
        return this;
    }

    /**
     * Sets the channel type to HTTP.
     *
     * @param strHttpMethod the HTTP method for requests
     * @param strUrl the URL to contact
     * @param listHttpHeaders an optional list of HTTP headers to send; set to null if unnecessary
     * @param strHttpBody optional HTTP body to send; set to null if unnecessary
     * @param iTimeNum time number for request frequency
     * @param timeUnit time unit for request frequency
     * @return this
     */
    public CreateChannelRequest typeHttp(
        String strHttpMethod,
        String strUrl,
        List<HttpHeader> listHttpHeaders,
        String strHttpBody,
        int iTimeNum,
        TimeUnit timeUnit
    ) throws ParsingException {
        getParameters().put("channel_type", ChannelType.HTTP.name());
        JSONObject jsonPreferences = new JSONObject();
        try {
            jsonPreferences.put("http_method", strHttpMethod);
        } catch (Exception e) {
            throw new ParsingException(e, strHttpMethod);
        }
        try {
            jsonPreferences.put("url", strUrl);
        } catch (Exception e) {
            throw new ParsingException(e, strUrl);
        }
        try {
            if (listHttpHeaders != null) {
                JSONArray jsonHttpHeaders = new JSONArray();
                for (HttpHeader header : listHttpHeaders) {
                    jsonHttpHeaders.put(header.toJson());
                }
            }
        } catch (Exception e) {
            throw new ParsingException(e, listHttpHeaders);
        }
        try {
            jsonPreferences.put("http_body", strHttpBody);
        } catch (Exception e) {
            throw new ParsingException(e, strHttpBody);
        }
        try {
            jsonPreferences.put("time", iTimeNum);
        } catch (Exception e) {
            throw new ParsingException(e, iTimeNum);
        }
        try {
            jsonPreferences.put("time_unit", timeUnit);
        } catch (Exception e) {
            throw new ParsingException(e, timeUnit);
        }
        getParameters().put("preferences", jsonPreferences);
        return this;
    }

    /**
     * Sets the channel type to DURATION.
     *
     * @param strDefaultBarColor the default color for duration bars in hex format
     * @return this
     */
    public CreateChannelRequest typeDuration(String strDefaultBarColor) throws ParsingException {
        getParameters().put("channel_type", ChannelType.DURATION.name());
        JSONObject jsonPreferences = new JSONObject();
        try {
            jsonPreferences.put("default_color", strDefaultBarColor);
        } catch (Exception e) {
            throw new ParsingException(e, strDefaultBarColor);
        }
        getParameters().put("preferences", jsonPreferences);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return a channel object
     * @throws NetToolKitException
     */
    public Channel send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new Channel(response.getFirstResult());
    }
}

