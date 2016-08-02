package model;

/**
 * A class that represents a player participating in a chess game.
 * 
 * @author jmm754 and yuky
 */

import java.util.ArrayList;

import model.Utils.Color;

public class Player {
	
	private ArrayList<Piece> pieces;
	private Color color;
	private boolean draw;
	
	/**
	 * Constructor for a player
	 * @param color which color player will be
	 */
	public Player(Color color){
		setColor(color);
		setDraw(false);
		pieces = new ArrayList<Piece>();
		
		//Create Pawns
		for (int i = 0; i < 8; i++){
			pieces.add(new Pawn(color));
		}
		//Create Rooks
		pieces.add(new Rook(color));
		pieces.add(new Rook(color));
		
		//Create Knights
		pieces.add(new Knight(color));
		pieces.add(new Knight(color));
		
		//Create Bishops
		pieces.add(new Bishop(color));
		pieces.add(new Bishop(color));
		
		//Create Queen
		pieces.add(new Queen(color));
		
		//Create King
		pieces.add(new King(color));
		setInCheck(false);
	}

	/**
	 * Places all of the pieces on the board, pieces will be placed in different locations
	 * according to the player's color
	 * @param board board to set the piece on
	 */
	public void placePieces(Board board){
		int i = 0;
		if (this.getColor() == Color.WHITE){
			//Place pawns
			for (i = 0; i < 8; i++){
				board.getFileRankAt(Utils.convert(i + 1), 2).setCurrentPiece(pieces.get(i));
			}
			//Place rooks
			board.getFileRankAt('a', 1).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('h', 1).setCurrentPiece(pieces.get(i++));
			//Place knights	
			board.getFileRankAt('b', 1).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('g', 1).setCurrentPiece(pieces.get(i++));
			//Place bishops
			board.getFileRankAt('c', 1).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('f', 1).setCurrentPiece(pieces.get(i++));
			//Place queen
			board.getFileRankAt('d', 1).setCurrentPiece(pieces.get(i++));
			//Place king
			board.getFileRankAt('e', 1).setCurrentPiece(pieces.get(i++));
		}else{
			//Place pawns
			for (i = 0; i < 8; i++){
				board.getFileRankAt(Utils.convert(i + 1), 7).setCurrentPiece(pieces.get(i));
			}
			//Place rooks
			board.getFileRankAt('a', 8).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('h', 8).setCurrentPiece(pieces.get(i++));
			//Place knights
			board.getFileRankAt('b', 8).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('g', 8).setCurrentPiece(pieces.get(i++));
			//Place bishops
			board.getFileRankAt('c', 8).setCurrentPiece(pieces.get(i++));
			board.getFileRankAt('f', 8).setCurrentPiece(pieces.get(i++));
			//Place queen
			board.getFileRankAt('d', 8).setCurrentPiece(pieces.get(i++));
			//Place king
			board.getFileRankAt('e', 8).setCurrentPiece(pieces.get(i++));
		}
		for(i = 0; i < pieces.size(); i++){
			pieces.get(i).setBoard(board);
			pieces.get(i).setNextPossibleMoves();
		}
	}
	
	/**
	 * Returns the list of pieces that belong to the player
	 * @return an ArrayList of type Piece
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * Returns the color of the player
	 * @return the color of the player
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the player
	 * @param color color to set the player to
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns whether or not the player is in check
	 * @return true if the player is in check, false otherwise
	 */
	public boolean isInCheck() {
		King king = (King) pieces.get(15);
		return king.isInCheck();
	}
	
	/**
	 * Returns whether or not there are any moves left for the player
	 * @return true if has next possible moves, false otherwise
	 */
	public boolean hasNextPossibleMoves(){
		for (Piece piece : pieces){
			// still have some moves left
			if (!piece.getNextPossibleMoves().isEmpty()) 
				return true;
		}
		return false;
	}

	/**
	 * Returns if this player requested draw or not
	 * @return if this player requested draw or not
	 */
	public boolean getDraw(){
		return draw;
	}
	
	/**
	 * Sets if this player requested draw or not
	 * @param draw true if the player requested draw, false otherwise
	 */
	public void setDraw(boolean draw){
		this.draw = draw;
	}
	
	/**
	 * Sets whether the player is in check or not
	 * @param inCheck boolean value to set if the player is in check or not
	 */
	public void setInCheck(boolean inCheck) {
		King king = (King) pieces.get(15);
		king.setInCheck(inCheck);
	}
	
}
