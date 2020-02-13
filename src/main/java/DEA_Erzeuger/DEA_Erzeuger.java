package DEA_Erzeuger;

import com.sun.source.tree.Tree;
import lexer.DFAState;
import second_visitor.FollowposTableEntry;

import java.util.*;

public class DEA_Erzeuger {
    private Set<Character> inputAlphabet;
    private Map<Integer, FollowposTableEntry> followposTable;
    public Map<DFAState, Map<Character, DFAState>> transitionMatrix;
    private int positionEndSymbol;
    private static int indexStatic = 0;

    public DEA_Erzeuger(Map<Integer, FollowposTableEntry> followposTable){
        this.followposTable = followposTable;
        this.transitionMatrix = new HashMap<>();

        // Initialisation phase
        this.inputAlphabet = inputAlphabet(this.followposTable);

        Queue<DFAState> qStates = new LinkedList<>();
        TreeMap<Integer, FollowposTableEntry> treeMap = (TreeMap<Integer, FollowposTableEntry>)this.followposTable;
        qStates.add(new DFAState(indexStatic++, false, treeMap.get(treeMap.firstKey()).followpos));

        // The main loop
        while(!qStates.isEmpty()){
            DFAState s = qStates.poll();
            DFAState state = new DFAState(indexStatic++, false, s.positionsSet);

            Map<Character, DFAState> transitions = new HashMap<>();
            this.transitionMatrix.put(state, transitions);

            for(char a: this.inputAlphabet){
                Set<Integer> followingStates = getFollowingStates(state.positionsSet, a);

                DFAState newState = new DFAState(indexStatic++, followingStates.contains(this.positionEndSymbol), followingStates);

                transitionMatrix.get(state).put(a, newState);

                if(!this.transitionMatrix.containsKey(newState) &&
                        !qStates.contains(newState)){
                    qStates.add(newState);
                }
            }
        }
    }

    public void print(){
        for(DFAState state: this.transitionMatrix.keySet()){
            System.out.print(state.positionsSet.toString() + " ");
            for(Character a: this.inputAlphabet) {
                System.out.print(a + ": " + this.transitionMatrix.get(state).get(a).positionsSet.toString() + " ");
            }
            System.out.println();
        }
    }

    private Set<Character> inputAlphabet(Map<Integer, FollowposTableEntry> followposTableEntry){
        Set<Character> result = new HashSet<>();
        for(int i = 1; i <= followposTableEntry.size(); i++){
            if(followposTableEntry.get(i).symbol.charAt(0) != '#'){
                result.add(followposTableEntry.get(i).symbol.charAt(0));
            }else{
                this.positionEndSymbol = followposTableEntry.get(i).position;
            }
        }
        return result;
    }

    private Set<Integer> getFollowingStates(Set<Integer> positionsSet, char inputSymbol){
        Set<Integer> result = new HashSet<>();
        for(FollowposTableEntry followposTableEntry: this.followposTable.values()){
            if(positionsSet.contains(followposTableEntry.position) && followposTableEntry.symbol.charAt(0) == inputSymbol){
                result.addAll(followposTableEntry.followpos);
            }
        }
        return result;
    }
}
