// package com.skywatch.model;

// import java.util.Arrays;

// import javax.persistence.Entity;

// import com.skywatch.model.InformationGainData;
// import com.skywatch.service.InformationGainService;;

// class Node {
//     Crash crashKey;
//     boolean boolKey;
//     Node left, right;

//     public Node(boolean bool) {
//         crashKey = null;
//         boolKey = bool;
//         left = right = null;
//     }

//     public Node(Crash crashObject){
//         crashKey = crashObject;
//         boolKey = false;
//         left = right = null;

//     }
    
// }

// class DecisionTree {

//     public DecisionTree(){}


//     Node root;

//     DecisionTree(Crash crashObject) {
//         root = new Node(crashObject);
//     }

//     DecisionTree(boolean bool) {
//         root = new Node(bool);
//     }

// }