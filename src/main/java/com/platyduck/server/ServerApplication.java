package com.platyduck.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);

		try (ServerSocket serverSocket = new ServerSocket(7171)) {
			System.out.println("TCP Server started on port 7171");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

				handleClient(clientSocket);
			}
		} catch (IOException e) {
			System.err.println("Error in TCP Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

			String request = reader.readLine();
			System.out.println("Received message from client: " + request);

			// Process the request and generate a response
			String response = "Hello, client! Your message was: " + request;

			// Send the response back to the client
			writer.println(response);
			System.out.println("Sent response to client: " + response);

			// Close the client socket
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
			e.printStackTrace();
		}
	}


}
