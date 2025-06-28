import java.util.*;

public class UserLogin {
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "user123";
    private static Scanner scanner = new Scanner(System.in);

    public static void start() {
        System.out.println("\nUser Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            UserConsole.start();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static boolean authenticate(String username, String password) {
        return username.equals(USER_USERNAME) && password.equals(USER_PASSWORD);
    }
}