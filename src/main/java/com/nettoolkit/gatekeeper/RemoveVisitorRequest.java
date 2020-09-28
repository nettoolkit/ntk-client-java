package com.nettoolkit.gatekeeper;

import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.BadArgumentException;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.internal.Parameters;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.DeleteRequest;
import com.nettoolkit.internal.http.HttpMethod;
import com.nettoolkit.internal.http.HttpContentType;

/**
 * Request to remove a visitor from a visitor group. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visitor-groups#remove-visitor-from-visitor-group"><code>DELETE /gatekeeper/visitor-groups/:visitor_group_id/visitors</code></a>.
 * Visitor group ID and visitor are required.
 * <p>If the visitor does not exist, the request succeeds without making any changes.
 * <p>Sample:
 * <p><blockquote><pre>
 * gatekeeperClient.newRemoveVisitorRequest()
 *     .visitorGroupId("b18f397b-d5f5-404a-b26c-00e295d3b3d3")
 *     .visitor("1.2.3.4")
 *     .send();
 * </blockquote></pre>
 */
public class RemoveVisitorRequest extends DeleteRequest {
    private UUID mVisitorGroupId;

    public RemoveVisitorRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public String getPath() {
        return "/v1/gatekeeper/visitor-groups/" + mVisitorGroupId + "/visitors";
    }

    /**
     * Sets the visitor group ID. The visitor will be removed from this visitor group.
     * <em>required</em>
     *
     * @param visitorGroupId the visitor group ID
     * @return this
     */
    public RemoveVisitorRequest visitorGroupId(UUID visitorGroupId) {
        mVisitorGroupId = visitorGroupId;
        return this;
    }

    /**
     * Sets the visitor group ID.
     *
     * @param strVisitorGroupId the visitor group ID as a string
     * @return this
     * @throws IllegalArgumentException if the string is not a valid UUID
     * @see RemoveVisitorRequest#visitorGroupId(UUID)
     */
    public RemoveVisitorRequest visitorGroupId(String strVisitorGroupId) {
        return visitorGroupId(UUID.fromString(strVisitorGroupId));
    }

    /**
     * Sets the visitor value. 
     *
     * @param strVisitor the visitor value
     * @return this
     * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/visitor-groups#visitor-types">visitor type docs</a>
     */
    public RemoveVisitorRequest visitor(String strVisitor) {
        getParameters().put("visitor", strVisitor);
        return this;
    }

    /**
     * Sends the request.
     *
     * @throws NetToolKitException
     */
    public void send() throws NetToolKitException {
        if (mVisitorGroupId == null) {
            throw new BadArgumentException("Missing visitor group ID", mVisitorGroupId);
        }
        getClient().send(this);
    }
}

