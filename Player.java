import java.awt.Point;    

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

   public Player(String name, char startingSide){
       this._name=name;
       this._mines=10;
       this._score=0;
       this._startingSide=startingSide;
       _base = new Base(this, startingSide);        //passes THIS player as the argument for owner of the base
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
   
   public boolean makeShips(){
	   Point shipPoint = new Point();
	   if (this._startingSide == 'w') {
			shipPoint.setLocation(4, 10);
			_cruiser1 = new Cruiser(shipPoint ,'e');
			shipPoint.setLocation(4, 11);
			_cruiser2 = new Cruiser(shipPoint ,'e');
			
			shipPoint.setLocation(2, 12);
			_destroyer1 = new Destroyer(shipPoint ,'e');
			shipPoint.setLocation(2, 13);
			_destroyer2 = new Destroyer(shipPoint ,'e');
			
			shipPoint.setLocation(3, 14);
			_torpedoBoat1 = new Torpedo(shipPoint ,'e');
			shipPoint.setLocation(3, 15);
			_torpedoBoat2 = new Torpedo(shipPoint ,'e');
			shipPoint.setLocation(3, 16);
			_torpedoBoat3 = new Torpedo(shipPoint ,'e');
			
			shipPoint.setLocation(1, 17);
			_dredger1 = new Dredger();
			shipPoint.setLocation(1, 18);
			_dredger2 = new Dredger();
	   }
	   else{
			shipPoint.setLocation(25, 10);
			_cruiser1 = new Cruiser(shipPoint ,'w');
			shipPoint.setLocation(25, 11);
			_cruiser2 = new Cruiser(shipPoint ,'w');
			
			shipPoint.setLocation(27, 12);
			_destroyer1 = new Destroyer(shipPoint ,'w');
			shipPoint.setLocation(27, 13);
			_destroyer2 = new Destroyer(shipPoint ,'w');
			
			shipPoint.setLocation(26, 14);
			_torpedoBoat1 = new Torpedo(shipPoint ,'w');
			shipPoint.setLocation(26, 15);
			_torpedoBoat2 = new Torpedo(shipPoint ,'w');
			shipPoint.setLocation(26, 16);
			_torpedoBoat3 = new Torpedo(shipPoint ,'w');
			
			shipPoint.setLocation(28, 17);
			_dredger1 = new Dredger();
			shipPoint.setLocation(28, 18);
			_dredger2 = new Dredger();
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
   
   //TODO
   public boolean isDocked(Ship ship){
	   boolean result = false;
	   for(int i=0; i<ship.getSize(); i++){
		   
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
   
   //TODO
   public Point[] getRadarVisibility(){
	   
   }
   
   //TODO
   public Point[] getSonarVisibility(){
	   
   }
   
   public String toString(){
	   StringBuilder result = new StringBuilder();
	   result.append("{[Name:]" + this._name);
	   result.append(",[Score:]" + this._score);
	   result.append(",[Mines:]" + this._mines);
	   result.append(",[Side:]" + this._startingSide);
	   result.append(",[Ships:]" + this._shipCount);
	   result.append("}");
       return result.toString();
   }
   
   //TODO
   public Player fromString(String s) throws Exception{
       Player tempPlayer = new Player(null, 'w');
       
       int i = s.indexOf('{');
       int j = s.indexOf('}');
       if(i == -1 || j == -1) throw new Exception("String must include '{' and '}' to be converted to Player: " + s);
       
       
       return tempPlayer;
   }
   
}


