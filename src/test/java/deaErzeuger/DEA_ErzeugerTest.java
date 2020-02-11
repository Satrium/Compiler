package deaErzeuger;

import DEA_Erzeuger.DEA_Erzeuger;
import lexer.DFAState;
import org.junit.jupiter.api.Test;
import second_visitor.FollowposTableEntry;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class DEA_ErzeugerTest {
    @Test
    public void testErzeuger(){
        Map<Integer, FollowposTableEntry> followposTable = new TreeMap<>();

        FollowposTableEntry followposTableEntry = new FollowposTableEntry(1, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTable.put(1, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(2, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTable.put(2, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(3, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(4));
        followposTable.put(3, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(4, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(5));
        followposTable.put(4, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(5, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(6));
        followposTable.put(5, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(6, "#");
        followposTableEntry.followpos.addAll(Arrays.asList());
        followposTable.put(6, followposTableEntry);


        Map<DFAState, Map<Character, DFAState>> transitionTable = new HashMap<>();

        DFAState DFA123 = new DFAState(0, false, new HashSet<>(Arrays.asList(1, 2, 3)));
        DFAState DFA1234 = new DFAState(1, false, new HashSet<>(Arrays.asList(1, 2, 3, 4)));
        DFAState DFA1235 = new DFAState(1, false, new HashSet<>(Arrays.asList(1, 2, 3, 5)));
        DFAState DFA1236 = new DFAState(1, false, new HashSet<>(Arrays.asList(1, 2, 3, 6)));

        Map<Character, DFAState> map = new HashMap<>();
        map.put('a', DFA1234);
        map.put('b', DFA123);
        transitionTable.put(DFA123, map);

        map = new HashMap<>();
        map.put('a', DFA1234);
        map.put('b', DFA1235);
        transitionTable.put(DFA1234, map);

        map = new HashMap<>();
        map.put('a', DFA1234);
        map.put('b', DFA1236);
        transitionTable.put(DFA1235, map);

        map = new HashMap<>();
        map.put('a', DFA1234);
        map.put('b', DFA123);
        transitionTable.put(DFA1236, map);

        DEA_Erzeuger dea_erzeuger = new DEA_Erzeuger(1, followposTable);
        assertEquals(transitionTable, dea_erzeuger.transitionMatrix);
    }
}
