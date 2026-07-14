package com.corejava.service;
public class ComplaintService {
        public String categorize(String description) {
            description = description.toLowerCase();
            if (description.contains("wifi") || description.contains("internet"))
                return "IT";
            else if (description.contains("water") || description.contains("leak"))
                return "Plumbing";
            else if (description.contains("electric") || description.contains("power"))
                return "Electrical";
            else
                return "General";
        }
        public String assignPriority(String description) {
            description = description.toLowerCase();
            if (description.contains("urgent") || description.contains("safety") || description.contains("not working"))
                return "HIGH";
            else if (description.contains("delay"))
                return "MEDIUM";
            else
                return "LOW";
        }
    }
