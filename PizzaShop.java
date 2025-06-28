import java.util.Scanner;

public class PizzaShop {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Pizza Shop Admin System ===");

        // Directly prompt for admin login
        if (AdminLogin.authenticate()) {
            AdminConsole.start();
        } else {
            System.out.println("Login failed. Exiting system.");
        }
    }
}