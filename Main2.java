
// DRY
class Logger {
    public void log(String message, String level) {
        System.out.println(level.toUpperCase() + ": " + message);
    }
}

class Config {
    public static String connectionString = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
}

class DatabaseService {
    public void connect() {
        System.out.println("Connecting with " + Config.connectionString);
    }
}

class LoggingService {
    public void log(String message) {
        System.out.println("Log to DB [" + Config.connectionString + "]: " + message);
    }
}

// KISS
class NumberProcessor {
    public void processNumbers(int[] numbers) {
        if (numbers == null || numbers.length == 0) return;

        for (int number : numbers) {
            if (number > 0) {
                System.out.println(number);
            }
        }
    }

    public void printPositiveNumbers(int[] numbers) {
        for (int number : numbers) {
            if (number > 0) {
                System.out.println(number);
            }
        }
    }

    public int divide(int a, int b) {
        if (b == 0) return 0;
        return a / b;
    }
}

// YAGNI
class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}

class FileReader {
    public String readFile(String filePath) {
        return "file content";
    }
}

class ReportGenerator {
    public void generatePdfReport() {
        System.out.println("PDF report generated");
    }
}

public class Main {
    public static void main(String[] args) {
        // DRY
        Logger logger = new Logger();
        logger.log("Wrong messege", "error");
        logger.log("warning messege", "warning");
        logger.log("messege information", "info");

        DatabaseService db = new DatabaseService();
        db.connect();

        LoggingService logService = new LoggingService();
        logService.log("Database connected");

        // KISS
        NumberProcessor processor = new NumberProcessor();
        int[] numbers = {-1, 0, 1, 2, 3};
        processor.processNumbers(numbers);
        processor.printPositiveNumbers(numbers);
        System.out.println("Divide: " + processor.divide(10, 2));
        System.out.println("Divide by zero: " + processor.divide(10, 0));

        // YAGNI
        User user = new User("Aidar", "aidar@mail.com");
        System.out.println("User: " + user.getName() + ", " + user.getEmail());

        FileReader reader = new FileReader();
        System.out.println(reader.readFile("file.txt"));

        ReportGenerator report = new ReportGenerator();
        report.generatePdfReport();
    }
}
