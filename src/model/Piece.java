package model;

import java.util.ArrayList;

import model.Utils.*;
/**
 * 
 * This abstract class is a representation of a general chess piece.
 * 
 * @author jmm754 and yuky
 *
 */

public abstract class Piece {
	
	protected Board board;
	private Color color;
	private FileRank currentFR;
	private boolean inPlay;
	private boolean attackingKing;
	protected ArrayList<FileRank> nextPossibleMoves;
	
	/**
	 * Constructor for this abstract class
	 * @param color color of the piece
	 */
	public Piece(Color color){
		setColor(color);
		setCurrentFR(null);
		setInPlay(true);
		nextPossibleMoves = new ArrayList<FileRank>();
	}

	/**
	 * Returns the color of the piece
	 * @return the color of the piece
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the piece
	 * @param color color to set the piece to
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the FileRank the piece is currently on
	 * @return FileRank that piece is currently on
	 */
	public FileRank getCurrentFR() {
		return currentFR;
	}

	/**
	 * Sets the FileRank that the piece is currently on
	 * @param currentFR FileRank to set the piece to
	 */
	public void setCurrentFR(FileRank currentFR) {
		this.currentFR = currentFR;
	}

	/**
	 * Returns whether or not the piece is in play
	 * @return true if piece is in play, false otherwise
	 */
	public boolean isInPlay() {
		return inPlay;
	}

	/**
	 * Sets whether or not the piece is in play
	 * @param inPlay boolean value to set the value of inPlay to
	 */
	public void setInPlay(boolean inPlay) {
		this.inPlay = inPlay;
	}

	/**
	 * Returns an ArrayList containing the FileRanks that are legal moves for this piece
	 * @return an ArrayList of type FileRank
	 */
	public ArrayList<FileRank> getNextPossibleMoves() {
		return nextPossibleMoves;
	}

	/**
	 * Sets the board that the piece is playing on
	 * @param board board taht the piece is playing on
	 */
	public void setBoard(Board board){
		this.board = board;
	}
	
	/**
	 * Moves the current piece to a specified FileRank
	 * If the specified location is occupied, then the occupying piece is removed from play
	 * @param to the FileRank to move the Piece to
	 * @return true if the move is completed, false otherwise
	 */
	public boolean move(FileRank to){
		if (!nextPossibleMoves.contains(to)){
			System.out.println("Illegal move, try again.");
			return false;
		}else{
			if (to.isOccupied()){
				to.getCurrentPiece().setInPlay(false);
			}
			this.getCurrentFR().removeCurrentPiece();
			to.setCurrentPiece(this);
			
			resetPossibleMoves();
			setNextPossibleMoves();
			
			return true;
		}
	}
	
	/**
	 * Moves a piece to a specified FileRank in addition to promoting the piece
	 * @param to FileRank to move piece to
	 * @param p char representation of desired piece to promote to
	 * @return true if move was successful only (failure in promotion will still return true), false otherwise
	 */
	public boolean promoteMove(FileRank to, char p){
		boolean move = move(to);
		if (move){
			board.promote(to, p);
		}
		return move;
	}
	
	/**
	 * Resets the currently list of possible moves by creating a new ArrayList
	 */
	protected void resetPossibleMoves(){
		setAttackingKing(false);
		nextPossibleMoves = new ArrayList<FileRank>();
	}
	
	/**
	 * Abstract method that calculates the next possible legal moves for the given piece
	 */
	public abstract void setNextPossibleMoves();
	
	/**
	 * Method that calculates the next possible legal moves while in check for the given piece
	 */
	public void setNextPossibleMovesInCheck(Player player) {
		setNextPossibleMoves();
		ArrayList<FileRank> nextPossibleMovesInCheck = new ArrayList<FileRank>();
		King king = (King) player.getPieces().get(15);
		FileRank fr = king.getCurrentFR();
		int inc = 1;
		for (Piece piece : player.getPieces()){
			int fileDistance = Utils.convert(king.getCurrentFR().getFile()) - Utils.convert(piece.getCurrentFR().getFile());
			int rankDistance = king.getCurrentFR().getRank() - piece.getCurrentFR().getRank();
			
			if (piece.isAttackingKing()){
				// figure out what kind of piece is attacking the king
				// depending on which piece, the move to block the piece differs
				// for example, the only way to 'block' a pawn, is the capture it
				if (piece instanceof Pawn){
				// only way to 'block' pawn is the capture it
				// if nextPossibleMoves contain a FileRank that is equal to where the pawn is currently located, this is a nextPossibleMoveInCheck
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
				if (piece instanceof Rook){
				// moving in any square in the rook's path blocks this
				// so if nextPossibleMoves contain a FileRank that is also in Rook's nextPossibleMoves, then this is a nextPossibleMoveInCheck
					//moving vertically
					if (fileDistance == 0){
						// King is above rook
						if (rankDistance > 0){
							while (board.getFileRankAt(fr.getFile(), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(fr.getFile(), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(fr.getFile(), fr.getRank() - inc));
									board.getFileRankAt(fr.getFile(), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						}else{
							while (board.getFileRankAt(fr.getFile(), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(fr.getFile(), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(fr.getFile(), fr.getRank() + inc));
									board.getFileRankAt(fr.getFile(), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						}
					}else{
						// King is to the right of rook
						if (fileDistance > 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()).setThreat(this);
								}
								inc++;
							}
						}else{
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()).setThreat(this);
								}
								inc++;
							}
						}
					}
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
				if (piece instanceof Knight){
					// Can only capture
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
				if (piece instanceof Queen){
					if (fileDistance == 0 && rankDistance != 0){
						// King is above queen
						if (rankDistance > 0){
							while (board.getFileRankAt(fr.getFile(), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(fr.getFile(), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(fr.getFile(), fr.getRank() - inc));
									board.getFileRankAt(fr.getFile(), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						// King is below the queen
						}else{
							while (board.getFileRankAt(fr.getFile(), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(fr.getFile(), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(fr.getFile(), fr.getRank() + inc));
									board.getFileRankAt(fr.getFile(), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						}
					}else if (rankDistance == 0 && fileDistance != 0){
						// King is to the right of queen
						if (fileDistance > 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank()).setThreat(this);
								}
								inc++;
							}
						// King is to the left of queen
						}else{
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank()).setThreat(this);
								}
								inc++;
							}
						}
					}else if (fileDistance > 0 && rankDistance > 0){
						// Queen is in first quadrant
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						// Queen is in fourth quadrant
						}else if (fileDistance > 0 && rankDistance < 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						}
					}else if (fileDistance < 0 && rankDistance > 0){
						// Queen is in the second quadrant
						
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						// Queen is in the third quadrant
						}else if (fileDistance < 0 && rankDistance < 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						}
					
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
				if (piece instanceof Bishop){
					if (fileDistance > 0){
						// Bishop is in first quadrant
						if (rankDistance > 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						// Bishop is in fourth quadrant
						}else{
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) - inc), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						}
					}else{
						// Bishop is in the second quadrant
						if (rankDistance > 0){
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() + inc).setThreat(this);
								}
								inc++;
							}
						// Bishop is in the third quadrant
						}else{
							while (board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc) != null){
								if (nextPossibleMoves.contains(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc))){
									nextPossibleMovesInCheck.add(board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc));
									board.getFileRankAt(Utils.convert(Utils.convert(fr.getFile()) + inc), fr.getRank() - inc).setThreat(this);
								}
								inc++;
							}
						}
					}
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
			}
	
		resetPossibleMoves();
		nextPossibleMoves = nextPossibleMovesInCheck;
		
		for (FileRank filerank : nextPossibleMoves){
			if (filerank.isOccupied()){
				Piece op = filerank.getCurrentPiece();
				if (op instanceof King){
					((King) op).setInCheck(true);
					this.setAttackingKing(true);
				}
			}
		}
	}
	
	/**
	 * Returns whether or not this piece is attacking the king
	 * @return whether or not this piece is attacking the king
	 */
	public boolean isAttackingKing(){
		return attackingKing;
	}
	
	/**
	 * Sets whether or not this piece is attacking the king
	 * @param attackingKing true if attacking king, false otherwise
	 */
	public void setAttackingKing(boolean attackingKing){
		this.attackingKing = attackingKing;
	}
	
	/**
	 * Prints the piece based on its color
	 */
	@Override
	public String toString(){
		return this.getColor() == Color.WHITE ? "w" : "b";
	}

}
