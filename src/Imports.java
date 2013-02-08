import java.util.ArrayList;
import java.util.List;

/* Object class that keeps track of which imports we need */
public class Imports {
    // default flags automatically set
    // TODO: alternatively don't have flags for these and just put them in no matter what?
    private boolean activityFlag;
    private boolean bundleFlag;

    private boolean viewFlag;
    private boolean viewgroupFlag;
    private boolean linearlayoutFlag;

    private boolean buttonFlag;
    private boolean textviewFlag;
    private boolean edittextFlag;
    private List<String> customImports;

    private static final String ACTIVITY_IMPORT_STRING = "import android.app.Activity;\n";
    private static final String BUNDLE_IMPORT_STRING = "import android.os.Bundle;\n";

    private static final String VIEW_IMPORT_STRING = "import android.view.View;\n";
    private static final String VIEWGROUP_IMPORT_STRING = "import android.view.ViewGroup;\n";
    private static final String LINEARLAYOUT_IMPORT_STRING = "import android.widget.LinearLayout;\n";

    private static final String BUTTON_IMPORT_STRING = "import android.widget.Button;\n";
    private static final String TEXTVIEW_IMPORT_STRING = "import android.widget.TextView;\n";
    private static final String EDITTEXT_IMPORT_STRING = "import android.widget.EditText;\n";

    public Imports() {
        activityFlag = true;
        bundleFlag = true;
        viewFlag = true;
        viewgroupFlag = true;
        linearlayoutFlag = true;
        customImports = new ArrayList<String>();
    }

    public enum ImportType {
        ACTIVITY,
        BUNDLE,
        VIEW,
        VIEWGROUP,
        LINEARLAYOUT,
        BUTTON,
        TEXTVIEW,
        EDITTEXT
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (activityFlag) sb.append(ACTIVITY_IMPORT_STRING);
        if (bundleFlag) sb.append(BUNDLE_IMPORT_STRING);

        if (viewFlag) sb.append(VIEW_IMPORT_STRING);
        if (viewgroupFlag) sb.append(VIEWGROUP_IMPORT_STRING);
        if (linearlayoutFlag) sb.append(LINEARLAYOUT_IMPORT_STRING);

        if (buttonFlag) sb.append(BUTTON_IMPORT_STRING);
        if (textviewFlag) sb.append(TEXTVIEW_IMPORT_STRING);
        if (edittextFlag) sb.append(EDITTEXT_IMPORT_STRING);
        for (String customImport : customImports) {
            sb.append("import ");
            sb.append(customImport);
            sb.append(";\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public void setFlag(ImportType type, boolean value) {
        if (type == ImportType.ACTIVITY)
            activityFlag = value;
        else if (type == ImportType.BUNDLE) {
            bundleFlag = value;
        }

        else if (type == ImportType.VIEW) {
            viewFlag = value;
        }
        else if (type == ImportType.VIEWGROUP) {
            viewgroupFlag = value;
        }
        else if (type == ImportType.LINEARLAYOUT) {
            linearlayoutFlag = value;
        }

        else if (type == ImportType.BUTTON) {
            buttonFlag = value;
        }
        else if (type == ImportType.TEXTVIEW) {
            textviewFlag = value;
        }
        else if (type == ImportType.EDITTEXT) {
            edittextFlag = value;
        }
    }

    public void addCustomImport(String importPackage) {
        customImports.add(importPackage);
    }

    public void setActivityFlag(boolean activityFlag) {
        this.activityFlag = activityFlag;
    }

    public void setBundleFlag(boolean bundleFlag) {
        this.bundleFlag = bundleFlag;
    }

    public void setViewFlag(boolean viewFlag) {
        this.viewFlag = viewFlag;
    }

    public void setViewgroupFlag(boolean viewgroupFlag) {
        this.viewgroupFlag = viewgroupFlag;
    }

    public void setLinearlayoutFlag(boolean linearlayoutFlag) {
        this.linearlayoutFlag = linearlayoutFlag;
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