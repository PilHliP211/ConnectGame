package ai;

import gameElements.Board.ColumnFullException;
import ai.dataStructs.GameStateTree;
import gameElements.Board;
import gameElements.Player;

/**
 * @author Phillip Byram 
 * An AI Player playing Connect
 *
 */
public class ArtificialPlayer implements Player {

    private int difficulty = 10;
    private GameStateTree tree;
    
    public ArtificialPlayer(int difficulty){
        this.difficulty = difficulty;
    }
    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        minMaxSearch();
        return false;
    }

    @Override
    public boolean isTurn() {
        return false;
    }

    private int minMaxSearch(Board b){
        tree = new GameStateTree(difficulty);
        int v =Integer.MIN_VALUE;
        int a = -1;
        for(int i = 0; i<b.getSpaces().length;i++){
            int newV = minvalue(b.placePiece(tree.root, i));
            if(newV > v){ 
                v = newV;
                a = i;
            }
            return a;
        }
    }

    private boolean play(int col)
    {
    }
}
