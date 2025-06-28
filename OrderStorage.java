import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class OrderStorage {
    private static final String ORDERS_DIR = "orders";

    public static void saveOrder(Order order) {
        try {
            File dir = new File(ORDERS_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = ORDERS_DIR + "/order_" + timestamp + ".txt";

            FileWriter writer = new FileWriter(filename);
            writer.write("--- Order ---\n");
            for (OrderItem item : order.getItems()) {
                writer.write(item.toString());
            }
            writer.write("-----------------\n");
            writer.write(String.format("Subtotal: $%.2f\n", order.getSubtotal()));
            writer.write(String.format("Tax (10%%): $%.2f\n", order.getTax()));
            writer.write(String.format("Total: $%.2f\n", order.getTotal()));
            writer.close();

            System.out.println("Order saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    public static void displayAllOrders() {
        File dir = new File(ORDERS_DIR);
        if (!dir.exists() || dir.listFiles() == null || dir.listFiles().length == 0) {
            System.out.println("No previous orders found.");
            return;
        }

        File[] orderFiles = dir.listFiles();
        for (File file : orderFiles) {
            System.out.println("\n--- Order from file: " + file.getName() + " ---");
            try (Scanner reader = new Scanner(file)) {
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
            }
        }
    }
}

