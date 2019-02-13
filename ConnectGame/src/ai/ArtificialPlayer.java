package ai;

import gameElements.Board.ColumnFullException;
import gameElements.Player;

/**
 * @author Phillip Byram 
 * An AI Player playing Connect
 *
 */
public class ArtificialPlayer implements Player {

    @Override
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException {
        return false;
    }

    @Override
    public boolean isTurn() {
        return false;
    }

}
