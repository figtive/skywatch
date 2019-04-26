package com.skywatch.service;

class Node {
    String attribute;
    boolean boolKey;
    Node parent;
    Node left, right;

    public Node(String attribute, Node parent){
        this.attribute = attribute;
        this.parent = parent;
        boolKey = false;
        left = right = null;

    }

    public Node(boolean boolKey, Node parent) {
        attribute = null;
        this.parent = parent;
        this.boolKey = boolKey;
        left = right = null;
    }
    
}