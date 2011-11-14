     package entity;

public class Player {
   
   private String _name;
   private int _mines;
   private int _turnNum;
   private int _shipCount;
   private int _score;
   private Base _base;
   private Ship _currentShip;
   private String _playerId;
   

   public Player(String name, String playerId){
       this._name=name;
       this._mines=10;
       this._turnNum=1;
       this._score=0;
       this._playerId=playerId;
       _base = new Base(this);        //passes THIS player as the argument for owner of the base
       this.makeShips();
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
   
   public String playerId(){
       return _playerId;
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
       //waiting for details on how to create each type of ship on the map
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
       Player tempPlayer = new Player(null, null);
       return tempPlayer;
   }
   
}


