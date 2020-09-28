package com.nettoolkit.gatekeeper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONArray;

/**
 * Policy is a model for the main building blocks of a rule set.
 * Policies describe how your application should handle incoming visits based on visit history and IP intelligence.
 * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/policies">Gatekeeper policy docs</a>
 */
public class Policy implements Serializable {
    private JSONObject mjsonPolicy;
    private UUID mId;
    private String mstrName;
    private boolean mbVisitorNegated;
    private List<UUID> mlistVisitorGroupIds = new ArrayList<>();
    private List<UUID> mlistPageGroupIds = new ArrayList<>();
    private String mstrCaptchaStatus;
    private String mstrUserAgentType;
    private int miNumTimes;
    private int miTimeIntervalNum;
    private String mstrTimeIntervalUnit;
    private int miVisitInterval;
    private String mstrAuthorization;
    private String mstrReason;
    private UUID mIpAppenderVisitorGroupId;
    private int miIpAppenderExpirationTimeNum;
    private String mstrIpAppenderExpirationTimeUnit;
    private int miPriority;
    private boolean mbEnabled;
    private String mstrDescription;
    private long mlCreated;
    private boolean mbIsDefault;

    protected Policy(JSONObject jsonPolicy) throws ParsingException {
        String strId = jsonPolicy.optString("id", null);
        try {
            mId = UUID.fromString(strId);
        } catch (Exception e) {
            throw new ParsingException("Unable to parse policy ID", e, strId);
        }
        mstrName = jsonPolicy.optString("name", null);
        mbVisitorNegated = jsonPolicy.optBoolean("visitor_negated");

        List<UUID> listVisitorGroupIds = new ArrayList<>();
        JSONArray jsonVisitorGroupIds = jsonPolicy.optJSONArray("visitor_group_ids");
        if (jsonVisitorGroupIds != null) {
            for (int i = 0; i < jsonVisitorGroupIds.length(); i++) {
                String strVisitorGroupId = jsonVisitorGroupIds.optString(i);
                try {
                    UUID visitorGroupId = UUID.fromString(strVisitorGroupId);
                    listVisitorGroupIds.add(visitorGroupId);
                } catch (Exception e) {
                    throw new ParsingException("Unable to parse visitor group IDs value", 
                        e, strVisitorGroupId);
                }
            }
        }
        mlistVisitorGroupIds = listVisitorGroupIds;

        List<UUID> listPageGroupIds = new ArrayList<>();
        JSONArray jsonPageGroupIds = jsonPolicy.optJSONArray("page_group_ids");
        if (jsonPageGroupIds != null) {
            for (int i = 0; i < jsonPageGroupIds.length(); i++) {
                String strPageGroupId = jsonPageGroupIds.optString(i);
                try {
                    UUID pageGroupId = UUID.fromString(strPageGroupId);
                    listPageGroupIds.add(pageGroupId);
                } catch (Exception e) {
                    throw new ParsingException("Unable to parse page group IDs value",
                        e, strPageGroupId);
                }
            }
        }
        mlistPageGroupIds = listPageGroupIds;

        mstrCaptchaStatus = jsonPolicy.optString("captcha_status");
        mstrUserAgentType = jsonPolicy.optString("user_agent_type");
        miNumTimes = jsonPolicy.optInt("num_times");
        miTimeIntervalNum = jsonPolicy.optInt("time_interval_num");
        mstrTimeIntervalUnit = jsonPolicy.optString("time_interval_unit");
        miVisitInterval = jsonPolicy.optInt("visit_interval");
        mstrAuthorization = jsonPolicy.optString("authorization");
        mstrReason = jsonPolicy.optString("reason");

        JSONObject jsonIpAppender = jsonPolicy.optJSONObject("ip_appender");
        if (jsonIpAppender != null) {
            if (jsonIpAppender.has("visitor_group_id")
                && !jsonIpAppender.isNull("visitor_group_id")) {
                String strIpAppenderVisitorGroupId =
                    jsonIpAppender.optString("visitor_group_id", null);
                try {
                    mIpAppenderVisitorGroupId = UUID.fromString(strIpAppenderVisitorGroupId);
                } catch (Exception e) {
                    throw new ParsingException("Unable to parse IP appender visitor group ID",
                        e, strIpAppenderVisitorGroupId);
                }
            }
            if (jsonIpAppender.has("expiration_time_num")) {
                miIpAppenderExpirationTimeNum = jsonIpAppender.optInt("expiration_time_num");
            }
            if (jsonIpAppender.has("expiration_time_unit")) {
                mstrIpAppenderExpirationTimeUnit = 
                    jsonIpAppender.optString("expiration_time_unit");
            }
        }

        miPriority = jsonPolicy.optInt("priority");
        mbEnabled = jsonPolicy.optBoolean("enabled");
        mstrDescription = jsonPolicy.optString("description");
        mlCreated = jsonPolicy.optLong("created");
        mbIsDefault = jsonPolicy.optBoolean("is_default");

        mjsonPolicy = jsonPolicy;
    }

    /**
     * Get policy ID.
     *
     * @return policy ID
     */
    public UUID getId() { return mId; }

    /**
     * Get policy name.
     *
     * @return policy name
     */
    public String getName() { return mstrName; }

    /**
     * Get visitor negation setting. If <code>true</code>, this policy will count visitors
     * that do <em>not</em> match the policy's visitor groups.
     *
     * @return visitor negation setting
     */
    public boolean isVisitorNegated() { return mbVisitorNegated; }

    /**
     * Get list of IDs for policy visitor groups.
     *
     * @return list of visitor group IDs
     */
    public List<UUID> getVisitorGroupIds() { return mlistVisitorGroupIds; }

    /**
     * Get list of IDs for policy page groups. For policies that do not use page groups,
     * the list will be empty. 
     *
     * @return list of page group IDs
     */
    public List<UUID> getPageGroupIds() { return mlistPageGroupIds; }

    /**
     * Get CAPTCHA status setting. TODO: make this an enum?
     */
    public String getCaptchaStatus() { return mstrCaptchaStatus; }

    /**
     * Get number of visits required to trigger policy. 
     *
     * @return number of visits
     */
    public int getNumTimes() { return miNumTimes; }

    /**
     * Get time interval number for triggering policy.
     * For example, if the time interval is "10 MINUTES", this method will return <code>10</code>.
     *
     * @return time interval number
     */
    public int getTimeIntervalNum() { return miTimeIntervalNum; }

    /**
     * Get time interval time unit for triggering policy.
     * For example, if the time interval is "10 MINUTES", this method will return <code>"MINUTES"</code>.
     *
     * @return time interval unit
     */
    public String getTimeIntervalUnit() { return mstrTimeIntervalUnit; }

    /**
     * Get visit interval number for triggering policy.
     * The visit interval is the number of visits after the policy if first triggered
     * before it can be triggered again.
     * Visit interval only applies to CAPTCHA authorization policies at the moment.
     *
     * @return visit interval number or <code>0</code> if not applicable
     */
    public int getVisitInterval() { return miVisitInterval; }
    
    /**
     * Get the policy authorization. This could be <code>"allow"</code>, <code>"deny"</code>, <code>"captcha"</code>, or something custom.
     *
     * @return policy authorization
     */
    public String getAuthorization() { return mstrAuthorization; }

    /**
     * Get the policy reason for being triggered. This must be manually set.
     *
     * @return policy reason
     */
    public String getReason() { return mstrReason; }

    /**
     * Get the ID for this policy's IP appender visitor group.
     * If set, Gatekeeper will automatically append the visitor's IP address to the given visitor group
     * when this policy is triggered.
     *
     * @return IP appender visitor group ID or <code>null</code> if unset
     */
    public UUID getIpAppenderVisitorGroupId() { return mIpAppenderVisitorGroupId; }

    /**
     * Get the expiration time number for IP appender.
     * For example, if IP appender expiration is "1 MONTHS", this method will return <code>1</code>.
     *
     * @return IP appender expiration time number or <code>0</code> if unset
     */
    public int getIpAppenderExpirationTimeNum() { return miIpAppenderExpirationTimeNum; }

    /**
     * Get the expiration time unit for IP appender.
     * For example, if IP appender expiration is "1 MONTHS", this method will return <code>"MONTHS"</code>.
     *
     * @return IP appender expiration time unit or <code>null</code> if unset
     */
    public String getIpAppenderExpirationTimeUnit() { return mstrIpAppenderExpirationTimeUnit; }

    /**
     * Get policy priority number. Policies with a higher priority will be checked first.
     *
     * @return policy priority
     */
    public int getPriority() { return miPriority; }

    /**
     * Get policy enabled status. Disabled policies will not be checked.
     *
     * @return enabled status
     */
    public boolean isEnabled() { return mbEnabled; }

    /**
     * Get policy description. Description is used purely for internal notes.
     *
     * @return policy description
     */
    public String getDescription() { return mstrDescription; }

    /**
     * Get policy creation timestamp in milliseconds. Time zone is set to UTC.
     *
     * @return policy creation timestamp
     */
    public long getCreated() { return mlCreated; }

    /**
     * Check whether policy is a preset default.
     * Default policies are created automatically by Gatekeeper and cannot be modified.
     *
     * @return whether policy is a default 
     */
    public boolean isDefault() { return mbIsDefault; }

    // @Override
    // public String toString() { return mjsonPolicy.toString(); }
}

