package gameElements;

/**
 * @author Phillip Byram
 * The board Connect is played on
 *
 */
public interface Board{

    /**
	 * Checks if a Player has won the game. 
	 * @return The piece that won the game or the empty piece if there are no winners.
	 */
    public Piece didWin();
    
    /**
	 * Checks if the board has any available spaces left
	 * @return true if the board is full; false otherwise
	 */
    public boolean isFull();
    
    /**
	 * Initializes or empties the Board.
	 */
    public void initBoard();
    
    /**
	 * Displays the graphical representation of the board
	 */
	public void showBoard();
	
	/**
	 * Gets the spaces
	 * @return the spaces
	 */
	public Piece[][] getSpaces();

	/**
	 * Gets the winCondition
	 * @return the winCondition
	 */
	public int getWinCondition();

    
    /**
	 * Drops a piece down a column of the Connect board.
	 * @param p the type of Piece to place (cannot be Piece.NONE)
	 * @param column the 0 based index of the column to drop the piece (cannot be greter than or equal to board width)
	 * @return true if the piece was placed on the board, false otherwise
	 * @throws ColumnFullException if the index of the piece to place is a full column
	 */
	public boolean placePiece(Piece p, int column) throws ColumnFullException;
	
	/**
	 * Creates an entirely new board with the same dimensions, wincondition, and spaces of this board
	 * @return the newly copied board
	 */
	public Board copy();
    
	@SuppressWarnings("serial")
	public class ColumnFullException extends Exception {

		public ColumnFullException(){
			
		}
	}
}