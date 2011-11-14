
import java.awt.Point;

public class Destroyer extends Ship{

	private int max_dam = 1; //Max value each ship block can have

	// No argument constructor
	public Destroyer(){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
	}

	// Single argument constructor
	public Destroyer(Point position){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
		this.moveShip(position);
	}

	// Two argument constructor
	public Destroyer(Point position, char direction){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
	}

	// Three argument constructor
	public Destroyer(Point position, char direction, String owner){
		super();
		this.setSize(3);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
		this.setOwner(owner);
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
