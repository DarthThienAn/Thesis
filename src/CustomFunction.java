import java.util.ArrayList;
import java.util.List;

public class CustomFunction {
    private String name;
    private String body;
    private String returnType;
    private List<Parameter> parameters;

    public CustomFunction(String name, String body, String returnType, List<Parameter> parameters) {
        this.name = name;
        this.body = body;
        this.returnType = returnType;
        this.parameters = (parameters == null) ? new ArrayList<Parameter>() : parameters;
    }

    public CustomFunction() {
        this(null, null, null, null);
    }

    // interface methods
    public void addParameter(Parameter parameter) {
        if (parameters == null) throw new RuntimeException("Paramaters was null");
        if (parameter == null) throw new IllegalArgumentException("Paramater cannot be null");
        parameters.add(parameter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // function header
        sb.append("\tprivate ").append(returnType).append(" ").append(name).append("(");

        // parameters
        for (int i = 0; i < parameters.size() - 1; i++) {
            sb.append(parameters.get(i).getType());
            sb.append(" ");
            sb.append(parameters.get(i).getName());
            sb.append(", ");
        }
        if (parameters.size() > 0) {
            sb.append(parameters.get(parameters.size() - 1).getType());
            sb.append(" ");
            sb.append(parameters.get(parameters.size() - 1).getName());
        }
        sb.append(") {\n");

        // body
        sb.append(body);

        // closing function brace
        sb.append("\n\t}\n");

        return sb.toString();
    }
}
