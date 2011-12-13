

import java.awt.Point;
public class Turn {
	private Map m;
	private Player current;
	private Player opponent;

	public Turn(String fileName){
		current = new Player("Player 1", 'w');
		opponent = new Player("Player 2", 'e');
		m = new Map(fileName, current, opponent);
	}//End of Default Constructor

	//I need the direction of the bow
	public boolean rotateShip(char direction){
		return false;
	}//End of rotateShip
	
	private boolean rotateDestroyer(char direction){
		return false;
	}//End of rotateDestroyer
	
	//Takes a ship to be moved in the game and the direction to move it.  If it was able to do so, 
	//checking game rules, it moves the ship and returns true, otherwise it returns false.
	//I need the direction of the bow
	public boolean moveShip(char direction, int distance){
		if(current.getCurrentShip().getDirection()== direction) return moveForward(current.getCurrentShip(), distance);
		
		int x = current.getCurrentShip().getPosition().x;
		int y = current.getCurrentShip().getPosition().y;
		if(direction == 'n'){ 
			current.getCurrentShip().moveShip(new Point(x,y-1)); return true;
		}else if(direction == 's'){ 
			current.getCurrentShip().moveShip(new Point(x,y+1)); return true;
		}else if(direction == 'e'){
			current.getCurrentShip().moveShip(new Point(x+1,y)); return true;
		}else if(direction == 'w'){
			current.getCurrentShip().moveShip(new Point(x-1,y)); return true;
		}
		return false;
		
		/*if(currentShip.getDirection() == 'n' && direction == 's' || currentShip.getDirection() == 'w' && direction == 'e' ||	
		   currentShip.getDirection() == 's' && direction == 'n' || currentShip.getDirection() == 'e' && direction == 'w')
			return moveBack(currentShip, direction);
		
		if(currentShip.getDirection() == 'n' && direction == 'e' || currentShip.getDirection() == 'n' && direction == 'w' ||
		   currentShip.getDirection() == 's' && direction == 'e' || currentShip.getDirection() == 's' && direction == 'w' ||	
		   currentShip.getDirection() == 'e' && direction == 'n' || currentShip.getDirection() == 'e' && direction == 's' ||
		   currentShip.getDirection() == 'w' && direction == 'n' || currentShip.getDirection() == 'w' && direction == 's')
		   	return moveSide(currentShip, direction);
			
				currentShip.getLeftMobility();//Not sure how this works?  Would a boolean be better?
				currentShip.moveLeft();
			}//End of inner if	
			if(currentShip.getDirection() == 'n' && direction == 'e' || 
					currentShip.getDirection() == 'w' && direction == 'n' ||	
					currentShip.getDirection() == 's' && direction == 'w' ||
					currentShip.getDirection() == 'e' && direction == 's'){
				//Make left turn
				currentShip.getRightMobility();//Not sure how this works?  Would a boolean be better?
				currentShip.moveRight();
			}//End of inner if	
		}//End of if

		return false;*/
	}//End of moveShip Method

	private boolean moveForward(int distance){
		int x = current.getCurrentShip().getPosition().x;
		int y = current.getCurrentShip().getPosition().y;
		if(current.getCurrentShip().getDirection() == 'n')
			
		current.getCurrentShip().getPosition();
		//currentShip.moveShip();
		return false;
	}//End of moveForward
	/*
	private boolean moveBack(Ship currentShip, char direction){
		int x = currentShip.getPosition().x;
		int y = currentShip.getPosition().y;
		if(direction == 'n'){ 
			currentShip.moveShip(new Point(x,y-1)); return true;
		}else if(direction == 's'){ 
			currentShip.moveShip(new Point(x,y+1)); return true;
		}else if(direction == 'e'){
			currentShip.moveShip(new Point(x+1,y)); return true;
		}else if(direction == 'w'){
			currentShip.moveShip(new Point(x-1,y)); return true;
		}
		return false;
	}//End of moveBack
	
	private boolean moveSide(Ship currentShip, char direction){
		int x = currentShip.getPosition().x;
		int y = currentShip.getPosition().y;
		if(direction == 'n'){ 
			currentShip.moveShip(new Point(x,y-1)); return true;
		}else if(direction == 's'){ 
			currentShip.moveShip(new Point(x,y+1)); return true;
		}else if(direction == 'e'){
			currentShip.moveShip(new Point(x+1,y)); return true;
		}else if(direction == 'w'){
			currentShip.moveShip(new Point(x-1,y)); return true;
		}
		return false;
	}//End of moveSide*/
	
	public boolean immerseMine(Point loc){
		//Check for type of ship
		if(current.mineCount() <= 0 || current.getCurrentShip().toString() != "Dredger") return false;
		if(m.hasMine(loc) || m.hasBase(loc) || m.hasReef(loc) || m.hasShip(loc))  return false;
			current.decrementMineCount();
			m.placeMine(loc);
			return true;
	}//End of immerseMine Method

	public boolean withdrawMine(Point loc){
		if(current.getCurrentShip().toString() != "Dredger") return false;
		//Make sure ship is touching mine;
			m.removeMine(loc);
			current.decrementMineCount();
			return true;
	}//End of withdrawMine Method
	
	private boolean isTouching(Point loc){
		return false;
	}//End of isTouching Method

	//Takes a ship to shoot a torpedo, if able to do so via game rules it returns true, 
	//otherwise returns false.
	public boolean launchTorpedo(){
		if(current.getCurrentShip().toString() != "Destroyer" || current.getCurrentShip().toString() != "Torpedo") return false;	
		
		char d = current.getCurrentShip().getDirection();			
		int x, y;
		x = current.getCurrentShip().getPosition().x;
		y = current.getCurrentShip().getPosition().y;
		if(d == 'n') 
			for(int i = y-10; y >= i; --y){
				if(checkForHit(x,y)) return true;
			}//End of for  
		else if(d == 's') 
			for(int i = y+10; y <= i; ++y){
				if(checkForHit(x,y)) return true;
			}//End of for
		if(d == 'e')
			for(int i = x+10; x <= i; ++x){
				if(checkForHit(x,y)) return true;
			}//End of for  //End of if	
		if(d == 'w') 
			for(int i = x-10; x >= i; --x){
				if(checkForHit(x,y)) return true;
			}//End of for  //End of if
		return false;
	}//End of launchTorpedo Method
	
	private boolean checkForHit(int x, int y){
		if(m.hasBase(new Point(x,y))){
			Base b = (Base)m.getTile(new Point(x,y)).getTileOwner();
			b.receiveDamage(new Point(x,y),'t');
			return true;
		}else if(m.hasShip(new Point(x,y))){
			Ship s = (Ship)m.getTile(new Point(x,y)).getTileOwner();
			s.receiveDamage(new Point(x,y),'t');
			return true;
		}else if(m.hasMine(new Point(x,y))){
			m.removeMine(new Point(x,y));
			return true;
		}else if(m.hasReef(new Point(x,y))) return true;
		else return false;
	}//End of checkForHit method

	public boolean shootGun(Point target){
		if(current.getCurrentShip().toString() != "Cruiser" || current.getCurrentShip().toString() != "Torpedo") return false;
		Point[] p = current.getCurrentShip().getGunRange();
		for(int i = 0; i < p.length; ++i){
			if(p[i].equals(target)){
				if(m.hasBase(target)){
					Base b = (Base) m.getTile(target).getTileOwner();
					b.receiveDamage(target, 'g');
					return true;
				}else if(m.hasShip(target)){
					Ship s = (Ship) m.getTile(target).getTileOwner();
					s.receiveDamage(target, 'g');
					return true;
				}else if(m.hasMine(target)){
					m.removeMine(target);}
				return true;
			}//End of if
		}//End of for
		return false;
	}//End of shootGun Method

	//Takes a ship to be repaired and if allowed it repairs the ship and returns true, 
	//otherwise it returns false.
	public boolean repairShip(){
		return current.base().repairShip(current.getCurrentShip());		
	}//End of repairShip Method

	//If allowed it repairs players base and returns true, otherwise returns false.
	public boolean repairBase(){
		return current.base().repairBase();	
	}//End of repairBase Method
	
	public int getMineCount(){
		return current.mineCount();
	}//End of getMineCount()
	
	public String toString(){
		return current.toString() + opponent.toString() + m.toString(); }
}//End of Class

