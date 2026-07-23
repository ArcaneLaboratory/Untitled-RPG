package com.arcanelaboratory.untitledrpg.utils;

public class PathfindingNode {
    public int x, y;
    public float thisCost;
    public float aCost; // Distance from starting node, aka cost so far
    public float bCost; // Distance to target node, aka how far left to go
    public float totalCost; // aCost + bCost
    public PathfindingNode parent;

    public PathfindingNode(int x, int y){
        this.x = x;
        this.y = y;
        this.thisCost = 1.0f;
    }

    public PathfindingNode(int x, int y, float thisCost){
        this.x = x;
        this.y = y;
        this.thisCost = thisCost;
    }

    public void calculateTotalCost(){
        this.totalCost = this.aCost + this.bCost;
    }
}
