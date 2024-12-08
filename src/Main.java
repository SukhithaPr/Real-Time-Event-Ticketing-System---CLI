import java.util.Scanner;

public class Main {
    public static int totalTickets;
    public static int ticketReleaseRate;
    public static int customerRetrievalRate;
    public static int maximumCapacity;

    private static boolean running = false;
    private static Thread[] vendorThreads;
    private static Thread[] customerThreads;

    public static void main(String[] args) {
        configureSystem();
    }

    private static void configureSystem() {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        // Loop until valid inputs are provided
        while (!isValid) {
            System.out.println("\n---Enter values (only positive integers are allowed)---\n");

            totalTickets = readPositiveInteger(scanner, "Enter total number of tickets: ");
            ticketReleaseRate = readPositiveInteger(scanner, "Enter ticket release rate (tickets per second): ");
            customerRetrievalRate = readPositiveInteger(scanner, "Enter customer retrieval rate (customers per second): ");
            maximumCapacity = readPositiveInteger(scanner, "Enter maximum ticket capacity: ");
            System.out.println();

            // Validate that all inputs are positive
            isValid = validateInputs();
            if (!isValid) {
                System.out.println("All values must be positive integers. Please re-enter.\n");
            }
        }

        // Proceed only after valid inputs
        TicketPool ticketPool = new TicketPool(maximumCapacity);

        initializeThreads(ticketPool);
        commandLoop(ticketPool);
    }

    private static boolean validateInputs() {
        // All inputs are already validated as positive integers
        return totalTickets > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0 && maximumCapacity > 0;
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value; // Valid positive integer
                } else {
                    System.out.println("Error: Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
            }
        }
    }

    private static void initializeThreads(TicketPool ticketPool) {
        vendorThreads = new Thread[3];
        for (int i = 0; i < vendorThreads.length; i++) {
            Vendor vendor = new Vendor(ticketPool, totalTickets, ticketReleaseRate);
            vendorThreads[i] = new Thread(vendor, "Vendor-" + (i + 1));
        }

        customerThreads = new Thread[5];
        for (int i = 0; i < customerThreads.length; i++) {
            Customer customer = new Customer(ticketPool, customerRetrievalRate, 5);
            customerThreads[i] = new Thread(customer, "Customer-" + (i + 1));
        }
    }

    private static void commandLoop(TicketPool ticketPool) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command (start/stop): ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "start":
                    if (!running) {
                        startTicketHandling();
                        running = true;
                    } else {
                        System.out.println("The system is already running.");
                    }
                    break;
                case "stop":
                    if (running) {
                        stopTicketHandling();
                        running = false;
                    } else {
                        System.out.println("The system is not running.");
                    }
                    break;
                default:
                    System.out.println("Invalid command. Please enter start/stop.");
            }
        }
    }

    private static void startTicketHandling() {
        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.start();
        }
        System.out.println("System started.");
    }

    private static void stopTicketHandling() {
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
        System.out.println("System stopped.");
    }
}
