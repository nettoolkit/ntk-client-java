package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional; import java.util.UUID; import com.nettoolkit.exception.ResponseParsingException; import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONObject;

public class DurationSpan {
    private UUID mId;
    private UUID mSignalId;
    private OffsetDateTime mStartTime;
    private OffsetDateTime mEndTime; // might be null
    private JSONObject mjsonAttributes;
    private List<DurationEvent> mlistEvents; // might be null

    public DurationSpan(
        UUID id,
        UUID signalId,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        JSONObject jsonAttributes,
        List<DurationEvent> listEvents
    ) {
        mId = id;
        mSignalId = signalId;
        mStartTime = startTime;
        mEndTime = endTime;
        mjsonAttributes = jsonAttributes;
        mlistEvents = listEvents;
    }

    public static DurationSpan fromResponseJson(JSONObject jsonSpan)
            throws ResponseParsingException {
        UUID id;
        try {
            id = UUID.fromString(jsonSpan.getString("id"));
        } catch (Exception e) {
            throw ResponseParsingException.expectedUuid(
                "duration_span",
                "id",
                jsonSpan.opt("id"),
                e,
                jsonSpan
            );
        }
        UUID signalId;
        try {
            signalId = UUID.fromString(jsonSpan.getString("signal_id"));
        } catch (Exception e) {
            throw ResponseParsingException.expectedUuid(
                "duration_span",
                "signal_id",
                jsonSpan.opt("signal_id"),
                e,
                jsonSpan
            );
        }
        long lStartTime;
        try {
            lStartTime = jsonSpan.getLong("start_time");
        } catch (Exception e) {
            throw ResponseParsingException.expectedUnixMsTimestamp(
                "duration_span",
                "start_time",
                jsonSpan.opt("start_time"),
                e,
                jsonSpan
            );
        }
        Long lEndTime;
        try {
            if (!jsonSpan.isNull("end_time")) {
                lEndTime = jsonSpan.getLong("end_time");
            } else {
                lEndTime = null;
            }
        } catch (Exception e) {
            throw ResponseParsingException.expectedUnixMsTimestamp(
                "duration_span",
                "end_time",
                jsonSpan.opt("end_time"),
                e,
                jsonSpan
            );
        }
        JSONObject jsonAttributes;
        try {
            if (!jsonSpan.isNull("attributes")) {
                jsonAttributes = jsonSpan.getJSONObject("attributes");
            } else {
                jsonAttributes = null;
            }
        } catch (Exception e) {
            throw ResponseParsingException.expectedJsonObject(
                "duration_span",
                "attributes",
                jsonSpan.opt("attributes"),
                e,
                jsonSpan
            );
        }
        JSONArray jsonEvents;
        try {
            if (!jsonSpan.isNull("events")) {
                jsonEvents = jsonSpan.getJSONArray("events");
            } else {
                jsonEvents = null;
            }
        } catch (Exception e) {
            throw ResponseParsingException.expected(
                "duration_span",
                "events",
                "a list of events",
                jsonSpan.opt("events"),
                e,
                jsonSpan
            );
        }
        List<DurationEvent> listEvents;
        if (jsonEvents != null) {
            listEvents = new ArrayList<>();
            for (int i = 0; i < jsonEvents.length(); i++) {
                JSONObject jsonEvent;
                try {
                    jsonEvent = jsonEvents.getJSONObject(i);
                } catch (Exception e) {
                    throw ResponseParsingException.expected(
                        "duration_span",
                        "events",
                        "a list of events",
                        jsonSpan.opt("events"),
                        e,
                        jsonEvents.opt(i)
                    );
                }
                DurationEvent event = DurationEvent.fromResponseJson(jsonEvent);
                listEvents.add(event);
            }
        } else {
            listEvents = null;
        }
        return new DurationSpan(
            id,
            signalId,
            OffsetDateTime.ofInstant(Instant.ofEpochMilli(lStartTime), ZoneId.systemDefault()),
            lEndTime == null
                ? null
                : OffsetDateTime.ofInstant(Instant.ofEpochMilli(lEndTime), ZoneId.systemDefault()),
            jsonAttributes,
            listEvents
        );
    }

    public UUID getId() { return mId; }

    public UUID getSignalId() { return mSignalId; }

    public OffsetDateTime getStartTime() { return mStartTime; }

    public Optional<OffsetDateTime> getEndTime() {
        if (mEndTime == null) {
            return Optional.empty();
        }
        return Optional.of(mEndTime);
    }

    public Optional<JSONObject> getAttributes() {
        if (mjsonAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(mjsonAttributes);
    }

    public List<DurationEvent> getEvents() {
        if (mlistEvents == null) {
            return Collections.unmodifiableList(new ArrayList<>());
        }
        return Collections.unmodifiableList(mlistEvents);
    }
}

