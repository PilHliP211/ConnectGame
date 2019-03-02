package ai;

import gameElements.Board.ColumnFullException;

import java.util.ArrayList;
import java.util.Random;

import ai.dataStructs.StateNode;
import ai.helper.Utility;
import gameElements.Board;
import gameElements.Piece;
import gameElements.Player;

/**
 * @author Phillip Byram 
 * @author Russell Plenkers
 * An AI Player playing Connect
 *
 */
public class ArtificialPlayer implements Player {

    private int difficulty = 6;
    private Piece myPiece;
    private Piece theirPiece;
    private Board gameBoard;
    private boolean turn;
    
    /**
     * Constructor for the ArtificialPlayer.
     * 
     * @param integer value indicating depth of tree traversal
     * @param Piece enumerator type for artificial player
     * @param Board object indicating the game's current board progress
     */
    public ArtificialPlayer(int difficulty, Piece p, Board b){
        this.difficulty = difficulty;
        this.myPiece = p;
        this.theirPiece = myPiece == Piece.X?Piece.O:Piece.X;
        this.gameBoard = b;
    }

    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        Board b = gameBoard.copy();
        //if place was successful, turn = false, but nextMove = true;
        return !(turn = !gameBoard.placePiece(myPiece,abSearch(new StateNode(b))));

    }

    @Override
    public boolean isTurn() {
        return turn;
    }

    private int abSearch(StateNode root)
    {
        Random r = new Random();
        
        //holder for all columns that can be placed into
        //used in a final catch all in case a never changes from -1
        ArrayList<Integer> goodColumns = new ArrayList<Integer>();

        int a = -1;
        root.setAlpha(Integer.MIN_VALUE);
        root.setBeta(Integer.MAX_VALUE);

        for(int i = 0; i<root.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            // work on a copy of the board so we never have to remove pieces
            Board b = root.getBoard().copy();
            try 
            {
                // check if we can place piece here
                b = (b.copy().placePiece(myPiece, i)?b.copy():null);
                // if the place piece worked, let's place it
                if(b!= null)
                    b.placePiece(myPiece, i);
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn  && b != null)
            {
                // place piece worked, let's progress through the tree.
                goodColumns.add(i);
                int newAlpha = abMinValue(new StateNode(b, root.getDepthInTree()+1), root.getAlpha(), root.getBeta());
                if(newAlpha > root.getAlpha())
                { 
                    root.setAlpha(newAlpha);
                    a = i;
                }
            }
        }
        // At this point if a == -1, we are in bad shape... Every column gave us minimal utility. 
        // This only happens if we are 100% going to lose assuming min plays logically.
        // Place a piece randomly and hope the opponent does something dumb.
        if(a==-1) return goodColumns.get(r.nextInt(goodColumns.size()));
        return a;
    }


    private int abMinValue(StateNode node, int alpha, int beta)
    {
        node.setAlpha(alpha);
        node.setBeta(beta);

        // cutoff test: leaf node or the game is over.
        if (difficulty <= node.getDepthInTree() || node.getBoard().didWin() != Piece.NONE)
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);

        for(int i = 0;i<node.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = null;
            try
            {
                // check if we can place piece here
                b = (node.getBoard().copy().placePiece(theirPiece, i)?node.getBoard().copy():null);
                // if the place piece worked, let's place it
                if(b != null)
                    b.placePiece(theirPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn && b != null)
            {
                // place piece worked, let's progress through the tree.
                StateNode n = new StateNode(b,node.getDepthInTree()+1); 
                node.setBeta(Math.min(node.getBeta(), abMaxValue(n, node.getAlpha(), node.getBeta())));
         
                if(node.getAlpha() >= node.getBeta())
                {
                    // prune neighbors
                    return Integer.MIN_VALUE;
                }
            }
            
        }
        return node.getBeta();
    }



    private int abMaxValue(StateNode node, int alpha, int beta)
    {
        node.setAlpha(alpha);
        node.setBeta(beta);

        // cutoff test: leaf node or the game is over.
        if (difficulty <= node.getDepthInTree() || node.getBoard().didWin() != Piece.NONE) 
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);

        for(int i = 0;i<node.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = null;
            try
            {
                // check if we can place piece here
                b = (node.getBoard().copy().placePiece(myPiece, i)?node.getBoard().copy():null);
                // if the place piece worked, let's place it
                if(b!= null)
                    b.placePiece(myPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn && b != null)
            {
                // place piece worked, let's progress through the tree.
                StateNode n = new StateNode(b,node.getDepthInTree()+1);
                node.setAlpha(Math.max(node.getAlpha(), abMinValue(n, node.getAlpha(), node.getBeta())));
            
                if(node.getAlpha() >= node.getBeta())
                {
                    // prune neighbors
                    return Integer.MAX_VALUE;
                }
            }
                
        }
        return node.getAlpha();
    }
}
