package com.nettoolkit.internal.request;

import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.request.BaseApiRequest;
import com.nettoolkit.internal.http.HttpMethod;

public abstract class DeleteRequest extends BaseApiRequest {
    public DeleteRequest(NetToolKitClient client) {
        super(client);
    }

    @Override
    protected HttpMethod getHttpMethod() { return HttpMethod.DELETE; }

    @Override
    protected String serializeParameters() throws ParsingException {
        return getParameters().toWwwFormUrlencoded();
    }
}

