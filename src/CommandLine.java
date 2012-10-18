import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

    public static ActivityCode activityCode;

    public static void parseButton(String text, String height, String width) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.thesis;\n");
        sb.append("import android.app.Activity;\n");
        sb.append("import android.os.Bundle;\n");
        sb.append("import android.view.ViewGroup;\n");
        sb.append("import android.widget.Button;\n");
        sb.append("\n");
        sb.append("public class BasicActivity extends Activity {\n");
        sb.append("\t@Override\n");
        sb.append("\tprotected void onCreate(Bundle savedInstanceState) {\n");
        sb.append("\t\tsuper.onCreate(savedInstanceState);\n");
        sb.append("\t\tViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);\n");
        sb.append("\t\trootView.addView(createNewButton());\n");
        sb.append("\t}\n");
        sb.append("\n");
        sb.append("\tpublic Button createNewButton() {\n");
        sb.append("\t\tButton button = new Button(this);\n");
        sb.append("\t\tbutton.setText(\"").append(text).append("\");\n");
        sb.append("\t\tbutton.setHeight(").append(height).append(");\n");
        sb.append("\t\tbutton.setWidth(").append(width).append(");\n");
        sb.append("\t\treturn button;\n");
        sb.append("\t}\n");
        sb.append("}");

        System.out.println(sb.toString());
    }

    public static void parseLine(String[] args) {
        //TODO: handle array out of bounds exception
        if (args.length < 2) {
            System.out.println("Usage: easyAndy <command> [<args>]");
            return;
        }
        // reset the activitycode
        if (args[1].equals("print")) {
            System.out.println(activityCode.toString());
        }
        else if (args[1].equals("packagename")) {
            activityCode.setPackageName(args[2]);
            System.out.println("package name set to " + args[2]);
        }
        else if (args[1].equals("classname")) {
            activityCode.setClassName(args[2]);
            System.out.println("class name set to " + args[2]);
        }
        else if (args[1].equals("reset")) {
            System.out.println("Are you sure? (Y/N)");
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = inputReader.readLine();
                if (line.toLowerCase().equals("y")) {
                    activityCode = new ActivityCode();
                    System.out.println("Activity reset was successful");
                }
                else
                    System.out.println("Activity reset cancelled");
            }
            catch (IOException e) {
                System.out.println("Activity reset was unsuccessful");
            }
        }
        //generalize this to be read from data file
        else if (args[1].equals("button")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            String text = null;
            String height = null;
            String width = null;

            //TODO: handle array out of bounds exception
            for (int i = 2; i < args.length; i++) {
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
            activityCode.addActivityObject(new ButtonActivityObject(name, text, height, width));
            System.out.println("button \"" + name + "\" added:");
        }
        else if (args[1].equals("textview")) {
            // set default object name in case it's not specified
            String name = activityCode.getDefaultObjectName();
            // initialize these to null, ButtonActivityObject should take care of null inputs
            String text = null;
            String height = null;
            String width = null;

            //TODO: handle array out of bounds exception
            for (int i = 2; i < args.length; i++) {
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
            activityCode.addActivityObject(new TextViewActivityObject(name, text, height, width));
            System.out.println("textview \"" + name + "\" added:");
        }
        else if (args[1].equals("--help")) {
            System.out.println("Available commands: \n\t\tbutton\tadds a button object to your current activity");
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("easyAndy: \'");
            sb.append(args[1]);
            sb.append("\' is not a valid command. See \'easyAndy --help\'");
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        //args[0] should be "easyAndy"

        //  open up standard input
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        //initialize default activityCode to start
        activityCode = new ActivityCode();

        while (true) {
            String input = inputReader.readLine();
            // split input by spaces
            String[] splitLine = input.split(" ");
            parseLine(splitLine);
        }
    }
}
