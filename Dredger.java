/* Created by David Gamez
 * Created for CS361 Naval Whoop Ass Project
 * Team name: The Compilers
 * Team members:
 * David Gamez
 * Tommy Grelecki
 * Alton Yee
 * Michael Mattson
 */

import java.awt.Point;

public class Dredger extends Ship{

	private int max_dam = 2; //Max value each ship block can have

	// No argument constructor
	public Dredger(){
		super();
		this.setSize(2);
		this.resetDamage(max_dam);
	}


	// Two argument constructor
	public Dredger(Point position, char direction){
		super();
		this.setSize(2);
		this.resetDamage(max_dam);
		this.setDirection(direction);
		this.startShip(position);
		
	}


	public Point[] getSonarRange(){
		return this.getRadarRange();
	}

	@Override
	public String toString(){
		return super.toString();
	}
	
	public static Cruiser fromString(String s){
		//TODO Figure out a way to apply the damage to the ship. . .
		int xCoor = s.charAt(2);
		int yCoor = s.charAt(4);
		char direction = s.charAt(6);
		Point startingPosition = new Point(xCoor, yCoor);
		return new Cruiser(startingPosition, direction);
	}

}
