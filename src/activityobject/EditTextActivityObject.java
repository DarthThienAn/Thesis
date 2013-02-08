package activityobject;

public class EditTextActivityObject extends ActivityObject {

    private String hint;

    public EditTextActivityObject(String objectName, String text, String hint, String height, String width, String action) {
        super(ActivityObjectType.EDITTEXT, objectName);
        this.text = ((text == null) || (text.isEmpty())) ? DEFAULT_TEXT : text;
        this.hint = ((hint == null) || (hint.isEmpty())) ? DEFAULT_HINT : hint;
        this.height = ((height == null) || (height.isEmpty())) ? DEFAULT_HEIGHT : height;
        this.width = ((width == null) || (width.isEmpty())) ? DEFAULT_WIDTH : width;
        this.action = action;
    }

    public EditTextActivityObject(String objectName) {
        this(objectName, null, null, null, null, null);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tfinal EditText ").append(getObjectName()).append(" = new EditText(this);\n");
        sb.append(addBasics());
        sb.append("\t\t").append(getObjectName()).append(".setHint(\"").append(hint).append("\");\n");
        return sb.toString();
    }
}
