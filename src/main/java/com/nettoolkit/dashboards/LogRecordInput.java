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

    public static Builder newBuilder() { return new Builder(); }

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

        public Builder time(OffsetDateTime time) {
            mTime = time;
            return this;
        }

        public Builder observedTime(OffsetDateTime observedTime) {
            mObservedTime = observedTime;
            return this;
        }

        public Builder severity(LogSeverity severity) {
            mSeverity = severity;
            return this;
        }

        public Builder severityText(String strSeverityText) {
            mstrSeverityText = strSeverityText;
            return this;
        }

        public Builder body(String strBody) {
            mstrBody = strBody;
            return this;
        }

        public Builder body(String strMessage, Throwable throwable) {
            mstrBody = strMessage + "\n" + getStackTrace(throwable);
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

