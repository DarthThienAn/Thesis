public class DefaultConstants {
    public static String DEFAULT_PACKAGE_NAME = "com.example";
    public static int DEFAULT_VERSION_CODE = 1;
    public static String DEFAULT_VERSION_NAME = "1.0";
    public static String DEFAULT_ICON = "@drawable/ic_launcher";

    public static String DEFAULT_PROJECT_NAME = "helloworld";
    public static String DEFAULT_PROJECT_SDK = "27";
    public static String DEFAULT_DIR_PATH = "C:\\easyandroid";
    public static String DEFAULT_SAVE_DIR_PATH = "C:\\easyandroid\\.saves";
    public static String DEFAULT_MAIN_ACTIVITY = "MyActivity";

    public static String ERROR_MSG = "Error: \'%s\' is not a valid command. See \'--help\'\nUsage: <command> [<args>]";
    public static String HELP_MSG =
            "\t\tprint: \tprints out the current file contents\n" +
            "\t\tcreate: \tcreates a new android project with current name, activity, package\n" +
            "\t\tbuild: \tbuilds the project\n" +
            "\t\tinstall: \tinstalls the current project onto an attached USB device\n" +
            "\t\tdebugimports: \tprints out the current import string\n" +
            "\t\tdebugmanifest: \tprints out the project's manifest *not exact\n" +
            "\n" +
            "\t\tpath: \tsets the project path to the first argument\n" +
            "\t\tname: \tsets the project name to the first argument\n" +
            "\t\tmain: \tsets the project main activity to the first argument\n" +
            "\t\tpackage: \tsets the project package name to the first argument\n" +
            "\t\taddfile: \tadds the current file to the project\n" +
            "\t\tclassname: \tsets the current class name to the first argument\n" +
            "\t\tcustomfunction: \topens up console for creating a custom function\n" +
            "\t\tcustomimport: \tadds the first argument as a custom import\n" +
            "\t\treset: \treset the activitycode\n" +
            "\n" +
            "\t\tbutton: \tadd a button\n" +
            "\t\ttextview: \tadd a textview\n" +
            "\t\tedittext: \tadd an edittext\n" +
            "\t\tcontactlist: \tadd a scrolling list of phone's contacts\n" +
            "\n" +
            "\t\tsee help <command> for more information on a specific command";

}
