package com.skywatch.service;

import com.skywatch.exception.CrashedFoundException;
import com.skywatch.exception.SafeFoundException;
import com.skywatch.model.Crash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class DecisionTreeService {

    private static final String[] attributes = new String[]{"rating", "modelAge", "pilotAge", "weather", "firstFlight"};

    private final NewInformationGainService newInformationGainService;

    public DecisionTreeService(NewInformationGainService newInformationGainService) {
        this.newInformationGainService = newInformationGainService;
    }

    public boolean predictCrash(Crash crash) {
        ArrayList<String> tempAttributes = new ArrayList<>(Arrays.asList(attributes));
        ArrayList<Node> tempArray = new ArrayList<>();

        for (String attribute : tempAttributes) {
            try {
                tempArray.add(new Node(attribute,
                        null,
                        crash.getBoolean(attribute),
                        newInformationGainService.getInformationGain(attribute,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                crash.getBoolean(attribute))));
            } catch (SafeFoundException e) {
                return false;
            } catch (CrashedFoundException e) {
                return true;
            }
        }

        Node root = Collections.max(tempArray);
        tempAttributes.remove(root.getAttribute());
        Node parent = root;
        Node tempNode;

        while (!tempAttributes.isEmpty()) {
            tempArray.clear();
            for (String attribute : tempAttributes) {
                try {
                    tempArray.add(new Node(attribute,
                            parent,
                            crash.getBoolean(attribute),
                            newInformationGainService.getInformationGain(attribute, parent.getAllAttribute(), parent.getAllBoolean(), crash.getBoolean(attribute))));
                } catch (SafeFoundException e) {
                    return false;
                } catch (CrashedFoundException e) {
                    return true;
                }
            }
            tempNode = Collections.max(tempArray);
            parent.setSuccessor(tempNode);
            parent = tempNode;
            tempAttributes.remove(parent.getAttribute());
        }
        return crash.getBoolean(parent.getAttribute());
    }
}