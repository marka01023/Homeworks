import java.util.*;

//Strategy
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("payment " + amount + " тг money.");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("payment " + amount + " тг on Paypal.");
    }
}

class CryptoPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("payment " + amount + " тг on crypto.");
    }
}

class PaymentContext {
    private PaymentStrategy paymentStrategy;
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("error!");
        } else {
            paymentStrategy.pay(amount);
        }
    }
}

//Observer
interface Observer {
    void update(String currency, double rate);
}

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(String currency, double rate);
}

class CurrencyExchange implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Map<String, Double> rates = new HashMap<>();

    public void attach(Observer o) {
        observers.add(o);
    }

    public void detach(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String currency, double rate) {
        for (Observer o : observers) {
            o.update(currency, rate);
        }
    }

    public void setRate(String currency, double rate) {
        rates.put(currency, rate);
        System.out.println(": " + currency + " = " + rate);
        notifyObservers(currency, rate);
    }
}

class MobileAppObserver implements Observer {
    public void update(String currency, double rate) {
        System.out.println("[Mobile] new currency " + currency + ": " + rate);
    }
}

class WebSiteObserver implements Observer {
    public void update(String currency, double rate) {
        System.out.println("update currency" + currency + ": " + rate);
    }
}

class BankSystemObserver implements Observer {
    public void update(String currency, double rate) {
        System.out.println("choosen new currency " + currency + ": " + rate);
    }
}

//main
public class Main6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Pattern strategy");
        PaymentContext paymentContext = new PaymentContext();

        System.out.println("choose payment method: 1 - Карта, 2 - PayPal, 3 - Криптовалюта");
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                paymentContext.setPaymentStrategy(new CreditCardPayment());
                break;
            case "2":
                paymentContext.setPaymentStrategy(new PayPalPayment());
                break;
            case "3":
                paymentContext.setPaymentStrategy(new CryptoPayment());
                break;
            default:
                System.out.println("wrong!");
                return;
        }

        paymentContext.executePayment(15000);

        System.out.println("pattern Наблюдатель");
        CurrencyExchange exchange = new CurrencyExchange();

        Observer mobileApp = new MobileAppObserver();
        Observer website = new WebSiteObserver();
        Observer bank = new BankSystemObserver();

        exchange.attach(mobileApp);
        exchange.attach(website);
        exchange.attach(bank);

        exchange.setRate("USD", 480.25);
        exchange.setRate("EUR", 505.70);

        exchange.detach(website);
        System.out.println("coming notification to website");

        exchange.setRate("RUB", 5.20);

        System.out.println("end programming");
        sc.close();
    }
}
