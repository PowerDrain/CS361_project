import java.awt.Point;    
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
   
   public Ship[] getPlayerShips(){
	   ArrayList<Ship> playerShips = new ArrayList<Ship>();
	   playerShips.add(_cruiser1);
	   playerShips.add(_cruiser2);
	   playerShips.add(_destroyer1);
	   playerShips.add(_destroyer2);
	   playerShips.add(_dredger1);
	   playerShips.add(_dredger2);
	   playerShips.add(_torpedoBoat1);
	   playerShips.add(_torpedoBoat2);
	   playerShips.add(_torpedoBoat3);

	   // Clean up array list, change into regular array and then return
	   playerShips.trimToSize();
	   Ship[] retVal = new Ship[playerShips.size()];
	   retVal = playerShips.toArray(retVal);
	   return retVal;
   }
   
   public void setCurrentShip(Ship ship){
	   this._currentShip = ship;
   }
   
   public int getPlayerScore(){
	   int playerTotalScore = 0;
	   Ship[] playerShips = this.getPlayerShips();
	   int playerBaseScore = this.base().getPointValue();
	   for (int i = 0; i < playerShips.length; i++){
		   playerTotalScore += playerShips[i].getPointValue();
	   }
	   playerTotalScore += playerBaseScore;
	   return playerTotalScore;
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
			shipPoint = new Point(27, 18);
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
	   Point basePoints[] = _base.getAdjacentPoints();
	   for(int i=0; i<ship.getSize(); i++){
		   for(int j=0; j<basePoints.length; j++){
			   if(shipPoints[i].equals(basePoints[j]))
				   return true;
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
	   result.append("<PLAYER>:<NAME>" + this._name + "</NAME>:");
	   result.append("<SCORE>" + this._score + "</SCORE>:");
	   result.append("<MINES>" + this._mines + "</MINES>:");
	   result.append("<SIDE>" + this._startingSide + "</SIDE>:");
	   result.append("<SHIPNUM>" + this._shipCount + "</SHIPNUM>:");
	   result.append("<BASE>" + this.base().toString() + "</BASE>");
	   result.append("<SHIPS>:<CRUISER1>" + "" + "</CRUISER1>:");
	   result.append("<CRUISER2>" + "" + "</CRUISER2>:");
	   result.append("<DESTROYER1>" + "" + "</DESTROYER1>:");
	   result.append("<DESTROYER2>" + "" + "</DESTROYER2>:");
	   result.append("<TORPEDOBOAT1>" + "" + "</TORPEDOBOAT1>:");
	   result.append("<TORPEDOBOAT2>" + "" + "</TORPEDOBOAT2>:");
	   result.append("<TORPEDOBOAT3>" + "" + "</TORPEDOBOAT3>:");
	   result.append("<DREDGER1>" + "" + "</DREDGER1:>");
	   result.append("<DREDGER2>" + "" + "</DREDGER2:>");
	   result.append("</SHIPS>:</PLAYER>");
       return result.toString();
   }
   
   public static Player fromString(String s) throws Exception{
       Player tempPlayer = new Player("tempPlayer", 'w');
       
       if(!s.contains("<PLAYER>") || !s.contains("</PLAYER>"))
    	   throw new Exception("String must include '<PLAYER>' and '</PLAYER>' to be converted to Player: " + s);
       
       String[] playerParsedStrings = s.split(":");
       String playerSubString;
       
       for(int i=0; i<playerParsedStrings.length; i++){
    	   if(playerParsedStrings[i].contains("<NAME>")){
	    	   playerSubString = playerParsedStrings[i].substring(6, playerParsedStrings[i].lastIndexOf("</NAME>"));
	    	   tempPlayer._name = playerSubString;
	       }
	       else if(playerParsedStrings[i].contains("<SCORE>")){
	    	   playerSubString = playerParsedStrings[i].substring(7, playerParsedStrings[i].lastIndexOf("</SCORE>"));
	    	   tempPlayer._score = Integer.decode(playerSubString);
	       }
	       else if(playerParsedStrings[i].contains("<MINES>")){
	    	   playerSubString = playerParsedStrings[i].substring(7, playerParsedStrings[i].lastIndexOf("</MINES>"));
	    	   tempPlayer._mines = Integer.decode(playerSubString);
	       }
	       else if(playerParsedStrings[i].contains("<SIDE>")){
	    	   playerSubString = playerParsedStrings[i].substring(6, playerParsedStrings[i].lastIndexOf("</SIDE>"));
	    	   tempPlayer._startingSide = playerSubString.charAt(0);
	       }
	       else if(playerParsedStrings[i].contains("<SHIPNUM>")){
	    	   playerSubString = playerParsedStrings[i].substring(9, playerParsedStrings[i].lastIndexOf("</SHIPNUM>"));
	    	   tempPlayer._shipCount = Integer.decode(playerSubString);
	       }
	       else if(playerParsedStrings[i].contains("<BASE>")){
	    	   playerSubString = playerParsedStrings[i].substring(6, playerParsedStrings[i].lastIndexOf("</BASE>"));
	    	   Base.fromString(playerSubString);
	       }
	       else if(playerParsedStrings[i].contains("<SHIPS>")){
	    	   //do nothing fun at all
	       }
	       else if(playerParsedStrings[i].contains("<CRUISER1>")){
	    	   //TODO these
	       }
	       else if(playerParsedStrings[i].contains("<CRUISER2>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<DESTROYER1>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<DESTROYER2>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<TORPEDOBOAT1>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<TORPEDOBOAT2>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<TORPEDOBOAT3>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<DREDGER1>")){
	    	   
	       }
	       else if(playerParsedStrings[i].contains("<DREDGER2>")){
	    	   
	       }
       }
       return tempPlayer;
   }
   
}


