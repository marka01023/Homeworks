import java.util.*;

// SRP__
class Order {
    private String productName;
    private int quantity;
    private double price;

    public Order(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}

class PriceCalculator {
    public double calculateTotalPrice(Order order) {
        return order.getQuantity() * order.getPrice() * 0.9;
    }
}

class PaymentProcessor {
    public void processPayment(String paymentDetails) {
        System.out.println("Payment processed using: " + paymentDetails);
    }
}

class NotificationService {
    public void sendConfirmationEmail(String email) {
        System.out.println("Confirmation email sent to: " + email);
    }
}

// OCP__
abstract class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract double calculateSalary();
}

class PermanentEmployee extends Employee {
    public PermanentEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }
    public double calculateSalary() {
        return baseSalary * 1.2;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }
    public double calculateSalary() {
        return baseSalary * 1.1;
    }
}

class InternEmployee extends Employee {
    public InternEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }
    public double calculateSalary() {
        return baseSalary * 0.8;
    }
}

// ISP__
interface IPrinter {
    void print(String content);
}

interface IScanner {
    void scan(String content);
}

interface IFax {
    void fax(String content);
}

class AllInOnePrinter implements IPrinter, IScanner, IFax {
    public void print(String content) { System.out.println("Printing: " + content); }
    public void scan(String content) { System.out.println("Scanning: " + content); }
    public void fax(String content) { System.out.println("Faxing: " + content); }
}

class BasicPrinter implements IPrinter {
    public void print(String content) { System.out.println("Printing: " + content); }
}

// DIP__
interface MessageSender {
    void sendMessage(String message);
}

class EmailSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

class SmsSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("SMS sent: " + message);
    }
}

class NotificationServiceDIP {
    private List<MessageSender> senders;

    public NotificationServiceDIP(List<MessageSender> senders) {
        this.senders = senders;
    }

    public void sendNotification(String message) {
        for (MessageSender sender : senders) {
            sender.sendMessage(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order("Book", 2, 50);
        PriceCalculator calculator = new PriceCalculator();
        PaymentProcessor payment = new PaymentProcessor();
        NotificationService notify = new NotificationService();

        System.out.println("Total price: " + calculator.calculateTotalPrice(order));
        payment.processPayment("Credit Card");
        notify.sendConfirmationEmail("test@mail.com");

        System.out.println("---");

        Employee emp1 = new PermanentEmployee("Aidos", 1000);
        Employee emp2 = new ContractEmployee("Maria", 800);
        Employee emp3 = new InternEmployee("Sanzhar", 600);
        System.out.println(emp1.calculateSalary());
        System.out.println(emp2.calculateSalary());
        System.out.println(emp3.calculateSalary());

        System.out.println("---");

        IPrinter printer = new BasicPrinter();
        printer.print("Hello World!");
        AllInOnePrinter allPrinter = new AllInOnePrinter();
        allPrinter.scan("Document");

        System.out.println("---");

        List<MessageSender> senders = new ArrayList<>();
        senders.add(new EmailSender());
        senders.add(new SmsSender());
        NotificationServiceDIP notification = new NotificationServiceDIP(senders);
        notification.sendNotification("Meeting at 10 AM");
    }
}
