package model;

/**
 * A class representation of the Rook piece
 * 
 * @author jmm754 and yuky
 */

import model.Utils.Color;

public class Rook extends Piece{
	
	private boolean canCastle;
	private boolean firstMove;

	/**
	 * Constructor for the Rook
	 * @param color color to set the Rook to
	 */
	public Rook(Color color) {
		super(color);
		setCanCastle(false);
		setFirstMove(true);
	}

	/**
	 * Moves the rook to the specified FileRank
	 * 
	 * Once moved, the Rook is no longer eligible to castle
	 * 
	 * @param to FileRank to move the rook to
	 */
	@Override
	public boolean move(FileRank to){
		boolean moveable = super.move(to);
		if (moveable){
			setFirstMove(false);
			setCanCastle(false);
		}
		return moveable;
	}
	
	/**
	 * Sets the next possible moves for the Rook
	 * 
	 * Legal moves contain FileRanks that are in both vertical or horizontal directions
	 * until a friendly piece is blocking further movement or opposing piece that may be captured
	 */
	@Override
	public void setNextPossibleMoves() {
		resetPossibleMoves();
		char file = this.getCurrentFR().getFile();
		int rank = this.getCurrentFR().getRank();
		
		//North
		int inc = 1;
		while (board.getFileRankAt(file, rank + inc) != null){
			if (!board.getFileRankAt(file, rank + inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(file, rank + inc));
				board.getFileRankAt(file, rank + inc).setThreat(this);
			}else{
				if (board.getFileRankAt(file, rank + inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(file, rank + inc));
					board.getFileRankAt(file, rank + inc).setThreat(this);
				}
				break;
			}		
			inc++;
		}
		//South
		inc = 1;
		while (board.getFileRankAt(file, rank - inc) != null){
			if (!board.getFileRankAt(file, rank - inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(file, rank - inc));
				board.getFileRankAt(file, rank - inc).setThreat(this);
				
			}else{
				if (board.getFileRankAt(file, rank - inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(file, rank - inc));
					board.getFileRankAt(file, rank - inc).setThreat(this);
				}
				break;
			}		
			inc++;
		}
		
		//East
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank).setThreat(this);
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank));
					board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank).setThreat(this);
				}
				break;
			}		
			inc++;
		}	
		
		//West
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).setThreat(this);
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank));
					board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank).setThreat(this);
				}
				break;
			}		
			inc++;
		}
		// Castleable
		if (isCanCastle()){
			if (this.getCurrentFR().getFile() == 'a'){
				nextPossibleMoves.add(board.getFileRankAt('d', this.getCurrentFR().getRank()));
			}
			else{
				nextPossibleMoves.add(board.getFileRankAt('f', this.getCurrentFR().getRank()));
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
	 * Returns whether or not the Rook can castle
	 * @return true if can castle, false otherwise
	 */
	public boolean isCanCastle() {
		return canCastle;
	}

	/**
	 * Sets whether or not the Rook can castle
	 * @return true if can castle, false otherwise
	 */
	public void setCanCastle(boolean canCastle) {
		this.canCastle = canCastle;
		if (isCanCastle()){
			if (this.getCurrentFR().getFile() == 'a'){
				nextPossibleMoves.add(board.getFileRankAt('d', this.getCurrentFR().getRank()));
			}
			else{
				nextPossibleMoves.add(board.getFileRankAt('f', this.getCurrentFR().getRank()));
			}
		}
	}
	
	/**
	 * Sets whether or not the Rook is on its first move
	 * @param firstMove true if first move, false otherwise
	 */
	public void setFirstMove(boolean firstMove){
		this.firstMove = firstMove;
	}
	
	/**
	 * Returns whether or not the Rook has moved
	 * @return true if moved, false otherwise
	 */
	public boolean moved(){
		return !firstMove;
	}
	
	/**
	 * Returns a string representation of a Rook
	 */
	@Override
	public String toString() {
		return super.toString() + "R";
	}

}
