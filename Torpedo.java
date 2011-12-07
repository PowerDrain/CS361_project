import java.awt.Point;

public class Torpedo extends Ship{

	private int max_dam = 1; //Max value each ship block can have

	// No argument constructor
	public Torpedo(){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
	}

	
	// Two argument constructor
	public Torpedo(Point position, char direction){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
		this.setDirection(direction);
		this.startShip(position);
	
	}

	@Override
	public String toString(){
		return ("Torpedo");
	}
}