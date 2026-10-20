import swiftbot.Button;
import swiftbot.SwiftBotAPI;

public class ButtonHandler {
    private final SwiftBotAPI swiftBotAPI;
    private final LightsHandler lightsHandler;
    private final MorseHandler morseHandler;
    private final MovementHandler movementHandler;
    private final AuthHandler authentication;
    private final QRHandler qrHandler;

    public ButtonHandler(SwiftBotAPI sBotAPI, LightsHandler lightHandler, MorseHandler morseCHandler,
                         MovementHandler movementManager, AuthHandler authentication, QRHandler qrHandler) {
        swiftBotAPI = sBotAPI;
        lightsHandler = lightHandler;
        morseHandler = morseCHandler;
        this.movementHandler = movementManager;
        this.authentication = authentication;
        this.qrHandler = qrHandler;
    }

    public void enableButtons() {
        swiftBotAPI.enableButton(Button.X, () -> {
            morseHandler.appendCodeAndLog(".");
            if (morseHandler.getComplete()) return;
            lightsHandler.blink(LightsHandler.MorseColours.WHITE);
        });

        swiftBotAPI.enableButton(Button.Y, () -> {
            morseHandler.appendCodeAndLog("-");
            if (morseHandler.getComplete()) return;
            lightsHandler.blink(LightsHandler.MorseColours.BLUE);
        });

        swiftBotAPI.enableButton(Button.A, () -> {
            morseHandler.endChar();
            if (morseHandler.getComplete()) return;
            lightsHandler.blink(LightsHandler.MorseColours.AMBER);
        });

        swiftBotAPI.enableButton(Button.B, () -> {
            lightsHandler.blink(LightsHandler.MorseColours.RED);
            morseHandler.endWord();

            if (morseHandler.getComplete()) {
                processDelivery();
            }
        });
    }

    private void processDelivery() {
        String destination = morseHandler.getDecodedSequence().split(" ")[0];

        for (int i = 0; i < 3; i++) {
            lightsHandler.blink(LightsHandler.MorseColours.RED);
        }

        movementHandler.moveByMessage(morseHandler.getDecodedSequence(), authentication.getCurrentPosition());

        UI.displayArrival(destination.charAt(0));

        for (int i = 0; i < 3; i++) {
            lightsHandler.blink(LightsHandler.MorseColours.RED);
        }

        Utilities.sleep(10000);
        UI.displayRecipientVerification();

        while (true) {
            String qrResult = qrHandler.scanAndDecode();

            if (authentication.verifyBeforeFlash(qrResult, morseHandler.getDecodedSequence())) {
                UI.displayMessageTransmission();
                morseHandler.flashMessage();
                UI.displayDeliveryComplete();
                Utilities.sleep(1000);

                movementHandler.returnBack();
                morseHandler.reset();
                authentication.resetSession();

                UI.displayStateHeader(State.AUTHENTICATING, authentication.getCurrentPosition());

                while (true) {
                    UI.displayAuthenticationPrompt(authentication.getCurrentPosition());
                    String senderQr = qrHandler.scanAndDecode();
                    authentication.verifyIdentity(senderQr, authentication.getCurrentPosition());

                    if (!authentication.getAuthenticated()) {
                        UI.displayAuthenticationFailed();
                        lightsHandler.errorBlink();
                    } else {
                        break;
                    }
                }

                UI.displayAuthenticationSuccess(authentication.getHouse());
                UI.displayListeningInstructions();
                break;
            } else {
                String scannedLoc = qrResult.split(":")[1];
                UI.displayVerificationFailed(scannedLoc, destination);
                lightsHandler.errorBlink();
                UI.displayRecipientVerification();
            }
        }
    }
}