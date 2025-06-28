import java.util.*;

public class AdminConsole {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Topping> availableToppings = new ArrayList<>(List.of(
            new Topping("Cheese", 100),
            new Topping("Pepperoni", 150),
            new Topping("Olives", 100),
            new Topping("Mushrooms", 120),
            new Topping("Onions", 80)
    ));

    private static List<Pizza> pizzas = new ArrayList<>(List.of(
            new Pizza("Margherita", "Regular", 500),
            new Pizza("Pepperoni", "Regular", 600),
            new Pizza("Vegetarian", "Regular", 550),
            new Pizza("BBQ Chicken", "Regular", 650)
    ));

    private static List<String> sizes = new ArrayList<>(List.of(
            "Small",
            "Medium",
            "Large",
            "Extra Large"
    ));

    public static void start() {
        System.out.println("\nPizza Shop Management");
        boolean running = true;

        while (running) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Place Order (as admin)");
            System.out.println("2. View All Orders");
            System.out.println("3. View Delivery Statuses");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    placeOrder();
                    break;
                case "2":
                    OrderStorage.displayAllOrders();
                    break;
                case "3":
                    DeliveryTracker.viewAllDeliveryStatuses();
                    break;
                case "4":
                    running = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void placeOrder() {
        Order order = new Order();
        boolean ordering = true;

        while (ordering) {
            System.out.println("\nAvailable Pizzas:");
            for (int i = 0; i < pizzas.size(); i++) {
                System.out.println((i + 1) + ". " + pizzas.get(i).getName() + " (৳" + pizzas.get(i).getBasePrice() + ")");
            }
            System.out.print("Select a pizza by number: ");
            int pizzaChoice = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
            Pizza basePizza = pizzas.get(pizzaChoice);

            System.out.println("\nSelect size:");
            for (int i = 0; i < sizes.size(); i++) {
                System.out.println((i + 1) + ". " + sizes.get(i));
            }
            System.out.print("Enter size number: ");
            int sizeChoice = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
            String size = sizes.get(sizeChoice);

            Pizza selectedPizza = new Pizza(basePizza.getName(), size, basePizza.getBasePrice());

            List<Topping> selectedToppings = new ArrayList<>();
            System.out.println("\nAvailable Toppings:");
            for (int i = 0; i < availableToppings.size(); i++) {
                System.out.println((i + 1) + ". " + availableToppings.get(i).getName() + " (৳" + availableToppings.get(i).getPrice() + ")");
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
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume newline

            order.addItem(new OrderItem(selectedPizza, selectedToppings, quantity));

            System.out.print("\nAdd another pizza? (y/n): ");
            ordering = scanner.nextLine().equalsIgnoreCase("y");
        }

        Bill.generateBill(order);
        OrderStorage.saveOrder(order);
        DeliveryTracker.setAdminOrder(true);
        String orderId = DeliveryTracker.createDelivery(order);

        System.out.println("\nOrder placed successfully! Order ID: " + orderId);
        System.out.println("Status: In house");
    }

    // Helper methods to access the static lists
    public static List<Pizza> getAvailablePizzas() {
        return new ArrayList<>(pizzas);
    }

    public static List<String> getAvailableSizes() {
        return new ArrayList<>(sizes);
    }

    public static List<Topping> getAvailableToppings() {
        return new ArrayList<>(availableToppings);
    }
}