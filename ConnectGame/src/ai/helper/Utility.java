package ai.helper;

import ai.helper.BoardHelpers.DimensionsOOBException;
import gameElements.Board;
import gameElements.Piece;
import gameElements.Board.ColumnFullException;

/** @author Phillip Byram 
 * A Node in The Tree Struture
 *
 */
public class Utility {


    public static int calculate(Board b,Piece p){
        int wins = 0;
        try{
        for(int w = 0;w<b.getSpaces().length - b.getWinCondition()+1;w++)
            for(int h = 0;h<b.getSpaces()[0].length - b.getWinCondition()+1;h++)
                wins += checkSubBoard(BoardHelpers.getSubBoard(b, w, h),p);
        return wins;
        } catch (DimensionsOOBException doobe){
            System.out.println("getSubBoard encountered a bounds error!");
        }
        return 0;
    }

    private static int checkSubBoard(Board b, Piece p){
        int wins = 0;
        for(int w = 0;w<b.getSpaces().length;w++)
            if(checkArray(getVert(b,w),p))
                wins++;
        for(int h = 0;h<b.getSpaces().length;h++)
            if(checkArray(getHoriz(b,h),p))
                wins++;
        if(checkArray(getLeftDiag(b), p))
            wins++;
        if(checkArray(getRightDiag(b), p))
            wins++;
        return wins;
    }
    private static Piece[] getVert(Board b, int w){
        return b.getSpaces()[w];
    }
    private static Piece[] getHoriz(Board b, int h){
        Piece[] horiz = new Piece[b.getSpaces()[0].length];
        for(int i = 0; i < b.getSpaces()[0].length; i++) horiz[i] = b.getSpaces()[i][h];
        return horiz;
    }
    private static Piece[] getLeftDiag(Board b){
        Piece[] leftDiag = new Piece[b.getSpaces().length];
        for(int i = 0; i< b.getSpaces().length; i++) leftDiag[i] = b.getSpaces()[i][b.getSpaces().length-1 - i];
        return leftDiag;
    }
    private static Piece[] getRightDiag(Board b){
        Piece[] rightDiag = new Piece[b.getSpaces().length];
        for(int i = 0; i< b.getSpaces().length; i++) rightDiag[i] = b.getSpaces()[b.getSpaces().length-1 - i][i];
        return rightDiag;
    }
    private static boolean checkArray(Piece[] a, Piece p){
        for(int i = 0;i<a.length;i++)
            if((a[i]!=p && a[i]!= Piece.NONE))return false;
        return true;
    }

    public static void main(String[] args) throws ColumnFullException{
        Board b = new gameElements.QuadraticBoard(4,4);
        b.placePiece(Piece.BLACK, 2);
        b.placePiece(Piece.RED, 2);
        System.out.println(Utility.calculate(b, Piece.BLACK));
    }
}