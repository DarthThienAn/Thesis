import java.util.ArrayList;
import java.util.List;

public class StringHelper {
    public static boolean isNullorEmpty (String s) {
        return (s == null || s.isEmpty());
    }

    public static String[] reconstructWithoutFirst(String[] s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length; i++) {
            sb.append(s[i]).append(" ");
        }
        String fullString = sb.toString();
        return fullString.split("-");
    }

    public static List<Parameter> stringToParams(String s) {
        List<Parameter> params = new ArrayList<Parameter>();
        String[] paramArgs = s.split(" ");
        if (paramArgs.length < 2) return null;
        for (int j = 0; j < paramArgs.length; j += 2) {
            params.add(new Parameter(paramArgs[j], paramArgs[j+1]));
        }
        return params;
    }
}
