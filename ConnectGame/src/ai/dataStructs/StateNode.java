
package ai.dataStructs;

import java.util.ArrayList;
import gameElements.Board;

import ai.helper.BoardHelpers;


/**
 * @author Phillip Byram 
 * A Node in The Tree Struture
 *
 */
public class StateNode 
{
    private Integer posInf = Integer.MAX_VALUE;
    private Integer negInf = Integer.MIN_VALUE;

    private Board board;
    private ArrayList<StateNode> children;
    private int utility;
    private int alpha;
    private int beta;
    private int depthInTree;
    

    /**
     * Creates default node.  Best to setup tree's root node. 
     */
    public StateNode()
    {
        board.initBoard();
        this.children = new ArrayList<StateNode>();
        this.utility = 0;
        this.alpha = negInf;
        this.beta = posInf;
    }


    public StateNode(Board b)
    {
        this.board = b;
        this.alpha = negInf;
        this.beta = posInf;
        this.children = new ArrayList<StateNode>();
    }


    public StateNode(Board b, int depth)
    {
        this.board = b;
        this.alpha = negInf;
        this.beta = posInf;
        this.children = new ArrayList<StateNode>();
        this.depthInTree = depth;
    }
 
    public void addChild(StateNode child)
    {
        children.add(child);
    }

    /**
     *  Get child from arraylist at index
     * 
     * @param index
     * @return StateNode
     */
    public StateNode getChildAtIndex(int index)
    {
        return this.children.get(index);
    }

    
    /**
     *  Prints node data to terminal
     * 
     */
    public void showNode()
    {
        System.out.println("Node Data:  ");
        System.out.println("--------------------------------------------");
        System.out.println("Utility:\t" + this.utility);
        System.out.println("Alpha:  \t" + this.alpha);
        System.out.println("Beta:   \t" + this.beta);
        this.board.showBoard();
        System.out.println("--------------------------------------------");
    }

    /**
     * Gets current node's depth in the tree.
     * @return  integer value of depth in tree.
     */

    public int getDepthInTree()
    {
        return this.depthInTree;
    }

    /**
     * increments the current node's depth in the tree.
     */

    public void incrementDepthInTree()
    {
        this.depthInTree++;
    }

    /**
     * Decrements the current node's depth in the tree.
     */

    public void decrementDepthInTree()
    {
        this.depthInTree--;
    }
    /**
     * Accessor method for variable alpha
     * @return int value of alpha
     */
    public int getAlpha()
    {
        return this.alpha;
    }
    /**
     * Mutator method for variable alpha
     * @param int value of alpha
     */
    public void setAlpha(int a)
    {
        this.alpha = a;
    }
    
    /**
     * Accessor method for variable beta
     * @return int value of beta
     */
    public int getBeta()
    {
        return this.beta;
    }

    /**
     * Mutator method for variable beta
     * @param int value of alpha
     */
    public void setBeta(int b)
    {
        this.beta = b;
    }

    /**
     * Accessor method for variable utility
     * @return int value of utility
     */
    public int getUtility()
    {
        return this.utility;
    }

    /**
     * Mutator method for variable utility
     * @param int value of utility
     */
    public void setUtility(int u)
    {
        this.utility = u;
    }

    /**
     * Accessor method for board
     * @return Board value of utility
     */
    public Board getBoard()
    {
        return this.board;
    }





    /**
     * Main function to test StateNode class.
     * @param args
     */
    public static void main(String[] args) //throws DimensionsOOBException{
    {
        Board tBoard = BoardHelpers.generateTestBoard(10, 10, 4);
        tBoard.showBoard();

        StateNode node = new StateNode(tBoard);
//        node.board.showBoard();
        node.showNode();
    }

}