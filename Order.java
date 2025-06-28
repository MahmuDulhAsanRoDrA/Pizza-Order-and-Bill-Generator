import java.util.ArrayList;
import java.util.List;

class Order {
    private List<OrderItem> items = new ArrayList<>();
    private final double TAX_RATE = 0.1;

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public double getTax() {
        return getSubtotal() * TAX_RATE;
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public List<OrderItem> getItems() {
        return items;
    }
}

