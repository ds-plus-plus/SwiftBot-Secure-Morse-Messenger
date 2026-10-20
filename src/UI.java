import java.util.Scanner;

public class UI {
    private static final String HORIZONTAL_LINE = "--------------------------------------------------------------";
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayStartup() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println("                                                              ");
        System.out.println("     SWIFTBOT SECURE MESSENGER v1.0                          ");
        System.out.println("     Triangular Communication Network                         ");
        System.out.println("     Encrypted Morse Code Transport System                   ");
        System.out.println("                                                              ");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[SYSTEM] Initializing components...");
        Utilities.sleep(500);
        System.out.println("[  OK  ] Morse dictionary loaded");
        System.out.println("[  OK  ] Agent registry validated (3 nodes)");
        System.out.println("[  OK  ] Camera module initialized");
        System.out.println("[  OK  ] Ultrasonic sensor active");
        System.out.println("[  OK  ] LED control system ready");
        System.out.println();
    }

    public static Character promptInitialPosition() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("                SYSTEM INITIALIZATION                         ");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("Where is the robot currently located?");
        System.out.println();
        System.out.println("  A - Node A (House Stark)");
        System.out.println("  B - Node B (House Lannister)");
        System.out.println("  C - Node C (House Bolton)");
        System.out.println();

        while (true) {
            System.out.print("Enter location (A/B/C): ");
            String input = scanner.nextLine().trim().toUpperCase(); // trimming to rid white space

            if (input.equals("A") || input.equals("B") || input.equals("C")) {
                Character position = input.charAt(0);
                System.out.println();
                System.out.println("[  ✓  ] Position set to Node " + position);
                System.out.println("[  ✓  ] System ready for operation");
                System.out.println();
                Utilities.sleep(1000);
                return position;
            }

            System.out.println("[  ✗  ] Invalid input. Please enter A, B, or C.");
            System.out.println();
        }
    }

    public static void displayStateHeader(State state, Character position) {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: " + state.name() + "                 [POS: " + position + "]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
    }

    public static void displayAuthenticationPrompt(Character position) {
        String houseName = getHouseName(position);
        System.out.println("[ STATUS ] Standing by at " + houseName);
        System.out.println("[ ACTION ] Please present <CALLSIGN>:<LOCATION_ID> to camera...");
        System.out.println();
    }

    public static void displayAuthenticationFailed() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: AUTHENTICATING            [FAILED]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✗  ] ACCESS DENIED: Unauthorized identity");
        System.out.println("[  !  ] HARDWARE ALERT: Red Status LED Active");
        System.out.println("[  ?  ] Ensure QR code matches current location");
        System.out.println();
        Utilities.sleep(2000);
    }

    public static void displayAuthenticationSuccess(String house) {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: AUTHENTICATING            [SUCCESS]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✓  ] ACCESS GRANTED: Welcome, Agent of House " + house);
        System.out.println("[  !  ] INITIALISING LISTENING MODE...");
        System.out.println();
        Utilities.sleep(1500);
    }

    public static void displayListeningInstructions() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: LISTENING                MODE: ENCRYPTED");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("BUTTON CONTROLS:");
        System.out.println("  X Button → Dot (.)");
        System.out.println("  Y Button → Dash (-)");
        System.out.println("  A Button → End Character");
        System.out.println("  B Button → End Word");
        System.out.println("  ----- (5 dashes) → End Message");
        System.out.println();
        System.out.println("[  !  ] Input recording active. Begin Morse encoding...");
        System.out.println();
    }

    public static void displayArrival(Character destination) {
        clearScreen();
        String houseName = getHouseName(destination);
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: DELIVERING            [Node: " + destination + "]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✓  ] Destination Reached: " + houseName);
        System.out.println("[  !  ] RED LED: Message arrival notification active");
        System.out.println();
    }

    public static void displayRecipientVerification() {
        System.out.println("------------------- RECIPIENT VERIFICATION -------------------");
        System.out.println("[ ACTION ] Waiting for recipient QR scan...");
        System.out.println();
    }

    public static void displayVerificationFailed(String scannedLocation, String expectedLocation) {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: DELIVERING            [VERIFICATION FAILED]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✗  ] ERROR: Location mismatch!");
        System.out.println();
        System.out.println("   Expected: Node " + expectedLocation);
        System.out.println("   Scanned:  Node " + scannedLocation);
        System.out.println();
        System.out.println("[  !  ] SECURITY ALERT: Unauthorized access attempt");
        System.out.println("[  !  ] RED LED: Security warning active");
        System.out.println();
        System.out.println("[  ?  ] Verify recipient is at correct location");
        System.out.println("[  ?  ] Re-scan authorized QR code to retry");
        System.out.println();
        Utilities.sleep(2000);
    }

    public static void displayMessageTransmission() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: DELIVERING            [TRANSMITTING]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✓  ] Recipient verified");
        System.out.println("[  !  ] Transmitting message via LED protocol...");
        System.out.println();
        System.out.println("------------------- MESSAGE TRANSMISSION --------------------");
        System.out.println("[  i  ] Do not interrupt LED sequence");
        System.out.println();
    }

    public static void displayDeliveryComplete() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" STATE: DELIVERING            [COMPLETE]");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  ✓  ] Message delivered successfully");
        System.out.println("[  ✓  ] Message purged from RAM (Security)");
        System.out.println();
        System.out.println("---------------------- RETURN JOURNEY -----------------------");
        System.out.println("[  !  ] Returning to sender...");
        System.out.println();
    }

    public static void displayObstacleDetected() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println("                  ⚠  SAFETY INTERRUPT  ⚠                     ");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  !  ] OBSTACLE DETECTED: Motors Halted");
        System.out.println("[  !  ] Safety Limit: 30cm proximity");
        System.out.println();
        System.out.println("[ ACTION ] Clear path to resume movement");
        System.out.println();
    }

    public static void displayInvalidPosition() {
        clearScreen();
        System.out.println(HORIZONTAL_LINE);
        System.out.println("                  ⚠  INVALID POSITION  ⚠                     ");
        System.out.println(HORIZONTAL_LINE);
        System.out.println();
        System.out.println("[  !  ] Message did not have a valid position");
        System.out.println("[  !  ] Movement Halted");
        System.out.println("[  !  ] System possibly intercepted... Self Destructing...");
        System.out.println();
    }

    private static String getHouseName(Character nodeId) {
        return switch (nodeId) {
            case 'A' -> "Node A (House Stark)";
            case 'B' -> "Node B (House Lannister)";
            case 'C' -> "Node C (House Bolton)";
            default -> "Unknown Node";
        };
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
