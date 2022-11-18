package com.nettoolkit.gatekeeper;

import com.nettoolkit.internal.NetToolKitClient;

/**
 * GatekeeperClient is the primary class for interacting with the Gatekeeper service web API.
 * Every endpoint in the web API should have a corresponding method in this class.
 * <p>Here's a typical usage of GatekeeperClient for visit authorization based on <a href="https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html">Java's HttpServlet</a>:
 * <p><blockquote><pre>{@code
 * public class MyServlet extends HttpServlet {
 *     private GatekeeperClient gatekeeperClient;
 *
 *     @Override
 *     public void init() throws ServletException {
 *         gatekeeperClient = new GatekeeperClient(NTK_API_KEY);
 *     }
 *
 *     @Override
 *     protected void doGet(HttpServletRequest request, HttpServletResponse response)
 *             throws ServletException, IOException {
 *         StringBuffer url = request.getRequestURL();
 *         if (request.getQueryString() != null) {
 *             url.append("?").append(request.getQueryString());
 *         }
 *         Visit visit = gatekeeperClient.newAuthorizeVisitRequest()
 *             .ip(request.getRemoteAddr())
 *             .url(url.toString())
 *             .userAgent(request.getHeader("User-Agent"))
 *             .send();
 *         // Handle request based on authorization...
 *     }
 * }
 * }</pre></blockquote>
 *
 * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/api/overview">web API docs</a>
 */
public class GatekeeperClient extends NetToolKitClient {
    /**
     * Constructs a new GatekeeperClient which uses the given API key for requests.
     *
     * @param strApiKey The <a href="https://www.nettoolkit.com/docs/overview#authentication">NetToolKit API key</a> used to authenticate requests. All requests made by GatekeeperClient will use this API key.
     */
    public GatekeeperClient(String strApiKey) {
        super(strApiKey);
    }

    public GatekeeperClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }

    /**
     * Creates a new request to authorize a visit. Call {@link com.nettoolkit.gatekeeper.AuthorizeVisitRequest#send} to execute.
     *
     * @return a new visit authorization request object
     */
    public AuthorizeVisitRequest newAuthorizeVisitRequest() {
        return new AuthorizeVisitRequest(this);
    }

    /**
     * Creates a new request to get policies list. Call {@link com.nettoolkit.gatekeeper.GetPoliciesRequest#send} to execute.
     *
     * @return a new get policies request object
     */
    public GetPoliciesRequest newGetPoliciesRequest() {
        return new GetPoliciesRequest(this);
    }

    /**
     * Creates a new request to get visitor groups. Call {@link com.nettoolkit.gatekeeper.GetVisitorGroupsRequest#send} to execute.
     *
     * @return a new get visitor groups request object
     */
    public GetVisitorGroupsRequest newGetVisitorGroupsRequest() {
        return new GetVisitorGroupsRequest(this);
    }

    /**
     * Creates a new request to add a visitor to a visitor group. Call {@link com.nettoolkit.gatekeeper.AddVisitorRequest#send} to execute.
     *
     * @return a new add visitor request object
     */
    public AddVisitorRequest newAddVisitorRequest() {
        return new AddVisitorRequest(this);
    }

    /**
     * Creates a new request to remove a visitor from a visitor group. Call {@link com.nettoolkit.gatekeeper.RemoveVisitorRequest#send} to execute.
     *
     * @return a new remove visitor request object
     */
    public RemoveVisitorRequest newRemoveVisitorRequest() {
        return new RemoveVisitorRequest(this);
    }

    /**
     * Create a new request to get page groups. Call {@link com.nettoolkit.gatekeeper.GetPageGroupsRequest#send} to execute.
     *
     * @return a new get page groups request object
     */
    public GetPageGroupsRequest newGetPageGroupsRequest() {
        return new GetPageGroupsRequest(this);
    }

    /**
     * Creates a new request to count visits. Call {@link com.nettoolkit.gatekeeper.CountVisitsRequest#send} to execute.
     *
     * @return a new count visits request object
     */
    public CountVisitsRequest newCountVisitsRequest() {
        return new CountVisitsRequest(this);
    }

    /**
     * Creates a new request to count visits for a certain policy. Call {@link com.nettoolkit.gatekeeper.CountVisitsForPolicyRequest#send} to execute.
     *
     * @return a new count visits for policy request object
     */
    public CountVisitsForPolicyRequest newCountVisitsForPolicyRequest() {
        return new CountVisitsForPolicyRequest(this);
    }

    /**
     * Creates a new request to report a CAPTCHA attempt. Call {@link com.nettoolkit.gatekeeper.ReportCaptchaAttemptRequest#send} to execute.
     *
     * @return a new report CAPTCHA attempt request object
     */
    public ReportCaptchaAttemptRequest newReportCaptchaAttemptRequest() {
        return new ReportCaptchaAttemptRequest(this);
    }
}

