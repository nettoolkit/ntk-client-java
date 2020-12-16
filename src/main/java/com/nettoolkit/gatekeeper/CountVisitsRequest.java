package com.nettoolkit.gatekeeper;

import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.Instant;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.GetRequest;

/**
 * Request to count visits. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visits#count-visits"><code>GET /v1/gatekeeper/visits/count</code></a>.
 * <strong>EXPERIMENTAL</strong>: This route is not guaranteed to have stable performance yet. The API is also subject to change, based on supportable queries.
 * <p>Sample 1:
 * <p><blockquote><pre>
 * // Count number of visits from IP address 1.2.3.4 that were denied.
 * int numVisits = gatekeeperClient.newCountVisitsRequest()
 *     .ip("1.2.3.4")
 *     .authorization("deny")
 *     .send();
 * </pre></blockquote>
 * <p>Sample 2:
 * <p><blockquote><pre>
 * // Count number of visits from Google crawler in the past 30 days.
 * int numVisits = gatekeeperClient.newCountVisitsRequest()
 *     .tag("Google crawler")
 *     .earliest(OffsetDateTime.now().minusDays(30))
 *     .send();
 * </pre></blockquote>
 */
public class CountVisitsRequest extends GetRequest {
    public CountVisitsRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    protected String getPath() { return "/v1/gatekeeper/visits/count"; }

    /**
     * Sets the IP address to query. Expects dot notation for IPv4 and hexadecimal for IPv6.
     *
     * @param strIpAddress the IP address in dot or hexadecimal format
     * @return this
     */
    public CountVisitsRequest ip(String strIpAddress) {
        getParameters().put("ip", strIpAddress);
        return this;
    }

    /**
     * Sets the user ID to query.
     *
     * @param lUserId the user ID
     * @return this
     */ 
    public CountVisitsRequest userId(long lUserId) {
        getParameters().put("user_id", lUserId);
        return this;
    }

    /**
     * Sets the tag to query. See <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/supported-tags">the supported tags page</a> for available options.
     *
     * @param strTag the tag name
     * @return this
     */
    public CountVisitsRequest tag(String strTag) {
        getParameters().put("tag", strTag);
        return this;
    }

    /**
     * Sets the country to query. Expects either a ISO 3166 alpha-2 country code (e.g. "US", "JP") or a full country name (e.g. "United States", "Japan"). For a full list of supported values, see the <a href="https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry">IANA Language Subtag Registry</a> and search for "Type: region".
     *
     * @param strCountry the country name or country code
     * @return this
     */
    public CountVisitsRequest country(String strCountry) {
        getParameters().put("country", strCountry);
        return this;
    }

    /**
     * Sets the page prefix to query. The page is everything in the URL from the first "/" onward (e.g. in "https://www.example.com/index.html?a=b", the page would be "/index.html?a=b"). The visit's page must start with the page prefix to be counted.
     *
     * @param strPagePrefix the page prefix
     * @return this
     */
    public CountVisitsRequest pagePrefix(String strPagePrefix) {
        getParameters().put("page_prefix", strPagePrefix);
        return this;
    }

    /**
     * Sets the authorization to query. Valid authorizations include "allow", "deny", "captcha", and any custom authorizations.
     * <em>Only applicable to visits created by the visit authorization route.</em>
     *
     * @param strAuthorization the authorization
     * @return this
     */
    public CountVisitsRequest authorization(String strAuthorization) {
        getParameters().put("authorization", strAuthorization);
        return this;
    }

    /**
     * Sets the earliest time to query. Visits before this time will not be counted.
     *
     * @param timestamp the earliest timestamp
     * @return this
     */
    public CountVisitsRequest earliest(OffsetDateTime timestamp) {
        getParameters().put("earliest", timestamp.toInstant().toEpochMilli());
        return this;
    }

    /**
     * Sets the earliest time to query. Visits before this time will not be counted.
     *
     * @param timestamp the earliest timestamp
     * @return this
     */
    public CountVisitsRequest earliest(Instant timestamp) {
        getParameters().put("earliest", timestamp.toEpochMilli());
        return this;
    }

    /**
     * Sets the earliest time to query. Visits before this time will not be counted.
     *
     * @param lTimestamp the earliest timestamp in epoch milliseconds
     * @return this
     */
    public CountVisitsRequest earliest(long lTimestamp) {
        getParameters().put("earliest", lTimestamp);
        return this;
    }

    /**
     * Sets the latest time to query. Visits after this time will not be counted.
     *
     * @param timestamp the latest timestamp
     * @return this
     */
    public CountVisitsRequest latest(OffsetDateTime timestamp) {
        getParameters().put("latest", timestamp.toInstant().toEpochMilli());
        return this;
    }

    /**
     * Sets the latest time to query. Visits after this time will not be counted.
     *
     * @param timestamp the latest timestamp
     * @return this
     */
    public CountVisitsRequest latest(Instant timestamp) {
        getParameters().put("latest", timestamp.toEpochMilli());
        return this;
    }

    /**
     * Sets the latest time to query. Visits after this time will not be counted.
     *
     * @param lTimestamp the latest timestamp in epoch milliseconds
     * @return this
     */
    public CountVisitsRequest latest(long lTimestamp) {
        getParameters().put("latest", lTimestamp);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the visit count for IP address as seen by policy
     * @throws NetToolKitException
     */
    public int send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        try {
            return response.getFirstResult().getInt("count");
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, response.getFirstResult());
        }
    }
}

