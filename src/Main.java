import swiftbot.SwiftBotAPI;

public class Main {
    public static void main(String[] args) {
        SwiftBotAPI swiftBotAPI = SwiftBotAPI.INSTANCE;

        UI.displayStartup();

        Character initialPosition = UI.promptInitialPosition();

        var lightsHandler = new LightsHandler(swiftBotAPI);
        var qrHandler = new QRHandler(swiftBotAPI, lightsHandler);
        var authentication = new AuthHandler();
        var obstacleDetection = new ObstacleHandler(swiftBotAPI);
        var movementManager = new MovementHandler(swiftBotAPI, obstacleDetection);
        var logHandler = new LogHandler();
        var morseHandler = new MorseHandler(lightsHandler, authentication, logHandler);
        var buttonHandler = new ButtonHandler(swiftBotAPI, lightsHandler, morseHandler, movementManager, authentication, qrHandler);

        authentication.setCurrentPosition(initialPosition);

        UI.displayStateHeader(State.AUTHENTICATING, authentication.getCurrentPosition());

        buttonHandler.enableButtons();

        while (true) {
            UI.displayAuthenticationPrompt(authentication.getCurrentPosition());

            String qrResult = qrHandler.scanAndDecode();
            authentication.verifyIdentity(qrResult, initialPosition);

            if (!authentication.getAuthenticated()) {
                UI.displayAuthenticationFailed();
                lightsHandler.errorBlink();
            } else {
                break;
            }
        }

        UI.displayAuthenticationSuccess(authentication.getHouse());
        UI.displayListeningInstructions();
    }
}