package com.thesis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BasicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getRootView here instead of findViewbyId would give PhoneWindow$DecorView
        //getRootView gives a FrameLayout
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        Log.d("THESIS", getWindow().getDecorView().getRootView().toString());
        Log.d("THESIS", rootView.toString());
//        rootView.setMinimumHeight(getWindowManager().getDefaultDisplay().getHeight());
//        rootView.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth());
        rootView.addView(createNewButton());
        rootView.addView(getTextView());
    }


    public Button createNewButton() {
        Button button = new Button(this);
        button.setText("Button");
//        button.setLayoutParams(new FrameLayout.LayoutParams(360, 480));
        button.setHeight(100);
        button.setWidth(100);
        Log.d("THESIS", button.toString());
        return button;
    }

    public TextView getTextView() {
        TextView textView = new TextView(this);
        textView.setHeight(360);
        textView.setWidth(480);
        textView.setTextColor(Color.BLUE);
        textView.setText("TextView");

        return textView;
    }

    public static TextView getTextViewStatic(Context context) {
        TextView textView = new TextView(context);
        textView.setHeight(360);
        textView.setWidth(480);

        return textView;
    }

    // --access [public|private|protected]
    //

    public static void parseButton(String text, String height, String width) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.thesis;\n");
        sb.append("import android.app.Activity;\n");
        sb.append("import android.os.Bundle;;\n");
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
        sb.append("\t\tbutton.setText(\"" + text + "\");\n");
        sb.append("\t\tbutton.setHeight(" + height + ");\n");
        sb.append("\t\tbutton.setWidth(" + width + ");\n");
        sb.append("\t\treturn button;\n");
        sb.append("\t}\n");
        sb.append("}");

        System.out.println(sb.toString());
    }

    public static void parseLine(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: easyAndy <command> [<args>]");
            return;
        }
        //generalize this to be read from data file
        if (args[1].equals("button")) {
            String text = "";
            String height = "";
            String width = "";

            //TODO: handle array out of bounds exception
            for (int i = 2; i < args.length; i++) {
                if (args[i].equals("-text")) {
                    text = args[i+1];
                }
                else if (args[i].equals("-height"))
                    height = args[i+1];
                else if (args[i].equals("-width"))
                    width = args[i+1];
            }
            parseButton(text, height, width);
        }
        else if (args[1].equals("--help")) {
            System.out.println("No help documentation available yet");
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

        while (true) {
            String input = inputReader.readLine();
            // split input by spaces
            String[] splitLine = input.split(" ");
            parseLine(splitLine);
        }
    }
}
