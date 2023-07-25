package com.nettoolkit.geo;

import com.nettoolkit.internal.NetToolKitClient;

public class GeoClient extends NetToolKitClient {
    /**
     * Constructs a new GeoClient which uses the given API key for requests.
     *
     * @param strApiKey The <a href="https://www.nettoolkit.com/docs/overview#authentication">NetToolKit API key</a> used to authenticate requests. All requests made by GeoClient will use this API key.
     */
    public GeoClient(String strApiKey) {
        super(strApiKey);
    }

    public GeoClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }

    /**
     * Creates a new request to authorize a visit. Call {@link com.nettoolkit.gatekeeper.AuthorizeVisitRequest#send} to execute.
     *
     * @return a new visit authorization request object
     */
    public GeocodeRequest newGeocodeRequest() {
        return new GeocodeRequest(this);
    }
}

