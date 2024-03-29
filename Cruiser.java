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


public class Cruiser extends Ship{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int max_dam = 1; //Max value each ship block can have

	// No argument constructor
	public Cruiser(){
		super();
		this.setSize(5);
		this.resetDamage(max_dam);
	}


	// Two argument constructor
	public Cruiser(Point position, char direction){
		super();
		this.setSize(5);
		this.resetDamage(max_dam);
		this.setDirection(direction);
		this.setPosition(position);
		
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

