/*
 * SquareCoordinate.java
 * Michael Mattson - cs361
 * 
 * Modification - 11/30/2011
 * Description:
 * 	Represents the center of Square which lies in a grid made up of squares.
 */


import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

public class SquareCoordinate implements Serializable{
	private final int _x;
	private final int _y;
	
	public SquareCoordinate(int x, int y){
		_x = x;
		_y = y;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}
	
	@Override
	public boolean equals(Object guest){
		if(!(guest instanceof SquareCoordinate)){
			return false;
		}else if(((SquareCoordinate)guest).getX() == this.getX() && 
				((SquareCoordinate)guest).getY() == this.getY()){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		String coordinate = "";
		coordinate += "(" + this.getX() +"," + this.getY() + ")";
		return coordinate;
	}
	
	/**
	 * Creates a SquareCoordinate specified by the given String
	 * @param s
	 * @return SquareCoordinate specified by s
	 * @throws FormatException
	 */
	public static SquareCoordinate fromString(String s) throws FormatException{
		int index = s.indexOf('(');
		int index2 = s.indexOf(')');
		if(index == -1 || index2 == -1) throw new FormatException("String is not formatted properly to be converted to SquareCoordinate1.");
		int x = 0;
		int y = 0;
		try{
			String sub1 = s.substring(s.indexOf('(')+1,s.indexOf(','));
			String sub2 = s.substring(s.indexOf(',')+1,s.indexOf(')'));
			x = Integer.parseInt(sub1);
			y = Integer.parseInt(sub2);
		}
		catch(NumberFormatException e){
			System.out.println("String is not formatted properly to be converted to SquareCoordinate.");
		}
		return new SquareCoordinate(x,y);
	}
	
	/**
	 * Returns the closest SquareCoordinate to the given Point P.In the case of two
	 * equally close SquareCoordinates either may be returned.
	 * @param p
	 * @param width width of grid must not be non-negative or zero
	 * @return SquareCoordinate closest to given Point p
	 */
	public static SquareCoordinate fromPoint(Point p, int width){
		double px = p.getX()/ (.5*width);
		double py = p.getY()/ (.5*width);
		
		int xc = (int)Math.floor(px);
		int yc = (int)Math.floor(py);
		if(Math.abs(xc)%2 == 0){
			if(xc<0){
				--xc;
			}else{
				++xc;
			}
		}
		if(Math.abs(yc)%2 == 0){
			if(yc<0){
				--yc;
			}else{
				++yc;
			}
		}
		
		double x = xc * (.5*width);
		double y = yc * (.5*width);
		x/=(double)width;
		y/=(double)width;
		return new SquareCoordinate((int)x,(int)y);
	}
	
	/**
	 * Returns the (x,y) center of the Square at this coordinate
	 * @param width
	 * @return a Point containing the (x,y) center of the polygon
	 */
	public Point toPoint(int width){
		int x = (int)Math.round(this.toPointX(width));
		int y = (int)Math.round(this.toPointY(width));
		return new Point(x,y);
	}
	
	/**
	 * return calling SquareCoordinates center x coordinate
	 * @param width
	 * @return center x coordinate
	 */
	public double toPointX(int width){
		double x = this.getX() * width;
		return x;
	}
	
	/**
	 * Return calling SquareCoordinates center y coordinate
	 * @param width
	 * @return center y coordinate
	 */
	public double toPointY(int width){
		double y = this.getY() * width;
		return y;
	}
		
	/**
	 * Creates a square of the given width, with it's center located at this SquareCoordinate
	 * @param width
	 * @return Square(Polygon) centered at this SquareCoordinate
	 */
	public Polygon toPolygon(int width){
		int[] xCoordinates = new int[4];
		int[] yCoordinates = new int[4];
		double cX = 0;
		double cY = 0;
		cX = this.toPointX(width);
		cY = this.toPointY(width);
		xCoordinates[0] = (int)Math.round(cX - .5*width);
		xCoordinates[1] = (int)Math.round(cX + .5*width);
		xCoordinates[2] = (int)Math.round(cX + .5*width);
		xCoordinates[3] = (int)Math.round(cX - .5*width);
		
		yCoordinates[0] = (int)Math.round(cY + .5*width);
		yCoordinates[1] = (int)Math.round(cY + .5*width);
		yCoordinates[2] = (int)Math.round(cY - .5*width);
		yCoordinates[3] = (int)Math.round(cY - .5*width);
		
		return new Polygon(xCoordinates, yCoordinates, 4);
	}
}
