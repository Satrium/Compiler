package second_visitor;

import org.junit.jupiter.api.Test;
import syntaxtree.DepthFirstIterator;
import syntaxtree.Visitable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondVisitorTest {

    @Test
    public void testCorrectFollowposTable() {
        // (a|b)*abb#
        Visitable syntaxTree = ;

        // Expected FollowposTable
        SortedMap<Integer, FollowposTableEntry> expected = new TreeMap<>();

        FollowposTableEntry followposTableEntry = new FollowposTableEntry(1, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        expected.put(1, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(2, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(1, 2, 3));
        expected.put(2, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(3, "a");
        followposTableEntry.followpos.addAll(Arrays.asList(4));
        expected.put(3, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(4, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(5));
        expected.put(4, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(5, "b");
        followposTableEntry.followpos.addAll(Arrays.asList(6));
        expected.put(5, followposTableEntry);

        followposTableEntry = new FollowposTableEntry(6, "#");
        followposTableEntry.followpos.addAll(Arrays.asList());
        expected.put(6, followposTableEntry);

        DepthFirstIterator.traverse(syntaxTree, visitor);

        SortedMap<Integer, FollowposTableEntry> result = visitor.getFollowposTable();
        assertEquals(expected, result);
    }
}
