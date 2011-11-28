import java.awt.Point;
import java.util.ArrayList;;


public abstract class Ship {

	// Data Fields
	private int shipSize;		// Size of ship, ex: Cruiser = 5, Torpedo boat = 4. . .
	private Point position;		// Coordinate of the bow of the ship
	private char direction;    	// cardinal direction of the ship: n, e, s, w
	private int[] shipDamage;	// holds the damage of each square of the ship
	private String owner;		// holds the owner of the ship either P1 or P2
	private int maxHealth;		// maximum health a ship box can have, 1 or 2

	// Class Methods

	// No argument constructor
	public Ship(){
	}

	// Sets the size of the ship, ex: Cruiser = 5, Torpedo boat = 4. . .
	public void setSize(int size){
		this.shipSize = size;
	}

	// Returns the size of the ship
	public int getSize(){
		return shipSize;
	}

	// Takes a point as the new position, checks validity and then sets the point of the new bow
	private void setPosition(Point pos)throws IllegalArgumentException {
		if (pos.x < 0 || pos.y < 0 || pos.x > 30 || pos.y > 30){
			throw new IllegalArgumentException();
		} else {
			this.position = pos;
		}
	}

	// Returns the position of the ship's bow
	public Point getPosition(){
		return position;
	}

	// Takes the direction as an argument and checks for validity and sets then sets the direction
	public void setDirection(char dir) throws IllegalArgumentException {
		if (dir == 'N' || dir == 'n'){
			this.direction = 'n';
		} else if (dir == 'E' || dir == 'e'){
			this.direction = 'e';
		} else if (dir == 'S' || dir == 's'){
			this.direction = 's';
		} else if (dir == 'W' || dir == 'w'){
			this.direction = 'w';
		} else {
			throw new IllegalArgumentException();
		}
	}

	// Returns the direction of the ship, in lower case
	public char getDirection(){
		return direction;
	}

	// Takes no argument and sets the damage of each block to 1 (untouched)
	public void resetDamage(int value){
		this.maxHealth = value;
		this.shipDamage = new int[this.shipSize];
		for (int i = 0; i < this.shipDamage.length; i++){
			this.shipDamage[i] = value;
		}
	}

	// Takes no arguments and returns an array containing the states of each block of the ship
	public int[] getDamage(){
		return shipDamage;
	}

	// Takes a string player and checks to verify it is a valid argument then sets the ship owner
	public void setOwner(String player)throws IllegalArgumentException {
		if (player.equalsIgnoreCase("P1")){
			this.owner = "P1";
		} else if (player.equalsIgnoreCase("P2")){
			this.owner = "P2";
		} else {
			throw new IllegalArgumentException();
		}
	}

	// Returns the owner of the ship
	public String getOwner(){
		return owner;
	}

	public Point[] getRadarRange(){
		ArrayList<Point> temp = new ArrayList<Point>();
		//TODO
		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}

	public Point[] getGunRange(){
		ArrayList<Point> temp = new ArrayList<Point>();
		//TODO
		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}


	public Point[] getShipCoordinates() throws IllegalStateException {
		Point[] retVal = new Point[this.shipSize];
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x, this.position.y - i);
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x - i, this.position.y);
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x, this.position.y + i);
			}
			break;
		case 'w':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x + i, this.position.y);
			}
			break;
		default:
			throw new IllegalStateException();
		}
		return retVal;
	}

	boolean aFloat(){
		int temp = 0;
		for (int i = 0; i < this.shipDamage.length; i++){
			temp = temp + this.shipDamage[i];
		}
		return (temp != 0);
	}

	public void receiveDamage(Point damLoc, char type){
		//TODO   
		//stuff
	}

	public void repairDamage(){
		boolean repaired = false;
		for (int i = 0; i < this.shipDamage.length; i++){
			if (this.shipDamage[i] == 0 && repaired == false){
				this.shipDamage[i] = maxHealth;
				repaired = true;
			} else if (this.shipDamage[i] == 1 && repaired == false){
				this.shipDamage[i] = maxHealth;
				repaired = true;
			}

		}
		if (repaired == false){
			error ("repairDamage called on but the ship has no damage to repair.");
		}
	}

	void moveShip(Point location){
		this.setPosition(location);
	}

	private Point[] getRightMobility() {
		ArrayList<Point> temp = new ArrayList<Point>();
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + 1, this.position.y - i));
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - i, this.position.y - 1));
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - 1, this.position.y + i));
			}
			break;
		case 'w':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + i, this.position.y + 1));
			}
			break;
		default:
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}

	private Point[] getLeftMobility(){
		ArrayList<Point> temp = new ArrayList<Point>();
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - 1, this.position.y - i));
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - i, this.position.y + 1));
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + 1, this.position.y + i));
			}
			break;
		case 'w':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + i, this.position.y - 1));
			}
			break;
		default:
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}

	private Point[] getForwardMobility(){
		ArrayList<Point> temp = new ArrayList<Point>();
		int stillGood = 0;
		for (int i = 0; i < this.shipDamage.length; i++){
			stillGood = stillGood + this.shipDamage[i];
		}
		switch (this.direction){
		case 'n':
			for (int i = 1; i <= stillGood * 2; i++){
				temp.add(new Point(this.position.x, this.position.y + 1));
			}
			break;
		case 'e':
			for (int i = 1; i <= stillGood * 2; i++){
				temp.add(new Point(this.position.x + 1, this.position.y));
			}
			break;
		case 's':
			for (int i = 1; i <= stillGood * 2; i++){
				temp.add(new Point(this.position.x , this.position.y - i));
			}
			break;
		case 'w':
			for (int i = 1; i <= stillGood * 2; i++){
				temp.add(new Point(this.position.x - i, this.position.y));
			}
			break;
		default:
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}

	private Point getBackwardMobility(){
		Point retVal = new Point();
		switch (this.direction){
		case 'n':
			retVal = new Point(this.position.x, this.position.y - this.shipSize);
			break;
		case 'e':
			retVal = new Point(this.position.x - this.shipSize, this.position.y);
			break;
		case 's':
			retVal = new Point(this.position.x, this.position.y + this.shipSize);
			break;
		case 'w':
			retVal = new Point(this.position.x + this.shipSize, this.position.y);
			break;
		default:
			throw new IllegalStateException();
		}
		return retVal;
	}

	public Point[] getMoveMobility(){
		Point[] left = this.getLeftMobility();
		Point[] right = this.getRightMobility();
		Point[] front = this.getForwardMobility();
		Point[] back = new Point[1];
		back[0] = this.getBackwardMobility();
		Point[]retVal = new Point[left.length + right.length + front.length + back.length];
		// Copy all arrays into a single returnable array
		System.arraycopy(left, 0, retVal, 0, left.length);
		System.arraycopy(right, 0, retVal, left.length, right.length);
		System.arraycopy(front, 0, retVal, left.length + right.length, front.length);
		System.arraycopy(back, 0, retVal, left.length + right.length + front.length, back.length);
		return retVal;
	}

	public void rotateShip(Point position){
		//TODO
	}

	public Point[] getRotationalMobility(){
		ArrayList<Point> temp = new ArrayList<Point>();

		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
		//TODO
	}
	private void error(String error){
		System.out.println(error);
	}

}

