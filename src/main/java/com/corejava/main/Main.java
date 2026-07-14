package com.corejava.main;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Before server");
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 9999), 0);
        System.out.println("Server created");
        server.createContext("/", exchange -> {
            String response = "Hello World";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        server.start();
        System.out.println("Server Started...");
    }
}