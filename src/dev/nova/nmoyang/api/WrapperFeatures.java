package dev.nova.nmoyang.api;

import dev.nova.nmoyang.api.stats.SaleStatisticsType;

import java.util.UUID;

public enum WrapperFeatures {

    NAME_CHANGE_CHECK("isNameChangeAllowed",new Class[0]),
    BLOCKED_SERVERS("getBlockedServers",new Class[0]),
    SALE_STATISTICS("getSaleStatistics",new Class[]{SaleStatisticsType.class}),
    SERVICE_STATUS("getServiceStatus",new Class[]{MojangServiceType.class}),
    UUID_FROM_USERNAME("getUserUUID",new Class[]{String.class}),
    USERNAME_FROM_UUID("getName",new Class[]{UUID.class}),
    GET_USER_PROFILE("getProfile",new Class[]{UUID.class}),
    NAME_AVAILABLE("isNameAvailable",new Class[]{String.class}),
    NAME_HISTORY("getNameHistory",new Class[]{UUID.class});

    private final String methodName;
    private final Class[] classes;

    WrapperFeatures(String methodName,Class[] classes)
    {
        this.methodName = methodName;
        this.classes = classes;

    }

    public Class[] getClasses() {
        return classes;
    }

    public String getMethodName() {
        return methodName;
    }
}
