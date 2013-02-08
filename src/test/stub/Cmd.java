package test.stub;

import java.io.*;

public class Cmd {

    public static void main (String[] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String input = inputReader.readLine();
            try {
                Process child = Runtime.getRuntime().exec(input);
//                child.destroy();
//                System.out.println(child.exitValue());
                InputStream is = child.getErrorStream();
//                OutputStream os = child.getOutputStream();
//                PrintStream ps = new PrintStream(os);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                try {
                    System.out.println(child.waitFor());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                br.close();
//                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        String command = "cmd cd C:/Users/Mark/android-sdks/tools";
//        String command2 = "./android create project -t 27 -n helloworld -p ./src/helloworld -a MyActivity -k com.thesis";
    }
}
