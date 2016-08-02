package model;

/**
 * A class representation of the Pawn piece
 * 
 * @author jmm754 and yuky
 */

import model.Utils.Color;

public class Pawn extends Piece{

	private boolean moved;
	
	/**
	 * Constructor for the Pawn
	 * @param color color to set the Pawn to
	 */
	public Pawn(Color color) {
		super(color);
		setMoved(false);
	}

	/**
	 * Returns whether or not the Pawn has moved yet
	 * @return true if moved, false otherwise
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * Sets whether or not the Pawn has moved
	 * @param moved true if moved, false otherwise
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	/**
	 * Moves the Pawn to a specified FileRank
	 * Once moved, the Pawn is no longer eligible to jump a FileRank
	 * 
	 * If the distance moved is greater than one, then the FileRank jumped is possible for EnPassant
	 * If FileRank moved to was an EnPassant FileRank, then the opposing Pawn is removed from play
	 */
	@Override
	public boolean move(FileRank to){
		int distance = Math.abs(this.getCurrentFR().getRank() - to.getRank());
		boolean moveable = super.move(to);
		if(moveable){
			if (distance > 1){
				if (this.getColor() == Color.WHITE){
					board.getFileRankAt(this.getCurrentFR().getFile(), this.getCurrentFR().getRank() - 1).setCanEnPassant(true);
				}else{
					board.getFileRankAt(this.getCurrentFR().getFile(), this.getCurrentFR().getRank() + 1).setCanEnPassant(true);
				}
			}
			if (to.getCanEnPassant()){
				if(this.getColor() == Color.WHITE){
					board.getFileRankAt(to.getFile(), to.getRank() - 1).getCurrentPiece().setInPlay(false);
					board.getFileRankAt(to.getFile(), to.getRank() - 1).removeCurrentPiece();
				}else{
					board.getFileRankAt(to.getFile(), to.getRank() + 1).getCurrentPiece().setInPlay(false);
					board.getFileRankAt(to.getFile(), to.getRank() + 1).removeCurrentPiece();
				}
				to.setCanEnPassant(false);
			}
			setMoved(true);
		}
		return moveable;
	}
	
	/**
	 * Sets the next possible moves for the Pawn piece
	 * 
	 * Legal moves contain spaces that are one rank forward if space is empty
	 * If an opposing piece is one diagonal forward adjacent to the Pawn, then the Pawn may capture the opposing piece
	 * If the Pawn has not yet moved, then the first move the Pawn can jump one FileRank
	 * If the criteria of EnPassant is met, then the EnPassant FileRank is legal as well
	 */
	@Override
	public void setNextPossibleMoves(){
		resetPossibleMoves();
		if (this.getCurrentFR() == null ){
			return;
		}
		char file = this.getCurrentFR().getFile();
		int rank = this.getCurrentFR().getRank();
		if (this.getColor() == Color.WHITE){
			//First move
			if (!moved){
				//Two spaces forward
				if (board.getFileRankAt(file, rank + 2) != null
				&& !board.getFileRankAt(file, rank + 2).isOccupied()){
					nextPossibleMoves.add(board.getFileRankAt(file, rank + 2));
					board.getFileRankAt(file, rank + 2).setThreat(this);
				}
			}	
			
			//One space
			if (board.getFileRankAt(file, rank + 1) != null
			&& !board.getFileRankAt(file,  rank + 1).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(file, rank + 1));
				board.getFileRankAt(file, rank + 1).setThreat(this);
			}
			//Diagonal 1
			if ((board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1) != null
			&&  board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).getCurrentPiece().getColor() != this.getColor()))
			|| (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1) != null
			&& !board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).getCanEnPassant()))){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank + 1).setThreat(this);
			}
			//Diagonal 2
			if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1) != null
			&&  board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).getCurrentPiece().getColor() != this.getColor())
			|| (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1) != null
			&& !board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).getCanEnPassant()))){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank + 1).setThreat(this);
			}
		}else{
			if (!moved){
				//Two spaces forward
				if (board.getFileRankAt(file, rank - 2) != null
				&& !board.getFileRankAt(file, rank - 2).isOccupied()){
					nextPossibleMoves.add(board.getFileRankAt(file, rank - 2));
					board.getFileRankAt(file, rank - 2).setThreat(this);
				}
			}
			//One space
			if (board.getFileRankAt(file, rank - 1) != null
			&& !board.getFileRankAt(file,  rank - 1).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(file, rank - 1));
				board.getFileRankAt(file, rank - 1).setThreat(this);
			}
			//Diagonal 1
			if (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1) != null
			&&  board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).getCurrentPiece().getColor() != this.getColor())
			|| (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1) != null
			&& !board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).getCanEnPassant()))){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - 1), rank - 1).setThreat(this);
			}
			//Diagonal 2
			if (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1) != null
			&&  board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).getCurrentPiece().getColor() != this.getColor())
			|| (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1) != null
			&& !board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).isOccupied()
			&& (board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).getCanEnPassant()))){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + 1), rank - 1).setThreat(this);
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
	 * Returns a string representation of a Pawn
	 */
	@Override
	public String toString() {
		return super.toString() + "p";
	}

}
