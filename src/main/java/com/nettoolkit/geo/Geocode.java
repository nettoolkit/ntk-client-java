package com.nettoolkit.geo;

import java.util.ArrayList;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;

public class Geocode {
    private String mstrAddress;
    private String mstrHouseNumber;
    private String mstrStreet;
    private String mstrStreetName;
    private String mstrStreetNumber;
    private String mstrStreetType;
    private String mstrCity;
    private String mstrPostalCode;
    private String mstrCounty;
    private String mstrState;
    private String mstrStateCode;
    private String mstrCountry;
    private String mstrCountryCode;
    private Provider mProvider;
    private Precision mPrecision; 
    private Double mdLatitude;
    private Double mdLongitude;
    private JSONObject mjsonOriginal;

    private Geocode(
        String strAddress,
        String strHouseNumber,
        String strStreet,
        String strStreetName,
        String strStreetNumber,
        String strStreetType,
        String strCity,
        String strPostalCode,
        String strCounty,
        String strState,
        String strStateCode,
        String strCountry,
        String strCountryCode,
        Provider provider,
        Precision precision,
        Double dLatitude,
        Double dLongitude,
        JSONObject jsonOriginal
    ) {
        mstrAddress = strAddress;
        mstrHouseNumber = strHouseNumber;
        mstrStreet = strStreet;
        mstrStreetName = strStreetName;
        mstrStreetNumber = strStreetNumber;
        mstrStreetType = strStreetType;
        mstrCity = strCity;
        mstrPostalCode = strPostalCode;
        mstrCounty = strCounty;
        mstrState = strState;
        mstrStateCode = strStateCode;
        mstrCountry = strCountry;
        mstrCountryCode = strCountryCode;
        mProvider = provider;
        mPrecision = precision;
        mdLatitude = dLatitude;
        mdLongitude = dLongitude;
        mjsonOriginal = jsonOriginal;
    }

    public static Geocode fromJson(JSONObject jsonGeocode) throws JSONException {
        return new Geocode(
            jsonGeocode.optString("address", null),
            jsonGeocode.optString("house_number", null),
            jsonGeocode.optString("street", null),
            jsonGeocode.optString("street_name", null),
            jsonGeocode.optString("street_number", null),
            jsonGeocode.optString("street_type", null),
            jsonGeocode.optString("city", null),
            jsonGeocode.optString("postal_code", null),
            jsonGeocode.optString("county", null),
            jsonGeocode.optString("state", null),
            jsonGeocode.optString("state_code", null),
            jsonGeocode.optString("country", null),
            jsonGeocode.optString("country_code", null),
            Provider.fromString(jsonGeocode.optString("provider", null)),
            Precision.fromString(jsonGeocode.optString("precision", null)),
            jsonGeocode.has("latitude") ? jsonGeocode.getDouble("latitude") : null,
            jsonGeocode.has("longitude") ? jsonGeocode.getDouble("longitude") : null,
            jsonGeocode
        );
    }

    public String getAddress() { return mstrAddress; }

    public String getHouseNumber() { return mstrHouseNumber; }

    public String getStreet() { return mstrStreet; }

    public String getStreetName() { return mstrStreetName; }

    public String getStreetNumber() { return mstrStreetNumber; }

    public String getStreetType() { return mstrStreetType; }

    public String getCity() { return mstrCity; }

    public String getPostalCode() { return mstrPostalCode; }

    public String getCounty() { return mstrCounty; }

    public String getState() { return mstrState; }

    public String getStateCode() { return mstrStateCode; }

    public String getCountry() { return mstrCountry; }

    public String getCountryCode() { return mstrCountryCode; }

    public Provider getProvider() { return mProvider; }

    public Precision getPrecision() { return mPrecision; }

    public double getLatitude() { return mdLatitude; }

    public double getLongitude() { return mdLongitude; }

    public JSONObject getOriginalJson() { return mjsonOriginal; }

    @Override
    public String toString() {
        ArrayList<String> listTokens = new ArrayList<>();
        if (mstrAddress != null) {
            listTokens.add("address=" + mstrAddress);
        }
        if (mstrHouseNumber != null) {
            listTokens.add("house_number=" + mstrHouseNumber);
        }
        if (mstrStreet != null) {
            listTokens.add("street=" + mstrStreet);
        }
        if (mstrStreetName != null) {
            listTokens.add("street_name=" + mstrStreetName);
        }
        if (mstrStreetNumber != null) {
            listTokens.add("street_number=" + mstrStreetNumber);
        }
        if (mstrStreetType != null) {
            listTokens.add("street_type=" + mstrStreetType);
        }
        if (mstrCity != null) {
            listTokens.add("city=" + mstrCity);
        }
        if (mstrPostalCode != null) {
            listTokens.add("postal_code=" + mstrPostalCode);
        }
        if (mstrCounty != null) {
            listTokens.add("county=" + mstrCounty);
        }
        if (mstrState != null) {
            listTokens.add("state=" + mstrState);
        }
        if (mstrStateCode != null) {
            listTokens.add("state_code=" + mstrStateCode);
        }
        if (mProvider != null) {
            listTokens.add("provider=" + mProvider.asStringValue());
        }
        if (mPrecision != null) {
            listTokens.add("precision=" + mPrecision.asStringValue());
        }
        if (mdLatitude != null) {
            listTokens.add("latitude=" + mdLatitude);
        }
        if (mdLongitude != null) {
            listTokens.add("longitude=" + mdLongitude);
        }
        return getClass().getSimpleName() + "{"
            + String.join(", ", listTokens)
            + "}";
    }
}

