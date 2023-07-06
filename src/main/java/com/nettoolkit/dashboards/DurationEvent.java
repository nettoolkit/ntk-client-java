package com.nettoolkit.dashboards;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import com.nettoolkit.exception.ResponseParsingException;
import com.nettoolkit.json.JSONObject;

public class DurationEvent {
    private UUID mId;
    private UUID mSpanId;
    private String mstrName;
    private OffsetDateTime mTime;
    private JSONObject mjsonAttributes;

    /**
     * Constructs a new duration event.
     *
     * @param id
     * @param spanId
     * @param strName
     * @param time
     * @param jsonAttributes
     */
    public DurationEvent(
        UUID id,
        UUID spanId,
        String strName,
        OffsetDateTime time,
        JSONObject jsonAttributes
    ) {
        mId = id;
        mSpanId = spanId;
        mstrName = strName;
        mTime = time;
        mjsonAttributes = jsonAttributes;
    }

    /**
     * Constructs a new duration event from API response JSON.
     *
     * @param jsonEvent
     * @return a new duration event
     * @throws ResponseParsingException
     */
    public static DurationEvent fromResponseJson(JSONObject jsonEvent)
            throws ResponseParsingException {
        UUID id;
        try {
            id = UUID.fromString(jsonEvent.getString("id"));
        } catch (Exception e) {
            throw ResponseParsingException.expectedUuid(
                "duration_event",
                "id",
                jsonEvent.opt("id"),
                e,
                jsonEvent
            );
        }
        UUID spanId;
        try {
            spanId = UUID.fromString(jsonEvent.getString("span_id"));
        } catch (Exception e) {
            throw ResponseParsingException.expectedUuid(
                "duration_event",
                "span_id",
                jsonEvent.opt("span_id"),
                e,
                jsonEvent
            );
        }
        String strName;
        try {
            strName = jsonEvent.getString("name");
        } catch (Exception e) {
            throw ResponseParsingException.expectedString(
                "duration_event",
                "name",
                jsonEvent.opt("name"),
                e,
                jsonEvent
            );
        }
        long lTime;
        try {
            lTime = jsonEvent.getLong("time");
        } catch (Exception e) {
            throw ResponseParsingException.expectedUnixMsTimestamp(
                "duration_event",
                "time",
                jsonEvent.opt("time"),
                e,
                jsonEvent
            );
        }
        JSONObject jsonAttributes = null;
        try {
            jsonAttributes = jsonEvent.optJSONObject("attributes");
        } catch (Exception e) {
            throw ResponseParsingException.expectedJsonObject(
                "duration_event",
                "attributes",
                jsonEvent.opt("attributes"),
                e,
                jsonEvent
            );
        }
        return new DurationEvent(
            id,
            spanId,
            strName,
            OffsetDateTime.ofInstant(Instant.ofEpochMilli(lTime), ZoneId.systemDefault()),
            jsonAttributes
        );
    }

    public UUID getId() { return mId; }

    public UUID getSpanId() { return mSpanId; }

    public String getName() { return mstrName; }

    public OffsetDateTime getTime() { return mTime; }

    public Optional<JSONObject> getAttributes() {
        if (mjsonAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(mjsonAttributes);
    }
}

