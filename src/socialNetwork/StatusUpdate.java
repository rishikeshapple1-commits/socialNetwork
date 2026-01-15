package socialNetwork;

/*
 * StatusUpdate.java
 * Rishikesh Soni
 * Last Update 01/01/2026
 *
 * StatusUpdate in the network (post or ad).
 */

/**
 * StatusUpdate in the network (a normal post or an ad).
 */
public class StatusUpdate {
	/** Timestamp for this message. Higher numbers represent newer messages. */
	private int timestamp;
	/** Author name (username for a user post, company name for an ad). */
	private String author;
	/** Privacy setting: 0 public, 1 friends, 2 private. */
	private int privacy;
	/** Minimum viewer age required to view this message. */
	private int ageLimit;
	/** Message content text. */
	private String content;
	/** True if this update represents a paid advertisement. */
	private boolean paid; // for ads

	/**
	 * Creates a normal user post.
	 *
	 * @param timestamp timestamp (higher means newer)
	 * @param author author name
	 * @param privacy privacy setting (0 public, 1 friends, 2 private)
	 * @param ageLimit minimum viewer age
	 * @param content message content
	 */
	public StatusUpdate(int timestamp, String author, int privacy, int ageLimit, String content) {
		this.timestamp = timestamp;
		this.author = author;
		this.privacy = privacy;
		this.ageLimit = ageLimit;
		this.content = content;
		this.paid = false;
	}

	/**
	 * Creates an ad post.
	 *
	 * @param timestamp timestamp (higher means newer)
	 * @param author author name (company)
	 * @param privacy privacy setting (typically 0 for ads)
	 * @param ageLimit minimum viewer age
	 * @param content message content
	 * @param paid whether this ad is paid
	 */
	public StatusUpdate(int timestamp, String author, int privacy, int ageLimit, String content, boolean paid) {
		this(timestamp, author, privacy, ageLimit, content);
		this.paid = paid;
	}

	/**
	 * Returns the timestamp.
	 *
	 * @return timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the author name.
	 *
	 * @return author name
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Returns the privacy setting (0 public, 1 friends, 2 private).
	 *
	 * @return privacy setting
	 */
	public int getPrivacy() {
		return privacy;
	}

	/**
	 * Returns the minimum viewer age.
	 *
	 * @return age limit
	 */
	public int getAgeLimit() {
		return ageLimit;
	}

	/**
	 * Returns the message text.
	 *
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns true if this update is a paid ad.
	 *
	 * @return true if paid
	 */
	public boolean isPaid() {
		return paid;
	}

	@Override
	public String toString() {
		return "Status update: " + timestamp + ", " + author + ", " + privacy + ", " + ageLimit + ", " + content;
	}
}
