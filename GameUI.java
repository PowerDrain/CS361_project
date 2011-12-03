/*
 * GameUI.java
 * Michael Mattson - cs361
 * 
 * Modification:
 * 	11/30/2011
 * Description:
 * 	Graphic user interface for battleship game
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class GameUI extends JFrame {
	private Map myMap;
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 800;
	
	public GameUI(){
		setTitle("Naval WhooopAsssss");
		//using default constructor that initializes map to be all water 
		// because specifying constructor was not working properly
		myMap = new Map();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);
	}
	
	public void createContents(){
		//setup content pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		//setup map
		SquarePanel map = new SquarePanel();
		
		// setup report window
		final TextArea reportWindow = new TextArea("Welcome to Naval WhoopAsss");
		reportWindow.setEditable(false);
		
		// setup buttons
		JButton shootGun = new JButton("Shoot Gun");
		shootGun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nGun Fired";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton launchTorpedo = new JButton("Launch Torpedo");
		launchTorpedo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nTorpedo Launched";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton deployMine = new JButton("Deploy Mine");
		deployMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nMine Deployed";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton retrieveMine = new JButton("Retrieve Mine");
		retrieveMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nMine Retrieved";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton moveShip = new JButton("Move Ship");
		moveShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Moved";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton rotateShip = new JButton("Rotate Ship");
		rotateShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Rotated";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton repairShip = new JButton("Repair Ship");
		repairShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Repaired";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton repairBase = new JButton("Repair Base");
		repairBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nBase Repaired";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		JButton passTurn = new JButton("Pass Turn");
		passTurn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\npassTurn";
				reportWindow.setText(s);
				repaint();
			}
		});
		
		// setup button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(9,1));
		buttonPanel.add(shootGun);
		buttonPanel.add(launchTorpedo);
		buttonPanel.add(deployMine);
		buttonPanel.add(retrieveMine);
		buttonPanel.add(moveShip);
		buttonPanel.add(rotateShip);
		buttonPanel.add(repairShip);
		buttonPanel.add(repairBase);
		buttonPanel.add(passTurn);
		
		
				
		// setup second content pane to hold button panel and report window
		JPanel contentPane2 = new JPanel();
		contentPane2.setLayout(new GridLayout(1,2));
		contentPane2.setPreferredSize(new Dimension(400,800));
		contentPane2.add(buttonPanel);
		contentPane2.add(reportWindow);

		
		
		
		//setup infoPane
		JPanel infoPane = new JPanel(new GridLayout(2,5));
		JLabel player1 = new JLabel("Player 1");
		player1.setHorizontalAlignment(JLabel.CENTER);
		infoPane.add(player1);
		JLabel mineCount = new JLabel("Mine Count");
		mineCount.setHorizontalAlignment(JLabel.CENTER);
		infoPane.add(mineCount);
		JLabel player2 = new JLabel("Player 2");
		player2.setHorizontalAlignment(JLabel.CENTER);
		infoPane.add(player2);
		JLabel turn = new JLabel("Turn");
		turn.setHorizontalAlignment(JLabel.CENTER);
		infoPane.add(turn);
		JButton menu = new JButton("Menu");
		menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new GameMenu("Game Menu");			
		}});
		infoPane.add(menu);
		JTextField player1Score = new JTextField("128");
		infoPane.add(player1Score);
		infoPane.add(new JTextField("10"));
		infoPane.add(new JTextField("128"));
		infoPane.add(new JTextField("41/80"));
		
		// add components to content pane
		//contentPane.add(infoPane, BorderLayout.NORTH);
		//contentPane.add(contentPane2, BorderLayout.EAST);
		contentPane.add(map, BorderLayout.CENTER);
		contentPane.add(contentPane2, BorderLayout.EAST);
		contentPane.add(infoPane, BorderLayout.NORTH);
		this.setContentPane(contentPane);

	}
	
	private final class SquarePanel extends JPanel{
		/**
		 * Keep Eclipse happy
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); // render background
			myMap.drawAll(g);
			
		}
	}
	
	


	public static void main(String[] args){
		new GameUI();
	}
}