package com.nettoolkit.gatekeeper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;

/**
 * VisitorGroup is a model for classifying incoming visits based on information about the visitor.
 * Information used by visitor groups include IP address, user agent, user ID, and so on.
 * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/visitor-groups">Gatekeeper visitor group docs</a>
 */
public class VisitorGroup implements Serializable {
    private JSONObject mjsonVisitorGroup;
    private UUID mId;
    private String mstrName;
    private String mstrType;
    private String mstrOperator;
    private List<Visitor> mlistVisitors = new ArrayList<>();

    protected VisitorGroup(JSONObject jsonVisitorGroup) throws ParsingException {
        String strId = jsonVisitorGroup.optString("id");
        if (strId != null) {
            try {
                mId = UUID.fromString(strId);
            } catch (Exception e) {
                throw new ParsingException("Unable to parse visitor group ID", e, strId);
            }
        }
        mstrName = jsonVisitorGroup.optString("name", null);
        mstrType = jsonVisitorGroup.optString("visitor_type", null);
        mstrOperator = jsonVisitorGroup.optString("operator", null);
        List<Visitor> listVisitors = new ArrayList<>();
        JSONArray jsonVisitors = jsonVisitorGroup.optJSONArray("visitors");
        if (jsonVisitors != null) {
            for (int i = 0; i < jsonVisitors.length(); i++) {
                JSONObject jsonVisitor = jsonVisitors.optJSONObject(i);
                try {
                    Visitor visitor = new Visitor(jsonVisitor);
                    listVisitors.add(visitor);
                } catch (JSONException jsone) {
                    throw new ParsingException("Unable to parse visitor" + jsone, jsonVisitor);
                }
            }
        }
        mlistVisitors = listVisitors;
        mjsonVisitorGroup = jsonVisitorGroup;
    }

    /**
     * Get visitor group ID.
     *
     * @return visitor group ID
     */
    public UUID getId() { return mId; }

    /**
     * Get visitor group name.
     *
     * @return visitor group name
     */
    public String getName() { return mstrName; }

    /**
     * Get visitor group type.
     * The visitor type represents the kind of visitors contained by this group.
     * Options include <code>"IP"</code>, <code>"ORGANIZATION"</code>, <code>"TAG"</code>, <code>"COUNTRY"</code>, and <code>"USER_ID"</code>.
     * For a complete list, see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/visitor-groups#visitor-types">visitor types documentation</a>.
     *
     * @return visitor type
     */
    public String getType() { return mstrType; }

    /**
     * Get operator for USER_ID type visitor groups.
     * Visitor groups of type USER_ID include an operator to determine how to match visitors.
     * Options include <code>"LESS"</code>, <code>"GREATER"</code>, <code>"BETWEEN"</code>, and <code>"IN"</code>.
     */
    public String getOperator() { return mstrOperator; }

    // public JSONObject toJson() { return mjsonVisitorGroup; }

    public static class Visitor {
        private String mstrValue;
        private Long mlCreated;
        private Long mlExpires;
        private String mstrCreator;

        protected Visitor(JSONObject jsonVisitor) throws JSONException {
            mstrValue = jsonVisitor.optString("value", null);
            if (jsonVisitor.has("created")) {
                mlCreated = jsonVisitor.optLong("created");
            }
            if (jsonVisitor.has("expires")) {
                mlExpires = jsonVisitor.optLong("expires");
            }
            String strCreatorType = jsonVisitor.optString("creator_type", null);
            String strCreatorName = jsonVisitor.optString("creator_name", null);
            if (strCreatorType != null && strCreatorName != null) {
                mstrCreator = strCreatorType + " " + strCreatorName;
            } else {
                mstrCreator = strCreatorType;
            }
        }

        public String getValue() { return mstrValue; }
        public Long getCreated() { return mlCreated; }
        public Long getExpires() { return mlExpires; }
        public String getCreator() { return mstrCreator; }
    }
}

