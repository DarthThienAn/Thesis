import java.util.ArrayList;

/* Object class that represents a program being built */
public class ActivityCode {
    String packageName;
    String className;
    Imports imports;
    ArrayList<ActivityObject> activityObjects;

    private static final String DEFAULT_PACKAGE_NAME = "com.example";
    private static final String DEFAULT_CLASS_NAME = "MyActivity";

    //TODO: more constructors for every case
    // constructors
    public ActivityCode(String packageName, String className, Imports imports, ArrayList<ActivityObject> activityObjects) {
        this.packageName = ((packageName == null) || (packageName.isEmpty())) ? DEFAULT_PACKAGE_NAME : packageName;
        this.className = ((className == null) || (className.isEmpty())) ? DEFAULT_CLASS_NAME : className;
        this.imports = (imports == null) ? new Imports() : imports;
        this.activityObjects = (activityObjects == null) ? new ArrayList<ActivityObject>() : activityObjects;
    }

    public ActivityCode() {
        this(null, null, null, null);
    }

    // interface methods
    public void addActivityObject(ActivityObject activityObject) {
        if (activityObjects == null) throw new RuntimeException("ActivityObjects was null");
        if (activityObject == null) throw new IllegalArgumentException("activityObject cannot be null");
        activityObjects.add(activityObject);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        // package name
        sb.append("package ").append(packageName).append(";\n\n");
        // imports
        sb.append(imports.toString());
        // class header line
        sb.append("public class ").append(className).append(" extends Activity {\n");
        // onCreate header
        sb.append("\t@Override\n");
        sb.append("\tprotected void onCreate(Bundle savedInstanceState) {\n");
        sb.append("\t\tsuper.onCreate(savedInstanceState);\n");
        sb.append("\n");
        // get the rootView
        sb.append("\t\tViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);\n\n");
        // parse through activityObjects and add them to the code
        for (ActivityObject activityObject : activityObjects) {
            // create the object
            sb.append(activityObject.print());
            // add the object the root view
            sb.append("\t\trootView.addView(").append(activityObject.getObjectName()).append(");\n\n");
        }

        // closing onCreate brace
        sb.append("\t}\n");
        // closing class brace
        sb.append("}");

        return sb.toString();
    }

    public String getDefaultObjectName() {
        return "object" + (activityObjects.size() + 1);
    }
}
