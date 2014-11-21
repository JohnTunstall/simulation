/**
 * @(#)PhysicsSimulationRunner.java
 *
 *
 * @author 
 * @version 1.00 2014/5/26
 */
import java.awt.*;
import javax.swing.*;

public class PhysicsSimulationRunner 
{

	public static void main(String args[]) 
	{
		JFrame frame = new JFrame();
		frame.setSize(1000,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	SimulationManager physics = new SimulationManager();
    	frame.add(physics);
    	frame.setTitle("PHYSICS SIMULATOR 2K14");
    	frame.setVisible(true);
    		
    	
    }
    
    
}