
import java.util.*;

public class UserConsole {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Pizza> availablePizzas = AdminConsole.getAvailablePizzas();
    private static List<String> availableSizes = AdminConsole.getAvailableSizes();
    private static List<Topping> availableToppings = AdminConsole.getAvailableToppings();

    public static void start() {
        System.out.println("\nWelcome to Pizza Ordering System");
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Place Order");
            System.out.println("2. Confirm Delivery");
            System.out.println("3. View Order Status");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    placeOrder();
                    break;
                case "2":
                    confirmDelivery();
                    break;
                case "3":
                    checkOrderStatus();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void placeOrder() {
        Order order = new Order();
        boolean ordering = true;

        while (ordering) {
            System.out.println("\nAvailable Pizzas:");
            for (int i = 0; i < availablePizzas.size(); i++) {
                System.out.println((i + 1) + ". " + availablePizzas.get(i).getName() +
                        " (৳" + availablePizzas.get(i).getBasePrice() + ")");
            }
            System.out.print("Select a pizza by number: ");
            int pizzaChoice = Integer.parseInt(scanner.nextLine()) - 1;
            Pizza basePizza = availablePizzas.get(pizzaChoice);

            System.out.println("\nAvailable Sizes:");
            for (int i = 0; i < availableSizes.size(); i++) {
                System.out.println((i + 1) + ". " + availableSizes.get(i));
            }
            System.out.print("Select size by number: ");
            int sizeChoice = Integer.parseInt(scanner.nextLine()) - 1;
            String size = availableSizes.get(sizeChoice);

            Pizza selectedPizza = new Pizza(basePizza.getName(), size, basePizza.getBasePrice());

            List<Topping> selectedToppings = new ArrayList<>();
            System.out.println("\nAvailable Toppings:");
            for (int i = 0; i < availableToppings.size(); i++) {
                System.out.println((i + 1) + ". " + availableToppings.get(i).getName() +
                        " (৳" + availableToppings.get(i).getPrice() + ")");
            }
            System.out.print("Enter topping numbers separated by spaces (0 to skip): ");
            String[] toppingChoices = scanner.nextLine().split(" ");
            for (String toppingChoice : toppingChoices) {
                try {
                    int toppingIndex = Integer.parseInt(toppingChoice) - 1;
                    if (toppingIndex >= 0 && toppingIndex < availableToppings.size()) {
                        selectedToppings.add(availableToppings.get(toppingIndex));
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                }
            }

            System.out.print("\nEnter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            order.addItem(new OrderItem(selectedPizza, selectedToppings, quantity));

            System.out.print("\nAdd another pizza? (y/n): ");
            ordering = scanner.nextLine().equalsIgnoreCase("y");
        }

        Bill.generateBill(order);
        OrderStorage.saveOrder(order);

        // Ensure this is marked as a user order
        DeliveryTracker.setAdminOrder(false);
        String orderId = DeliveryTracker.createDelivery(order);
        System.out.println("\nOrder placed successfully! Your Order ID: " + orderId);
        System.out.println("Current Status: Preparing");
    }

    private static void confirmDelivery() {
        System.out.print("\nEnter your Order ID to confirm delivery: ");
        String orderId = scanner.nextLine();

        try {
            // First verify the order exists
            String currentStatus = DeliveryTracker.getDeliveryStatus(orderId);

            // Directly update to Delivered if order exists
            DeliveryTracker.updateDeliveryStatus(orderId, "Delivered");
            System.out.println("Delivery confirmed for order: " + orderId);
            System.out.println("Thank you for your order!");

        } catch (DeliveryException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please check your Order ID and try again.");
        }
    }

    private static void checkOrderStatus() {
        System.out.print("\nEnter your Order ID to check status: ");
        String orderId = scanner.nextLine();

        try {
            String status = DeliveryTracker.getDeliveryStatus(orderId);
            System.out.println("Current status of order " + orderId + ": " + status);
        } catch (DeliveryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}