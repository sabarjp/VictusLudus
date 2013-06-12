package com.teamderpy.victusludus.gui.eventhandler.event;

import java.util.EventObject;

import com.teamderpy.victusludus.game.Game;


/**
 * The Class ResizeEvent.
 */
public class RenderEvent extends EventObject{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3965247445490684232L;

	/** The type of render event. */
	private EnumRenderEventType eventType;

	/** The game that the event refers to */
	private Game game;

	/**
	 * Instantiates a new resize event.
	 *
	 * @param source the source
	 * @param width the width
	 * @param height the height
	 */
	public RenderEvent(final Object source, final EnumRenderEventType eventType, final Game game) {
		super(source);

		this.setEventType(eventType);
		this.setGame(game);
	}

	public EnumRenderEventType getEventType() {
		return this.eventType;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType the new event type
	 */
	public void setEventType(final EnumRenderEventType eventType) {
		this.eventType = eventType;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

	@Override
	public String toString(){
		return "RenderEvent" + "  type " + this.eventType + "  game " + this.game;
	}
}
