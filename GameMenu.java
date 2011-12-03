import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton buttons[];             
	private final String buttonNames[] = {"Quit Game", "Save and Quit Game", "Help Menu", "Resume Game"};
	public GameMenu(String name){		
		super(name);
		this.setLocation(350, 100);
		buttons = new JButton[buttonNames.length];

		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));		
		setContentPane(controls);

		JLabel j = new JLabel("Game Menu");
		j.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));

		controls.add(j);
		controls.add(Box.createHorizontalStrut(100));
		controls.add(Box.createVerticalStrut(-50));	

		ButtonHandler handler = new ButtonHandler();
		for(int i = 0; i < buttonNames.length; ++i) {		
			controls.add(Box.createGlue());
			buttons[i] = new JButton(buttonNames[i]);
			buttons[i].setPreferredSize(new Dimension(100,35));
			buttons[i].addActionListener(handler);
			controls.add(buttons[i]);		
		}//End of for
		controls.add(Box.createGlue());	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 400);
		setResizable(false);		
	}//End of Constructor

	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){		
			setLocation(350, 100);
			if(event.getActionCommand() == "Quit Game")
				System.out.println(String.format("This is the " + event.getActionCommand() + " Button"));
			if(event.getActionCommand() == "Help Menu")				
				new HelpMenu("Help Menu");
			if(event.getActionCommand() == "Resume Game")
				//GameInterface.;
				JOptionPane.showMessageDialog(GameMenu.this, "Game Is Resumed");
			if(event.getActionCommand() == "Save and Quit Game")
				JOptionPane.showMessageDialog(GameMenu.this, "Game Is Saved");
			dispose();
		}//End of actionPerformed
	}//End of Class
}//End of Class
