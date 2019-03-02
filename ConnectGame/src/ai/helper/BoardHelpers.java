package ai.helper;

import gameElements.Board;
import gameElements.Piece;
import gameElements.QuadraticBoard;

/**
 * @author Russell Plenkers
 * Class for some useful Board methods
 *
 */
public class BoardHelpers {

    /**
     *  Produce a smaller Board, of size winCondition square, from the input Board b.  
     *  Start position for the new board is given with the input values x (horizontal position)
     *  and y (vertical position).
     * 
     * @param b game board used to generate sub board.
     * @param x horizontal position of game board to start copying into sub board
     * @param y vertical position of game board to start copying into sub board
     * @return sub board object of type QuadraticBoard
     * @throws DimensionsOOBException if the x,y coordinate would create a subboard that falls out of bounds of the board
     */
    public static QuadraticBoard getSubBoard(Board b, int x, int y) throws DimensionsOOBException
    {
        //iterate through spaces[w+i][h+i] until i = b.getWinCondition
        int dimension = b.getWinCondition();
        // Pass win condition as constructor parameters for height, width, and win condition. 
        QuadraticBoard subBoard = new QuadraticBoard(dimension, dimension);  
        // Throw error if start position + dimension exceeds size of board.
        if( b.getSpaces().length < (x + dimension)  || b.getSpaces()[0].length < (y + dimension) )
        {
            throw new DimensionsOOBException();
        }
        else
        {
            for(int i = 0; i < dimension; i++)  
            {                                       
                for(int j = 0; j < dimension; j++)
                {
                    subBoard.setPieceAtSpace(b.getSpaces()[i+x][j+y] , i, j);  
                }
            }
        }
        return subBoard;
    }


    /**
     * Checks if a given board is empty
     * @param b the board to check the emptiness of
     * @return true if b is empty, false otherwise.
     */    
    public static boolean isBoardEmpty(Board b)
    {
        int dimension = b.getSpaces().length - 1;
        for(int i = 0; i < dimension; i++)
        {
            if(b.getSpaces()[i][dimension] != Piece.NONE)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Create a test board used to test the getSubBoard method.
     * 
     * @param w - width of new board
     * @param h - height of new board
     * @param winCond - win condition
     * @return a test Board 
     */
    public static Board generateTestBoard(int w, int h, int winCond)
    {

        QuadraticBoard testBoard = new QuadraticBoard(w, winCond);
        Piece piece = null;
        testBoard.setSpaces(testBoard.getSpaces());
        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                if((i*j)%3 == 0)
                {
                    piece = Piece.NONE;
                }
                if((i*j)%3 == 1)
                {
                    piece = Piece.X;
                }
                if((i*j)%3 == 2)
                {
                    piece = Piece.O;
                }
                testBoard.setPieceAtSpace(piece, i, j);
            }
        }
        return testBoard;
    }



    public static void main(String[] args) throws DimensionsOOBException{
        Board tBoard = generateTestBoard(10, 10, 4);
        tBoard.showBoard();
        Board subBoard = getSubBoard(tBoard,0,0);
        subBoard.showBoard();
        System.out.println("\nIs Board Empty:  " + isBoardEmpty(subBoard) + "\n");
    }

    @SuppressWarnings("serial")
    public static class DimensionsOOBException extends Exception
    {
        public DimensionsOOBException(){
			
		}
    }


}