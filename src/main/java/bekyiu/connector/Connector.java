package bekyiu.connector;

import bekyiu.processor.StaticProcessor;
import bekyiu.utils.ConnectorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable
{
    private static final int DEFAULT_PORT = 8888;
    private ServerSocket server;
    private int port;

    public Connector()
    {
        this(DEFAULT_PORT);
    }

    public Connector(int port)
    {
        this.port = port;
    }

    public void start()
    {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("server on... port: " + port);
            while (true)
            {
                Socket socket = server.accept();
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                // 构造请求
                Request req = new Request(input);
                req.parse();
                // 构造响应
                Response resp = new Response(output);
                resp.setReq(req);
                // 发送
                StaticProcessor staticProcessor = new StaticProcessor();
                staticProcessor.process(req, resp);

                ConnectorUtils.close(socket);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectorUtils.close(server);
        }
    }
}
