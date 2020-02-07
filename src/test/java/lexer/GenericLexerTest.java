package lexer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericLexerTest {

    static GenericLexer lexer;

    @BeforeAll
    public static void setup(){
        Map<DFAState, Map<Character, DFAState>> matrix = new HashMap<DFAState, Map<Character, DFAState>>();
        // generate matrix for regular expression a(b|c)a*
        //TODO: Add correct positions set, those were added to give each state a different hash
        DFAState stateA = new DFAState(0, false, new HashSet<>(Arrays.asList(1)));
        DFAState stateB = new DFAState(1, false, new HashSet<>(Arrays.asList(2)));
        DFAState stateC = new DFAState(2, true, new HashSet<>(Arrays.asList(3)));
        DFAState stateD = new DFAState(3, true, new HashSet<>(Arrays.asList(4)));
        DFAState stateE = new DFAState(4, true, new HashSet<>(Arrays.asList(5)));
        //Matrices
        // For State A
        Map<Character, DFAState> stateAMap = new HashMap<Character, DFAState>();
        stateAMap.put('a', stateB);
        matrix.put(stateA, stateAMap);
        // For State B
        Map<Character, DFAState> stateBMap = new HashMap<>();
        stateBMap.put('b', stateC);
        stateBMap.put('c', stateD);
        matrix.put(stateB, stateBMap);
        // For State C
        Map<Character, DFAState> stateCMap = new HashMap<>();
        stateCMap.put('a', stateE);
        matrix.put(stateC, stateCMap);
        // For State D
        Map<Character, DFAState> stateDMap = new HashMap<>();
        stateDMap.put('a', stateE);
        matrix.put(stateD, stateDMap);
        // For State E
        Map<Character, DFAState> stateEMap = new HashMap<>();
        stateEMap.put('a', stateE);
        matrix.put(stateE, stateEMap);
        lexer = new GenericLexer(matrix);
    }

    @Test()
    public void testForSuccess(){
        assertTrue(lexer.match("aba"));
        assertTrue(lexer.match("aca"));
        assertTrue(lexer.match("ab"));
        assertTrue(lexer.match("abaaaaaaaa"));
    }

    @Test()
    public void testForFail(){
        assertFalse(lexer.match("baa"));
        assertFalse(lexer.match("efg"));
    }
}
