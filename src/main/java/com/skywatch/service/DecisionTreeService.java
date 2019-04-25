package com.skywatch.service;

import java.util.Arrays;
import java.lang.Math;

import javax.persistence.Entity;

import com.skywatch.service.InformationGainService;

import org.springframework.stereotype.Service;

import com.skywatch.repository.*;
import com.skywatch.model.*;

@Service
class DecisionTree {

    private final InformationGainData informationGainData;
    private final InformationGainService informationGainService;
    Node root;
    

    public DecisionTree(String attribute, InformationGainData informationGainData, InformationGainService informationGainService){
        this.informationGainData = informationGainData;
        this.informationGainService = informationGainService;
        root = new Node(attribute);
    }

    public DecisionTree(boolean bool, InformationGainData informationGainData, InformationGainService informationGainService){
        this.informationGainData = informationGainData;
        this.informationGainService = informationGainService;
        root = new Node(bool);
    }

    public double findHighestInfoGain(double[] gainArray){
        gainArray[1] = informationGainData.getRating();
        gainArray[2] = informationGainData.getModelAge();
        gainArray[3] = informationGainData.getFirstFlight();
        gainArray[4] = informationGainData.getPilotAge();
        gainArray[5] = informationGainData.getWeather();

        double max = 0.0;
        int index = 0;
        for(int i = 0; i < gainArray.length; i++){
            if(gainArray[i] > max){
                max = gainArray[i];
                index = i;
            }
        }
        gainArray[index] *= -1;
        return index;
    }

    public void setRootNode(int index){
        switch(index){
            case 0:
                root = new Node("rating");
                // TODO left
                // TODO right
            
            case 1:
                root = new Node("modelAge");
                // TODO left
                // TODO right

            case 2:
                root = new Node("firstFlight");
                // TODO left
                // TODO right

            case 3:
                root = new Node("pilotAge");
                // TODO left
                // TODO right
                
        }
        
    }

}

class Node {
    String attribute;
    boolean boolKey;
    Node left, right;

    public Node(boolean bool) {
        attribute = null;
        boolKey = bool;
        left = right = null;
    }

    public Node(String attribute){
        attribute = attribute;
        boolKey = false;
        left = right = null;

    }
    
}