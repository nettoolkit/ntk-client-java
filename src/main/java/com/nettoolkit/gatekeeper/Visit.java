package com.nettoolkit.gatekeeper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;

/**
 * Visit is a model for visit information and authorization.
 * Visits contain both provided visit data, such as IP address and URL, and data derived from IP intelligence, such as country and tags.
 * If the visit object was created by visit authorization, it will contain authorization information as well.
 * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/policies">Gatekeeper policy docs</a>
 */
public class Visit implements Serializable {
    private UUID mId;
    private String mstrIpAddress;
    private String mstrDomain;
    private String mstrPage;
    private Long mlUserId;
    private String mstrUserAgent;
    private String mstrCountryCode;
    private String mstrCountryName;
    private List<String> mlistTags = new ArrayList<>();
    private UUID mPolicyId;
    private String mstrPolicyName;
    private String mstrAuthorization;
    private String mstrReason;
    private Long mlCreated;

    protected Visit(JSONObject jsonVisit) throws ParsingException {
        String strId = jsonVisit.optString("id", null);
        try {
            mId = UUID.fromString(strId);
        } catch (Exception e) {
            throw new ParsingException("Unable to parse visit ID", e, strId);
        }
        mstrIpAddress = jsonVisit.optString("ip", null);
        mstrDomain = jsonVisit.optString("domain", null);
        mstrPage = jsonVisit.optString("page", null);
        if (jsonVisit.has("user_id")) {
            mlUserId = jsonVisit.optLong("user_id");
        }
        mstrUserAgent = jsonVisit.optString("user_agent", null);
        mstrCountryCode = jsonVisit.optString("country_code", null);
        mstrCountryName = jsonVisit.optString("country_name", null);
        List<String> listTags = new ArrayList<>();
        JSONArray jsonTags = jsonVisit.optJSONArray("tags");
        if (jsonTags != null) {
            for (int i = 0; i < jsonTags.length(); i++) {
                String strTag = jsonTags.optString(i);
                listTags.add(strTag);
            }
        }
        mlistTags = listTags;

        // Visit auth only
        String strPolicyId = jsonVisit.optString("policy_id", null);
        if (strPolicyId != null) {
            try {
                mPolicyId = UUID.fromString(strPolicyId);
            } catch (Exception e) {
                throw new ParsingException("Unable to parse policy ID", e, strPolicyId);
            }
        }
        mstrPolicyName = jsonVisit.optString("policy_name", null);
        mstrAuthorization = jsonVisit.optString("authorization", null);
        mstrReason = jsonVisit.optString("reason", null);
        if (jsonVisit.has("created")) {
            mlCreated = jsonVisit.optLong("created");
        }
    }

    /**
     * Get visit ID.
     *
     * @return visit ID
     */
    public UUID getId() { return mId; }

    /**
     * Get visit IP address.
     *
     * @return IP address in IPv4 dot notation or IPv6 hexadecimal
     */
    public String getIpAddress() { return mstrIpAddress; }

    /**
     * Get visit domain.
     * For example, if visit URL is "https://www.example.com/hello/world?q=abc", this method will return <code>"https://www.example.com"</code>.
     *
     * @return visit domain
     */
    public String getDomain() { return mstrDomain; }

    /**
     * Get visit page.
     * For example, if visit URL is "https://www.example.com/hello/world?q=abc", this method will return <code>"/hello/world?q=abc"</code>.
     *
     * @return visit page
     */
    public String getPage() { return mstrPage; }

    /**
     * Get visitor user ID.
     *
     * @return visitor user ID or <code>null</code> if absent
     */
    public Long getUserId() { return mlUserId; }

    /**
     * Get visitor user agent.
     *
     * @return visitor user agent or <code>null</code> if absent
     */
    public String getUserAgent() { return mstrUserAgent; }

    /**
     * Get country code of visit.
     * Country codes are in ISO Alpha-2 format. For example, <code>"US"</code>, <code>"JP"</code>, etc.
     *
     * @return visit country code
     */
    public String getCountryCode() { return mstrCountryCode; }

    /**
     * Get country name of visit.
     *
     * @return visit country name
     */
    public String getCountryName() { return mstrCountryName; }

    /**
     * Get a list of tags for this visitor.
     *
     * @return list of tags
     */
    public List<String> getTags() { return mlistTags; }

    /**
     * Get ID of policy used to authorize this visit.
     * Policy ID will only be present if visit was created during visit authorization.
     *
     * @return authorizing policy ID or <code>null</code> if not applicable
     */
    public UUID getPolicyId() { return mPolicyId; }

    /**
     * Get name of policy used to authorize this visit.
     * Policy name will only be present if visit was created during visit authorization.
     *
     * @return authorizing policy name or <code>null</code> if not applicable
     */
    public String getPolicyName() { return mstrPolicyName; }

    /**
     * Get authorization for this visit.
     * Authorization will only be present if visit was created during visit authorization.
     *
     * @return authorization or <code>null</code> if not applicable
     */
    public String getAuthorization() { return mstrAuthorization; }

    /**
     * Get reason for authorization.
     * Reason will only be present if visit was created during visit authorization.
     *
     * @return reason for authorization or <code>null</code> if not applicable
     */
    public String getReason() { return mstrReason; }

    /**
     * Get visit creation timestamp in milliseconds. Time zone is set to UTC.
     *
     * @return visit creation timestamp
     */
    public long getCreated() { return mlCreated; }
}

