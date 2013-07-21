package com.teamderpy.victusludus.game.entity.behavior;

import com.teamderpy.victusludus.game.entity.GameEntityInstance;
import com.teamderpy.victusludus.game.map.Map;


/**
 * The Class EntityBehavior.
 */
public abstract class EntityBehavior {

	/**
	 * Tick.
	 *
	 * @param ge the GameEntity to tick
	 * @param map the map to tick on
	 */
	public abstract void tick(GameEntityInstance ge, Map map);
}
