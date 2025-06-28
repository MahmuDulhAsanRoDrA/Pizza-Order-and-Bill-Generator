import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class DeliveryTracker {
    private static final String DELIVERY_DIR = "deliveries";
    private static final DateTimeFormatter ID_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static boolean isAdminOrder = false;

    // Set this flag when admin is placing an order
    public static void setAdminOrder(boolean isAdmin) {
        isAdminOrder = isAdmin;
    }

    // Create a new delivery record and return the Order ID
    public static String createDelivery(Order order) {
        try {
            Files.createDirectories(Paths.get(DELIVERY_DIR));
            String orderId = LocalDateTime.now().format(ID_FORMAT);
            String filename = DELIVERY_DIR + "/delivery_" + orderId + ".txt";

            String initialStatus = isAdminOrder ? "In house" : "Preparing";

            Files.writeString(Paths.get(filename),
                    "Order ID: " + orderId + "\n" +
                            "Status: " + initialStatus + "\n" +
                            "Created at: " + LocalDateTime.now() + "\n" +
                            (isAdminOrder ? "Admin Order: true\n" : "")
            );

            // Reset the flag after creating the delivery
            isAdminOrder = false;

            return orderId;
        } catch (IOException e) {
            System.err.println("Error creating delivery: " + e.getMessage());
            return null;
        }
    }

    // Get the current status of an order
    public static String getDeliveryStatus(String orderId) throws DeliveryException {
        String filename = DELIVERY_DIR + "/delivery_" + orderId + ".txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                if (line.startsWith("Status: ")) {
                    return line.substring(8).trim();
                }
            }
            throw new DeliveryException("Status not found for order: " + orderId);
        } catch (IOException e) {
            throw new DeliveryException("Order ID not found or invalid.");
        }
    }

    // Update delivery status (general method)
    public static void updateDeliveryStatus(String orderId, String newStatus) throws DeliveryException {
        String filename = DELIVERY_DIR + "/delivery_" + orderId + ".txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            List<String> updatedLines = new ArrayList<>();

            boolean statusUpdated = false;
            for (String line : lines) {
                if (line.startsWith("Status: ")) {
                    updatedLines.add("Status: " + newStatus);
                    statusUpdated = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!statusUpdated) {
                updatedLines.add("Status: " + newStatus);
            }

            updatedLines.add("Status Updated at: " + LocalDateTime.now() + " to " + newStatus);
            Files.write(Paths.get(filename), updatedLines);
        } catch (IOException e) {
            throw new DeliveryException("Failed to update delivery status: " + e.getMessage());
        }
    }

    // List all deliveries (for admin)
    public static void viewAllDeliveryStatuses() {
        File dir = new File(DELIVERY_DIR);
        if (!dir.exists() || dir.listFiles() == null || dir.listFiles().length == 0) {
            System.out.println("No deliveries found.");
            return;
        }

        System.out.println("\nAll Delivery Statuses:");
        System.out.println("----------------------------------------");
        System.out.printf("%-15s %-15s %-20s%n", "Order ID", "Status", "Type");
        System.out.println("----------------------------------------");

        for (File file : dir.listFiles()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                String id = file.getName().replace("delivery_", "").replace(".txt", "");
                String status = lines.stream()
                        .filter(l -> l.startsWith("Status: "))
                        .findFirst()
                        .orElse("Status: Unknown")
                        .substring(8);
                boolean isAdmin = lines.stream().anyMatch(l -> l.contains("Admin Order: true"));
                String type = isAdmin ? "Admin Order" : "Customer Order";

                System.out.printf("%-15s %-15s %-20s%n", id, status, type);
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName());
            }
        }
        System.out.println("----------------------------------------");
    }
}