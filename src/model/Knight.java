package model;

/**
 * A class representation of the Knight piece
 * 
 * @author jmm754 and yuky
 */

import model.Utils.Color;

public class Knight extends Piece{

	/**
	 * Constructor for the Knight
	 * @param color color to set the Knight to
	 */
	public Knight(Color color) {
		super(color);
	}

	/**
	 * Sets the next possible moves for the Knight
	 * 
	 * There are eight possible legal moves for the Knight unless one of the moves is off the board, collision is ignored
	 */
	@Override
	public void setNextPossibleMoves() {
		resetPossibleMoves();
		char file = this.getCurrentFR().getFile();
		int rank = this.getCurrentFR().getRank();
		//One
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 2) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 2).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 2).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 2));
			board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 2).setThreat(this);
		}
		//Two
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 2) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 2).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 2).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 2));
			board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 2).setThreat(this);
		}
		//Three
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank + 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank + 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank + 1).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank + 1));
			board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank + 1).setThreat(this);
		}		
		//Four
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank + 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank + 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank + 1).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank + 1));
			board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank + 1).setThreat(this);
			
		}		
		//Five
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 2) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 2).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 2).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 2));
			board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 2).setThreat(this);
		}		
		//Six
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 2) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 2).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 2).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 2));
			board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 2).setThreat(this);
		}		
		//Seven
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank - 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank - 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank - 1).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank - 1));
			board.getFileRankAt(Utils.convert(Utils.convert(file) + 2), rank - 1).setThreat(this);
		}		
		//Eight
		if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank - 1) != null
		&& (!board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank - 1).isOccupied()
		||   board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank - 1).getCurrentPiece().getColor() != this.getColor())){
			nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank - 1));
			board.getFileRankAt(Utils.convert(Utils.convert(file) - 2), rank - 1).setThreat(this);
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
	 * Returns a string representation of the Knight
	 */
	@Override
	public String toString() {
		return super.toString() + "N";
	}

}
