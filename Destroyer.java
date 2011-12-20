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
import java.util.ArrayList;

public class Destroyer extends Ship{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		this.setPosition(position);
		
	}

	public Point[] checkTurnMobility() throws IllegalStateException {
		ArrayList<Point> mobilityPoints = new ArrayList<Point>();
		Point[] shipCoordinates = this.getShipCoordinates();
		switch (this.getDirection()) {
		case 'n':
			for (int i = 0; i <= shipCoordinates.length; i++){
				mobilityPoints.add(new Point(shipCoordinates[i].x - 1, shipCoordinates[i].y));
				mobilityPoints.add(new Point(shipCoordinates[i].x + 1, shipCoordinates[i].y));
			}
			break;
		case 'e':
			for (int i = 1; i <= this.getSize(); i++){
				mobilityPoints.add(new Point(shipCoordinates[i].x, shipCoordinates[i].y - 1));
				mobilityPoints.add(new Point(shipCoordinates[i].x, shipCoordinates[i].y + 1));
			}
			break;
		case 's':
			for (int i = 1; i <= this.getSize(); i++){
				mobilityPoints.add(new Point(shipCoordinates[i].x - 1, shipCoordinates[i].y));
				mobilityPoints.add(new Point(shipCoordinates[i].x + 1, shipCoordinates[i].y));
			}
			break;
		case 'w':
			for (int i = 1; i <= this.getSize(); i++){
				mobilityPoints.add(new Point(shipCoordinates[i].x, shipCoordinates[i].y - 1));
				mobilityPoints.add(new Point(shipCoordinates[i].x, shipCoordinates[i].y + 1));
			}
			break;
		default:		
			throw new IllegalStateException();
		}

		// Clean up array list, change into regular array and then return
		mobilityPoints.trimToSize();
		Point[] returnValue = new Point[mobilityPoints.size()];
		returnValue = mobilityPoints.toArray(returnValue);
		return returnValue;
	}

	public void turnShip() throws IllegalStateException {
		Point[] shipCoordinates = this.getShipCoordinates();
		switch (this.getDirection()){
		case 'n':
			this.setDirection('s');
			this.setPosition(shipCoordinates[this.getSize() - 1]);
			break;
		case 'e':
			this.setDirection('w');
			this.setPosition(shipCoordinates[this.getSize() - 1]);
			break;
		case 's':
			this.setDirection('n');
			this.setPosition(shipCoordinates[this.getSize() - 1]);
			break;
		case 'w':
			this.setDirection('e');
			this.setPosition(shipCoordinates[this.getSize() - 1]);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public Point[] getRotationalMobility(){
		Point[] leftMobility = this.getLeftRotateMobility();
		Point[] rightMobility = this.getRightRotateMobility();
		ArrayList<Point> mobilityPoints = new ArrayList<Point>();
		
		for (int i = 0; i < leftMobility.length; i++){
			mobilityPoints.add(leftMobility[i]);
		}
		for (int i = 0; i < rightMobility.length; i++) {
			mobilityPoints.add(rightMobility[i]);
		}
		
		// Clean up array list, change into regular array and then return
				mobilityPoints.trimToSize();
				Point[] returnValue = new Point[mobilityPoints.size()];
				returnValue = mobilityPoints.toArray(returnValue);
				return returnValue;
	}

	@Override
	public void rotateShip(Point position) {
		System.out.println("DO NOT call rotateShip on a Destroyer, please call rotateShipLeft or rotateShipRight.");
	}
	
	public void rotateShipLeft(Point position) throws IllegalStateException {
		Point[] sc = this.getShipCoordinates();
		switch (this.getDirection()){
		case 'n':
			this.setDirection('w');
			this.setPosition(new Point(sc[1].x - 1, sc[1].y));
			break;
		case 'e':
			this.setDirection('n');
			this.setPosition(new Point(sc[1].x, sc[1].y - 1));
			break;
		case 's':
			this.setDirection('e');
			this.setPosition(new Point(sc[1].x + 1, sc[1].y));
			break;
		case 'w':
			this.setDirection('s');
			this.setPosition(new Point(sc[1].x, sc[1].y + 1));
			break;
		default:
			throw new IllegalStateException();
		}
	}
	
	public void rotateShipRight(Point position) throws IllegalStateException {
		Point[] sc = this.getShipCoordinates();
		switch (this.getDirection()){
		case 'n':
			this.setDirection('e');
			this.setPosition(new Point(sc[1].x + 1, sc[1].y));
			break;
		case 'e':
			this.setDirection('s');
			this.setPosition(new Point(sc[1].x, sc[1].y + 1));
			break;
		case 's':
			this.setDirection('w');
			this.setPosition(new Point(sc[1].x - 1, sc[1].y));
			break;
		case 'w':
			this.setDirection('n');
			this.setPosition(new Point(sc[1].x, sc[1].y - 1));
			break;
		default:
			throw new IllegalStateException();
		}
	}
	
	@Override
	public Point[] getRightRotateMobility() throws IllegalStateException {
		ArrayList<Point> mobilityPoints = new ArrayList<Point>();
		Point[] shipCoordinates = this.getShipCoordinates();
		switch (this.getDirection()){
		case 'n':
			mobilityPoints.add(new Point(shipCoordinates[0].x + 1, shipCoordinates[0].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x + 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x - 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[2].x - 1, shipCoordinates[2].y));
			break;
		case 'e':
			mobilityPoints.add(new Point(shipCoordinates[0].x, shipCoordinates[0].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[2].x, shipCoordinates[2].y - 1));
			break;
		case 's':
			mobilityPoints.add(new Point(shipCoordinates[0].x - 1, shipCoordinates[0].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x - 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x + 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[2].x + 1, shipCoordinates[2].y));
			break;
		case 'w':
			mobilityPoints.add(new Point(shipCoordinates[0].x, shipCoordinates[0].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[2].x, shipCoordinates[2].y + 1));
			break;
		default:
			throw new IllegalStateException();
		}
		// Clean up array list, change into regular array and then return
		mobilityPoints.trimToSize();
		Point[] returnValue = new Point[mobilityPoints.size()];
		returnValue = mobilityPoints.toArray(returnValue);
		return returnValue;
	}
	
	@Override
	public Point[] getLeftRotateMobility() {
		ArrayList<Point> mobilityPoints = new ArrayList<Point>();
		Point[] shipCoordinates = this.getShipCoordinates();
		switch (this.getDirection()){
		case 'n':
			mobilityPoints.add(new Point(shipCoordinates[0].x - 1, shipCoordinates[0].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x - 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x + 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[2].x + 1, shipCoordinates[2].y));
			break;
		case 'e':
			mobilityPoints.add(new Point(shipCoordinates[0].x, shipCoordinates[0].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[2].x, shipCoordinates[2].y + 1));
			break;
		case 's':
			mobilityPoints.add(new Point(shipCoordinates[0].x + 1, shipCoordinates[0].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x + 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[1].x - 1, shipCoordinates[1].y));
			mobilityPoints.add(new Point(shipCoordinates[2].x - 1, shipCoordinates[2].y));
			break;
		case 'w':
			mobilityPoints.add(new Point(shipCoordinates[0].x, shipCoordinates[0].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y + 1));
			mobilityPoints.add(new Point(shipCoordinates[1].x, shipCoordinates[1].y - 1));
			mobilityPoints.add(new Point(shipCoordinates[2].x, shipCoordinates[2].y - 1));
			break;
		default:
			throw new IllegalStateException();
		}
		// Clean up array list, change into regular array and then return
		mobilityPoints.trimToSize();
		Point[] returnValue = new Point[mobilityPoints.size()];
		returnValue = mobilityPoints.toArray(returnValue);
		return returnValue;
	}

}
