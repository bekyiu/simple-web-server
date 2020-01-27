package bekyiu.connector;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

public class Request
{
    private InputStream inputStream;
    private static final int BUFFER_SIZE = 1024;
    @Getter
    // 例如 /index.html, /hello
    private String uri;

    public Request(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    /**
     * 得到url
     */
    public void parse()
    {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        try
        {
            len = inputStream.read(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
        {
            sb.append((char) buffer[i]);
        }
        uri = parseUri(sb.toString());
    }

    private String parseUri(String packet)
    {
        String lineSeparator = "\r\n";
        try
        {
            String[] lines = packet.split(lineSeparator);
            return lines[0].split(" ")[1];
        }
        catch (Exception e)
        {
            System.err.println("http请求格式错误!");
            System.out.println(packet);
            e.printStackTrace();
        }
        return "";
    }
}
