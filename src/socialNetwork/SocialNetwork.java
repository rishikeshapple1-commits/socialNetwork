package socialNetwork;

/*
 * SocialNetwork.java
 * Rishikesh Soni
 * Last Update 08/01/2026
 *
 * Social network
 */

import dataStuctures.DictionaryTree;
import dataStuctures.Queue;
import dataStuctures.Vector;

/**
 * Social network class.
 * It implements the iSocialNetwork interface and provides the functionality
 * for creating user and corporate profiles, posting status updates and ads,
 * connecting users, and managing friendships and follow relationships.
 */
public class SocialNetwork implements iSocialNetwork {

	/**
	 * Dictionary for fast user lookup by username.
	 * Key: {@link String}, Value: {@link UserProfile}.
	 */
	private final DictionaryTree usersByName = new DictionaryTree();

	/**
	 * Dictionary for fast corporate lookup by company name.
	 * Key: {@link String}, Value: {@link CorporateProfile}.
	 */
	private final DictionaryTree companiesByName = new DictionaryTree();

	/**
	 * Indexable list of user profiles used for BFS indexing.
	 * Element type: {@link UserProfile}.
	 */
	private final Vector users = new Vector(10);

	/**
	 * Indexable list of corporate profiles used for BFS indexing.
	 * Element type: {@link CorporateProfile}.
	 */
	private final Vector companies = new Vector(10);

	/**
	 * Finds a user by username.
	 *
	 * @param username username
	 * @return user profile or {@code null} if not found
	 */
	private UserProfile findUser(String username) {
		return (UserProfile) usersByName.findKey(username);
	}

	/**
	 * Finds a company by company name.
	 *
	 * @param companyName company name
	 * @return corporate profile or {@code null} if not found
	 */
	private CorporateProfile findCompany(String companyName) {
		return (CorporateProfile) companiesByName.findKey(companyName);
	}

	/**
	 * Returns the index of this user in the internal list, or -1.
	 *
	 * @param u user
	 * @return index or -1
	 */
	private int indexOfUser(UserProfile u) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i) == u) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the index of this company in the internal list, or -1.
	 *
	 * @param c company
	 * @return index or -1
	 */
	private int indexOfCompany(CorporateProfile c) {
		for (int i = 0; i < companies.size(); i++) {
			if (companies.get(i) == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Builds an adjacency list for the current graph (used by BFS).
	 * If corporates are included, they come after the users.
	 *
	 * @param includeCorporates true to include corporate nodes
	 * @return adjacency list where each entry is a {@link Vector} of neighbor indices
	 */
	private Vector buildAdjacency(boolean includeCorporates) {
		int userCount = users.size();
		int total = includeCorporates ? userCount + companies.size() : userCount;

		Vector adjacency = new Vector(total);
		for (int i = 0; i < total; i++) {
			adjacency.addLast(new Vector(10));
		}

		// user-user edges from friend lists
		for (int i = 0; i < userCount; i++) {
			UserProfile u = (UserProfile) users.get(i);
			Vector adj = (Vector) adjacency.get(i);
			Vector friends = u.getFriends();
			for (int j = 0; j < friends.size(); j++) {
				UserProfile f = (UserProfile) friends.get(j);
				int idx = indexOfUser(f);
				if (idx >= 0) {
					adj.addLast(Integer.valueOf(idx));
				}
			}
		}

		if (includeCorporates) {
			for (int i = 0; i < userCount; i++) {
				UserProfile u = (UserProfile) users.get(i);
				Vector adj = (Vector) adjacency.get(i);
				Vector follows = u.getFollows();
				for (int j = 0; j < follows.size(); j++) {
					CorporateProfile c = (CorporateProfile) follows.get(j);
					int cIdx = indexOfCompany(c);
					if (cIdx >= 0) {
						int cNode = userCount + cIdx;
						adj.addLast(Integer.valueOf(cNode));
						((Vector) adjacency.get(cNode)).addLast(Integer.valueOf(i));
					}
				}
			}
		}

		return adjacency;
	}

	/**
	 * Breadth-first search distance computation.
	 *
	 * @param start start node index
	 * @param goal goal node index
	 * @param adjacency adjacency list
	 * @param parentOut if non-null, filled with BFS parent pointers (for path printing)
	 * @return distance in number of edges, or -1 if unreachable
	 */
	private int bfsDistanceAndParent(int start, int goal, Vector adjacency, int[] parentOut) {
		int n = adjacency.size();
		int[] dist = new int[n];
		boolean[] visited = new boolean[n];

		for (int i = 0; i < n; i++) {
			dist[i] = -1;
			if (parentOut != null) {
				parentOut[i] = -1;
			}
		}

		Queue q = new Queue();
		visited[start] = true;
		dist[start] = 0;
		q.push(Integer.valueOf(start));

		while (!q.empty()) {
			int u = (Integer) q.pop();
			if (u == goal) {
				return dist[u];
			}

			Vector adj = (Vector) adjacency.get(u);
			for (int i = 0; i < adj.size(); i++) {
				int v = (Integer) adj.get(i);
				if (!visited[v]) {
					visited[v] = true;
					dist[v] = dist[u] + 1;
					if (parentOut != null) {
						parentOut[v] = u;
					}
					q.push(Integer.valueOf(v));
				}
			}
		}

		return -1;
	}

	@Override
	public void createUserProfile(String username, int age) {
		if (findUser(username) != null) {
			return;
		}
		UserProfile u = new UserProfile(username, age);
		users.addLast(u);
		usersByName.add(username, u);
	}

	@Override
	public void createCorporateProfile(String companyName) {
		if (findCompany(companyName) != null) {
			return;
		}
		CorporateProfile c = new CorporateProfile(companyName);
		companies.addLast(c);
		companiesByName.add(companyName, c);
	}

	@Override
	public void printWallOf(String username) {
		UserProfile user = findUser(username);
		if (user == null) {
			System.out.println("User not found.");
			return;
		}
		user.printWall();
	}

	@Override
	public void postStatus(String username, String status, int privacy, int ageLimit, int timestamp) {
		UserProfile user = findUser(username);
		if (user == null) {
			return;
		}
		StatusUpdate su = new StatusUpdate(timestamp, username, privacy, ageLimit, status);
		user.postStatus(su);
	}

	@Override
	public void postAd(String companyName, String ad, int ageLimit, boolean paid, int timestamp) {
		CorporateProfile company = findCompany(companyName);
		if (company == null) {
			return;
		}
		StatusUpdate adMsg = new StatusUpdate(timestamp, companyName, 0, ageLimit, ad, paid);
		company.postAd(adMsg);
	}

	@Override
	public void connect(String username1, String username2) {
		UserProfile u1 = findUser(username1);
		UserProfile u2 = findUser(username2);
		if (u1 == null || u2 == null) {
			return;
		}
		u1.addFriend(u2);
		u2.addFriend(u1);
	}

	@Override
	public void printFriendListOf(String username) {
		UserProfile u = findUser(username);
		if (u == null) {
			System.out.println("User not found.");
			return;
		}
		Vector friends = u.getFriends();
		for (int i = 0; i < friends.size(); i++) {
			System.out.println(((UserProfile) friends.get(i)).getUsername());
		}
	}

	@Override
	public void printWallOfAFriend(String username, String friendUsername) {
		UserProfile viewer = findUser(username);
		UserProfile friend = findUser(friendUsername);
		if (viewer == null || friend == null) {
			System.out.println("User not found.");
			return;
		}
		if (!viewer.isFriend(friend)) {
			System.out.println("Not friends.");
			return;
		}

		friend.printWallForFriend(viewer.getAge(), viewer.getFollows());
	}

	@Override
	public void follow(String username, String corporateName) {
		UserProfile u = findUser(username);
		CorporateProfile c = findCompany(corporateName);
		if (u == null || c == null) {
			return;
		}
		u.follow(c);
	}

	@Override
	public int distance(String username1, String username2) {
		UserProfile u1 = findUser(username1);
		UserProfile u2 = findUser(username2);
		if (u1 == null || u2 == null) {
			return -1;
		}

		int start = indexOfUser(u1);
		int goal = indexOfUser(u2);
		if (start < 0 || goal < 0) {
			return -1;
		}

		Vector adjacency = buildAdjacency(true);
		return bfsDistanceAndParent(start, goal, adjacency, null);
	}

	@Override
	public void printPath(String username1, String username2) {
		UserProfile u1 = findUser(username1);
		UserProfile u2 = findUser(username2);
		if (u1 == null || u2 == null) {
			System.out.println("User not found.");
			return;
		}

		int start = indexOfUser(u1);
		int goal = indexOfUser(u2);
		if (start < 0 || goal < 0) {
			System.out.println("User not found.");
			return;
		}

		Vector adjacency = buildAdjacency(true);
		int[] parent = new int[adjacency.size()];
		int d = bfsDistanceAndParent(start, goal, adjacency, parent);
		if (d < 0) {
			System.out.println("No path.");
			return;
		}

		Vector path = new Vector(d + 2);
		int cur = goal;
		while (cur != -1) {
			path.addLast(Integer.valueOf(cur));
			cur = parent[cur];
		}

		for (int i = path.size() - 1; i >= 0; i--) {
			int node = (Integer) path.get(i);
			if (node < users.size()) {
				System.out.print(((UserProfile) users.get(node)).getUsername());
			} else {
				int cIdx = node - users.size();
				System.out.print(((CorporateProfile) companies.get(cIdx)).getCompanyName());
			}
			if (i != 0) {
				System.out.print(" to ");
			}
		}
		System.out.println();
	}

	@Override
	public int distanceExcludeCorporate(String username1, String username2) {
		UserProfile u1 = findUser(username1);
		UserProfile u2 = findUser(username2);
		if (u1 == null || u2 == null) {
			return -1;
		}

		int start = indexOfUser(u1);
		int goal = indexOfUser(u2);
		if (start < 0 || goal < 0) {
			return -1;
		}

		Vector adjacency = buildAdjacency(false);
		return bfsDistanceAndParent(start, goal, adjacency, null);
	}

	@Override
	public void printPathExcludeCorporate(String username1, String username2) {
		UserProfile u1 = findUser(username1);
		UserProfile u2 = findUser(username2);
		if (u1 == null || u2 == null) {
			System.out.println("User not found.");
			return;
		}

		int start = indexOfUser(u1);
		int goal = indexOfUser(u2);
		if (start < 0 || goal < 0) {
			System.out.println("User not found.");
			return;
		}

		Vector adjacency = buildAdjacency(false);
		int[] parent = new int[adjacency.size()];
		int d = bfsDistanceAndParent(start, goal, adjacency, parent);
		if (d < 0) {
			System.out.println("No path.");
			return;
		}

		Vector path = new Vector(d + 2);
		int cur = goal;
		while (cur != -1) {
			path.addLast(Integer.valueOf(cur));
			cur = parent[cur];
		}

		for (int i = path.size() - 1; i >= 0; i--) {
			int node = (Integer) path.get(i);
			System.out.print(((UserProfile) users.get(node)).getUsername());
			if (i != 0) {
				System.out.print(" to ");
			}
		}
		System.out.println();
	}
}
