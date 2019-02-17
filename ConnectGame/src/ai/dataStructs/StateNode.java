
package ai.dataStructs;

import java.util.ArrayList;
import gameElements.Board;

import gameElements.QuadraticBoard;
import gameElements.Piece;

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
    

    /**
     * Constructor for default node.  Best to setup tree's root node. 
     */
    public StateNode()
    {
        board.initBoard();
        this.utility = 0;
        this.alpha = negInf;
        this.beta = posInf;
        this.children = new ArrayList<StateNode>();

    }

    /**
     * Constructor 
     * @param b
     */
    public StateNode(Board b)
    {
        this.board = b;
        this.utility = 0;
        this.alpha = negInf;
        this.beta = posInf;
        this.children = new ArrayList<StateNode>();
    }

    /** 
     *  Add child to the end of ArrayList
     *  @param Statenode child to add
     */
    public void addChild(StateNode child)
    {
        this.children.add(child);
    }

    /**
     *  Add child to ArrayList at specified index
     *  @param int element to add to ArrayList
     */
    public void addChildAtIndex(int index, StateNode child)
    {
        this.children.add(index, child);
    }

    /**
     *  Remove child at index
     *  @param int location index to remove child
     */
    public void removeChild(int index)
    {
        this.children.remove(index);
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
     *  Return ArrayList from current node
     *  @return ArrayList of children
     */
    public ArrayList<StateNode> getChildren()
    {
        return this.children;
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
     * Accessor method for board object
     * @return Board object
     */
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * Mutator method for board object
     * @param b board to set in node
     */

    public void setBoard(Board b)
    {
        this.board = b;
    }



    /**
     * Main function to test StateNode class.
     * @param args
     */
    public static void main(String[] args)
    {
        Board tBoard = BoardHelpers.generateTestBoard(10, 10, 4);
        tBoard.showBoard();

        StateNode node = new StateNode(tBoard);
//        node.board.showBoard();
        node.showNode();
    }

}