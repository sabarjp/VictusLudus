
package com.teamderpy.victusludus.readerwriter;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.teamderpy.victusludus.VictusLudusGame;
import com.teamderpy.victusludus.VictusRuntimeException;
import com.teamderpy.victusludus.engine.graphics.BitmapHandler;
import com.teamderpy.victusludus.game.EnumFlags;
import com.teamderpy.victusludus.game.entity.GameEntity;
import com.teamderpy.victusludus.game.entity.behavior.CreateAdjacentBehavior;
import com.teamderpy.victusludus.game.entity.behavior.EntityBehavior;
import com.teamderpy.victusludus.game.entity.behavior.MoveBehavior;

/** The Class EntityReader. */
public class EntityReader implements IObjectReader {

	@SuppressWarnings("unchecked")
	@Override
	public <T> void ReadAndLoad (final FileHandle f, final Map<String, T> hash) {
		JLDLSerialReader r = new JLDLSerialReader(f);

		ReadData baseLevel;

		// base level nodes
		while ((baseLevel = r.getNext()) != null) {
			if (baseLevel.getNode().equalsIgnoreCase(JLDLSerialReader.BASE_LEVEL_NODE)) {
				System.out.println("found base level node");

				// entities
				if (baseLevel.getId().equalsIgnoreCase("entity")) {
					System.out.println("found entity node");

					ReadData entityLevel;

					GameEntity e = new GameEntity();
					e.setId(baseLevel.getValue());

					// entity children
					while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("entity")) {
						entityLevel = r.getNext();
						System.out.println("checking entity children");

						if (entityLevel.getId().equalsIgnoreCase("main")) {
							System.out.println("found entity main node");

							ReadData mainLevel;

							while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("main")) {
								mainLevel = r.getNext();
								System.out.println("checking entity main node children");

								if (mainLevel.getId().equalsIgnoreCase("name")) {
									System.out.println("found entity main name node");

									e.setName(mainLevel.getValue());
								} else if (mainLevel.getId().equalsIgnoreCase("build_mode")) {
									System.out.println("found entity main build_mode node");

									e.setBuildMode(mainLevel.getValue());
								} else if (mainLevel.getId().equalsIgnoreCase("height")) {
									System.out.println("found entity main height node");

									e.setHeight(Integer.valueOf(mainLevel.getValue()));
								} else if (mainLevel.getId().equalsIgnoreCase("width")) {
									System.out.println("found entity main width node");

									e.setWidth(Integer.valueOf(mainLevel.getValue()));
								} else {
									System.out.println("mainLevel");
									this.logError(f.path(), r, mainLevel);
								}
							}
						} else if (entityLevel.getId().equalsIgnoreCase("flags")) {
							System.out.println("found entity flags node");

							ReadData flagsLevel;

							while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("flags")) {
								flagsLevel = r.getNext();
								System.out.println("checking entity flags node children");

								if (flagsLevel.getId().equalsIgnoreCase("flag")) {
									System.out.println("found entity flags flag node");

									e.getFlagSet().add(EnumFlags.valueOf(flagsLevel.getValue()));
								} else {
									System.out.println("flagsLevel");
									this.logError(f.path(), r, flagsLevel);
								}
							}
						} else if (entityLevel.getId().equalsIgnoreCase("graphics")) {
							System.out.println("found entity graphics node");

							ReadData graphicsLevel;

							while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("graphics")) {
								graphicsLevel = r.getNext();
								System.out.println("checking entity graphics children");

								if (graphicsLevel.getId().equalsIgnoreCase("icon")) {
									System.out.println("found entity graphics icon node");

									ReadData iconLevel;

									while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("icon")) {
										iconLevel = r.getNext();
										System.out.println("checking entity graphics icon children");

										if (iconLevel.getId().equalsIgnoreCase("parent_build_node")) {
											System.out.println("found entity graphics icon parent_build_node node");

											e.setParentButtonNode(iconLevel.getValue());
										} else if (iconLevel.getId().equalsIgnoreCase("sprite")) {
											System.out.println("found entity graphics icon sprite node");

											e.loadButtonSpriteSheet(iconLevel.getValue());
										} else {
											System.out.println("iconLevel");
											this.logError(f.path(), r, iconLevel);
										}
									}
								} else if (graphicsLevel.getId().equalsIgnoreCase("billboard")) {
									System.out.println("found entity graphics billboard node");
									e.setBillboard(Boolean.valueOf(graphicsLevel.getValue()));
								} else if (graphicsLevel.getId().equalsIgnoreCase("animation")) {
									System.out.println("found entity graphics animation node");

									ReadData animationLevel;

									String animationID = graphicsLevel.getValue();
									String animationPath = "";

									int firstFrame = 1;
									int lastFrame = 1;
									int framesPerRow = 1;
									int rowsPerSprite = 1;
									int speed = 1000;

									while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("animation")) {
										animationLevel = r.getNext();
										System.out.println("checking entity graphics animation children");

										if (animationLevel.getId().equalsIgnoreCase("sprite")) {
											System.out.println("found entity graphics animation sprite node");
											animationPath = animationLevel.getValue();
										} else if (animationLevel.getId().equalsIgnoreCase("begin_frame")) {
											System.out.println("found entity graphics animation begin_frame node");
											firstFrame = Integer.valueOf(animationLevel.getValue());
										} else if (animationLevel.getId().equalsIgnoreCase("end_frame")) {
											System.out.println("found entity graphics animation end_frame node");
											lastFrame = Integer.valueOf(animationLevel.getValue());
										} else if (animationLevel.getId().equalsIgnoreCase("frames_per_row")) {
											System.out.println("found entity graphics animation frames_per_row node");
											framesPerRow = Integer.valueOf(animationLevel.getValue());
										} else if (animationLevel.getId().equalsIgnoreCase("rows_in_sprite")) {
											System.out.println("found entity graphics animation rows_in_sprite node");
											rowsPerSprite = Integer.valueOf(animationLevel.getValue());
										} else if (animationLevel.getId().equalsIgnoreCase("speed")) {
											System.out.println("found entity graphics animation speed node");
											speed = Integer.valueOf(animationLevel.getValue());
										} else {
											System.out.println("animationLevel");
											this.logError(f.path(), r, animationLevel);
										}
									}

									e.getAnimationHash().put(
										animationID,
										BitmapHandler.LoadAnimationSheet(VictusLudusGame.resources.getTextureAtlasEntities(),
											animationPath, framesPerRow, rowsPerSprite, firstFrame, lastFrame, speed));
								} else {
									System.out.println("graphicsLevel");
									this.logError(f.path(), r, graphicsLevel);
								}
							}
						} else if (entityLevel.getId().equalsIgnoreCase("behavior")) {
							System.out.println("found entity behavior node");

							ReadData behaviorLevel;

							e.setBehaviorList(new ArrayList<EntityBehavior>());

							while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("behavior")) {
								behaviorLevel = r.getNext();
								System.out.println("found entity behavior children");

								if (behaviorLevel.getId().equalsIgnoreCase("create_adjacent")) {
									System.out.println("found entity behavior create_adjacent node");

									ReadData cabLevel;

									CreateAdjacentBehavior cab = new CreateAdjacentBehavior();

									while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("create_adjacent")) {
										cabLevel = r.getNext();
										System.out.println("checking entity behavior create_adjacent children");

										if (cabLevel.getId().equalsIgnoreCase("object_id")) {
											System.out.println("found entity behavior create_adjacent object_id node");

											cab.setObjectID(cabLevel.getValue());
										} else if (cabLevel.getId().equalsIgnoreCase("count")) {
											System.out.println("found entity behavior create_adjacent count node");

											cab.setCount(Integer.valueOf(cabLevel.getValue()));
										} else if (cabLevel.getId().equalsIgnoreCase("restrict_id")) {
											System.out.println("found entity behavior create_adjacent restrict_id node");

											cab.getRestrictionList().add(cabLevel.getValue());
										} else if (cabLevel.getId().equalsIgnoreCase("rarity")) {
											System.out.println("found entity behavior create_adjacent rarity node");

											cab.setRarity(Integer.valueOf(cabLevel.getValue()));
										} else {
											System.out.println("cabLevel");
											this.logError(f.path(), r, cabLevel);
										}
									}

									e.getBehaviorList().add(cab);
								} else if (behaviorLevel.getId().equalsIgnoreCase("move")) {
									System.out.println("found entity behavior move node");

									ReadData mbLevel;

									MoveBehavior mb = new MoveBehavior();

									while (r.peek() != null && r.peek().getNode().equalsIgnoreCase("move")) {
										mbLevel = r.getNext();
										System.out.println("checking entity behavior move children");

										if (mbLevel.getId().equalsIgnoreCase("restrict_id")) {
											System.out.println("found entity behavior move restrict_id node");

											mb.getRestrictionList().add(mbLevel.getValue());
										} else if (mbLevel.getId().equalsIgnoreCase("rarity")) {
											System.out.println("found entity behavior move rarity node");

											mb.setRarity(Integer.valueOf(mbLevel.getValue()));
										} else if (mbLevel.getId().equalsIgnoreCase("random")) {
											System.out.println("found entity behavior move random node");

											mb.setRandom(Boolean.valueOf(mbLevel.getValue()));
										} else {
											System.out.println("mbLevel");
											this.logError(f.path(), r, mbLevel);
										}
									}

									e.getBehaviorList().add(mb);
								}
							}
						} else {
							System.out.println("entityLevel");
							this.logError(f.path(), r, entityLevel);
						}
					}

					// put entity in hash
					Gdx.app.log("warning", "PLACED ENTITY IN HASH");
					hash.put(e.getId(), (T)e);
				} else {
					System.out.println("baseLevelnode");
					this.logError(f.path(), r, baseLevel);
				}
			} else {
				System.out.println("baseLevelparent");
				this.logError(f.path(), r, baseLevel);
			}
		}

		r.close();
	}

	/**
	 * Log error.
	 * 
	 * @param path the path
	 * @param r the r
	 * @param node the node
	 */
	private void logError (final String path, final JLDLSerialReader r, final ReadData node) {
		Gdx.app.log("severe", "entity in " + path + " on line " + r.getLineNumber() + ":\n\t"
			+ " bad indentation or unknown keyword '" + node.getNode() + "' -> '" + node.getId() + ":" + node.getValue() + "'");
		throw new VictusRuntimeException("Improper formatting for file " + path + " on line " + r.getLineNumber());
	}
}
