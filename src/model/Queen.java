package model;

/**
 * A class representation of the Queen piece
 * 
 * @author jmm754 and yuky
 */

import model.Utils.Color;

public class Queen extends Piece{

	/**
	 * Constructor for the Queen
	 * @param color color to set the Queen to
	 */
	public Queen(Color color) {
		super(color);
	}

	/**
	 * Sets the next possible moves for the Queen
	 * 
	 * Legal moves contain the FileRanks that are in all vertical, horizontal, and diagonal directions
	 * until a friendly piece is blocking further movement or an opposing piece that may be captured
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
		
		//North-East
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc).setThreat(this);
				
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc));
					board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank + inc).setThreat(this);
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
		
		//South-East
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc));
				board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc).setThreat(this);
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc));
					board.getFileRankAt(Utils.convert(Utils.convert(file) + inc), rank - inc).setThreat(this);
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
		
		//South-West
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc).setThreat(this);
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc));
					board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank - inc).setThreat(this);
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
		
		//North-West
		inc = 1;
		while (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc) != null){
			if (!board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc).isOccupied()){
				nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc));
				board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc).setThreat(this);
			}else{
				if (board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc).getCurrentPiece().getColor() != this.getColor()){
					nextPossibleMoves.add(board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc));
					board.getFileRankAt(Utils.convert(Utils.convert(file) - inc), rank + inc).setThreat(this);
				}
				break;
			}		
			inc++;
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
	 * Returns a string representation of the Queen
	 */
	@Override
	public String toString(){
		return super.toString() + "Q";
	}

}
