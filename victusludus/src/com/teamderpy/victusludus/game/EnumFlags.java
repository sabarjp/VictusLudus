package com.teamderpy.victusludus.game;


/**
 * The Enum EnumFlags.
 */
public enum EnumFlags {
	
	/** The buildable. */
	BUILDABLE,			      /* This item can be built */
	/** The stackable. */
      			STACKABLE,			      /* This item can be stacked with other items */
	/** The walkable. */
      			WALKABLE,               /* This entity can be walked on by other entities */
	/** The cannot build stacks. */
               CANNOT_BUILD_STACKS,    /* Only one of this type of entity can be built on a tile */
	/** The cannot build on others. */
    CANNOT_BUILD_ON_OTHERS, /* Cannot build this type of entity where other buildables exist */
	/** The keeps momentum. */
 KEEPS_MOMENTUM,         /* The entity prefers to move in the same direction and avoids moving backwards */
	/** The randomly turns. */
         RANDOMLY_TURNS;         /* The entity will randomly move onto eligible side-paths, even if it has forward momentum */
}
