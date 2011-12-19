import java.awt.Color;
import java.io.Serializable;
/**
 * Provides a unique color and visibility for each of 6 different possible occupants of a tile
 * @author Michael Mattson - cs361
 *
 *	Modification - 12/19/2011
 */
public enum Occupant implements Serializable{
	WATER(Color.BLUE, 'A'),
	ARMOREDSHIP(Color.LIGHT_GRAY,'R'),
	UNARMOREDSHIP(Color.GRAY, 'R'),
	DAMAGEDSHIP(Color.BLACK, 'R'),
	BASE(Color.YELLOW,'A'),
	DAMAGEDBASE(new Color(238,187,0),'A'),
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
	
}
