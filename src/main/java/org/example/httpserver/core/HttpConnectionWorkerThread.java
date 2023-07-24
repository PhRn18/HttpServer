package org.example.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


            String html = "<html><head><title>Simple Java Http Server</title></head><body><h1>This page was server using my simple java http server</h1></body></html>";

            final String CRLF = "\n\r"; //13 , 10

            String response =
                    "HTTP/1.1 200 OK" +CRLF+//Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: "+html.getBytes().length+CRLF+CRLF+html+CRLF+CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info(" * Connection Processing finished...");
        }catch (IOException e){
            LOGGER.error("Problem with communication",e);
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException ignored) {}
        }
    }
}
