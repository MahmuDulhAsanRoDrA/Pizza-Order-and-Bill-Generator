public class Pizza {
    private String name;
    private String size;
    private double basePrice;

    public Pizza(String name, String size, double basePrice) {
        this.name = name;
        this.size = size;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public String getSize() { return size; }
    public double getBasePrice() { return basePrice; }

    public double getSizeMultiplier() {
        return switch (size.toLowerCase()) {
            case "small" -> 1.0;
            case "medium" -> 1.5;
            case "large" -> 2.0;
            default -> 1.0;
        };
    }

    public double getPrice() {
        return basePrice * getSizeMultiplier();
    }
}
