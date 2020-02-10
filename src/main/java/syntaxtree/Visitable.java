package syntaxtree;

import java.util.Set;

public interface Visitable
{
    void accept(Visitor visitor);
    Set<Integer> getFirstPos();
    Set<Integer> getLastPos();
}