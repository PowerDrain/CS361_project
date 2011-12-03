import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton buttons[];             
	private final String buttonNames[] = {"Quit Game", "Save and Quit Game", "Help Menu", "Resume Game"};
	private ButtonHandler handler;
	public GameMenu(String name){		
		super(name);
		this.setLocation(350, 100);
		buttons = new JButton[buttonNames.length];
		handler = new ButtonHandler();
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));		
		setContentPane(controls);

		JLabel j = new JLabel("Game Menu");
		j.setFont(new Font("", Font.CENTER_BASELINE, 30));

		controls.add(j);
		controls.add(Box.createHorizontalStrut(100));
		controls.add(Box.createVerticalStrut(-50));	
		
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
		setSize(330, 400);
		setResizable(false);		
	}//End of Constructor

	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){		
			setLocation(350, 100);
			if(event.getActionCommand() == "Quit Game"){				
				JOptionPane.showMessageDialog(GameMenu.this, "Game Is Over");	
				System.exit(0);
			}	else if(event.getActionCommand() == "Resume Game"){
				JOptionPane.showMessageDialog(GameMenu.this, "Game Is Resumed");
				dispose();
			}	else if(event.getActionCommand() == "Save and Quit Game"){
				JOptionPane.showMessageDialog(GameMenu.this, "Game Is Saved");
				System.exit(0);
			}	else if(event.getActionCommand() == "Help Menu")				
				new HelpMenu("Help Menu");
		}//End of actionPerformed
	}//End of Class
}//End of Class
