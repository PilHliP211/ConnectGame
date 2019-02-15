package ai.helper;

import gameElements.Board;
import gameElements.Piece;
import gameElements.QuadraticBoard;

/**
 * @author Russell Plenkers
 * A Node in The Tree Struture
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
     * @return sub board object of type Board
     * @throws DimensionsOOBException
     */
    public static Board getSubBoard(Board b, int x, int y) throws DimensionsOOBException
    {
        //iterate through spaces[w+i][h+i] until i = b.getWinCondition
        int dimension = b.getWinCondition();
        // Pass win condition as constructor parameters for height, width, and win condition. 
        QuadraticBoard subBoard = new QuadraticBoard(dimension, dimension);  
        // Throw error if start position + dimension exceeds
        if( b.getSpaces().length < (x + dimension)  || b.getSpaces()[0].length > (y + dimension) )
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
     * Create a test board used to test the getSubBoard method.
     * 
     * @param w - width of new board
     * @param h - height of new board
     * @param winCond - win condition
     * @return  Board 
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
                    piece = Piece.RED;
                }
                if((i*j)%3 == 2)
                {
                    piece = Piece.BLACK;
                }
                testBoard.setPieceAtSpace(piece, i, j);
            }
        }
        return testBoard;
    }


    public static void main(String[] args) throws DimensionsOOBException{
        Board tBoard = generateTestBoard(10, 10, 4);
        tBoard.showBoard();
        getSubBoard(tBoard,5,6).showBoard();
    }

    @SuppressWarnings("serial")
    static class DimensionsOOBException extends Exception
    {
        public DimensionsOOBException(){
			
		}
    }
}