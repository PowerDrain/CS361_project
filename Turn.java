
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

	public boolean rotateShip(Point p){
		current.getCurrentShip().rotateShip(p);
		return true;
	}//End of rotateShip
	
	public boolean moveShip(Point p){
		//I need to add Stuff that detects obj on map.  I just want to test if it will move first
		current.getCurrentShip().moveShip(p);
		return true;
	}//End of moveShip
	
/*
	public boolean moveShip(char direction, int distance){
		if(current.getCurrentShip().getDirection()== direction) return moveForward(distance);

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
	}//End of moveShip Method

	private boolean moveForward(int distance){
		int x = current.getCurrentShip().getPosition().x;
		int y = current.getCurrentShip().getPosition().y;
		if(current.getCurrentShip().getDirection() == 'n')
			
			
		//currentShip.moveShip();
		return false;
	}//End of moveForward*/

	public boolean immerseMine(Point loc){
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
					m.removeMine(target);
					return true;}
			}//End of if
		}//End of for
		return false;
	}//End of shootGun Method

	public boolean repairShip(){
		return current.base().repairShip(current.getCurrentShip());		
	}//End of repairShip Method

	public boolean repairBase(){
		return current.base().repairBase();	
	}//End of repairBase Method

	public int getMineCount(){
		return current.mineCount();
	}//End of getMineCount()
	
	public Map getMap(){
		return m;
	}//End of getMap

	public String toString(){
		return current.toString() + opponent.toString() + m.toString(); }
}//End of Class

