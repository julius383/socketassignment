package socketassignment;

public class ClientProtocol {
    public static int parse(String response) {
        if (response.startsWith("Successfully accepted with code")) {
            return 0;
        } else {
            return 1;
        }
    }
}
