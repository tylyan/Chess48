package model;

/**
 * A class representation of a chess board.
 * The board contains a 2-dimensional grid of FileRanks
 * 
 * @author jmm754 and yuky
 */
import model.Utils.Color;

public class Board {
	
	FileRank[][] gameBoard;
	
	/**
	 * Constructor for the chess board, creates the specified number of FileRanks
	 * @param files number of files to be created
	 * @param ranks number of ranks to be created
	 */
	public Board(int files, int ranks){
		gameBoard = new FileRank[files][ranks];
		for (int i = 0; i < files; i++){
			for (int j = 0; j < ranks; j++){
				gameBoard[i][j] = new FileRank(j + 1, ranks - i);
			}
		}
	}
	
	/**
	 * Returns the FileRank at a given location
	 * @param file file of the FileRank to be returned
	 * @param rank rank of the FileRank to be returned
	 * @return the specified FileRank requested if exists, null if request is off the board
	 */
	public FileRank getFileRankAt(char file, int rank){
		//Off of the board
		if (Utils.convert(file) > gameBoard[0].length || 
				rank > gameBoard.length || 
				Utils.convert(file) < 1 || rank < 1){
			return null;
		}
		return gameBoard[gameBoard.length - rank][Utils.convert(file) - 1];
	}
	
	/**
	 * Resets the threat for a given player
	 * @param player player to reset threat
	 */
	public void resetAllThreats(Player player){
		if(player.getColor() == Color.WHITE){
			for(int j = 0; j < gameBoard.length; j++){
				for(int k = 0; k < gameBoard[j].length; k++){
					this.getFileRankAt(Utils.convert(j + 1), k + 1).resetThreat(Color.WHITE);
					
				}
			}
		}else{
				for(int j = 0; j < gameBoard.length; j++){
					for(int k = 0; k < gameBoard[j].length; k++){
						this.getFileRankAt(Utils.convert(j + 1), k + 1).resetThreat(Color.BLACK);
					}
				}
			}
		}
	
	/**
	 * Decrements the EnPassant counter for all of the FileRanks
	 */
	public void tickEPCounts(){
		for (int i = 0; i < gameBoard.length; i ++){
			for (int j = 0; j < gameBoard[0].length; j++){
				gameBoard[i][j].tickEPCount();
			}
		}
	}
	
	/**
	 * Promotes a piece to another specified piece
	 * If the FileRank is not valid (rank = 1 or rank = 8), or the piece promoted is not a pawn, then promotion fails
	 * 
	 * @param fr the FileRank that the promotion takes place
	 * @param p char representation of the desired piece to promote to
	 */
	public void promote(FileRank fr, char p){
		if (fr == null){
			return;
		}
		Piece piece = fr.getCurrentPiece();
		// Should never run into this, but only pawn can promote
		if (!(piece instanceof Pawn)){
			return;
		}
		// Must be on last rank
		if (piece.getColor() == Color.WHITE){
			if (fr.getRank() != 8){
				return;
			}
		}else{
			if (fr.getRank() != 1){
				return;
			}
		}
		
		piece = Utils.pieceFromChar(p, piece.getColor());
		piece.setBoard(this);
		fr.setCurrentPiece(piece);
		piece.setCurrentFR(fr);
		piece.resetPossibleMoves();
		piece.setNextPossibleMoves();
	}
	
	/**
	 * Prints the chess board to the console
	 * If FileRank is not occupied, the color of the FileRank is printed
	 * If FileRank is occupied, the piece occupying the FileRank is printed
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < gameBoard.length; i++){
			for (int j = 0; j < gameBoard[i].length; j++){
				if (gameBoard[i][j].isOccupied()){
					sb.append(gameBoard[i][j].getCurrentPiece().toString() + " ");
				}else if (gameBoard[i][j].getColor() == Color.BLACK){
					sb.append("## ");
				}else{
					sb.append("   ");
				}
				/*sb.append(gameBoard[i][j].getFile());
				sb.append(gameBoard[i][j].getRank() + " ");
				*/
			}
			sb.append( (gameBoard.length - i) + "\n");
		}
		for (int k = 0; k < gameBoard[0].length; k++){
			sb.append(" " + Utils.convert(k + 1) + " ");
		}
		sb.append("\n");
		return sb.toString();
	}

}
