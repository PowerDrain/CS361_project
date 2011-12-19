/*
 * GameUI.java
 * Michael Mattson, David Gamez, Alton Yee  - cs361
 * 
 * Modification:
 * 	12/19/2011
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
import java.util.Random;

public class GameUI extends JFrame {
	private Turn gameTurn;
	private Map myMap;
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 800;
	private static boolean canMoveShip = false;
	private static boolean canFireGun = false;
	private static boolean canRotateShip = false;
	private final String layoutFile = chooseFileRandomly();
	private static SquareCoordinate selected = null;
	private boolean playerMovingShip = false;
	private boolean playerRotatingShip = false;
	private boolean playerShootingGun = false;

	public GameUI(){
		setTitle("Naval WhoopAss");
		gameTurn = new Turn(layoutFile);
		myMap = gameTurn.getMap(); 
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		gameTurn.serialize("mygamefile");
		gameTurn = Turn.unserialize("mygamefile");
		createContents();
		setVisible(true);
	}
	
	/**
	 * Randomly selects one of the map layout files
	 * @return name of file selected
	 */
	public String chooseFileRandomly(){
		Double randomNumber = Math.random();
		if(randomNumber>=0 && randomNumber<0.3){
			return "ReefLayout1.txt";
		}else if(randomNumber <0.6){
			return "ReefLayout2.txt";
		}else{
			return "ReefLayout3.txt";
		}
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
		final JTextField turnInfo = new JTextField(gameTurn.getTurnNumber() + "/80");
		
		//setup mine display;
		final JTextField mineInfo = new JTextField("10");
		
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
					//TODO add part about moving ships, rotating ships and shooting guns.
					if (playerMovingShip){
						String[] results;
						results = gameTurn.moveShip(new Point(selected.getX() - 1, selected.getY() - 1));
						playerMovingShip = false;
						System.out.println(results[1]);
						repaint();
					} else if (playerRotatingShip){
						
					} else if (playerShootingGun){
						String[] results;
						results = gameTurn.shootGun(new Point(selected.getX() - 1, selected.getY() - 1));
						playerShootingGun = false;
						System.out.println(results[1]);
						repaint();
					}
					repaint();
				}
			}
		});
		
		map.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					Player currentPlayer = myMap.getCurrentPlayer();
					SquareCoordinate sc = SquareCoordinate.fromPoint(new Point(e.getX(),e.getY()), SquareTile.WIDTH);
					int myX = sc.getX();
					int myY = sc.getY();
					if(myX <=30 && myX > 0 && myY > 0 && myY<=30){
						SquareTile mytile = myMap._map[myX-1][myY-1];
						System.out.println(mytile.toString());
						if(mytile.tileOwnerIsShip()){
							Ship selectedShip = (Ship)mytile.getTileOwner();
							if(currentPlayer.isPlayersShip(selectedShip)){
								myMap.setCurrentShip(selectedShip);
								gameTurn.getCurrentPlayer().setCurrentShip(selectedShip);
							}
						}
					}
				}	
			}
		});
				
		// setup report window
		final TextArea reportWindow = new TextArea("Welcome to Naval WhoopAss");
		reportWindow.setEditable(false);

		// setup buttons
		//TODO
		JButton shootGun = new JButton("Shoot Gun");
		shootGun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String consoleText = reportWindow.getText();
				consoleText+="\nPick a square to shoot!";
				reportWindow.setText(consoleText);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				playerShootingGun = true;
				canFireGun = true;
				repaint();
			}
		});
		
		JButton launchTorpedo = new JButton("Launch Torpedo");
		launchTorpedo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String[] results = gameTurn.launchTorpedo();
				String consoleText = reportWindow.getText();
				consoleText+="\n" + results[1];
				reportWindow.setText(consoleText);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				repaint();
			}
		});
		
		//TODO verify that setting in the map, red tile is correct.
		JButton deployMine = new JButton("Deploy Mine");
		deployMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Point selectedPoint = new Point(selected.getX()-1,selected.getY()-1);
				String[] results = gameTurn.immerseMine(selectedPoint);
				String consoleText = reportWindow.getText();
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				mineInfo.setText("" + gameTurn.getMineCount());
				if (results[0] != null){
					myMap.setTile(selectedPoint, Occupant.MINE, null);
				}
				/*Original Code
				 * if(myMap.getTile(selectedPoint).getOccupant().equals(Occupant.WATER)){
					myMap.setTile(selectedPoint, Occupant.MINE, null);
					consoleText+="\n";
				}else{
					consoleText+="\nCannot lay mine at selected coordinate.";
				}*/
				consoleText+="\n" + results[1];
				reportWindow.setText(consoleText);
				repaint();
			}
		});
		
		//TODO verify that the tile changes are correct.
		JButton retrieveMine = new JButton("Retrieve Mine");
		retrieveMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Point selectedPoint = new Point(selected.getX()-1,selected.getY()-1);
				String[] result = gameTurn.withdrawMine(selectedPoint);
				String consoleText = reportWindow.getText();
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				mineInfo.setText("" + gameTurn.getMineCount());
				if (result[0] != null){
					myMap.setTile(selectedPoint, Occupant.WATER, null);
				consoleText+="\n" + result[1];
				reportWindow.setText(consoleText);
				repaint();
				}
			}
		});
		
		//TODO
		JButton moveShip = new JButton("Move Ship");
		moveShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				playerMovingShip = true;
				canMoveShip = true;
				repaint();
			}
		});
		
		//TODO
		JButton rotateShip = new JButton("Rotate Ship");
		rotateShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = reportWindow.getText();
				s+="\nShip Rotated";
				reportWindow.setText(s);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				canRotateShip = true;
				repaint();
			}
		});
		
		JButton repairShip = new JButton("Repair Ship");
		repairShip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String[] results = gameTurn.repairShip();
				String consoleText = reportWindow.getText();
				consoleText+="\n" +results[1];
				reportWindow.setText(consoleText);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				repaint();
			}
		});
		
		JButton repairBase = new JButton("Repair Base");
		repairBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String[] results;
				String consoleText = reportWindow.getText();
				results = gameTurn.repairBase();
				consoleText+="\n" + results[1];
				reportWindow.setText(consoleText);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
				repaint();
			}
		});
		
		JButton passTurn = new JButton("Pass Turn");
		passTurn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String[] results;
				results = gameTurn.passTurn();
				String sconsoleText = reportWindow.getText();
				sconsoleText+="\n" + results[1];
				reportWindow.setText(sconsoleText);
				turnInfo.setText(gameTurn.getTurnNumber() + "/80");
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
		infoPane.add(mineInfo);
		infoPane.add(new JTextField("128"));
		infoPane.add(turnInfo);
		infoPane.add(myCoord);
		
		// add components to content pane
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
			myMap.draw(g);
			//myMap.drawAll(g);
			if(selected != null){
				g.setColor(Color.magenta);
				g.drawPolygon(selected.toPolygon(SquareTile.WIDTH));
			}
			if(canMoveShip){
				myMap.drawMobility(g);
				canMoveShip = false;
			}
			if(canFireGun){
				myMap.drawGunRange(g);
				canFireGun = false;
			}
			if(canRotateShip){
				myMap.drawRotationalMobility(g);
				canRotateShip = false;
			}
			myMap.drawCurrentShip(g);
		}
	}

	/*public static void main(String[] args){
		new GameUI();
	}*/
}