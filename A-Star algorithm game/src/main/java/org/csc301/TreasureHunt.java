package org.csc301;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class TreasureHunt {

	private final int DEFAULT_SONARS = 3; // default number of available sonars
	private final int DEFAULT_RANGE = 100; // default range of a sonar
	protected Grid islands; // the world where the action happens!
	protected int height, width, landPercent;
	protected int sonars, range; // user defined number of sonars and range
	protected String state; // state of the game (STARTED, OVER)
	protected ArrayList<Node> path; // the path to the treasure!

	public TreasureHunt() {
		// The default constructor
		this.sonars = DEFAULT_SONARS;
		this.range = DEFAULT_RANGE;
		this.state = "STARTED";
		this.islands = new Grid();
	}

	public TreasureHunt(int height, int width, int landPercent, int sonars,
			int range) {
		// The constructor thatuses parameters
		this.islands = new Grid(height, width, landPercent);
		this.state = "STARTED";
		this.sonars = sonars;
		this.range = range;
	}

	public void processCommand(String command) throws HeapFullException,
			HeapEmptyException {
		// The allowed commands are: 
		// SONAR to drop the sonar in hope to detect treasure
		// GO direction to move the boat in some direction
		// For example, GO NW means move the boat one cell up left (if the cell is navigable; if not simply ignore the command) 
		if (state.equals("OVER"))
			return;
		if (command.substring(0,2).equals("GO")){
			String[] parts = command.split(" ");
			String direction = parts[1];
			islands.move(direction);
		}
		if (command.equals("SONAR")){
			Node s = islands.getTreasure(range);
			if (sonars == 0){
				state = "OVER";
				return;
			} else
				sonars--; 
			
			if (s == null)
				return;
			islands.findPath(islands.getBoat(), s);
			this.path = islands.retracePath(islands.getBoat(), s);
			state = "OVER";
		}
		return;
	}

	public int pathLength() {
		if (path == null)
			return 0;
		else return path.size();
	}

	public String getMap() {
		return islands.drawMap();
	}

	public void play(String pathName) throws FileNotFoundException,
	HeapFullException, HeapEmptyException {
		Scanner getData = new Scanner(new File(pathName));
		while (getData.hasNextLine() && !state.equals("OVER")) {
			String line = getData.nextLine();
			processCommand(line);
		}
		getData.close();
	}

}
