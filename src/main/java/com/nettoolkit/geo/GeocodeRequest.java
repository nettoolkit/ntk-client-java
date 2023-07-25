package com.nettoolkit.geo;

import java.util.ArrayList;
import java.util.List;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.GetRequest;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

/**
 * Request to geocode an address. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/geo/geocoding#geocode"><code>GET /v1/geo/geocodes</code></a>. At least one of address, city/state, and zip are required.
 * <p>Sample:
 * <p><blockquote><pre>
 * List&lt;Geocode&gt; geocodes = geoClient.newGeocodeRequest()
 *     .address("123 Hello Street, A City, 98765")
 *     .send();
 * </pre></blockquote>
 */
public class GeocodeRequest extends GetRequest {
    public GeocodeRequest(GeoClient client) {
        super(client);
    }

    @Override
    protected String getPath() { return "/v1/geo/geocodes"; }

    /**
     * Sets the unstructured address for the query.
     *
     * @param strUnstructuredQuery the unstructured address string
     * @return this
     */
    public GeocodeRequest address(String strUnstructuredQuery) {
        getParameters().put("address", strUnstructuredQuery);
        return this;
    }

    /**
     * Sets the city for the query.
     *
     * @param strCityName the name of the city
     * @return this
     */
    public GeocodeRequest city(String strCityName) {
        getParameters().put("city", strCityName);
        return this;
    }

    /**
     * Sets the state for the query.
     *
     * @param strStateName the name of the state
     * @return this
     */
    public GeocodeRequest state(String strStateName) {
        getParameters().put("state", strStateName);
        return this;
    }

    /**
     * Sets the street (and house number) for the query.
     *
     * @param strStreetAddress the street, including house number if available
     * @return this
     */
    public GeocodeRequest street(String strStreetAddress) {
        getParameters().put("street", strStreetAddress);
        return this;
    }

    /**
     * Sets the zip code for the query.
     *
     * @param strZipCode
     * @return this
     */
    public GeocodeRequest zip(String strZipCode) {
        getParameters().put("zip", strZipCode);
        return this;
    }

    /**
     * Sets the country for the query.
     *
     * @param strCountryCode Alpha-2 country code
     * @return this
     */
    public GeocodeRequest countryCode(String strCountryCode) {
        getParameters().put("country_code", strCountryCode);
        return this;
    }

    /**
     * Only return results from this provider.
     *
     * @param provider
     * @return this
     */
    public GeocodeRequest provider(Provider provider) {
        getParameters().put("provider", provider.asStringValue());
        return this;
    }

    /**
     * Sends the request.
     *
     * @return a list of geocode objects
     * @throws NetToolKitException
     */
    public List<Geocode> send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        JSONArray jsonGeocodes = response.getResults();
        List<Geocode> listGeocodes = new ArrayList<>();
        for (int i = 0; i < jsonGeocodes.length(); i++) {
            JSONObject jsonGeocode;
            try {
                jsonGeocode = jsonGeocodes.getJSONObject(i);
            } catch (JSONException jsone) {
                throw new ParsingException(jsone, jsonGeocodes.opt(i));
            }
            try {
                listGeocodes.add(Geocode.fromJson(jsonGeocode));
            } catch (JSONException jsone) {
                throw new ParsingException(jsone, jsonGeocode);
            }
        }
        return listGeocodes;
    }
}

