/*
 * Map.java
 * Michael Mattson - cs361
 * 
 * Modification:
 * 	11/30/2011
 * 
 * Description:
 * 	Models a 30 X 30 map for a game of battle ship
 */

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Map {

	public SquareTile[][]	_map;
	private Player _currentPlayer;
	private Player _opponent;

	/**
	 * Map constructor
	 * Constructs the map specified by the text file
	 */
	public Map(String txtFile){
		// initialize _map
		// set _map to contain all water using file (reefs and mines may be added here also)
		// initialize both players
		// add all ships and bases
		_map = new SquareTile[30][30];
		initializeMap(txtFile);
		_currentPlayer = new Player("p1",'w');
		_opponent = new Player("p2", 'e');
		addShips(_currentPlayer);
		addShips(_opponent);
		addBase(_currentPlayer);
		addBase(_opponent);
	}
	
	/**
	 * Creates map containing all current values from _currentPlayer and _opponent
	 * @param txtFile
	 */
	public void updateMap(String txtFile){
		if(_map == null){
			System.out.println("_map must not be null when updateMap is called.");
		    return;
		}
		initializeMap(txtFile);
		addShips(_currentPlayer);
		addShips(_opponent);
		addBase(_currentPlayer);
		addBase(_opponent);
	}
	
	/**
	 * adds all ships from the given Player to _map
	 */
	private void addShips(Player p){
		addShip(p._cruiser1);
		addShip(p._cruiser2);
		addShip(p._destroyer1);
		addShip(p._destroyer2);
		addShip(p._torpedoBoat1);
		addShip(p._torpedoBoat2);
		addShip(p._torpedoBoat3);
		addShip(p._dredger1);
		addShip(p._dredger2);
	
	}
	
	/**
	 * Map default constructor
	 * Initializes map to consist entirely of water
	 */
	public Map(){
		_map = new SquareTile[30][30];
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				_map[i][j] = new SquareTile(new SquareCoordinate(i+1,j+1),Occupant.WATER, null);
			}
		}
	}
	
	/**
	 * Reads in a .txt file containing a the specifications for the map
	 * if the user does not specify saved game a default file is used that sets the map to 
	 * the default layout for the start of a game.
	 * The default layout is specified as follows:
	 * Initially:
	 * 	- map consists entirely of water along with a 
	 * 	reef arrangement (one of x possiblities chose at random)
	 */
	private void initializeMap(String txtFile){
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(txtFile));
			this.readSequence(br);
			
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		catch(IOException e){
			System.out.println(e);
		}
		
	}

	/**
	 * Parses a line of text to a SquareTile and adds it to the map
	 * @param map
	 * @param r
	 * @throws IOException
	 */
	private void readSequence(BufferedReader r) throws IOException
	{
		String input;
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30 && (input = r.readLine()) != null ; ++j){
				try{
					_map[i][j] = SquareTile.fromString(input);
				}
				catch(FormatException e){
					System.out.println(e);
				}
			}
		}
		
	}

	/**
	 * Prints the contents of the map to console
	 */
	public void printMap(){
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				System.out.println(_map[i][j].toString());
			}
		}
	}

	/**
	 * returns SquareTile located at positing (x,y) in the map
	 * @param x
	 * @param y
	 * @return SquareTile at specified coordinates
	 */
	public SquareTile getTile(Point p){
		int x = (int)p.getX();
		int y = (int)p.getY();
		return _map[x][y];
	}

	/**
	 * Sets the tile at the given location to be occupied by o, have the given ship. If not occupied by
	 * a ship the tileShip field is null.
	 * Returns true if method was successful and false otherwise
	 * @param location
	 * @return whether or not method was successful
	 */
	public void setTile(Point location, Occupant o, Ship tileShip ) throws IllegalArgumentException{
		int x = (int)location.getX();
		int y = (int)location.getY();
		if(x<0 || x>29 || y<0 || y>29){
			throw new IllegalArgumentException("coordinates must be in the range 0<=x<=29 and 0<=y<=29");
		}
		_map[x][y].setOccupant(o);
		_map[x][y].setShip(tileShip);
	}
	
	/**
	 * Adds a ship to the map by appropriately changing the attributes of all of the tiles occupied by a
	 * ship to reflect those of the ship occupying it.
	 * @param s
	 * @return true if method was successful and false otherwise
	 */
	public boolean addShip(Ship s){
		//TODO account for every direction that ship could be facing
		Point bowCoordinate = s.getPosition();
		int bowX = (int)bowCoordinate.getX();
		int bowY = (int)bowCoordinate.getY();
		char shipDirection = s.getDirection();
		int[] myArray = s.getDamage();
		Occupant o = Occupant.WATER;
		if(shipDirection == 'e'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 1){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 3){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				--bowX;
			}
		}else if(shipDirection == 'w'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 1){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 3){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				++bowX;
			}
		}else if(shipDirection == 'n'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 1){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 3){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				++bowY;
			}
		}else if(shipDirection == 's'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 1){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 3){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				--bowY;
			}
		}else{
			return false;
		}
		return true;
		
	}
	
	/**
	 * Adds given players base to _map
	 * @param p
	 */
	private void addBase(Player p){
		Base baseToAdd = p.base();
		Point[] baseTiles = baseToAdd.location();
		for(Point currentBaseTile : baseTiles){
			this.setTile(currentBaseTile, Occupant.BASE, null);
		}
	}
	
	/**
	 * Places a mine at the specified location
	 * @param location
	 */
	public void placeMine(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		_map[x][y].setOccupant(Occupant.MINE);
	}
	
	/**
	 * Removes mine from map at given location
	 * @param location
	 */
	public void removeMine(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		_map[x][y].setOccupant(Occupant.WATER);
	}
	
	/**
	 * Returns whether or not a mine occupies the given point
	 * @param location
	 * @return True if mine occupies the given point and false otherwise
	 */
	public boolean hasMine(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		if(_map[x][y].getOccupant() == Occupant.MINE){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns whether or not a ship occupies the given point
	 * @param location
	 * @return True if ship occupies the given Point and false otherwise
	 */
	public boolean hasShip(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		SquareTile thisTile = _map[x][y];
		Occupant thisOccupant = thisTile.getOccupant();
		if(thisOccupant == Occupant.ARMOREDSHIP || thisOccupant == Occupant.DAMAGEDSHIP || thisOccupant == Occupant.UNARMOREDSHIP){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns whether or not a base occupies the given Point
	 * @param location
	 * @return True if base occupies the given Point and false otherwise
	 */
	public boolean hasBase(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		Occupant thisOccupant = _map[x][y].getOccupant();
		
		if(thisOccupant == Occupant.BASE){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns whether or not a reef occupies the given Point
	 * @param location
	 * @return True if reef occupies the given Point and false otherwise
	 */
	public boolean hasReef(Point location){
		int x = (int)location.getX();
		int y = (int)location.getY();
		Occupant thisOccupant = _map[x][y].getOccupant();
		
		if(thisOccupant == Occupant.REEF){
			return true;
		}else{
			return false;
		}
	}

	public void drawAll(Graphics g){
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				g.setColor(_map[i][j].getOccupant().getColor());
				_map[i][j].draw(g, 'm');
			}
		}
	}
	
	/**
	 * Draws Map to a user interface
	 */
	public void draw(Graphics g){
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				Point squareTilePosition = new Point(i,j);
				Ship squareTilesShip = _map[i][j].getTileShip();
				if(!inVisibility(squareTilePosition)){
					_map[i][j].draw(g,'n');
				}else if(_opponent.isPlayersShip(squareTilesShip)){
					_map[i][j].draw(g, 'e');
				}else if(this.hasMine(squareTilePosition) && !inSonarVisibility(squareTilePosition)){
					_map[i][j].draw(g, 'm');
				}else{
					_map[i][j].draw(g, 'v');
				}
				
			}
		}
	}
	
	private boolean inVisibility(Point p){
		Point[] radarVisibility = _currentPlayer.getRadarVisibility();
		Point[] sonarVisibility = _currentPlayer.getSonarVisibility();
		for(Point visiblePoint : radarVisibility){
			if(p.equals(visiblePoint)){
				return true;
			}
		}
		for(Point visiblePoint : sonarVisibility){
			if(p.equals(visiblePoint)){
				return true;
			}
		}
		return false;
	}
	
	private boolean inSonarVisibility(Point p){
		Point[] sonarVisibility = _currentPlayer.getSonarVisibility();
		for(Point visiblePoint : sonarVisibility){
			if(p.equals(visiblePoint)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Outlines all square tiles contained in the current ships mobility in magenta
	 * @param g
	 */
	public void drawMobility(Graphics g){
		//TODO
	}
	
	/**
	 * Outlines all square tiles contained in the current ships gun range in cyan
	 * @param g
	 */
	public void drawGunRange(Graphics g){
		//TODO
	}
	
	/**
	 * Returns an array containing all of the points in the currentShip's mobility
	 * @return array of all possible points of mobility for currentShip
	 */
	public Point[] getCurrentShipMobility(){
		//TODO
		return null;
	}
	
}
