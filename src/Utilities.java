import swiftbot.SwiftBotAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utilities {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public static void swiftbotJanitor(SwiftBotAPI swiftBotAPI) {
        swiftBotAPI.disableAllButtons();
        swiftBotAPI.disableUnderlights();
        System.exit(0);
    }

    public static void cleanImages() {
        try {
            Files.list(Paths.get("./"))
                    .filter(p -> p.toString().toLowerCase().endsWith(".bmp"))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (Exception ignored) {
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
