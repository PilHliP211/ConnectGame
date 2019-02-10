package gameElements;

import java.io.PrintStream;
import java.util.LinkedList;

import gameElements.Board.ColumnFullException;

public class Game {

	Player player1;
	Player player2;
	LinkedList<Player> playerQueue;
	Board board;
	PrintStream output = null;
	
	public Game(){
		board = new QuadraticBoard();
		player1 = new Player(Piece.BLACK,board);
		player2 = new Player(Piece.RED,board);
		playerQueue = new LinkedList<Player>();
		playerQueue.add(player1);
		playerQueue.add(player2);
		output = new PrintStream(System.out);
	}
	
	public Game(Board b, Player p1, Player p2){
		board = b;
		player1 = p1;
		player2 = p2;
		playerQueue = new LinkedList<Player>();
		playerQueue.add(player1);
		playerQueue.add(player2);
		output = new PrintStream(System.out);
	}

	public Game(Board b){
		board = b;
		player1 = new Player(Piece.BLACK,board);
		player2 = new Player(Piece.RED,board);
		playerQueue = new LinkedList<Player>();
		playerQueue.add(player1);
		playerQueue.add(player2);
		output = new PrintStream(System.out);
	}

	/**
	 * Starts a Game showing the board each turn
	 */
	public void startGame(){
		startGame(false);
	}

	/**
	 * Starts a game using an option to show the board every turn or just at the end of the game.
	 * @param minimalOutput true if the board should only be shown at the end of the game; false if the board should be showed with every played piece
	 */
	public void startGame(boolean minimalOutput)
	{
		int playedCol = -1;
		Piece winner;
		if(!minimalOutput)board.showBoard();
		while(!board.isFull()){
			Player p = playerQueue.poll();
			p.toggleTurn();
			while(p.isTurn())
			try{
				playedCol = Integer.valueOf(p.nextMove(minimalOutput));
				p.play(playedCol);
			} catch (NumberFormatException nfe) {
				if(!minimalOutput)output.println("Invalid column. Cannot place piece! Try Again...");
			}
			catch(ColumnFullException cfe){
				if(!minimalOutput)output.println("Column is full. Cannot place piece! Try Again...");
			}
			if(!minimalOutput)board.showBoard();
			winner = board.didWin();
			if(winner!=Piece.NONE){
				declareWinner(winner);
				if(minimalOutput)board.showBoard();//minimalOutput? show final state only, otherwise don't since we just showed it.
				return;
			}
			playerQueue.add(p);
				
		}
		System.out.println("No winner!");
		board.showBoard();
	}
	
	
	private void declareWinner(Piece winner)
	{
		System.out.println(winner.prettyName() + " wins!");
	}
	
	public static void main(String[] args){
		if(args.length < 2)
			new Game().startGame();
		else
			startGameFromArgs(args);
	}

	private static void startGameFromArgs(String[] args){
		Board b = new QuadraticBoard(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		new Game(b).startGame();
	}
}
