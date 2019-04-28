package com.skywatch.service;

import java.util.ArrayList;

import com.skywatch.service.Node;
import com.skywatch.service.InformationGainService;
import com.skywatch.model.InformationGainData;
import org.springframework.stereotype.Service;

@Service
class DecisionTreeService {

    private final InformationGainData informationGainData;
    private final InformationGainService informationGainService;
    private Node root;
    private ArrayList<Node> nodeArray;
    

    public DecisionTreeService(String attribute, Node parent, InformationGainData informationGainData, InformationGainService informationGainService){
        this.informationGainData = informationGainData;
        this.informationGainService = informationGainService;
        this.root = new Node(attribute, parent);
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

    public ArrayList<Node> setTopNode(int index){
        switch(index){
            case 0:
                root = new Node("rating", null);
                nodeArray.add(root);
                // TODO left
                // TODO right
            
            case 1:
                root = new Node("modelAge", null);
                nodeArray.add(root);
                // TODO left
                // TODO right

            case 2:
                root = new Node("firstFlight", null);
                nodeArray.add(root);
                // TODO left
                // TODO right

            case 3:
                root = new Node("pilotAge", null);
                nodeArray.add(root);
                // TODO left
                // TODO right
                
        }

        return nodeArray;
        
    }

}
