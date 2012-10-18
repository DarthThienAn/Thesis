public abstract class ActivityObject {
    private ActivityObjectType type;
    private String objectName;

    public ActivityObject(ActivityObjectType type, String objectName) {
        this.type = type;
        this.objectName = objectName;
    }

    public ActivityObjectType getType() {
        return type;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public abstract String print();
}
