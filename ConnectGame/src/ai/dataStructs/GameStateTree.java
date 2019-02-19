package ai.dataStructs;

import gameElements.Board;
import gameElements.Piece;
import gameElements.QuadraticBoard;

import ai.dataStructs.StateNode;
import ai.helper.BoardHelpers;
import ai.helper.Utility;

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
     * The buildTree function appears to be building the tree 
     * properly now that it is implementing the copy function 
     * in the QuadraticBoard class.  Still need to think about
     * how to track player turn, and if it is necessary.  Need  
     * to test the function more thoroughly to ensure proper 
     * operation. 
     * 
     */
    public void buildTree(StateNode node, int depth)
    {
        Board tmpBoard;
        Piece tmpPiece = Piece.NONE;
        int turn = depth % 2; // FIXME:  Couldn't find where player turn is defined. 

        if(turn == 0)
        {
            tmpPiece = Piece.RED;
        }
        else if(turn == 1)
        {
            tmpPiece = Piece.BLACK;
        }

        for(int i = 0; i < node.getBoard().getSpaces().length; i++)
        {
            tmpBoard = node.getBoard().copy();
            try 
            {
                tmpBoard.placePiece(tmpPiece, i);
                node.addChild(new StateNode(tmpBoard));
                if(depth == 0)
                {
                    node.setUtility(Utility.calculateAll(tmpBoard, tmpPiece));
                    //FIXME:  Print statement for testing.
//                    System.out.println("Utility:  " + node.getUtility());
                }
                // FIXME:  Below statements are for testing.  Remove once proper 
                //         operation is verified. 
//                System.out.println("Current Depth:  " + depth);
//                node.getBoard().showBoard();
//                System.out.println("Utility:  " + node.getUtility());
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
    


    /**
     * Accessor method for root node
     * @return StateNode object
     */
    public StateNode getRoot()
    {
        return this.root;
    }

    /**
     * Accessor method for tree depth
     * @return
     */
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
    // I integrated utility population with the build tree function
    public static void populateUtility(StateNode node, int depth)
    {

    }


    public static void main(String[] args)
    {
        GameStateTree newTree = new GameStateTree(3);
        newTree.buildTree(newTree.getRoot(), newTree.getDepth());
    }
}