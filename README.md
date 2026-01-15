# SocialNetwork

## Project structure

- `src/socialNetwork/` – social network implementation (profiles, posts/ads, connections, BFS distance/path)
- `src/dataStuctures/` – custom data structure implementations used by the project
- `bin/` – compiled `.class` output (generated)

## Requirements

- Java JDK **21** (or newer)

> Note: If you compile with a newer JDK, make sure you also run with a compatible Java runtime.
> To avoid runtime version issues, compile using `--release 21`.

## How to compile

From the project root:

```bash
rm -rf bin/*
find src -name "*.java" -print0 | xargs -0 javac --release 21 -d bin
```

## How to run

Run the demo program (`Main`):

```bash
java -cp bin socialNetwork.Main
```

## Notes

- `Main.java` – demo/test scenarios for posting, wall printing, friends/follows, privacy/age-limit filtering, ad alternation, and BFS distance/path.
- `SocialNetwork.java` – implementation of `iSocialNetwork`; stores/looks up profiles, manages friendships/follows, posting, wall printing, and shortest distance/path via BFS.
- `iSocialNetwork.java` – public API (interface) listing the required operations.
- `Profile.java` – shared base class for profiles; common wall/post handling used by both user and corporate profiles.
- `UserProfile.java` – represents a person; stores age, friends, follows, and applies privacy/age-limit rules when printing a wall.
- `CorporateProfile.java` – represents a company; stores advertisements and supports ad posting/printing.
- `StatusUpdate.java` – post/ad record (author, content, timestamp, privacy/ageLimit flags, and paid flag for ads) stored on walls.

### Data structures used

- `Vector`: used for walls, ads lists, friend lists, follow lists, and the BFS adjacency list.
- `DictionaryTree`: maps profile names to profile objects for quick lookup (e.g., `username -> UserProfile`, `companyName -> CorporateProfile`).
- `Queue`: used by BFS when computing shortest distance/path in the graph.
- Primitive arrays (`int[]`, `boolean[]`): used inside BFS for `dist`, `visited`, and `parent` pointers.

### Why these choices

- `DictionaryTree` avoids repeated scans when accessing profiles by name.
- For shortest paths in an unweighted graph, BFS is appropriate and simple.
- Walls/ads are kept sorted by timestamp (newest first) by inserting into the correct position in a `Vector`.

## About `@SuppressWarnings`

This project uses `@SuppressWarnings("rawtypes", "unchecked")` in a few places due to data structures using raw `Comparable` types without generics. (due to time contraint).
