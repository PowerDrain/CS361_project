
import java.awt.Point;

public class Base {
   
   private int[] _damage;
   private Player _owner;
   private Point[] _location;
   private char _startingSide;
   
   public Base(Player owner, char startingSide){
       this._owner=owner;
       this._startingSide=startingSide;
       _damage = new int[10];
       for(int i=0; i<10; i++){
           _damage[i]=1;        //1=untouched, 0=damaged
       }
       if(this._startingSide=='w'){
    	   for(int i=0; i<_location.length; i++){
    		   _location[i].setLocation(0, i+10);
    	   }
       }
       else{
    	   for(int i=0; i<_location.length; i++){
    		   _location[i].setLocation(29, i+10);
    	   }
       }
   }
   
   public boolean repairShip(Ship ship){
       if(this.canRepairShip()){
           ship.repairDamage();
           return true;
       }
       else
           return false;
   }
   
   public boolean repairBase(){
       if(this.canRepairBase()){
           for(int i=0; i<_damage.length; i++){
               if(_damage[i]==0){
                   _damage[i]=1;
                   return true;
               }
           }
       }
       return false;
   }
   
   public int[] damage(){
       return _damage;
   }
   
   public Player owner(){
       return _owner;
   }
   
   public boolean receiveDamage(Point loc, char type) {
       int damageIndex =(loc.y-10);
       boolean didDamage=false;
       // [Requirements Page 5 Regarding Torpedoes]:
       //... The damage is only caused to an additional box if it
       //can be reached in direction to the bow (if it is a ship which is reached)
       //or towards a side to be determined (if it is a naval base which is reached).
       if(type == 't'){
           int damageToDo=2;
           while(this.intact() && (damageToDo != 0) && (damageIndex<10)){
               if(_damage[damageIndex] == 1){
                   _damage[damageIndex]=0;
                   damageToDo--;
                   didDamage=true;
               }
               damageIndex++;
           }
       }
       else if(type == 'g'){
           if(_damage[damageIndex]==1){
               _damage[damageIndex]=0;
               didDamage=true;
           }
       }
       return didDamage;
   }
   
   public boolean canRepairShip(){
       int count=0;
       for(int i=0; i<_damage.length; i++){    //counts number of untouched
           if(_damage[i]==1)
               count++;
       }
       if(count>=5)
           return true;
       else
           return false;
   }
   
   public boolean isFullyRepaired(){
	   for(int i=0; i<_damage.length; i++){
		   if(_damage[i]==0){
			   return false;
		   }
	   }
	   return true;
   }
   
   public boolean canRepairBase(){
	   if(this.isFullyRepaired())  //the case that the base is fully repaired which returns false
		   return false;
       for(int i=0; i<_damage.length; i++){
           if(_damage[i]==1){
               return true;
           }
       }
       return false;
   }
   
   public boolean intact(){
       for(int i=0; i<_damage.length; i++){
           if(_damage[i]==1){
               return true;
           }
       }
       return false;
   }
   
   public Point[] location(){
	   return _location;
   }
   
}


