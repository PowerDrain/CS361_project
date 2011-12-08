import java.awt.Point;    
import java.util.ArrayList;

public class Player {
   
   private String _name;
   private int _mines;
   private int _shipCount;
   private int _score;
   private Base _base;
   private Ship _currentShip;
   private char _startingSide;

   public Ship _cruiser1;
   public Ship _cruiser2;
   public Ship _destroyer1;
   public Ship _destroyer2;
   public Ship _torpedoBoat1;
   public Ship _torpedoBoat2;
   public Ship _torpedoBoat3;
   public Ship _dredger1;
   public Ship _dredger2;

   public Player(){
	   this("Player", 'w');
   }
   
   public Player(String name, char startingSide){
       this._name=name;
       this._mines=10;
       this._score=0;
       this._startingSide=startingSide;
       _base = new Base(startingSide);        //passes THIS player as the argument for owner of the base
       this.makeShips();
       _currentShip = _cruiser1;
   }
   
   public int mineCount(){
       return _mines;
   }
   
   public boolean incrementMineCount(){
       ++_mines;
       return true;
   }
   
   public boolean decrementMineCount(){
       if(_mines>0){
           --_mines;
           return true;
       }
       return false;
   }
   
   public String name(){
       return _name;
   }

   public Ship getCurrentShip(){
	   return _currentShip;
   }
   
   public char getStartingSide(){
	   return _startingSide;
   }
   
   public int shipCount(){
       return _shipCount;
   }
   
   public void decrementShipCount(){
       --_shipCount;
   }
   
   private boolean makeShips(){
	   Point shipPoint;
	   if (this._startingSide == 'w') {
			shipPoint = new Point(5, 10);
			_cruiser1 = new Cruiser(shipPoint ,'e');
			shipPoint = new Point(5, 11);
			_cruiser2 = new Cruiser(shipPoint ,'e');
			
			shipPoint = new Point(3, 12);
			_destroyer1 = new Destroyer(shipPoint ,'e');
			shipPoint = new Point(3, 13);
			_destroyer2 = new Destroyer(shipPoint ,'e');
			
			shipPoint = new Point(4, 14);
			_torpedoBoat1 = new Torpedo(shipPoint ,'e');
			shipPoint = new Point(4, 15);
			_torpedoBoat2 = new Torpedo(shipPoint ,'e');
			shipPoint = new Point(4, 16);
			_torpedoBoat3 = new Torpedo(shipPoint ,'e');
			
			shipPoint = new Point(2, 17);
			_dredger1 = new Dredger(shipPoint, 'e');
			shipPoint = new Point(2, 18);
			_dredger2 = new Dredger(shipPoint, 'e');
	   }
	   else{
			shipPoint = new Point(24, 10);
			_cruiser1 = new Cruiser(shipPoint ,'w');
			shipPoint = new Point(24, 11);
			_cruiser2 = new Cruiser(shipPoint ,'w');
			
			shipPoint = new Point(26, 12);
			_destroyer1 = new Destroyer(shipPoint ,'w');
			shipPoint = new Point(26, 13);
			_destroyer2 = new Destroyer(shipPoint ,'w');
			
			shipPoint = new Point(25, 14);
			_torpedoBoat1 = new Torpedo(shipPoint ,'w');
			shipPoint = new Point(25, 15);
			_torpedoBoat2 = new Torpedo(shipPoint ,'w');
			shipPoint = new Point(25, 16);
			_torpedoBoat3 = new Torpedo(shipPoint ,'w');
			
			shipPoint = new Point(27, 17);
			_dredger1 = new Dredger(shipPoint, 'w');
			shipPoint.setLocation(27, 18);
			_dredger2 = new Dredger(shipPoint, 'w');
	   }
	this._shipCount=9;
       return true;
   }
   
   public Base base(){
       return this._base;
   }
   
   public int totalScore(){
       return _score;
   }
   
   public boolean isDocked(Ship ship){
	   boolean result = false;
	   Point shipPoints[] = ship.getShipCoordinates();
	   Point basePoints[] = _base.location();
	   for(int i=0; i<ship.getSize(); i++){
		   for(int j=0; j<11; j++){
			   if(shipPoints[i].equals(basePoints[j]))
				   result = true;
		   }
	   }
	   return result;
   }
   
   public boolean isPlayersShip(Ship ship){
	   boolean result = false;
	   if(ship.equals(_cruiser1))
		   result = true;
	   else if(ship.equals(_cruiser2))
		   result = true;
	   else if(ship.equals(_destroyer1))
		   result = true;
	   else if(ship.equals(_destroyer2))
		   result = true;
	   else if(ship.equals(_torpedoBoat1))
		   result = true;
	   else if(ship.equals(_torpedoBoat2))
		   result = true;
	   else if(ship.equals(_torpedoBoat3))
		   result = true;
	   else if(ship.equals(_dredger1))
		   result = true;
	   else if(ship.equals(_dredger2))
		   result = true;
	   return result;
   }
   
   public Point[] getRadarVisibility(){
	ArrayList<Point> points = new ArrayList<Point>();
	
	Point[] cruiser1Radar = _cruiser1.getRadarRange();
	for (int i = 0; i < cruiser1Radar.length; i++){
		points.add(cruiser1Radar[i]);
	}
	
	Point[] cruiser2Radar = _cruiser2.getRadarRange();
	for(int i=0; i<cruiser2Radar.length; i++){
		points.add(cruiser2Radar[i]);
	}
	
	Point[] destroyer1Radar = _destroyer1.getRadarRange();
	for(int i=0; i<destroyer1Radar.length; i++){
		points.add(destroyer1Radar[i]);
	}
	
	Point[] destroyer2Radar = _destroyer2.getRadarRange();
	for(int i=0; i<destroyer2Radar.length; i++){	
		points.add(destroyer2Radar[i]);
	}
	
	Point[] torpedoBoat1Radar = _torpedoBoat1.getRadarRange();
	for(int i=0; i<torpedoBoat1Radar.length; i++){
		points.add(torpedoBoat1Radar[i]);
	}
	
	Point[] torpedoBoat2Radar = _torpedoBoat2.getRadarRange();
	for(int i=0; i<torpedoBoat2Radar.length; i++){
		points.add(torpedoBoat2Radar[i]);
	}
	Point[] torpedoBoat3Radar = _torpedoBoat3.getRadarRange();
	for(int i=0; i<torpedoBoat3Radar.length; i++){
		points.add(torpedoBoat3Radar[i]);
	}
	points.trimToSize();
	Point[] retVal = new Point[points.size()];
	retVal = points.toArray(retVal);
	return retVal;
   }

   public Point[] getSonarVisibility(){
		ArrayList<Point> points = new ArrayList<Point>();
		
		Point[] dredger1Radar = _dredger1.getRadarRange();
		for(int i=0; i<dredger1Radar.length; i++){
			points.add(dredger1Radar[i]);
		}
		
		Point[] dredger2Radar = _dredger2.getRadarRange();
		for(int i=0; i<dredger2Radar.length; i++){
			points.add(dredger2Radar[i]);
		}
		points.trimToSize();
		Point[] retVal = new Point[points.size()];
		retVal = points.toArray(retVal);
		return retVal;
   }
   
   public Point[] getMobility(){
	   return _currentShip.getMoveMobility();
   }
   
   public Point[] getGunRange(){
	   return _currentShip.getGunRange();
   }
   
   public String toString(){
	   StringBuilder result = new StringBuilder();
	   result.append("{<NAME>" + this._name + "</NAME>");
	   result.append("<SCORE>" + this._score + "</SCORE>");
	   result.append("<MINES" + this._mines + "</MINES>");
	   result.append("<SIDE>" + this._startingSide + "</SIDE>");
	   result.append("<SHIPS>" + this._shipCount + "</SHIPS>");
	   result.append("}");
       return result.toString();
   }
   
   //TODO
   public Player fromString(String s) throws Exception{
       Player tempPlayer = new Player(null, 'w');
       if(s.indexOf('{') == -1 || s.indexOf('}') == -1) 
    	   throw new Exception("String must include '{' and '}' to be converted to Player: " + s);
       
       
       return tempPlayer;
   }
   
}


