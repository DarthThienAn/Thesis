import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AndroidManifestGenerator {
    private List<ActivityManifestObject> activities;
    private String mainActivityName;
    private String packageName;
    private int versionCode;
    private String versionName;
    private String applicationName;
    private String icon;
    private PermissionManifestObject permissionManifestObject;

    private static final String MANIFEST_TAG = "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\" package=\"%s\" android:versionCode=\"%d\" android:versionName=\"%s\">\n";
    private static final String MANIFEST_END_TAG = "</manifest>\n";
    private static final String APPLICATION_TAG = "<application android:label=\"%s\" android:icon=\"%s\">\n";
    private static final String APPLICATION_END_TAG = "</application>\n";
    private static final String ACTIVITY_TAG = "<activity android:name=\"%s\">\n";
    private static final String ACTIVITY_END_TAG = "</activity>\n";

    private static final String MAIN_ACTIVITY_TAG = "<intent-filter>\n<action android:name=\"android.intent.action.MAIN\"/>\n<category android:name=\"android.intent.category.LAUNCHER\"/>\n</intent-filter>\n";

    public AndroidManifestGenerator() {
        this(DefaultConstants.DEFAULT_MAIN_ACTIVITY, DefaultConstants.DEFAULT_PACKAGE_NAME, DefaultConstants.DEFAULT_VERSION_CODE, DefaultConstants.DEFAULT_VERSION_NAME, DefaultConstants.DEFAULT_PROJECT_NAME, DefaultConstants.DEFAULT_ICON, new PermissionManifestObject());
    }

    public AndroidManifestGenerator(String mainActivityName, String packageName, int versionCode, String versionName, String applicationName, String icon, PermissionManifestObject permissionManifestObject) {
        this.mainActivityName = mainActivityName;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.applicationName = applicationName;
        this.icon = icon;
        this.permissionManifestObject = permissionManifestObject;
        activities = new ArrayList<ActivityManifestObject>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MANIFEST_TAG, packageName, versionCode, versionName));
        sb.append(permissionManifestObject.toString());
        sb.append(String.format(APPLICATION_TAG, applicationName, icon));

        for (ActivityManifestObject activity : activities) {
            sb.append(String.format(ACTIVITY_TAG, activity.getActivityName()));
            if (mainActivityName.equals(activity.getActivityName())) {
                sb.append(MAIN_ACTIVITY_TAG);
            }
            sb.append(ACTIVITY_END_TAG);
        }

        sb.append(APPLICATION_END_TAG);
        sb.append(MANIFEST_END_TAG);
        return sb.toString();
    }

    public void saveToXML(String xml) {
        Document dom;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element manifestTag = dom.createElement("manifest");
            manifestTag.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
            manifestTag.setAttribute("package", packageName);
            manifestTag.setAttribute("android:versionCode", "" + versionCode);
            manifestTag.setAttribute("android:versionName", versionName);

            // permissions tags
            permissionManifestObject.createListElements(dom);

            // create data elements and place them under root
            Element applicationTag = dom.createElement("application");
            applicationTag.setAttribute("android:label", applicationName);
            applicationTag.setAttribute("android:icon", icon);

            for (ActivityManifestObject activity : activities) {
                Element activityTag = dom.createElement("activity");
                activityTag.setAttribute("android:name", activity.getActivityName());

                if (mainActivityName.equals(activity.getActivityName())) {
                    Element intentFilterTag = dom.createElement("intent-filter");
                    Element actionTag = dom.createElement("action");
                    actionTag.setAttribute("android:name", "android.intent.action.MAIN");
                    Element categoryTag = dom.createElement("category");
                    categoryTag.setAttribute("android:name", "android.intent.category.LAUNCHER");
                    intentFilterTag.appendChild(actionTag);
                    intentFilterTag.appendChild(categoryTag);

                    activityTag.appendChild(intentFilterTag);
                }

                applicationTag.appendChild(activityTag);
            }

            manifestTag.appendChild(applicationTag);
            dom.appendChild(manifestTag);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.VERSION, "1.0");
                tr.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "AndroidManifest.xml");
//                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                dom.setXmlStandalone(true);

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public void addActivity(String activityName) {
        for (ActivityManifestObject activityManifestObject : activities) {
            if (activityManifestObject.getActivityName().equals(activityName))
                return;
        }
        activities.add(new ActivityManifestObject(activityName));
    }

    public String getMainActivityName() {
        return mainActivityName;
    }

    public void setMainActivityName(String mainActivityName) {
        this.mainActivityName = mainActivityName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PermissionManifestObject getPermissionManifestObject() {
        return permissionManifestObject;
    }

    public void setPermissionManifestObject(PermissionManifestObject permissionManifestObject) {
        this.permissionManifestObject = permissionManifestObject;
    }
}