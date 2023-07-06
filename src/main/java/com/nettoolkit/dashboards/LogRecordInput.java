package com.nettoolkit.dashboards;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

public class LogRecordInput {
    private OffsetDateTime mTime;
    private OffsetDateTime mObservedTime;
    private LogSeverity mSeverity;
    private String mstrSeverityText;
    private String mstrBody;
    private AttributeMap mAttributes;
    private UUID mSignalId;
    private String mstrSignalName;
    private String mstrSignalDescription;

    /**
     * Constructs a new log record input.
     *
     * @param time
     * @param observedTime
     * @param severity
     * @param strSeverityText
     * @param strBody
     * @param attributes
     * @param signalId
     * @param strSignalName
     * @param strSignalDescription
     */
    private LogRecordInput(
        OffsetDateTime time,
        OffsetDateTime observedTime,
        LogSeverity severity,
        String strSeverityText,
        String strBody,
        AttributeMap attributes,
        UUID signalId,
        String strSignalName,
        String strSignalDescription
    ) {
        mTime = Objects.requireNonNull(time);
        mObservedTime = observedTime;
        mSeverity = severity;
        mstrSeverityText = strSeverityText;
        mstrBody = strBody;
        mAttributes = attributes;
        mSignalId = signalId;
        mstrSignalName = strSignalName;
        mstrSignalDescription = strSignalDescription;
    }

    /**
     * Creates a new log record input builder.
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
        Long lObservedTimeMs = null;
        if (mObservedTime != null) {
            lObservedTimeMs = mObservedTime.toInstant().toEpochMilli();
        }
        Integer iSeverity = null;
        if (mSeverity != null) {
            iSeverity = mSeverity.toNumber();
        }
        JSONObject jsonAttributes = null;
        if (mAttributes != null) {
            jsonAttributes = mAttributes.toJson();
        }
        return new JSONObject()
            .put("time", mTime.toInstant().toEpochMilli())
            .put("observed_time", lObservedTimeMs)
            .put("severity", iSeverity)
            .put("severity_text", mstrSeverityText)
            .put("body", mstrBody)
            .put("attributes", jsonAttributes)
            .put("signal_id", mSignalId)
            .put("signal_name", mstrSignalName)
            .put("signal_description", mstrSignalDescription);
    }

    @Override
    public String toString() {
        return "{time=" + mTime + ", severity=" + mSeverity + ", attributes=" + mAttributes + "}";
    }

    public static class Builder {
        private OffsetDateTime mTime;
        private OffsetDateTime mObservedTime;
        private LogSeverity mSeverity;
        private String mstrSeverityText;
        private String mstrBody;
        private AttributeMap mAttributes;
        private UUID mSignalId;
        private String mstrSignalName;
        private String mstrSignalDescription;

        /**
         * Sets the log time. Default is current time.
         *
         * @param time
         * @return this
         */
        public Builder time(OffsetDateTime time) {
            mTime = time;
            return this;
        }

        /**
         * Sets the log observed time. Default is current time.
         *
         * @param time
         * @return this
         */
        public Builder observedTime(OffsetDateTime time) {
            mObservedTime = time;
            return this;
        }

        /**
         * Sets the log severity.
         *
         * @param severity
         * @return this
         */
        public Builder severity(LogSeverity severity) {
            mSeverity = severity;
            return this;
        }

        /**
         * Sets the log severity text.
         *
         * @param strSeverityText
         * @return this
         */
        public Builder severityText(String strSeverityText) {
            mstrSeverityText = strSeverityText;
            return this;
        }

        /**
         * Sets the log body to the given message.
         *
         * @param strMessage
         * @return this
         */
        public Builder body(String strMessage) {
            mstrBody = strMessage;
            return this;
        }

        /**
         * Sets the log body to the given stack trace.
         *
         * @param throwable
         * @return this
         */
        public Builder body(Throwable throwable) {
            mstrBody = getStackTrace(throwable);
            return this;
        }

        /**
         * Sets the log body to the given message and stack trace.
         *
         * @param strMessage
         * @param throwable
         * @return
         */
        public Builder body(String strMessage, Throwable throwable) {
            mstrBody = strMessage + "\n" + getStackTrace(throwable);
            return this;
        }

        /**
         * Sets the log attributes.
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
         * Sets the signal for log by ID.
         *
         * @param signalId
         * @return this
         */
        public Builder signalId(UUID signalId) {
            mSignalId = signalId;
            return this;
        }

        /**
         * Sets the signal for log by name.
         *
         * @param strSignalName
         * @return this
         */
        public Builder signalName(String strSignalName) {
            mstrSignalName = strSignalName;
            return this;
        }

        /**
         * Sets the signal description for log.
         *
         * @param strSignalDescription
         * @return this
         */
        public Builder signalDescription(String strSignalDescription) {
            mstrSignalDescription = strSignalDescription;
            return this;
        }

        /**
         * Build a log record input from parameters.
         *
         * @return a new log record input
         */
        public LogRecordInput build() {
            return new LogRecordInput(
                mTime,
                mObservedTime,
                mSeverity,
                mstrSeverityText,
                mstrBody,
                mAttributes,
                mSignalId,
                mstrSignalName,
                mstrSignalDescription
            );
        }
    }

    private static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

