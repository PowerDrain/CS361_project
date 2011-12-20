
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
public class Turn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map currentMap;
	private Player currentPlayer;
	private Player opponent;
	private int turnNumber;
	private String layoutFile;

	public Turn(String fileName){
		layoutFile = fileName;
		currentPlayer = new Player("Player 1", 'w');
		opponent = new Player("Player 2", 'e');
		currentMap = new Map(fileName, currentPlayer, opponent);
		turnNumber = 0;
	}//End of Default Constructor


	public String[] rotateShip(Point pointToRotate){
		//TODO break this into two parts, check right and check left, depending on input
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[1] = null;
		boolean shipCanRotate = false;
		Point[] possrp = currentPlayer.getCurrentShip().getRotationalMobility();

		for (int i = 0; i < possrp.length; i++){
			if (pointToRotate.equals(possrp[i])){
				shipCanRotate = true;
				break;
			} else {
				shipCanRotate = false;
			}
		}
		if (shipCanRotate){
			for (int i = 0; i < possrp.length; i++){
				if (currentMap.hasBase(possrp[i]) || currentMap.hasReef(possrp[i]) || currentMap.hasShip(possrp[i])) {
					returnValue[0] = null;
					returnValue[1] = "Cannot rotate ship to point " + pointToRotate + "\nThere is something in the way.";
					return returnValue;
				} else if (currentMap.hasMine(possrp[i])){
					returnValue[0] = null;
					returnValue[1] = "Rotating ship into mine at point" + pointToRotate + " implemnt this.";
					return returnValue;
				}
			}
			currentPlayer.getCurrentShip().rotateShip(pointToRotate);
			returnValue[0] = "0";
			returnValue[1] = "Ship rotated to point " + pointToRotate + ".";
			turnNumber+=1;
			return returnValue;
		} else {
			returnValue[0] = null;
			returnValue[1] = "Cannon move to point...\nOut of range.";
			return returnValue;
		}	
	}//End of rotateShip

	public String[] moveShip(Point pointToMove){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[1] = null;
		boolean shipCanMove = false;
		boolean pointInMobility = false;
		Point[] possibleMovePoints = currentPlayer.getCurrentShip().getMoveMobility();

		//Test to see if the point is a legal move
		for (int i = 0; i <possibleMovePoints.length; i++) {
			if (pointToMove.equals(possibleMovePoints[i])){
				pointInMobility = true;
				break;
			}
		}
		if (!pointInMobility){
			returnValue[0] = null;
			returnValue[1] = "This ship is not able to move to point " + pointToMove + ".";
			return returnValue;
		}

		//Testing to see if anything might be in the way
		for (int i = 0; i < possibleMovePoints.length; i++) {
			if (currentMap.hasBase(possibleMovePoints[i]) || currentMap.hasMine(possibleMovePoints[i]) || currentMap.hasReef(possibleMovePoints[i]) || currentMap.hasShip(possibleMovePoints[i])){
				shipCanMove = false;
				break;
			} else {
				shipCanMove = true;
			}
		}
		if (shipCanMove){
			return actuallyMoveShip(pointToMove);
		}
		//Stuff in the way, need to test if it's in the direct path
		//Test for backward mobility
		Point behindShip = currentPlayer.getCurrentShip().getBackwardMobility();
		if (pointToMove.equals(behindShip)){
			if(currentMap.hasBase(behindShip) || currentMap.hasMine(behindShip) || currentMap.hasReef(behindShip) || currentMap.hasShip(behindShip)){
				shipCanMove = false;
			} else {
				shipCanMove = true;
			}
			if (shipCanMove){
				return actuallyMoveShip(pointToMove);
			}
		}

		//Test for left mobility
		Point[] leftOfShip = currentPlayer.getCurrentShip().getLeftMobility();
		boolean moveLeft = false;
		for (int i = 0; i < leftOfShip.length; i++){
			if (pointToMove.equals(leftOfShip[i])){
				moveLeft = true;
				break;
			} else {
				moveLeft = false;
			}
		}
		if (moveLeft){
			for (int i = 0; i < leftOfShip.length; i++){
				if (currentMap.hasBase(leftOfShip[i]) || currentMap.hasReef(leftOfShip[i]) || currentMap.hasShip(leftOfShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Cannot move ship to the left.\nSomething in the way.";
					return returnValue;
				} else if (currentMap.hasMine(leftOfShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Moving into mine, need to figure out how to implement this.";
					return returnValue;
				}
			}
			return actuallyMoveShip(pointToMove);
		}
		//Test for right mobility
		Point[] rightOfShip = currentPlayer.getCurrentShip().getRightMobility();
		boolean moveRight = false;
		for (int i = 0; i < rightOfShip.length; i++){
			if (pointToMove.equals(rightOfShip[i])){
				moveRight = true;
				break;
			} else {
				moveRight = false;
			}
		}
		if (moveRight){
			for (int i = 0; i < rightOfShip.length; i++){
				if (currentMap.hasBase(rightOfShip[i]) || currentMap.hasReef(rightOfShip[i]) || currentMap.hasShip(rightOfShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Cannot move ship to the right.\nSomething in the way.";
					return returnValue;
				} else if (currentMap.hasMine(rightOfShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Moving into mine, need to figure out how to implement this.";
					return returnValue;
				}
			}
			return actuallyMoveShip(pointToMove);
		}
		//Test for forward mobility
		Point[] frontOfShip = currentPlayer.getCurrentShip().getForwardMobility();
		boolean moveForward = false;
		for (int i = 0; i < frontOfShip.length; i++){
			if (pointToMove.equals(frontOfShip[i])){
				moveForward = true;
				break;
			} else {
				moveForward = false;
			}
		}
		if (moveForward){
			for (int i = 0; i < frontOfShip.length; i++){
				if (currentMap.hasBase(frontOfShip[i]) || currentMap.hasReef(frontOfShip[i]) || currentMap.hasShip(frontOfShip[i])){
					if (i == 0){
						returnValue[0] = null;
						returnValue[1] = "Object directly in front of ship.\nShip cannot move.";
						return returnValue;
					} else if(frontOfShip[i].equals(pointToMove)){
						return actuallyMoveShip(frontOfShip[i - 1]);
					}
				} else if (frontOfShip[i].equals(pointToMove)){
					return actuallyMoveShip(pointToMove);
				}
			}
		}
		returnValue[0] = null;
		returnValue[1] = "Shouldn't ever see this in console, if you do something is wrong with moveShip in Turn class.";

		return returnValue;
	}//End of moveShip

	private String[] actuallyMoveShip(Point pointToMove){
		String[] returnValue = new String[2];
		currentPlayer.getCurrentShip().moveShip(pointToMove);
		returnValue[0] = "0";
		returnValue[1] = "Ship has been moved to point " + pointToMove + ".";
		turnNumber += 1;
		return returnValue;
	}

	public String[] immerseMine(Point dropMineLocation){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[1] = null;
		boolean canDropMine = false;
		if (!(currentPlayer.getCurrentShip() instanceof Dredger)){
			returnValue[0] = null;
			returnValue[1] = "Current ship is not a dredger...\nOnly dredgers can drop mines.";
			return returnValue;
		}
		Point[] legalDropSpots = currentPlayer.getCurrentShip().getAdjacentPoints();
		for (int i = 0; i <legalDropSpots.length; i++){
			if (dropMineLocation.equals(legalDropSpots[i])){
				canDropMine = true;
				break;
			}
		}
		if (!canDropMine){
			returnValue[0] = null;
			returnValue[1] = "Cannot drop mine...\nLocation is not adjacent dredger.";
			return returnValue;
		}
		if (currentMap.hasBase(dropMineLocation) || currentMap.hasReef(dropMineLocation) || currentMap.hasShip(dropMineLocation) || currentMap.hasMine(dropMineLocation)){
			returnValue[0] = null;
			returnValue[1] = "Cannot drop mine...\n" + dropMineLocation + "is occupied.";
			return returnValue;
		}
		Ship[] allCurrentPlayerShips = currentPlayer.getPlayerShips();
		for (int i = 0; i < allCurrentPlayerShips.length; i++){
			Point[] pointsAroundShip = allCurrentPlayerShips[i].getAdjacentPoints();
			for (int j = 0; j < pointsAroundShip.length; j++){
				if (dropMineLocation.equals(pointsAroundShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Cannot drop mine next to a ship\nWould cause an instantaneous explosion!";
					return returnValue;
				}
			}
		}

		Ship[] allOpponentShips = opponent.getPlayerShips();
		for (int i = 0; i < allOpponentShips.length; i++){
			Point[] pointsAroundShip = allOpponentShips[i].getAdjacentPoints();
			for (int j = 0; j < pointsAroundShip.length; j++){
				if (dropMineLocation.equals(pointsAroundShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Cannot drop mine next to a ship\nWould cause an instantaneous explosion!";
					return returnValue;
				}
			}
		}
		Point[] pointsAroundBase = currentPlayer.base().getAdjacentPoints();
		for (int i = 0; i < pointsAroundBase.length; i++){
			if (dropMineLocation.equals(pointsAroundBase[i])){
				returnValue[0] = null;
				returnValue[1] = "Cannot drop mine next to base\nWould cause instantaneous explosion!";
				return returnValue;
			}
		}
		pointsAroundBase = opponent.base().getAdjacentPoints();
		for (int i = 0; i < pointsAroundBase.length; i++){
			if (dropMineLocation.equals(pointsAroundBase[i])){
				returnValue[0] = null;
				returnValue[1] = "Cannot drop mine next to base\nWould cause instantaneous explosion!";
				return returnValue;
			}
		}

		if (currentPlayer.decrementMineCount()){
			currentMap.placeMine(dropMineLocation);
			returnValue[0] = "0";
			returnValue[1] = "Mine successfully droped!\nMine now at " + dropMineLocation + ".";
		} else {
			returnValue[0] = null;
			returnValue[1] = "You can't drop mines joker\nYou're out of mines!";
		}
		return returnValue;
	}

	public String[] withdrawMine(Point pickupMineLocation){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[1] = null;
		boolean canPickupMine = false;
		if (currentMap.hasMine(pickupMineLocation)){
			if (!(currentPlayer.getCurrentShip() instanceof Dredger)){
				returnValue[0] = null;
				returnValue[1] = "Ship is not a dredger...\nOnly dredgers can pickup mines.";
				return returnValue;
			}
			Point[] legalPickupSpots = currentPlayer.getCurrentShip().getAdjacentPoints();
			for (int i = 0; i <legalPickupSpots.length; i++){
				if (pickupMineLocation.equals(legalPickupSpots[i])){
					canPickupMine = true;
					break;
				}
			}
			if (!canPickupMine){
				returnValue[0] = null;
				returnValue[1] = "Cannot pick up a mine...\nLocation is not adjacent to dredger.";
				return returnValue;
			} else {
				currentPlayer.incrementMineCount();
				currentMap.removeMine(pickupMineLocation);
				returnValue[0] = "0";
				returnValue[1] = "Mine successfully removed!\nNo longer mine at " + pickupMineLocation + ".";
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Cannot pickup mine...\nNo mine at location " + pickupMineLocation + ".";
		}
		return returnValue;
	}

	public String[] launchTorpedo() throws IllegalStateException {
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[1] = null;

		if (currentPlayer.getCurrentShip() instanceof Destroyer || currentPlayer.getCurrentShip() instanceof Torpedo){
			int xCoor = currentPlayer.getCurrentShip().getPosition().x;
			int yCoor = currentPlayer.getCurrentShip().getPosition().y;

			switch (currentPlayer.getCurrentShip().getDirection()){
			case 'n':
				for (int i = 1; (i <= 10)&&(yCoor >= 0); i++){
					if (torpedoHit(xCoor, yCoor - i)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo hit target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo missed target.";
					}
					yCoor -= 1;
				}
				break;
			case 'e':
				for (int i = 1; (i <= 10)&&(xCoor <= 30); i++){
					if (torpedoHit(xCoor + i, yCoor)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo hit target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo missed target.";
					}
					xCoor += 1;
				}
				break;
			case 's':
				for (int i = 1; (i <= 10)&&(yCoor < 30); i++){
					if (torpedoHit(xCoor, yCoor + i)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo hit target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo missed target.";
					}
					yCoor += 1;
				}
				break;
			case 'w':
				for (int i = 1; (i <= 10)&&(xCoor >= 0); i++){
					if (torpedoHit(xCoor - i, yCoor)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo hit target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired...\nTorpedo missed target.";
					}
					xCoor -= 1;
				}
				break;
			default:
				throw new IllegalStateException();
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Ship cannot shoot torpedo...\nNot equiped with torpedos.";
			return returnValue;
		}
		return returnValue;
	}

	private boolean torpedoHit(int xCoor, int yCoor) {
		Point checkPoint = new Point(xCoor, yCoor);
		if (currentMap.hasBase(checkPoint)){
			Base baseHit = (Base)currentMap.getTile(checkPoint).getTileOwner();
			baseHit.receiveDamage(checkPoint, 't');
			return true;
		} else if(currentMap.hasReef(checkPoint)){
			return true;
		} else if (currentMap.hasShip(checkPoint)){
			Ship shipHit = (Ship)currentMap.getTile(checkPoint).getTileOwner();
			shipHit.receiveDamage(checkPoint, 't');
			return true;
		} else if (currentMap.hasMine(checkPoint)){
			currentMap.removeMine(checkPoint);
			return true;
		} else {
			return false;
		}
	}

	//TODO 
	/*fix this method. . . no longer can use toString, must use
	 * instance of and this method must check to see if the 
	 * target point is within the gun range before applying
	 * damage.  Also correct return type to return what other 
	 * methods return.
	 */
	public String[] shootGun(Point target){
		String[] returnValue = new String[2];
		Point[] placesToShoot = currentPlayer.getCurrentShip().getGunRange();
		boolean legalTarget = false;
		for (int i = 0; i < placesToShoot.length; i++){
			if (placesToShoot[i].equals(target)){
				legalTarget = true;
				break;
			}
		}
		if (legalTarget){
			if (currentMap.getTile(target).getTileOwner() instanceof Base){
				Base attackedBase = (Base)currentMap.getTile(target).getTileOwner();
				attackedBase.receiveDamage(target, 'g');
				returnValue[0] = "0";
				returnValue[1] = "Gun fired\nHit Base!";
			} else if (currentMap.getTile(target).getTileOwner() instanceof Ship){
				Ship attackedShip = (Ship)currentMap.getTile(target).getTileOwner();
				attackedShip.receiveDamage(target, 'g');
				returnValue[0] = "0";
				returnValue[1] = "Gun fired\nHit Ship!";
			} else {
				returnValue[0] = "0";
				returnValue[1] = "Gun fired\nMissed Base and Ship.";
			}
			return returnValue;
		} else {
			returnValue[0] = null;
			returnValue[1] = "Can't shoot there\nNo within range.";
		}

		return returnValue;
		/* Original code
		if(currentPlayer.getCurrentShip().toString() != "Cruiser" || currentPlayer.getCurrentShip().toString() != "Torpedo") return false;
		Point[] p = currentPlayer.getCurrentShip().getGunRange();
		for(int i = 0; i < p.length; ++i){
			if(p[i].equals(target)){
				if(currentMap.hasBase(target)){
					Base b = (Base) currentMap.getTile(target).getTileOwner();
					b.receiveDamage(target, 'g');
					return true;
				}else if(currentMap.hasShip(target)){
					Ship s = (Ship) currentMap.getTile(target).getTileOwner();
					s.receiveDamage(target, 'g');
					return true;
				}else if(currentMap.hasMine(target)){
					currentMap.removeMine(target);
					return true;}
			}//End of if
		}//End of for
		return false;*/
	}//End of shootGun Method

	public String[] repairShip(){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[0] = null;
		if (currentPlayer.getCurrentShip().aFloat()){
			if (currentPlayer.isDocked(currentPlayer.getCurrentShip())){
				if (currentPlayer.getCurrentShip().repairDamage()){
					returnValue[0] = "0";
					returnValue[1] = "Ship has been repaired!";
					turnNumber+=1;
				} else {
					returnValue[0] = null;
					returnValue[1] = "Ship is at full health\nCannot repair ship.";
				}

			} else {
				returnValue[0] = null;
				returnValue[1] = "Ship is not docked at base...\nCannot repair ship.";
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Ship is fully destroyed...\nCannot repair ship.";
		}
		return returnValue;
	}

	public int getTurnNumber(){
		return turnNumber;
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public Player getOpponent(){
		return opponent;
	}
	
	public String[] repairBase(){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[0] = null;
		if (currentPlayer.base().repairBase()){
			returnValue[0] = "0";
			returnValue[1] = "Base has been repaired.";
			turnNumber+=1;
		} else {
			returnValue[0] = null;
			returnValue[1] = "Base is at full health...\nCannot repair base.";
		}
		return returnValue;
	}//End of repairBase Method

	public int getMineCount(){
		return currentPlayer.mineCount();
	}//End of getMineCount()

	public Map getMap(){
		return currentMap;
	}//End of getMap

	public String[] passTurn(){
		String[] returnValue = new String[2];
		returnValue[0] = "0";
		returnValue[1] = "Passing turn to opponent.";
		turnNumber +=1;
		return returnValue;
	}
	public String toString(){
		return currentPlayer.toString() + opponent.toString() + currentMap.toString(); }
	
	public void serialize(String filename){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try{
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static Turn unserialize(String filename){
		Turn newTurn = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try{
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			newTurn = (Turn)in.readObject();
			in.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return newTurn;
	}
	
	public void updateMap(){
		currentMap.updateMap(layoutFile);
	}
}//End of Class

