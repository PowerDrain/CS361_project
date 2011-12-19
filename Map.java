/*
 * Map.java
 * Michael Mattson - cs361
 * 
 * Modification:
 * 	12/14/2011
 * 
 * Description:
 * 	Models a 30 X 30 map for a game of battle ship
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
public class Map implements Serializable{

	public SquareTile[][]	_map;
	private Player _currentPlayer;
	private Player _opponent;

	/**
	 * Map constructor
	 * Constructs the map specified by the text file
	 */
	public Map(String txtFile, Player currentPlayer, Player opponent){
		// initialize _map
		// set _map to contain all water using file (reefs and mines may be added here also)
		// initialize both players
		// add all ships and bases
		_map = new SquareTile[30][30];
		initializeMap(txtFile);
		_currentPlayer = currentPlayer;
		_opponent = opponent;
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
		clearShipsFromMap();
		addShips(_currentPlayer);
		addShips(_opponent);
		addBase(_currentPlayer);
		addBase(_opponent);
	}
	
	private void clearShipsFromMap(){
		for(int i =0; i<30; ++i){
			for(int j=0; j<30; ++j){
				Point mapCoordinate = new Point(i,j);
				if(hasShip(mapCoordinate)){
					setTile(mapCoordinate, Occupant.WATER,null);
				}
			}
		}
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
	 * Adds a ship to the map by appropriately changing the attributes of all of the tiles occupied by a
	 * ship to reflect those of the ship occupying it.
	 * @param s
	 * @return true if method was successful and false otherwise
	 */
	private boolean addShip(Ship s){
		Point bowCoordinate = s.getPosition();
		int bowX = (int)bowCoordinate.getX();
		int bowY = (int)bowCoordinate.getY();
		char shipDirection = s.getDirection();
		int[] myArray = s.getDamage();
		Occupant o = Occupant.WATER;
		if(shipDirection == 'e'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 0){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 1){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				--bowX;
			}
		}else if(shipDirection == 'w'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 0){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 1){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				++bowX;
			}
		}else if(shipDirection == 'n'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 0){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 1){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 2){
					o = Occupant.ARMOREDSHIP;
				}else{
					return false;
				}
				this.setTile(new Point(bowX,bowY), o, s);
				++bowY;
			}
		}else if(shipDirection == 's'){
			for(int i = myArray.length-1; i>=0; --i){
				if(myArray[i] == 0){
					o = Occupant.DAMAGEDSHIP;
				}else if(myArray[i] == 1){
					o = Occupant.UNARMOREDSHIP;
				}else if(myArray[i] == 2){
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
		int[] baseDamage = baseToAdd.damage();
		Point[] baseTiles = baseToAdd.location();
		int damageIndex = 0;
		for(Point currentBaseTile : baseTiles){
			if(baseDamage[damageIndex]==0){
				this.setTile(currentBaseTile, Occupant.DAMAGEDBASE, null);
			}else if(baseDamage[damageIndex]==1){
				this.setTile(currentBaseTile, Occupant.BASE, null);
			}else{
				throw new IllegalStateException("the base damage array contains invalid value.");
			}
		}
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
	 * @throws IllegalArgumentException if the tileOwner parameter is not a Ship, Base, or null
	 * @throws IllegalArgumentException if the location parameter is not in the map
	 */
	public void setTile(Point location, Occupant o, Object tileOwner) throws IllegalArgumentException{
		int x = (int)location.getX();
		int y = (int)location.getY();
		if(x<0 || x>29 || y<0 || y>29){
			throw new IllegalArgumentException("coordinates must be in the range 0<=x<=29 and 0<=y<=29");
		}
		if(!(tileOwner instanceof Base) && !(tileOwner instanceof Ship) && tileOwner!=null){
			throw new IllegalArgumentException("tile owner must be a Ship, Base, or null.");
		}
		
		_map[x][y].setOccupant(o);
		if(tileOwner instanceof Ship){
			_map[x][y].setTileOwner(tileOwner);
		}else if(tileOwner instanceof Base){
			_map[x][y].setTileOwner(tileOwner);
		}else{
			_map[x][y].setTileOwner(null);
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
		
		if(thisOccupant == Occupant.BASE || thisOccupant == Occupant.DAMAGEDBASE){
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

	/**
	 * Draws Map to a user interface with unfiltered visibility
	 * @param g
	 */
	public void drawAll(Graphics g){
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				g.setColor(_map[i][j].getOccupant().getColor());
				_map[i][j].draw(g, 'v');
			}
		}
	}
	
	/**
	 * Draws Map to a user interface with filtered visibility
	 * 
	 */
	public void draw(Graphics g){
		for(int i = 0; i<30; ++i){
			for(int j = 0; j<30; ++j){
				Point squareTilePosition = new Point(i,j);
				Ship squareTilesShip = null;
				if(_map[i][j].tileOwnerIsShip()){
					squareTilesShip = (Ship)_map[i][j].getTileOwner();
				}
				if(hasBase(squareTilePosition) || hasReef(squareTilePosition)){
					_map[i][j].draw(g, 'v');
				}else if((!inVisibility(squareTilePosition)) ){
					_map[i][j].draw(g,'n');
				}else if(squareTilesShip != null && _opponent.isPlayersShip(squareTilesShip)){
					_map[i][j].draw(g, 'e');
				}else if(this.hasMine(squareTilePosition) && !inSonarVisibility(squareTilePosition)){
					_map[i][j].draw(g, 'm');
				}else{
					_map[i][j].draw(g, 'v');
				}
				
			}
		}
	}
	
	/**
	 * Returns whether or not the given point is in either radar visibility or sonar visibility
	 * @param p
	 * @return True if the given point is in radar visibility or sonar visibility and false otherwise
	 */
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
	
	/**
	 * Returns whether or not a SquareTile located at a given point is visible by sonar
	 * @param p
	 * @return true if given Point is visible by sonar and false otherwise
	 */
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
	 * Outlines all square tiles contained in the current ships mobility in pink
	 * @param g
	 */
	public void drawMobility(Graphics g){
		//TODO
		g.setColor(Color.pink);
		Point[] mobilityArray = _currentPlayer.getMobility();
		for(Point mobilityPoint : mobilityArray){
			int x = (int)mobilityPoint.getX()+1; // adding 1 to x and y coordinates because map[0][0] is
			int y = (int)mobilityPoint.getY()+1; // at (1,1) on the SquareTile grid
			SquareCoordinate s = new SquareCoordinate(x,y);
			Polygon newPolygon = s.toPolygon(SquareTile.WIDTH);
			g.drawPolygon(newPolygon);
		}
	}
	
	/**
	 * Outlines all square tiles contained in the current ships gun range in cyan
	 * @param g
	 */
	public void drawGunRange(Graphics g){
		g.setColor(Color.cyan);
		Point[] gunRangeArray = _currentPlayer.getGunRange();
		for(Point gunRangePoint : gunRangeArray){
			int x = (int)gunRangePoint.getX()+1; // adding 1 to x and y coordinates because map[0][0] is
			int y = (int)gunRangePoint.getY()+1; // at (1,1) on the SquareTile grid
			SquareCoordinate s = new SquareCoordinate(x,y);
			Polygon newPolygon = s.toPolygon(SquareTile.WIDTH);
			g.drawPolygon(newPolygon);
		}
	}
	
	public void drawCurrentShip(Graphics g){
		g.setColor(Color.orange);
		Ship currentShip = _currentPlayer.getCurrentShip();
		Point[] occupiedCoordinates = currentShip.getShipCoordinates();
		for(Point coordinateToDraw : occupiedCoordinates){
			int x = (int)coordinateToDraw.getX()+1; 
			int y = (int)coordinateToDraw.getY()+1;
			SquareCoordinate squareCoordinateToDraw = new SquareCoordinate(x,y);
			Polygon shipBlock = squareCoordinateToDraw.toPolygon(SquareTile.WIDTH);
			g.drawPolygon(shipBlock);
		}
	}
	
	public void drawRotationalMobility(Graphics g){
		g.setColor(Color.pink);
		Ship currentShip = _currentPlayer.getCurrentShip();
		Point[] rotationalMobilityCoordinates = currentShip.getRotationalMobility();
		for(Point coordinateToOutline : rotationalMobilityCoordinates){
			int x = (int)coordinateToOutline.getX()+1;
			int y = (int)coordinateToOutline.getY()+1;
			SquareCoordinate squareCoordinateToOutline = new SquareCoordinate(x,y);
			Polygon gridCoordinateToOutline = squareCoordinateToOutline.toPolygon(SquareTile.WIDTH);
			g.drawPolygon(gridCoordinateToOutline);
		}
		
	}
	
	public void setCurrentShip(Ship s){
		_currentPlayer.setCurrentShip(s);
	}
	
	public Player getCurrentPlayer(){
		return _currentPlayer;
	}
}
