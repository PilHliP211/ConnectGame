package gameElements;

import java.io.PrintStream;
import java.util.LinkedList;

import gameElements.Board.ColumnFullException;
import gameElements.Board.Piece;

public class Game {

	Player player1;
	Player player2;
	LinkedList<Player> playerQueue;
	Board board;
	PrintStream output = null;
	
	public Game(){
		board = new Board();
		player1 = new Player(Piece.BLACK,board);
		player2 = new Player(Piece.RED,board);
		playerQueue = new LinkedList<Player>();
		playerQueue.add(player1);
		playerQueue.add(player2);
		output = new PrintStream(System.out);
	}
	
	public void startGame()
	{
		int playedCol = -1;
		Piece winner;
		while(!board.isFull()){
			Player p = playerQueue.poll();
			p.toggleTurn();
			while(p.isTurn())
			try{
				playedCol = Integer.valueOf(p.nextMove());
				p.play(playedCol);
			} catch (NumberFormatException nfe) {
				output.println("Invalid column. Cannot place piece! Try Again...");
			}
			catch(ColumnFullException cfe){
				output.println("Column is full. Cannot place piece! Try Again...");
			}
			board.showBoard();
			winner = board.didWin();
			if(winner!=Piece.NONE){
				declareWinner(winner);
				return;
			}
			playerQueue.add(p);
				
		}
	}
	
	private void declareWinner(Piece winner)
	{
		System.out.println(winner.prettyName() + " wins!");
	}
	
	public static void main(String[] args){
		new Game().startGame();
	}
}
