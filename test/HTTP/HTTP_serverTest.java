/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class HTTP_serverTest {

    public HTTP_serverTest() {
    }
    private static final String CRLF = "\r\n";

    @Test
    public void testResponseOK() throws IOException {
        final Socket client = new Socket("localhost", HTTP_server.SERVER_PORT);

        final OutputStream output = client.getOutputStream();
        output.write(("GET /hola.html HTTP/1.0" + CRLF + CRLF).getBytes());
        output.flush();

        final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final String statusLine = input.readLine();
        assertEquals("HTTP/1.0 200 OK", statusLine);
        client.close();
    }

    @Test
    public void testResponseNotOK() throws IOException {
        final Socket client = new Socket("localhost", HTTP_server.SERVER_PORT);

        final OutputStream output = client.getOutputStream();
        output.write(("GET /doesNotExist.html HTTP/1.0" + CRLF + CRLF).getBytes());
        output.flush();

        final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final String statusLine = input.readLine();
        assertEquals("HTTP/1.0 404 Not found: /doesNotExist.html", statusLine);
        client.close();
    }

    @Test
    public void testIllegalProtocol() throws IOException {
        final Socket client = new Socket("localhost", HTTP_server.SERVER_PORT);

        final OutputStream output = client.getOutputStream();
        output.write(("GET /doesNotExist.html" + CRLF + CRLF).getBytes());
        output.flush();

        final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final String statusLine = input.readLine();
        assertEquals("HTTP/1.0 400 Illegal request", statusLine);
        client.close();
    }

    /**
     * This is in fact a legal request, called Simple-Request,
     * http://www.faqs.org/rfcs/rfc1945.html, section 5
     */
    @Test
    public void testMissingProtocol() throws IOException {
        final Socket client = new Socket("localhost", HTTP_server.SERVER_PORT);

        final OutputStream output = client.getOutputStream();
        output.write(("GET /doesNotExist.html HTTP 1.0" + CRLF + CRLF).getBytes());
        output.flush();

        final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final String statusLine = input.readLine();
        assertEquals("HTTP/1.0 400 Illegal protocol: HTTP 1.0", statusLine);
        client.close();
    }

    @Test
    public void testNotImplemented() throws IOException {
        final Socket client = new Socket("localhost", HTTP_server.SERVER_PORT);

        final OutputStream output = client.getOutputStream();
        output.write(("PUT /doesNotExist.html HTTP/1.0" + CRLF + CRLF).getBytes());
        output.flush();

        final BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final String statusLine = input.readLine();
        assertEquals("HTTP/1.0 501 Not implemented: PUT", statusLine);
        client.close();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}