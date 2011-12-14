import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HowToPlayMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	public HowToPlayMenu(String name){
		super(name);
		setLocation(450,250);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));		
		setContentPane(panel);
		
		JLabel j = new JLabel("How To Play");
		j.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		panel.add(j);
		panel.add(Box.createHorizontalStrut(100));
		
		//panel.add(new JTextField("This is where the How To Play info will go."));
				
		try{
			FileInputStream fstream = new FileInputStream("README.txt");//Could use a HowToPlay textfile.
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String total = "";
			JTextPane helpText = new JTextPane();
			JScrollPane slider = new JScrollPane(helpText);

			while ((strLine = br.readLine()) != null)   
				total+=strLine+'\n';

			helpText.setText(total);
			panel.add(slider);
		}catch(Exception e){ System.err.println("Error: " + e.getMessage()); }*/
		
		ButtonHandler handler = new ButtonHandler();
		JButton back = new JButton("Back");
		back.setMaximumSize(new Dimension(150,50));
		back.setPreferredSize(new Dimension(100,50));
		back.addActionListener(handler);
		panel.add(back);
		
		panel.add(Box.createGlue());	
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		
	}
	
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){					
			dispose();
		}
	}
}
