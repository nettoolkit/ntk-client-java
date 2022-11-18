package com.nettoolkit.dashboards;

import java.io.Serializable;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;

public class ChannelDatum implements Serializable {
    private UUID mId;
    private UUID mChannelId;
    private Double mdValue;
    private Long mlCreated;
    private JSONObject mjsonAdditionalValues = null;
    private String mstrTextBlob;

    public ChannelDatum(JSONObject jsonChannelDatum) throws ParsingException {
        mId = UUID.fromString(jsonChannelDatum.optString("id"));
        mChannelId = UUID.fromString(jsonChannelDatum.optString("channel_id"));
        if (jsonChannelDatum.has("value")) {
            mdValue = jsonChannelDatum.optDouble("value");
        } else {
            mdValue = null;
        }
        mlCreated = jsonChannelDatum.optLong("created");
        mjsonAdditionalValues = jsonChannelDatum.optJSONObject("additional_values");
        mstrTextBlob = jsonChannelDatum.optString("text_blob");

    }

    public UUID getId() { return mId; }

    public UUID getChannelId() { return mChannelId; }

    public Double getValue() { return mdValue; }

    public Long getCreated() { return mlCreated; }

    public JSONObject getAdditionalValues() { return mjsonAdditionalValues; }

    public String getTextBlob() { return mstrTextBlob; }
}
