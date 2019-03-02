package test;

import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import gameElements.Board;
import gameElements.Game;
import gameElements.InputPlayer;
import gameElements.Piece;
import gameElements.Player;
import gameElements.QuadraticBoard;

/**
 * @author Phillip Byram
 * A Tester for Connect that randomly places pieces between two players and reports a winner, if any
 * Used for checking board win detection, etc.
 *
 */
public class RandomTester {

	/**
	 * Make 10 boards in their final state
	 * @param args none
	 */
	public static void main(String[] args) {
		
		final int BOARD_MAX = 6;
		final int DIMENSIONS_DIFF = 3;
		Random r = new Random();
		int dimension = 4+r.nextInt(BOARD_MAX);//4-10
		//int height = width-(r.nextInt(DIMENSIONS_DIFF)*((r.nextBoolean())?-1:1));//width +/- 3
		int winCondition = 3+(r.nextInt(DIMENSIONS_DIFF));//3-6
		int iterations = 10;
		System.out.println("Winner needs to connect "+winCondition);
		
		int i = 0;
		while(i<iterations)
		{
			Board b = new QuadraticBoard(dimension,winCondition);
			ByteArrayInputStream in = new ByteArrayInputStream(convertIntsToInputs(r.ints(400,0,dimension)).getBytes());
			System.setIn(in);
			Scanner sIn = new Scanner(System.in);
			Player p1 = new InputPlayer(Piece.O, b, sIn);
			Player p2 = new InputPlayer(Piece.X, b, sIn);
			Game g = new Game(b,p1,p2);
			g.startGame(true);
			i++;
		}

	}

	private static String convertIntsToInputs(IntStream ints){
		String r = "";
		OfInt  it = ints.iterator();
		while(it.hasNext()){
			r += String.valueOf(it.nextInt())+'\n';
		}
		return r;
	}
}
