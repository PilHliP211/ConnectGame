
package ai.dataStructs;

import gameElements.Board;

import ai.helper.BoardHelpers;

/**
 * @author Phillip Byram 
 * A Node in The Tree Struture
 *
 */
public class StateNode 
{
    private static final int POS_INF = Integer.MAX_VALUE;
    private static final int NEG_INF = Integer.MIN_VALUE;

    private Board board;
    private int alpha;
    private int beta;
    private int depthInTree = 0;

    /**
     * Creates default node.  Best to setup tree's root node. 
     */
    public StateNode()
    {
        board.initBoard();
        this.alpha = NEG_INF;
        this.beta = POS_INF;
    }

    /**
     * Creates a node with a board
     * @param b a board
     */
    public StateNode(Board b)
    {
        this.board = b;
        this.alpha = NEG_INF;
        this.beta = POS_INF;
    }

    /**
     * Creates a node with a board a certain depth down the tree
     * @param b a board
     * @param depth the depth in the tree
     */
    public StateNode(Board b, int depth)
    {
        this.board = b;
        this.alpha = NEG_INF;
        this.beta = POS_INF;
        this.depthInTree = depth;
    }
    
    /**
     *  Prints node data to System.out
     * 
     */
    public void showNode()
    {
        System.out.println("Node Data:  ");
        System.out.println("--------------------------------------------");
        System.out.println("Alpha:  \t" + this.alpha);
        System.out.println("Beta:   \t" + this.beta);
        this.board.showBoard();
        System.out.println("--------------------------------------------");
    }

    /**
     * Gets current node's depth in the tree.
     * @return value of depth in tree.
     */
    public int getDepthInTree()
    {
        return this.depthInTree;
    }

    /**
     * Accessor method for variable alpha
     * @return value of alpha
     */
    public int getAlpha()
    {
        return this.alpha;
    }
    
    /**
     * Mutator method for variable alpha
     * @param a value of alpha
     */
    public void setAlpha(int a)
    {
        this.alpha = a;
    }
    
    /**
     * Accessor method for variable beta
     * @return value of beta
     */
    public int getBeta()
    {
        return this.beta;
    }

    /**
     * Mutator method for variable beta
     * @param b value of beta
     */
    public void setBeta(int b)
    {
        this.beta = b;
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
     * @param args none
     */
    public static void main(String[] args)
    {
        Board tBoard = BoardHelpers.generateTestBoard(10, 10, 4);
        tBoard.showBoard();

        StateNode node = new StateNode(tBoard);
        node.showNode();
    }

}