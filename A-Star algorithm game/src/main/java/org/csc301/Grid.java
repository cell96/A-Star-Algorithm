package org.csc301;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Grid {

	private final int DEFAULT_WIDTH = 60; // default width of the world map - gridX runs from 0 to 59
	private final int DEFAULT_HEIGHT = 15; // default height of the map - gridY runs from 0 to 14
	private final int DEFAULT_PERCENT = 20; // this is the percentageof the map occupied by islands
	protected int width, height; // user defined width and height, if one is not using defaults
	protected int percent; // user defined percentage of islands on the map
	protected Node treasure; // points to the map node where the Redbeard treasure is sunken
	protected Node boat; // points to the current location of our boat on the map

	protected Node[][] map; // the map

	public Grid() {
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		percent = DEFAULT_PERCENT;
		buildMap();
	}

	public Grid(int width, int height, int percent) {
		this.width = width;
		this.height = height;
		if (percent <= 0 || percent >= 100)
			this.percent = DEFAULT_PERCENT;
		else
			this.percent = percent;
		buildMap();
	}
	
	private int getRand(int max){
		Random r = new Random();
		return r.nextInt(max);
	}
	
	private void buildMap() {
		int x,y,x1,y1;
	
		map = new Node[height][width];
		y = getRand(height);
		x = getRand(width);
		boat = new Node(true, x, y);
		map[y][x] = boat;
		
		
		y1 = getRand(height);
		x1 = getRand(width);

		treasure = new Node(true, x1, y1);
		if (treasure.gridX == boat.gridX && treasure.gridY == boat.gridY)
			buildMap(); //build map again if they are on the same spot
		map[y1][x1] = treasure;
		
		boolean putObs;
		for (int i=0; i<height; i++){
			for (int j=0; j<width; j++){
				if (map[i][j] == null){
					putObs = getRand(99) < percent;
					if (putObs && map[i][j] != treasure)
						map[i][j] = new Node(false,j,i);
					else
						map[i][j] = new Node(true,j,i);
				}
			}
		}
	}

	public String drawMap() {
		// provided for your convenience
		String result = "";
		String hline = "       ";
		String extraSpace;
		for (int i = 0; i < width / 10; i++)
			hline += "         " + (i + 1);
		result += hline + "\n";
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		for (int i = 0; i < height; i++) {
			if (i < 10)
				extraSpace = "      ";
			else
				extraSpace = "     ";
			hline = extraSpace + i;
			for (int j = 0; j < width; j++) {
				if (i == boat.gridY && j == boat.gridX)
					hline += "B";
				else if (i == treasure.gridY && j == treasure.gridX)
					hline += "T";
				else if (map[i][j].inPath)
					hline += "*";
				else if (map[i][j].walkable)
					hline += ".";
				else
					hline += "+";
			}
			result += hline + i + "\n";
		}
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		return result;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPercent() {
		return percent;
	}
	
	public Node getBoat() {
		return boat;
	}
	
	private ArrayList<Node> getNeighbours(Node node) {
			
			ArrayList<Node> neighbours = new ArrayList<Node>();
			int x;
			int y;
			boolean N;
			boolean E;
			boolean S;
			boolean W;		
			x = node.gridX;
			y = node.gridY;
			
			N = (y-1) >= 0;
			E = (x+1) < width;
			S = (y+1) < height;
			W = (x-1) >= 0;
			
			if (N){
				neighbours.add(map[y-1][x]);
			}
			if (N && E){
				neighbours.add(map[y-1][x+1]);
			}
			if (N && W){
				neighbours.add(map[y-1][x-1]);
			}
			if (S){
				neighbours.add(map[y+1][x]);
			}
			if (S && E){
				neighbours.add(map[y+1][x+1]);
			}
			if (S && W){
				neighbours.add(map[y+1][x-1]);
			}
			if (E){
				neighbours.add(map[y][x+1]);
			}
			if (W){
				neighbours.add(map[y][x-1]);
			}
			return neighbours;		
			
		}

	private int getDistance(Node nodeA, Node nodeB) {
		// helper method. Provided for your convenience.
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridY - nodeB.gridY);
		if (dstX > dstY)
			return 14 * dstY + 10 * (dstX - dstY);
		return 14 * dstX + 10 * (dstY - dstX);
	}

	public void findPath(Node startNode, Node targetNode)
			throws HeapFullException, HeapEmptyException {
		Heap<Node> openSet = new Heap<>(width * height); 
		Set<Node> closedSet = new HashSet<Node>();
		
		startNode.parent = null;
		startNode.gCost = 0;
		startNode.hCost = getDistance(startNode, targetNode);
		openSet.add(startNode);
        
		
        while (openSet.isEmpty() == false){
			Node top = openSet.removeFirst();
	        
			closedSet.add(top);
			if (top == targetNode){
				return;
			}
	        
			ArrayList<Node> neighbors = getNeighbours(top);
			Iterator<Node> i = neighbors.iterator();
			while (i.hasNext()) {
				Node n = i.next();
				
				if (n == targetNode){
					n.parent = top;
					return;
				}
				
				if (n.walkable == false)
					continue;
			
				n.hCost = getDistance(n, targetNode);
				int g = top.gCost + getDistance(top, n);
				boolean comp = n.getFCost() < g + n.hCost;
				
	            if (openSet.contains(n) && comp) {
	            	continue;
	            }
	            if(closedSet.contains(n) && comp){
	            	continue;
	            }
				n.parent = top;
				n.gCost = g;
				if (openSet.contains(n)) 
	                openSet.updateItem(n);
				else 
					openSet.add(n);
				
			}
        }
	}


	public ArrayList<Node> retracePath(Node startNode, Node endNode) {
		Node currentNode = endNode;
	    ArrayList<Node> path = new ArrayList<Node>();
		while (currentNode != startNode && currentNode != null) {
			currentNode.inPath = true;
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		return path;
	}

	public void move(String direction) {
		// Direction may be: N,S,W,E,NE,NW,SE,SW
		// move the boat 1 cell in the required direction
		int x = boat.gridX;
		int y = boat.gridY;
		boolean N = (y-1) >= 0;
		boolean E = (x+1) < width;
		boolean S = (y+1) < height;
		boolean W = (x-1) >= 0;		
		
		if (direction.equals("N") && N){
			if (map[y-1][x].walkable == false){
				return;
			}
			else{
				Node temp = map[y-1][x];
				map[y-1][x] = boat;
				boat.gridY = y - 1;
				map[y][x] = temp;
				temp.gridY = y;
			}				
		}
		else if (direction.equals("NE") && N && E){
			if (map[y-1][x+1].walkable == false){
				return;
			}
			else{
				Node temp = map[y-1][x+1];
				map[y-1][x+1] = boat;
				boat.gridX = x + 1;
				boat.gridY = y - 1;
				map[y][x] = temp;
				temp.gridY = y;
				temp.gridX = x;
			}
		}
		else if (direction.equals("NW") && N && W){
			if (map[y-1][x-1].walkable == false){
				return;
			}
			else{
				Node temp = map[y-1][x+1];
				map[y-1][x-1] = boat;
				boat.gridX = x - 1;
				boat.gridY = y - 1;
				map[y][x] = temp;
				temp.gridY = y;
				temp.gridX = x;
			}
		}
		else if (direction.equals("W") && W){
			if (map[y+1][x].walkable == false){
				return;
			}
			else{
				Node temp = map[y+1][x];
				map[y+1][x] = boat;
				boat.gridY = y + 1;
				map[y][x] = temp;
				temp.gridY = y;				
			}
		}
		else if(direction.equals("SE") && S && E){
			if (map[y+1][x+1].walkable == false){
				return;
			}
			else{
				Node temp = map[y+1][x+1];
				map[y+1][x+1] = boat;
				boat.gridX = x + 1;
				boat.gridY = y + 1;
				map[y][x] = temp;
				temp.gridY = y;
				temp.gridX = x;
			}
						
		}
		else if (direction.equals("SW") && S && W){
			if (map[y+1][x-1].walkable== false){
				return;
			}
			else{
				Node temp = map[y+1][x - 1];
				map[y+1][x-1] = boat;
				boat.gridX = x - 1;
				boat.gridY = y + 1;
				map[y][x] = temp;
				temp.gridY = y;
				temp.gridX = x;
			}
		}
		else if (direction.equals("E") && E){
			if (map[y][x+1].walkable == false){
				return;
			}
			else{
				Node change = map[y][x+1];
				map[y][x+1] = boat;
				boat.gridX = x + 1;
				map[y][x] = change;
				change.gridX = x;				
			}
		}
		else if (direction.equals("S") && S){
			if (map[y][x-1].walkable == false){
				return;
			}
			else{
				Node change = map[y][x-1];
				map[y][x-1] = boat;
				boat.gridX = x - 1;
				map[y][x] = change;
				change.gridX = x;			
			}
		}
	}
	
		
	
	public Node getTreasure(int range) {
		// range is the range of the sonar
		// if the distance of the treasure from the boat is less or equal that the sonar range,
		// return the treasure node. Otherwise return null.
		int distance = getDistance(boat,treasure);
		if (distance <= range) 
			return treasure;
		return null;
	}

}
