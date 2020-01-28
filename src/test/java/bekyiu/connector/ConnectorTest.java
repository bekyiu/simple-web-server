package bekyiu.connector;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ConnectorTest
{

    @Test
    public void start() throws IOException
    {
        String req = "GET /index.html HTTP/1.1\r\n";
        Socket socket = new Socket("localhost", 8888);
        OutputStream out = socket.getOutputStream();
        out.write(req.getBytes());
        socket.shutdownOutput();

        InputStream in = socket.getInputStream();
        byte[] buf = new byte[8192];
        int len = in.read(buf);
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
        {
            sb.append((char) buf[i]);
        }
        socket.shutdownInput();
        System.out.println(sb.toString());
    }
}