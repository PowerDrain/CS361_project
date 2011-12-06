/*
 * GameUI.java
 * Michael Mattson, Alton Yee  - cs361
 * 
 * Modification:
 * 	12/4/2011
 * Description:
 * 	Graphic user interface for battleship game
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private static int myTurn = 0;
	public GameUI(){
		setTitle("Naval WhooopAsssss");
		//using default constructor that initializes map based on a .txt file
		myMap = new Map("defaultLayout.txt");
		myMap.printMap();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);
	}
	
	/**
	 * Returns the currently selected map coordinate in a formatted string form
	 * @return String containing currently selected coordinate
	 */
	private String getSelectedCoordinate(){
		if(selected == null){
			return "No coordinate selected.";
		}else{
			return "Selected Coordinate: " + ((int)selected.getX()-1) + ", " + ((int)selected.getY()-1);
		}
	}
	
	public void createContents(){
		//setup content pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		//setup map
		final SquarePanel map = new SquarePanel();
		
		// setup turn display;
		final JTextField turnInfo = new JTextField(myTurn + "/80");
		
		//setup coordinate display
		final JTextField myCoord = new JTextField(getSelectedCoordinate());
		
		// setup map listener
		map.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				SquareCoordinate sc = SquareCoordinate.fromPoint(new Point(e.getX(),e.getY()), SquareTile.WIDTH);
				System.out.println(sc.toString());
				int myX = sc.getX();
				int myY = sc.getY();
				if(myX <=30 && myX > 0 && myY > 0 && myY<=30){
					SquareTile mytile = myMap._map[myX-1][myY-1];
					System.out.println(mytile.toString());
					selected = new SquareCoordinate(myX,myY);
					myCoord.setText(getSelectedCoordinate());
					repaint();
				}
			}
		});
		
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
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton launchTorpedo = new JButton("Launch Torpedo");
		launchTorpedo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nTorpedo Launched";
				reportWindow.setText(s);
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton deployMine = new JButton("Deploy Mine");
		deployMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nMine Deployed";
				turnInfo.setText(++myTurn + "/80");
				Point selectedPoint = new Point(selected.getX()-1,selected.getY()-1);
				if(myMap.getTile(selectedPoint).getOccupant().equals(Occupant.WATER)){
					myMap.setTile(selectedPoint, Occupant.MINE, null);
				}else{
					s+="\nCannot lay mine at selected coordinate.";
				}
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
				turnInfo.setText(++myTurn + "/80");
				Point selectedPoint = new Point(selected.getX()-1,selected.getY()-1);
				if(myMap.getTile(selectedPoint).getOccupant().equals(Occupant.MINE)){
					myMap.setTile(selectedPoint, Occupant.WATER, null);
				}else{
					s+="\nNo mine to retrieve at selected coordinate.";
				}
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
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton rotateShip = new JButton("Rotate Ship");
		rotateShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Rotated";
				reportWindow.setText(s);
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton repairShip = new JButton("Repair Ship");
		repairShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Repaired";
				reportWindow.setText(s);
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton repairBase = new JButton("Repair Base");
		repairBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nBase Repaired";
				reportWindow.setText(s);
				turnInfo.setText(++myTurn + "/80");
				repaint();
			}
		});
		
		JButton passTurn = new JButton("Pass Turn");
		passTurn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\npassTurn";
				reportWindow.setText(s);
				turnInfo.setText(++myTurn + "/80");
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
		infoPane.add(turnInfo);
		infoPane.add(myCoord);
		
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
			if(selected != null){
				g.setColor(Color.magenta);
				g.drawPolygon(selected.toPolygon(SquareTile.WIDTH));
			}
			
		}
	}
	
	


	private static SquareCoordinate selected = null;

	public static void main(String[] args){
		new GameUI();
	}
}