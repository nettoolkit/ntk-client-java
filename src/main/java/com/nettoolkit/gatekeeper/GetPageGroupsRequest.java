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
import com.nettoolkit.gatekeeper.PageGroup;

/**
 * Request to retrieve a list of page groups. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/page-groups#list-page-groups"><code>GET /v1/gatekeeper/page-groups</code></a>.
 * <p>Sample:
 * <p><blockquote><pre>
 * List&lt;PageGroup&gt; pageGroups = gatekeeperClient.newGetPageGroupsRequest().send();
 * </pre></blockquote>
 */
public class GetPageGroupsRequest extends GetRequest {
    public GetPageGroupsRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public String getPath() { return "/v1/gatekeeper/page-groups"; }

    /**
     * Sends get page groups request and parses response.
     *
     * @return a list of page group objects
     * @throws NetToolKitException
     */
    public List<PageGroup> send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        List<PageGroup> listPageGroups = new ArrayList<>();
        JSONArray jsonResults = response.getResults();
        for (int i = 0; i < jsonResults.length(); i++) {
            JSONObject jsonPageGroup = jsonResults.optJSONObject(i);
            if (jsonPageGroup == null) {
                throw new ParsingException("Invalid page group encountered", jsonResults.opt(i));
            }
            listPageGroups.add(new PageGroup(jsonPageGroup));
        }
        return listPageGroups;
    }
}

