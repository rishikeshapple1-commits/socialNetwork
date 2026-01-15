package socialNetwork;

/*
 * Main.java
 * Rishikesh Soni
 * Last update 09/01/2026
 *
 * testing for the social network.
 */

public class Main {
	public static void main(String[] args) {
		SocialNetwork sn = new SocialNetwork();

		// Basic setup
		sn.createUserProfile("Rishi", 26);
		sn.createUserProfile("Hemant", 25);
		sn.createUserProfile("Salman", 27);

		// Add an underage user to test ageLimit filtering
		sn.createUserProfile("Dinesh", 16);

		sn.createCorporateProfile("Synopsys");
		sn.createCorporateProfile("Walmart");
		sn.createCorporateProfile("Nvidia");

		// Follow only Synopsys for Rishi (used to verify: ads shown only from followed companies)
		sn.follow("Rishi", "Synopsys");

		// Ads with out-of-order timestamps (verify: chronological newest-first, and follow filter)
		sn.postAd("Synopsys", "Synopsys ad newest", 0, true, 200);
		sn.postAd("Synopsys", "Synopsys ad older", 0, true, 50);
		sn.postAd("Walmart", "Walmart ad (not shown on Rishi owner wall; shown when Walmart followers view Rishi)", 0,
				true, 150);
		sn.postAd("Nvidia", "Nvidia ad (not shown on Rishi owner wall; shown when Nvidia followers view Rishi)", 0, true,
				180);

		// Rishi posts out-of-order timestamps (verify: wall kept newest-first)
		sn.postStatus("Rishi", "Public ts=100", 0, 0, 100);
		sn.postStatus("Rishi", "Public ts=10", 0, 0, 10);
		sn.postStatus("Rishi", "Friends ts=90", 1, 0, 90);
		sn.postStatus("Rishi", "Private ts=95", 2, 0, 95);
		sn.postStatus("Rishi", "18+ public ts=110", 0, 18, 110);
		sn.postStatus("Rishi", "Public ts=105", 0, 0, 105);
		sn.postStatus("Rishi", "Public ts=20", 0, 0, 20);

		System.out.println();
		System.out.println("Wall of Rishi (owner view) - newest-first and 4-posts-then-ad");
		System.out.println("Check: newest-first ordering (timestamps inserted out of order)");
		System.out.println("Check: 4 posts then 1 ad pattern");
		System.out.println("Check: owner wall shows ads only from companies the owner follows (Rishi follows Synopsys)");
		sn.printWallOf("Rishi");
		System.out.println(
				"Expected: posts sorted by timestamp desc; after each 4 posts, a Synopsys ad; no Walmart/Nvidia ads on Rishi owner wall)");

		// Friends
		sn.connect("Hemant", "Rishi");
		sn.connect("Salman", "Rishi");

		// Connect underage user as friend so they can view FRIENDS privacy posts (but age limit should still apply)
		sn.connect("Dinesh", "Rishi");

		// Viewer follows (used to verify: friend wall ads come from viewer follows)
		sn.follow("Hemant", "Walmart");
		sn.follow("Salman", "Nvidia");

		// Underage viewer follows Synopsys so ads will be candidates
		sn.follow("Dinesh", "Synopsys");

		// Add one restricted ad to Synopsys to verify viewer age limit filtering on ads
		sn.postAd("Synopsys", "Synopsys ad 18+", 18, true, 210);

		System.out.println();
		System.out.println("Hemant views Rishi wall - privacy + viewer-based ads");
		System.out.println("Check: private posts hidden for non-owner");
		System.out.println("Check: friends-only posts visible to connected friends");
		System.out.println("Check: ads come from viewer followed companies (Hemant follows Walmart)");
		System.out.println("Check: age limits enforced for posts/ads");
		sn.printWallOfAFriend("Hemant", "Rishi");
		System.out.println("Expected: visible posts exclude privacy=2; ads are Walmart; ageLimit applied");

		System.out.println();
		System.out.println("Salman views Rishi wall - viewer-based ads");
		System.out.println("Check: ads come from viewer followed companies (Salman follows Nvidia)");
		sn.printWallOfAFriend("Salman", "Rishi");

		System.out.println();
		System.out.println("Dinesh (age 16) views Rishi wall - ageLimit filtering");
		System.out.println("Check: age-limited post (18+) should NOT be shown");
		System.out.println("Check: age-limited ad (18+) should NOT be shown");
		sn.printWallOfAFriend("Dinesh", "Rishi");

		// Path and distance checks
		System.out.println();
		System.out.println("Distance/path (corporates allowed)");
		System.out.println("Check: graph distance between two profiles");
		System.out.println("Check: path can be printed");
		System.out.println("Check: corporate follow links count when corporates are allowed");
		sn.follow("Hemant", "Synopsys");
		sn.follow("Salman", "Synopsys");
		System.out.println("distance(Rishi, Hemant) = " + sn.distance("Rishi", "Hemant"));
		System.out.print("path(Rishi to Hemant) = ");
		sn.printPath("Rishi", "Hemant");

		System.out.println();
		System.out.println("Distance/path (corporates excluded)");
		System.out.println("Check: corporate nodes ignored in distance/path mode");
		System.out.println(
				"distanceExcludeCorporate(Salman, Hemant) = " + sn.distanceExcludeCorporate("Salman", "Hemant"));
		System.out.print("pathExcludeCorporate(Salman to Hemant) = ");
		sn.printPathExcludeCorporate("Salman", "Hemant");
	}
}
