package activityobject;

public class ContactsListActivityObject extends ActivityObject {

    private boolean hasName, hasNumber;
    private String divider;

    public ContactsListActivityObject(String objectName, String height, String width, String action, boolean hasName, boolean hasNumber, String divider) {
        super(ActivityObjectType.CONTACTSLIST, objectName);
        this.height = ((height == null) || (height.isEmpty())) ? DEFAULT_HEIGHT : height;
        this.width = ((width == null) || (width.isEmpty())) ? DEFAULT_WIDTH : width;
        this.hasName = hasName;
        this.hasNumber = hasNumber;
        this.divider = ((divider == null) || (divider.isEmpty())) ? DEFAULT_DIVIDER : divider;
        this.action = action;
    }

    public ContactsListActivityObject(String objectName) {
        this(objectName, null, null, null, true, true, null);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t\tCursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {\n")
                .append("\t\t\tContactsContract.CommonDataKinds.Phone._ID,\n");
        if (hasName) sb.append("\t\t\tContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,\n");
        if (hasNumber) sb.append("\t\t\tContactsContract.CommonDataKinds.Phone.NUMBER,\n");
        if (hasName) sb.append("\t\t}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + \" ASC\");\n");

        sb.append("\t\tc.moveToFirst();\n\n");

        sb.append("\t\tCursorAdapter adapter = new CursorAdapter(this, c, true) {\n")
                .append("\t\t\t@Override\n")
                .append("\t\t\tpublic View newView(Context context, Cursor cursor, ViewGroup parent) {\n")
                .append("\t\t\t\treturn new TextView(context);\n")
                .append("\t\t\t}\n")
                .append("\n")
                .append("\t\t\t@Override\n")
                .append("\t\t\tpublic void bindView(View view, Context context, Cursor c) {\n")
                .append("\t\t\t\tString name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));\n")
                .append("\t\t\t\tString number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));\n")
                .append("\t\t\t\t((TextView) view).setText(name + \"").append(divider).append("\" + number);\n")
                .append("\t\t\t}\n")
                .append("\t\t};\n");

        sb.append("\t\t").append(getObjectName()).append(" = new ListView(this);\n");

        if (width.equals("fill")) width = FILL_PARENT;
        else if (width.equals("wrap")) width = WRAP_CONTENT;
        if (height.equals("fill")) height = FILL_PARENT;
        else if (height.equals("wrap")) height = WRAP_CONTENT;

        sb.append("\t\t").append(getObjectName()).append(".setLayoutParams(new LinearLayout.LayoutParams(").append(width).append(", ").append(height).append("));\n");
        sb.append("\t\t").append(getObjectName()).append(".setAdapter(adapter);\n");
        if (action != null)
            sb.append("\t\t").append(getObjectName()).append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {\n\t\t\t@Override\n\t\t\tpublic void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n")
                    .append(action).append("\n\t\t\t}\n\t\t});\n");

        return sb.toString();
    }

    @Override
    public String printDeclaration() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tListView ").append(getObjectName()).append(";\n");
        return sb.toString();
    }
}
