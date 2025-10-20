
import java.util.*;


interface ICommand {
    void execute();
    void undo();
}

class Light {
    private boolean isOn = false;
    public void on() { isOn = true; System.out.println("Light is ON"); }
    public void off() { isOn = false; System.out.println("Light is OFF"); }
}

class Door {
    private boolean isOpen = false;
    public void open() { isOpen = true; System.out.println("Door is OPEN"); }
    public void close() { isOpen = false; System.out.println("Door is CLOSED"); }
}

class Thermostat {
    private int temperature = 22;
    public void increase() { temperature++; System.out.println("Temperature increased to " + temperature); }
    public void decrease() { temperature--; System.out.println("Temperature decreased to " + temperature); }
}

class LightOnCommand implements ICommand {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}

class LightOffCommand implements ICommand {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.off(); }
    public void undo() { light.on(); }
}

class DoorOpenCommand implements ICommand {
    private Door door;
    public DoorOpenCommand(Door door) { this.door = door; }
    public void execute() { door.open(); }
    public void undo() { door.close(); }
}

class DoorCloseCommand implements ICommand {
    private Door door;
    public DoorCloseCommand(Door door) { this.door = door; }
    public void execute() { door.close(); }
    public void undo() { door.open(); }
}

class IncreaseTempCommand implements ICommand {
    private Thermostat thermostat;
    public IncreaseTempCommand(Thermostat thermostat) { this.thermostat = thermostat; }
    public void execute() { thermostat.increase(); }
    public void undo() { thermostat.decrease(); }
}

class DecreaseTempCommand implements ICommand {
    private Thermostat thermostat;
    public DecreaseTempCommand(Thermostat thermostat) { this.thermostat = thermostat; }
    public void execute() { thermostat.decrease(); }
    public void undo() { thermostat.increase(); }
}

class SmartHomeController {
    private Stack<ICommand> history = new Stack<>();

    public void executeCommand(ICommand command) {
        command.execute();
        history.push(command);
    }

    public void undoLastCommand() {
        if (!history.isEmpty()) {
            ICommand command = history.pop();
            command.undo();
        } else {
            System.out.println("No commands to undo!");
        }
    }
}

abstract class Beverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    void boilWater() { System.out.println("Boiling water"); }
    void pourInCup() { System.out.println("Pouring into cup"); }

    abstract void brew();
    abstract void addCondiments();

    boolean customerWantsCondiments() {
        return true;
    }
}

class Tea extends Beverage {
    void brew() { System.out.println("Steeping the tea"); }
    void addCondiments() { System.out.println("Adding lemon"); }
}

class Coffee extends Beverage {
    void brew() { System.out.println("Dripping coffee through filter"); }
    void addCondiments() { System.out.println("Adding sugar and milk"); }

    @Override
    boolean customerWantsCondiments() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like milk and sugar (y/n)? ");
        String answer = sc.nextLine();
        return answer.toLowerCase().startsWith("y");
    }
}

class HotChocolate extends Beverage {
    void brew() { System.out.println("Mixing chocolate powder with hot water"); }
    void addCondiments() { System.out.println("Adding whipped cream"); }
}

interface IMediator {
    void sendMessage(String message, User sender);
    void addUser(User user);
}

class ChatRoom implements IMediator {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " joined the chat.");
    }

    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receive(message, sender);
            }
        }
    }
}

abstract class User {
    protected IMediator mediator;
    protected String name;

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() { return name; }
    public abstract void send(String message);
    public abstract void receive(String message, User sender);
}

class ChatUser extends User {
    public ChatUser(IMediator mediator, String name) {
        super(mediator, name);
    }

    public void send(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    public void receive(String message, User sender) {
        System.out.println(name + " receives from " + sender.getName() + ": " + message);
    }
}

public class Main7 {
    public static void main(String[] args) {

        System.out.println("===== COMMAND PATTERN =====");
        Light light = new Light();
        Door door = new Door();
        Thermostat thermostat = new Thermostat();
        SmartHomeController controller = new SmartHomeController();

        controller.executeCommand(new LightOnCommand(light));
        controller.executeCommand(new DoorOpenCommand(door));
        controller.executeCommand(new IncreaseTempCommand(thermostat));
        controller.undoLastCommand();
        controller.undoLastCommand();

        System.out.println("\n===== TEMPLATE METHOD PATTERN =====");
        Beverage tea = new Tea();
        Beverage coffee = new Coffee();
        Beverage choco = new HotChocolate();

        System.out.println("\nMaking tea...");
        tea.prepareRecipe();

        System.out.println("\nMaking coffee...");
        coffee.prepareRecipe();

        System.out.println("\nMaking hot chocolate...");
        choco.prepareRecipe();

        System.out.println("\n===== MEDIATOR PATTERN =====");
        ChatRoom chatRoom = new ChatRoom();

        User user1 = new ChatUser(chatRoom, "Alice");
        User user2 = new ChatUser(chatRoom, "Bob");
        User user3 = new ChatUser(chatRoom, "Charlie");

        chatRoom.addUser(user1);
        chatRoom.addUser(user2);
        chatRoom.addUser(user3);

        user1.send("Hello everyone!");
        user2.send("Hi Alice!");
        user3.send("Hey guys!");
    }
}
