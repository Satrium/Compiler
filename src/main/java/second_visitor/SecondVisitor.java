package second_visitor;

import syntaxtree.*;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class SecondVisitor implements Visitor {
    private SortedMap<Integer, FollowposTableEntry> followposTable = new TreeMap<>();

    public SortedMap<Integer, FollowposTableEntry> getFollowposTable() {
        return followposTable;
    }

    @Override
    public void visit(OperandNode node) {
        FollowposTableEntry followposTableEntry = new FollowposTableEntry(node.position, node.symbol);
        followposTable.put(followposTableEntry.position, followposTableEntry);
    }

    @Override
    public void visit(BinOpNode node) {
        Iterator<Integer> iterator = node.left.getLastPos().iterator();
        switch (node.operator) {
            case "°":
                while(iterator.hasNext()) {
                    int nextPosition = iterator.next();
                    FollowposTableEntry followposTableEntry = followposTable.get(nextPosition);
                    followposTableEntry.followpos.addAll(node.right.getFirstPos());
                    followposTable.put(nextPosition, followposTableEntry);
                }
                break;
            case "|":
                break;
            default:
                throw new RuntimeException("No valid BinOpNode.");
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        Iterator<Integer> iterator = node.getLastPos().iterator();
        switch (node.operator) {
            case "*":
            case "+":
                while(iterator.hasNext()) {
                    int nextPosition = iterator.next();
                    FollowposTableEntry followposTableEntry = followposTable.get(nextPosition);
                    followposTableEntry.followpos.addAll(node.getFirstPos());
                    followposTable.put(nextPosition, followposTableEntry);
                }
                break;
            case "?":
                break;
            default:
                throw new RuntimeException("No valid UnaryOpNode.");
        }
    }
}
