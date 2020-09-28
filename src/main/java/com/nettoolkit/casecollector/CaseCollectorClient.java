package com.nettoolkit.casecollector;

import java.io.StringWriter;
import java.io.PrintWriter;

import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;

public class CaseCollectorClient extends NetToolKitClient {
    // Instantiation
    public CaseCollectorClient(String strApiKey) {
        super(strApiKey);
    }

    public CaseCollectorClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }

    // API 
    public void createCase(Integer iProjectId, String strJsonData)
            throws NetToolKitException { 
    	createCase(iProjectId, strJsonData, 1);
    }

    public void createCase(Integer iProjectId, String strJsonData, Integer iStatus)
            throws NetToolKitException {
        JSONObject jsonData;
        try {
            jsonData = new JSONObject(strJsonData);
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, strJsonData);
        }
        JSONObject jsonParameters;
        try {
            jsonParameters = new JSONObject();
            jsonParameters.put("project_id", iProjectId);
            jsonParameters.put("data", jsonData);
            jsonParameters.put("status", iStatus);
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, null);
        }
        String strResponse = sendPost("/v1/case/cases", jsonParameters);
        parseResponse(strResponse); // Checks for errors
    }

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
