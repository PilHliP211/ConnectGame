package ai.dataStructs;

import java.util.ArrayList;

import gameElements.Board;

/**
 * @author Phillip Byram 
 * A Node in The Tree Struture
 *
 */
public class StateNode {

    Board board;
    ArrayList<StateNode> children;
    int utility;

    public StateNode(){
        board.initBoard();
    }



    public StateNode getChildAtIndex(int index){
        return null;
    }


}