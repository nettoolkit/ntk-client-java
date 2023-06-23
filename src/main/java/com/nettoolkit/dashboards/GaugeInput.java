package com.nettoolkit.dashboards;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

public class GaugeInput {
    private OffsetDateTime mTime;
    private double mdValue;
    private AttributeMap mAttributes; // might be null
    private UUID mSignalId; // might be null
    private String mstrSignalName; // might null
    private String mstrSignalDescription; // might null

    private GaugeInput(
        OffsetDateTime time,
        double dValue,
        AttributeMap attributes,
        UUID signalId,
        String strSignalName,
        String strSignalDescription
    ) {
        mTime = Objects.requireNonNull(time);
        mdValue = dValue;
        mAttributes = attributes;
        mSignalId = signalId;
        mstrSignalName = strSignalName;
        mstrSignalDescription = strSignalDescription;
    }

    public static Builder newBuilder() { return new Builder(); }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonAttributes = null;
        if (mAttributes != null) {
            jsonAttributes = mAttributes.toJson();
        }
        return new JSONObject()
            .put("time", mTime.toInstant().toEpochMilli())
            .put("value", mdValue)
            .put("attributes", jsonAttributes)
            .put("signal_id", mSignalId)
            .put("signal_name", mstrSignalName)
            .put("signal_description", mstrSignalDescription);
    }

    @Override
    public String toString() {
        return "{time=" + mTime + ", value=" + mdValue + ", attributes=" + mAttributes + "}";
    }

    public static class Builder {
        private OffsetDateTime mTime;
        private double mdValue;
        private AttributeMap mAttributes;
        private UUID mSignalId;
        private String mstrSignalName;
        private String mstrSignalDescription;

        public Builder time(OffsetDateTime time) {
            mTime = time;
            return this;
        }

        public Builder value(double dValue) {
            mdValue = dValue;
            return this;
        }

        public Builder attributes(AttributeMap attributes) {
            mAttributes = attributes;
            return this;
        }

        public Builder signalId(UUID signalId) {
            mSignalId = signalId;
            return this;
        }

        public Builder signalName(String strSignalName) {
            mstrSignalName = strSignalName;
            return this;
        }

        public Builder signalDescription(String strSignalDescription) {
            mstrSignalDescription = strSignalDescription;
            return this;
        }

        public GaugeInput build() {
            return new GaugeInput(
                mTime,
                mdValue,
                mAttributes,
                mSignalId,
                mstrSignalName,
                mstrSignalDescription
            );
        }
    }
}

