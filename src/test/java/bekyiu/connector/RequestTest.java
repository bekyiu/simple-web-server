package bekyiu.connector;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RequestTest
{

    @Test
    public void parse()
    {
        String str = "GET /sample.jsp HTTP/1.1\r\n" +
                "Accept:image/gif,image/jpeg,*/*\r\n" +
                "Accept-Language:zh-cn\r\n" +
                "Connection:Keep-Alive\r\n" +
                "Host:localhost\r\n" +
                "User-Agent:Mozila/4.0(compatible;MSIE5.01;Window NT5.0)\r\n" +
                "Accept-Encoding:gzip,deflate\r\n" +
                "\r\n" +
                "username=jinqiao&password=1234";
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        Request req = new Request(inputStream);
        req.parse();
        System.out.println(req.getUri());
    }
}