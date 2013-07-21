
package com.teamderpy.victusludus.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.badlogic.gdx.math.Vector3;
import com.teamderpy.victusludus.game.map.Map;

/**
 * The Class AStarSearch.
 */
public class AStarSearch {

	/**
	 * Search.
	 * 
	 * @param map the map
	 * @param start the start
	 * @param goal the goal
	 * @return the array list
	 */
	public static ArrayList<Vector3> search (final Map map, final Vector3 start, final Vector3 goal) {
		HashMap<Vector3, AStarNode> AStarMap = new HashMap<Vector3, AStarNode>();

		/*
		 * for(GameTile[][] layer:map.getMap()){ for(GameTile[] row:layer){
		 * for(GameTile t:row){ if(t != null){ AStarMap.put(t.getPos(), new
		 * AStarNode()); } } } }
		 */

		class AStarComparator implements Comparator<Vector3> {
			HashMap<Vector3, AStarNode> AStarMap;

			public AStarComparator (final HashMap<Vector3, AStarNode> AStarMap) {
				this.AStarMap = AStarMap;
			}

			@Override
			public int compare (final Vector3 o1, final Vector3 o2) {
				if (this.AStarMap.get(o1).getF() < this.AStarMap.get(o2).getF()) {
					return -1;
				}

				if (this.AStarMap.get(o1).getF() > this.AStarMap.get(o2).getF()) {
					return 1;
				}

				return 0;
			}

		}

		Set<Vector3> closedSet = new HashSet<Vector3>();
		PriorityQueue<Vector3> openSet = new PriorityQueue<Vector3>(10, new AStarComparator(AStarMap));
		HashMap<Vector3, Vector3> cameFrom = new HashMap<Vector3, Vector3>();

		AStarMap.get(start).setG(0);
		AStarMap.get(start).setF(AStarMap.get(start).getG() + AStarSearch.costEstimate(start, goal));
		openSet.add(start);

		while (!openSet.isEmpty()) {
			Vector3 current = openSet.remove();

			if (current.equals(goal)) {
				return AStarSearch.reconstructPath(cameFrom, goal);
			}

			closedSet.add(current);

			for (Vector3 neighbor : map.getNeighbors(current)) {
				if (AStarMap.containsKey(neighbor)) {
					int tentativeG = AStarMap.get(current).getG() + AStarSearch.distance(current, neighbor);
					if (closedSet.contains(neighbor)) {
						if (tentativeG > AStarMap.get(neighbor).getG()) {
							continue;
						}
					}

					if (!openSet.contains(neighbor) || tentativeG < AStarMap.get(neighbor).getG()) {
						cameFrom.put(neighbor, current);
						AStarMap.get(neighbor).setG(tentativeG);
						AStarMap.get(neighbor).setF(tentativeG + AStarSearch.costEstimate(neighbor, goal));
						if (!openSet.contains(neighbor)) {
							openSet.add(neighbor);
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Reconstruct path.
	 * 
	 * @param cameFrom the came from
	 * @param current the current
	 * @return the array list
	 */
	private static ArrayList<Vector3> reconstructPath (final HashMap<Vector3, Vector3> cameFrom, final Vector3 current) {
		ArrayList<Vector3> p;

		if (cameFrom.containsKey(current)) {
			p = AStarSearch.reconstructPath(cameFrom, cameFrom.get(current));
			p.add(current);

			return p;
		} else {
			ArrayList<Vector3> c = new ArrayList<Vector3>();
			c.add(current);
			return c;
		}
	}

	/**
	 * Cost estimate.
	 * 
	 * @param s1 the s1
	 * @param s2 the s2
	 * @return the int
	 */
	private static int costEstimate (final Vector3 s1, final Vector3 s2) {
		return (int)(Math.abs(s2.x - s1.x) + Math.abs(s2.y - s1.y) + Math.abs(s2.z - s1.z));
	}

	/**
	 * Distance.
	 * 
	 * @param s1 the s1
	 * @param s2 the s2
	 * @return the int
	 */
	private static int distance (final Vector3 s1, final Vector3 s2) {
		return (int)(Math.abs(s2.x - s1.x) + Math.abs(s2.y - s1.y) + Math.abs(s2.z - s1.z));
	}
}
