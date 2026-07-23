package com.arcanelaboratory.untitledrpg.utils;

import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.PriorityQueue;
import java.util.Comparator;

public class AStarPathfinder {
    public MapManager manager;

    public AStarPathfinder(MapManager mapManager){
        this.manager = mapManager;
    }

    public Array<Vector2> findPath(int startX, int startY, int targetX, int targetY){
        // Guard: If target tile is directly inside a wall, abort immediately
        if (manager.isCellBlocked(targetX, targetY)) return null;

        // Open list tracks nodes to evaluate. Sorts automatically so the lowest fCost node is evaluated first.
//        PriorityQueue<PathfindingNode> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.totalCost));
        PriorityQueue<PathfindingNode> openList = new PriorityQueue<>(new Comparator<PathfindingNode>() {
            @Override
            public int compare(PathfindingNode a, PathfindingNode b) {
                if (a.aCost == b.aCost) {
                    // Tie-breaker: choose the node closest to the target
                    return Float.compare(a.totalCost, b.bCost);
                }
                return Float.compare(a.aCost, b.aCost);
            }
        });
        // Track closed grid spaces using a flat tracker table
        boolean[][] closedSet = new boolean[manager.getMapWidthTiles()][manager.getMapHeightTiles()];

        PathfindingNode startNode = new PathfindingNode(startX, startY);
        PathfindingNode targetNode = new PathfindingNode(targetX, targetY);

        openList.add(startNode);

        while (!openList.isEmpty()) {
            PathfindingNode currentNode = openList.poll(); // Grab lowest fCost node

            // Success! We found the target coordinate
            if (currentNode.x == targetNode.x && currentNode.y == targetNode.y) {
                return retracePath(startNode, currentNode);
            }

            closedSet[currentNode.x][currentNode.y] = true;

            Array<PathfindingNode> neighbors = getNeighbors(currentNode);

            for (PathfindingNode neighbor : neighbors) {
                if (manager.isCellBlocked(neighbor.x, neighbor.y) || closedSet[neighbor.x][neighbor.y]) {
                    System.out.println("blocked or skipped " + neighbor.x + "," + neighbor.y);
                    continue; // Skip if already checked or physically blocked
                }

                // Cost calculation (moving 1 tile over costs 1.0 unit weight)
                float newMovementCostToNeighbor = currentNode.aCost + neighbor.thisCost;

                // Check if this path step is shorter than an entry already in our work queue
                boolean inOpenList = false;
                for (PathfindingNode openNode : openList) {
                    if (openNode.x == neighbor.x && openNode.y == neighbor.y) {
                        inOpenList = true;
                        if (newMovementCostToNeighbor < openNode.aCost) {
                            openNode.aCost = newMovementCostToNeighbor;
                            openNode.parent = currentNode;
                            openNode.calculateTotalCost();
                        }
                        break;
                    }
                }

                if (!inOpenList) {
                    neighbor.aCost = newMovementCostToNeighbor;
                    neighbor.bCost = calculateOctileDistance(neighbor, targetNode);
                    neighbor.parent = currentNode;
                    neighbor.calculateTotalCost();
                    openList.add(neighbor);
                }
            }
        }

        return null; // No path found
    }
    private Array<PathfindingNode> getNeighbors(PathfindingNode node) {
        Array<PathfindingNode> neighbors = new Array<>();
        boolean northBlocked = manager.isCellBlocked(node.x, node.y + 1);
        boolean southBlocked = manager.isCellBlocked(node.x, node.y - 1);
        boolean eastBlocked  = manager.isCellBlocked(node.x + 1, node.y);
        boolean westBlocked  = manager.isCellBlocked(node.x - 1, node.y);

        // 1. Cardinal Neighbors (Cost: 1.0)
        if (!northBlocked) neighbors.add(new PathfindingNode(node.x, node.y + 1));
        if (!southBlocked) neighbors.add(new PathfindingNode(node.x, node.y - 1));
        if (!eastBlocked)  neighbors.add(new PathfindingNode(node.x + 1, node.y));
        if (!westBlocked)  neighbors.add(new PathfindingNode(node.x - 1, node.y));

        // 2. Diagonal Neighbors (Cost: 1.414) - Only add if we aren't cutting a wall corner
        if (!northBlocked && !eastBlocked) neighbors.add(new PathfindingNode(node.x + 1, node.y + 1, 1.414f)); // North-East
        if (!northBlocked && !westBlocked) neighbors.add(new PathfindingNode(node.x - 1, node.y + 1, 1.414f)); // North-West
        if (!southBlocked && !eastBlocked) neighbors.add(new PathfindingNode(node.x + 1, node.y - 1, 1.414f)); // South-East
        if (!southBlocked && !westBlocked) neighbors.add(new PathfindingNode(node.x - 1, node.y - 1, 1.414f)); // South-West
        return neighbors;
    }

    private float calculateOctileDistance(PathfindingNode a, PathfindingNode b) {
        float dx = Math.abs(a.x - b.x);
        float blueY = Math.abs(a.y - b.y);

        // Standard Octile formula: straight steps + diagonal steps
        return (dx + blueY) + (1.414f - 2f) * Math.min(dx, blueY);
    }

    private Array<Vector2> retracePath(PathfindingNode startNode, PathfindingNode endNode) {
        Array<Vector2> path = new Array<>();
        PathfindingNode currentNode = endNode;

        while (currentNode != startNode) {
            // Convert grid positions back to world units for tracking vectors
            path.add(new Vector2(currentNode.x, currentNode.y));
            currentNode = currentNode.parent;
        }

        path.reverse(); // Flip it so it goes from start -> finish
        return path;
    }
}
