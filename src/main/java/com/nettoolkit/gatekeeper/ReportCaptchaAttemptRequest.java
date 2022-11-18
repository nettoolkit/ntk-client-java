package com.nettoolkit.gatekeeper;

import java.util.UUID;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.internal.request.PutRequest;
import com.nettoolkit.internal.http.HttpContentType;

/**
 * Request to report a CAPTCHA attempt by updating visit CAPTCHA status. Corresponds to endpoint <a href="https://www.nettoolkit.com/docs/gatekeeper/api/visits#update-visit-captcha-status"><code>PUT /gatekeeper/visits/:visit_id/captcha</code></a>.
 * Visit ID and CAPTCHA status are required.
 * <p>If the visit did not have the "captcha" authorization, this request will throw an {@link com.nettoolkit.exception.ApiException} with a NOT_FOUND code.
 * <p>Sample:
 * <p><blockquote><pre>
 * gatekeeperClient.newReportCaptchaAttemptRequest()
 *     .visitId("5f3b7ffe-9740-4aa4-9c15-7fb3dc8df636")
 *     .status(CaptchaStatus.SOLVED)
 *     .send();
 * </pre></blockquote>
 */
public class ReportCaptchaAttemptRequest extends PutRequest {
    private UUID mVisitId;

    public ReportCaptchaAttemptRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    public String getPath() {
        return "/v1/gatekeeper/visits/" + mVisitId + "/captcha";
    }

    /**
     * Sets the visit ID. 
     * <em>required</em>
     *
     * @param visitId the visit ID
     * @return this
     */
    public ReportCaptchaAttemptRequest visitId(UUID visitId) {
        mVisitId = visitId;
        return this;
    }

    /**
     * Sets the visit ID.
     *
     * @param strVisitId the visit ID as a string
     * @return this
     * @throws IllegalArgumentException if the string is not a valid UUID
     * @see ReportCaptchaAttemptRequest#visitId(UUID)
     */
    public ReportCaptchaAttemptRequest visitId(String strVisitId) {
        return visitId(UUID.fromString(strVisitId));
    }

    /**
     * Sets the CAPTCHA status. This can either be SOLVED or FAILED.
     * <em>required</em>
     *
     * @param status the CAPTCHA status
     * @return this
     */
    public ReportCaptchaAttemptRequest status(CaptchaStatus status) {
        getParameters().put("status", String.valueOf(status));
        return this;
    }

    /**
     * Sends the request.
     *
     * @throws NetToolKitException
     */
    public void send() throws NetToolKitException {
        getClient().send(this);
    }
}


