import java.awt.Point;     

public class Player {
   
   private String _name;
   private int _mines;
   private int _turnNum;
   private int _shipCount;
   private int _score;
   private Base _base;
   private Ship _currentShip;
   private String _playerId;
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
   
   //TODO add method to check if ship is docked by checking array of points of
   //base and currentShip

   public Player(String name, String playerId, char startingSide){
       this._name=name;
       this._mines=10;
       this._turnNum=1;
       this._score=0;
       this._playerId=playerId;
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
   
   public String playerId(){
       return _playerId;
   }
   
   public char getStartingSide(){
	   return _startingSide;
   }
   
   public int turnNumber(){
       return _turnNum;
   }
   
   public void incrementTurn(){
       ++_turnNum;
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
   
   public String toString(){
       return "";
   }
   
   public Player fromString(){
       Player tempPlayer = new Player(null, null, 'w');
       return tempPlayer;
   }
   
}


