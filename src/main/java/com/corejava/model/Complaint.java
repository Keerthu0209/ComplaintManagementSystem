package com.corejava.model;
public class Complaint {
        private int complaintId;
        private int userId;
        private String title;
        private String description;
        private String category;
        private String priority;
        private String status;

        public Complaint() {}
        public Complaint(int complaintId, int userId, String title, String description,
                         String category, String priority, String status) {
            this.complaintId = complaintId;
            this.userId = userId;
            this.title = title;
            this.description = description;
            this.category = category;
            this.priority = priority;
            this.status = status;
        }

        public int getComplaintId() {
            return complaintId;
        }
        public void setComplaintId(int complaintId) {
            this.complaintId = complaintId;
        }

        public int getUserId() {
            return userId;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title; }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category; }
        public void setCategory(String category) {
            this.category = category; }

        public String getPriority() {
            return priority;
        }
        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ID: " + complaintId + " Title: " + title + " Category: " + category +
                    "  Priority: " + priority + " Status: " + status;
        }
    }
