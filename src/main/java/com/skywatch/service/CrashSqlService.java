package com.skywatch.service;

import org.springframework.stereotype.Service;

@Service
public class CrashSqlService {
    String mapSql(String[] attributes, Boolean[] booleans) {
        StringBuilder out = new StringBuilder("select count(c.*) from crash_data.crash c");

        if (attributes.length == 0) {
            return out.toString();
        }

        out.append(" where c.").append(swapAttribute(attributes[0])).append(" = ").append(booleans[0]);
        for (int i = 1; i < attributes.length; i++) {
            out.append(" and ");
            out.append("c.").append(swapAttribute(attributes[i])).append(" = ").append(booleans[i]);
        }
        out.append(";");

        return out.toString();
    }

    private String swapAttribute(String attribute) {
        switch (attribute) {
            case "pilotAge":
                return "pilot_age";
            case "modelAge":
                return "model_age";
            case "firstFlight":
                return "first_flight";
            default:
                return attribute;
        }
    }
}
