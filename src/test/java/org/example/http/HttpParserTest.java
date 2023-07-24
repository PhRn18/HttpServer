package org.example.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {
    private HttpParser httpParser;

    @BeforeAll
    void beforeClass(){
        httpParser = new HttpParser();
    }
    @Test
    void parseHttpRequest() {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpParser.parseHttpRequest(
                    generateValidGetTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(httpRequest.getMethod(),HttpMethod.GET);
    }
    @Test
    void parseBadHttpRequest() {
        try {
            HttpRequest httpRequest = httpParser.parseHttpRequest(
                    generateBadGetTestCase()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    }
    @Test
    void parseBadHttpRequest2() {
        try {
            HttpRequest httpRequest = httpParser.parseHttpRequest(
                    generateBadGetTestCase2()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    }
    @Test
    void parseBadHttpRequest3() {
        try {
            HttpRequest httpRequest = httpParser.parseHttpRequest(
                    generateBadGetTestCase3()
            );
            fail();
        } catch (HttpParsingException e) {
           assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }
    @Test
    void parseBadHttpRequest4() {
        try {
            HttpRequest httpRequest = httpParser.parseHttpRequest(
                    generateBadGetTestCase4()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }
    @Test
    void parseBadHttpRequest5() {
        try {
            HttpRequest httpRequest = httpParser.parseHttpRequest(
                    generateBadGetTestCase5()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }

    private InputStream generateValidGetTestCase(){
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }
    private InputStream generateBadGetTestCase(){
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }

    private InputStream generateBadGetTestCase2(){
        String rawData = "GeTTTTTTTTTTTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }
    private InputStream generateBadGetTestCase3(){
        String rawData = "GET  / AAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }
    private InputStream generateBadGetTestCase4(){
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }
    private InputStream generateBadGetTestCase5(){
        String rawData = "GET / HTTP/1.1\r" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: pt,en;q=0.9,es;q=0.8,en-US;q=0.7\r\n"+"\r\n";

        return new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

    }


}