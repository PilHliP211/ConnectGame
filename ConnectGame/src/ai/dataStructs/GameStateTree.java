package ai.dataStructs;

import gameElements.Board;

/**
 * @author Phillip Byram The Tree Struture the AI uses to make decisions
 *
 */
public class GameStateTree {

    private StateNode root;
    private int depth;

    public GameStateTree(int depth, Board b){
        this.depth = depth;
        root = new StateNode(b);
    }

    /**
     * Given a root node, prune irrelevant paths.
     * @param node the root node that will be pruned
     */
    public static void pruneTree(StateNode node){

    }

    public int getDepth(){
        return depth;
    }

    public int incrementDepth(){
        return ++depth;
    }
    public void resetDepth(){
        System.out.println("resetting depth");
        depth = 0;
    }
    public StateNode getRoot(){
        return root;
    }
}