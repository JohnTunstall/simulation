
import java.awt.*;

public class Ball 
{
	public static final int PIXELS_PER_METER = 20;
	public double x;
	public double y;
	public double angleRad;
	public double dy;
	public double dx;
	public double time;
	public double r;
	public double v;
	
    public Ball(double xx, double yy, double theta, double velocity, double radius) 
    {
    	x=xx;
    	y=yy;
    	angleRad = theta;
    	dy = -1*Math.sin(Math.toRadians(angleRad))*velocity;
    	dx = Math.cos(Math.toRadians(angleRad))*velocity;
    	time = 0;
    	r = radius;
    	v = velocity;
    }
    public boolean move(Ball b)
    {
    	
    	/// for electrostatic forces
//    	double yDiff = b.y-y;
//    	double xDiff = b.x-x;
//    	double radius = Math.sqrt(Math.pow(xDiff,2)+Math.pow(yDiff,2));
//    	double phi =  Math.atan(Math.abs(yDiff/xDiff));
//    	
//    	
//    	if(radius!=0)
//    	{
//    		radius = 1/Math.pow(radius,2);
//    	}
//    	radius *= .001*8.9875517873681764*Math.pow(10,9); 
//    	double yComp = 0;
//    	double xComp = 0;
//    	///1st quad
//		if(yDiff>0 && xDiff>0)
//		{
//			yComp = radius*Math.sin(phi);
//			xComp = radius*Math.cos(phi); 
//		}
//    	///2nd quad
//    	if(yDiff>0 && xDiff<0)
//    	{
//    		yComp = radius* Math.sin(phi);
//    		xComp = -radius*Math.cos(phi);
//    	}
//    	///3rd quad
//    	if(yDiff<0 && xDiff<0)
//    	{
//    		yComp = -radius* Math.sin(phi);
//    		xComp = -radius*Math.cos(phi);
//    	}
//    	///4th quad	
//    	if(yDiff<0 && xDiff>0)
//    	{
//    		yComp = -radius* Math.sin(phi);
//    		xComp = radius*Math.cos(phi);
//    	}
//    	if(yDiff==0)
//    		xComp = xDiff;
//    	else if(xDiff==0)
//    		yComp =;
    	boolean collide = false;
    	x+=dx/100;
    	if(b.getRect().intersects(getRect()))
    		collide = true;
    	y+=dy/100;
    	if(b.getRect().intersects(getRect()))
    		collide = true;
    	if(y>=480)
    	{
    		y=480;
    		dy=0;
    		if(dx>0)
    		{
    			dx-=3;
    			if(dx<0)
    				dx=0;
    		}
    		else if(dx<0)
    		{
    			dx+=3;
    			if(dx>0)
    				dx=0;
    		}
    		
    	}
    	v = Math.sqrt(dy*dy+dx*dx);
//    	if(dx<0 ^ dy<0)
//    		v*=-1;
    	angleRad = Math.atan(Math.abs(dy/dx));
    	angleRad = Math.toDegrees(angleRad);
    	if(dy>0 && dx>0)
    		angleRad = 360 - angleRad;
    	else if(dy>0 && dx<0)
    		angleRad+=180;
    	else if(dy<0 && dx<0)
    		angleRad= 180 - angleRad;
    		
    	return collide;
    }
    public Rectangle getRect()
    {
    	double newR = r*Math.cos(Math.toRadians(45));
    	return new Rectangle( (int)(x+r-newR) , (int)(y+r-newR) , (int)(newR*2) , (int)(newR*2) );
    }
    public void draw(Graphics g)
    {
    	g.setColor(Color.RED);
    	g.fillOval((int)x,(int)y,(int)(r*2),(int)(r*2));
    }
    
    
}