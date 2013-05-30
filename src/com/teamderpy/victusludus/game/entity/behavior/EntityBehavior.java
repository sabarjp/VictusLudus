package com.teamderpy.victusludus.game.entity.behavior;

import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.map.Map;

// TODO: Auto-generated Javadoc
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
	public abstract void tick(GameEntity ge, Map map);
}
