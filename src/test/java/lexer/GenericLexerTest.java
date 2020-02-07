package lexer;

import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public class GenericLexerTest {

    GenericLexer lexer;

    @BeforeAll
    public void setup(){
        Map<DFAState, Map<Character, DFAState>> matrix = new HashMap<DFAState, Map<Character, DFAState>>();
        // generate matrix
        lexer = new GenericLexer(matrix);
    }
}
