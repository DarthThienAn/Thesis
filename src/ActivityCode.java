import activityobject.ActivityObject;

import java.util.ArrayList;
import java.util.List;

/* Object class that represents a program being built */
public class ActivityCode {
    String packageName;
    String className;
    Imports imports;
    List<ActivityObject> activityObjects;
    List<CustomFunction> customFunctions;

    //TODO: more convenience constructors for every case
    // constructors
    public ActivityCode(String packageName, String className, Imports imports, ArrayList<ActivityObject> activityObjects, ArrayList<CustomFunction> customFunctions) {
        this.packageName = ((packageName == null) || (packageName.isEmpty())) ? DefaultConstants.DEFAULT_PACKAGE_NAME : packageName;
        this.className = ((className == null) || (className.isEmpty())) ? DefaultConstants.DEFAULT_MAIN_ACTIVITY : className;
        this.imports = (imports == null) ? new Imports() : imports;
        this.activityObjects = (activityObjects == null) ? new ArrayList<ActivityObject>() : activityObjects;
        this.customFunctions = (customFunctions == null) ? new ArrayList<CustomFunction>() : customFunctions;
    }

    public ActivityCode() {
        this(null, null, null, null, null);
    }

    // interface methods
    public void addActivityObject(ActivityObject activityObject) {
        if (activityObjects == null) throw new RuntimeException("ActivityObjects was null");
        if (activityObject == null) throw new IllegalArgumentException("activityObject cannot be null");
        activityObjects.add(activityObject);
    }

    public List<ActivityObject> getActivityObjects() {
        return activityObjects;
    }

    public void addCustomFunction(CustomFunction customFunction) {
        if (customFunctions == null) throw new RuntimeException("CustomFunctions was null");
        if (customFunction == null) throw new IllegalArgumentException("CustomFunction cannot be null");
        customFunctions.add(customFunction);
    }

    public CustomFunction removeCustomFunction(int index) {
        if (customFunctions == null) throw new RuntimeException("CustomFunctions was null");
        return customFunctions.remove(index);
    }

    public List<CustomFunction> getCustomFunctions() {
        return customFunctions;
    }

    public boolean moveUp(int index) {
        if (activityObjects == null) throw new RuntimeException("ActivityObjects was null");
        if (index < 0) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index > (activityObjects.size() - 1)) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index == 0) return false;
        ActivityObject o = activityObjects.remove(index);
        activityObjects.add(index - 1, o);
        return true;
    }

    public boolean moveDown(int index) {
        if (activityObjects == null) throw new RuntimeException("ActivityObjects was null");
        if (index < 0) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index > (activityObjects.size() - 1)) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index == (activityObjects.size() - 1)) return false;
        ActivityObject o = activityObjects.remove(index);
        activityObjects.add(index + 1, o);
        return true;
    }

    public ActivityObject remove(int index) {
        if (activityObjects == null) throw new RuntimeException("ActivityObjects was null");
        if (index < 0) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index > (activityObjects.size() - 1)) throw new IndexOutOfBoundsException("Index out of bounds");
        return activityObjects.remove(index);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String printImports() {
        return imports.toString();
    }

    public String printFunctions() {
        return customFunctions.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

//        sb.append("\n");
        // package name
        sb.append("package ").append(packageName).append(";\n\n");
        // imports
        sb.append(imports.toString());
        // class header line
        sb.append("public class ").append(className).append(" extends Activity {\n");

        sb.append("\n");
        // declare the activityObjects as instance variables
        for (ActivityObject activityObject : activityObjects) {
            sb.append(activityObject.printDeclaration());
        }
        sb.append("\n");

        // onCreate header
        sb.append("\t@Override\n");
        sb.append("\tprotected void onCreate(Bundle savedInstanceState) {\n");
        sb.append("\t\tsuper.onCreate(savedInstanceState);\n");
        sb.append("\n");
        // get the rootView
        sb.append("\t\tLinearLayout rootView = new LinearLayout(this);\n");
        sb.append("\t\taddContentView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));\n\n");
        sb.append("\t\trootView.setOrientation(LinearLayout.VERTICAL);\n\n");
//        sb.append("\t\tViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);\n\n");

        // parse through activityObjects and add them to the code
        for (ActivityObject activityObject : activityObjects) {
            // create the object
            sb.append(activityObject.print());
            // add the object the root view
            sb.append("\t\trootView.addView(").append(activityObject.getObjectName()).append(");\n\n");
        }

        // closing onCreate brace
        sb.append("\t}\n");

        for (CustomFunction function : customFunctions) {
            // print the custom function
            sb.append("\n");
            sb.append(function.toString());
        }

        // closing class brace
        sb.append("}");

        return sb.toString();
    }

    public String getDefaultObjectName() {
        return "widget" + (activityObjects.size() + 1);
    }

    public void setImportFlag(Imports.ImportType type, boolean value) {
        imports.setFlag(type, value);
    }

    public void addCustomImport(String customImport) {
        imports.addCustomImport(customImport);
    }
}
