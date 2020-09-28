package com.nettoolkit.internal.request;

import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.request.BaseApiRequest;
import com.nettoolkit.internal.http.HttpMethod;

public abstract class GetRequest extends BaseApiRequest {
    public GetRequest(NetToolKitClient client) {
        super(client);
    }

    @Override
    protected HttpMethod getHttpMethod() { return HttpMethod.GET; }

    @Override
    protected String serializeParameters() throws ParsingException {
        return getParameters().toWwwFormUrlencoded();
    }
}

