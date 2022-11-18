package com.nettoolkit.gatekeeper;

import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.internal.http.HttpContentType;

/**
 * Request to authorize a visit. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visits#create-and-authorize-visit"><code>POST /v1/gatekeeper/visits/authorization</code></a>. IP address and URL are required; other fields are optional.
 * <p>Sample:
 * <p><blockquote><pre>
 * Visit visit = gatekeeperClient.newAuthorizeVisitRequest()
 *     .ip("1.2.3.4")
 *     .url("https://example.com/hello")
 *     .userAgent("SomeBot/1.0 (+http://somebot.com/about)")
 *     .send();
 * </pre></blockquote>
 */
public class AuthorizeVisitRequest extends PostRequest {
    public AuthorizeVisitRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v1/gatekeeper/visits/authorization"; }

    /**
     * Sets the visit IP address. Expects dot notation for IPv4 and hexadecimal for IPv6.
     * <em>required</em>
     *
     * @param strIpAddress the IP address in dot or hexadecimal format
     * @return this
     */
    public AuthorizeVisitRequest ip(String strIpAddress) {
        getParameters().put("ip", strIpAddress);
        return this;
    }

    /**
     * Sets the visit URL. Best practice is to set full URL for request, but use cases may vary. Format is not strictly enforced.
     * <em>required</em>
     *
     * @param strUrl the URL
     * @return this
     */
    public AuthorizeVisitRequest url(String strUrl) {
        getParameters().put("url", strUrl);
        return this;
    }

    /**
     * Sets the visitor user agent. User agent can typically be found in the "User-Agent" HTTP header.
     *
     * @param strUserAgent the user agent string
     * @return this
     */
    public AuthorizeVisitRequest userAgent(String strUserAgent) {
        getParameters().put("user_agent", strUserAgent);
        return this;
    }

    /**
     * Sets the visit referrer. Referrer can typically be found in the "Referer" (<a href="https://en.wikipedia.org/wiki/HTTP_referer">famously misspelled</a>) HTTP header.
     *
     * @param strReferrer the referrer
     * @return this
     */
    public AuthorizeVisitRequest referrer(String strReferrer) {
        getParameters().put("referrer", strReferrer);
        return this;
    }

    /**
     * Sets the visitor session ID. Finding session ID may depend on server implementation. For <a href="https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html">Java's HttpSession</a>, you can get it from the <code>getId</code> method.
     *
     * @param strSessionId the session ID
     * @return this
     */
    public AuthorizeVisitRequest sessionId(String strSessionId) {
        getParameters().put("session_id", strSessionId);
        return this;
    }

    /**
     * Sets the visitor user ID. Finding user ID will depend entirely on your application's structure.
     *
     * @param lUserId the user ID number
     * @return this
     */
    public AuthorizeVisitRequest userId(Long lUserId) {
        getParameters().put("user_id", lUserId);
        return this;
    }

    /**
     * Sets whether to record the visit in the database. Unrecorded visits will not affect visit counts. Visits are recorded by default.
     *
     * @param bShouldRecord <code>true</code> to save visit (default), <code>false</code> to not save visit
     * @return this
     */
    public AuthorizeVisitRequest record(boolean bShouldRecord) {
        getParameters().put("record", bShouldRecord);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return a visit object containing authorization information
     * @throws NetToolKitException
     */
    public Visit send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        return new Visit(response.getFirstResult());
    }
}

