
import java.awt.Point;

public class Torpedo extends Ship{

	private int max_dam = 1; //Max value each ship block can have

	// No argument constructor
	public Torpedo(){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
	}

	// Single argument constructor
	public Torpedo(Point position){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
		this.moveShip(position);
	}

	// Two argument constructor
	public Torpedo(Point position, char direction){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
	}

	// Three argument constructor
	public Torpedo(Point position, char direction, String owner){
		super();
		this.setSize(4);
		this.resetDamage(max_dam);
		this.moveShip(position);
		this.setDirection(direction);
		this.setOwner(owner);
	}
	@Override
	public String toString(){
		return ("Torpedo");
	}
}