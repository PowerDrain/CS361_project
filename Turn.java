

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
	public boolean rotateShip(Ship currentShip, char direction){
		return false;
	}//End of rotateShip
	
	private boolean rotateDestroyer(Ship currentShip, char direction){
		return false;
	}//End of rotateDestroyer
	
	//Takes a ship to be moved in the game and the direction to move it.  If it was able to do so, 
	//checking game rules, it moves the ship and returns true, otherwise it returns false.
	//I need the direction of the bow
	public boolean moveShip(Ship currentShip, char direction, int distance){//TODO I need to add distance -Al
		if(currentShip.getDirection() != direction){	
			//Change direction TODO  Should I use getPosition()?
			if(currentShip.getDirection() == 'n' && direction == 'w' || 
					currentShip.getDirection() == 'w' && direction == 's' ||	
					currentShip.getDirection() == 's' && direction == 'e' ||
					currentShip.getDirection() == 'e' && direction == 'n'){
				//Make left turn
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
		//How to move forward?
		//Have not set it to move 180
		return false;
	}//End of moveShip Method

	private boolean moveForward(Ship currentShip, int distance){
		return false;
	}//End of moveForward
	
	private boolean moveBack(Ship currentShip, char direction){
		return false;
	}//End of moveBack
	
	private boolean moveSide(Ship currentShip, char direction){
		return false;
	}//End of moveSide
	
	//Takes the location to drop a mine. Immerses a mine at the given location and decrements 
	//users mine count. If move is legal and there are mines available it returns true, 
	//otherwise returns false.
	public boolean immerseMine(Point loc, Ship currentShip){
		//Check for type of ship
		if(current.mineCount() <= 0 || currentShip.toString() != "Dredger") return false;
		if(m.hasMine(loc) || m.hasBase(loc) || m.hasReef(loc) || m.hasShip(loc))  return false;
			current.decrementMineCount();
			m.placeMine(loc);
			return true;
	}//End of immerseMine Method

	//Takes the location of the mine. Withdraw mine from the map and increments users mine count. 
	//If the move is legal and completes successfully it returns true, otherwise returns false. 
	public boolean withdrawMine(Point loc, Ship currentShip){
		//Check for type of ship
		//TODO maybe there is a more elegant solution to this,
		//this just doesn't seem good but it may work. -Tommy
		//if(current.getCurrentShip().getClass().getName()=="Dredger"){
		if(currentShip.toString() != "Dredger") return false;
			m.removeMine(loc);
			current.decrementMineCount();
			return true;
	}//End of withdrawMine Method
	
	private boolean isTouching(Point loc, Ship currentShip){
		return false;
	}//End of isTouching Method

	//Takes a ship to shoot a torpedo, if able to do so via game rules it returns true, 
	//otherwise returns false.
	public boolean launchTorpedo(Ship currentShip){
		if(currentShip.toString() != "Destroyer" || currentShip.toString() != "Torpedo") return false;	
		
		char d = currentShip.getDirection();			
		int x, y;
		x = currentShip.getPosition().x;
		y = currentShip.getPosition().y;
		//if d equals n decrement y
		if(d == 'n') 
			for(int i = y-10; y >= i; --y){
				if(checkForHit(x,y)) return true;
			}//End of for  
		//if d equals s increment y
		if(d == 's') 
			for(int i = y+10; y <= i; ++y){
				if(checkForHit(x,y)) return true;
			}//End of for
		//if d equals e increment x
		if(d == 'e')
			for(int i = x+10; x <= i; ++x){
				if(checkForHit(x,y)) return true;
			}//End of for  //End of if	
		//if d equals w decrement x	
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

	//Takes a ship to shoot the gun and the location of where the shot should go.  If all game rules 
	//followed it returns true, otherwise returns false.
	public boolean shootGun(Ship currentShip, Point target){
		if(currentShip.toString() != "Cruiser" || currentShip.toString() != "Torpedo") return false;
		Point[] p = currentShip.getGunRange();
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
	public boolean repairShip(Ship currentShip){
		return current.base().repairShip(currentShip);		
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

