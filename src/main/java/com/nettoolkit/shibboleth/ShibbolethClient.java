package com.nettoolkit.shibboleth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import com.nettoolkit.api.StatusCode;
import com.nettoolkit.exception.NetToolKitException;
import com.nettoolkit.exception.BadArgumentException;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.internal.NetToolKitClient;
import com.nettoolkit.internal.Parameters;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;
import com.nettoolkit.json.JSONException;

public class ShibbolethClient extends NetToolKitClient {
    public enum CaptchaType {
        BASIC, SUBSTRING, MATH, PATH, SPOTTER, SPLIT, DATE, NAVAL_COMMAND;
    }

    // Instantiation
    public ShibbolethClient(String strApiKey) { super(strApiKey); }
    public ShibbolethClient(String strApiKey, boolean bUseHttps) {
        super(strApiKey, bUseHttps);
    }

    // API
    public Captcha createCaptcha(String strOptions, String strIpAddress)
            throws NetToolKitException {
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("options", strOptions);
            if (strIpAddress != null)
                jsonParams.put("ip_address", strIpAddress);
        }
        catch (JSONException e) {
            throw new BadArgumentException(e.getMessage(), strOptions);
        }

        String strResponse = sendPost("/v1/shibboleth/captchas", jsonParams);
        JSONObject jsonResult = getFirstResult(strResponse);

        return new Captcha(jsonResult);
    }

    public boolean verifyCaptcha(String strId, String strSubmittedKey,
            String options) throws NetToolKitException {
        Parameters parameters = new Parameters();
        parameters.put("submitted_key", strSubmittedKey);

        String strResponse = sendPost("/v1/shibboleth/captchas/" + strId
            + "/verification", parameters.toWwwFormUrlencoded());

        return getFirstResult(strResponse).optBoolean("pass", false);
    }
}

