package com.teamderpy.victusludus.game.renderer;

import java.util.Comparator;

import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.EuclideanObject;
import com.teamderpy.victusludus.game.WorldCoord;
import com.teamderpy.victusludus.game.entity.GameEntity;


public class EuclideanComparator implements Comparator<EuclideanObject> {
	@Override
	public int compare(final EuclideanObject e1, final EuclideanObject e2) {
		WorldCoord ep1 = e1.getWorldCoord();
		WorldCoord ep2 = e2.getWorldCoord();

		int mod1 = 0;
		int mod2 = 0;

		if(e1 instanceof GameEntity){
			if(((GameEntity) e1).getEntity().getFlagSet().contains(EnumFlags.WALKABLE)){
				mod1--;
			}
		}

		if(e2 instanceof GameEntity){
			if(((GameEntity) e2).getEntity().getFlagSet().contains(EnumFlags.WALKABLE)){
				mod2--;
			}
		}

		int sum1 = ep1.getX() + ep1.getY() + ep1.getZ() + mod1;
		int sum2 = ep2.getX() + ep2.getY() + ep2.getZ() + mod2;

		if(sum1 < sum2){
			return -1;
		} else if (sum1 > sum2){
			return 1;
		}

		//it is a perfect tie
		return 0;
	}
}
