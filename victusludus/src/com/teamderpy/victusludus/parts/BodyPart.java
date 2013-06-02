package com.teamderpy.victusludus.parts;

import java.util.ArrayList;


/**
 * The Class BodyPart.
 */
public class BodyPart {
	
	/** The name. */
	private String name = "";
	
	/** The description. */
	private String description = "";
	
	/** The id. */
	private String id = "";
	
	/** The material. */
	private Material material = null;

	/* state */
	/** The is destroyed. */
	private boolean isDestroyed = false;
	
	/** The is bruised. */
	private boolean isBruised = false;
	
	/** The is torn. */
	private boolean isTorn = false;
	
	/** The is cut. */
	private boolean isCut = false;
	
	/** The is shredded. */
	private boolean isShredded = false;
	
	/** The is severed. */
	private boolean isSevered = false;
	
	/** The is mangled. */
	private boolean isMangled = false;
	
	/** The is burned. */
	private boolean isBurned = false;
	
	/** The is frozen. */
	private boolean isFrozen = false;
	
	/** The is fractured. */
	private boolean isFractured = false;
	
	/* data */
	/** The is vital. */
	private boolean isVital = false;
	
	/** The is vital for parent function. */
	private boolean isVitalForParentFunction = false;
	
	/** The provides grasping. */
	private boolean providesGrasping = false;
	
	/** The provides locomotion. */
	private boolean providesLocomotion = false;
	
	/** The provides digestion. */
	private boolean providesDigestion = false;
	
	/** The provides movement. */
	private boolean providesMovement = false;
	
	/** The provides cognition. */
	private boolean providesCognition = false;
	
	/** The provides sight. */
	private boolean providesSight = false;
	
	/** The provides hearing. */
	private boolean providesHearing = false;
	
	/** The provides blood. */
	private boolean providesBlood = false;
	
	/** The provides breath. */
	private boolean providesBreath = false;
	
	/** The provides support. */
	private boolean providesSupport = false;
	
	/** The can bleed. */
	private boolean canBleed = true;
	
	/** The can break. */
	private boolean canBreak = true;
	
	/** The can shear. */
	private boolean canShear = true;
	
	/** The can pierce. */
	private boolean canPierce = true;
	
	/** The can rot. */
	private boolean canRot = true;

	/** The relative size. */
	private float relativeSize = 1.0f;

	/** The attached parts. */
	private ArrayList<BodyPart> attachedParts = new ArrayList<BodyPart>();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is vital.
	 *
	 * @return true, if is vital
	 */
	public boolean isVital() {
		return isVital;
	}

	/**
	 * Sets the vital.
	 *
	 * @param isVital the new vital
	 */
	public void setVital(boolean isVital) {
		this.isVital = isVital;
	}

	/**
	 * Checks if is destroyed.
	 *
	 * @return true, if is destroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Sets the destroyed.
	 *
	 * @param isDestroyed the new destroyed
	 */
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if is bruised.
	 *
	 * @return true, if is bruised
	 */
	public boolean isBruised() {
		return isBruised;
	}

	/**
	 * Sets the bruised.
	 *
	 * @param isBruised the new bruised
	 */
	public void setBruised(boolean isBruised) {
		this.isBruised = isBruised;
	}

	/**
	 * Checks if is torn.
	 *
	 * @return true, if is torn
	 */
	public boolean isTorn() {
		return isTorn;
	}

	/**
	 * Sets the torn.
	 *
	 * @param isTorn the new torn
	 */
	public void setTorn(boolean isTorn) {
		this.isTorn = isTorn;
	}

	/**
	 * Checks if is cut.
	 *
	 * @return true, if is cut
	 */
	public boolean isCut() {
		return isCut;
	}

	/**
	 * Sets the cut.
	 *
	 * @param isCut the new cut
	 */
	public void setCut(boolean isCut) {
		this.isCut = isCut;
	}

	/**
	 * Checks if is shredded.
	 *
	 * @return true, if is shredded
	 */
	public boolean isShredded() {
		return isShredded;
	}

	/**
	 * Sets the shredded.
	 *
	 * @param isShredded the new shredded
	 */
	public void setShredded(boolean isShredded) {
		this.isShredded = isShredded;
	}

	/**
	 * Checks if is severed.
	 *
	 * @return true, if is severed
	 */
	public boolean isSevered() {
		return isSevered;
	}

	/**
	 * Sets the severed.
	 *
	 * @param isSevered the new severed
	 */
	public void setSevered(boolean isSevered) {
		this.isSevered = isSevered;
	}

	/**
	 * Checks if is mangled.
	 *
	 * @return true, if is mangled
	 */
	public boolean isMangled() {
		return isMangled;
	}

	/**
	 * Sets the mangled.
	 *
	 * @param isMangled the new mangled
	 */
	public void setMangled(boolean isMangled) {
		this.isMangled = isMangled;
	}

	/**
	 * Checks if is burned.
	 *
	 * @return true, if is burned
	 */
	public boolean isBurned() {
		return isBurned;
	}

	/**
	 * Sets the burned.
	 *
	 * @param isBurned the new burned
	 */
	public void setBurned(boolean isBurned) {
		this.isBurned = isBurned;
	}

	/**
	 * Checks if is frozen.
	 *
	 * @return true, if is frozen
	 */
	public boolean isFrozen() {
		return isFrozen;
	}

	/**
	 * Sets the frozen.
	 *
	 * @param isFrozen the new frozen
	 */
	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	/**
	 * Checks if is fractured.
	 *
	 * @return true, if is fractured
	 */
	public boolean isFractured() {
		return isFractured;
	}

	/**
	 * Sets the fractured.
	 *
	 * @param isFractured the new fractured
	 */
	public void setFractured(boolean isFractured) {
		this.isFractured = isFractured;
	}

	/**
	 * Checks if is vital for parent.
	 *
	 * @return true, if is vital for parent
	 */
	public boolean isVitalForParent() {
		return isVitalForParentFunction;
	}

	/**
	 * Sets the vital for parent.
	 *
	 * @param isVitalForParent the new vital for parent
	 */
	public void setVitalForParent(boolean isVitalForParent) {
		this.isVitalForParentFunction = isVitalForParent;
	}

	/**
	 * Checks if is provides grasping.
	 *
	 * @return true, if is provides grasping
	 */
	public boolean isProvidesGrasping() {
		return providesGrasping;
	}

	/**
	 * Sets the provides grasping.
	 *
	 * @param providesGrasping the new provides grasping
	 */
	public void setProvidesGrasping(boolean providesGrasping) {
		this.providesGrasping = providesGrasping;
	}

	/**
	 * Checks if is provides locomotion.
	 *
	 * @return true, if is provides locomotion
	 */
	public boolean isProvidesLocomotion() {
		return providesLocomotion;
	}

	/**
	 * Sets the provides locomotion.
	 *
	 * @param providesLocomotion the new provides locomotion
	 */
	public void setProvidesLocomotion(boolean providesLocomotion) {
		this.providesLocomotion = providesLocomotion;
	}

	/**
	 * Checks if is provides digestion.
	 *
	 * @return true, if is provides digestion
	 */
	public boolean isProvidesDigestion() {
		return providesDigestion;
	}

	/**
	 * Sets the provides digestion.
	 *
	 * @param providesDigestion the new provides digestion
	 */
	public void setProvidesDigestion(boolean providesDigestion) {
		this.providesDigestion = providesDigestion;
	}

	/**
	 * Checks if is provides movement.
	 *
	 * @return true, if is provides movement
	 */
	public boolean isProvidesMovement() {
		return providesMovement;
	}

	/**
	 * Sets the provides movement.
	 *
	 * @param providesMovement the new provides movement
	 */
	public void setProvidesMovement(boolean providesMovement) {
		this.providesMovement = providesMovement;
	}

	/**
	 * Checks if is provides cognition.
	 *
	 * @return true, if is provides cognition
	 */
	public boolean isProvidesCognition() {
		return providesCognition;
	}

	/**
	 * Sets the provides cognition.
	 *
	 * @param providesCognition the new provides cognition
	 */
	public void setProvidesCognition(boolean providesCognition) {
		this.providesCognition = providesCognition;
	}

	/**
	 * Checks if is provides sight.
	 *
	 * @return true, if is provides sight
	 */
	public boolean isProvidesSight() {
		return providesSight;
	}

	/**
	 * Sets the provides sight.
	 *
	 * @param providesSight the new provides sight
	 */
	public void setProvidesSight(boolean providesSight) {
		this.providesSight = providesSight;
	}

	/**
	 * Checks if is provides hearing.
	 *
	 * @return true, if is provides hearing
	 */
	public boolean isProvidesHearing() {
		return providesHearing;
	}

	/**
	 * Sets the provides hearing.
	 *
	 * @param providesHearing the new provides hearing
	 */
	public void setProvidesHearing(boolean providesHearing) {
		this.providesHearing = providesHearing;
	}

	/**
	 * Checks if is provides blood.
	 *
	 * @return true, if is provides blood
	 */
	public boolean isProvidesBlood() {
		return providesBlood;
	}

	/**
	 * Sets the provides blood.
	 *
	 * @param providesBlood the new provides blood
	 */
	public void setProvidesBlood(boolean providesBlood) {
		this.providesBlood = providesBlood;
	}

	/**
	 * Checks if is provides breath.
	 *
	 * @return true, if is provides breath
	 */
	public boolean isProvidesBreath() {
		return providesBreath;
	}

	/**
	 * Sets the provides breath.
	 *
	 * @param providesBreath the new provides breath
	 */
	public void setProvidesBreath(boolean providesBreath) {
		this.providesBreath = providesBreath;
	}

	/**
	 * Checks if is provides support.
	 *
	 * @return true, if is provides support
	 */
	public boolean isProvidesSupport() {
		return providesSupport;
	}

	/**
	 * Sets the provides support.
	 *
	 * @param providesSupport the new provides support
	 */
	public void setProvidesSupport(boolean providesSupport) {
		this.providesSupport = providesSupport;
	}

	/**
	 * Checks if is can bleed.
	 *
	 * @return true, if is can bleed
	 */
	public boolean isCanBleed() {
		return canBleed;
	}

	/**
	 * Sets the can bleed.
	 *
	 * @param canBleed the new can bleed
	 */
	public void setCanBleed(boolean canBleed) {
		this.canBleed = canBleed;
	}

	/**
	 * Checks if is can break.
	 *
	 * @return true, if is can break
	 */
	public boolean isCanBreak() {
		return canBreak;
	}

	/**
	 * Sets the can break.
	 *
	 * @param canBreak the new can break
	 */
	public void setCanBreak(boolean canBreak) {
		this.canBreak = canBreak;
	}

	/**
	 * Checks if is can shear.
	 *
	 * @return true, if is can shear
	 */
	public boolean isCanShear() {
		return canShear;
	}

	/**
	 * Sets the can shear.
	 *
	 * @param canShear the new can shear
	 */
	public void setCanShear(boolean canShear) {
		this.canShear = canShear;
	}

	/**
	 * Checks if is can pierce.
	 *
	 * @return true, if is can pierce
	 */
	public boolean isCanPierce() {
		return canPierce;
	}

	/**
	 * Sets the can pierce.
	 *
	 * @param canPierce the new can pierce
	 */
	public void setCanPierce(boolean canPierce) {
		this.canPierce = canPierce;
	}

	/**
	 * Checks if is can rot.
	 *
	 * @return true, if is can rot
	 */
	public boolean isCanRot() {
		return canRot;
	}

	/**
	 * Sets the can rot.
	 *
	 * @param canRot the new can rot
	 */
	public void setCanRot(boolean canRot) {
		this.canRot = canRot;
	}

	/**
	 * Gets the relative size.
	 *
	 * @return the relative size
	 */
	public float getRelativeSize() {
		return relativeSize;
	}

	/**
	 * Sets the relative size.
	 *
	 * @param relativeSize the new relative size
	 */
	public void setRelativeSize(float relativeSize) {
		this.relativeSize = relativeSize;
	}

	/**
	 * Gets the attached parts.
	 *
	 * @return the attached parts
	 */
	public ArrayList<BodyPart> getAttachedParts() {
		return attachedParts;
	}

	/**
	 * Sets the attached parts.
	 *
	 * @param attachedParts the new attached parts
	 */
	public void setAttachedParts(ArrayList<BodyPart> attachedParts) {
		this.attachedParts = attachedParts;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the material.
	 *
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the material.
	 *
	 * @param material the new material
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	/**
	 * Prints the parts.
	 *
	 * @return the string
	 */
	public String printParts() {
		String buffer = "";

		for (BodyPart i : this.attachedParts) {
			buffer = buffer + i.printParts() + "\n";
		}

		return "* " + id + " - " + name + " - " + description + "\n" + buffer;
	}
}
