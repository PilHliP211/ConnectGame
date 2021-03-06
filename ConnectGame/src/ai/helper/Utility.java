package ai.helper;

import ai.helper.BoardHelpers.DimensionsOOBException;
import gameElements.Board;
import gameElements.Piece;
import gameElements.Board.ColumnFullException;

/** @author Phillip Byram 
 * Helper class to find utility of a board
 *
 */
public class Utility {

    /**
     * @param b the board to calculate the utility of
     * @param myPiece my piece
     * @param theirPiece the opponent's piece
     * @return the utility of a given board for one piece over another
     */
    public static int calculate(Board b,Piece myPiece, Piece theirPiece){
        
        // get the positive utility
        int wins = 0;
        try{
            boolean won = false;
            for(int w = 0;w<b.getSpaces().length - b.getWinCondition()+1&& !won;w++)
                for(int h = 0;h<b.getSpaces()[0].length - b.getWinCondition()+1&& !won;h++)
                    {
                        int win = checkSubBoard(BoardHelpers.getSubBoard(b, w, h),myPiece);
                        if(checkForInf(win)) 
                        {
                            // this board lets us win, return max utility
                            won = true;
                            return win;
                        }
                        else
                            wins += win;
                    }
        } catch (DimensionsOOBException doobe){
            System.out.println("getSubBoard encountered a bounds error!");
        }
        
        // get the negative utility
        int losses = 0;
        try{
            boolean lost = false;
            for(int w = 0;w<b.getSpaces().length - b.getWinCondition()+1&& !lost;w++)
                for(int h = 0;h<b.getSpaces()[0].length - b.getWinCondition()+1&& !lost;h++)
                {
                    int lose = checkSubBoard(BoardHelpers.getSubBoard(b, w, h),theirPiece);
                    if(checkForInf(lose)) 
                    {
                        // this board makes us lose, return min utility.
                        lost = true;
                        return -lose;
                    }
                    else
                        losses += lose;
                }
        } catch (DimensionsOOBException doobe){
            System.out.println("getSubBoard encountered a bounds error!");
        }

        return wins - losses;
    }

    private static int checkSubBoard(Board b, Piece p){
        int wins = 0;
        // get the utility points for each vertical in the subboard
        for(int w = 0;w<b.getSpaces().length;w++)
        {
            int win = checkArray(getVert(b,w),p);
            if(checkForInf(win)) return win;
            wins+= win;
        }
        // get the utility points for each horizontal in the subboard
        for(int h = 0;h<b.getSpaces().length;h++){
            int win = checkArray(getHoriz(b,h),p);
            if(checkForInf(win)) return win;
            wins+= win;
        }
        // get the utility for the single left diagonal in the subboard
        {
            int win = checkArray(getLeftDiag(b), p);
            if(checkForInf(win)) return win;
            wins+=win;
        }
        // get the utility for the single right diagonal in the subboard
        {
            int win = checkArray(getRightDiag(b), p);
            if(checkForInf(win)) return win;
            wins += win;
        }
        return wins;
    }
    
    private static boolean checkForInf(int win) {
        if(win == Integer.MIN_VALUE || win == Integer.MAX_VALUE) return true;
        return false;
    }

    private static Piece[] getVert(Board b, int w) {
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
    private static int checkArray(Piece[] a, Piece p){
        int val = 0;
        
        // reward number of mine in an array
        for(int i = 0;i<a.length;i++)
            if(a[i]==p)val++;
            else if(a[i]!= Piece.NONE)val--;

        // sections with no enemy pieces are exponentially more valuable
        boolean bad = false;
        int tempVal = 0;
        for(int i = 0;i<a.length && !bad;i++)   
            if(a[i]!=p && a[i]!=Piece.NONE) bad = true;
            else if (a[i]==Piece.NONE)tempVal++;
            else tempVal+=i+1;
        if(!bad)
            val = tempVal;

        // check if this array has only our pieces and report a win
        boolean winner = true;
        for(int i = 0;i<a.length;i++){
            if(a[i]!=p)winner= false;
        }
        if(winner) 
            return Integer.MAX_VALUE;

        // check if this array has only thier piece and report a loss
        boolean loser = true;
        for(int i = 0;i<a.length;i++){
            if(a[i]==p || a[i]==Piece.NONE)
            loser= false;
        }
        if(loser) 
            return Integer.MIN_VALUE;

        return val;
    }

    public static void main(String[] args) throws ColumnFullException{
        Board b = new gameElements.QuadraticBoard(4,4);
        b.placePiece(Piece.O, 2);
        b.placePiece(Piece.X, 2);
        System.out.println(Utility.calculate(b, Piece.O, Piece.X));
    }
}