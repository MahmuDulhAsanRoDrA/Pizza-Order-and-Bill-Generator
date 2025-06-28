import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Bill {
    public static void generateBill(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Pizza Shop Bill ---\n");
        for (OrderItem item : order.getItems()) {
            sb.append(item);
        }
        sb.append("-------------------------\n");
        sb.append(String.format("Subtotal: ৳%.2f\n", order.getSubtotal()));
        sb.append(String.format("Tax (10%%): ৳%.2f\n", order.getTax()));
        sb.append(String.format("Total: ৳%.2f\n", order.getTotal()));

        System.out.println(sb);

        try {
            String filename = "bill_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            FileWriter writer = new FileWriter(filename);
            writer.write(sb.toString());
            writer.close();
            System.out.println("Bill saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving bill.");
        }
    }
}
