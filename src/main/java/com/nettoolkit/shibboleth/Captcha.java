package com.nettoolkit.shibboleth;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONException;

public class Captcha implements Serializable {
    private final ShibbolethClient.CaptchaType type;
    private final String id;
    private final String caption;
    private final JSONObject displayInformation;

    public Captcha(JSONObject jsonCaptcha) throws ParsingException {
        try {
            this.type = ShibbolethClient.CaptchaType.valueOf(jsonCaptcha.getString("type"));
            this.id = jsonCaptcha.getString("id");
            this.displayInformation = jsonCaptcha.getJSONObject("displayInformation");
            this.caption = this.displayInformation.getString("caption");
        } catch (JSONException jsone) {
            throw new ParsingException(jsone, jsonCaptcha);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonCaptcha = new JSONObject();

        jsonCaptcha.put("type", this.type);
        jsonCaptcha.put("id", this.id);
        jsonCaptcha.put("displayInformation", this.displayInformation);

        return jsonCaptcha;
    }


    public ShibbolethClient.CaptchaType getType() { return this.type; }
    public String getId() { return this.id; }
    public String getCaption() { return this.caption; }
    public JSONObject getDisplayInformation() { return this.displayInformation; }

    //Member variables
    // protected Hashtable<String, String> mhtFields = null;
    // public static final String VERB = "verb", INTENSIFIER = "intensifier",
    //     OBJECTQUALIFIER = "objectQualifier", OBJECT = "object";
    // protected static final String FIELD_ORDER[] = { VERB, INTENSIFIER,
    //                                                 OBJECTQUALIFIER, OBJECT };

    // //Instantiation
    // public Captcha(JSONObject jsonCaptcha) throws JSONException {

    //     System.out.println(jsonCaptcha);
    //     mhtFields = new Hashtable<String, String>();
    //     String[] fields = JSONObject.getNames(jsonCaptcha);
    //     for (int i = 0; i < fields.length; i++) {
    //         mhtFields.put(fields[i], jsonCaptcha.getString(fields[i]));
    //     }
    // }

    // //Getters
    // public String getId() { return getField("id"); }
    // public String getCaption() { return getField("caption"); }
    // public String getField(String strField) { return mhtFields.get(strField); }
    // public String[] getFieldOrder() {
    //     int iCount = 3, j = 0;
    //     String strIntensifier = mhtFields.get("intensifier");
    //     if ((strIntensifier != null) && !strIntensifier.equals("")) iCount++;
    //     String fields[] = new String[iCount];
    //     for (int i = 0; i < FIELD_ORDER.length; i++) {
    //         if ((iCount == 3) && FIELD_ORDER[i].equals(INTENSIFIER)) continue;
    //         fields[j] = FIELD_ORDER[i];
    //         j++;
    //     }
    //     return fields;
    // }

    // public JSONObject toJSON() throws JSONException {
    //     JSONObject jsonCaptcha = new JSONObject();
    //     Set<String> fields = mhtFields.keySet();
    //     for(String field: fields) {
    //         jsonCaptcha.put(field, mhtFields.get(field));    
    //     }
    //     return jsonCaptcha;
    // }
}

