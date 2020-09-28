package com.nettoolkit.internal.request;

import java.net.http.HttpRequest;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.request.BaseApiRequest;
import com.nettoolkit.internal.http.HttpMethod;
import com.nettoolkit.internal.http.HttpContentType;

public abstract class PostRequest extends BaseApiRequest {
    public PostRequest(NetToolKitClient client) {
        super(client);
    }

    // Stubs
    protected abstract HttpContentType getContentType();

    @Override
    protected HttpMethod getHttpMethod() { return HttpMethod.POST; }

    @Override
    protected String serializeParameters() throws ParsingException {
        switch (getContentType()) {
            case JSON:
                return getParameters().toJson().toString();
            case WWW_FORM_URLENCODED:
                return getParameters().toWwwFormUrlencoded();
            default:
                throw new IllegalStateException("Unrecognized content type: " + getContentType());
        }
    }

    @Override
    protected HttpRequest.Builder newHttpRequestBuilder() {
        return super.newHttpRequestBuilder()
            .setHeader("Content-Type", getContentType().getValue());
    }
}

