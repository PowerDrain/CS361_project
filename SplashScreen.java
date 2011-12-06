import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SplashScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton buttons[];
	private final String buttonNames[] = {"New Game", "Load Game", "How To Play"};
	private ButtonHandler handler;
	
	public SplashScreen(String name){
		super(name);
		this.setLocation(200, 50);
		buttons = new JButton[buttonNames.length];
		handler = new ButtonHandler();

		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		setContentPane(controls);
		controls.setBackground(Color.black);
		
		ImageIcon icon = new ImageIcon("Naval_Whoop_Ass.JPG");
		//Trying to package into an external jar
		//ImageIcon icon = new ImageIcon(getClass().getResource("Naval_Whoop_Ass.JPG"));
		JLabel image = new JLabel();
		image.setIcon(icon);
		JLabel j = new JLabel("");
		j.setFont(new Font("", Font.CENTER_BASELINE, 30));

		controls.add(j);
		controls.add(Box.createHorizontalStrut(100));
		controls.add(Box.createVerticalStrut(-50));
		controls.add(image);
		for(int i = 0; i < buttonNames.length; ++i) {
			controls.add(Box.createGlue());
			buttons[i] = new JButton(buttonNames[i]);
			buttons[i].setPreferredSize(new Dimension(100,50));
			buttons[i].setMaximumSize(new Dimension(175,50));
			buttons[i].addActionListener(handler);
			controls.add(buttons[i]);
		}//End of for

		controls.add(Box.createGlue());
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 700);
		setResizable(false);
	}//End of Constructor

	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			setLocation(200, 50);
			if(event.getActionCommand() == "New Game"){
				JOptionPane.showMessageDialog(SplashScreen.this, "Starting Game. . .");
				new GameUI();
			} else if(event.getActionCommand() == "Load Game"){
				JOptionPane.showMessageDialog(SplashScreen.this, "Loading Game. . .");
				dispose();
			} else if(event.getActionCommand() == "How To Play"){
				new HowToPlayMenu("How To Play");
			}
		}//End of actionPerformed
	}//End of Class
}//End of Class

