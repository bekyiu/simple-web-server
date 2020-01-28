package bekyiu.processor;

import bekyiu.connector.Request;
import bekyiu.connector.Response;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class ServletProcessorTest
{

    @Test
    public void process() throws Exception
    {
        String reqStr = "GET /servlet/TimeServlet HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(reqStr.getBytes());
        Request req = new Request(inputStream);
        req.parse();
        System.out.println(req.getUri());

        OutputStream outputStream = System.out;
        Response resp = new Response(outputStream);
        resp.setReq(req);

        ServletProcessor processor = new ServletProcessor();
        processor.process(req, resp);
    }
}