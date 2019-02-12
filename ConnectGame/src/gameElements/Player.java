package gameElements;

import gameElements.Board.ColumnFullException;

/**
 * @author Phillip Byram 
 * A Player that can play Connect
 */
public interface Player{
    /**
	 * gets the player's desired next move and returns the success of playing the player's piece.
	 * @param noPrompt true if input should be prompted via output, false for no output indicating needed input
	 * @return the success of the attempted move
	 * @throws NumberFormatException if the input is not a valid integer
	 * @throws ColumnFullException if the index of the column the player is trying to place in is full
	 */
    public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException;
    
    /**
     * @return true if it is this players turn; false otherwise
     */
    public boolean isTurn();

}