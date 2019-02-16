package ai;

import gameElements.Board.ColumnFullException;
import ai.dataStructs.GameStateTree;
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
        tree = new GameStateTree(difficulty);
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

    private void minMaxSearch(){
        
    }
}
