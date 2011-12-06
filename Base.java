import java.awt.Point;
import java.util.*;

public class Base {
   
   private int[] _damage;
   private Point[] _location;
   private char _startingSide;
   
   public Base(){
	   this('w');
   }
   
   public Base(char startingSide){
       this._startingSide=startingSide;
       _damage = new int[10];
       _location = new Point[10];
       
       for(int i=0; i<_damage.length; i++){
           _damage[i]=1;        //1=untouched, 0=damaged
       }
       
       if(this._startingSide=='w'){
    	   for(int i=0; i<_location.length; i++){
    		   Point x = new Point(0, i+10);
    		   _location[i] = x;
    	   }
       }
       else{
    	   for(int i=0; i<_location.length; i++){
    		   Point x = new Point(29, i+10);
    		   _location[i] = x;
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
   
   public boolean receiveDamage(Point loc, char type) {
       int damageIndex =(loc.y-10);
       boolean didDamage=false;
       // [Requirements Page 5 Regarding Torpedoes]:
       //... The damage is only caused to an additional box if it
       //can be reached in direction to the bow (if it is a ship which is reached)
       //or towards a side to be determined (if it is a naval base which is reached).
       type = Character.toLowerCase(type);
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
   
   public String toString(){
	   StringBuilder result = new StringBuilder();
	   result.append("{[Damage:]");
	   for(int i=0; i<this._damage.length; i++){
		   result.append(this._damage[i] + ",");
	   }
	   result.append("[Location:]");
	   //TODO change the way Points are converted to string... java's toString implementation blows.
	   for(int i=0; i<this._location.length; i++){
		   result.append(this._location.toString() + ",");
	   }
	   result.append("[Side:]" + this._startingSide);
	   result.append("}");
       return result.toString();
   }
   
   //TODO finish for Location and Side.
   public Base fromString(String s) throws Exception{
	   Base tempBase = new Base('w');
       if(s.indexOf('{') == -1 || s.indexOf('}') == -1) 
    	   throw new Exception("String must include '{' and '}' to be converted to Player: " + s);
       s = s.substring(1, s.length()-2); //trim off the { and }
	   s = s.substring(s.lastIndexOf("[Damage:]")+1); //trim off [Damage:]
	   String damage = s.substring(0, s.indexOf("[Location:]"));
	   _damage = new int[10];
	   //TODO fix method to accommodate for numbers bigger than 1 digit, and actually convert to Integer instead of characters
	   for(int i=0; i<_damage.length+1; i++){
		   _damage[i]=damage.charAt(0);
		   damage = damage.substring(1);	//trim the ',' off after each run through besides the first
	   }
	   s = s.substring(s.indexOf("Location:]")+1); 	//trim off [Location:]
	   String location = s.substring(0, s.indexOf("[Side:]"));
	   _location = new Point[10];

	   return tempBase;
   }
}


