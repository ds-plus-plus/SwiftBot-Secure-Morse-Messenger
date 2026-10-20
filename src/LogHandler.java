import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHandler {
    private static final String CSV_FILE = "communication_log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LocalDateTime recordedTime;

    public LogHandler() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
                writer.println("timestamp_recorded,timestamp_delivered,sender_id,receiver_id,message_encrypted");
            } catch (IOException e) {
                System.err.println("[ERROR] Failed to create log file: " + e.getMessage());
            }
        }
    }

    public void recordMessageTime() {
        recordedTime = LocalDateTime.now();
    }

    public void logJourney(Character sender, Character receiver, String message) {
        LocalDateTime deliveredTime = LocalDateTime.now();

        if (recordedTime == null) {
            recordedTime = deliveredTime;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
            String line = String.format("%s,%s,%c,%c,%s",
                    formatter.format(recordedTime),
                    formatter.format(deliveredTime),
                    sender,
                    receiver,
                    message.replace("\"", "\"\""));
            writer.println(line);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write to log file: " + e.getMessage());
        }

        recordedTime = null;
    }
}