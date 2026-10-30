# DAA Assignment3 - Report and Defense 

## Problem 1 and 2 - Number of Provinces 

### Technical Summary & Architecture

For the graph representations in this assignment, problems 1 through 4 utilize standard matrix/complete graph approaches. A province in an undirected graph corresponds directly to a connected component.

* **Problem 1 (DFS Approach):** We maintain a `visited` boolean array and iterate through all cities from $0$ to $n-1$. If an unvisited city is encountered, it defines a new province. We increment our component counter and trigger a DFS, marking every reachable city within that component.
* **Problem 2 (DSU Approach):** We initialize $n$ independent sets where each city is its own parent. For every unique pair $(i, j)$ where $i < j$ and `isConnected[i][j] == 1`, we invoke `union(i, j)` with rank optimization and path compression. Each successful merge decreases the total component count by one.

### Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n^2)$ for both methods, as we must read the complete adjacency matrix. DSU operations run in near-constant time $\mathcal{O}(n^2 \cdot \alpha(n))$ where $\alpha$ is the inverse Ackermann function.
* **Space Complexity:** $\mathcal{O}(n)$ auxiliary space for the visited tracking structure, recursion call stack (DFS), or parent/rank arrays (DSU).

---

## Problem 3 and 4 - Min Cost Spanning Tree 

### Technical Summary & Architecture

The problem requires connecting all points on a 2D plane with minimum total edge weight using Manhattan distances ($\vert{}x_1 - x_2\vert{} + \vert{}y_1 - y_2\vert{}$), mapping directly to the Minimum Spanning Tree (MST) problem on a complete graph.

* **Problem 3 (Kruskal's Algorithm):** We generate all $n(n-1)/2$ possible edges, sort them by weight ascending, and iteratively select safe edges using a Disjoint Set Union (DSU) structure to prevent cycles until $n-1$ edges are secured.
* **Problem 4 (Prim's Algorithm):** We grow the MST starting from vertex $0$ by maintaining a `minDist` array tracking the cheapest connecting edge from the tree to each unvisited node. We iteratively extract the minimum outside vertex without constructing an explicit edge list.

### Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n^2 \log n)$ for Kruskal's due to edge sorting, whereas Prim's algorithm runs efficiently in $\mathcal{O}(n^2)$ time by scanning dense array structures without heap overhead.
* **Space Complexity:** $\mathcal{O}(n^2)$ for Kruskal's edge storage versus $\mathcal{O}(n)$ memory for Prim's `minDist` and `inTree` state arrays.

---

## Problem 5 and 6 - Software Dependency Cycles 

### Technical Summary & Architecture

For problems 5 through 10, to handle large constraints up to $n = 10^5$ and prevent `StackOverflowError` from deep recursive chains, graphs are flattened into primitive arrays (`head`, `next`, `to`) alongside explicit stack loops with state pointer arrays (`ptr`). Circular dependency groups map precisely to Strongly Connected Components (SCCs).

* **Problem 5 (Kosaraju's Algorithm):** Employs two iterative DFS passes—first on the original graph to compute vertex finishing times, and second on the transposed (reversed) graph in reverse order of completion to isolate each SCC.
* **Problem 6 (Tarjan's Algorithm):** Employs a single iterative DFS pass using discovery timestamps (`disc`), low-link boundary values (`low`), an active tracking flag (`onStack`), and a custom stack frame structure to identify SCC roots when `low[v] == disc[v]`.

### Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n + m)$ linear time for both algorithms, visiting every node and edge a constant number of times.
* **Space Complexity:** $\mathcal{O}(n + m)$ to maintain adjacency structures, index maps, low-link arrays, and explicit traversal stacks.

---

## Problem 7 and 8 - Course Schedule 

### Technical Summary & Architecture

Course prerequisites are modeled as directed edges ($b \to a$), where completing all courses requires proving the graph is completely free of directed cycles.

* **Problem 7 (DFS Cycle Detection):** Utilizes a 3-color vertex marking strategy (`WHITE` for unvisited, `GRAY` for nodes currently active on the recursive path, and `BLACK` for fully processed nodes) to distinguish back-edges (cycles) from diamond dependency patterns.
* **Problem 8 (Kahn's Topological Algorithm):** Computes incoming degree values (`inDegree`) for every vertex. Nodes with zero dependencies enter a queue. We iteratively poll elements, decrement neighbor degrees, and verify if the total processed count matches `numCourses`.

### Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n + m)$ linear bounds for graph construction and traversal steps.
* **Space Complexity:** $\mathcal{O}(n + m)$ for graph representation, color tracking states, queue structures, and execution stack memory.

---

## Problem 9 and 10 - Course Schedule II 

### Technical Summary & Architecture

Extends the course scheduling constraint verification by requiring an explicit valid topological ordering array, or returning an empty set if a dependency cycle is detected.

* **Problem 9 (DFS Topological Ordering):** Executes a 3-color DFS traversal while back-filling the results array in reverse finishing order (`order[--pos] = v`), safely avoiding explicit list reversals.
* **Problem 10 (Kahn's Queue Ordering):** Adapts Kahn's in-degree reduction approach where the output array serves directly as the processing queue buffer, tracking element extraction sequence to yield a correct topological arrangement.

### Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n + m)$ linear time complexity across vertex expansions and edge relaxations.
* **Space Complexity:** $\mathcal{O}(n + m)$ auxiliary memory usage for adjacency lists, degree arrays, and final sequence buffers.