package socketassignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerProtocol {

    public static String processInput(String input) {
        Pattern pattern = Pattern.compile("\t");
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        if (input.startsWith("**") && count == 5) {
            String[] parts = input.split("\t");
            int code = Integer.parseInt(parts[parts.length - 1]);
            return "Successfully accepted with code " + code;
        }
        return "INVALID PAYLOAD!";
    }
}
