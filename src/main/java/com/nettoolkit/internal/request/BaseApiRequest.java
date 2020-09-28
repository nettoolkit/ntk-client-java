package com.nettoolkit.internal.request;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.Parameters;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.http.HttpMethod;
import com.nettoolkit.internal.http.HttpContentType;

public abstract class BaseApiRequest {
    private Parameters mParams;
    private NetToolKitClient mClient;

    public BaseApiRequest(NetToolKitClient client) {
        mClient = client;
        mParams = new Parameters();
    }

    // Stubs
    protected abstract HttpMethod getHttpMethod();
    // Ex. "/v1/gatekeeper/visits/authorization"
    protected abstract String getPath();
    protected abstract String serializeParameters() throws ParsingException;
    
    public Parameters getParameters() { return mParams; }

    public NetToolKitClient getClient() { return mClient; }

    public HttpRequest toHttpRequest() throws ParsingException {
        HttpRequest.Builder builder = newHttpRequestBuilder();

        String strSerializedParams = serializeParameters();

        // Create URL, appending query string if necessary.
        String strUrl = getClient().getBaseUrl() + getPath();
        if (getHttpMethod() == HttpMethod.GET || getHttpMethod() == HttpMethod.DELETE) {
            strUrl += "?" + strSerializedParams;
        }
        builder.uri(URI.create(strUrl));

        // Create the body if necessary.
        HttpRequest.BodyPublisher body;
        if (getHttpMethod() == HttpMethod.POST || getHttpMethod() == HttpMethod.PUT) {
            body = HttpRequest.BodyPublishers.ofString(strSerializedParams);
        } else {
            body = HttpRequest.BodyPublishers.noBody();
        }

        // Sett HTTP method + body.
        builder.method(getHttpMethod().name(), body);

        // Add API key to the header.
        builder.setHeader("X-NTK-KEY", getClient().getApiKey());

        // Add Accept header so server knows we expect a JSON response.
        builder.setHeader("Accept", HttpContentType.JSON.getValue());

        // Add request timeout.
        builder.timeout(Duration.ofMillis(Long.valueOf(getClient().getTimeout())));

        return builder.build();
    }

    protected HttpRequest.Builder newHttpRequestBuilder() {
        return HttpRequest.newBuilder();
    }
}

