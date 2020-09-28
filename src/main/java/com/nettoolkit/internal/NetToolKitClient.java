package com.nettoolkit.internal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import java.net.URI;
import javax.net.ssl.SSLContext;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

import com.nettoolkit.exception.ApiConnectionException;
import com.nettoolkit.exception.ApiException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.BaseApiRequest;
import com.nettoolkit.internal.http.HttpMethod;
import com.nettoolkit.internal.http.HttpContentType;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

public abstract class NetToolKitClient {
    protected static final String BASE_URL = "api.nettoolkit.com";
    protected static final String BASE_URL_DEV = "devapi.nettoolkit.com";
    // Member variables
    protected String mstrApiKey;
    protected boolean mbUseHttps = true;
    protected boolean mbDevMode = false;
    protected int miDevPort = -1;
    protected int miTimeout = 3000;
    protected HttpClient mHttpClient;
    // Instantiation
    private NetToolKitClient() {}
    public NetToolKitClient(String strApiKey) {
        this(strApiKey, true);
    }
    public NetToolKitClient(String strApiKey, boolean bUseHttps) {
        mstrApiKey = strApiKey;
        mbUseHttps = bUseHttps;
        // TLSv1.3 is not well supported in java right now; turn it off in the mean time?
        // System.setProperty("jdk.tls.client.protocols", "TLSv1.1,TLSv1.2");
        // System.setProperty("https.protocols", "TLSv1.1,TLSv1.2");
        // mHttpClient = HttpClient.newHttpClient();
        try {
            mHttpClient = HttpClient.newBuilder()
                .sslContext(SSLContext.getInstance("TLSv1.2"))
                .build();
        } catch (NoSuchAlgorithmException nae) {
            throw new IllegalStateException("HTTP client failed to initialize", nae);
        }
    }
    // Getters/setters
    public String getApiKey() { return mstrApiKey; }
    public void setApiKey(String strApiKey) { mstrApiKey = strApiKey; }
    public boolean getUseHttps() { return mbUseHttps; }
    public void setUseHttps(boolean bUseHttps) { mbUseHttps = bUseHttps; }
    public void setDevMode() { mbDevMode = true; }
    public void setDevMode(int iPort) {
        mbDevMode = true;
        miDevPort = iPort;
    }
    public int getTimeout() { return miTimeout; }
    public void setTimeout(int iTimeout) { miTimeout = iTimeout; }

    public String getBaseUrl() {
        StringBuilder sb = new StringBuilder();
        if (getUseHttps()) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }
        sb.append(getHostname());
        return sb.toString();
    }

    protected String getHostname() {
        return "api.nettoolkit.com";
    }

    public ApiResponse send(BaseApiRequest request)
            throws ParsingException, ApiConnectionException, ApiException {
        HttpRequest httpRequest = request.toHttpRequest();

        HttpResponse<String> httpResponse;
        try {
            httpResponse = mHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiConnectionException(e);
        }

        return new ApiResponse(httpResponse);
    }

    // ========
    // = POST =
    // ========
    protected String sendPost(String strPath, String strBody)
            throws ApiConnectionException {
        return sendPost(strPath, strBody, HttpContentType.WWW_FORM_URLENCODED);
    }
    protected String sendPost(String strPath, JSONObject jsonBody)
            throws ApiConnectionException {
        return sendPost(strPath, jsonBody.toString(), HttpContentType.JSON);
    }
    protected String sendPost(String strPath, String strBody, HttpContentType contentType)
            throws ApiConnectionException {
        return sendHttpRequest(HttpMethod.POST, strPath, null, strBody, contentType);
    }
    // =======
    // = GET =
    // =======
    protected String sendGet(String strPath)
            throws ApiConnectionException {
        return sendGet(strPath, null);
    }
    protected String sendGet(String strPath, String strQuery)
            throws ApiConnectionException {
        return sendHttpRequest(HttpMethod.GET, strPath, strQuery, null, null);
    }
    // =======
    // = PUT =
    // =======
    protected String sendPut(String strPath, String strBody)
            throws ApiConnectionException {
        return sendPut(strPath, strBody, HttpContentType.WWW_FORM_URLENCODED);
    }
    protected String sendPut(String strPath, JSONObject jsonBody)
            throws ApiConnectionException {
        return sendPut(strPath, jsonBody.toString(), HttpContentType.JSON);
    }
    protected String sendPut(String strPath, String strBody, HttpContentType contentType)
            throws ApiConnectionException {
        return sendHttpRequest(HttpMethod.PUT, strPath, null, strBody, contentType);
    }
    // ==========
    // = DELETE =
    // ==========
    protected String sendDelete(String strPath)
            throws ApiConnectionException {
        return sendDelete(strPath, null);
    }
    protected String sendDelete(String strPath, String strQuery)
            throws ApiConnectionException {
        return sendHttpRequest(HttpMethod.DELETE, strPath, strQuery, null, null);
    }
    // ================
    // = HTTP request =
    // ================
    protected String sendHttpRequest(HttpMethod method, String strPath,
                                     String strQueryString, String strBody,
                                     HttpContentType contentType)
            throws ApiConnectionException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        // Set request URI, adding query string if present
        String strUrl = buildUrl(strPath);
        if (strQueryString != null && strQueryString.length() > 0)
            strUrl += "?" + strQueryString;
        builder.uri(URI.create(strUrl));
        // Set request body if present
        HttpRequest.BodyPublisher body;
        if (strBody != null && strBody.length() > 0)
            body = HttpRequest.BodyPublishers.ofString(strBody);
        else
            body = HttpRequest.BodyPublishers.noBody();
        // Set method
        builder.method(method.name(), body);
        // Set headers
        builder.setHeader("X-NTK-KEY", mstrApiKey);
        builder.setHeader("Accept", HttpContentType.JSON.getValue());
        if (contentType != null)
            builder.setHeader("Content-Type", contentType.getValue());
        builder.timeout(Duration.ofMillis(Long.valueOf(miTimeout)));
        // Build and send request
        HttpRequest httpRequest = builder.build();
        HttpResponse<String> httpResponse;
        try {
            httpResponse = mHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiConnectionException(e);
        }
        return httpResponse.body();
    }

    protected String buildUrl(String strPath) {
        StringBuilder sb = new StringBuilder();
        if (mbUseHttps) sb.append("https://");
        else sb.append("http://");
        // "api" or "devapi"
        if (mbDevMode) sb.append(BASE_URL_DEV);
        else sb.append(BASE_URL);
        // If port is not 80
        if (miDevPort > -1)
            sb.append(":").append(miDevPort);
        sb.append(strPath);
        return sb.toString();
    }
    // ====================
    // = Response parsing =
    // ====================
    protected JSONObject parseResponse(String strResponse)
            throws ParsingException, ApiException {
        if (strResponse == null || strResponse.length() < 1)
            throw new ParsingException("Got empty response", strResponse);
        try {
            JSONObject jsonResponse = new JSONObject(strResponse);
            int iCode = jsonResponse.getInt("code");
            if (iCode >= 2000) {
                String strMessage = jsonResponse.optString("message");
                throw new ApiException(iCode, strMessage);
            }
            return jsonResponse;
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, strResponse);
        }
    }

    protected JSONArray getResults(String strResponse) 
            throws ParsingException, ApiException {
        JSONObject jsonResponse = parseResponse(strResponse);
        JSONArray jsonResults = jsonResponse.optJSONArray("results");
        if (jsonResults == null) {
            throw new ParsingException("Missing 'results' from response", jsonResponse);
        }
        return jsonResults;
    }

    protected JSONObject getFirstResult(String strResponse)
            throws ParsingException, ApiException {
        JSONArray jsonResults = getResults(strResponse);
        if (jsonResults.length() < 1) {
            throw new ParsingException("No results", jsonResults);
        }
        return jsonResults.optJSONObject(0);
    }
}

