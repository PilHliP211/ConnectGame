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
    
    /**
     * The top level max function for the Artificial Player class' Game
     * Tree.  abSearch calls the abMinValue function that starts the recursive
     * traversal of the Game Tree.    
     *  
     * @return integer value indicating the AI's best move
     * @param StateNode root node object
     */

    private int abSearch(StateNode root)
    {
        // Random Integer Generator used to select column when AI cannot win
        Random r = new Random();
        
        //holder for all columns that can be placed into
        //used in a final catch all in the case the action variable 'a' never changes from -1
        ArrayList<Integer> goodColumns = new ArrayList<Integer>();
        
        // Declare and initialize the action variable to its initial state
        int a = -1;
        
        // Set Alpha and Beta values to -inf and +inf to be passed to children
        root.setAlpha(Integer.MIN_VALUE);
        root.setBeta(Integer.MAX_VALUE);
        
        // Iterate through all columns on the board and place AI's piece
        for(int i = 0; i<root.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            
            // Copy of parent board to be passed to children 
            Board b = root.getBoard().copy();

            // Try/Catch block to check that column is not full, and place piece at 
            // column i if it is not.
            try 
            {
                // placePiece function from board class throws ColumnFullException
                // If column is not full, copy board to be passed to child, else
                // board is null.
                b = (b.copy().placePiece(myPiece, i)?b.copy():null);

                // Place next piece on board at location i if column empty
                if(b!= null)
                    b.placePiece(myPiece, i);
            } 
            catch (ColumnFullException cfe){
                goodColumn = false;
            }

            // If column is empty and board is not null, pass new board to child in 
            // abMinValue function.  
            if(goodColumn  && b != null)
            {
                // Add good column to ArrayList for final catch
                //FIXME:  We are adding a lot of columns here, and we never remove any.  
                //        Maybe we should add bad columns in catch block?
                goodColumns.add(i);

                // Call recursive abMinValue function.  Store current child alpha value.
                int newAlpha = abMinValue(new StateNode(b, root.getDepthInTree()+1), root.getAlpha(), root.getBeta());
                
                // If current child alpha value is greater than root alpha value, store current
                // child alpha value in root alpha variable to be passed to next child. Also, 
                // select current for loop iteration i value as current best action.
                if(newAlpha > root.getAlpha())
                { 
                    root.setAlpha(newAlpha);
                    a = i;
                }
            }
        }

        // If algorithm's best action is initial state, then the AI loses.  In this
        // event, have AI select random columns so that player may finish out the game.
        if(a==-1) return goodColumns.get(r.nextInt(goodColumns.size()));

        // After tree traversal complete, return best possible action as determined by 
        // Min-Max functions.
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
