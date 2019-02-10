package test;

import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import gameElements.Board;
import gameElements.Game;
import gameElements.Piece;
import gameElements.Player;
import gameElements.QuadraticBoard;

public class RandomTester {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		final int BOARD_MAX = 6;
		final int DIMENSIONS_DIFF = 3;
		Random r = new Random();
		int dimension = 4+r.nextInt(BOARD_MAX);//4-10
		//int height = width-(r.nextInt(DIMENSIONS_DIFF)*((r.nextBoolean())?-1:1));//width +/- 3
		int winCondition = 3+(r.nextInt(DIMENSIONS_DIFF));//3-6
		int iterations = 10;
		
		int i = 0;
		while(i<iterations)
		{
			Board b = new QuadraticBoard(dimension,winCondition);
			ByteArrayInputStream in = new ByteArrayInputStream(convertIntsToInputs(r.ints(1000,0,dimension-1)).getBytes());
			System.setIn(in);
			Scanner sIn = new Scanner(System.in);
			Player p1 = new Player(Piece.BLACK, b, sIn);
			Player p2 = new Player(Piece.RED, b, sIn);
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
