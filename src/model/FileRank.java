package model;

import model.Utils.Color;

/**
 * 
 *  This class is a representation of one FileRank on a chess board.
 *  File is represented by a char ranging from 'a' - 'h'
 *  Rank is represented by an int ranging from 1 - 8
 *  
 * @author jmm754 and yuky
 * 
 */

public class FileRank {
	
	private Color color;
	private Piece currentPiece;
	private char file;
	private int rank;
	private boolean inThreatByBlack;
	private boolean inThreatByWhite;
	private boolean canEnPassant;
	private int enPassantCount;
	
	/**
	 * Constructor for a FileRank
	 * @param file value of the file
	 * @param rank value of the rank
	 */
	public FileRank(int file, int rank){
		this.file = Utils.convert(file);
		this.rank = rank;
		enPassantCount = 0;
		//odd rows
		if (this.rank%2 != 0){
			if (this.file%2 != 0){
				this.color = Color.BLACK;
			}else{
				this.color = Color.WHITE;
			}
		//even rows
		}else{
			if (this.file%2 != 0){
				this.color = Color.WHITE;
			}else{
				this.color = Color.BLACK;
			}
		}
	}
	
	/**
	 * Returns the value of the file
	 * @return the value of the file
	 */
	public char getFile(){
		return file;
	}
	
	/**
	 * Returns the value of the rank
	 * @return the value of the rank
	 */
	public int getRank(){
		return rank;
	}
	
	/**
	 * Returns whether or not the FileRank is occupied by a piece or not
	 * @return true if FileRank is occupied, false otherwise
	 */
	public boolean isOccupied(){
		return !(currentPiece == null);
	}
	
	/**
	 * Returns the color of the FileRank, used primarily for printing FileRank to screen
	 * @return the color of the FileRank
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the FileRank
	 * @param color color to set FileRank to
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Removes the piece currently occupying the FileRank
	 */
	public void removeCurrentPiece(){
		if(getCurrentPiece() != null){
			getCurrentPiece().setCurrentFR(null);
		}
		setCurrentPiece(null);
	}
	
	/**
	 * Returns the piece that is occupying the FileRank
	 * @return the piece that is occupying the FileRank
	 */
	public Piece getCurrentPiece() {
		return currentPiece;
	}

	/**
	 * Sets the piece that is currently occupying the FileRank
	 * @param currentPiece piece to occupy FileRank with
	 */
	public void setCurrentPiece(Piece currentPiece) {
		this.currentPiece = currentPiece;
		if (currentPiece != null){
			currentPiece.setCurrentFR(this);
		}
	}
	
	/**
	 * Sets whether or not if EnPassant is legal in this FileRank
	 * If EnPassant is legal, then a timer of the EnPassant move is also set
	 * @param canEnPassant boolean value to set whether or not enPassant is legal
	 */
	public void setCanEnPassant(boolean canEnPassant){
		this.canEnPassant = canEnPassant;
		if (getCanEnPassant()){
			enPassantCount = 2;
		}
	}
	
	/**
	 * Decreases the EnPassant timer if not already 0
	 * If the timer hits 0, then EnPassant is no longer legal
	 */
	public void tickEPCount(){
		if (enPassantCount == 0){
			setCanEnPassant(false);
			return;
		}
		enPassantCount--;
	}
	
	/**
	 * Returns whether or not EnPassant is legal or not for the FileRank
	 * @return whether or not EnPassant is legal or not for the FileRank
	 */
	public boolean getCanEnPassant(){
		return canEnPassant;
	}
	
	/**
	 * Given a piece, sets which color the FileRank is in threat by
	 * @param piece piece that is threatening the FileRank
	 */
	public void setThreat(Piece piece){
		if(piece.getColor() == Color.WHITE){
			this.inThreatByWhite = true;
		}else{
			this.inThreatByBlack = true;
		}
	
	}
	
	/**
	 * Given a color, returns if the FileRank is threatened by an opposing color
	 * @param color color to check if the FileRank is threatening it or not
	 * @return true if given color is threatened, false otherwise
	 */
	public boolean getThreat(Color color){
		if(color == Color.WHITE){
			return this.inThreatByBlack;
		}else{
			return this.inThreatByWhite;
		}
	}
	
	/**
	 * Resets the threat for a given color for the FileRank
	 * @param color which color to reset the threat for
	 */
	 public void resetThreat(Color color){
		if(color == Color.WHITE){
			this.inThreatByBlack = false;
		}else{
			this.inThreatByWhite = false;
		}
	}  
	
}
