package com.teamderpy.victusludus.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.teamderpy.victusludus.game.map.Map;
import com.teamderpy.victusludus.game.tile.GameTile;

// TODO: Auto-generated Javadoc
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
	public static ArrayList<WorldCoord> search(final Map map, final WorldCoord start, final WorldCoord goal){
		HashMap<WorldCoord, AStarNode> AStarMap = new HashMap<WorldCoord, AStarNode>();

		for(GameTile[][] layer:map.getMap()){
			for(GameTile[] row:layer){
				for(GameTile t:row){
					if(t != null){
						AStarMap.put(t.getPos(), new AStarNode());
					}
				}
			}
		}

		class AStarComparator implements Comparator<WorldCoord>{
			HashMap<WorldCoord, AStarNode> AStarMap;

			public AStarComparator(final HashMap<WorldCoord, AStarNode> AStarMap){
				this.AStarMap = AStarMap;
			}

			@Override
			public int compare(final WorldCoord o1, final WorldCoord o2) {
				if(this.AStarMap.get(o1).getF() < this.AStarMap.get(o2).getF()){
					return -1;
				}

				if(this.AStarMap.get(o1).getF() > this.AStarMap.get(o2).getF()){
					return 1;
				}

				return 0;
			}

		}

		Set<WorldCoord> closedSet = new HashSet<WorldCoord>();
		PriorityQueue<WorldCoord> openSet = new PriorityQueue<WorldCoord>(10, new AStarComparator(AStarMap));
		HashMap<WorldCoord, WorldCoord> cameFrom = new HashMap<WorldCoord, WorldCoord>();

		AStarMap.get(start).setG(0);
		AStarMap.get(start).setF(AStarMap.get(start).getG() + AStarSearch.costEstimate(start, goal));
		openSet.add(start);

		while(!openSet.isEmpty()){
			WorldCoord current = openSet.remove();

			if(current.equals(goal)){
				return AStarSearch.reconstructPath(cameFrom, goal);
			}

			closedSet.add(current);

			for(WorldCoord neighbor:map.getNeighbors(current)){
				if(AStarMap.containsKey(neighbor)){
					int tentativeG = AStarMap.get(current).getG() +  AStarSearch.distance(current, neighbor);
					if(closedSet.contains(neighbor)){
						if(tentativeG > AStarMap.get(neighbor).getG()){
							continue;
						}
					}

					if(!openSet.contains(neighbor) || tentativeG < AStarMap.get(neighbor).getG()){
						cameFrom.put(neighbor, current);
						AStarMap.get(neighbor).setG(tentativeG);
						AStarMap.get(neighbor).setF(tentativeG + AStarSearch.costEstimate(neighbor, goal));
						if(!openSet.contains(neighbor)){
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
	private static ArrayList<WorldCoord> reconstructPath(final HashMap<WorldCoord, WorldCoord> cameFrom, final WorldCoord current){
		ArrayList<WorldCoord> p;

		if(cameFrom.containsKey(current)){
			p = AStarSearch.reconstructPath(cameFrom, cameFrom.get(current));
			p.add(current);

			return p;
		} else {
			ArrayList<WorldCoord> c = new ArrayList<WorldCoord>();
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
	private static int costEstimate(final WorldCoord s1, final WorldCoord s2){
		return  Math.abs(s2.getX() - s1.getX()) + Math.abs(s2.getY() - s1.getY()) + Math.abs(s2.getZ() - s1.getZ());
	}

	/**
	 * Distance.
	 *
	 * @param s1 the s1
	 * @param s2 the s2
	 * @return the int
	 */
	private static int distance(final WorldCoord s1, final WorldCoord s2){
		return  Math.abs(s2.getX() - s1.getX()) + Math.abs(s2.getY() - s1.getY()) + Math.abs(s2.getZ() - s1.getZ());
	}
}
