package cn.edu.xmu.oomall.service.dao.bo;

public enum ServiceProviderStatus {
    APPLY(0),
    VALID(1),
    SUSPEND(2),
    BANNED(3),
    FAILED(4);

    private final int value;

    ServiceProviderStatus(int value) {
        this.value = value;
    }

    public static ServiceProviderStatus fromValue(Integer state) {
        for (ServiceProviderStatus status : ServiceProviderStatus.values()) {
            if (status.value == state) {
                return status;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
