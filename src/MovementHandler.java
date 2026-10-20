import swiftbot.SwiftBotAPI;
import java.util.ArrayList;

public class MovementHandler {
    private final SwiftBotAPI swiftBotAPI;
    private final ObstacleHandler obstacleHandler;

    public final int FORWARDS = 1;
    public final int BACKWARDS = -1;

    private static final int FORWARD_SPEED_LEFT = 79;
    private static final int FORWARD_SPEED_RIGHT = 98;
    private static final int FORWARD_DURATION_MS = 2000;

    private static final int ROTATE_SPEED = 70;
    private static final int ROTATE_DURATION_MS = 700;
    private static final float ROTATE_STRENGTH = 0.73f;

    private enum ActionType {
        MOVE, ROTATE
    }

    private static class MovementAction {
        final ActionType type;
        final int direction;
        final float strength;

        MovementAction(ActionType type, int direction, float strength) {
            this.type = type;
            this.direction = direction;
            this.strength = strength;
        }
    }

    private final ArrayList<MovementAction> movements = new ArrayList<>();

    public MovementHandler(SwiftBotAPI swiftBotAPI, ObstacleHandler obstacleHandler) {
        this.swiftBotAPI = swiftBotAPI;
        this.obstacleHandler = obstacleHandler;
    }

    private void moveAndDetect50CM(int direction) {
        if (direction != FORWARDS && direction != BACKWARDS) return;

        if (direction == FORWARDS) {
            obstacleHandler.detectAndBusyWaitObstacles();
        }

        swiftBotAPI.move(FORWARD_SPEED_LEFT * direction, FORWARD_SPEED_RIGHT * direction, FORWARD_DURATION_MS);
        movements.add(new MovementAction(ActionType.MOVE, -direction, 0f));
        Utilities.sleep(200);
    }

    private void rotateAndDetect(int direction) {
        rotateAndDetect(direction, ROTATE_STRENGTH);
    }

    private void rotateAndDetect(int direction, float strength) {
        if (direction != FORWARDS && direction != BACKWARDS) return;

        if (direction == FORWARDS) {
            swiftBotAPI.move((int)(-ROTATE_SPEED * strength), (int)(ROTATE_SPEED * strength), ROTATE_DURATION_MS);
        } else {
            swiftBotAPI.move((int)(ROTATE_SPEED * strength), (int)(-ROTATE_SPEED * strength), ROTATE_DURATION_MS);
        }

        movements.add(new MovementAction(ActionType.ROTATE, -direction, strength));
        Utilities.sleep(200);
    }

    public void moveByMessage(String message, Character currentPosition) {
        if (message == null || message.trim().isEmpty()) {
            handleInvalidRoute();
            return;
        }

        String destination = String.valueOf(message.trim().charAt(0));

        switch (currentPosition) {
            case 'A':
                switch (destination) {
                    case "B":
                        moveAndDetect50CM(FORWARDS);
                        break;
                    case "C":
                        rotateAndDetect(FORWARDS);
                        moveAndDetect50CM(FORWARDS);
                        break;
                    default:
                        handleInvalidRoute();
                        break;
                }
                break;
            case 'B':
                switch (destination) {
                    case "C":
                        rotateAndDetect(FORWARDS);
                        moveAndDetect50CM(FORWARDS);
                        break;
                    case "A":
                        rotateAndDetect(BACKWARDS);
                        moveAndDetect50CM(FORWARDS);
                        break;
                    default:
                        handleInvalidRoute();
                        break;
                }
                break;
            case 'C':
                switch (destination) {
                    case "A":
                        rotateAndDetect(FORWARDS);
                        moveAndDetect50CM(FORWARDS);
                        break;
                    case "B":
                        rotateAndDetect(BACKWARDS);
                        moveAndDetect50CM(FORWARDS);
                        break;
                    default:
                        handleInvalidRoute();
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void returnBack() {
        for (int i = movements.size() - 1; i >= 0; i--) {
            MovementAction action = movements.get(i);

            if (action.type == ActionType.MOVE) {
                moveAndDetect50CM(action.direction);
            } else {
                rotateAndDetect(action.direction, action.strength);
            }
        }

        movements.clear();
    }

    private void handleInvalidRoute() {
        UI.displayInvalidPosition();
        Utilities.sleep(3000);
        Utilities.swiftbotJanitor(swiftBotAPI);
    }
}