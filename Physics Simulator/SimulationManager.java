/**
 * Yak and Hanky Technologies
 * Copyright 5/26/2014
 * we will sue
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class SimulationManager extends JPanel implements ActionListener 
{
    public Timer timer;
	public Ball ball1,ball2;
	public boolean canType,message;
	public String[][] values;
	public static final int NUM_COLS = 2;
	public static final int NUM_ROWS = 4;
	public double v1,v2,x1,x2,y1,y2,a1,a2;
	public int row,col;
	public int counter;
	public int countToFity;
	public double time;
	public double time1,time2;
	
    public SimulationManager() 
    {

    	canType = true;
    	newValues();
    	counter = -1;
    	timer = new Timer(10, this);
        timer.start();
        setVisible(true);
        setFocusable(true);
        addKeyListener(new SimKeyListener());
    }
    public void newValues()
    {

    	v1 = Math.random()*70+70;
    	v2 = Math.random()*70+70;
    	x1 = Math.random()*100;
    	x2 = Math.random()*100+800;
    	a1 = Math.random()*70+10;
    	a2 = Math.random()*70+100;
    	y1 = y2 = 0;
    	String velocity1 = new String("" + v1).substring(0,5);
    	String velocity2 = new String("" + v2).substring(0,5);
    	String sx1 = new String("" + x1).substring(0,5);
    	String sx2 = new String("" + x2).substring(0,5);
    	String angle1 = new String("" + a1).substring(0,5);
    	String angle2 = new String("" + a2).substring(0,5);
    	row = 0; col = 0;
       	values = new String[][]{{sx1,sx2},{""+y2,""+y1},{angle1,angle2},{velocity1,velocity2}};
       	counter = -1;
    	
    }
    public void resetBalls()
    {
        ball1 = ball2 = null;
    	canType = true;
    	String velocity1 = String.format("%5.3f",v1);
    	String velocity2 = String.format("%5.3f",v2);
    	String sx1 = String.format("%5.3f",x1);
    	String sx2 = String.format("%5.3f",x2);
    	String angle1 = String.format("%5.3f",a1);
    	String angle2 = String.format("%5.3f",a2);
    	row = 0; col = 0;
       	values = new String[][]{{sx1,sx2},{""+y1,""+y2},{angle1,angle2},{velocity1,velocity2}};
       	counter = -1;
    }
    public void actionPerformed(ActionEvent e)
    {
    	
    	if(!canType)
    	{
    		if(counter==-1)
    			time += .01;
    		if(counter<-1)
    		{
    			counter-=1;
    			repaint();
    			return;
    		}
    		if(counter<-20)
    			counter=-2;
    		else if(counter>-1)
    			counter++;
    		else if(ball1.move(ball2) || ball2.move(ball1))
    		{
    			counter--;
    		}
    		if(ball2.dx==0 && ball2.dy==0)
    		{
    			if(time2==0)
    				time2 = time;
    		}
    		if(ball1.dx==0 && ball1.dy==0)
    		{
    			if(time1==0)
    				time1 = time;
    		}
    		if(ball1.dx==0 && ball2.dx==0 && ball1.dy==0 && ball2.dy==0)
    		{
    			counter++;
    		}
    		else if(countToFity==50)
			{
				countToFity=0;
				values[0][0] = String.format("%5.3f",ball1.x);
				values[1][0] = String.format("%5.3f",(ball1.y-480)*-1);
				values[2][0] = String.format("%5.3f",ball1.angleRad);
				values[3][0] = String.format("%5.3f",ball1.v);
				values[0][1] = String.format("%5.3f",ball2.x);
				values[1][1] = String.format("%5.3f",(ball2.y-480)*-1);
				values[2][1] = String.format("%5.3f",ball2.angleRad);
				values[3][1] = String.format("%5.3f",ball2.v);
			}
    		countToFity++;
    	}
    	repaint();
    }
    public void paintComponent(Graphics g)
    {
    	g.setColor(Color.CYAN);
    	g.fillRect(0,0,1000,600);
    	g.setColor(Color.GREEN);
    	g.fillRect(0,500,1000,100);
    	g.setColor(Color.BLACK);
    	g.drawLine(0,500,1000,500);
    	g.drawString("Time : "+ String.format("%5.3f",time),600,300);
    	if(time1!=0)
    		g.drawString("Time1 : "+time1,600,330);
    	if(time2!=0)
    		g.drawString("Time2 :"+time2,600,360);
    	g.setFont(new Font("Courier New",Font.PLAIN,25));
    	g.drawString("Ball 1",200,25);
    	g.drawString("Ball 2",400,25);
    	g.drawString("x |",110,50);
    	g.drawString("y |",110,75);
    	g.drawString("angle |",50,100);
    	g.drawString("velocity |",5,125);
    	for(int i = 0; i<4;i++)
    	{
    		if(i==row && col == 0)
    			g.setColor(Color.MAGENTA);
    		else
    			g.setColor(Color.BLACK);
    		g.drawString(values[i][0],150,50+i*25);
    	}
    	for(int i = 0; i<4;i++)
    	{
    		if(i==row && col == 1)
    			g.setColor(Color.ORANGE);
    		else
    			g.setColor(Color.BLACK);
    		g.drawString("|"+values[i][1],310,50+i*25);
    	}

    	if(ball1!=null)
    	{
    		ball1.draw(g);
    		ball2.draw(g);
    	}
    	if(counter<-10)
    	{
    		g.setFont(new Font("Courier New",Font.BOLD,50));
    		g.drawString("SUCCESS!!!",400,450);
    	}
    	else if(counter>-1)
    		g.drawString("simulation failed......press spacebar to reset",300,400);
    }
    class SimKeyListener extends KeyAdapter
    {
        public void keyPressed(KeyEvent e) 
        {
            int keyCode = e.getKeyCode();
            if(keyCode == KeyEvent.VK_ENTER && canType)
            {
            	 canType = false;
                 try
                 {
                 	 v1 = Double.parseDouble(values[3][0]);
	                 v2 = Double.parseDouble(values[3][1]);
	                 x1 = Double.parseDouble(values[0][0]);
	                 x2 = Double.parseDouble(values[0][1]);
	                 a1 = Double.parseDouble(values[2][0]);
	                 a2 = Double.parseDouble(values[2][1]);
	                 y1 = Double.parseDouble(values[1][0]);
	                 y2 = Double.parseDouble(values[1][1]);
                 }
                 catch(Exception exception)
                 {
                 	canType = true;
                 }
                 if(!canType)
                 {
                 	ball1 = new Ball(x1,y1*-1+480,a1,v1,10);
    				ball2 = new Ball(x2,y2*-1+480,a2,v2,10);
                 }
                 countToFity = 0;
                 time = 0;
                 time1=0;
                 time2=0;
                 return;
                 
            }
            else if(keyCode == KeyEvent.VK_SPACE)
            {
            	ball1 = ball2 = null;
            	canType = true;
        		newValues();
        		return;
            }
            else if(keyCode == KeyEvent.VK_R)
            {
				resetBalls();
		       	return;
            }
            else if(!canType)
            {
            	return;
            }
            else if(keyCode == KeyEvent.VK_LEFT)
            {
            	col^=1;
            }
            else if(keyCode == KeyEvent.VK_RIGHT)
            {
            	col^=1;
            }
            else if(keyCode == KeyEvent.VK_UP)
            {
            	row--;
            	if(row<0)
            		row = 3;
            	return;
            }
            else if(keyCode == KeyEvent.VK_DOWN)
            {
            	row++;
            	if(row>3)
            		row = 0;
            	return;
            }
            else if(keyCode == KeyEvent.VK_BACK_SPACE)
            {
            	if(values[row][col].length()>0)
            		values[row][col] = values[row][col].substring(0,values[row][col].length()-1);
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_DECIMAL)
            {
            	if(values[row][col].contains("."))
            		return;
            	if(values[row][col].length()<9)// no point if less than ten
            		values[row][col]+=".";
            	else
            		return;
            }            
            else if(keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0)
            {
            	if(values[row][col].length()<10)
            		values[row][col]+= "0";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "1";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "2";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "3";
            	else
            		return;	
            }
            else if(keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "4";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "5";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_6 || keyCode == KeyEvent.VK_NUMPAD6)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "6";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "7";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_8 || keyCode == KeyEvent.VK_NUMPAD8)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "8";
            	else
            		return;
            }
            else if(keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9)
            {
            	if(values[row][col].length()<10)
            		values[row][col] += "9";
            	else
            		return;
            }
			try
        	{
        		switch(col)
        		{
        			case 0: 
        				switch(row)
        				{
        					case 0:x1 = Double.parseDouble(values[0][0]);break;
        					case 1:y1 = Double.parseDouble(values[1][0]);break;
        					case 2:a1 = Double.parseDouble(values[2][0]);break;
        					case 3:v1 = Double.parseDouble(values[3][0]);break;
        				}break;
        			case 1:
        				switch(row)
        				{
        					case 0:x2 = Double.parseDouble(values[0][1]);break;
        					case 1:y2 = Double.parseDouble(values[1][1]);break;
        					case 2:a2 = Double.parseDouble(values[2][1]);break;
        					case 3:v2 = Double.parseDouble(values[3][1]);break;
        				}break;
        		}
        	}
        	catch(Exception exception)
        	{
         		
       		}         
        }
    }
}