package test;

import java.util.Random;

import ai.ArtificialPlayer;
import gameElements.Board;
import gameElements.Game;
import gameElements.Piece;
import gameElements.QuadraticBoard;

/**
 * Main class to test the Artificial Players
 */
public class ArtificialTester {


    public static final int N_MIN = 3;
    public static final int N_MAX = 10;
    public static final int M_MIN = 1;
	/**
	 * Play a game on every possible board with randomly varried 
     * difficulty of ArtificialPlayers playing each other.
     * 
	 * @param args none
	 */
    public static void main(String[] args) 
    {
        Random r = new Random();
        for(int n= N_MIN; n<=N_MAX;n++)
            for(int m=M_MIN;m<=n; m++)
            {
                int xDif = r.nextInt(8)+1;
                int oDif = r.nextInt(8)+1;
                System.out.println("N: "+String.valueOf(n)+"\nM: "+String.valueOf(m));
                System.out.println("X difficulty: "+String.valueOf(xDif)+"\nO difficulty: "+String.valueOf(oDif));
                Board board = new QuadraticBoard(n, m);
                ArtificialPlayer p1 = new ArtificialPlayer(xDif, Piece.X, board);
                ArtificialPlayer p2 = new ArtificialPlayer(oDif, Piece.O, board);
                Game game = new Game(board,p1,p2);
                game.startGame(true);
            }
    }
}