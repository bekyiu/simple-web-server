package bekyiu.connector;

import bekyiu.processor.ServletProcessor;
import bekyiu.processor.StaticProcessor;
import bekyiu.utils.ConnectorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.*;
import java.util.Set;

public class Connector implements Runnable
{
    private static final int DEFAULT_PORT = 8888;
    private ServerSocketChannel serverChannel;
    private Selector selector;
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
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server on... port: " + port);
            while(true)
            {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys)
                {
                    handle(selectionKey);
                }
                selectionKeys.clear();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectorUtils.close(selector);
        }
    }

    private void handle(SelectionKey selectionKey) throws IOException
    {
        if(selectionKey.isAcceptable())
        {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
        else if(selectionKey.isReadable())
        {
            // 此时socketChannel是非阻塞式的
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            // 取消 key所属的通道的响应事件的监听
            selectionKey.cancel();
            // 恢复为阻塞后 才能操作input/output stream
            socketChannel.configureBlocking(true);

            Socket socket = socketChannel.socket();
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            // 构造请求
            Request req = new Request(input);
            req.parse();
            // 构造响应
            Response resp = new Response(output);
            resp.setReq(req);
            // 处理
            if (req.getUri().startsWith("/servlet/"))
            {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(req, resp);
            }
            else
            {
                StaticProcessor staticProcessor = new StaticProcessor();
                staticProcessor.process(req, resp);
            }
            ConnectorUtils.close(socketChannel);

        }
    }
}
