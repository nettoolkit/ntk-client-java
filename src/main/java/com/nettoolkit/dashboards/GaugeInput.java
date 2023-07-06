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

    /**
     * Constructs a new gauge input.
     *
     * @param time
     * @param dValue
     * @param attributes
     * @param signalId
     * @param strSignalName
     * @param strSignalDescription
     */
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

    /**
     * Creates a new gauge input builder.
     *
     * @return the builder
     */
    public static Builder newBuilder() { return new Builder(); }

    /**
     * Creates a JSON representation of this object.
     *
     * @return this object in JSON format
     * @throws JSONException
     */
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

        /**
         * Sets the gauge time. Default is current time.
         *
         * @param time
         * @return this
         */
        public Builder time(OffsetDateTime time) {
            mTime = time;
            return this;
        }

        /**
         * Sets the gauge value.
         * <em>required</em>
         *
         * @param dValue
         * @return this
         */
        public Builder value(double dValue) {
            mdValue = dValue;
            return this;
        }

        /**
         * Sets the gauge attributes.
         *
         * @param attributes
         * @return this
         */
        public Builder attributes(AttributeMap attributes) {
            mAttributes = attributes;
            return this;
        }

        /**
         * Sets a string attribute.
         *
         * @param strKey
         * @param strValue
         * @return this
         */
        public Builder attribute(String strKey, String strValue) {
            if (mAttributes == null) {
                mAttributes = new AttributeMap();
            }
            mAttributes.set(strKey, strValue);
            return this;
        }

        /**
         * sets a number attributes.
         *
         * @param strKey
         * @param dValue
         * @return this
         */
        public Builder attribute(String strKey, double dValue) {
            if (mAttributes == null) {
                mAttributes = new AttributeMap();
            }
            mAttributes.set(strKey, dValue);
            return this;
        }

        /**
         * Sets a boolean attribute.
         *
         * @param strKey
         * @param bValue
         * @return this
         */
        public Builder attribute(String strKey, boolean bValue) {
            if (mAttributes == null) {
                mAttributes = new AttributeMap();
            }
            mAttributes.set(strKey, bValue);
            return this;
        }

        /**
         * Sets the signal for gauge by ID.
         *
         * @param signalId
         * @return this
         */
        public Builder signalId(UUID signalId) {
            mSignalId = signalId;
            return this;
        }

        /**
         * Sets the signal for gauge by name.
         *
         * @param strSignalName
         * @return this
         */
        public Builder signalName(String strSignalName) {
            mstrSignalName = strSignalName;
            return this;
        }

        /**
         * Sets the signal description for gauge.
         *
         * @param strSignalDescription
         * @return this
         */
        public Builder signalDescription(String strSignalDescription) {
            mstrSignalDescription = strSignalDescription;
            return this;
        }

        /**
         * Build a gauge input from parameters.
         *
         * @return a new gauge input
         */
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

