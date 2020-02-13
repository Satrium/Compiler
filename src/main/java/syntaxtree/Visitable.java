package syntaxtree;

import java.util.Set;

public interface Visitable
{
    void accept(Visitor visitor);
}