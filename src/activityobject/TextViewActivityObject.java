package activityobject;

public class TextViewActivityObject extends ActivityObject {

    public TextViewActivityObject(String objectName, String text, String height, String width, String action) {
        super(ActivityObjectType.TEXTVIEW, objectName);
        this.text = ((text == null) || (text.isEmpty())) ? DEFAULT_TEXT : text;
        this.height = ((height == null) || (height.isEmpty())) ? DEFAULT_HEIGHT : height;
        this.width = ((width == null) || (width.isEmpty())) ? DEFAULT_WIDTH : width;
        this.action = action;
    }

    public TextViewActivityObject(String objectName) {
        this(objectName, null, null, null, null);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t").append(getObjectName()).append(" = new TextView(this);\n");
        sb.append(addBasics());
        return sb.toString();
    }

    @Override
    public String printDeclaration() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tTextView ").append(getObjectName()).append(";\n");
        return sb.toString();
    }
}
