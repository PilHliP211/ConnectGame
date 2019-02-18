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

    private int difficulty = 6;
    private GameStateTree tree;
    
    public ArtificialPlayer(int difficulty){
        this.difficulty = difficulty;
        tree = new GameStateTree(difficulty);
        tree.buildTree(tree.getRoot(), tree.getDepth());
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

    public static void main(String[] args)
    {
        // WARNING:  Do not recommend depth greater than 6.  I set it to 7, and my 
        // laptop was working with all 8 threads at 100% for more than 10 minutes
        // before I terminated the process.
        ArtificialPlayer ai = new ArtificialPlayer(6);
        System.out.println("Artificial Player Created!");
    }

}
