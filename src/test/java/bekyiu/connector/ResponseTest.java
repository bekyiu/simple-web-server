package bekyiu.connector;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ResponseTest
{
    private static final String resp200 = "";

    @Test
    public void sendStaticResource() throws IOException
    {
        String invalidReq = "GET /sample.jsp HTTP/1.1\r\n";
        String validReq = "GET /index.html HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(invalidReq.getBytes());
        Request req = new Request(inputStream);
        req.parse();
        System.out.println(req.getUri());

        OutputStream outputStream = System.out;
        Response resp = new Response(outputStream);
        resp.setReq(req);

        resp.sendStaticResource();

    }
}