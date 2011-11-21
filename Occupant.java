package mjm.lib.gameboard;
import java.awt.Color;
/**
 * Provides a unique color and visibility for each of 6 different possible occupants of a tile
 * @author Michael Mattson - cs361
 *
 */
public enum Occupant {
	WATER(Color.BLUE, 'A'),
	ARMOREDSHIP(Color.LIGHT_GRAY,'R'),
	UNARMOREDSHIP(Color.GRAY, 'R'),
	DAMAGEDSHIP(Color.BLACK, 'R'),
	BASE(Color.YELLOW,'A'),
	REEF(Color.GREEN, 'A'),
	MINE(Color.RED, 'S');
	

	private final Color _color;
	private final char _visibility; // 'A' = Always visible, 'S' = Visible by sonar, 'R' = Visible by radar
	
	
	/**
	 * Constructor for enum Occupant, sets _color field to color associated with the 
	 * Occupant type being specified by the client
	 * @param color
	 */
	private Occupant(Color color, char visiblility){
		_color = color;
		_visibility = visiblility;
		
	}
	
	/**
	 * Returns the color associated with the Occupant type being specified by the client
	 * @return Color of Occupant type 
	 */
	public Color getColor(){
		return this._color;
	}
	
	/**
	 * Returns whether or not occupant is always visible
	 * @return true if occupant is always visible and false otherwise
	 */
	public boolean isVisibleAlways(){
		if(_visibility == 'A'){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns whether or not occupant is visible to Radar
	 * @return true if occupant is visible to radar and false otherwise
	 */
	public boolean isVisibleRadar(){
		if(_visibility == 'R'){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns whether or not occupant is visible to sonar
	 * @return true if occupant is visible to sonar and false otherwise
	 */
	public boolean isVisibleSonar(){
		if(_visibility == 'S'){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Parses a string and returns the indicated Occupant type
	 * @param occupantType
	 * @return Occupant type indicated by the given string
	 */
	public static Occupant parseOccupant(String occupantType){
		if(occupantType.equals("Occupant.WATER")){
			return Occupant.WATER;
		}else{
			return Occupant.MINE;
		}
	}
	
}
