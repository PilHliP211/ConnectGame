package ai.helper;

import gameElements.Board;
import gameElements.Piece;
import gameElements.QuadraticBoard;

/**
 * @author Phillip Byram 
 * A Node in The Tree Struture
 *
 */
public class BoardHelpers {


    public static Board getSubBoard(Board b, int w, int h) throws DimensionsOOBException
    {
        //b.getSpaces
        //iterate through spaces[w+i][h+i] until i = b.getWinCondition
        //return board

        int dimension = b.getWinCondition();
        // Pass win condition as constructor parameters for height, width, and win condition. 
        QuadraticBoard subBoard = new QuadraticBoard(dimension, dimension);  
       
        if( (w + dimension) > b.getSpaces().length || (h + dimension) > b.getSpaces()[0].length )
        {
            throw new DimensionsOOBException();
        }
        else
        {
            for(int i = w; i < dimension; i++)  
            {                                       
                for(int j = h; j < dimension;j++)
                {
                    subBoard.setPieceAtSpace(b.getSpaces()[i+w][j+h] , i,j);  // = b.getSpaces[i+w][j+h];
                }

            }
        }
        return subBoard;
    }




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
        Board tBoard = generateTestBoard(7, 7, 4);
        tBoard.showBoard();
        getSubBoard(tBoard,3,3).showBoard();


    }

    @SuppressWarnings("serial")
    static class DimensionsOOBException extends Exception
    {
        public DimensionsOOBException(){
			
		}
    }
}