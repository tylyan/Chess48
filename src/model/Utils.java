package model;

/**
 * A utility class that contains several static methods that are related to Chess
 * 
 * @author jmm754 and yuky
 *
 */
public class Utils {
	
	/**
	 * Colors that are valid in chess, either black or white
	 *
	 */
	public enum Color{
		BLACK, WHITE
	}
	
	/**
	 * Given a character, returns the corresponding integer value
	 * Used for converting char represented files to integers so array indexing is possible
	 * @param c character to convert
	 * @return integer representation of character
	 */
	public static int convert(char c){
		return c - 96;
	}
	
	/**
	 * Given an integer, returns the corresponding character value
	 * Used for converting integer represented files to back to characters
	 * @param i integer to convert 
	 * @return character representation of integer
	 */
	public static char convert(int i){
		return (char) (i + 96);
	}

	/**
	 * Given a character representation of a piece and a color, returns a new piece corresponding to the character
	 * @param c character representation of desired piece
	 * @param color color to set the piece to
	 * @return the corresponding Piece to the character given, Queen is returned by default
	 */
	public static Piece pieceFromChar(char c, Color color){
		switch(c){
		case 'R':
			return new Rook(color);
		case 'N':
			return new Knight(color);
		case 'B':
			return new Bishop(color);
		default:
			return new Queen(color);
		}
	}
}
