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
		return ("Dredger");
	}

}
