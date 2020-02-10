import java.util.*;

public class DEA_Parser {
    private Set<Character> inputAlphabet;
    public List<FollowposTableEntry> followposTable;
    private Map<DFAState, Map<Character, DFAState>> transitionMatrix;
    private int positionEndSymbol;

    public DEA_Parser(List<FollowposTableEntry> followposTable){
        this.followposTable = followposTable;
        this.transitionMatrix = new HashMap<>();
    }

    public void run(){
        // Initialisation phase
        this.inputAlphabet = inputAlphabet(this.followposTable);

        Queue<DFAState> qStates = new LinkedList<>();
        qStates.add(new DFAState(this.followposTable.get(0).followpos));

        // The main loop
        while(!qStates.isEmpty()){
            DFAState s = qStates.poll();
            DFAState state = new DFAState(s.positionSet);

            Map<Character, DFAState> transitions = new HashMap<>();
            this.transitionMatrix.put(state, transitions);

            for(char a: this.inputAlphabet){
                Set<Integer> followingStates = getFollowingStates(state.positionSet, a);

                DFAState newState = new DFAState(followingStates);

                transitionMatrix.get(state).put(a, newState);

                if(!transitionMatrixContainsStartingState(newState) &&
                !qStatesContainFollowingStates(qStates, followingStates)){
                    setAcceptingState(newState);
                    qStates.add(newState);
                }
            }
        }
    }
    
    private void setAcceptingState(DFAState state){
        if(state.positionSet.contains(this.positionEndSymbol)){
            state.isAcceptingState = true;
        }
    }

    public void print(){
        for(DFAState state: this.transitionMatrix.keySet()){
            System.out.print(state.positionSet.toString() + " ");
            for(Character a: this.inputAlphabet) {
                System.out.print(a + ": " + this.transitionMatrix.get(state).get(a).positionSet.toString() + " ");
            }
            System.out.println();
        }
    }

    private boolean transitionMatrixContainsStartingState(DFAState startingState){
        for(DFAState state: this.transitionMatrix.keySet()){
            if(state.equals(startingState)){
                return true;
            }
        }
        return false;
    }

    private boolean qStatesContainFollowingStates(Queue<DFAState> qStates, Set<Integer> followingStates){
        for(DFAState state: qStates){
            if(state.positionSet.equals(followingStates)){
                return true;
            }
        }
        return false;
    }

    private Set<Character> inputAlphabet(List<FollowposTableEntry> followposTableEntry){
        Set<Character> result = new HashSet<>();
        for(int i = 0; i < followposTableEntry.size(); i++){
            if(followposTableEntry.get(i).inputSymbol != '#'){
                result.add(followposTableEntry.get(i).inputSymbol);
            }else{
                this.positionEndSymbol = i;
            }
        }
        return result;
    }

    private Set<Integer> getFollowingStates(Set<Integer> positionsSet, char inputSymbol){
        Set<Integer> result = new HashSet<>();
        for(FollowposTableEntry followposTableEntry: this.followposTable){
            if(positionsSet.contains(followposTableEntry.position) && followposTableEntry.inputSymbol == inputSymbol){
                result.addAll(followposTableEntry.followpos);
            }
        }
        return result;
    }

    public static class FollowposTableEntry{
        private int position;
        private char inputSymbol;
        private Set<Integer> followpos;

        public FollowposTableEntry(int position, char inputSymbol, Set<Integer> followpos){
            this.inputSymbol = inputSymbol;
            this.followpos = followpos;
            this.position = position;
        }
    }
    

    private static class DFAState{
        private int index;
        public boolean isAcceptingState = false;
        public Set<Integer> positionSet;
        private static int indexStatic = 1;

        public DFAState(Set<Integer> positionSet) {
            this.index = DFAState.indexStatic++;
            this.positionSet = positionSet;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DFAState state = (DFAState) o;
            return this.positionSet.equals(state.positionSet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.positionSet);
        }
    }
}
