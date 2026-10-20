public class MorseHandler {
    private final LightsHandler lightsHandler;
    private final AuthHandler authentication;
    private final LogHandler logHandler;

    private StringBuilder currentLetter;
    private StringBuilder decodedSequence;
    private StringBuilder rawSequence;
    private boolean completed;

    public MorseHandler(LightsHandler lightsHandler, AuthHandler authentication, LogHandler logHandler) {
        this.lightsHandler = lightsHandler;
        this.authentication = authentication;
        this.logHandler = logHandler;
        reset();
    }

    public void reset() {
        currentLetter = new StringBuilder();
        decodedSequence = new StringBuilder();
        rawSequence = new StringBuilder();
        completed = false;
    }

    public void appendCodeAndLog(String code) {
        if (completed) {
            return;
        }

        currentLetter.append(code);
        rawSequence.append(code);

        if (currentLetter.toString().equals("-----")) {
            lightsHandler.blink(LightsHandler.MorseColours.GREEN);
            completed = true;
            rawSequence.setLength(rawSequence.length() - 5);

            logHandler.recordMessageTime();
            logHandler.logJourney(authentication.getCurrentPosition(), decodedSequence.toString().split(" ")[0].charAt(0), rawSequence.toString());
        }
    }

    public void endChar() {
        if (completed) {
            return;
        }

        decodeLetter();
        rawSequence.append("|");
        currentLetter.setLength(0);
    }

    public void endWord() {
        if (completed) {
            return;
        }
        decodedSequence.append(" ");
        rawSequence.append(" ");
    }

    private void decodeLetter() {
        Character parsedLetter = MorseParser.morseMap.get(currentLetter.toString());

        if (parsedLetter == null) {
            return;
        }

        decodedSequence.append(parsedLetter);
    }

    public void flashMessage() {
        Utilities.sleep(200);

        String senderMorse = MorseParser.locationMorseMap.get(authentication.getCurrentPosition());
        char[] messageArray = (senderMorse + "|" + rawSequence.toString()).toCharArray();

        for (char symbol : messageArray) {
            lightsHandler.blink(MorseParser.morseColoursMap.get(symbol));
            Utilities.sleep(150);
        }

        lightsHandler.blink(LightsHandler.MorseColours.GREEN);
        reset();
    }

    public boolean getComplete() {
        return completed;
    }

    public String getDecodedSequence() {
        return decodedSequence.toString();
    }
}