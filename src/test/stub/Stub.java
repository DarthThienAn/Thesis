package test.stub;

public class Stub {

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

}
