package chess;

/**
 * Class that runs the main game loop for chess
 * 
 * @author jmm754 and yuky
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Board;
import model.FileRank;
import model.King;
import model.Player;
import model.Utils.Color;

public class Chess {
	
	//private static boolean draw = false;

	/**
	 * Initializes players given a color
	 * @param color color to create player
	 * @return returns player that was created
	 */
	public static Player initPlayers(Color color){
		return new Player(color);
	}

	/**
	 * The main game loop
	 */
	public static void startGame(){
		Board board = new Board(8,8);
		Player whitePlayer = new Player(Color.WHITE);
		Player blackPlayer = new Player(Color.BLACK);
		whitePlayer.placePieces(board);
		blackPlayer.placePieces(board);
		System.out.println(board);
		
		while(true){
			nextMove(whitePlayer, blackPlayer, board);
			nextMove(blackPlayer, whitePlayer, board);
		}	
	}
	
	/**
	 * The next move of a player for a given board
	 * This method accepts the players input and parses whether or not the input was valid
	 * @param playerTurn player to move
	 * @param board board that is being played on
	 */
	public static void nextMove(Player playerTurn, Player opposingPlayer, Board board){
		//Gameplay Loop

		//Asks for user input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean invalid = false;
		String[] moves;
		
		if (playerTurn.isInCheck()){
			System.out.println("Check");
		}
		//isCheckmate(playerTurn);
		if (playerTurn.getDraw()){
			playerTurn.setDraw(false);
		}
		
		//Resets the possible threats for the specific player
		board.resetAllThreats(playerTurn);
		board.tickEPCounts();
		
		//Recalculate possible positions after previous player make a move
		if (playerTurn.isInCheck()){
			for(int i = 0; i < playerTurn.getPieces().size(); i++){
				playerTurn.getPieces().get(i).setNextPossibleMovesInCheck(opposingPlayer);
			}
		}else{
			for(int i = 0; i < playerTurn.getPieces().size(); i++){
				playerTurn.getPieces().get(i).setNextPossibleMoves();
			}
		}
		
		// CHECKMATE/STALEMATE CHECK
		// is currently in check
		if (playerTurn.isInCheck()){
			// no more possible moves
			if (!playerTurn.hasNextPossibleMoves()){
				System.out.println("Checkmate");
				winner(opposingPlayer.getColor());
			}
		}else{
			// no more possible moves
			if (!playerTurn.hasNextPossibleMoves()){
				System.out.println("Stalemate");
				winner(opposingPlayer.getColor());
			}
		}
		 
		do{
			invalid = false;
			if(playerTurn.getColor() == Color.WHITE){
				System.out.print("White's Move: ");
			}else{
				System.out.print("Black's Move: ");
			}
			
			String move = null;
			try {
				move = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			moves = move.split(" ");
			
			if(moves.length == 1 && moves[0].equalsIgnoreCase("resign")){
				if(playerTurn.getColor() == Color.WHITE){
					System.out.println("resign");
					winner(Color.BLACK);
					System.exit(0);
				}else{
					System.out.println("resign");
					winner(Color.WHITE);
					System.exit(0);
				}
			}else if(moves.length == 1 && moves[0].equalsIgnoreCase("draw")){
				if(opposingPlayer.getDraw()){
					System.out.println("Draw");
					System.exit(0);
				}else{
					System.out.println("Illegal Move: Try Again");
					invalid = true;
					continue;
				}
			}
			
			if(moves.length != 2 && moves.length != 3){
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
			
			if(moves[0].length() != 2 || moves[1].length() != 2){
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
		
			char firstLet = moves[0].charAt(0);
			int firstNum = Integer.parseInt(moves[0].substring(1));
			char secondLet = moves[1].charAt(0);
			int secondNum = Integer.parseInt(moves[1].substring(1));
			
			//checks if the first input is correct
			if(((firstLet >= 97 && firstLet <= 104) || (firstLet >=  65 && firstLet <= 72)) && (firstNum > 48 || firstNum < 57)){
				invalid = false;
				
			}else{
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
			
			//checks if the second input is correct
			if(((secondLet >= 97 && secondLet <= 104) || (secondLet >=  65 && secondLet <= 72)) && (secondNum > 48 || secondNum < 57)){
				invalid = false;
			}else{
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
			
			//checks if the third input is correct
			if (moves.length == 3){
				char p = moves[2].charAt(0);
				
				if (moves[2].length() != 1){
					if(moves[2].equalsIgnoreCase("draw?")){
						//draw = true;
						playerTurn.setDraw(true);
					
					}else{
						System.out.println("Illegal Move: Try Again");
						invalid = true;
						continue;
					}
				}else if (p != 'R' && p != 'N' && p != 'B' && p != 'Q'){
					System.out.println("Illegal Move: Try Again");
					invalid = true;
					continue;
				}
			}
			
			//Check if the move is legal
			if(board.getFileRankAt(Character.toLowerCase(firstLet), firstNum) == null){
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
			//Check if space contains a piece or if the piece is not the player's piece
			if(!board.getFileRankAt(Character.toLowerCase(firstLet), firstNum).isOccupied() || board.getFileRankAt(Character.toLowerCase(firstLet), firstNum).getCurrentPiece().getColor() != playerTurn.getColor()){
				System.out.println("Illegal Move: Try Again");
				invalid = true;
				continue;
			}
			
			FileRank fr1 = board.getFileRankAt(Character.toLowerCase(firstLet), firstNum);
			FileRank fr2 = board.getFileRankAt(Character.toLowerCase(secondLet), secondNum);
			
			
			if(moves.length == 2){
				//Check if the piece can actually move to that spot
				if(!fr1.getCurrentPiece().promoteMove(fr2, 'Q')){
					invalid = true;
					continue;
				}
			}else{
				if(!fr1.getCurrentPiece().promoteMove(fr2, moves[2].charAt(0))){
					invalid = true;
					continue;
				}
			}
			
			if (playerTurn.isInCheck()){
				playerTurn.setInCheck(false);
			}
			
			board.resetAllThreats(playerTurn);
			
		 	//Set possible moves after player moves a piece
			for(int i = 0; i < playerTurn.getPieces().size(); i++){
				playerTurn.getPieces().get(i).setNextPossibleMoves();
			}
			
			invalid = false;
			if(moves.length == 2){
				System.out.println(moves[0] + " " + moves[1] + "\n");
			}else if(moves.length == 3){
				System.out.println(moves[0] + " " + moves[1] + " " + moves[2] + "\n");
			}
			
			System.out.println(board);
			
		}while(invalid);
			
		
	}
	
	/**
	 * Checks whether or not player's king is in check
	 * @param player player to check
	 */
	public static void isCheck(Player player){
		if(player.getColor() == Color.WHITE){
			if(player.getPieces().get(15).getCurrentFR().getThreat(Color.WHITE) == true){
				System.out.println("Check");
				King king = (King)player.getPieces().get(15);
				king.setInCheck(true);
				
				return;
			}
		}else{
			if(player.getPieces().get(15).getCurrentFR().getThreat(Color.BLACK) == true){
				System.out.println("Check");
				King king = (King)player.getPieces().get(15);
				king.setInCheck(true);
				return;
			}
		}
		
		//King isn't in check
		King king = (King)player.getPieces().get(15);
		king.setInCheck(false);
	}
	
	/**
	 * Ends the game if player gets checkmate
	 * @param player player to check if in checkmate or not
	 */
	public static void isCheckmate(Player player){
		if(player.getPieces().get(15).isInPlay() == false){
			System.out.println("Checkmate");
			if(player.getColor() == Color.WHITE){
				winner(Color.BLACK);
				System.exit(0);
			}else{
				winner(Color.WHITE);
				System.exit(0);
			}
		}
	}

	/**
	 * Prints the winning message for a given color
	 * @param color color of the player that won
	 */
	public static void winner(Color color){
		String winningColor = color == Color.WHITE ? "White" : "Black";
		System.out.println(winningColor + " wins");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startGame();
	}

}
