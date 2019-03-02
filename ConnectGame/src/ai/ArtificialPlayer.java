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
        // Random Generator used to select column when AI cannot win
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
            // work on a copy of the board so we never have to remove pieces
            Board b = root.getBoard().copy();

            // Try/Catch block to check that column is not full, and place piece at 
            // column i if it is not.
            try 
            {
                // check if we can place piece here
                b = (b.copy().placePiece(myPiece, i)?b.copy():null);
                // if the place piece worked, let's place it
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
                // place piece worked, let's progress through the tree.

                // Add good column to ArrayList for final catch, if needed
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
        // At this point if a == -1, we are in bad shape... Every column gave us minimal utility. 
        // This only happens if we are 100% going to lose assuming min plays logically.
        // Place a piece randomly and hope the opponent does something dumb.
        if(a==-1) return goodColumns.get(r.nextInt(goodColumns.size()));

        // After tree traversal complete, return best possible action as determined by 
        // Min-Max functions.
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
