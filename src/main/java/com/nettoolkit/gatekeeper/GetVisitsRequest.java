package com.nettoolkit.gatekeeper;

import java.util.ArrayList;
import java.util.List;
import com.nettoolkit.exception.BadArgumentException;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.gatekeeper.GatekeeperClient;
import com.nettoolkit.gatekeeper.VisitorGroup;
import com.nettoolkit.internal.ApiResponse;
import com.nettoolkit.internal.request.GetRequest;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;

/**
 * Request to retrieve visits.
 */
public class GetVisitsRequest extends GetRequest {
    protected String mstrVisitor = null;
    protected long mlEarliest = 0, mlLatest = 0;
    public GetVisitsRequest(GatekeeperClient client) {
        super(client);
    }

    @Override
    public String getPath() {
        return "/v1/gatekeeper/visits/" + mstrVisitor;
    }

    public GetVisitsRequest visitor(String strVisitor) {
        mstrVisitor = strVisitor;
        return this;
    }

    public GetVisitsRequest earliest(long lEarliest) {
        getParameters().put("earliest", lEarliest);
        return this;
    }

    public GetVisitsRequest latest(long lLatest) {
        getParameters().put("latest", lLatest);
        return this;
    }

    /**
     * Sends get visitor groups request and parses response.
     *
     * @return a list of visitor group objects
     * @throws NetToolKitException
     */
    public List<Visit> send() throws NetToolKitException {
        ApiResponse response = getClient().send(this);
        List<Visit> listVisits = new ArrayList<>();
        JSONArray jsonResults = response.getResults();
        for (int i = 0; i < jsonResults.length(); i++) {
            JSONObject jsonVisit = jsonResults.optJSONObject(i);
            if (jsonVisit == null) {
                throw new ParsingException("Invalid visit encountered",
                                           jsonResults.opt(i));
            }
            listVisits.add(new Visit(jsonVisit));
        }
        return listVisits;
    }
    public static void main(String args[]) throws Exception {
        GatekeeperClient gatekeeper = new GatekeeperClient("");
        GetVisitsRequest request = new GetVisitsRequest(gatekeeper);
        request.visitor("").earliest(1784627481000L).latest(1784658481000L);
        List<Visit> listVisits = request.send();
        for (Visit visit : listVisits) {
            System.out.println("visit: "+visit.getPage()+" at "+visit.getCreated());
        }
    }
}

