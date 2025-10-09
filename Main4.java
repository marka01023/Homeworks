import java.util.Scanner;

interface IVehicle {
    void drive();
    void refuel();
}

class Car implements IVehicle {
    private String brand;
    private String model;
    private String fuelType;

    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    public void drive() {
        System.out.println("Car " + brand + " " + model + " is driving.");
    }

    public void refuel() {
        System.out.println("Car is refueling with: " + fuelType);
    }
}

class Motorcycle implements IVehicle {
    private String type;
    private int engineVolume;

    public Motorcycle(String type, int engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    public void drive() {
        System.out.println("Motorcycle " + type + " with engine volume " + engineVolume + " is driving.");
    }

    public void refuel() {
        System.out.println("Motorcycle is refueling with gasoline.");
    }
}

class Truck implements IVehicle {
    private int capacity;
    private int axles;

    public Truck(int capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    public void drive() {
        System.out.println("Truck with capacity " + capacity + " tons and " + axles + " axles is driving.");
    }

    public void refuel() {
        System.out.println("Truck is refueling with diesel.");
    }
}

class Bus implements IVehicle {
    private int seats;
    private String route;

    public Bus(int seats, String route) {
        this.seats = seats;
        this.route = route;
    }

    public void drive() {
        System.out.println("Bus on route " + route + " with " + seats + " seats is driving.");
    }

    public void refuel() {
        System.out.println("Bus is refueling with gas.");
    }
}

abstract class VehicleFactory {
    public abstract IVehicle createVehicle();
}

class CarFactory extends VehicleFactory {
    private String brand;
    private String model;
    private String fuelType;

    public CarFactory(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    public IVehicle createVehicle() {
        return new Car(brand, model, fuelType);
    }
}

class MotorcycleFactory extends VehicleFactory {
    private String type;
    private int engineVolume;

    public MotorcycleFactory(String type, int engineVolume) {
        this.type = type;
        this.engineVolume = engineVolume;
    }

    public IVehicle createVehicle() {
        return new Motorcycle(type, engineVolume);
    }
}

class TruckFactory extends VehicleFactory {
    private int capacity;
    private int axles;

    public TruckFactory(int capacity, int axles) {
        this.capacity = capacity;
        this.axles = axles;
    }

    public IVehicle createVehicle() {
        return new Truck(capacity, axles);
    }
}

class BusFactory extends VehicleFactory {
    private int seats;
    private String route;

    public BusFactory(int seats, String route) {
        this.seats = seats;
        this.route = route;
    }

    public IVehicle createVehicle() {
        return new Bus(seats, route);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose vehicle type (car/motorcycle/truck/bus): ");
        String choice = scanner.nextLine();

        IVehicle vehicle = null;

        switch (choice.toLowerCase()) {
            case "car":
                System.out.print("Enter brand: ");
                String brand = scanner.nextLine();
                System.out.print("Enter model: ");
                String model = scanner.nextLine();
                System.out.print("Enter fuel type: ");
                String fuel = scanner.nextLine();
                vehicle = new CarFactory(brand, model, fuel).createVehicle();
                break;
            case "motorcycle":
                System.out.print("Enter motorcycle type: ");
                String type = scanner.nextLine();
                System.out.print("Enter engine volume: ");
                int volume = scanner.nextInt();
                vehicle = new MotorcycleFactory(type, volume).createVehicle();
                break;
            case "truck":
                System.out.print("Enter capacity (tons): ");
                int capacity = scanner.nextInt();
                System.out.print("Enter number of axles: ");
                int axles = scanner.nextInt();
                vehicle = new TruckFactory(capacity, axles).createVehicle();
                break;
            case "bus":
                System.out.print("Enter number of seats: ");
                int seats = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter route: ");
                String route = scanner.nextLine();
                vehicle = new BusFactory(seats, route).createVehicle();
                break;
            default:
                System.out.println("Unknown vehicle type.");
        }

        if (vehicle != null) {
            vehicle.drive();
            vehicle.refuel();
        }

        scanner.close();
    }
}
