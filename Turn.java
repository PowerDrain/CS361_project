package entity;

import java.awt.Point;

public class Turn {
	//Not sure if Map is created in the Turn Class
	private Map m;
	private Player user;
	private Player enemy;

	//No argument constructor for creating a instance of a turn.
	public Turn(){
		//Still figuring out how to implement
		user = new Player("Player 1");
		enemy = new Player("Player 2");
		m = new Map();
	}//End of Default Constructor

	//Takes a ship to be moved in the game and the direction to move it.  If it was able to do so, 
	//checking game rules, it moves the ship and returns true, otherwise it returns false.
	public boolean moveShip(Ship currentShip, char direction){
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
	public boolean immerseMine(Point loc){
		//Check for type of ship
		if(user.numMines() > 0){
			user.setNumMines(-1);
			return m.placeMine(loc);
		}//End of if
		return false;
	}//End of immerseMine Method

	//Takes the location of the mine. Withdraw mine from the map and increments users mine count. 
	//If the move is legal and completes successfully it returns true, otherwise returns false. 
	public boolean withdrawMine(Point loc){
		//Check for type of ship
		if(m.removeMine(loc)){
			user.setNumMines(1);
			return true;
		}//End of if
		return false;
	}//End of withdrawMine Method

	//Takes a ship to shoot a torpedo, if able to do so via game rules it returns true, 
	//otherwise returns false.
	public boolean launchTorpedo(Ship currentShip){
		//Check if currentShip is the right type!
		//Must get ship location and direction
		Point a = currentShip.getPosition();
		char d = currentShip.getDirection();
		int x, y;
		x = a.x;
		y = a.y;
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
		return user.base().repairShip(currentShip);		
	}//End of repairShip Method

	//If allowed it repairs players base and returns true, otherwise returns false.
	public boolean repairBase(){
		return user.base().repairBase();	
	}//End of repairBase Method

	//Returns true if the turn is passed otherwise returns false.
	public boolean pass(){   //Needs a little work  
		if(getCurrentPlayer()){
			user.setNumTurn();
			return true;
		}//End of if
		return false;
	}//End of pass Method

	//Returns true if it is users turn and false if it is enemy�s turn
	public boolean getCurrentPlayer(){
		//I should increment numTurn   
		return user.numTurn() == enemy.numTurn();
	}//End of getCurrentPlayer Method

	//Displays the �Help� topics for user�s reference.
	public void getHelp(){   
		System.out.println("Step #1:  Eat Shit and Die!");
	}//End of getHelp Method	
}//End of Class