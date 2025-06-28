import java.util.List;

class OrderItem {
    private Pizza pizza;
    private List<Topping> toppings;
    private int quantity;

    public OrderItem(Pizza pizza, List<Topping> toppings, int quantity) {
        this.pizza = pizza;
        this.toppings = toppings;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        double toppingCost = toppings.stream().mapToDouble(Topping::getPrice).sum();
        return (pizza.getPrice() + toppingCost) * quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity).append(" x ").append(pizza.getSize()).append(" ").append(pizza.getName()).append(" - ").append(String.format("৳%.2f", pizza.getPrice())).append("\n");
        if (!toppings.isEmpty()) {
            sb.append("  Toppings:\n");
            for (Topping t : toppings) {
                sb.append("    ").append(t.getName()).append(" - ").append(String.format("৳%.2f", t.getPrice())).append("\n");
            }
        }
        sb.append("  Item Total: ").append(String.format("৳%.2f", getTotalPrice())).append("\n");
        return sb.toString();
    }
}
