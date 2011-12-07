import java.awt.Point;


public class Cruiser extends Ship{

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
		this.startShip(position);
		
	}

	@Override
	public String toString(){
		return ("Cruiser");
	}


}

