package socialNetwork;

/*
 * CorporateProfile.java
 * Rishikesh Soni
 * Last Update 04/01/2026
 *
 * Corporate profile.
 */

import dataStuctures.Vector;

/**
 * A company profile.
 *
 * It can publish advertisements represented by {@link StatusUpdate} objects.
 * Advertisements are stored in a {@link Vector} and kept sorted by timestamp (newest first).
 */
public class CorporateProfile extends Profile {
	/**
	 * List of ads posted by this company.
	 *
	 * Element type: {@link StatusUpdate}. list is always maintained sorted by timestamp
	 * descending (newest first).
	 */
	private final Vector ads = new Vector(10); // stores StatusUpdate

	/**
	 * Creates a company profile.
	 *
	 * @param companyName company name to use as the profile name
	 */
	public CorporateProfile(String companyName) {
		super(companyName);
	}

	/**
	 * Returns the company name.
	 *
	 * @return company name
	 */
	public String getCompanyName() {
		return getName();
	}

	/**
	 * Returns the list of ads.
	 *
	 * The returned list is the internal mutable representation and is kept sorted by timestamp
	 * (newest first).
	 *
	 * @return internal ads list
	 */
	public Vector getAds() {
		return ads;
	}

	/**
	 * Adds an ad to this company.
	 *
	 * The list is kept sorted by timestamp (newest first). If ad is null, nothing happens.
	 *
	 * @param ad advertisement status update to add
	 */
	public void postAd(StatusUpdate ad) {
		insertSortedByTimestampDesc(ads, ad);
	}

	/**
	 * Inserts update into list while maintaining timestamp-descending order.
	 *
	 * @param list destination list
	 * @param update update to insert; if null, nothing happens
	 */
	private void insertSortedByTimestampDesc(Vector list, StatusUpdate update) {
		if (update == null) {
			return;
		}

		int i = 0;
		while (i < list.size()) {
			StatusUpdate cur = (StatusUpdate) list.get(i);
			if (update.getTimestamp() >= cur.getTimestamp()) {
				break;
			}
			i++;
		}

		list.addLast(update);
		for (int j = list.size() - 1; j > i; j--) {
			Object tmp = list.get(j - 1);
			list.set(j - 1, list.get(j));
			list.set(j, tmp);
		}
	}

	/**
	 * Returns a String representation.
	 *
	 * @return  String
	 */
	@Override
	public String toString() {
		return "Corporate Profile: " + getName();
	}
}
