package com.nettoolkit.gatekeeper;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.BadArgumentException;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.internal.http.HttpContentType;

/**
 * Request to add a visitor to a visitor group. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visitor-groups#add-visitor-to-visitor-group"><code>POST /v1/gatekeeper/visitor-groups/:visitor_group_id/visitors</code></a>.
 * Visitor group ID and visitor are required.
 * <p>If the visitor already exists, the request succeeds without making any changes.
 * <p>Sample:
 * <p><blockquote><pre>
 * gatekeeperClient.newAddVisitorRequest()
 *     .visitorGroupId("b18f397b-d5f5-404a-b26c-00e295d3b3d3")
 *     .visitor("1.2.3.4")
 *     .send();
 * </pre></blockquote>
 */
public class AddVisitorRequest extends PostRequest {
    private UUID mVisitorGroupId;

    public AddVisitorRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() {
        return "/v1/gatekeeper/visitor-groups/" + mVisitorGroupId + "/visitors";
    }

    /**
     * Sets the visitor group ID. The visitor will be added to this visitor group.
     * <em>required</em>
     *
     * @param visitorGroupId the visitor group ID
     * @return this
     */
    public AddVisitorRequest visitorGroupId(UUID visitorGroupId) {
        mVisitorGroupId = visitorGroupId;
        return this;
    }

    /**
     * Sets the visitor group ID.
     *
     * @param strVisitorGroupId the visitor group ID as a string
     * @return this
     * @throws IllegalArgumentException if the string is not a valid UUID
     * @see AddVisitorRequest#visitorGroupId(UUID)
     */
    public AddVisitorRequest visitorGroupId(String strVisitorGroupId) {
        return visitorGroupId(UUID.fromString(strVisitorGroupId));
    }

    /**
     * Sets the visitor value. The visitor must be a valid member of the visitor group type.
     *
     * @param strVisitor the visitor value
     * @return this
     * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/visitor-groups#visitor-types">visitor type docs</a>
     */
    public AddVisitorRequest visitor(String strVisitor) {
        getParameters().put("visitor", strVisitor);
        return this;
    }

    /**
     * Sets an expiration value after which any visitors created by this request will be removed
     * from the visitor group.
     *
     * @param iTimeNum
     * @param timeUnit
     * @return this
     */
    public AddVisitorRequest expiration(int iTimeNum, TimeUnit timeUnit) {
        getParameters().put("expiration_time_num", iTimeNum);
        getParameters().put("expiration_time_unit", timeUnit.name());
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

