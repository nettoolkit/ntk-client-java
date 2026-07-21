package com.nettoolkit.gatekeeper;

import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.Instant;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.http.HttpContentType;
import com.nettoolkit.internal.request.GetRequest;

/**
 * <strong>EXPERIMENTAL</strong>: This route is not guaranteed to have stable performance yet. The API is also subject to change, based on supportable queries.
 */
public class CheckVisitorGroupMembershipRequest extends GetRequest {
    private UUID mVisitorGroupId = null;
    private String mstrVisitor = null;

    public CheckVisitorGroupMembershipRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    protected String getPath() {
        return "/v1/gatekeeper/visitor-groups/" + mVisitorGroupId
            + "/membership/" + mstrVisitor;
    }

    public CheckVisitorGroupMembershipRequest
        visitorGroupId(UUID visitorGroupId) {
        mVisitorGroupId = visitorGroupId;
        return this;
    }

    public CheckVisitorGroupMembershipRequest visitor(String strVisitor) {
        mstrVisitor = strVisitor;
        return this;
    }

    /**
     * Sends the request.
     *
     * @return the visit count for IP address as seen by policy
     * @throws NetToolKitException
     */
    public boolean send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        JSONObject jsonResult = response.getFirstResult();
        if (jsonResult != null) {
            String strIsMember = jsonResult.optString("is_member");
            if (strIsMember != null) {
                return strIsMember.equalsIgnoreCase("true");
            }
        }
        throw new NetToolKitException("Missing or invalid response from NetToolKit");
    }
}
