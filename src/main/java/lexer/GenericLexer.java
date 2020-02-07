package lexer;

import java.util.Map;

public class GenericLexer {

    private final Map<DFAState, Map<Character, DFAState>> matrix;

    public GenericLexer(Map<DFAState, Map<Character, DFAState>> matrix){
        this.matrix = matrix;
    }

    public boolean match(String s){
        char[] chars = s.toCharArray();
        DFAState state = null;
        //Find start state
        for(DFAState tmpState:matrix.keySet()){
            if(tmpState.index == 0){
                state = tmpState;
                break;
            }
        }
        if(state == null)throw new IllegalArgumentException("The matrix does not contain a starting state");
        for(char letter:chars){
            if(matrix.containsKey(state) && matrix.get(state).containsKey(letter)){
                state = matrix.get(state).get(letter);
            }else{
                return false;
            }
        }
        return state.isAcceptingState;
    }
}
