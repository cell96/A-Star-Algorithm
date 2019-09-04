package org.csc301;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GameBoard {
	static TreasureHunt game = new TreasureHunt();

	public static void main(String[] args) {
		
		final JFrame f = new JFrame("Treasure Hunt");
		JButton N = new JButton("NORTH");
		JButton E = new JButton("EAST");
		JButton W = new JButton("SOUTH"); 
		JButton S = new JButton("WEST");
		JButton sonar = new JButton("SONAR: " + game.sonars);
		
		N.setPreferredSize(new Dimension(300, 200));
		E.setPreferredSize(new Dimension(300, 200));
		S.setPreferredSize(new Dimension(300, 200)); 
		W.setPreferredSize(new Dimension(300, 200));
		sonar.setPreferredSize(new Dimension(300, 200));
		
		N.setFont(N.getFont().deriveFont(50.0f));
		E.setFont(E.getFont().deriveFont(50.0f));
		S.setFont(S.getFont().deriveFont(50.0f));
		W.setFont(W.getFont().deriveFont(50.0f));
		sonar.setFont(sonar.getFont().deriveFont(50.0f));
		
		
		JPanel panel = new JPanel(new GridLayout(game.islands.height, game.islands.width));
		JPanel button = new JPanel(new GridLayout(1,1));
		
		for (int i = 0; i < game.islands.height; i++) {
			for (int j = 0; j < game.islands.width; j++) {
				if (i == game.islands.boat.gridY && j == game.islands.boat.gridX){
					JLabel l = new JLabel(new ImageIcon(GameBoard.class.getResource("boat.jpg")));
					l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		            panel.add(l);
				}
				else if (i == game.islands.treasure.gridY && j == game.islands.treasure.gridX && game.islands.map[i][j].inPath){
					JLabel l = new JLabel(new ImageIcon(GameBoard.class.getResource("treasure.jpg")));
					l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		            l.setOpaque(true);
		            panel.add(l);
				}
				else if (game.islands.map[i][j].inPath){
					JLabel l = new JLabel("*", JLabel.CENTER);
					l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		            l.setOpaque(true);
		            panel.add(l);
				}
				else if (game.islands.map[i][j].walkable){
					JLabel l = new JLabel(new ImageIcon(GameBoard.class.getResource("water.jpg")));

					l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		            l.setOpaque(true);
		            panel.add(l);
				}
				else{
					JLabel l = new JLabel(new ImageIcon(GameBoard.class.getResource("land.jpg")));
					l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		            l.setOpaque(true);
		            panel.add(l);
				}
			}
		}
			button.add(N, BorderLayout.NORTH);
			button.add(W, BorderLayout.EAST);
			button.add(sonar, BorderLayout.SOUTH);
			button.add(S, BorderLayout.SOUTH);
			button.add(E, BorderLayout.WEST);
			if (game.state.equals("OVER")) {
				final JFrame over = new JFrame("Treasure Hunt");
				JLabel o = new JLabel("GAME OVER");
				over.add(o);
				f.setVisible(false);
				f.dispose();
				o.setVisible(true);
			}
			
			
			
			N.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						game.processCommand("GO N");
						main(null);
					} catch (HeapFullException e) {
						e.printStackTrace();
					} catch (HeapEmptyException e) {
						e.printStackTrace();
					}					
				}
			});
			
			E.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						game.processCommand("GO E");
						main(null);
					} catch (HeapFullException e) {
						e.printStackTrace();
					} catch (HeapEmptyException e) {
						e.printStackTrace();
					}					
				}
			});
			
			W.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						game.processCommand("GO W");
						main(null);
					} catch (HeapFullException e) {
						e.printStackTrace();
					} catch (HeapEmptyException e) {
						e.printStackTrace();
					}					
				}
			});
			
			S.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						game.processCommand("GO S");
						main(null);
					} catch (HeapFullException e) {
						e.printStackTrace();
					} catch (HeapEmptyException e) {
						e.printStackTrace();
					}					
				}
			});
			
			sonar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						game.processCommand("SONAR");
						main(null);
					} catch (HeapFullException e) {
						e.printStackTrace();
					} catch (HeapEmptyException e) {
						e.printStackTrace();
					}					
				}
			});
			
		button.setPreferredSize(new Dimension(1000,300));
        f.add(panel, BorderLayout.CENTER);
        f.add(button, BorderLayout.SOUTH);
        f.setSize(3200, 1500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
	}
}
