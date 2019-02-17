package ai.dataStructs;

import gameElements.Board;
import gameElements.Piece;
import gameElements.QuadraticBoard;

import ai.dataStructs.StateNode;
import ai.helper.BoardHelpers;

/**
 * @author Phillip Byram 
 * The Tree Struture the AI uses to make decisions
 *
 */
public class GameStateTree 
{

    private StateNode root;
    private int depth;

    public GameStateTree()
    {
        this.depth = 0;
        this.root = new StateNode();
    }

    public GameStateTree(int depth)
    {
        this.depth = depth;
        this.root = new StateNode(new QuadraticBoard());
//        this.root = new StateNode();
    }

    /**********************************************************
     * 
     * FIXME:  The buildTree function is not working correclty
     * yet.  It appears to drill down to the bottom layer fine.
     * For some reason, the board I keep for reference to the
     * original state of the board upon moving into the node
     * is being updated when I update the temporary board.  
     * It almost seems like I am passing a pointer to the
     * temp board, and so the original is being updated also.  
     * Phillip, If you're reading this, please know that I 
     * am exhausted.  If the above comments were even remotely
     * coherent, I would be very surprised.  Hopefully I'll 
     * get this fixed tomorrow and you'll never see any of this.
     * 
     */
    public void buildTree(StateNode node, int depth)
    {
        QuadraticBoard tmpBoard = new QuadraticBoard(); 
        tmpBoard.setSpaces(node.getBoard().getSpaces());
        Piece tmpPiece = Piece.NONE;
        int turn = depth % 2; // FIXME:  Couldn't find where player turn is defined. 

        for(int i = 0; i < node.getBoard().getSpaces().length; i++)
        {
            tmpBoard.setSpaces(node.getBoard().getSpaces());
            tmpBoard.showBoard();
            if(turn == 0)
            {
                tmpPiece = Piece.RED;
            }
            else if(turn == 1)
            {
                tmpPiece = Piece.BLACK;
            }
            
            try 
            {
                tmpBoard.placePiece(tmpPiece, i);
                node.addChild(new StateNode(tmpBoard));
                System.out.println("Current Depth:  " + depth);
                node.getBoard().showBoard();
                
            } 
            catch(QuadraticBoard.ColumnFullException e) 
            {            
                node.addChild(new StateNode(null));               
            }

            if(depth > 0)
            {
                buildTree(node.getChildAtIndex(i), depth - 1);
            }

        }
        return;
    }                           

    public StateNode getRoot()
    {
        return this.root;
    }

    public int getDepth()
    {
        return this.depth;
    }

    /**
     * Given a root node, prune irrelevant paths.
     * @param node the root node that will be pruned
     */
    public static void pruneTree(StateNode node)
    {

    }

    public static void populateUtility(StateNode node, int depth)
    {

    }


    public static void main(String[] args)
    {
        GameStateTree newTree = new GameStateTree(3);
        newTree.buildTree(newTree.getRoot(), newTree.getDepth());
    }


}