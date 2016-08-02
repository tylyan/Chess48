package model;

import java.util.ArrayList;

/**
 * A class representation of the King Piece
 * 
 * @author jmm754 and yuky
 */
import model.Utils.Color;

public class King extends Piece{
	
	private boolean inCheck;
	private boolean canCastle;
	
	/**
	 * Constructor for the King
	 * @param color color to set the King to
	 */
	public King(Color color) {
		super(color);
		setInCheck(false);
		setCanCastle(true);
	}

	/**
	 * Moves the King to the specified FileRank
	 * If the distance of the file is greater than 1, this means that the King is castling
	 * If the King is castling, the corresponding Rook is also moved
	 */
	@Override
	public boolean move(FileRank to){
		FileRank from = this.getCurrentFR();
		boolean moveable = super.move(to);
		if (moveable){
			if (Math.abs(Utils.convert(from.getFile()) - Utils.convert(to.getFile())) != 1){
				// the only situation where this is possible is if the king is castling
				// determine which direction king is castling towards, move that rook next to king
				int direction = Utils.convert(from.getFile()) - Utils.convert(to.getFile());
				if (direction > 0){
					// positive - means castling to the left
					board.getFileRankAt('a', this.getCurrentFR().getRank()).getCurrentPiece().move(board.getFileRankAt('d', this.getCurrentFR().getRank()));
				}else{
					// negative - means castling to the right
					board.getFileRankAt('h', this.getCurrentFR().getRank()).getCurrentPiece().move(board.getFileRankAt('f', this.getCurrentFR().getRank()));
				}
			}
			setCanCastle(false);
		}
		return moveable;
	}
	
	/**
	 * Sets the next possible moves for the King
	 * Legal moves contain FileRanks that are adjacent to the King in all direction unless it is occupied by a friendly piece
	 * 
	 * If King has not yet moved, then castling is a possibility, will check either side of the King
	 * If the path is clear, not in threat by the opponent, and the Rook has also not moved, then castling is also legal
	 */
	@Override
	public void setNextPossibleMoves() {
		resetPossibleMoves();
		char file = this.getCurrentFR().getFile();
		int rank = this.getCurrentFR().getRank();
		//One
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).setThreat(this);
			}
		}
		//Two
		if (board.getFileRankAt(file, rank - 1) != null
		&& (!board.getFileRankAt(file, rank - 1).isOccupied()
		||   board.getFileRankAt(file, rank - 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(file, rank - 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(file, rank - 1));
				board.getFileRankAt(file, rank - 1).setThreat(this);
			}
		}
		//Three
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).setThreat(this);
			}
		}		
		//Four
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank).setThreat(this);
			}
		}		
		//Five
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank).setThreat(this);
			}
			
		}		
		//Six
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).setThreat(this);
			}
		}		
		//Seven
		if (board.getFileRankAt(file, rank + 1) != null
		&& (!board.getFileRankAt(file, rank + 1).isOccupied()
		||   board.getFileRankAt(file, rank + 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(file, rank + 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(file, rank + 1));
				board.getFileRankAt(file, rank + 1).setThreat(this);
			}
		}			
		//Eight
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).getCurrentPiece().getColor() != this.getColor())){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).getThreat(this.getColor())){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).setThreat(this);
			}
		}
		
		if (canCastle){
			if (this.inCheck){
				return;
			}
			boolean clear = true;
			int inc = 0;
			// Castleable
			// check if any pieces are blocking path to left rook
			for (inc = 1; inc < 4; inc++){
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank) != null
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).isOccupied()){
					clear = false;
					break;
				}
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).getThreat(this.getColor())){
					clear = false;
					break;
				}
			}
			// path is clear, check if rook is castleable
			if (clear){
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 4), rank) != null
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) - 4), rank).isOccupied()
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) - 4), rank).getCurrentPiece() instanceof Rook){
					Rook rook = (Rook) board.getFileRankAt(Utils.convert(Utils.convert(file) - 4), rank).getCurrentPiece();
					if (!rook.moved()){
						nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank));
						rook.setCanCastle(true);
					}
				}
			}
			// check if any pieces are blocking path to right rook
			clear = true;
			for (inc = 1; inc < 3; inc++){
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank) != null
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank).isOccupied()){
					clear = false;
					break;
				}
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).getThreat(this.getColor())){
					clear = false;
					break;
				}
			}
			// path is clear, check if rook is castleable
			if (clear){
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 3), rank) != null
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) + 3), rank).isOccupied()
				 && board.getFileRankAt(Utils.convert(Utils.convert(file) + 3), rank).getCurrentPiece() instanceof Rook){
					Rook rook = (Rook) board.getFileRankAt(Utils.convert(Utils.convert(file) + 3), rank).getCurrentPiece();
					if (!rook.moved()){
						nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank));
						rook.setCanCastle(true);
					}
				}
			}
		}
		
		for (FileRank fr : nextPossibleMoves){
			if (fr.isOccupied()){
				Piece op = fr.getCurrentPiece();
				if (op instanceof King){
					((King) op).setInCheck(true);
					this.setAttackingKing(true);
				}
			}
		}
	}

	/**
	 * Method that calculates the next possible legal moves while in check for the given piece
	 * 
	 * This method is slightly different for the King because it can only move away to safety, or capture checking piece
	 */
	public void setNextPossibleMovesInCheck(Player player) {
		setNextPossibleMoves();
		ArrayList<FileRank> nextPossibleMovesInCheck = new ArrayList<FileRank>();
		for (Piece piece : player.getPieces()){
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
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
				if (piece instanceof Bishop){
					if (nextPossibleMoves.contains(piece.getCurrentFR())){
						nextPossibleMovesInCheck.add(piece.getCurrentFR());
						piece.getCurrentFR().setThreat(this);
					}
				}
			}
		}
		resetPossibleMoves();
		nextPossibleMoves = nextPossibleMovesInCheck;
		
		for (FileRank fr : nextPossibleMoves){
			if (fr.isOccupied()){
				Piece op = fr.getCurrentPiece();
				if (op instanceof King){
					((King) op).setInCheck(true);
					this.setAttackingKing(true);
				}
			}
		}
	}
	
	/**
	 * Returns whether or not the King is in check
	 * @return true if the King is in check, false otherwise
	 */
	public boolean isInCheck() {
		return inCheck;
	}

	/**
	 * Sets whether the King is in check or not
	 * @param inCheck true if the King is in check, false otherwise
	 */
	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	/**
	 * Returns whether or not the King can castle
	 * @return true if the King can castle, false otherwise
	 */
	public boolean isCanCastle() {
		return canCastle;
	}

	/**
	 * Sets whether or not the King can castle
	 * @return true if the King can castle, false otherwise
	 */
	public void setCanCastle(boolean canCastle) {
		this.canCastle = canCastle;
	}

	/**
	 * Returns a string representation of the King 
	 */
	@Override
	public String toString() {
		return super.toString() + "K";
	}
}
