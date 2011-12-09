/*
 * SquareTile.java
 * Michael Mattson - cs361
 * 
 * Modification - 12/5/2011
 * Description:
 * 	Models a tile with certain characteristics that represent coordinates with different 
 * 	occupants in a battleship game.
 */


import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
public class SquareTile {
	public static final int WIDTH = 20; // Subject to change
	
	private Occupant _occupant;
	private SquareCoordinate _location;
	private Object _tileOwner = null; // must be either base object or ship object
	
	/**
	 * SquareTile Constructor
	 * @param location SquareCoordinate
	 * @param o	Occupant
	 * @param tileOwner Object (must be Ship, Base, or null)
	 * @throws IllegalArgumentException if the tileOwner parameter is not a Ship, Base, or null
	 */
	public SquareTile(SquareCoordinate location, Occupant o, Object tileOwner) throws IllegalArgumentException{
		_location = location;
		_occupant = o;
		if(!(tileOwner instanceof Base) && !(tileOwner instanceof Ship) && tileOwner != null) throw new IllegalArgumentException("tile owner must be a ship, base, or null.");
		_tileOwner = tileOwner;
	}
	
	/**
	 * Mutator for tileOwner data field
	 * @param tileOwner Object (must be Ship, Base, or null)
	 * @throws IllegalArgumentException if the tileOwner parameter is not a Ship, Base, or null
	 */
	public void setTileOwner(Object tileOwner) throws IllegalArgumentException{
		if(!(tileOwner instanceof Base) && !(tileOwner instanceof Ship) && tileOwner != null) throw new IllegalArgumentException("tile owner must be a ship, base, or null.");
		_tileOwner = tileOwner;
	}
	
	/**
	 * Converts a SquareTile to a String in the form Occupant(x,y)tileShip.toString()
	 */
	@Override
	public String toString(){
		//TODO
		String result = _occupant.toString() + _location.toString();// + _tileOwner.toString();
		
		return result;
	}
	
	/**
	 * Returns the occupant of this tile
	 * @return _occupant
	 */
	public Occupant getOccupant(){
		return _occupant;
	}
	
	public boolean tileOwnerIsBase(){
		if(this._tileOwner instanceof Base){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean tileOwnerIsShip(){
		if(this._tileOwner instanceof Ship){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean tileHasNoOwner(){
		if(this._tileOwner == null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns the reference of this tiles tileOwner
	 * @return _tileShip which is either a Ship, Base, or null
	 */
	public Object getTileOwner(){
		return _tileOwner;
	}
	
	/**
	 * Returns whether or not tile is visible by sonar 
	 * @return true if tile is visible by sonar and false otherwise
	 */
	public boolean isVisibleSonar(){
		return _occupant.isVisibleSonar();
	}
	
	/**
	 * Returns whether or not tile is visible by radar
	 * @return true if visible by radar and false otherwise
	 */
	public boolean isVisibleRadar(){
		return _occupant.isVisibleRadar();
	}
	
	/**
	 * Returns location of this SquareTile
	 * @return SquareCoordinate representing center of this SquareTile
	 */
	public SquareCoordinate getLocation(){
		return _location;
	}
	
	/**
	 * Sets SquareTiles occupant to the given Occupant o
	 * @param o
	 */
	public void setOccupant(Occupant o){
		_occupant = o;
	}

	/**
	 * Draws this tile to screen based on visibility and enemy status;
	 * visiblity 'n' == not visible, 'e' == enemy and in visibility, 'm' == mine not in visibility, 'v' == in visibility
	 * @param g, visiblity
	 */
	public void draw(Graphics g, char visiblity){
		Polygon squareTile = _location.toPolygon(WIDTH);
		if(visiblity == 'n'){
			g.setColor(Color.blue.darker());
			g.fillPolygon(squareTile);
			g.setColor(Color.black);
			g.drawPolygon(squareTile);
		}else if(visiblity == 'e'){
			g.setColor(this.getOccupant().getColor());
			g.fillPolygon(squareTile);
			g.setColor(Color.red);
			g.drawPolygon(squareTile);
		}else if(visiblity == 'm'){
			g.setColor(Color.blue);
			g.fillPolygon(squareTile);
			g.setColor(Color.black);
			g.drawPolygon(squareTile);
		}else{
			g.setColor(this.getOccupant().getColor());
			g.fillPolygon(squareTile);
			g.setColor(Color.black);
			g.drawPolygon(squareTile);
		}
	}
	
	@Override
	public boolean equals(Object guest){
		if(guest instanceof SquareTile){
			if(this.getLocation().equals(((SquareTile)guest).getLocation()) && this.getOccupant().equals(((SquareTile)guest).getOccupant()) && 
					this.getTileOwner()==((SquareTile)guest).getTileOwner()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * Returns the SquareTile specified by the given string
	 * @param s
	 * @return SquareTile specified by s
	 * @throws FormatException
	 */
	public static SquareTile fromString(String s) throws FormatException{
		//TODO change to include tileShip data field
		int i = s.indexOf('(');
		int j = s.indexOf(')');
		if(i == -1 || j == -1) throw new FormatException("String must include '(' and ')' to be converted to squaretile: " + s);
		Occupant o = null;
		try{
			o = Occupant.valueOf(s.substring(0,i));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		SquareCoordinate c = null;
		try{
			String subS;
			subS = s.substring(i,j+1);
			c = SquareCoordinate.fromString(subS);
		}
		catch(FormatException e){
			System.out.println(e);
		}
		Object tileOwner = null;
		//try{
		//	String subS;
		//	subS = s.substring(j+1);
		//	tileShip = Ship.fromString(subS);
		//}
		//catch(FormatException e){
		//	System.out.println(e);
		//}/*
		
		return new SquareTile(c, o, tileOwner);
	}
}
