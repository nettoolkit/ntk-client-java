package com.nettoolkit.dashboards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.OffsetDateTime;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.request.PostRequest;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.internal.http.HttpContentType;

public class CreateLogsRequest extends PostRequest {
    private List<LogRecordInput> mlistLogs = new ArrayList<>();

    public CreateLogsRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/create-logs"; }

    /**
     * Sets the default signal for logs by ID. Individual logs can override the default signal.
     *
     * @param signalId
     * @return this
     */
    public CreateLogsRequest signalId(UUID signalId) {
        getParameters().put("signal_id", signalId);
        return this;
    }

    /**
     * Sets the default signal for logs by name. Individual logs can override the default signal.
     *
     * @param strSignalName
     * @return this
     */
    public CreateLogsRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the default signal description for logs. Individual logs can override the default signal.
     *
     * @param strSignalDescription
     * @return this
     */
    public CreateLogsRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Adds a new log to the logs list. Shorthand for {@link #addLog(LogRecordInput)}.
     * Time defaults to now.
     * <em>at least one log is required</em>
     *
     * @param strLogBody
     * @return this
     */
    public CreateLogsRequest addLog(String strLogBody) {
        return addLog(LogRecordInput.newBuilder()
            .time(OffsetDateTime.now())
            .body(strLogBody)
            .build());
    }

    /**
     * Adds a new log to the logs list. Shorthand for {@link #addLog(LogRecordInput)}.
     * Time defaults to now. Log body includes msessage and throwable stack trace.
     * <em>at least one log is required</em>
     *
     * @param strLogBody
     * @return this
     */
    public CreateLogsRequest addLog(String strMessage, Throwable throwable) {
        return addLog(LogRecordInput.newBuilder()
            .time(OffsetDateTime.now())
            .body(strMessage, throwable)
            .build());
    }

    /**
     * Adds a new log to the logs list. Shorthand for {@link #addLog(LogRecordInput)}.
     * Time defaults to now.
     * <em>at least one log is required</em>
     *
     * @param strLogBody
     * @param time
     * @return this
     */
    public CreateLogsRequest addLog(String strLogBody, OffsetDateTime time) {
        return addLog(LogRecordInput.newBuilder()
            .time(time)
            .body(strLogBody)
            .build());
    }

    /**
     * Adds a new log to the logs list.
     * <em>at least one log is required</em>
     *
     * @param log
     * @return this
     */
    public CreateLogsRequest addLog(LogRecordInput log) {
        mlistLogs.add(log);
        return this;
    }

    /**
     * Adds multiple new logs to the log list.
     * <em>at least one log is required</em>
     *
     * @param listLogs
     * @return this
     */
    public CreateLogsRequest addLogs(List<LogRecordInput> listLogs) {
        mlistLogs.addAll(listLogs);
        return this;
    }

    /**
     * Sends the request.
     *
     * @return void
     * @throws NetToolKitException
     */
    public void send() throws NetToolKitException {
        JSONArray jsonLogs = new JSONArray();
        for (LogRecordInput log : mlistLogs) {
            try {
                jsonLogs.put(log.toJson());
            } catch (JSONException jsone) {
                throw new ParsingException(jsone, log);
            }
        }
        getParameters().put("logs", jsonLogs);
        getClient().sendV2(this);
    }
}

