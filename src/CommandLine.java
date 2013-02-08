import activityobject.ButtonActivityObject;
import activityobject.EditTextActivityObject;
import activityobject.TextViewActivityObject;

import java.io.*;

public class CommandLine {

    public static final int CMD_OFFSET = 0;
    public static ActivityCode activityCode;
    public static AndroidManifestGenerator androidManifestGenerator;
    public static String path;
    public static String projectName;
    public static String mainActivity;
    public static String packageName;
    public static PermissionManifestObject permissionManifestObject;

    public static void parseLine(String[] args) throws ArrayIndexOutOfBoundsException {
        if (args.length < (CMD_OFFSET + 1)) {
            print("Usage: <command> [<args>]");
            return;
        }
        // prints out the current file contents
        if (args[CMD_OFFSET].equals("print")) {
            print(activityCode.toString());
        }
        // prints out the current import string
        else if (args[CMD_OFFSET].equals("debugimports")) {
            print(activityCode.printImports());
        }
        // prints out the project's manifest *not exact
        else if (args[CMD_OFFSET].equals("debugmanifest")) {
//            writeManifest();
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
            writeManifest();
            print("Manifest updating. Building...");
            buildProject();
            print("Project built");
        }
        // installs the current project onto an attached USB device
        else if (args[CMD_OFFSET].equals("install")) {
            installApplication();
            print("Application installed");
        }
        // sets the project path to the first argument
        else if (args[CMD_OFFSET].equals("path")) {
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

            if (action) {
                print("Enter body of action. Type QUIT to end");
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line = inputReader.readLine();
                    StringBuilder bodySb = new StringBuilder();
                    while (line != null && !line.equals("QUIT")) {
                        if (line.equals(""))
                            bodySb.append("\n");
                        else
                            bodySb.append(line);
                        line = inputReader.readLine();
                    }

                    actionBody = bodySb.toString();
                } catch (IOException e) {
                    print("Error with input");
                }
            }

            activityCode.addActivityObject(new ButtonActivityObject(name, text, height, width, actionBody));
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

            if (action) {
                print("Enter body of action. Type QUIT to end");
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line = inputReader.readLine();
                    StringBuilder bodySb = new StringBuilder();
                    while (line != null && !line.equals("QUIT")) {
                        if (line.equals(""))
                            bodySb.append("\n");
                        else
                            bodySb.append(line);
                        line = inputReader.readLine();
                    }

                    actionBody = bodySb.toString();
                } catch (IOException e) {
                    print("Error with input");
                }
            }

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

            if (action) {
                print("Enter body of action. Type QUIT to end");
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line = inputReader.readLine();
                    StringBuilder bodySb = new StringBuilder();
                    while (line != null && !line.equals("QUIT")) {
                        if (line.equals(""))
                            bodySb.append("\n");
                        else
                            bodySb.append(line);
                        line = inputReader.readLine();
                    }

                    actionBody = bodySb.toString();
                } catch (IOException e) {
                    print("Error with input");
                }
            }

            activityCode.addActivityObject(new EditTextActivityObject(name, text, hint, height, width, actionBody));
            activityCode.setImportFlag(Imports.ImportType.EDITTEXT, true);
            print("edittext \"" + name + "\" added:");
        }
        // add a contacts list
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

            if (action) {
                print("Enter body of action. Type QUIT to end");
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line = inputReader.readLine();
                    StringBuilder bodySb = new StringBuilder();
                    while (line != null && !line.equals("QUIT")) {
                        if (line.equals(""))
                            bodySb.append("\n");
                        else
                            bodySb.append(line);
                        line = inputReader.readLine();
                    }

                    actionBody = bodySb.toString();
                } catch (IOException e) {
                    print("Error with input");
                }
            }

            activityCode.addActivityObject(new EditTextActivityObject(name, text, hint, height, width, actionBody));
            activityCode.setImportFlag(Imports.ImportType.EDITTEXT, true);
            print("edittext \"" + name + "\" added:");
        }
        // shows available commands
        else if (args[CMD_OFFSET].equals("--help")) {
//            print("Available commands: \n\t\tbutton\tadds a button object to your current activity");
            print("\t\tprint: \tprints out the current file contents\n" +
                    "\t\tmanifest: \tprints out the project's manifest *not exact\n" +
                    "\t\tcreate: \tcreates a new android project with current name, activity, package\n" +
                    "\t\tbuild: \tbuilds the project\n" +
                    "\t\tinstall: \tinstalls the current project onto an attached USB device\n" +
                    "\t\tpath: \tsets the project path to the first argument\n" +
                    "\t\tname: \tsets the project name to the first argument\n" +
                    "\t\tmain: \tsets the project main activity to the first argument\n" +
                    "\t\tpackage: \tsets the project package name to the first argument\n" +
                    "\t\taddfile: \tadds the current file to the project\n" +
                    "\t\tclassname: \tsets the current class name to the first argument\n" +
                    "\t\tcustomfunction: \topens up console for creating a custom function\n" +
                    "\t\treset: \treset the activitycode\n" +
                    "\n" +
                    "\t\tbutton: \tadd a button\n" +
                    "\t\ttextview: \tadd a textview" +
                    "\t\tedittext: \tadd a edittext");
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("Error: \'");
            sb.append(args[CMD_OFFSET]);
            sb.append("\' is not a valid command. See \'--help\'");
            sb.append("\nUsage: <command> [<args>]");
            print(sb.toString());
        }
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
        File f = new File(String.format(pathTemplate, path,projectName,
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
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
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

    public static void main(String[] args) throws IOException {
        //  open up standard input
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        //initialize default activityCode to start
        activityCode = new ActivityCode();

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

            // split input by spaces
            String[] splitLine = input.split(" ");
            try {
                parseLine(splitLine);
            } catch (ArrayIndexOutOfBoundsException e) {
                print("Error with arguments");
            }
        }
    }
}
