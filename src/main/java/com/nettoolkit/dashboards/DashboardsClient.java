package com.nettoolkit.dashboards;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.StatusCode;
import com.nettoolkit.internal.Parameters;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

import java.io.StringWriter;
import java.io.PrintWriter;

public class DashboardsClient extends NetToolKitClient {

    public DashboardsClient(String strApiKey) {
        super(strApiKey);
    }
    public DashboardsClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }
    public Channel createChannel(String strChannelName, String strType)
            throws NetToolKitException {
        return createChannel(strChannelName, null, strType, null);
    }
    public Channel createChannel(String strChannelName, String strDisplayName,
                                 String strType, Double dDenominator)
            throws NetToolKitException{
        JSONObject jsonParameters;
        try {
            jsonParameters = new JSONObject();
            jsonParameters.put("channel_name", strChannelName);
            jsonParameters.put("display_name", strDisplayName);
            jsonParameters.put("type", strType);
            JSONObject jsonPreferences = new JSONObject();
            jsonParameters.put("preferences", jsonPreferences);
            jsonParameters.put("denominator", dDenominator);
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, null);
        }
        String strResponse = sendPost("/v1/dashboards/channels", jsonParameters);
        // parseResponse(strResponse); // Checks for errors
        return new Channel(getFirstResult(strResponse));
    }
    public ChannelDatum createChannelData(String strChannelName, Double dValue,
                                          String strAdditionalValues)
            throws NetToolKitException {
        return createChannelData(strChannelName, dValue, strAdditionalValues, "simple"); 
    }
    public ChannelDatum createChannelData(String strChannelName, Double dValue,
                                          String strAdditionalValues, String strType)
            throws NetToolKitException {
        return createChannelData(strChannelName, dValue, strAdditionalValues, strType, (Double) null); 
    }
    @Deprecated
    public ChannelDatum createChannelData(String strChannelName, Double dValue,
                                          String strAdditionalValues, String strType,
                                          String strUnit)
            throws NetToolKitException {
        return createChannelData(strChannelName, dValue, strAdditionalValues, strType); 
    }
    public ChannelDatum createChannelData(String strChannelName,      Double dValue,
                                          String strAdditionalValues, String strType,
                                          Double dDenominator)
            throws NetToolKitException {
        return createChannelData(strChannelName, dValue, strAdditionalValues, strType, dDenominator, ""); 
    }
    public ChannelDatum createChannelData(String strChannelName,      Double dValue,
                                          String strAdditionalValues, String strType,
                                          Double dDenominator,        String strDisplayName)
            throws NetToolKitException {
        JSONObject jsonParameters;
        try {
            jsonParameters = new JSONObject();
            jsonParameters.put("name", strChannelName);
            jsonParameters.put("value", dValue);
            jsonParameters.put("additional_values", strAdditionalValues);
            jsonParameters.put("type", strType);
            jsonParameters.put("denominator", dDenominator);
            jsonParameters.put("display_name", strDisplayName);
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, null);
        }
        String strResponse = sendPost("/v1/dashboards/channel-data", jsonParameters);
        // parseResponse(strResponse); // Checks for errors
        return new ChannelDatum(getFirstResult(strResponse));
    }
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
