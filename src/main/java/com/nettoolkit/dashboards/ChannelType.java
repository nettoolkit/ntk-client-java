package com.nettoolkit.dashboards;

import java.util.Objects;

public enum ChannelType {
    SIMPLE,
    SSL,
    PING,
    HTTP,
    DURATION;

    public static ChannelType fromString(String strChannelType) {
        strChannelType = Objects.requireNonNull(strChannelType).toUpperCase();
        return valueOf(strChannelType);
    }
}
