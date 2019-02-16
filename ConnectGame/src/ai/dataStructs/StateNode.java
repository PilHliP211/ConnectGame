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
    Integer posInf = Integer.MAX_VALUE;
    Integer negInf = Integer.MIN_VALUE;

    QuadraticBoard board;
    ArrayList<StateNode> children;
    int utility;
    int alpha;
    int beta;
    int depthInTree;

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
        this.depthInTree = 0;
    }


    public StateNode(Board b)
    {
        // FIXME:  Need to add getDimension() to Board interface.
        this.board = new QuadraticBoard(b.getDimension(), b.getWinCondition());
        this.board.setSpaces(b.getSpaces());
        this.alpha = negInf;
        this.beta = posInf;
        this.depthInTree = 0;
        this.children = new ArrayList<StateNode>();
//        for(int i = 0; i < this.board.getWinCondition(); i++)
//        {
//
//        }
    }

    public StateNode(Board b, int depth)
    {
        // FIXME:  Need to add getDimension() to Board interface.
        this.board = new QuadraticBoard(b.getDimension(), b.getWinCondition());
        this.board.setSpaces(b.getSpaces());
        this.alpha = negInf;
        this.beta = posInf;
        this.depthInTree = 0;
        this.depthInTree = depth;
        this.children = new ArrayList<StateNode>();
    }

    public void createChild(StateNode parent)
    {

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

    public int getAlpha()
    {
        return this.alpha;
    }

    public void setAlpha(int a)
    {
        this.alpha = a;
    }

    public int getBeta()
    {
        return this.beta;
    }

    public void setBeta(int b)
    {
        this.beta = b;
    }

    public int getUtility()
    {
        return this.utility;
    }

    public void setUtility(int u)
    {
        this.utility = u;
    }

    public int getDepthInTree()
    {
        return this.depthInTree;
    }

    public void setDepthInTree(int d)
    {
        this.depthInTree = d;
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