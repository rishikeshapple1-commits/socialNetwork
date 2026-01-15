package socialNetwork;

/*
 * UserProfile.java
 * Rishikesh Soni
 * Last Update 05/01/2026
 *
 * User profile.
 */

import dataStuctures.Vector;

/**
 * A user profile in the social network.
 *
 * The user maintains:
 * - a wall of {@link StatusUpdate} posts sorted by timestamp (newest first)
 * - a list of friends (other {@link UserProfile} instances)
 * - a list of followed companies ({@link CorporateProfile} instances) used for ads
 */
public class UserProfile extends Profile {
	/** User age used for age-limited visibility filtering. */
	private final int age;

	/**
	 * User posts, kept sorted by timestamp (newest first).
	 * Element type: {@link StatusUpdate}.
	 */
	private final Vector wall = new Vector(10); // StatusUpdate

	/**
	 * Friend connections for this user.
	 * Element type: {@link UserProfile}.
	 */
	private final Vector friends = new Vector(10); // UserProfile

	/**
	 * Followed corporate profiles.
	 * Element type: {@link CorporateProfile}.
	 */
	private final Vector follows = new Vector(10); // CorporateProfile

	/**
	 * Creates a user with a username and age.
	 *
	 * @param username username for this profile
	 * @param age user age
	 */
	public UserProfile(String username, int age) {
		super(username);
		this.age = age;
	}

	/**
	 * Returns the username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return getName();
	}

	/**
	 * Returns the age.
	 *
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Adds a post to this user's wall.
	 *
	 * The wall is kept sorted by timestamp (newest first). If status is null, nothing happens.
	 *
	 * @param status status update to add
	 */
	public void postStatus(StatusUpdate status) {
		insertSortedByTimestampDesc(wall, status);
	}

	/**
	 * Prints the wall as the owner sees it.
	 *
	 * Ads and user posts are alternated: for every 4 user posts, one ad is inserted.
	 * Both are shown newest first. Ads come from companies followed by this user.
	 */
	public void printWall() {
		Vector allAds = collectAllAdsFromCompanyList(follows);
		printAlternating(wall, allAds, -1, true);
	}

	/**
	 * Prints this user's wall as seen by a friend.
	 *
	 * Only public (0) and friends-only (1) posts are shown and the viewer age is checked.
	 * Ads come from the viewer's followed companies.
	 *
	 * @param viewerAge age of the viewer
	 * @param viewerFollows companies followed by the viewer
	 */
	public void printWallForFriend(int viewerAge, Vector viewerFollows) {
		Vector visiblePosts = filterVisiblePostsForFriend(viewerAge);
		Vector allAds = collectAllAdsFromCompanyList(viewerFollows);
		printAlternating(visiblePosts, allAds, viewerAge, false);
	}

	/**
	 * Filters wall posts that are visible to a friend viewer of the given age.
	 *
	 * @param viewerAge age of the viewer
	 * @return a new Vector containing visible {@link StatusUpdate} posts in newest-first order
	 */
	private Vector filterVisiblePostsForFriend(int viewerAge) {
		Vector res = new Vector(10);
		for (int i = 0; i < wall.size(); i++) {
			StatusUpdate su = (StatusUpdate) wall.get(i);
			if (su.getAgeLimit() <= viewerAge && (su.getPrivacy() == 0 || su.getPrivacy() == 1)) {
				res.addLast(su);
			}
		}
		return res;
	}

	/**
	 * Collects ads from a provided list of companies.
	 *
	 * The returned list is sorted by timestamp (newest first).
	 *
	 * @param companies Vector of {@link CorporateProfile}
	 * @return sorted Vector of {@link StatusUpdate} ads
	 */
	private Vector collectAllAdsFromCompanyList(Vector companies) {
		Vector allAds = new Vector(10);
		for (int i = 0; i < companies.size(); i++) {
			CorporateProfile c = (CorporateProfile) companies.get(i);
			Vector ads = c.getAds();
			for (int j = 0; j < ads.size(); j++) {
				allAds.addLast(ads.get(j));
			}
		}
		return mergeSortedByTimestampDesc(allAds);
	}

	/**
	 * Merges a list of ads into a single sorted list.
	 *
	 * Each company's ad list is sorted already, but the combined list is not.
	 *
	 * @param unsorted list of ads
	 * @return sorted ads (newest first)
	 */
	private Vector mergeSortedByTimestampDesc(Vector unsorted) {
		Vector res = new Vector(10);
		for (int i = 0; i < unsorted.size(); i++) {
			insertSortedByTimestampDesc(res, (StatusUpdate) unsorted.get(i));
		}
		return res;
	}

	/**
	 * Prints posts and ads in an alternating pattern.
	 *
	 * Prints up to 4 posts, then 1 ad, repeating while items remain.
	 * If this is not the owner view, ads are filtered by viewerAge.
	 *
	 * @param posts posts to print
	 * @param ads ads to print
	 * @param viewerAge viewer age (used only when isOwner is false)
	 * @param isOwner true to print owner view
	 */
	private void printAlternating(Vector posts, Vector ads, int viewerAge, boolean isOwner) {
		int postIndex = 0;
		int adIndex = 0;

		while (postIndex < posts.size() || adIndex < ads.size()) {
			for (int i = 0; i < 4 && postIndex < posts.size(); i++) {
				System.out.println(posts.get(postIndex++));
			}

			if (adIndex < ads.size()) {
				StatusUpdate ad = (StatusUpdate) ads.get(adIndex++);
				if (isOwner || (viewerAge >= 0 && ad.getAgeLimit() <= viewerAge)) {
					System.out.println(ad);
				}
			}
		}
	}

	/**
	 * Inserts update into list while maintaining timestamp-descending order.
	 *
	 * @param list destination list
	 * @param update update to insert; if null this method does nothing
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
	 * Adds a friend link (one direction).
	 *
	 * @param other other user
	 */
	public void addFriend(UserProfile other) {
		if (other == null || other == this) {
			return;
		}
		if (!isFriend(other)) {
			friends.addLast(other);
		}
	}

	/**
	 * Returns true if this user already has the other user as a friend.
	 *
	 * @param other other user
	 * @return true if other is in the friend list
	 */
	public boolean isFriend(UserProfile other) {
		if (other == null) {
			return false;
		}
		for (int i = 0; i < friends.size(); i++) {
			if (friends.get(i) == other) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the friend list.
	 *
	 * @return internal friend list (element type: {@link UserProfile})
	 */
	public Vector getFriends() {
		return friends;
	}

	/**
	 * Follows a company (if not already following).
	 *
	 * @param company company profile to follow
	 */
	public void follow(CorporateProfile company) {
		if (company == null) {
			return;
		}
		for (int i = 0; i < follows.size(); i++) {
			if (follows.get(i) == company) {
				return;
			}
		}
		follows.addLast(company);
	}

	/**
	 * Returns the list of followed companies.
	 *
	 * @return internal list of followed companies (element type: {@link CorporateProfile})
	 */
	public Vector getFollows() {
		return follows;
	}

	@Override
	public String toString() {
		return "User Profile: " + getName() + ", " + age;
	}
}
