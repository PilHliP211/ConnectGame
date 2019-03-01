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
     * Constructor for the ArtificialPlayer class.
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

    /**
     * Overrides Player interface's nextMove function to allow the AI to make its
     * next move.  
     * 
     * @return boolean value indicating AI has made its move
     * @param boolean which player's turn it is
     * @throws NumberFormatException if number entered is outside valid bounds
     * @throws ColumnFullException if invalid move is chosen because column is full
     */
    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        Board b = gameBoard.copy();
        //if place was successful, turn = false, but nextMove = true;
        return !(turn = !gameBoard.placePiece(myPiece,abSearch(new StateNode(b))));
    }

    /**
     * Accessor method for ArtificialPlayer class turn variable.  Overrides Player
     * interface's isTurn function.  
     * 
     * @return boolean value indicating whether it is AI's turn
     */
    @Override
    public boolean isTurn() {
        return turn;
    }

    private int abSearch(StateNode root)
    {
        Random r = new Random();
        
        //holder for all columns that can be placed into
        //used in a final catch all in the case the a never changes from -1
        ArrayList<Integer> goodColumns = new ArrayList<Integer>();

        int a = -1;
        root.setAlpha(Integer.MIN_VALUE);
        root.setBeta(Integer.MAX_VALUE);

        for(int i = 0; i<root.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = root.getBoard().copy();
            try 
            {
                b = (b.copy().placePiece(myPiece, i)?b.copy():null);
                if(b!= null)
                    b.placePiece(myPiece, i);
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn  && b != null)
            {
                goodColumns.add(i);
                int newAlpha = abMinValue(new StateNode(b, root.getDepthInTree()+1), root.getAlpha(), root.getBeta());
                if(newAlpha > root.getAlpha())
                { 
                    root.setAlpha(newAlpha);
                    a = i;
                }
            }
        }
        if(a==-1) return goodColumns.get(r.nextInt(goodColumns.size()));

        return a;
    }


    private int abMinValue(StateNode node, int alpha, int beta)
    {
        node.setAlpha(alpha);
        node.setBeta(beta);

        if (difficulty <= node.getDepthInTree() || node.getBoard().didWin() != Piece.NONE)
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);

        for(int i = 0;i<node.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = null;
            try
            {
                b = (node.getBoard().copy().placePiece(theirPiece, i)?node.getBoard().copy():null);
                if(b != null)
                    b.placePiece(theirPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn && b != null)
            {
                StateNode n = new StateNode(b,node.getDepthInTree()+1); 
                node.setBeta(Math.min(node.getBeta(), abMaxValue(n, node.getAlpha(), node.getBeta())));
         
                if(node.getAlpha() >= node.getBeta())
                {
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

        if (difficulty <= node.getDepthInTree() || node.getBoard().didWin() != Piece.NONE) 
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);

        for(int i = 0;i<node.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = null;
            try
            {
                b = (node.getBoard().copy().placePiece(myPiece, i)?node.getBoard().copy():null);
                if(b!= null)
                    b.placePiece(myPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn && b != null)
            {
                StateNode n = new StateNode(b,node.getDepthInTree()+1);
                node.setAlpha(Math.max(node.getAlpha(), abMinValue(n, node.getAlpha(), node.getBeta())));
            
                if(node.getAlpha() >= node.getBeta())
                {
                    return Integer.MAX_VALUE;
                }
            }
                
        }
        return node.getAlpha();
    }
}
