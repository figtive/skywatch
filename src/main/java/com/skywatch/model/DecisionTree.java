package com.skywatch.model;


class Node {
    Crash crashKey;
    boolean boolKey;
    Node left, right;

    public Node(boolean bool) {
        crashKey = null;
        boolKey = bool;
        left = right = null;
    }

    public Node(Crash crashObject){
        crashKey = crashObject;
        boolKey = false;
        left = right = null;

    }
    
}

class DecisionTree {
    Node root;

    DecisionTree(Crash crashObject) {
        root = new Node(crashObject);
    }

    DecisionTree(boolean bool) {
        root = new Node(bool);
    }
    
}