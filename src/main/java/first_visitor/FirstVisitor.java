package first_visitor;

import syntaxtree.*;
import java.util.Iterator;



public class FirstVisitor implements Visitor {
    static int position =1;
    static int firsposition = 1;
    static int lastposition = 1;

    public static Visitable createTree(Visitable root){
        Visitor visitor = new FirstVisitor();

        DepthFirstIterator.traverse(root, visitor);
        return root;
    }


    @Override
    public void visit(BinOpNode node) {
        SyntaxNode childLeft = (SyntaxNode) node.left;
        SyntaxNode childRight = (SyntaxNode) node.right;
        switch (node.operator) {
            case "°":
                //bestimmt ob node nullable ist
                if(childLeft.nullable == true && childRight.nullable == true){
                    node.nullable = true;
                }else {node.nullable =false;}

                //berechnet firstpos
                if(childLeft.nullable == true){
                    node.firstpos.addAll(childLeft.firstpos);
                    node.firstpos.addAll(childRight.firstpos);
                } else {node.firstpos.addAll(childLeft.firstpos);}

               //berechnet lastpos
                if(childLeft.nullable == true){
                    node.lastpos.addAll(childLeft.lastpos);
                    node.lastpos.addAll(childRight.lastpos);
                } else {node.lastpos.addAll(childRight.lastpos);}
                break;

            case "|":
                //bestimmt ob node nullable ist
                if(childLeft.nullable == true || childRight.nullable == true){
                    node.nullable = true;
                }
                else {node.nullable = false;}
                //firstpos und lastpos berechnen
                node.firstpos.addAll(childLeft.firstpos);
                node.firstpos.addAll(childRight.firstpos);
                node.lastpos.addAll(childLeft.lastpos);
                node.lastpos.addAll(childRight.lastpos);
                break;

            default:
                throw new RuntimeException("No valid BinOpNode.");
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
            SyntaxNode subNode = (SyntaxNode) node.subNode;
            switch (node.operator) {
                case "*":
                case "?":
                    node.nullable = true;
                    node.firstpos.addAll(subNode.firstpos);
                    node.lastpos.addAll(subNode.lastpos);
                    break;
                case "+":
                    node.nullable = subNode.nullable;
                    node.firstpos.addAll(subNode.firstpos);
                    node.lastpos.addAll(subNode.lastpos);
                    break;
                default:
                    throw new RuntimeException("No valid UnaryOpNode.");
            }
        }


    @Override
    public void visit(OperandNode node) {
        node.nullable = false;
        node.firstpos.add(firsposition);
        node.lastpos.add(lastposition);
        node.position = position;
        firsposition++;
        lastposition++;
        position++;
    }
}




