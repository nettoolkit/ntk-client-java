package com.nettoolkit.gatekeeper;

import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.GetRequest;

/**
 * Request to count visits for a certain policy. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visits#count-visits-for-policy"><code>GET /v1/gatekeeper/visits/count-for-policy</code></a>. Policy ID and IP address are required.
 * <p>Sample:
 * <p><blockquote><pre>
 * int numVisits = gatekeeperClient.newCountVisitsForPolicyRequest()
 *     .policyId("14f4a740-e3ef-47a4-8315-aa4316c2e7b5")
 *     .ip("1.2.3.4")
 *     .send();
 * </pre></blockquote>
 */
public class CountVisitsForPolicyRequest extends GetRequest {
    public CountVisitsForPolicyRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    protected String getPath() { return "/v1/gatekeeper/visits/count-for-policy"; }

    /**
     * Sets the policy ID. The visit count as seen by this policy will be returned.
     * <em>required</em>
     *
     * @param policyId the policy ID
     * @return this
     */
    public CountVisitsForPolicyRequest policyId(UUID policyId) {
        getParameters().put("policy_id", policyId);
        return this;
    }

    /**
     * Sets the policy ID.
     *
     * @param strPolicyId the policy ID as a string
     * @return this
     * @throws IllegalArgumentException if the string is not a valid UUID
     * @see CountVisitsForPolicyRequest#policyId(UUID)
     */
    public CountVisitsForPolicyRequest policyId(String strPolicyId) {
        return policyId(UUID.fromString(strPolicyId));
    }

    /**
     * Sets the visit IP address. Expects dot notation for IPv4 and hexadecimal for IPv6.
     * <em>required</em>
     *
     * @param strIpAddress the IP address in dot or hexadecimal format
     * @return this
     */
    public CountVisitsForPolicyRequest ip(String strIpAddress) {
        getParameters().put("ip", strIpAddress);
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

