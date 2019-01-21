package gameElements;

import java.util.PriorityQueue;
import java.util.Scanner;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import gameElements.Board.Piece;

public class Game {

	Player player1;
	Player player2;
	PriorityQueue<Player> playerQueue;
	Board board;
	
	public Game(){
		board = new Board();
		player1 = new Player(Piece.BLACK,board);
		player2 = new Player(Piece.RED,board);
		playerQueue = new PriorityQueue<Player>();
		playerQueue.add(player1);
		playerQueue.add(player2);
	}
	
	public void startGame()
	{
		int playedCol = -1;
		Piece winner;
		while(!board.isFull()){
			Player p = playerQueue.poll();
			
			playedCol = Integer.valueOf(p.nextMove());
			
			p.play(playedCol);
			
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
