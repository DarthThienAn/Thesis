package activityobject;

public abstract class ActivityObject {
    private ActivityObjectType type;
    private String objectName;

    protected String text;
    protected String height;
    protected String width;
    protected String action;

    protected static final String FILL_PARENT = "ViewGroup.LayoutParams.FILL_PARENT";
    protected static final String WRAP_CONTENT = "ViewGroup.LayoutParams.WRAP_CONTENT";

    protected static final String DEFAULT_TEXT = "";
    protected static final String DEFAULT_HINT = "";
    protected static final String DEFAULT_HEIGHT = WRAP_CONTENT;
    protected static final String DEFAULT_WIDTH = WRAP_CONTENT;
    protected static final String DEFAULT_DIVIDER = ": ";

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

    public abstract String printDeclaration();

    protected String addBasics() {
        StringBuilder sb = new StringBuilder();
//        sb.append("\t\tfinal TextView ").append(getObjectName()).append(" = new TextView(this);\n");
        sb.append("\t\t").append(getObjectName()).append(".setText(\"").append(text).append("\");\n");

        if (width.equals("fill")) width = FILL_PARENT;
        else if (width.equals("wrap")) width = WRAP_CONTENT;
        if (height.equals("fill")) height = FILL_PARENT;
        else if (height.equals("wrap")) height = WRAP_CONTENT;

//        sb.append("\t\t").append(getObjectName()).append(".setHeight(").append(height).append(");\n");
//        sb.append("\t\t").append(getObjectName()).append(".setWidth(").append(width).append(");\n");
        sb.append("\t\t").append(getObjectName()).append(".setLayoutParams(new LinearLayout.LayoutParams(").append(width).append(", ").append(height).append("));\n");
        if (action != null)
            sb.append("\t\t").append(getObjectName()).append(".setOnClickListener(new View.OnClickListener() {\n\t\t\t@Override\n\t\t\tpublic void onClick(View view) {\n")
                    .append(action).append("\n\t\t\t}\n\t\t});\n");

        return sb.toString();
    }
}
