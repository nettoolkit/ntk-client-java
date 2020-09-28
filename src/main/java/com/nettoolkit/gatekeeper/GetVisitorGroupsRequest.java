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
import com.nettoolkit.gatekeeper.VisitorGroup;

/**
 * Request to retrieve a list of visitor groups. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visitor-groups#list-visitor-groups"><code>GET /v1/gatekeeper/visitor-groups</code></a>.
 * <p>Sample:
 * <p><blockquote><pre>
 * List&lt;VisitorGroup&gt; visitorGroups = gatekeeperClient.newGetVisitorGroupsRequest().send();
 * </pre></blockquote>
 */
public class GetVisitorGroupsRequest extends GetRequest {
    public GetVisitorGroupsRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public String getPath() { return "/v1/gatekeeper/visitor-groups"; }

    /**
     * Sends get visitor groups request and parses response.
     *
     * @return a list of visitor group objects
     * @throws NetToolKitException
     */
    public List<VisitorGroup> send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        List<VisitorGroup> listVisitorGroups = new ArrayList<>();
        JSONArray jsonResults = response.getResults();
        for (int i = 0; i < jsonResults.length(); i++) {
            JSONObject jsonVisitorGroup = jsonResults.optJSONObject(i);
            if (jsonVisitorGroup == null) {
                throw new ParsingException("Invalid visitor group encountered",
                    jsonResults.opt(i));
            }
            listVisitorGroups.add(new VisitorGroup(jsonVisitorGroup));
        }
        return listVisitorGroups;
    }
}

