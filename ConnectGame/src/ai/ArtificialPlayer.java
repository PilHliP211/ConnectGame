package ai;

import gameElements.Board.ColumnFullException;
import ai.dataStructs.StateNode;
import ai.helper.Utility;
import gameElements.Board;
import gameElements.Piece;
import gameElements.Player;

//FIXME:  added import BoardHelpers
import ai.helper.BoardHelpers;

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
        this.theirPiece = myPiece == Piece.RED?Piece.BLACK:Piece.RED;
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
        return !(turn = !gameBoard.placePiece(myPiece,minMaxSearch(new StateNode(b))));

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
     * min/max search function calls recursive minValue and maxValue functions
     * to determine best move based on results returned from utility funciton.  
     * Superceded by abSearch function that provides the same functionality with 
     * added alpha/beta tree pruning.  
     * 
     * @return integer value indicting board position of best move
     * @param StateNode object which is passed as the root of the min/max tree
     */

    private int minMaxSearch(StateNode root)
    {
        int val =Integer.MIN_VALUE;
        int a = -1;
        for(int i = 0; i<root.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = root.getBoard().copy();
            try 
            {
                b = (b.copy().placePiece(myPiece, i)?b.copy():null);
                if(b!= null)
                    b.placePiece(myPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn  && b != null)
            {
                if(Utility.calculate(b, myPiece, theirPiece)==Integer.MAX_VALUE)
                    return i;
                int newVal = minValue(new StateNode(b, root.getDepthInTree()+1));
                if(newVal > val)
                { 
                    val = newVal;
                    a = i;
                }
            }
        }
        return a;
    }

    /**
     * One of two recursive functions that return the best possible move for the
     * AI.  The min Function chooses the worst branch for the player.  Superceded
     * by abMinValue function that provides the same functionality with added 
     * alpha/beta tree pruning.
     * 
     * @return integer value indicating the lowest utility as determined by the utility function
     * @param StateNode object, which is the local root node
     */

    private int minValue(StateNode node)
    {
        if (difficulty <= node.getDepthInTree())
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);
        int val = Integer.MAX_VALUE;
        for(int i = 0;i<node.getBoard().getSpaces().length;i++)
        {
            boolean goodColumn = true;
            Board b = null;
            try
            {
                b = (node.getBoard().copy().placePiece(theirPiece, i)?node.getBoard().copy():null);
                if(b!= null)
                    b.placePiece(theirPiece, i);
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn && b != null)
            {
                StateNode n = new StateNode(b,node.getDepthInTree()+1);
                val = Math.min(val, maxValue(n));
            }
            
        }
        return val;
    }


    /**
     * One of two recursive functions that return the best possible move for the
     * AI.  The max Function chooses the best branch for the AI.  Superceded
     * by abMaxValue function that provides the same functionality with added 
     * alpha/beta tree pruning.
     * 
     * @return integer value indicating the highest utility as determined by the utility function
     * @param StateNode object, which is the local root node
     */
    private int maxValue(StateNode node)
    {
        if (difficulty <= node.getDepthInTree()) 
            return Utility.calculate(node.getBoard(), myPiece, theirPiece);
        int val = Integer.MIN_VALUE;
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
                val = Math.max(val, minValue(n));

            }
            
        }
        return val;
    }




    private int abSearch(StateNode root)
    {
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
            } 
            catch (ColumnFullException cfe)
            {
                goodColumn = false;
            }
            if(goodColumn  && b != null)
            {
                if(Utility.calculate(b, myPiece, theirPiece)==Integer.MAX_VALUE)
                    return i;
                int newAlpha = abMinValue(new StateNode(b, root.getDepthInTree()+1), root.getAlpha(), root.getBeta());
                if(newAlpha > root.getAlpha())
                { 
                    root.setAlpha(newAlpha);
                    a = i;
                }
            }
        }
        return a;
    }


    private int abMinValue(StateNode node, int alpha, int beta)
    {
        node.setAlpha(alpha);
        node.setBeta(beta);

        if (difficulty <= node.getDepthInTree())
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
         
                //FIXME:  Print statment for checking alpha/beta values
                System.out.println("abMinValue Depth:  " + node.getDepthInTree() +"\n\tAlpha:  " + node.getAlpha() + "   Beta:  " + node.getBeta());
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

        if (difficulty <= node.getDepthInTree()) 
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
            
                //FIXME:  Print statment for checking alpha/beta values 
                System.out.println("abMaxValue Depth:  " + node.getDepthInTree() +"\n\tAlpha:  " + node.getAlpha() + "   Beta:  " + node.getBeta());        
                if(node.getAlpha() >= node.getBeta())
                {
                    return Integer.MAX_VALUE;
                }
            }
                
        }
        return node.getAlpha();
    }
}
