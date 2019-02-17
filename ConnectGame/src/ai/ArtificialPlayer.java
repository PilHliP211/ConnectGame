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
    private Piece piece;
    private Board gameBoard;
    private boolean turn;
    
    public ArtificialPlayer(int difficulty, Piece p, Board b){
        this.difficulty = difficulty;
        this.piece = p;
        this.gameBoard = b;
    }
    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        Board b = gameBoard.copy();
        tree = new GameStateTree(0, b);
        Board rootBoard = tree.getRoot().getBoard();
        //if place was successful, turn = false, but nextMove = true;
        return !(turn = !rootBoard.placePiece(piece,minMaxSearch(rootBoard)));
    }

    @Override
    public boolean isTurn() {
        return turn;
    }

    //TODO use incrementDepth() somehow
    private int minMaxSearch(Board b){
        StateNode node = new StateNode(b);
        int val =Integer.MIN_VALUE;
        int a = -1;
        for(int i = 0; i<b.getSpaces().length;i++){
            boolean goodColumn = true;
            StateNode newNode = null;
            try { 
                newNode = new StateNode((node.getBoard().placePiece(piece, i)?node.getBoard():null));
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn || newNode == null){
                int newVal = minValue(newNode,node.getBoard());
                if(newVal > val){ 
                    val = newVal;
                    a = i;
                }
            }
        }
        return a;
    }
    private int minValue(StateNode node,Board b){
        if (difficulty == tree.getDepth()) return Utility.calculate(b, piece);
        int val = Integer.MAX_VALUE;
        for(int i = 0;i<b.getSpaces().length;i++){
            boolean goodColumn = true;
            StateNode newNode = null;
            try{
                newNode = new StateNode((b.placePiece(piece, i)?b:null));
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn || newNode == null){
                val = Math.min(val, maxValue(newNode,b));
            }
            
        }
        return val;
    }
    private int maxValue(StateNode node,Board b){
        if (difficulty == tree.getDepth()) return Utility.calculate(b, piece);
        int val = Integer.MIN_VALUE;
        for(int i = 0;i<b.getSpaces().length;i++){
            boolean goodColumn = true;
            StateNode newNode = null;
            try{
                newNode = new StateNode((b.placePiece(piece, i)?b:null));
            } catch (ColumnFullException cfe){
                goodColumn = false;
            }
            if(goodColumn || newNode == null){
                val = Math.max(val, minValue(newNode,b));
            }
            
        }
        return val;
    }
}
