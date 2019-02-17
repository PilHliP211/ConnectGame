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

    private int difficulty = 10;
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
        return !(turn = !gameBoard.placePiece(myPiece,minMaxSearch(root)));
    }

    @Override
    public boolean isTurn() {
        return turn;
    }

    //TODO use incrementDepth() somehow
    private int minMaxSearch(StateNode root){
        StateNode node = new StateNode(root.getBoard());
        int val =Integer.MIN_VALUE;
        int a = -1;
        for(int i = 0; i<root.getBoard().getSpaces().length;i++){
            boolean goodColumn = true;
            Board b = null;
            try {
                b = (node.getBoard().placePiece(myPiece, i)?node.getBoard():null);
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn || b == null){
                int newVal = minValue(new StateNode(b),root.getBoard());
                if(newVal > val){ 
                    val = newVal;
                    a = i;
                }
            }
        }
        return a;
    }
    private int minValue(StateNode node,Board r){
        if (difficulty == tree.getDepth()) return Utility.calculate(r, myPiece);
        int val = Integer.MAX_VALUE;
        for(int i = 0;i<r.getSpaces().length;i++){
            boolean goodColumn = true;
            Board b = null;
            try{
                b = (r.placePiece(theirPiece, i)?r:null);
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn && b != null){
                StateNode n = new StateNode(b);
                val = Math.min(val, maxValue(n,r));
                node.addChild(n);
            }
            
        }
        tree.incrementDepth();
        return val;
    }
    private int maxValue(StateNode node,Board r){
        if (difficulty == tree.getDepth()) return Utility.calculate(r, myPiece);
        int val = Integer.MIN_VALUE;
        for(int i = 0;i<r.getSpaces().length;i++){
            boolean goodColumn = true;
            Board b = null;
            try{
                b = (r.placePiece(myPiece, i)?r:null);
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn && b != null){
                StateNode n = new StateNode(b);
                val = Math.max(val, minValue(n,r));
                node.addChild(n);
            }
            
        }
        tree.incrementDepth();
        return val;
    }
}
