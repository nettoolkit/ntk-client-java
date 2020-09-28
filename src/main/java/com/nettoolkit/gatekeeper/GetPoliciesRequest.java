package com.nettoolkit.gatekeeper;

import java.util.List;
import java.util.ArrayList;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.GetRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.gatekeeper.Policy;

/**
 * Request to retrieve a list of policies. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/policies#list-policies"><code>GET /v1/gatekeeper/policies</code></a>.
 * <p>Sample:
 * <p><blockquote><pre>
 * List&lt;Policy&gt; policies = gatekeeperClient.newGetPoliciesRequest().send();
 * </pre></blockquote>
 */
public class GetPoliciesRequest extends GetRequest {
    public GetPoliciesRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public String getPath() { return "/v1/gatekeeper/policies"; }

    /**
     * Sends get policies request and parses response. Policies are ordered by priority.
     *
     * @return a list of policy objects
     * @throws NetToolKitException
     */
    public List<Policy> send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        List<Policy> listPolicies = new ArrayList<>();
        JSONArray jsonResults = response.getResults();
        for (int i = 0; i < jsonResults.length(); i++) {
            JSONObject jsonPolicy = jsonResults.optJSONObject(i);
            if (jsonPolicy == null) {
                throw new ParsingException("Invalid policy encountered", jsonResults.opt(i));
            }
            listPolicies.add(new Policy(jsonPolicy));
        }
        return listPolicies;
    }
}

