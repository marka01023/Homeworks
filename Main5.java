import java.io.*;
import java.util.*;

//singleton
class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private Map<String, String> settings;

    private ConfigurationManager() {
        settings = new HashMap<>();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            settings.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    settings.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (var entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("error saving config: " + e.getMessage());
        }
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public String getSetting(String key) {
        return settings.getOrDefault(key, "tabylmady");
    }
}

//builder
class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) { this.header = header; }
    public void setContent(String content) { this.content = content; }
    public void setFooter(String footer) { this.footer = footer; }

    public String showReport() {
        return header + "\n" + content + "\n" + footer;
    }
}

interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    Report getReport();
}

class TextReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.setHeader("=== " + header + " ==="); }
    public void setContent(String content) { report.setContent(content); }
    public void setFooter(String footer) { report.setFooter("--- " + footer + " ---"); }
    public Report getReport() { return report; }
}

class HtmlReportBuilder implements IReportBuilder {
    private Report report = new Report();

    public void setHeader(String header) { report.setHeader("<h1>" + header + "</h1>"); }
    public void setContent(String content) { report.setContent("<p>" + content + "</p>"); }
    public void setFooter(String footer) { report.setFooter("<footer>" + footer + "</footer>"); }
    public Report getReport() { return report; }
}

class ReportDirector {
    public void constructReport(IReportBuilder builder, String h, String c, String f) {
        builder.setHeader(h);
        builder.setContent(c);
        builder.setFooter(f);
    }
}

//==================== PROTOTYPE ====================//
interface Prototype extends Cloneable {
    Object clone();
}

class Product implements Prototype {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name; this.price = price; this.quantity = quantity;
    }

    public Product(Product other) {
        this.name = other.name;
        this.price = other.price;
        this.quantity = other.quantity;
    }

    public Object clone() {
        return new Product(this);
    }

    public String toString() {
        return name + " (" + quantity + " pcs, $" + price + ")";
    }
}

class Discount implements Prototype {
    private String type;
    private double value;

    public Discount(String type, double value) {
        this.type = type; this.value = value;
    }

    public Discount(Discount other) {
        this.type = other.type;
        this.value = other.value;
    }

    public Object clone() {
        return new Discount(this);
    }

    public String toString() {
        return type + " - " + value + "%";
    }
}

class Order implements Prototype {
    private List<Product> products = new ArrayList<>();
    private double deliveryCost;
    private Discount discount;
    private String paymentMethod;

    public Order(List<Product> products, double deliveryCost, Discount discount, String paymentMethod) {
        this.products = products;
        this.deliveryCost = deliveryCost;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
    }

    public Order(Order other) {
        this.deliveryCost = other.deliveryCost;
        this.discount = (Discount) other.discount.clone();
        this.paymentMethod = other.paymentMethod;
        this.products = new ArrayList<>();
        for (Product p : other.products) {
            this.products.add((Product) p.clone());
        }
    }

    public Object clone() {
        return new Order(this);
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public String toString() {
        return "Order: " + products + "\nDelivery: $" + deliveryCost +
                "\nDiscount: " + discount + "\nPayment: " + paymentMethod;
    }
}

public class Main {
    public static void main(String[] args) {
        // Singleton test
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.setSetting("theme", "dark");
        config.setSetting("language", "en");
        config.saveToFile("config.txt");
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        System.out.println("Same instance: " + (config == config2));
        System.out.println("Language: " + config2.getSetting("language"));

        // Builder test
        ReportDirector director = new ReportDirector();
        IReportBuilder textBuilder = new TextReportBuilder();
        IReportBuilder htmlBuilder = new HtmlReportBuilder();

        director.constructReport(textBuilder, "Report Title", "This is content", "End of report");
        director.constructReport(htmlBuilder, "HTML Report", "HTML content here", "End of HTML");

        System.out.println("\nText Report:\n" + textBuilder.getReport().showReport());
        System.out.println("\nHTML Report:\n" + htmlBuilder.getReport().showReport());

        // Prototype test
        Product p1 = new Product("Laptop", 1500, 1);
        Product p2 = new Product("Mouse", 25, 2);
        Discount d1 = new Discount("New Year", 10);

        List<Product> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);

        Order order1 = new Order(list, 20, d1, "Card");
        Order order2 = (Order) order1.clone();

        order2.addProduct(new Product("Keyboard", 50, 1));
        System.out.println("\nOriginal Order:\n" + order1);
        System.out.println("\nCloned Order:\n" + order2);
    }
}
