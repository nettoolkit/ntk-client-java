package com.nettoolkit.dashboards;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONArray;


public class ChannelDatum {

    protected UUID mId;
    protected UUID mChannelId;
    protected Double mdValue;
    protected Long mlCreated;
    protected JSONObject mjsonAdditionalValues = null;

    public ChannelDatum(JSONObject jsonChannelDatum) throws ParsingException {
        mId = UUID.fromString(jsonChannelDatum.optString("id"));
        mChannelId = UUID.fromString(jsonChannelDatum.optString("channel_id"));
        if (jsonChannelDatum.has("value")) {
            mdValue = jsonChannelDatum.optDouble("value");
        } else { mdValue = null; }
        mlCreated = jsonChannelDatum.optLong("created");
        mjsonAdditionalValues = jsonChannelDatum.optJSONObject("additional_values");

    }
    // Getters/setters
    public UUID getId() { return mId; }
    public void setId(UUID id) { mId = id; }

    public UUID getChannelId() { return mChannelId; }
    public void setChannelId(UUID channelId) { mChannelId = channelId; }
    public Double getValue() { return mdValue; }
    public void setValue(Double dValue) { 
        mdValue = dValue; 
    }
    public Long getCreated() { return mlCreated; }
    public void setCreated(Long lCreated) { mlCreated = lCreated; }
    public JSONObject getAdditionalValues() { return mjsonAdditionalValues; }
    public void setAdditionalValues(JSONObject jsonAdditionalValues) {
        mjsonAdditionalValues = jsonAdditionalValues;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonChannelDatum = new JSONObject();
        jsonChannelDatum.put("id", mId);
        jsonChannelDatum.put("channel_id", mChannelId);
        jsonChannelDatum.put("value", mdValue);
        jsonChannelDatum.put("created", mlCreated);
        jsonChannelDatum.put("additional_values", mjsonAdditionalValues);

        return jsonChannelDatum;
    }
}
