/* Object class that keeps track of which imports we need */
public class Imports {
    // default flags automatically set
    // TODO: alternatively don't have flags for these and just put them in no matter what?
    private boolean activityFlag = true;
    private boolean bundleFlag = true;
    private boolean viewgroupFlag = true;
    private boolean buttonFlag;
    private boolean textviewFlag;
    private boolean edittextFlag;

    private static final String ACTIVITY_IMPORT_STRING = "import android.app.Activity;\n";
    private static final String BUNDLE_IMPORT_STRING = "import android.os.Bundle;\n";
    private static final String VIEWGROUP_IMPORT_STRING = "import android.view.ViewGroup;\n";
    private static final String BUTTON_IMPORT_STRING = "import android.widget.Button;\n";
    private static final String TEXTVIEW_IMPORT_STRING = "import android.widget.TextView;\n";
    private static final String EDITTEXT_IMPORT_STRING = "import android.widget.EditText;\n";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (activityFlag) sb.append(ACTIVITY_IMPORT_STRING);
        if (bundleFlag) sb.append(BUNDLE_IMPORT_STRING);
        if (viewgroupFlag) sb.append(VIEWGROUP_IMPORT_STRING);
        if (buttonFlag) sb.append(BUTTON_IMPORT_STRING);
        if (textviewFlag) sb.append(TEXTVIEW_IMPORT_STRING);
        if (edittextFlag) sb.append(EDITTEXT_IMPORT_STRING);
        sb.append("\n");
        return sb.toString();
    }

    public void setActivityFlag(boolean activityFlag) {
        this.activityFlag = activityFlag;
    }

    public void setBundleFlag(boolean bundleFlag) {
        this.bundleFlag = bundleFlag;
    }

    public void setViewgroupFlag(boolean viewgroupFlag) {
        this.viewgroupFlag = viewgroupFlag;
    }

    public void setButtonFlag(boolean buttonFlag) {
        this.buttonFlag = buttonFlag;
    }

    public void setTextviewFlag(boolean textviewFlag) {
        this.textviewFlag = textviewFlag;
    }

    public void setEdittextFlag(boolean edittextFlag) {
        this.edittextFlag = edittextFlag;
    }
}
