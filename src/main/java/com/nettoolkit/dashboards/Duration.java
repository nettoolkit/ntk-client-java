package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONObject;

public class Duration {
    private UUID mId;
    private UUID mChannelId;
    private OffsetDateTime mStartTime;
    private OffsetDateTime mEndTime;
    private JSONObject mjsonAdditionalValues;
    private List<Milestone> mlistMilestones;

    public Duration(JSONObject jsonDuration) {
        mId = UUID.fromString(jsonDuration.optString("id"));
        mChannelId = UUID.fromString(jsonDuration.optString("channel_id"));
        if (jsonDuration.has("start_time")) {
            mStartTime = OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(jsonDuration.optLong("start_time")),
                ZoneId.systemDefault()
            );
        }
        if (jsonDuration.has("end_time")) {
            mEndTime = OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(jsonDuration.optLong("end_time")),
                ZoneId.systemDefault()
            );
        }
        if (jsonDuration.has("additional_values")) {
            mjsonAdditionalValues = jsonDuration.optJSONObject("additional_values");
        }
        List<Milestone> listMilestones = new ArrayList<>();
        if (jsonDuration.has("milestones")) {
            JSONArray jsonMilestones = jsonDuration.optJSONArray("milestones");
            for (int i = 0; i < jsonMilestones.length(); i++) {
                JSONObject jsonMilestone = jsonMilestones.optJSONObject(i);
                listMilestones.add(new Milestone(jsonMilestone));
            }
        }
        mlistMilestones = listMilestones;
    }

    public UUID getId() { return mId; }

    public UUID getChannelId() { return mChannelId; }

    public OffsetDateTime getStartTime() { return mStartTime; }

    public OffsetDateTime getEndTime() { return mEndTime; }

    public JSONObject getAdditionalValues() { return mjsonAdditionalValues; }

    public List<Milestone> getMilestones() { return mlistMilestones; }

    public static class Milestone {
        private String mstrName;
        private OffsetDateTime mTimestamp;
        private boolean mbIsFailure = false;
        private JSONObject mjsonAdditionalValues;

        public Milestone(JSONObject jsonMilestone) {
            mstrName = jsonMilestone.optString("name");
            if (jsonMilestone.has("timestamp")) {
                mTimestamp = OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(jsonMilestone.optLong("timestamp")),
                    ZoneId.systemDefault()
                );
            }
            mbIsFailure = jsonMilestone.optBoolean("failure", false);
            if (jsonMilestone.has("additional_values")) {
                mjsonAdditionalValues = jsonMilestone.optJSONObject("additional_values");
            }
        }

        public String getName() { return mstrName; }
        
        public OffsetDateTime getTimestamp() { return mTimestamp; }

        public boolean isFailure() { return mbIsFailure; }

        public JSONObject getAdditionalValues() { return mjsonAdditionalValues; }
    }
}

