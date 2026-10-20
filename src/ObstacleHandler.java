import swiftbot.SwiftBotAPI;

public class ObstacleHandler {
    private final SwiftBotAPI swiftBotAPI;
    private static final int OBSTACLE_THRESHOLD_CM = 30;

    public ObstacleHandler(SwiftBotAPI swiftBotAPI) {
        this.swiftBotAPI = swiftBotAPI;
    }

    public void detectAndBusyWaitObstacles() {
        while (true) {
            if (swiftBotAPI.useUltrasound() <= OBSTACLE_THRESHOLD_CM) {
                UI.displayObstacleDetected();
                Utilities.sleep(2000);
            } else {
                break;
            }
        }
    }
}