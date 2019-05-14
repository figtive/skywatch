package com.skywatch.service;

import java.util.ArrayList;

@SuppressWarnings("unused")
class Node implements Comparable<Node>, TreePrinter.PrintableNode {
    private String attribute;
    private boolean value;
    private Node predecessor;
    private Node successor;
    private double gain;
    private double trueProbabilityGiven;

    Node(String attribute, Node predecessor, boolean value, double gain) {
        this.attribute = attribute;
        this.predecessor = predecessor;
        this.value = value;
        this.gain = gain;
        this.successor = null;
        this.trueProbabilityGiven = 0.5;
    }

    public Node(String attribute, boolean value, Node predecessor) {
        this.attribute = attribute;
        this.value = value;
        this.predecessor = predecessor;
    }

    public Node(String attribute) {
        this.attribute = attribute;
    }

    String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public Node getSuccessor() {
        return successor;
    }

    Node setSuccessor(Node successor) {
        this.successor = successor;
        return successor;
    }

    @Override
    public int compareTo(Node o) {
        if (this.gain == o.getGain()) {
            return 0;
        }
        return this.gain > o.getGain() ? 1 : -1;
    }

    private double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public double getTrueProbabilityGiven() {
        return trueProbabilityGiven;
    }

    public void setTrueProbabilityGiven(double trueProbabilityGiven) {
        this.trueProbabilityGiven = trueProbabilityGiven;
    }

    ArrayList<String> getAllAttribute() {
        ArrayList<String> out = new ArrayList<>();
        out.add(this.attribute);
        if (predecessor != null) {
            predecessor.getAllAttribute(out);
        }
        return out;
    }

    ArrayList<Boolean> getAllBoolean() {
        ArrayList<Boolean> out = new ArrayList<>();
        out.add(this.value);
        if (predecessor != null) {
            predecessor.getAllBoolean(out);
        }
        return out;
    }

    private void getAllAttribute(ArrayList<String> out) {
        out.add(this.attribute);
        if (predecessor != null) {
            predecessor.getAllAttribute(out);
        }
    }

    private void getAllBoolean(ArrayList<Boolean> out) {
        out.add(this.value);
        if (predecessor != null) {
            predecessor.getAllBoolean(out);
        }
    }

    @Override
    public TreePrinter.PrintableNode getLeft() {
        return !value ? successor: null;
    }

    @Override
    public TreePrinter.PrintableNode getRight() {
        return value ? successor: null;
    }

    @Override
    public String getText() {
        return attribute;
    }
}