package model;

/**
 * A class representation of the Bishop piece
 */
import model.Utils.Color;

public class Bishop extends Piece{

	/**
	 * Constructor for the Bishop
	 * @param color color to set the Bishop to
	 */
	public Bishop(Color color) {
		super(color);
	}

	/**
	 * Sets the next possible moves for the Bishop
	 * 
	 * Legal moves contain FileRanks that are in all four diagonal directions
	 * until a friendly piece is blocking further movement or opposing piece that may be captured
	 */
	@Override
	public void setNextPossibleMoves() {
		resetPossibleMoves();
		char file = this.getCurrentFR().getFile();
		int rank = this.getCurrentFR().getRank();
		//North-West
		int inc = 1;
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
	 * Returns a string representation of the Bishop
	 */
	@Override
	public String toString() {
		return super.toString() + "B";
	}

}
