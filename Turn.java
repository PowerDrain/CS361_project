
import java.awt.Point;
public class Turn {
	private Map currentMap;
	private Player currentPlayer;
	private Player opponent;
	private int turnNumber;

	public Turn(String fileName){
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
					returnValue[1] = "Cannot rotate ship to point " + pointToRotate + " there is something in the way.";
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
			returnValue[1] = "Point passed is not in the rotational mobility of this ship.";
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
			}
			shipCanMove = true;
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
					returnValue[1] = "Cannot move ship left to point " + pointToMove + " something in the way.";
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
					returnValue[1] = "Cannot move ship right to point " + pointToMove + " something in the way.";
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
						returnValue[1] = "Object directly in front of ship, it cannot move.";
						return returnValue;
					} else {
						return actuallyMoveShip(frontOfShip[i - 1]);
					}
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
			returnValue[1] = "Current ship is not a dredger, only dredgers can drop mines.";
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
			returnValue[1] = "Cannot drop a mine in location that is not adjacent to a dredger.";
			return returnValue;
		}
		if (currentMap.hasBase(dropMineLocation) || currentMap.hasReef(dropMineLocation) || currentMap.hasShip(dropMineLocation) || currentMap.hasMine(dropMineLocation)){
			returnValue[0] = null;
			returnValue[1] = "Cannot drop mine at point " + dropMineLocation + " there is something in the way.";
			return returnValue;
		}
		Ship[] allCurrentPlayerShips = currentPlayer.getPlayerShips();
		for (int i = 0; i < allCurrentPlayerShips.length; i++){
			Point[] pointsAroundShip = allCurrentPlayerShips[i].getAdjacentPoints();
			for (int j = 0; j < pointsAroundShip.length; j++){
				if (dropMineLocation.equals(pointsAroundShip[i])){
					returnValue[0] = null;
					returnValue[1] = "Cannot drop a mine right next to a ship, this would cause an instantaneous explosion!";
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
					returnValue[1] = "Cannot drop a mine right next to a ship, this would cause an instantaneous explosion!";
					return returnValue;
				}
			}
		}
		Point[] pointsAroundBase = currentPlayer.base().getAdjacentPoints();
		for (int i = 0; i < pointsAroundBase.length; i++){
			if (dropMineLocation.equals(pointsAroundBase[i])){
				returnValue[0] = null;
				returnValue[1] = "Cannot drop a mine right next to a base, this would cause an instantaneous explosion!";
				return returnValue;
			}
		}
		pointsAroundBase = opponent.base().getAdjacentPoints();
		for (int i = 0; i < pointsAroundBase.length; i++){
			if (dropMineLocation.equals(pointsAroundBase[i])){
				returnValue[0] = null;
				returnValue[1] = "Cannot drop a mine right next to a base, this would cause an instantaneous explosion!";
				return returnValue;
			}
		}

		if (currentPlayer.decrementMineCount()){
			currentMap.placeMine(dropMineLocation);
			returnValue[0] = "0";
			returnValue[1] = "Mine successfully drop at location " + dropMineLocation + ".";
		} else {
			returnValue[0] = null;
			returnValue[1] = "You can't drop mines joker, you're out of mines!";
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
				returnValue[1] = "Current ship is not a dredger, only dredgers can pickup mines.";
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
				returnValue[1] = "Cannot pick up a mine in location that is not adjacent to a dredger.";
				return returnValue;
			} else {
				currentPlayer.incrementMineCount();
				currentMap.removeMine(pickupMineLocation);
				returnValue[0] = "0";
				returnValue[1] = "Mine successfully removed from point " + pickupMineLocation + ".";
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Cannot pickup mine, no mine at location " + pickupMineLocation + ".";
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
						returnValue[1] = "Torpedo has hit a target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired but has missed target.";
					}
					yCoor -= 1;
				}
				break;
			case 'e':
				for (int i = 1; (i <= 10)&&(xCoor <= 30); i++){
					if (torpedoHit(xCoor + i, yCoor)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo has hit a target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired but has missed target.";
					}
					xCoor += 1;
				}
				break;
			case 's':
				for (int i = 1; (i <= 10)&&(yCoor < 30); i++){
					if (torpedoHit(xCoor, yCoor + i)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo has hit a target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired but has missed target.";
					}
					yCoor += 1;
				}
				break;
			case 'w':
				for (int i = 1; (i <= 10)&&(xCoor >= 0); i++){
					if (torpedoHit(xCoor - i, yCoor)){
						returnValue[0] = "0";
						returnValue[1] = "Torpedo has hit a target!";
						turnNumber+=1;
						break;
					} else {
						returnValue[0] = "0";
						returnValue[1] = "Torpedo fired but has missed target.";
					}
					xCoor -= 1;
				}
				break;
			default:
				throw new IllegalStateException();
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Current ship cannot shoot a torpedo.";
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
	public boolean shootGun(Point target){
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
		return false;
	}//End of shootGun Method

	public String[] repairShip(){
		String[] returnValue = new String[2];
		returnValue[0] = null;
		returnValue[0] = null;
		if (currentPlayer.getCurrentShip().aFloat()){
			if (currentPlayer.isDocked(currentPlayer.getCurrentShip())){
				if (currentPlayer.getCurrentShip().repairDamage()){
					returnValue[0] = "0";
					returnValue[1] = "Current ship repaired!";
					turnNumber+=1;
				} else {
					returnValue[0] = null;
					returnValue[1] = "Current ship is at full health, cannot repair it.";
				}

			} else {
				returnValue[0] = null;
				returnValue[1] = "Current ship is not docked at a base, cannot repair it.";
			}
		} else {
			returnValue[0] = null;
			returnValue[1] = "Current ship is destroyed, cannot repair it.";
		}
		return returnValue;
	}

	public int getTurnNuber(){
		return turnNumber;
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
			returnValue[1] = "Base is at full health, cannot repair.";
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
		returnValue[0] = "Passing turn to opponent.";
		turnNumber +=1;
		return returnValue;
	}
	public String toString(){
		return currentPlayer.toString() + opponent.toString() + currentMap.toString(); }
}//End of Class

