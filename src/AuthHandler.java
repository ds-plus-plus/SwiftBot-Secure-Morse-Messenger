import java.util.Map;

public class AuthHandler {
    private Character currentPosition;
    private String house;
    private boolean authenticated = false;

    private final Map<String, Character> verificationMap = Map.of(
            "STARK", 'A',
            "LANNISTER", 'B',
            "BOLTON", 'C'
    );

    public void verifyIdentity(String qrInput, Character robotActualPosition) {
        qrInput = qrInput.toUpperCase();
        String[] splitInput = qrInput.split(":");

        if (splitInput.length != 2) {
            authenticated = false;
            return;
        }

        String callsign = splitInput[0];
        char scannedLocation = splitInput[1].charAt(0);

        if (!verificationMap.containsKey(callsign) ||
                verificationMap.get(callsign) != scannedLocation ||
                robotActualPosition != scannedLocation) {

            authenticated = false;
            return;
        }

        currentPosition = scannedLocation;
        house = callsign;
        authenticated = true;
    }

    public boolean verifyBeforeFlash(String qrInput, String decodedSequence) {
        qrInput = qrInput.toUpperCase();
        String[] splitInput = qrInput.split(":");

        if (splitInput.length != 2) {
            return false;
        }

        String callsign = splitInput[0];
        if (!verificationMap.containsKey(callsign)) {
            return false;
        }

        char destination = decodedSequence.charAt(0);
        char scannedLocation = splitInput[1].charAt(0);

        return scannedLocation == destination && verificationMap.get(callsign).equals(destination);
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

    public String getHouse() {
        return house;
    }

    public void resetSession() {
        house = null;
        authenticated = false;
    }

    public Character getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Character position) {
        currentPosition = position;
    }
}