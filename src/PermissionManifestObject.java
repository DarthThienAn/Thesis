import android.Manifest.permission;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class PermissionManifestObject {
    private boolean accessCheckinPropertiesFlag;
    private boolean accessCoarseLocationFlag;
    private boolean accessFineLocationFlag;
    private boolean accessLocationExtraCommandsFlag;
    private boolean accessMockLocationFlag;
    private boolean accessNetworkStateFlag;
    private boolean accessSurfaceFlingerFlag;
    private boolean accessWifiStateFlag;
    private boolean accountManagerFlag;
    private boolean addVoicemailFlag;
    private boolean authenticateAccountsFlag;
    private boolean batteryStatsFlag;
    private boolean bindAccessibilityServiceFlag;
    private boolean bindAppWidgetFlag;
    private boolean bindDeviceAdminFlag;

    private boolean callPhoneFlag;
    private boolean callPrivilegedFlag;
    private boolean cameraFlag;

    private boolean internetFlag;

    private boolean readCalendarFlag;

    private boolean readContactsFlag;

    private boolean sendSmsFlag;

    private static final String PERMISSIONS_TAG = "<uses-permission android:name=\"";
    private static final String PERMISSIONS_TAG_END = "\"/>\n";

    public PermissionManifestObject() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (accessCheckinPropertiesFlag) sb.append(createPermission(permission.ACCESS_CHECKIN_PROPERTIES));
        if (accessCoarseLocationFlag) sb.append(createPermission(permission.ACCESS_COARSE_LOCATION));
        if (accessFineLocationFlag) sb.append(createPermission(permission.ACCESS_FINE_LOCATION));
        if (accessLocationExtraCommandsFlag) sb.append(createPermission(permission.ACCESS_LOCATION_EXTRA_COMMANDS));
        if (accessMockLocationFlag) sb.append(createPermission(permission.ACCESS_MOCK_LOCATION));
        if (accessNetworkStateFlag) sb.append(createPermission(permission.ACCESS_NETWORK_STATE));
        if (accessSurfaceFlingerFlag) sb.append(createPermission(permission.ACCESS_SURFACE_FLINGER));
        if (accessWifiStateFlag) sb.append(createPermission(permission.ACCESS_WIFI_STATE));
        if (accountManagerFlag) sb.append(createPermission(permission.ACCOUNT_MANAGER));
        if (addVoicemailFlag) sb.append(createPermission(permission.ADD_VOICEMAIL));
        if (authenticateAccountsFlag) sb.append(createPermission(permission.AUTHENTICATE_ACCOUNTS));
        if (batteryStatsFlag) sb.append(createPermission(permission.BATTERY_STATS));
        if (bindAccessibilityServiceFlag) sb.append(createPermission(permission.BIND_ACCESSIBILITY_SERVICE));
        if (bindAppWidgetFlag) sb.append(createPermission(permission.BIND_APPWIDGET));
        if (bindDeviceAdminFlag) sb.append(createPermission(permission.BIND_DEVICE_ADMIN));

        if (callPhoneFlag) sb.append(createPermission(permission.CALL_PHONE));
        if (callPrivilegedFlag) sb.append(createPermission(permission.CALL_PRIVILEGED));
        if (cameraFlag) sb.append(createPermission(permission.CAMERA));

        if (internetFlag) sb.append(createPermission(permission.INTERNET));

        if (readCalendarFlag) sb.append(createPermission(permission.READ_CALENDAR));

        if (readContactsFlag) sb.append(createPermission(permission.READ_CONTACTS));

        if (sendSmsFlag) sb.append(createPermission(permission.SEND_SMS));
        return sb.toString();
    }

    public List<Element> createListElements(Document dom) {
        List<String> permissions = createList();
        List<Element> elementList = new ArrayList<Element>();
        for (String permission : permissions) {
            Element element = dom.createElement("uses-permission");
            element.setAttribute("android:name", permission);
            elementList.add(element);
        }
        return elementList;
    }

    private List<String> createList() {
        List<String> list = new ArrayList<String>();
        if (accessCheckinPropertiesFlag) list.add(permission.ACCESS_CHECKIN_PROPERTIES);
        if (accessCoarseLocationFlag) list.add(permission.ACCESS_COARSE_LOCATION);
        if (accessFineLocationFlag) list.add(permission.ACCESS_FINE_LOCATION);
        if (accessLocationExtraCommandsFlag) list.add(permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        if (accessMockLocationFlag) list.add(permission.ACCESS_MOCK_LOCATION);
        if (accessNetworkStateFlag) list.add(permission.ACCESS_NETWORK_STATE);
        if (accessSurfaceFlingerFlag) list.add(permission.ACCESS_SURFACE_FLINGER);
        if (accessWifiStateFlag) list.add(permission.ACCESS_WIFI_STATE);
        if (accountManagerFlag) list.add(permission.ACCOUNT_MANAGER);
        if (addVoicemailFlag) list.add(permission.ADD_VOICEMAIL);
        if (authenticateAccountsFlag) list.add(permission.AUTHENTICATE_ACCOUNTS);
        if (batteryStatsFlag) list.add(permission.BATTERY_STATS);
        if (bindAccessibilityServiceFlag) list.add(permission.BIND_ACCESSIBILITY_SERVICE);
        if (bindAppWidgetFlag) list.add(permission.BIND_APPWIDGET);
        if (bindDeviceAdminFlag) list.add(permission.BIND_DEVICE_ADMIN);

        if (callPhoneFlag) list.add(permission.CALL_PHONE);
        if (callPrivilegedFlag) list.add(permission.CALL_PRIVILEGED);
        if (cameraFlag) list.add(permission.CAMERA);

        if (internetFlag) list.add(permission.INTERNET);

        if (readCalendarFlag) list.add(permission.READ_CALENDAR);

        if (readContactsFlag) list.add(permission.READ_CONTACTS);

        if (sendSmsFlag) list.add(permission.SEND_SMS);
        return list;
    }

    private String createPermission(String permissionString) {
        StringBuilder sb = new StringBuilder();
        sb.append(PERMISSIONS_TAG).append(permissionString).append(PERMISSIONS_TAG_END);
        return sb.toString();
    }

    public boolean isAccessCheckinPropertiesFlag() {
        return accessCheckinPropertiesFlag;
    }

    public void setAccessCheckinPropertiesFlag(boolean accessCheckinPropertiesFlag) {
        this.accessCheckinPropertiesFlag = accessCheckinPropertiesFlag;
    }

    public boolean isAccessCoarseLocationFlag() {
        return accessCoarseLocationFlag;
    }

    public void setAccessCoarseLocationFlag(boolean accessCoarseLocationFlag) {
        this.accessCoarseLocationFlag = accessCoarseLocationFlag;
    }

    public boolean isAccessFineLocationFlag() {
        return accessFineLocationFlag;
    }

    public void setAccessFineLocationFlag(boolean accessFineLocationFlag) {
        this.accessFineLocationFlag = accessFineLocationFlag;
    }

    public boolean isAccessLocationExtraCommandsFlag() {
        return accessLocationExtraCommandsFlag;
    }

    public void setAccessLocationExtraCommandsFlag(boolean accessLocationExtraCommandsFlag) {
        this.accessLocationExtraCommandsFlag = accessLocationExtraCommandsFlag;
    }

    public boolean isAccessMockLocationFlag() {
        return accessMockLocationFlag;
    }

    public void setAccessMockLocationFlag(boolean accessMockLocationFlag) {
        this.accessMockLocationFlag = accessMockLocationFlag;
    }

    public boolean isAccessNetworkStateFlag() {
        return accessNetworkStateFlag;
    }

    public void setAccessNetworkStateFlag(boolean accessNetworkStateFlag) {
        this.accessNetworkStateFlag = accessNetworkStateFlag;
    }

    public boolean isAccessSurfaceFlingerFlag() {
        return accessSurfaceFlingerFlag;
    }

    public void setAccessSurfaceFlingerFlag(boolean accessSurfaceFlingerFlag) {
        this.accessSurfaceFlingerFlag = accessSurfaceFlingerFlag;
    }

    public boolean isAccessWifiStateFlag() {
        return accessWifiStateFlag;
    }

    public void setAccessWifiStateFlag(boolean accessWifiStateFlag) {
        this.accessWifiStateFlag = accessWifiStateFlag;
    }

    public boolean isAccountManagerFlag() {
        return accountManagerFlag;
    }

    public void setAccountManagerFlag(boolean accountManagerFlag) {
        this.accountManagerFlag = accountManagerFlag;
    }

    public boolean isAddVoicemailFlag() {
        return addVoicemailFlag;
    }

    public void setAddVoicemailFlag(boolean addVoicemailFlag) {
        this.addVoicemailFlag = addVoicemailFlag;
    }

    public boolean isAuthenticateAccountsFlag() {
        return authenticateAccountsFlag;
    }

    public void setAuthenticateAccountsFlag(boolean authenticateAccountsFlag) {
        this.authenticateAccountsFlag = authenticateAccountsFlag;
    }

    public boolean isBatteryStatsFlag() {
        return batteryStatsFlag;
    }

    public void setBatteryStatsFlag(boolean batteryStatsFlag) {
        this.batteryStatsFlag = batteryStatsFlag;
    }

    public boolean isBindAccessibilityServiceFlag() {
        return bindAccessibilityServiceFlag;
    }

    public void setBindAccessibilityServiceFlag(boolean bindAccessibilityServiceFlag) {
        this.bindAccessibilityServiceFlag = bindAccessibilityServiceFlag;
    }

    public boolean isBindAppWidgetFlag() {
        return bindAppWidgetFlag;
    }

    public void setBindAppWidgetFlag(boolean bindAppWidgetFlag) {
        this.bindAppWidgetFlag = bindAppWidgetFlag;
    }

    public boolean isBindDeviceAdminFlag() {
        return bindDeviceAdminFlag;
    }

    public void setBindDeviceAdminFlag(boolean bindDeviceAdminFlag) {
        this.bindDeviceAdminFlag = bindDeviceAdminFlag;
    }

    public boolean isCallPhoneFlag() {
        return callPhoneFlag;
    }

    public void setCallPhoneFlag(boolean callPhoneFlag) {
        this.callPhoneFlag = callPhoneFlag;
    }

    public boolean isCallPrivilegedFlag() {
        return callPrivilegedFlag;
    }

    public void setCallPrivilegedFlag(boolean callPrivilegedFlag) {
        this.callPrivilegedFlag = callPrivilegedFlag;
    }

    public boolean isCameraFlag() {
        return cameraFlag;
    }

    public void setCameraFlag(boolean cameraFlag) {
        this.cameraFlag = cameraFlag;
    }

    public boolean isInternetFlag() {
        return internetFlag;
    }

    public void setInternetFlag(boolean internetFlag) {
        this.internetFlag = internetFlag;
    }

    public boolean isReadCalendarFlag() {
        return readCalendarFlag;
    }

    public void setReadCalendarFlag(boolean readCalendarFlag) {
        this.readCalendarFlag = readCalendarFlag;
    }

    public boolean isReadContactsFlag() {
        return readContactsFlag;
    }

    public void setReadContactsFlag(boolean readContactsFlag) {
        this.readContactsFlag = readContactsFlag;
    }

    public boolean isSendSmsFlag() {
        return sendSmsFlag;
    }

    public void setSendSmsFlag(boolean sendSmsFlag) {
        this.sendSmsFlag = sendSmsFlag;
    }
}
