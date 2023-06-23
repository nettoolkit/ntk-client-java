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

public class CreateMetricsRequest extends PostRequest {
    private List<GaugeInput> mlistGauges = new ArrayList<>();

    public CreateMetricsRequest(DashboardsClient client) {
        super(client);
    }

    @Override
    protected HttpContentType getContentType() { return HttpContentType.JSON; }

    @Override
    protected String getPath() { return "/v2/dashboards/create-metrics"; }

    /**
     * Sets the default signal for metrics by ID. Individual metrics can override the default
     * signal.
     *
     * @param signalId
     * @return this
     */
    public CreateMetricsRequest signalId(UUID signalId) {
        getParameters().put("signal_id", signalId);
        return this;
    }

    /**
     * Sets the default signal for metrics by name. Individual metrics can override the default
     * signal.
     *
     * @param strSignalName
     * @return this
     */
    public CreateMetricsRequest signalName(String strSignalName) {
        getParameters().put("signal_name", strSignalName);
        return this;
    }

    /**
     * Sets the default signal description for metrics. Individual metrics can override the default
     * signal.
     *
     * @param strSignalDescription
     * @return this
     */
    public CreateMetricsRequest signalDescription(String strSignalDescription) {
        getParameters().put("signal_description", strSignalDescription);
        return this;
    }

    /**
     * Adds a new gauge to the metrics list. Shorthand for {@link #addGauge(GaugeInput)}.
     * Time defaults to now.
     * <em>at least one metric is required</em>
     *
     * @param value
     * @return this
     */
    public CreateMetricsRequest addGauge(double dValue) {
        return addGauge(GaugeInput.newBuilder()
            .time(OffsetDateTime.now())
            .value(dValue)
            .build());
    }

    /**
     * Adds a new gauge to the metrics list. Shorthand for {@link #addGauge(GaugeInput)}.
     * <em>at least one metric is required</em>
     *
     * @param dValue
     * @param time
     * @return this
     */
    public CreateMetricsRequest addGauge(double dValue, OffsetDateTime time) {
        return addGauge(GaugeInput.newBuilder()
            .time(time)
            .value(dValue)
            .build());
    }

    /**
     * Adds a new gauge to the metrics list. Shorthand for {@link #addGauge(GaugeInput)}.
     * <em>at least one metric is required</em>
     *
     * @param dValue
     * @param time
     * @param attributes
     * @return this
     */
    public CreateMetricsRequest addGauge(
        double dValue,
        OffsetDateTime time,
        AttributeMap attributes
    ) {
        return addGauge(GaugeInput.newBuilder()
            .time(time)
            .value(dValue)
            .attributes(attributes)
            .build());
    }

    /**
     * Adds a new gauge to the metrics list.
     * <em>at least one metric is required</em>
     *
     * @param gauge
     * @return this
     */
    public CreateMetricsRequest addGauge(GaugeInput gauge) {
        mlistGauges.add(gauge);
        return this;
    }

    /**
     * Adds multiple new gauges to the metrics list.
     * <em>at least one metric is required</em>
     *
     * @param listGauges
     * @return this
     */
    public CreateMetricsRequest addGauges(List<GaugeInput> listGauges) {
        mlistGauges.addAll(listGauges);
        return this;
    }

    // private static String getStackTrace(Throwable throwable) {
    //     StringWriter sw = new StringWriter();
    //     throwable.printStackTrace(new PrintWriter(sw));
    //     return sw.toString();
    // }

    /**
     * Sends the request.
     *
     * @return void
     * @throws NetToolKitException
     */
    public void send() throws NetToolKitException {
        JSONArray jsonGauges = new JSONArray();
        for (GaugeInput gauge : mlistGauges) {
            try {
                jsonGauges.put(gauge.toJson());
            } catch (JSONException jsone) {
                throw new ParsingException(jsone, gauge);
            }
        }
        getParameters().put("gauges", jsonGauges);
        getClient().sendV2(this);
    }
}

