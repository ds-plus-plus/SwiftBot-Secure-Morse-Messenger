import java.util.Map;
import static java.util.Map.entry;

public class MorseParser {
    public static final Map<String, Character> morseMap = Map.ofEntries(
            entry(".-", 'A'),
            entry("-...", 'B'),
            entry("-.-.", 'C'),
            entry("-..", 'D'),
            entry(".", 'E'),
            entry("..-.", 'F'),
            entry("--.", 'G'),
            entry("....", 'H'),
            entry("..", 'I'),
            entry(".---", 'J'),
            entry("-.-", 'K'),
            entry(".-..", 'L'),
            entry("--", 'M'),
            entry("-.", 'N'),
            entry("---", 'O'),
            entry(".--.", 'P'),
            entry("--.-", 'Q'),
            entry(".-.", 'R'),
            entry("...", 'S'),
            entry("-", 'T'),
            entry("..-", 'U'),
            entry("...-", 'V'),
            entry(".--", 'W'),
            entry("-..-", 'X'),
            entry("-.--", 'Y'),
            entry("--..", 'Z'),
            entry("-----", '0')
    );

    public static final Map<Character, LightsHandler.MorseColours> morseColoursMap = Map.of(
            '.', LightsHandler.MorseColours.WHITE,
            '-', LightsHandler.MorseColours.BLUE,
            ' ', LightsHandler.MorseColours.RED,
            '|', LightsHandler.MorseColours.AMBER
    );

    public static final Map<Character, String> locationMorseMap = Map.of(
            'A', "-.",
            'B', "-...",
            'C', "-.-."
    );
}