package com.nettoolkit.gatekeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.BadArgumentException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.gatekeeper.AuthorizeVisitRequest;
import com.nettoolkit.gatekeeper.GetPoliciesRequest;
import com.nettoolkit.gatekeeper.GetVisitorGroupsRequest;
import com.nettoolkit.gatekeeper.AddVisitorRequest;
import com.nettoolkit.gatekeeper.RemoveVisitorRequest;
import com.nettoolkit.gatekeeper.CountVisitsForPolicyRequest;
import com.nettoolkit.gatekeeper.ReportCaptchaAttemptRequest;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.StatusCode;
import com.nettoolkit.internal.Parameters;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

/**
 * GatekeeperClient is the primary class for interacting with the Gatekeeper service web API.
 * Every endpoint in the web API should have a corresponding method in this class.
 * <p>Here's a typical usage of GatekeeperClient for visit authorization based on <a href="https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html">Java's HttpServlet</a>:
 * <p><blockquote><pre>
 * public class MyServlet extends HttpServlet {
 *     private GatekeeperClient gatekeeperClient;
 *
 *     &#064;Override
 *     public void init() throws ServletException {
 *         gatekeeperClient = new GatekeeperClient(NTK_API_KEY);
 *     }
 *
 *     &#064;Override
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
 * <pre></blockquote>
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

    /*
    public String exportConfiguration() throws NetToolKitException {
        String strResponse = sendGet("/v1/gatekeeper/configs");
        return getFirstResult(strResponse).toString();
    }

    public void importConfiguration(String strConfigurationJson) 
            throws NetToolKitException {
        JSONObject jsonConfig;
        try {
            jsonConfig = new JSONObject(strConfigurationJson);
        } catch (JSONException jsone) {
            throw new BadArgumentException(jsone.getMessage(), strConfigurationJson);
        }
        String strResponse = sendPost("/v1/gatekeeper/configs", jsonConfig);
        parseResponse(strResponse); // Checks for errors
    }

    public String simulateVisits(String strVisitsJson) throws NetToolKitException {
        JSONObject jsonVisits;
        try {
            jsonVisits = new JSONObject().put("visits", new JSONArray(strVisitsJson));
        } catch (JSONException jsone) {
            throw new BadArgumentException(jsone.getMessage(), strVisitsJson);
        }
        String strResponse = sendPost("/v1/gatekeeper/visits/simulations", jsonVisits);
        parseResponse(strResponse); // Checks for errors
        return strResponse;
    }
    */
}

