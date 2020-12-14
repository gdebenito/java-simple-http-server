package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {

    public static void main(String[] args) throws Exception {
        Map<String, String> env = System.getenv();
        int port = Integer.parseInt(env.get("PORT"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server is running in port: " + port);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Test!! Gon";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            this.writeDocument("Data", java.time.LocalDateTime.now());
            os.close();
        }

        /**
         * Use Streams when you are dealing with raw data
         * @param data
         */
        private void writeUsingOutputStream(String data, String name) {
            OutputStream os = null;
            try {
                os = new FileOutputStream(new File("./" + name));
                os.write(data.getBytes(), 0, data.length());
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
