import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HelpMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	public HelpMenu(String name){
		super(name);
		setLocation(380,150);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));		
		setContentPane(panel);
		
		JLabel j = new JLabel("Help Menu");
		j.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		panel.add(j);
		panel.add(Box.createHorizontalStrut(100));
		panel.add(Box.createVerticalStrut(-50));
		
		panel.add(new JTextField("This is the Help Menu!"));
		
		ButtonHandler handler = new ButtonHandler();
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(100,35));
		back.addActionListener(handler);
		panel.add(back);
		
		panel.add(Box.createGlue());	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setResizable(false);
		
	}//End of Constructor
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){					
			dispose();
		}//End of actionPerformed
	}//End of Class
}//End of HelpMenu
