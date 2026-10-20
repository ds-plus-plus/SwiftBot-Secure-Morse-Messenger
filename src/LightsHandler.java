import swiftbot.SwiftBotAPI;
import swiftbot.Underlight;
import java.util.Map;

public class LightsHandler {
    public enum MorseColours {
        WHITE, BLUE, AMBER, GREEN, RED, ERROR
    }

    private final SwiftBotAPI swiftBotAPI;
    private final Map<MorseColours, int[]> colourMap;

    public LightsHandler(SwiftBotAPI swiftBotAPI) {
        this.swiftBotAPI = swiftBotAPI;
        colourMap = Map.of(
                MorseColours.WHITE, new int[]{255, 255, 200},
                MorseColours.BLUE, new int[]{0, 150, 255},
                MorseColours.GREEN, new int[]{34, 255, 34},
                MorseColours.RED, new int[]{200, 20, 25},
                MorseColours.AMBER, new int[]{255, 191, 0},
                MorseColours.ERROR, new int[]{255, 0, 0}
        );
    }

    private void turnOnAllLights(int[] colour) {
        swiftBotAPI.setUnderlight(Underlight.FRONT_LEFT, colour);
        swiftBotAPI.setUnderlight(Underlight.BACK_LEFT, colour);
        swiftBotAPI.setUnderlight(Underlight.FRONT_RIGHT, colour);
        swiftBotAPI.setUnderlight(Underlight.BACK_RIGHT, colour);
    }

    public void blink(MorseColours colour) {
        turnOnAllLights(colourMap.get(colour));
        Utilities.sleep(300);
        swiftBotAPI.disableUnderlights();
    }

    public void errorBlink() {
        for (int i = 0; i < 5; i++) {
            turnOnAllLights(colourMap.get(MorseColours.ERROR));
            Utilities.sleep(500);
            swiftBotAPI.disableUnderlights();
            Utilities.sleep(500);
        }
    }
}