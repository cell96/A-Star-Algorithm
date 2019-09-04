package org.csc301;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Test {

	@org.junit.Test
	public final void testTreasureCons() {
		TreasureHunt game = new TreasureHunt(5,5,50,15,1);
		assertEquals("STARTED", game.state, "STARTED");
		assertEquals("10 sonars", game.sonars, 15);
	}
	
	@org.junit.Test
	public final void testTreasureCons2(){
		TreasureHunt game = new TreasureHunt();
		assertEquals("game started", game.state, "STARTED");
		assertEquals("default sonars are 3", game.sonars, 3);
		assertEquals("default range is 500", game.range, 500);
	}
	
	@org.junit.Test
	public final void testProcessCommand() throws HeapFullException, HeapEmptyException{
		TreasureHunt game = new TreasureHunt();
		int s = game.sonars; 
		game.processCommand("SONAR");
		assertEquals("sonars decrease by 1", game.sonars, s-1);
		
		int t = game.islands.boat.gridY;
		//System.out.println(t);
		//game.processCommand("GO N");
		//System.out.println(game.islands.boat.gridY);
		//if (game.islands.map[game.islands.boat.gridY][game.islands.boat.gridX].walkable)
			//assertEquals("go north", game.islands.boat.gridY, t-1);
	}
	
	@org.junit.Test
	public final void testPathLength() throws HeapFullException, HeapEmptyException{
		TreasureHunt grid = new TreasureHunt();
		grid.islands.findPath(grid.islands.map[1][1], grid.islands.map[1][2]);
		ArrayList<Node> path = new ArrayList<Node>();
		path = grid.islands.retracePath(grid.islands.map[1][1], grid.islands.map[1][2]);
		grid.path = path;
		assertEquals("path should be 1", grid.pathLength(), 1);
	}
	
	@org.junit.Test
	public final void testGetMap(){
		TreasureHunt grid = new TreasureHunt();
		String s = grid.islands.drawMap();
		assertFalse(s.isEmpty());
	}
	
	
	@org.junit.Test
	public final void testSortUp() throws HeapFullException, HeapEmptyException{
		Node n1 = new Node(true, 1, 1);
		Node n2 = new Node(true, 2, 2);
		Node n3 = new Node(true, 3, 3);
		Node n4 = new Node(true, 4, 4);
		
		n1.hCost = 1;
		n1.gCost = 1;
		
		n2.hCost = 2;
		n2.gCost = 2;
		
		n3.hCost = 3;
		n3.gCost = 3;
		
		n4.hCost = 4;
		n4.gCost = 4;
		
		Heap<Node> openSet = new Heap<>(10*10);
		openSet.add(n1);
		openSet.add(n2);
		openSet.add(n3);
		openSet.add(n4);
		
		assertTrue(openSet.removeFirst().equals(n1));
	}
	
	@org.junit.Test
	public final void testSortDown() throws HeapFullException, HeapEmptyException{
		Node n1 = new Node(true, 1, 1);
		Node n2 = new Node(true, 2, 2);
		Node n3 = new Node(true, 3, 3);
		Node n4 = new Node(true, 4, 4);
		
		n1.hCost = 1;
		n1.gCost = 1;
		
		n2.hCost = 2;
		n2.gCost = 2;
		
		n3.hCost = 3;
		n3.gCost = 3;
		
		n4.hCost = 4;
		n4.gCost = 4;
		
		Heap<Node> openSet = new Heap<>(10*10);
		openSet.add(n1);
		openSet.add(n2);
		openSet.add(n3);
		openSet.add(n4);
		
		openSet.removeFirst();
		assertTrue(openSet.removeFirst().equals(n2));
		
	}
	
	@org.junit.Test
	public final void testGridCons(){
		Grid g = new Grid(10, 10, 1);
		assertEquals("should be 10", g.height, 10);
		assertEquals("should be 10", g.width, 10);
		assertEquals("should be 1", g.percent, 1);
	}
	
	@org.junit.Test
	public final void testfindPath() throws HeapFullException, HeapEmptyException{
		Grid g = new Grid(10, 10, 0);
		g.findPath(g.map[1][1], g.map[1][2]);
		ArrayList<Node> path = new ArrayList<Node>();
		path = g.retracePath(g.map[1][1], g.map[1][3]);
		assertEquals("final x cord is 3", path.get(0).gridX, 3);
		assertEquals("final y cord is 1", path.get(0).gridY, 1);
	}
	
	@org.junit.Test
	public final void testRetracePath() throws HeapFullException, HeapEmptyException{
		Grid g = new Grid(10, 10, 0);
		g.findPath(g.map[1][1], g.map[1][2]);
		ArrayList<Node> path = new ArrayList<Node>();
		path = g.retracePath(g.map[1][1], g.map[1][3]);
		assertEquals("final x cord is 3", path.get(0).gridX, 3);
		assertEquals("final y cord is 1", path.get(0).gridY, 1);
	}
	
	@org.junit.Test
	public final void testGetTreasure(){
		Grid g = new Grid(10, 10, 0);
		int range = 100;
		assertEquals(g.treasure.gridX, g.getTreasure(range).gridX);
		assertEquals(g.treasure.gridY, g.getTreasure(range).gridY);
	}
	
	@org.junit.Test
	public final void testMove(){
		Grid g = new Grid(10, 10, 0);
		Grid g2 = new Grid(10,10,99); //all islands
		int x = g.boat.gridY;
		int x2 = g2.boat.gridY;
		
		g.move("N");
		if (x <= 10 && x > 0) {
			assertEquals(g.boat.gridY, x-1);
		}
		
		g2.move("N");
		assertEquals(g2.boat.gridY, x2);
	}
	
	@org.junit.Test
	public final void testCompareTo(){
		Node n1 = new Node(true, 1, 1);
		Node n2 = new Node(true, 2, 2);
		n1.gCost = 1;
		n1.hCost = 1;
		
		n2.gCost = 2;
		n2.hCost = 2;
		assertTrue(n1.compareTo(n2) < 0);
		
		n2.gCost = 0;
		n2.hCost = 0;
		assertFalse(n1.compareTo(n2) < 0);
	}
	
	@org.junit.Test
	public final void testEquals(){
		Node n1 = new Node(true, 2, 2);
		Node n2 = new Node(true, 2, 2);
		assertTrue(n1.equals(n2));
	}
	
}
