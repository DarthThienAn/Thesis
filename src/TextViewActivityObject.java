public class TextViewActivityObject extends ActivityObject {

    private String text;
    private String height;
    private String width;

    private static final String DEFAULT_TEXT = "";
    private static final String DEFAULT_HEIGHT = "100";
    private static final String DEFAULT_WIDTH = "100";

    public TextViewActivityObject(String objectName, String text, String height, String width) {
        super(ActivityObjectType.BUTTON, objectName);
        this.text = ((text == null) || (text.isEmpty())) ? DEFAULT_TEXT : text;
        this.height = ((height == null) || (height.isEmpty())) ? DEFAULT_HEIGHT : height;
        this.width = ((width == null) || (width.isEmpty())) ? DEFAULT_WIDTH : width;
    }

    public TextViewActivityObject(String objectName) {
        this(objectName, null, null, null);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tTextView ").append(getObjectName()).append(" = new TextView(this);\n");
        sb.append("\t\t").append(getObjectName()).append(".setText(\"").append(text).append("\");\n");
        sb.append("\t\t").append(getObjectName()).append(".setHeight(").append(height).append(");\n");
        sb.append("\t\t").append(getObjectName()).append(".setWidth(").append(width).append(");\n");

        return sb.toString();
    }
}
