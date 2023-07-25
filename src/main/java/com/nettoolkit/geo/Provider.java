package com.nettoolkit.geo;

public enum Provider {
    NAD("nad"),
    OPENADDRESSES("openaddresses"),
    OPENSTREETMAP("openstreetmap"),
    POSTGIS("postgis"),
    REGRID("regrid"),
    TIGER("tiger"),
    UNKNOWN("unknown");

    private String mstrStringValue;

    Provider(String strStringValue) {
        mstrStringValue = strStringValue;
    }

    public String asStringValue() { return mstrStringValue; }

    public static Provider fromString(String strValue) {
        // Attempt to parse value as a known string value (e.g. "nad", "openstreetmap").
        for (Provider provider : values()) {
            if (provider.mstrStringValue.equals(strValue)) {
                return provider;
            }
        }
        // Attempt to parse value as an enum string (e.g. "NAD", "OPENSTREETMAP").
        try {
            return valueOf(strValue);
        } catch (Exception e) {
            // An exception here means that this is not a known provider value. Instead of throwing
            // an error, we return UNKNOWN since this could be a newly added value on the server.
            return UNKNOWN;
        }
    }
}


