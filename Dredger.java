import java.awt.Point;

public class Dredger extends Ship{

	private int max_dam = 2; //Max value each ship block can have

	// No argument constructor
	public Dredger(){
		super();
		this.setSize(2);
		this.resetDamage(max_dam);
	}

	// Single argument constructor
	public Dredger(Point position){
		super();
		this.setSize(5);
		this.resetDamage(max_dam);
		this.moveShip(position);
	}

	// Two argument constructor
	public Dredger(Point position, char direction){
		super();
		this.setSize(5);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
	}

	// Three argument constructor
	public Dredger(Point position, char direction, String owner){
		super();
		this.setSize(5);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
		this.setOwner(owner);
	}

	public Point[] getSonarRange(){
		return this.getRadarRange();
	}

	@Override
	public String toString(){
		return ("Dredger");
	}

}
