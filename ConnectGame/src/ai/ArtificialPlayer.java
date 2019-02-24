package ai;

import gameElements.Board.ColumnFullException;
import ai.dataStructs.GameStateTree;
import ai.dataStructs.StateNode;
import ai.helper.Utility;
import gameElements.Board;
import gameElements.Piece;
import gameElements.Player;

/**
 * @author Phillip Byram 
 * An AI Player playing Connect
 *
 */
public class ArtificialPlayer implements Player {

    private int difficulty = 4;
    private GameStateTree tree;
    private Piece myPiece;
    private Piece theirPiece;
    private Board gameBoard;
    private boolean turn;
    
    public ArtificialPlayer(int difficulty, Piece p, Board b){
        this.difficulty = difficulty;
        this.myPiece = p;
        this.theirPiece = myPiece == Piece.RED?Piece.BLACK:Piece.RED;
        this.gameBoard = b;
    }
    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        Board b = gameBoard.copy();
        tree = new GameStateTree(0, b);
        StateNode root = tree.getRoot();
        //if place was successful, turn = false, but nextMove = true;
//        return !(turn = !gameBoard.placePiece(myPiece,minMaxSearch(root)));
        return !(turn = !gameBoard.placePiece(myPiece,abSearch(root)));
    }

    @Override
    public boolean isTurn() {
        return turn;
    }


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
                int newVal = minValue(new StateNode(b, root.getDepthInTree()+1));
                System.out.println("Column "+i+" has "+newVal+" Utility.");
                if(newVal > val)
                { 
                    val = newVal;
                    a = i;
                }
            }
        }
        return a;
    }

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
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn  && b != null)
            {
                int newAlpha = abMinValue(new StateNode(b, root.getDepthInTree()+1), root.getAlpha(), root.getBeta());
                if(newAlpha > root.getAlpha())
                { 
                    root.setAlpha(newAlpha);
                    a = i;
                }
//                int blockLoss = Utility.willLose(b, myPiece, theirPiece);
//                if(blockLoss>=0)
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
                System.out.println("abMinValue:  \n\tAlpha:  " + node.getAlpha() + "   Beta:  " + node.getBeta());
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
                System.out.println("abMaxValue:  \n\tAlpha:  " + node.getAlpha() + "   Beta:  " + node.getBeta());              
                if(node.getAlpha() >= node.getBeta())
                {
                    return Integer.MAX_VALUE;
                }
            }
                
        }
        return node.getBeta();
    }
}
