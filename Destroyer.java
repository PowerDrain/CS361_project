//Testing for Mike

import java.awt.Point;

public class Destroyer extends Ship{

	private int max_dam = 1; //Max value each ship block can have

	// No argument constructor
	public Destroyer(){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
	}


	// Two argument constructor
	public Destroyer(Point position, char direction){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
		this.setDirection(direction);
		this.startShip(position);
		
	}

	public Point[] checkTurnMobility(){
		//TODO
	}

	public void turnShip(){
		//TODO
	}

	@Override
	public Point[] getRotationalMobility(){
		//TODO
	}

	@Override
	public void rotateShip(Point position){
		//TODO
	}

	@Override
	public String toString(){
		return ("Destroyer");
	}

}
