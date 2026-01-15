package socialNetwork;

/*
 * Profile.java
 * Rishikesh Soni
 * Last Update 27/12/2025
 *
 * Base class for profiles (users and companies).
 */

/**
 * Base type for profiles in the network.
 *
 * A profile has a single immutable name. Concrete subclasses ({@link UserProfile} and
 * {@link CorporateProfile}) provide additional fields/behavior and implements a
 * {@link #toString()} String.
 */
public abstract class Profile {

	/** Profile name (username or company name). */
	private final String name;

	/**
	 * Creates a profile with the given name.
	 *
	 * @param name profile name (username or company name)
	 */
	protected Profile(String name) {
		this.name = name;
	}

	/**
	 * Returns the profile name.
	 *
	 * @return profile name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a String representation of this profile.
	 *
	 * @return a String
	 */
	@Override
	public abstract String toString();
}
