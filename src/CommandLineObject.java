import activityobject.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommandLineObject {

    private static final int CMD_OFFSET = 0;
    private static ActivityCode activityCode;
    private static AndroidManifestGenerator androidManifestGenerator;
    //TODO: Make these a part of ActivityCode instead?
    private static String path;
    private static String projectName;
    private static String mainActivity;
    private static String packageName;
    //TODO: this should really be a part of the ManifestGenerator
    private static PermissionManifestObject permissionManifestObject;
    private static StringBuilder commandList;
    private static ArrayList<String> classFiles;

    private static boolean recoveryMode;
    private static BufferedReader recoveryReader;
    private static String actionBodyTemp;

    public CommandLineObject() {
        //initialize default activityCode to start
        activityCode = new ActivityCode();

        //initialize command list to keep track
        commandList = new StringBuilder();
        classFiles = new ArrayList<String>();

        //initialize default androidmanifest to start
        androidManifestGenerator = new AndroidManifestGenerator();

        //initialize defaults
        path = DefaultConstants.DEFAULT_DIR_PATH;
        projectName = DefaultConstants.DEFAULT_PROJECT_NAME;
        mainActivity = DefaultConstants.DEFAULT_MAIN_ACTIVITY;
        packageName = DefaultConstants.DEFAULT_PACKAGE_NAME;
        permissionManifestObject = new PermissionManifestObject();

        addDefaultFunctions();
    }

    public void parseCmd(String input) {
        // split input by spaces
        String[] args = input.split(" ");

        if (args.length < (CMD_OFFSET + 1)) {
            print("Usage: <command> [<args>]");
        }
        // try parsing it as an global command
        else if (!parseGlobalCmd(args)) {
            // try parsing it as a application-specific command; if so, save it to log
            if (parseLocalCmd(args)) {
                commandList.append(input);
                commandList.append('\n');
                if (actionBodyTemp != null) {
                    commandList.append(actionBodyTemp);
                    actionBodyTemp = null;
                }
            }
            // command not recognized
            else {
                print(String.format(DefaultConstants.ERROR_MSG, args[CMD_OFFSET]));
            }
        }
    }

    public static boolean parseGlobalCmd(String[] args) throws ArrayIndexOutOfBoundsException {
        // prints out the current file contents
        if (args[CMD_OFFSET].equals("print")) {
            print(activityCode.toString());
        }
        // opens a saved project
        else if (args[CMD_OFFSET].equals("open")) {
            openProject(args[CMD_OFFSET + 1]);
            print("Project opened");
        }
        // saves the current project
        else if (args[CMD_OFFSET].equals("save")) {
            if (args.length < 2)
                saveProject(null);
            else
                saveProject(args[CMD_OFFSET + 1]);
            print("Project state saved");
        }
        // prints out the current import string
        else if (args[CMD_OFFSET].equals("debugimports")) {
            print(activityCode.printFunctions());
        }
        // prints out the current functions
        else if (args[CMD_OFFSET].equals("debugfunctions")) {
            print(activityCode.printImports());
        }
        // prints out the project's manifest *not exact
        else if (args[CMD_OFFSET].equals("debugmanifest")) {
//            writeManifest();
            androidManifestGenerator.setPermissionManifestObject(permissionManifestObject);
            print(androidManifestGenerator.toString());
        }
        // creates a new android project with current name, activity, package
        else if (args[CMD_OFFSET].equals("create")) {
            createProject();
            print("Project created");
        }
        // builds the project
        else if (args[CMD_OFFSET].equals("build")) {
            writeManifest();
            print("Manifest updating. Building...");
            addFile();
            buildProject();
            print("Project built");
        }
        // installs the current project onto an attached USB device
        else if (args[CMD_OFFSET].equals("install")) {
            installApplication();
            print("Application installed");
        }
        // builds and installs the current project onto an attached USB device
        else if (args[CMD_OFFSET].equals("run")) {
            writeManifest();
            addFile();
            print("Manifest updating. Building...");
            buildAndInstall();
            print("Application installed");
        }
        // reset the activitycode
        else if (args[CMD_OFFSET].equals("reset")) {
//            resetProject();
            activityCode = new ActivityCode();
            androidManifestGenerator = new AndroidManifestGenerator();
            permissionManifestObject = new PermissionManifestObject();
            commandList = new StringBuilder();
            classFiles = new ArrayList<String>();
            path = DefaultConstants.DEFAULT_DIR_PATH;
            projectName = DefaultConstants.DEFAULT_PROJECT_NAME;
            mainActivity = DefaultConstants.DEFAULT_MAIN_ACTIVITY;
            packageName = DefaultConstants.DEFAULT_PACKAGE_NAME;
            addDefaultFunctions();
        }
        else if (args[CMD_OFFSET].equals("help")) {
            if (args[CMD_OFFSET + 1].equals("path"))
                print("Usage: path <path name>");
            else if (args[CMD_OFFSET + 1].equals("projectname"))
                print("Usage: name <project name>");
            else if (args[CMD_OFFSET + 1].equals("mainclass"))
                print("Usage: main <class name>");
            else if (args[CMD_OFFSET + 1].equals("packagename"))
                print("Usage: packagename <package name>");
            else if (args[CMD_OFFSET + 1].equals("button"))
                print("Usage: button [-text this is a button] [-name myButton] [-action 0,1] <package name>");
        }
        // shows available commands
        else if (args[CMD_OFFSET].equals("--help")) {
            print(DefaultConstants.HELP_MSG);
        }
        else {
            return false;
        }
        return true;
    }

    public static boolean parseLocalCmd(String[] args) throws ArrayIndexOutOfBoundsException {
        String[] arguments = StringHelper.reconstructWithoutFirst(args);

            // sets the project path to the first argument
        if (args[CMD_OFFSET].equals("path")) {
            path = args[CMD_OFFSET + 1];
            print("Home directory updated to " + path);
        }
        // sets the project name to the first argument
        else if (args[CMD_OFFSET].equals("projectname")) {
            projectName = args[CMD_OFFSET + 1];
//            androidManifestGenerator.setApplicationName(projectName);
            print("Project name updated to " + projectName);
        }
        // sets the project main activity to the first argument
        else if (args[CMD_OFFSET].equals("mainclass")) {
            mainActivity = args[CMD_OFFSET + 1];
//            androidManifestGenerator.setMainActivityName(mainActivity);
            print("Main activity set to " + mainActivity);
        }
        // sets the project package name to the first argument
        else if (args[CMD_OFFSET].equals("packagename")) {
            packageName = args[CMD_OFFSET + 1];
            activityCode.setPackageName(packageName);
//            androidManifestGenerator.setPackageName(packageName);
            print("package name set to " + packageName);
        }

        // adds the current file to the project
        else if (args[CMD_OFFSET].equals("addfile")) {
            addFile();
//            androidManifestGenerator.addActivity(activityCode.getClassName());
            classFiles.add(activityCode.getClassName());
            print("File added to project");
        }
        // sets the current class name to the first argument
        else if (args[CMD_OFFSET].equals("classname")) {
            activityCode.setClassName(args[CMD_OFFSET + 1]);
            print("class name set to " + args[CMD_OFFSET + 1]);
        }
        // opens up console for creating a custom function
        else if (args[CMD_OFFSET].equals("customfunction")) {
//            CustomFunction customFunction = createCustomFunction();
            String name = null;
            String body = null;
            String returnType = null;
            List<Parameter> params = new ArrayList<Parameter>();
            for (int i = (CMD_OFFSET + 1); i < arguments.length; i++) {
                String[] items = arguments[i].split(" ", 2);

                if (items[0].equals("name"))
                    name = items[1];
                else if (items[0].equals("body"))
                    body = items[1];
                else if (items[0].equals("returnType"))
                    returnType = items[1];
                else if (items[0].equals("params")) {
                    String s = arguments[i].replaceAll("\n|\t", " ");
                    System.out.println(s);
                    String[] paramArgs = s.split(" ");
                    for (int j = 1; j < paramArgs.length; j += 2) {
                        params.add(new Parameter(paramArgs[j], paramArgs[j+1]));
                    }
                }
            }
            CustomFunction customFunction = new CustomFunction(name, body, returnType, params);
            activityCode.addCustomFunction(customFunction);
            print("Custom function " + customFunction.getName() + "() successfully created");
            print(customFunction.toString());
        }
        // adds the first argument as a custom import
        else if (args[CMD_OFFSET].equals("customimport")) {
            activityCode.addCustomImport(args[CMD_OFFSET + 1]);
            print("Custom import " + args[CMD_OFFSET + 1] + " successfully added");
        }
        //
        else if (args[CMD_OFFSET].equals("up")) {
            int index = Integer.parseInt(args[CMD_OFFSET + 1]);
            activityCode.moveUp(index);
            print("Object at index " + index + " moved up one.");
        }
        else if (args[CMD_OFFSET].equals("down")) {
            int index = Integer.parseInt(args[CMD_OFFSET + 1]);
            activityCode.moveDown(index);
            print("Object at index " + index + " moved down one.");
        }
        else if (args[CMD_OFFSET].equals("remove")) {
            int index = Integer.parseInt(args[CMD_OFFSET + 1]);
            ActivityObject o = activityCode.remove(index);
            print("Object " + o.getObjectName() + " removed.");
        }
        else if (args[CMD_OFFSET].equals("removefunction")) {
            int index = Integer.parseInt(args[CMD_OFFSET + 1]);
            CustomFunction o = activityCode.removeCustomFunction(index);
            print("Object " + o.getName() + " removed.");
        }

        //TODO: generalize this to be read from data file
        // add a button
        else if (args[CMD_OFFSET].equals("button")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            String text = null;
            String height = null;
            String width = null;
            String action = null;
            for (int i = (CMD_OFFSET + 1); i < arguments.length; i++) {
                String[] items = arguments[i].split(" ", 2);

                if (items[0].equals("name"))
                    name = items[1].trim();
                else if (items[0].equals("text"))
                    text = items[1].trim();
                else if (items[0].equals("height"))
                    height = items[1].trim();
                else if (items[0].equals("width"))
                    width = items[1].trim();
                else if (items[0].equals("action")) {
                    action = items[1];
                }
            }

            activityCode.addActivityObject(new ButtonActivityObject(name, text, height, width, action));
            activityCode.setImportFlag(Imports.ImportType.BUTTON, true);
            print("button \"" + name + "\" added: ");
        }
        // add a textview
        else if (args[CMD_OFFSET].equals("textview")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            String text = null;
            String height = null;
            String width = null;
            String action = null;
            for (int i = (CMD_OFFSET + 1); i < arguments.length; i++) {
                String[] items = arguments[i].split(" ", 2);

                if (items[0].equals("name"))
                    name = items[1].trim();
                else if (items[0].equals("text"))
                    text = items[1].trim();
                else if (items[0].equals("height"))
                    height = items[1].trim();
                else if (items[0].equals("width"))
                    width = items[1].trim();
                else if (items[0].equals("action")) {
                    action = items[1];
                }
            }

            activityCode.addActivityObject(new TextViewActivityObject(name, text, height, width, action));
            activityCode.setImportFlag(Imports.ImportType.TEXTVIEW, true);
            print("textview \"" + name + "\" added:");
        }
        // add an edittext
        else if (args[CMD_OFFSET].equals("edittext")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            String text = null;
            String height = null;
            String width = null;
            String hint = null;
            for (int i = (CMD_OFFSET + 1); i < arguments.length; i++) {
                String[] items = arguments[i].split(" ", 2);

                if (items[0].equals("name"))
                    name = items[1].trim();
                else if (items[0].equals("text"))
                    text = items[1].trim();
                else if (items[0].equals("height"))
                    height = items[1].trim();
                else if (items[0].equals("width"))
                    width = items[1].trim();
                else if (items[0].equals("hint"))
                    hint = items[1].trim();
            }

            activityCode.addActivityObject(new EditTextActivityObject(name, text, hint, height, width, null));
            activityCode.setImportFlag(Imports.ImportType.EDITTEXT, true);
            print("edittext \"" + name + "\" added:");
        }
        // add a contacts list
        else if (args[CMD_OFFSET].equals("contactslist")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            String height = null;
            String width = null;
            String divider = null;
            String action = null;
            boolean hasName = true;
            boolean hasNumber = true;

            for (int i = (CMD_OFFSET + 1); i < arguments.length; i++) {
                String[] items = arguments[i].split(" ", 2);

                if (items[0].equals("name"))
                    name = items[1].trim();
                else if (items[0].equals("height"))
                    height = items[1].trim();
                else if (items[0].equals("width"))
                    width = items[1].trim();
                else if (items[0].equals("divider"))
                    divider = items[1].trim();
                else if (items[0].equals("action"))
                    action = items[1];
                else if (items[0].equals("hasName"))
                    hasName = items[1].trim().equals("1");
                else if (items[0].equals("hasNumber"))
                    hasNumber = items[1].trim().equals("1");
            }

            activityCode.addActivityObject(new ContactsListActivityObject(name, height, width, action, hasName, hasNumber, divider));
            activityCode.setImportFlag(Imports.ImportType.CONTACTSLIST, true);
            permissionManifestObject.setReadContactsFlag(true);
            print("contactslist \"" + name + "\" added:");
        }
        else {
            return false;
        }
        return true;
    }

    private static void createProject() {
        String commandTemplate = "cmd /C android create project -t %s -n %s -p %s -a %s -k %s";
        String command = String.format(commandTemplate,
                DefaultConstants.DEFAULT_PROJECT_SDK,
                projectName,
                String.format("%s\\%s", path, projectName),
                mainActivity,
                packageName);
        System.out.println(command);
        try {
            Process child = Runtime.getRuntime().exec(command);
            child.waitFor();
            child.destroy();
        } catch (Exception e) {
            System.out.print(e);
            e.printStackTrace();
        }
    }

    private static void buildProject() {
        String commandTemplate = "cmd /C ant debug -f %s\\%s\\build.xml";
        String command = String.format(commandTemplate, path, projectName);
        System.out.println(command);
        try {
            Process child = Runtime.getRuntime().exec(command);
            child.getInputStream().close();
            child.getOutputStream().close();
            child.waitFor();
//            child.destroy();
        } catch (Exception e) {
            System.out.print(e);
            e.printStackTrace();
        }
    }

    private static void addFile() {
        String pathTemplate = "%s\\%s\\src\\%s\\%s.java";
        File f = new File(String.format(pathTemplate, path, projectName,
                activityCode.getPackageName().replace('.', '\\'),
                activityCode.getClassName()));
        print(f.getPath());
        try {
            FileWriter fstream = new FileWriter(f.getPath());
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(activityCode.toString());
            out.close();
            fstream.close();
//            FileOutputStream fos = new FileOutputStream(f.getPath());
//            DataOutputStream dos = new DataOutputStream(fos);
//            dos.writeUTF(activityCode.toString());
////            dos.writeChars();
//            dos.close();
//            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeManifest() {
        androidManifestGenerator = new AndroidManifestGenerator();
        androidManifestGenerator.setApplicationName(projectName);
        androidManifestGenerator.setMainActivityName(mainActivity);
        androidManifestGenerator.addActivity(mainActivity);
        androidManifestGenerator.setPackageName(packageName);
        androidManifestGenerator.addActivity(activityCode.getClassName());
        for (String s : classFiles)
            androidManifestGenerator.addActivity(s);
        androidManifestGenerator.setPermissionManifestObject(permissionManifestObject);

        String pathTemplate = "%s\\%s\\AndroidManifest.xml";
        File f = new File(String.format(pathTemplate, path, projectName));
//        print(f.getPath());

        androidManifestGenerator.saveToXML(f.getPath());
    }

    private static void installApplication() {
        String commandTemplate = "cmd /C ant installd -f %s\\%s\\build.xml";
        String command = String.format(commandTemplate, path, projectName);
        try {
            Process child = Runtime.getRuntime().exec(command);
            child.waitFor();
            child.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buildAndInstall() {
        String commandTemplate = "cmd /C ant debug install -f %s\\%s\\build.xml";
        String command = String.format(commandTemplate, path, projectName);
        System.out.println(command);
        try {
            Process child = Runtime.getRuntime().exec(command);
            child.getInputStream().close();
            child.getOutputStream().close();
            child.waitFor();
//            child.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CustomFunction createCustomFunction() {
        CustomFunction customFunction = new CustomFunction();

        BufferedReader inputReader = recoveryMode ? recoveryReader : new BufferedReader(new InputStreamReader(System.in));
        try {
            print("Enter function name: ");
            String line = inputReader.readLine();
            while (customFunction.getName() == null) {
                if (line != null) customFunction.setName(line);
                else {
                    print("Error. Name cannot be null");
                    line = inputReader.readLine();
                }
            }
            print("Enter function return type: ");
            line = inputReader.readLine();
            while (customFunction.getReturnType() == null) {
                if (line != null) customFunction.setReturnType(line);
                else {
                    print("Error. Return type cannot be null");
                    line = inputReader.readLine();
                }
            }
            print("Enter function parameters as TYPE NAME pairs, one per line, separated by spaces. Enter an empty line to stop: \nFor example \"String myParameterName\"");
            line = inputReader.readLine();
            while (line != null && !line.equals("")) {
                String[] lineArgs = line.split(" ");
                customFunction.addParameter(new Parameter(lineArgs[0], lineArgs[1]));
                line = inputReader.readLine();
            }
            print("Enter function body: \nWrite \"QUIT\" to finish");
            line = inputReader.readLine();
            StringBuilder bodySb = new StringBuilder();
            while (line != null && !line.equals("QUIT")) {
                if (line.equals(""))
                    bodySb.append("\n");
                else
                    bodySb.append(line);
                line = inputReader.readLine();
            }
            customFunction.setBody(bodySb.toString());
        }
        catch (IOException e) {
            print("custom function creation was unsuccessful");
        }

        return customFunction;
    }

    private static void resetProject() {
        print("Are you sure? This will reset all progress except for project name, package name, and main activity (Y/N)");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = inputReader.readLine().toLowerCase();
            if ((line.equals("y")) || (line.equals("yes"))) {
                activityCode = new ActivityCode();
                androidManifestGenerator = new AndroidManifestGenerator();
                permissionManifestObject = new PermissionManifestObject();
                commandList = new StringBuilder();
                print("Activity reset was successful");
            }
            else
                print("Activity reset cancelled");
        }
        catch (IOException e) {
            print("Activity reset was unsuccessful");
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }

    private static void openProject(String filename) {
        String pathTemplate = DefaultConstants.DEFAULT_SAVE_DIR_PATH + "\\%s.txt";

        try {
            FileInputStream fileInputStream = new FileInputStream(String.format(pathTemplate, filename));
            recoveryReader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));

            String line;
            recoveryMode = true;
            while ((line = recoveryReader.readLine()) != null) {
                commandList.append(line);
                commandList.append('\n');
                parseLocalCmd(line.split(" "));
            }

        } catch (Exception e) {
            print(e.toString());
            e.printStackTrace();
        }

        recoveryMode = false;
    }

    private static void saveProject(String filename) {
        String pathTemplate = DefaultConstants.DEFAULT_SAVE_DIR_PATH + "\\%s.txt";
        File dir = new File(DefaultConstants.DEFAULT_SAVE_DIR_PATH);
        if (dir.mkdirs()) {
            print("directory created");
        }

        File f = filename != null ? new File(String.format(pathTemplate, filename)) : new File(String.format(pathTemplate, projectName));
        print(f.getPath());
        try {
            FileWriter fstream = new FileWriter(f.getPath());
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(commandList.toString());
            out.close();
            fstream.close();
        } catch (Exception e) {
            print(e.toString());
            e.printStackTrace();
        }
    }

    private static String getActionBody() {
        StringBuilder bodySb = new StringBuilder();
        String actionBody = null;

        if (!recoveryMode) print("Enter body of action. Type QUIT to end");
        BufferedReader inputReader = recoveryMode ? recoveryReader : new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = inputReader.readLine();
            while (line != null && !line.equals("QUIT")) {
                if (line.equals(""))
                    line = "\n";
                bodySb.append(line);
                line = inputReader.readLine();
            }

            actionBody = bodySb.toString();
        } catch (IOException e) {
            print("Error with input");
        }

        actionBodyTemp = actionBody;
        return actionBody;
    }

    public String getString() {
        return activityCode.toString();
    }

    public String getPath() {
        return path;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPath(String s) {
        path = s;
    }

    public void setProjectName(String projectName) {
        CommandLineObject.projectName = projectName;
    }

    public void setMainActivity(String mainActivity) {
        CommandLineObject.mainActivity = mainActivity;
    }

    public void setPackageName(String s) {
        packageName = s;
    }

    public String getNextDefaultObjectName() {
        return activityCode.getDefaultObjectName();
    }

    public String getClassName() {
        return activityCode.getClassName();
    }

    public List<CustomFunction> getFunctions() {
        return activityCode.getCustomFunctions();
    }

    public List<ActivityObject> getActivityObjects() {
        return activityCode.getActivityObjects();
    }

    public static void addDefaultFunctions() {
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("double", "a"));
        params.add(new Parameter("double", "b"));
        activityCode.addCustomFunction(new CustomFunction("addition", "return a + b;", "double", params));
        activityCode.addCustomFunction(new CustomFunction("subtraction", "return a - b;", "double", params));
        activityCode.addCustomFunction(new CustomFunction("multiplication", "return a * b;", "double", params));
        activityCode.addCustomFunction(new CustomFunction("division", "return a / b;", "double", params));
        addSmsFunction();
    }

    public static void addSmsFunction() {
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("String", "number"));
        params.add(new Parameter("String", "text"));

        String body = "SmsManager sms = SmsManager.getDefault();\n" +
                "sms.sendTextMessage(number, null, text, null, null);\n";

        permissionManifestObject.setSendSmsFlag(true);
        activityCode.addCustomImport("android.telephony.SmsManager");
        activityCode.addCustomFunction(new CustomFunction("sendsms", body, "void", params));
    }
}
