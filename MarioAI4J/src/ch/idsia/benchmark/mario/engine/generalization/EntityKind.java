package ch.idsia.benchmark.mario.engine.generalization;

/**
 * Describes what the entity is like. You can determine how to treat an entity according to its {@link EntityKind}.
 * @author Jimmy
 */
public enum EntityKind {

	/**
	 * Entity is DANGEROUS and CANNOT BE KILLED.
	 */
	DANGER_INVINCIBLE("!!", 0, true, false, false, false),
	/**
	 * Entity that is DANGEROUS, can be shooted but cannot be squished.
	 */
	DANGER_SHOOTABLE("!S", 1, true, true, false, false),
	/**
	 * Entity that is DANGEROUS, can be squished, but cannot be shooted out.
	 * There is NO such entity in Mario...
	 */
	//DANGER_SQUISHABLE("!Q", 2, true, false, false, false),
	/**
	 * Entity that is DANGEROUS and is both shootable and squishable.
	 */
	DANGER_SHOOTABLE_SQUISHABLE("!K", 3, true, true, true, false),	
	/**
	 * Entity that is safe to touch and collectible. {@link EntityType#PRINCESS}
	 */
	COLLECTIBLE(".C", 4, false, false, false, true),	
	/**
	 * Entity that is safe to touch but also shootable. {@link EntityType#SHELL_STILL}
	 */
	SAFE_SHOOTABLE(".S", 5, false, false, false, false),
	/**
	 * Entity that is safe to touch but is not collectible.
	 */
	SAFE(".", 6, false, false, false, false);
	
	/**
	 * {@link EntityKind} that is the most dangerous one.
	 */
	public static EntityKind KIND_THE_MOST_DANGEROUS = EntityKind.DANGER_INVINCIBLE;
	/**
	 * {@link EntityKind} that is the least dangerous one.
	 */
	public static EntityKind KIND_THE_LEAST_DANGEROUS = EntityKind.SAFE;
	
	private String debug;
	
	private int threatLevel;
	
	private boolean dangerous;
	
	private boolean shootable;
	
	private boolean squishy;
	
	private boolean collectible;
	
	private EntityKind(String debug, int threatLevel, boolean dangerous, boolean shootable, boolean squishy, boolean collectible) {
		this.debug = debug;
		this.threatLevel = threatLevel;
		this.dangerous = dangerous;
		this.shootable = shootable;
		this.squishy = squishy;
		this.collectible = collectible;
	}
	
	/**
	 * Debug output for receptive field (grid) visualization.
	 * @return
	 */
	public String getDebug() {
		return debug;
	}

	/**
	 * Lower the number is bigger a threat!
	 * @return
	 */
	public int getThreatLevel() {
		return threatLevel;
	}
	
	/**
	 * Can this entity be picked up? Collected? (E.g. coins, collectible mushrooms, flowers, etc.)
	 * @return
	 */
	public boolean isCollectible() {
		return collectible;
	}

	/**
	 * Can this entity be shot? (Killed by shooting. Note that SHELL is not SHOOTABLE.)
	 * @return
	 */
	public boolean isShootable() {
		return shootable;
	}

	/**
	 * Can this entity be killed by jumping on it or shooting it out? (Note that SHELL is not SQUISHY.)
	 * @return
	 */
	public boolean isSquishy() {
		return squishy;
	}

	/**
	 * Is this entity dangerous?
	 * @return
	 */
	public boolean isDangerous() {
		return dangerous;
	}
	
}
