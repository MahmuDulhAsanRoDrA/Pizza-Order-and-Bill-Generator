import java.util.*;

public class AdminLogin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static Scanner scanner = new Scanner(System.in);

    public static boolean authenticate() {
        System.out.println("\nAdmin Login Required");
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                return true;
            }

            attempts--;
            System.out.println("Invalid UserName or Password" );
        }

        return false;
    }


}
