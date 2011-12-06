package entity;
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

	//Add a Rotate Ship
	//I need the direction of the bow
	public boolean rotateShip(Ship currentShip, char direction){
		return false;
	}//End of rotateShip
	
	//Takes a ship to be moved in the game and the direction to move it.  If it was able to do so, 
	//checking game rules, it moves the ship and returns true, otherwise it returns false.
	//I need the direction of the bow
	public boolean moveShip(Ship currentShip, char direction){//TODO I need to add distance -Al
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

	//Takes the location to drop a mine. Immerses a mine at the given location and decrements 
	//users mine count. If move is legal and there are mines available it returns true, 
	//otherwise returns false.
	public boolean immerseMine(Point loc, Ship currentShip){
		//Check for type of ship
		if(current.mineCount() > 0 && currentShip.toString() == "Dredger"){
			current.decrementMineCount();
			m.placeMine(loc);
			return true;
		}//End of if
		return false;
	}//End of immerseMine Method

	//Takes the location of the mine. Withdraw mine from the map and increments users mine count. 
	//If the move is legal and completes successfully it returns true, otherwise returns false. 
	public boolean withdrawMine(Point loc, Ship currentShip){
		//Check for type of ship
		//TODO maybe there is a more elegant solution to this,
		//this just doesn't seem good but it may work. -Tommy
		//if(current.getCurrentShip().getClass().getName()=="Dredger"){
		if(currentShip.toString() == "Dredger"){  //TODO Ship must be touching mine.
			m.removeMine(loc);
			current.decrementMineCount();
			return true;
		}
		return false;
	}//End of withdrawMine Method

	//Takes a ship to shoot a torpedo, if able to do so via game rules it returns true, 
	//otherwise returns false.
	public boolean launchTorpedo(Ship currentShip){
		//Check if currentShip is the right type!
		//Must get ship location and direction
		if(currentShip.toString() != "Destroyer" || currentShip.toString() != "Torpedo") return false;
		
		Point shipPosition = currentShip.getPosition();
		char d = currentShip.getDirection();
				
		int x, y;
		x = shipPosition.x;
		y = shipPosition.y;
		//if d equals n decrement y
		if(d == 'n') 
			for(; y >= y - 10; --y){
				if(m.hasBase(new Point(x,y))) return true;//Should destroy Box
				if(m.hasMine(new Point(x,y))) return true;//Should destroy Mine
				if(m.hasReef(new Point(x,y))) return true;//Nothing should happen
				if(m.hasShip(new Point(x,y))) return true;//Should damage Ship      TODO
			}//End of for  //End of if
		//if d equals s increment y
		if(d == 's') 
			for(; y <= y + 10; ++y){
				if(m.hasBase(new Point(x,y))) return true;//Should destroy Box
				if(m.hasMine(new Point(x,y))) return true;//Should destroy Mine
				if(m.hasReef(new Point(x,y))) return true;//Nothing should happen
				if(m.hasShip(new Point(x,y))) return true;//Should damage Ship	TODO
			}//End of for  //End of if	
		//if d equals e increment x
		if(d == 'e')
			for(; x <= x + 10; ++x){
				if(m.hasBase(new Point(x,y))) return true;//Should destroy Box
				if(m.hasMine(new Point(x,y))) return true;//Should destroy Mine
				if(m.hasReef(new Point(x,y))) return true;//Nothing should happen
				if(m.hasShip(new Point(x,y))) return true;//Should damage Ship  TODO
			}//End of for  //End of if	
		//if d equals w decrement x	
		if(d == 'w') 
			for(; x >= x - 10; --x){
				if(m.hasBase(new Point(x,y))) return true;//Should destroy Box
				if(m.hasMine(new Point(x,y))) return true;//Should destroy Mine
				if(m.hasReef(new Point(x,y))) return true;//Nothing should happen
				if(m.hasShip(new Point(x,y))) return true;//Should damage Ship TODO
			}//End of for  //End of if
		return false;
	}//End of launchTorpedo Method

	//Takes a ship to shoot the gun and the location of where the shot should go.  If all game rules 
	//followed it returns true, otherwise returns false.
	public boolean shootGun(Ship currentShip, Point target){
		if(currentShip.toString() != "Cruiser" || currentShip.toString() != "Torpedo") return false;
		Point[] p = currentShip.getGunRange();
		for(int i = 0; i < p.length; ++i){
			if(p[i].equals(target)){
				//This should inflict damage on a ship  
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

	//Returns true if the turn is passed otherwise returns false.
	//TODO This method might not have to do anything at all... -Tommy
	//Yeah I think the Gui handles the pass -Al
/*	public boolean pass(){
		return true;
	}//End of pass Method

	//Returns true if it is users turn and false if it is enemy’s turn
	//TODO I don't think we need this one -Tommy
	//Agreed -Al
	public boolean getCurrentPlayer(){
		//I should increment numTurn   
		return current.numTurn() == opponent.numTurn();
	}//End of getCurrentPlayer Method

	//Displays the “Help” topics for user’s reference.
	//I think the Gui would handle this also -Al
	public void getHelp(){   
		System.out.println("Step #1:  Eat Shit and Die!");
	}//End of getHelp Method*/
	
	public String toString(){
		return current.toString() + opponent.toString() + m.toString(); }
}//End of Class
