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
import java.io.Serializable;
import java.util.ArrayList;;


public abstract class Ship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Data Fields
	private int shipSize;		// Size of ship, ex: Cruiser = 5, Torpedo boat = 4. . .
	private Point position;		// Coordinate of the bow of the ship
	private char direction;    	// cardinal direction of the ship: n, e, s, w
	private int[] shipDamage;	// holds the damage of each square of the ship
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
	
	public int getPointValue(){
		int pointValue = 0;
		if (!this.aFloat()){
			return 0;
		}
		for (int i = 0; i < shipDamage.length; i++){
			if (shipDamage[i] == 2){
				pointValue += 3;
			} else if (shipDamage[i] == 1){
				pointValue += 1;
			}
		}
		return pointValue;
	}

	// Takes no argument and sets the damage of each block to 1 (untouched)
	public void resetDamage(int maxDamage){
		this.maxHealth = maxDamage;
		this.shipDamage = new int[this.shipSize];
		for (int i = 0; i < this.shipDamage.length; i++){
			this.shipDamage[i] = maxDamage;
		}
	}

	// Takes no arguments and returns an array containing the states of each block of the ship
	public int[] getDamage(){
		return shipDamage;
	}

	public Point[] getRadarRange() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		switch (this.direction) {
		case 'n':
			for (int i = 1; i <= this.shipSize + 1; i++){
				temp.add(new Point(this.position.x, this.position.y - i));
				temp.add(new Point(this.position.x - 1, this.position.y - i));
				temp.add(new Point(this.position.x + 1, this.position.y - i));
			}
			for (int i = 0; i < this.shipSize - 1; i++){
				temp.add(new Point(this.position.x - 1, this.position.y + i));
				temp.add(new Point(this.position.x + 1, this.position.y + i));
			}

			break;
		case 'e':
			for (int i = 1; i <= this.shipSize + 1; i++){
				temp.add(new Point(this.position.x + i, this.position.y));
				temp.add(new Point(this.position.x + i, this.position.y - 1));
				temp.add(new Point(this.position.x + i, this.position.y + 1));
			}
			for (int i = 0; i < this.shipSize - 1; i++){
				temp.add(new Point(this.position.x - i, this.position.y + 1));
				temp.add(new Point(this.position.x - i, this.position.y - 1));
			}
			break;
		case 's':
			for (int i = 1; i <= this.shipSize + 1; i++){
				temp.add(new Point(this.position.x, this.position.y));
				temp.add(new Point(this.position.x - 1, this.position.y + i));
				temp.add(new Point(this.position.x + 1, this.position.y + i));
			}
			for (int i = 0; i < this.shipSize - 1; i++){
				temp.add(new Point(this.position.x - 1, this.position.y - i));
				temp.add(new Point(this.position.x + 1, this.position.y - i));
			}
			break;
		case 'w':
			for (int i = 1; i <= this.shipSize + 1; i++){
				temp.add(new Point(this.position.x - i, this.position.y));
				temp.add(new Point(this.position.x - i, this.position.y - 1));
				temp.add(new Point(this.position.x - i, this.position.y + 1));
			}
			for (int i = 0; i < this.shipSize - 1; i++){
				temp.add(new Point(this.position.x + i, this.position.y + 1));
				temp.add(new Point(this.position.x + i, this.position.y - 1));
			}
			break;
		default:
			throw new IllegalStateException();
		}
		temp.trimToSize();
		Point[] shipCoordinates = this.getShipCoordinates();
		for (int i = 0; i < shipCoordinates.length; i++){
			temp.add(shipCoordinates[i]);
		}

		// Clean up array list, change into regular array and then return
		temp.trimToSize();
		Point[] retVal = new Point[temp.size()];
		retVal = temp.toArray(retVal);
		return retVal;
	}

	public Point[] getGunRange() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		Point cornerOfRange;
		switch (this.direction) {
		case 'n':
			cornerOfRange = new Point(this.position.x - this.shipSize, this.position.y - this.shipSize);
			for (int i = 0; i < (this.shipSize*3); i++){
				for (int j = 0; j < (this.shipSize*2 + 1); j++){
					temp.add(new Point(cornerOfRange.x + j, cornerOfRange.y + i));
				}
			}
			break;
		case 'e':
			cornerOfRange = new Point(this.position.x + this.shipSize, this.position.y - this.shipSize);
			for (int i = 0; i < (this.shipSize*3); i++){
				for (int j = 0; j < (this.shipSize*2 + 1); j++){
					temp.add(new Point(cornerOfRange.x - i, cornerOfRange.y + j));
				}
			}
			break;
		case 's':
			cornerOfRange = new Point(this.position.x + this.shipSize, this.position.y + this.shipSize);
			for (int i = 0; i < (this.shipSize*3); i++){
				for (int j = 0; j < (this.shipSize*2 + 1); j++){
					temp.add(new Point(cornerOfRange.x - j, cornerOfRange.y - i));
				}
			}
			break;
		case 'w':
			cornerOfRange = new Point(this.position.x - this.shipSize, this.position.y + this.shipSize);
			for (int i = 0; i < (this.shipSize*3); i++){
				for (int j = 0; j < (this.shipSize*2 + 1); j++){
					temp.add(new Point(cornerOfRange.x + i, cornerOfRange.y - j));
				}
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


	public Point[] getShipCoordinates() throws IllegalStateException {
		Point[] retVal = new Point[this.shipSize];
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x, this.position.y + i);
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x - i, this.position.y);
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				retVal[i] = new Point(this.position.x, this.position.y - i);
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

	public Point[] getAdjacentPoints() throws IllegalStateException {
		ArrayList<Point> listOfPoints = new ArrayList<Point>();
		Point[] shipPoints = this.getShipCoordinates();
		Point shipBow = this.getPosition();
		Point shipStern = shipPoints[shipPoints.length - 1];

		switch (direction){
		case 'n':
			for (int i = 0; i < shipPoints.length; i++){
				listOfPoints.add(new Point(shipPoints[i].x - 1, shipPoints[i].y));
				listOfPoints.add(new Point(shipPoints[i].x + 1, shipPoints[i].y));
			}
			listOfPoints.add(new Point(shipBow.x - 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipBow.x, shipBow.y - 1));
			listOfPoints.add(new Point(shipBow.x + 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipStern.x - 1, shipBow.y + 1));
			listOfPoints.add(new Point(shipStern.x, shipBow.y + 1));
			listOfPoints.add(new Point(shipStern.x + 1, shipBow.y + 1));
			break;
		case 'e':
			for (int i = 0; i < shipPoints.length; i++){
				listOfPoints.add(new Point(shipPoints[i].x, shipPoints[i].y - 1));
				listOfPoints.add(new Point(shipPoints[i].x, shipPoints[i].y + 1));
			}
			listOfPoints.add(new Point(shipBow.x + 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipBow.x + 1, shipBow.y));
			listOfPoints.add(new Point(shipBow.x + 1, shipBow.y + 1));
			listOfPoints.add(new Point(shipStern.x - 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipStern.x - 1, shipBow.y));
			listOfPoints.add(new Point(shipStern.x - 1, shipBow.y + 1));
			break;
		case 's':
			for (int i = 0; i < shipPoints.length; i++){
				listOfPoints.add(new Point(shipPoints[i].x - 1, shipPoints[i].y));
				listOfPoints.add(new Point(shipPoints[i].x + 1, shipPoints[i].y));
			}
			listOfPoints.add(new Point(shipBow.x - 1, shipBow.y + 1));
			listOfPoints.add(new Point(shipBow.x, shipBow.y + 1));
			listOfPoints.add(new Point(shipBow.x + 1, shipBow.y + 1));
			listOfPoints.add(new Point(shipStern.x - 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipStern.x, shipBow.y - 1));
			listOfPoints.add(new Point(shipStern.x + 1, shipBow.y - 1));
			break;
		case 'w':
			for (int i = 0; i < shipPoints.length; i++){
				listOfPoints.add(new Point(shipPoints[i].x, shipPoints[i].y - 1));
				listOfPoints.add(new Point(shipPoints[i].x, shipPoints[i].y + 1));
			}
			listOfPoints.add(new Point(shipBow.x - 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipBow.x - 1, shipBow.y));
			listOfPoints.add(new Point(shipBow.x - 1, shipBow.y + 1));
			listOfPoints.add(new Point(shipStern.x + 1, shipBow.y - 1));
			listOfPoints.add(new Point(shipStern.x + 1, shipBow.y));
			listOfPoints.add(new Point(shipStern.x + 1, shipBow.y + 1));
			break;
		default:
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		listOfPoints.trimToSize();
		Point[] retVal = new Point[listOfPoints.size()];
		retVal = listOfPoints.toArray(retVal);
		return retVal;
	}

	public boolean aFloat(){
		int temp = 0;
		for (int i = 0; i < this.shipDamage.length; i++){
			temp = temp + this.shipDamage[i];
		}
		return (temp != 0);
	}

	public boolean receiveDamage(Point damLoc, char damType) throws IllegalArgumentException {
		//TODO receive mine damage
		Point[] shipCoordinates = this.getShipCoordinates();
		boolean shipDamaged = false;
		for (int i = 0; i < shipCoordinates.length; i++){
			if (shipCoordinates[i].equals(damLoc)){
				switch(damType){
				case 't':
					shipDamaged = this.applyTorDamage(i);
					break;
				case 'g':
					shipDamaged = this.applyGunDamage(i);
					break;
				case 'm':
					shipDamaged = this.applyMineDamage();
					break;
				default:
					throw new IllegalArgumentException ();
				}
			}
		}
		return shipDamaged;
	}

	public void rotateShip(Point position) throws IllegalArgumentException, IllegalStateException {
		Point[] currentRotateMobility = this.getRotationalMobility();
		boolean foundPoint = false;
		for (int i = 0; i < currentRotateMobility.length; i++){
			if (currentRotateMobility[i].equals(position)){
				foundPoint = true;
				break;
			}
		}
		if (!foundPoint){
			throw new IllegalArgumentException();
		}
		switch (this.direction) {
		case 'n':
			if (position.x < this.position.x){
				this.setPosition(new Point(this.position.x - (this.shipSize - 1), this.position.y + (this.shipSize - 1)));
				this.setDirection('w');
			} else {
				this.setPosition(new Point(this.position.x + (this.shipSize - 1), this.position.y + (this.shipSize - 1)));
				this.setDirection('e');
			}
			break;
		case 'e':
			if (position.y < this.position.y){
				this.setPosition(new Point(this.position.x - (this.shipSize - 1), this.position.y + (this.shipSize - 1)));
				this.setDirection('s');
			} else {
				this.setPosition(new Point(this.position.x - (this.shipSize - 1), this.position.y - (this.shipSize - 1)));
				this.setDirection('n');
			}
			break;
		case 's':
			if (position.x < this.position.x){
				this.setPosition(new Point(this.position.x - (this.shipSize - 1), this.position.y - (this.shipSize - 1)));
				this.setDirection('w');
			} else {
				this.setPosition(new Point(this.position.x + (this.shipSize - 1), this.position.y - (this.shipSize - 1)));
				this.setDirection('e');
			}
			break;
		case 'w':
			if (position.y < this.position.y){
				this.setPosition(new Point(this.position.x + (this.shipSize - 1), this.position.y + (this.shipSize - 1)));
				this.setDirection('s');
			} else {
				this.setPosition(new Point(this.position.x + (this.shipSize - 1), this.position.y - (this.shipSize - 1)));
				this.setDirection('n');
			}
			break;
		default:
			throw new IllegalStateException();
		}
	}
	
	public Point[] getRightRotateMobility() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		int offset = 0;
		switch (this.direction) {
		case 'n':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x + j, this.position.y + (i - 1)));
				}
			}
			break;
		case 'e':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - (i - 1), this.position.y + j));
				}
			}
			break;
		case 's':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x + j, this.position.y - (i - 1)));
				}
			}
			break;
		case 'w':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x + (i - 1), this.position.y + j));
				}
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
	
	public Point[] getLeftRotateMobility() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		int offset = 0;
		switch (this.direction) {
		case 'n':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - j, this.position.y + (i - 1)));
				}
			}
			break;
		case 'e':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - (i - 1), this.position.y - j));
				}
			}
			break;
		case 's':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - j, this.position.y - (i - 1)));
				}
			}
			break;
		case 'w':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x + (i - 1), this.position.y - j));
				}
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

	public Point[] getRotationalMobility() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		int offset = 0;
		switch (this.direction) {
		case 'n':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - j, this.position.y + (i - 1)));
					temp.add(new Point(this.position.x + j, this.position.y + (i - 1)));
				}
			}
			break;
		case 'e':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - (i - 1), this.position.y - j));
					temp.add(new Point(this.position.x - (i - 1), this.position.y + j));
				}
			}
			break;
		case 's':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x - j, this.position.y - (i - 1)));
					temp.add(new Point(this.position.x + j, this.position.y - (i - 1)));
				}
			}
			break;
		case 'w':
			for (int i = 1; i <= this.shipSize; i++){
				if (i < this.shipSize){
					offset = i;
				} else {
					offset = i - 1;
				}
				for (int j = 1; j <= offset; j++){
					temp.add(new Point(this.position.x + (i - 1), this.position.y - j));
					temp.add(new Point(this.position.x + (i - 1), this.position.y + j));
				}
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

	public boolean repairDamage(){
		boolean repaired = false;
		for (int i = 0; i < this.shipDamage.length; i++){
			if (this.shipDamage[i] == 0 && repaired == false){
				this.shipDamage[i] = maxHealth;
				repaired = true;
				break;
			} else if (this.shipDamage[i] == (maxHealth - 1) && repaired == false){
				this.shipDamage[i] = maxHealth;
				repaired = true;
				break;
			}
		}
		return repaired;
	}

	public void moveShip(Point location){
		Point[] rightMove = this.getRightMobility();
		Point[] leftMove = this.getLeftMobility();
		Point[] forwardMove = this.getForwardMobility();
		Point backMove = this.getBackwardMobility();

		for (int i = 0; i < rightMove.length; i++){
			if (rightMove[i].equals(location)){
				this.moveRight();
			}
		}
		for (int i = 0; i < leftMove.length; i++){
			if (leftMove[i].equals(location)){
				this.moveLeft();
			}
		}
		for (int i = 0; i < forwardMove.length; i++){
			if (forwardMove[i].equals(location)){
				this.setPosition(location);
			}
		}
		if (backMove.equals(location)){
			this.moveBack();
		}
	}

	// Takes a point as the new position, checks validity and then sets the point of the new bow
	public void setPosition(Point newPosition)throws IllegalArgumentException {
		if (newPosition.x < 0 || newPosition.y < 0 || newPosition.x > 30 || newPosition.y > 30){
			throw new IllegalArgumentException();
		} else {
			this.position = newPosition;
		}
	}

	private boolean applyTorDamage(int shipDamLoc){
		boolean shipDamaged = false;
		if (this.shipDamage[shipDamLoc] > 0){
			this.shipDamage[shipDamLoc] = this.shipDamage[shipDamLoc] - 1;
			shipDamaged = true;
		}
		if (shipDamLoc == 0){
			for (int i = 1; i < this.shipSize; i++){
				if (this.shipDamage[i] > 0){
					this.shipDamage[shipDamLoc] = this.shipDamage[shipDamLoc] - 1;
					shipDamaged = true;
					break;
				}
			}
		} else {
			for (int i = shipDamLoc; i >= 0; i--){
				if (this.shipDamage[i] > 0){
					this.shipDamage[shipDamLoc] = this.shipDamage[shipDamLoc] - 1;
					shipDamaged = true;
					break;
				}
			}
		}
		return shipDamaged;
	}

	private boolean applyGunDamage(int shipDamLoc){
		boolean shipDamaged = false;
		if (this.shipDamage[shipDamLoc] > 0){
			this.shipDamage[shipDamLoc] = this.shipDamage[shipDamLoc] - 1;
			shipDamaged = true;
		}
		return shipDamaged;
	}

	private boolean applyMineDamage() {
		boolean shipDamaged = false;
		for (int i = 0; i < this.shipDamage.length; i++){
			if (this.shipDamage[i] > 0){
				this.shipDamage[i] = this.shipDamage[i] - 1;
				shipDamaged = true;
				break;
			}
		}
		return shipDamaged;
	}

	private void moveRight() throws IllegalStateException {
		switch (this.direction){
		case 'n':
			this.setPosition(new Point(this.position.x + 1, this.position.y));
			break;
		case 'e':
			this.setPosition(new Point(this.position.x, this.position.y + 1));
			break;
		case 's':
			this.setPosition(new Point(this.position.x - 1, this.position.y));
			break;
		case 'w':
			this.setPosition(new Point(this.position.x, this.position.y - 1));
			break;
		default:
			throw new IllegalStateException();
		}
	}
	private void moveLeft() throws IllegalStateException {
		switch (this.direction){
		case 'n':
			this.setPosition(new Point(this.position.x - 1, this.position.y));
			break;
		case 'e':
			this.setPosition(new Point(this.position.x, this.position.y - 1));
			break;
		case 's':
			this.setPosition(new Point(this.position.x + 1, this.position.y));
			break;
		case 'w':
			this.setPosition(new Point(this.position.x, this.position.y + 1));
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void moveBack() throws IllegalStateException {
		switch (this.direction){
		case 'n':
			this.setPosition(new Point(this.position.x, this.position.y + 1));
			break;
		case 'e':
			this.setPosition(new Point(this.position.x - 1, this.position.y));
			break;
		case 's':
			this.setPosition(new Point(this.position.x, this.position.y - 1));
			break;
		case 'w':
			this.setPosition(new Point(this.position.x + 1, this.position.y));
			break;
		default:
			throw new IllegalStateException();
		}
	}

	public Point[] getRightMobility() throws IllegalStateException {
		ArrayList<Point> temp = new ArrayList<Point>();
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + 1, this.position.y + i));
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - i, this.position.y + 1));
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - 1, this.position.y - i));
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

	public Point[] getLeftMobility(){
		ArrayList<Point> temp = new ArrayList<Point>();
		switch (this.direction){
		case 'n':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - 1, this.position.y + i));
			}
			break;
		case 'e':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x - i, this.position.y - 1));
			}
			break;
		case 's':
			for (int i = 0; i < this.shipSize; i++){
				temp.add(new Point(this.position.x + 1, this.position.y - i));
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

	public Point[] getForwardMobility(){
		ArrayList<Point> listOfPoints = new ArrayList<Point>();
		int stillGood = 0;
		for (int i = 0; i < this.shipDamage.length; i++){
			if (this.shipDamage[i] != 0){
				stillGood += 1;
			}
		}
		switch (this.direction){
		case 'n':
			for (int i = 1; i <= stillGood * 2; i++){
				listOfPoints.add(new Point(this.position.x, this.position.y - i));
			}
			break;
		case 'e':
			for (int i = 1; i <= stillGood * 2; i++){
				listOfPoints.add(new Point(this.position.x + i, this.position.y));
			}
			break;
		case 's':
			for (int i = 1; i <= stillGood * 2; i++){
				listOfPoints.add(new Point(this.position.x , this.position.y + i));
			}
			break;
		case 'w':
			for (int i = 1; i <= stillGood * 2; i++){
				listOfPoints.add(new Point(this.position.x - i, this.position.y));
			}
			break;
		default:
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		listOfPoints.trimToSize();
		Point[] retVal = new Point[listOfPoints.size()];
		retVal = listOfPoints.toArray(retVal);
		return retVal;
	}

	public Point getBackwardMobility(){
		Point retVal = new Point();
		switch (this.direction){
		case 'n':
			retVal = new Point(this.position.x, this.position.y + this.shipSize);
			break;
		case 'e':
			retVal = new Point(this.position.x - this.shipSize, this.position.y);
			break;
		case 's':
			retVal = new Point(this.position.x, this.position.y - this.shipSize);
			break;
		case 'w':
			retVal = new Point(this.position.x + this.shipSize, this.position.y);
			break;
		default:
			throw new IllegalStateException();
		}
		return retVal;
	}


	@Override
	public String toString(){
		String returnString;
		returnString = this.shipSize + "," + this.position.x + "," + this.position.y + "," + this.direction + ",";
		returnString += this.maxHealth;
		for (int i = 0; i < this.shipDamage.length; i++){
			returnString += this.shipDamage[i] + ",";
		}
		return returnString;
	}

	public static Ship fromString(String s) {
		System.out.println("Calling fromString in Ship class, child classes should override this method.");
		System.out.println("Returning NULL.");
		return null;
	}

}

