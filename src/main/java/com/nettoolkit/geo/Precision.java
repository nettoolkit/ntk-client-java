package com.nettoolkit.geo;

public enum Precision {
    ROOFTOP("rooftop"),
    INTERPOLATED("interpolated"),
    STREET("street"),
    GEOMETRY_CENTROID("geometry_centroid"),
    UNKNOWN("unknown");

    private String mstrStringValue;

    Precision(String strStringValue) {
        mstrStringValue = strStringValue;
    }

    public static Precision fromString(String strValue) {
        // Attempt to parse value as a known string value (e.g. "rooftop", "geometry_centroid").
        for (Precision precision : values()) {
            if (precision.mstrStringValue.equals(strValue)) {
                return precision;
            }
        }
        // Attempt to parse value as an enum string (e.g. "ROOFTOP", "GEOMETRY_CENTROID").
        try {
            return valueOf(strValue);
        } catch (Exception e) {
            // An exception here means that this is not a known precision value. Instead of throwing
            // an error, we return UNKNOWN since this could be a newly added value on the server.
            return UNKNOWN;
        }
    }
}

