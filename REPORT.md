# DAA Assignment 3 - Report and Defense

## Problems 1 and 2 - Number of Provinces (10 points)

`isConnected` is the adjacency matrix of an undirected graph. A province is a connected component, so the answer is the number of components.

### Problem 1 - DFS

Each unvisited city starts a new DFS. The DFS marks the whole province, so every new start adds one to the count. Example: `[[1,1,0],[1,1,0],[0,0,1]]` gives {0,1} and {2}, so the answer is 2.

- Time: O(n^2), since DFS scans a full matrix row for each city.
- Memory: O(n). Recursion is safe because n <= 200.

### Problem 2 - DSU

The count starts at n. For each pair i < j with `isConnected[i][j] = 1` (only the upper half, because the matrix is symmetric), a successful union merges two sets and lowers the count by one. `find` uses path compression and `union` uses union by size.

- Time: O(n^2 · α(n)), which is O(n^2) in practice.
- Memory: O(n).

**Comparison:** both are O(n^2) because the whole matrix has to be read. DFS is shorter. DSU is better when edges arrive one at a time.

## Problems 3 and 4 - Min Cost to Connect All Points (10 points)

This is an MST on a complete graph. The weight of each edge is the Manhattan distance between its two points.

### Problem 3 - Kruskal

All n(n-1)/2 edges are built and sorted by weight. Going from the cheapest, an edge is taken if its endpoints are in different DSU sets. Otherwise it would make a cycle and is skipped. The loop stops after n-1 edges. `find` uses path halving.

- Time: O(n^2 log n) for sorting.
- Memory: O(n^2), about 500,000 edges at n = 1000.

### Problem 4 - Prim

The tree grows from point 0. `dist[i]` is the cheapest edge from the tree to point i. Each step adds the closest point outside the tree, adds its `dist` to the answer, and relaxes the other points through it. There is no heap: in a complete graph a simple O(n) scan per step is already optimal.

- Time: O(n^2).
- Memory: O(n).

**Comparison:** Prim is the better fit here because it never stores or sorts the O(n^2) edges.

## Problems 5 and 6 - Software Dependency Cycles (10 points)

A circular dependency group is a strongly connected component (SCC), and a module with no cycle is an SCC of size 1. The answer is the number of SCCs. With n up to 10^5, a chain of dependencies would overflow Java's recursion stack, so both solutions run DFS with an explicit stack. `idx[v]` remembers the next neighbour to visit.

### Problem 5 - Kosaraju

1. Run DFS on the original graph and record vertices in finish order.
2. Go through that order from the end and run DFS on the reversed graph. Each new start collects exactly one SCC.

This works because the vertex that finishes last is in a source component. In the reversed graph no edge leaves that component, so the second DFS stays inside it. Example 1 gives {0,1,2}, {3}, {4}, so the answer is 3.

- Time: O(n + m).
- Memory: O(n + m), since both graphs are stored.

### Problem 6 - Tarjan

This needs one DFS. `disc[v]` is the time v was discovered. `low[v]` is the smallest `disc` reachable from v's subtree through vertices still on the stack. A tree edge updates `low[v]` from `low[u]` after u finishes. An edge to a vertex on the stack updates it from `disc[u]`. When `low[v] == disc[v]`, v is the root of an SCC, and popping the stack down to v gives one group.

- Time: O(n + m).
- Memory: O(n + m).

**Comparison:** Tarjan uses one pass and no reversed graph. Kosaraju is easier to explain but goes through the graph twice.

## Problems 7 and 8 - Course Schedule (10 points)

Each pair [a, b] becomes an edge b -> a. All courses can be finished exactly when this graph has no cycle.

### Problem 7 - DFS (three colours)

Each course is in one of three states: 0 = unvisited, 1 = on the current path, 2 = done. Reaching a state-1 course means a back edge, so there is a cycle. Courses in state 2 are skipped, so each course is processed once. Example 2: `[[1,0],[0,1]]` goes 0 -> 1 -> 0 and finds a cycle, so the answer is false.

- Time: O(V + E).
- Memory: O(V + E). Recursion is fine because numCourses <= 2000.

### Problem 8 - Kahn

`indeg[i]` counts the prerequisites of course i that are not taken yet. Courses with indegree 0 go into the queue. Taking a course lowers the indegree of the courses that depend on it. Courses in a cycle wait on each other and never reach 0, so `done < numCourses` means there is a cycle.

- Time: O(V + E).
- Memory: O(V + E).

**Comparison:** DFS finds the cycle directly, while Kahn finds it by counting and needs no recursion.

## Problems 9 and 10 - Course Schedule II (10 points)

This uses the same graph as Problems 7 and 8, but now it returns a topological order. It returns an empty array if there is a cycle.

### Problem 9 - DFS

The cycle check is the same as in Problem 7. A course finishes only after every course that depends on it, so the reversed finish order is a valid schedule. Writing each finished course at `order[pos--]` builds that reversed order directly. Example 2 finishes in the order 3, 1, 2, 0, which gives [0,2,1,3].

- Time: O(V + E).
- Memory: O(V + E).

### Problem 10 - Kahn

This is Problem 8, but every course taken from the queue is added to the order. A course enters the queue only after all its prerequisites, so the queue order is valid. If `k < numCourses`, there is a cycle and the result is an empty array. Example 2 gives [0,1,2,3].

- Time: O(V + E).
- Memory: O(V + E).

**Comparison:** DFS builds the order from the back and uses recursion. Kahn builds it from the front, and its cycle check is a single comparison.
