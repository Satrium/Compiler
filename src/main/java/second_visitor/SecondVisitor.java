package second_visitor;

import syntaxtree.BinOpNode;
import syntaxtree.OperandNode;
import syntaxtree.UnaryOpNode;
import syntaxtree.Visitor;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecondVisitor implements Visitor {
    private SortedMap<Integer, FollowposTableEntry> followposTableEntries;

    public SecondVisitor() {
        followposTableEntries = new TreeMap<>();
    }

    @Override
    public void visit(OperandNode node) {
        FollowposTableEntry followposTableEntry = new FollowposTableEntry(node.position, node.symbol);
        followposTableEntries.put(followposTableEntry.position, followposTableEntry);
    }

    @Override
    public void visit(BinOpNode node) {
        // ° |
        Iterator<Integer> iterator = node.left.getLastPos().iterator();
        switch (node.operator) {
            case '°':
                while(iterator.hasNext()) {
                    FollowposTableEntry followposTableEntry = 
                }
                break;
            case '|':
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        // * + ?
    }
}
