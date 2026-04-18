package file_utility;

public class Token {
    private static String token;

    public static String getToken() {
        return Token.token;
    }

    public static void setToken(String token) {
        // System.out.println("ttttttttttttttttttttttttttt" + token);
        Token.token = token;
    }
}
