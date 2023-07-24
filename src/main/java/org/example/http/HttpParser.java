package org.example.http;

import org.example.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private static final int SP = 0x20; //32
    private static final int CR = 0x0D; //13
    private static final int LF = 0x0A; //100

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest();
        try {
            parseRequestLine(reader, httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeaders(reader, httpRequest);
        parseBody(reader, httpRequest);
        return httpRequest;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();
        boolean methodParsed = false;
        boolean requestTargetParsed = false;
        int _byte;
        while((_byte = reader.read())>=0){
            if(_byte == CR){
                _byte = reader.read();
                if(_byte == LF){
                    if(!methodParsed || !requestTargetParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }

            if(_byte == SP){
                if(!methodParsed){
                    httpRequest.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    requestTargetParsed = true;
                } else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0,processingDataBuffer.length());
            }else{
                processingDataBuffer.append((char) _byte);
                if(!methodParsed && processingDataBuffer.length() > HttpMethod.MAX_LENGTH){
                    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                }
            }

        }

    }

    private void parseHeaders(InputStreamReader reader, HttpRequest httpRequest) {

    }

    private void parseBody(InputStreamReader reader, HttpRequest httpRequest) {

    }
}
