package com.corejava.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.corejava.dao.ComplaintDAO;
import com.corejava.model.Complaint;
import com.corejava.service.ComplaintService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {
    static ComplaintDAO dao = new ComplaintDAO();
    static ComplaintService service = new ComplaintService();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);

        server.createContext("/", new HomeHandler());
        server.createContext("/add", new AddComplaintHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started! Open browser: http://localhost:9999");
    }

    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            List<Complaint> complaints = dao.getAllComplaints();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Complaint Management System</title>");
            html.append(getStyles());
            html.append("</head><body>");

            html.append("<div class='container'>");
            html.append("<h1>Smart Complaint Management System</h1>");

            html.append("<div class='form-box'>");
            html.append("<h2>Add New Complaint</h2>");
            html.append("<form action='/add' method='POST'>");
            html.append("<label>User ID:</label>");
            html.append("<input type='text' name='userId' required><br>");
            html.append("<label>Title:</label>");
            html.append("<input type='text' name='title' required><br>");
            html.append("<label>Description:</label>");
            html.append("<textarea name='description' required></textarea><br>");
            html.append("<button type='submit'>Submit Complaint</button>");
            html.append("</form>");
            html.append("</div>");

            html.append("<h2>All Complaints</h2>");
            html.append("<table>");
            html.append("<tr><th>ID</th><th>Title</th><th>Category</th><th>Priority</th><th>Status</th></tr>");

            for (Complaint c : complaints) {
                String priorityClass = "low";
                if (c.getPriority().equals("HIGH")) priorityClass = "high";
                else if (c.getPriority().equals("MEDIUM")) priorityClass = "medium";

                html.append("<tr>");
                html.append("<td>").append(c.getComplaintId()).append("</td>");
                html.append("<td>").append(c.getTitle()).append("</td>");
                html.append("<td>").append(c.getCategory()).append("</td>");
                html.append("<td class='").append(priorityClass).append("'>").append(c.getPriority()).append("</td>");
                html.append("<td>").append(c.getStatus()).append("</td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("</div>");
            html.append("</body></html>");

            sendResponse(exchange, html.toString());
        }
    }

    static class AddComplaintHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String formData = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseFormData(formData);

                int userId = Integer.parseInt(params.get("userId"));
                String title = params.get("title");
                String description = params.get("description");

                String category = service.categorize(description);
                String priority = service.assignPriority(description);

                int newId = dao.getAllComplaints().size() + 1;

                Complaint c = new Complaint(newId, userId, title, description, category, priority, "PENDING");
                dao.insertComplaint(c);
            }

            exchange.getResponseHeaders().set("Location", "/");
            exchange.sendResponseHeaders(303, -1);
            exchange.close();
        }
    }

    static Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0], URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }

    static void sendResponse(HttpExchange exchange, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    static String getStyles() {
        return "<style>"
                + "body { font-family: Arial, sans-serif; background: #f4f6f8; margin: 0; padding: 20px; }"
                + ".container { max-width: 900px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
                + "h1 { color: #2c3e50; text-align: center; }"
                + "h2 { color: #34495e; }"
                + ".form-box { background: #ecf0f1; padding: 20px; border-radius: 8px; margin-bottom: 30px; }"
                + "label { display: block; margin-top: 10px; font-weight: bold; }"
                + "input, textarea { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ccc; border-radius: 5px; box-sizing: border-box; }"
                + "button { margin-top: 15px; padding: 10px 20px; background: #3498db; color: white; border: none; border-radius: 5px; cursor: pointer; }"
                + "button:hover { background: #2980b9; }"
                + "table { width: 100%; border-collapse: collapse; margin-top: 10px; }"
                + "th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }"
                + "th { background: #2c3e50; color: white; }"
                + ".high { color: #e74c3c; font-weight: bold; }"
                + ".medium { color: #f39c12; font-weight: bold; }"
                + ".low { color: #27ae60; font-weight: bold; }"
                + "</style>";
    }
}