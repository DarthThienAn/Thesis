import activityobject.ButtonActivityObject;
import activityobject.ContactsListActivityObject;
import activityobject.EditTextActivityObject;
import activityobject.TextViewActivityObject;

import java.io.*;

public class CommandLine {

    private static final int CMD_OFFSET = 0;
    private static ActivityCode activityCode;
    private static AndroidManifestGenerator androidManifestGenerator;
    private static String path;
    private static String projectName;
    private static String mainActivity;
    private static String packageName;
    private static PermissionManifestObject permissionManifestObject;
    private static StringBuilder commandList;

    private static boolean recoveryMode;
    private static BufferedReader recoveryReader;
    private static String actionBodyTemp;

    public static void parseCmd(String input) {
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
            androidManifestGenerator = new AndroidManifestGenerator();
            androidManifestGenerator.setApplicationName(projectName);
            androidManifestGenerator.setMainActivityName(mainActivity);
            androidManifestGenerator.addActivity(mainActivity);
            androidManifestGenerator.setPackageName(packageName);
            writeManifest();
            print("Project created");
        }
        // builds the project
        else if (args[CMD_OFFSET].equals("build")) {
            androidManifestGenerator.addActivity(activityCode.getClassName());
            androidManifestGenerator.setPermissionManifestObject(permissionManifestObject);
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
        // reset the activitycode
        else if (args[CMD_OFFSET].equals("reset")) {
            resetProject();
        }
        else if (args[CMD_OFFSET].equals("help")) {
            if (args[CMD_OFFSET + 1].equals("path"))
                print("Usage: path <path name>");
            else if (args[CMD_OFFSET + 1].equals("name"))
                print("Usage: name <project name>");
            else if (args[CMD_OFFSET + 1].equals("main"))
                print("Usage: main <class name>");
            else if (args[CMD_OFFSET + 1].equals("package"))
                print("Usage: package <package name>");
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
        // sets the project path to the first argument
        if (args[CMD_OFFSET].equals("path")) {
            path = args[CMD_OFFSET + 1];
            print("Home directory updated to " + path);
        }
        // sets the project name to the first argument
        else if (args[CMD_OFFSET].equals("name")) {
            projectName = args[CMD_OFFSET + 1];
            androidManifestGenerator.setApplicationName(projectName);
            print("Project name updated to " + projectName);
        }
        // sets the project main activity to the first argument
        else if (args[CMD_OFFSET].equals("main")) {
            mainActivity = args[CMD_OFFSET + 1];
            androidManifestGenerator.setMainActivityName(mainActivity);
            print("Main activity set to " + mainActivity);
        }
        // sets the project package name to the first argument
        else if (args[CMD_OFFSET].equals("package")) {
            packageName = args[CMD_OFFSET + 1];
            activityCode.setPackageName(packageName);
            androidManifestGenerator.setPackageName(packageName);
            print("package name set to " + packageName);
        }
        // adds the current file to the project
        else if (args[CMD_OFFSET].equals("addfile")) {
            addFile();
            androidManifestGenerator.addActivity(activityCode.getClassName());
            print("File added to project");
        }
        // sets the current class name to the first argument
        else if (args[CMD_OFFSET].equals("classname")) {
            activityCode.setClassName(args[CMD_OFFSET + 1]);
            print("class name set to " + args[CMD_OFFSET + 1]);
        }
        // opens up console for creating a custom function
        else if (args[CMD_OFFSET].equals("customfunction")) {
            CustomFunction customFunction = createCustomFunction();
            activityCode.addCustomFunction(customFunction);
            print("Custom function " + customFunction.getName() + "() successfully created");
            print(customFunction.toString());
        }
        // adds the first argument as a custom import
        else if (args[CMD_OFFSET].equals("customimport")) {
            activityCode.addCustomImport(args[CMD_OFFSET + 1]);
            print("Custom import " + args[CMD_OFFSET + 1] + " successfully added");
        }
        // reset the activitycode
        else if (args[CMD_OFFSET].equals("reset")) {
            resetProject();
        }
        //generalize this to be read from data file
        // add a button
        else if (args[CMD_OFFSET].equals("button")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            StringBuilder textSb = new StringBuilder();
            String text = null;
            String height = null;
            String width = null;
            boolean action = false;
            String actionBody = null;

            for (int i = (CMD_OFFSET + 1); i < args.length; i++) {
                if (args[i].equals("-name")) {
                    name = args[++i];
                }
                else if (args[i].equals("-text")) {
                    if (args[i+1].charAt(0) == ('\"')) {
                        textSb.append(args[i+1].substring(1)).append(' ');
                        i++;

                        while (!args[i+1].endsWith("\"")) {
                            textSb.append(args[i+1]).append(' ');
                            i++;
                        }

                        textSb.append(args[i+1].substring(0, args[i+1].length() - 1));
                    }
                    else
                        textSb.append(args[i+1]);
                }
                else if (args[i].equals("-height"))
                    height = args[i+1];
                else if (args[i].equals("-width"))
                    width = args[i+1];
                else if (args[i].equals("-action")) {
                    if (args[i+1].equals("1"))
                        action = true;
                }
            }

            if (action) actionBody = getActionBody();

            activityCode.addActivityObject(new ButtonActivityObject(name, textSb.toString(), height, width, actionBody));
            activityCode.setImportFlag(Imports.ImportType.BUTTON, true);
            print("button \"" + name + "\" added: ");
        }
        // add a textview
        else if (args[CMD_OFFSET].equals("textview")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            String text = null;
            String height = null;
            String width = null;
            boolean action = false;
            String actionBody = null;

            for (int i = (CMD_OFFSET + 1); i < args.length; i++) {
                if (args[i].equals("-name")) {
                    name = args[i+1];
                }
                else if (args[i].equals("-text")) {
                    text = args[i+1];
                }
                else if (args[i].equals("-height"))
                    height = args[i+1];
                else if (args[i].equals("-width"))
                    width = args[i+1];
                else if (args[i].equals("-action")) {
                    if (args[i+1].equals("1"))
                        action = true;
                }
            }

            if (action) actionBody = getActionBody();

            activityCode.addActivityObject(new TextViewActivityObject(name, text, height, width, actionBody));
            activityCode.setImportFlag(Imports.ImportType.TEXTVIEW, true);
            print("textview \"" + name + "\" added:");
        }
        // add an edittext
        else if (args[CMD_OFFSET].equals("edittext")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            String text = null;
            String hint = null;
            String height = null;
            String width = null;
            boolean action = false;
            String actionBody = null;

            for (int i = (CMD_OFFSET + 1); i < args.length; i++) {
                if (args[i].equals("-name"))
                    name = args[i+1];
                else if (args[i].equals("-text"))
                    text = args[i+1];
                else if (args[i].equals("-hint"))
                    hint = args[i+1];
                else if (args[i].equals("-height"))
                    height = args[i+1];
                else if (args[i].equals("-width"))
                    width = args[i+1];
                else if (args[i].equals("-action")) {
                    if (args[i+1].equals("1"))
                        action = true;
                }
            }

            if (action) actionBody = getActionBody();

            activityCode.addActivityObject(new EditTextActivityObject(name, text, hint, height, width, actionBody));
            activityCode.setImportFlag(Imports.ImportType.EDITTEXT, true);
            print("edittext \"" + name + "\" added:");
        }
        // add a contacts list
        else if (args[CMD_OFFSET].equals("contactslist")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            String height = null;
            String width = null;
            boolean action = false;
            boolean hasName = true;
            boolean hasNumber = true;
            String divider = null;
            String actionBody = null;

            for (int i = (CMD_OFFSET + 1); i < args.length; i++) {
                if (args[i].equals("-name"))
                    name = args[i+1];
                else if (args[i].equals("-hasName"))
                    hasName = args[i+1].equals("1");
                else if (args[i].equals("-hasNumber"))
                    hasNumber = args[i+1].equals("1");
                else if (args[i].equals("-height"))
                    height = args[i+1];
                else if (args[i].equals("-width"))
                    width = args[i+1];
                else if (args[i].equals("-divider"))
                    divider = args[i+1];
                else if (args[i].equals("-action")) {
                    action = args[i+1].equals("1");
                }
            }

            if (action) actionBody = getActionBody();

            activityCode.addActivityObject(new ContactsListActivityObject(name, height, width, actionBody, hasName, hasNumber, divider));
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
        try {
            Process child = Runtime.getRuntime().exec(command);
            child.waitFor();
            child.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buildProject() {
        String commandTemplate = "cmd /C ant debug -f %s\\%s\\build.xml";
        String command = String.format(commandTemplate, path, projectName);
        try {
            Process child = Runtime.getRuntime().exec(command);
//            child.waitFor();
//            child.destroy();
        } catch (Exception e) {
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

    private static void newObject(String[] args) {
        // set default object name in case it's not specified
        String name = activityCode.getDefaultObjectName();
        // initialize these to null, ButtonActivityObject should take care of null inputs
        String text = null;
        String height = null;
        String width = null;

        for (int i = (CMD_OFFSET + 1); i < args.length; i++) {
            if (args[i].equals("-name")) {
                name = args[i+1];
            }
            else if (args[i].equals("-text")) {
                text = args[i+1];
            }
            else if (args[i].equals("-height"))
                height = args[i+1];
            else if (args[i].equals("-width"))
                width = args[i+1];
        }
        activityCode.addActivityObject(new ButtonActivityObject(name, text, height, width, null));
        activityCode.setImportFlag(Imports.ImportType.BUTTON, true);
        print("button \"" + name + "\" added: ");
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

    public static void main(String[] args) throws IOException {
        //  open up standard input
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        //initialize default activityCode to start
        activityCode = new ActivityCode();

        //initialize command list to keep track
        commandList = new StringBuilder();

        //initialize default androidmanifest to start
        androidManifestGenerator = new AndroidManifestGenerator();

        //initialize defaults
        path = DefaultConstants.DEFAULT_DIR_PATH;
        projectName = DefaultConstants.DEFAULT_PROJECT_NAME;
        mainActivity = DefaultConstants.DEFAULT_MAIN_ACTIVITY;
        packageName = DefaultConstants.DEFAULT_PACKAGE_NAME;
        permissionManifestObject = new PermissionManifestObject();

        while (true) {
            System.out.print(">");
            String input = inputReader.readLine();

            // null input typically means user has exited with ctrl+C
            if (input == null) System.exit(0);

            try {
                parseCmd(input);
            } catch (ArrayIndexOutOfBoundsException e) {
                print("Error with arguments");
                print("See \'--help\'");
            }
        }
    }
}
