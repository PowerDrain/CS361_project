/*
 * SquareTile.java
 * Michael Mattson - cs361
 * 
 * Modification - 11/20/2011
 * Description:
 * 	Models a tile with certain characteristics that represent coordinates with different 
 * 	occupants in a battleship game.
 */
package mjm.lib.gameboard;

import mjm.lib.gameboard.FormatException;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
public class SquareTile {
	public static final int WIDTH = 20; // Subject to change
	
	private Occupant _occupant;
	private SquareCoordinate _location;
	private Ship tileShip = null;
	
	public SquareTile(SquareCoordinate location, Occupant o){
		_location = location;
		_occupant = o;
		tileShip = null;
	}
	
	public boolean setShip(Ship s){
		tileShip = s;
		return true;
	}
	
	@Override
	public String toString(){
		//TODO
		String result = _occupant.toString() + _location.toString() + tileShip.toString();
		return result;
	}
	
	/**
	 * Returns the occupant of this tile
	 * @return _occupant
	 */
	public Occupant getOccupant(){
		return _occupant;
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
	 * Draws this tile to screen
	 * @param g
	 */
	public void draw(Graphics g){
		Polygon square = _location.toPolygon(WIDTH);
		g.setColor(_occupant.getColor());
		g.fillPolygon(square);
		g.drawPolygon(square);
	}
	
	@Override
	public boolean equals(Object guest){
		//TODO check tileShip field
		if(guest instanceof SquareTile){
			if(this.getLocation().equals(((SquareTile)guest).getLocation()) && this.getOccupant().equals(((SquareTile)guest).getOccupant()) ){
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
		
		return new SquareTile(c, o);
	}
}
