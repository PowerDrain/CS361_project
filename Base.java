import java.awt.Point;

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
	   result.append("{<BASE>:<DAMAGE>");
	   for(int i=0; i<this._damage.length; i++){
		   result.append(this._damage[i] + ",");
	   }
	   result.append("</DAMAGE>:");
	   result.append("<LOCATIONX>");
	   for(int i=0; i<this._location.length; i++){
		   result.append(this._location[i].x + ",");
	   }
	   result.append("</LOCATIONX>:<LOCATIONY>");
	   for(int i=0; i<this._location.length; i++){
		   result.append(this._location[i].y + ",");
	   }
	   result.append("</LOCATIONY>:");
	   
	   result.append("<SIDE>" + _startingSide + "</SIDE>:");
	   result.append("</BASE>");
       return result.toString();
   }
   
   public Base fromString(String s) throws Exception{
	   Base tempBase = new Base('w');
       String[] baseParsedStrings = s.split(":");
       String baseSubString;
	   String[] tempStrings;
       for(int i=0; i<baseParsedStrings.length; i++){
    	   if(baseParsedStrings[i].contains("<DAMAGE>")){
	    	   baseSubString = baseParsedStrings[i].substring(8, baseParsedStrings[i].lastIndexOf("</DAMAGE>"));
		       tempStrings = baseSubString.split(",");
	    	   for(int j=0; j<tempStrings.length; j++){
	    		   tempBase._damage[j] = Integer.decode(tempStrings[j]);
	    	   }
    	   }
    	   else if(baseParsedStrings[i].contains("<LOCATIONX>")){
	    	   baseSubString = baseParsedStrings[i].substring(11, baseParsedStrings[i].lastIndexOf("</LOCATIONX>"));
	    	   tempStrings = baseSubString.split(",");
	    	   for(int j=0; j<tempStrings.length; j++){
	    		   tempBase._location[j].x = Integer.decode(tempStrings[j]);
	    	   }

    	   }
    	   else if(baseParsedStrings[i].contains("<LOCATIONY>")){
	    	   baseSubString = baseParsedStrings[i].substring(11, baseParsedStrings[i].lastIndexOf("</LOCATIONY>"));
	    	   tempStrings = baseSubString.split(",");
	    	   for(int j=0; j<tempStrings.length; j++){
	    		   tempBase._location[j].y = Integer.decode(tempStrings[j]);
	    	   }
    	   }
    	   else if(baseParsedStrings[i].contains("<SIDE>")){
	    	   baseSubString = baseParsedStrings[i].substring(6, baseParsedStrings[i].lastIndexOf("</SIDE>"));
	    	   tempBase._startingSide = baseSubString.charAt(0);	    	   
    	   }
       }

	   return tempBase;
   }
}


