import swiftbot.SwiftBotAPI;

public class QRHandler {
    private final SwiftBotAPI swiftBotAPI;
    private final LightsHandler lightsHandler;

    public QRHandler(SwiftBotAPI swiftBotAPI, LightsHandler lightsHandler) {
        this.swiftBotAPI = swiftBotAPI;
        this.lightsHandler = lightsHandler;
    }

    public String scanAndDecode() {
        String decodedData;

        do {
            Utilities.sleep(1500);
            decodedData = swiftBotAPI.decodeQRImage(swiftBotAPI.getQRImage());
            Utilities.cleanImages();
        } while (decodedData.isEmpty());

        lightsHandler.blink(LightsHandler.MorseColours.GREEN);
        Utilities.sleep(1000);

        return decodedData;
    }
}