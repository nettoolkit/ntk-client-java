package com.nettoolkit.dashboards;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONArray;

public class Channel {
        
    protected UUID mId;
    protected String mstrName;
    protected String mstrDisplayName;
    protected String mstrType;
    protected Double mdDenominator;

    public Channel(JSONObject jsonChannel) throws ParsingException {
        try {
            mId = UUID.fromString(jsonChannel.getString("id"));
            mstrName = jsonChannel.getString("name");
            mstrDisplayName = jsonChannel.optString("display_name");
            mstrType = jsonChannel.optString("type");
            mdDenominator = (Double) jsonChannel.opt("denominator");
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, jsonChannel);
        }
    }

    public UUID getId() { return mId; }
    public void setId(UUID id) {
        mId = id;
    }
    public String getName() { return mstrName; }
    public void setName(String strName) {
        mstrName = strName; 
    }
    public String getDisplayName() { return mstrDisplayName; }
    public void setDisplayName(String strDisplayName) {
        mstrDisplayName = strDisplayName; 
    }
    public String getType() { return mstrType; }
    public void setType(String strType) {
        mstrType = strType; 
    }
    public Double getDenominator() { return mdDenominator; }
    public void setDenominator(Double dDenominator) {
        mdDenominator = dDenominator;
    }
}
