package bekyiu.connector;

import lombok.Getter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class Request implements ServletRequest
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

    @Override
    public Object getAttribute(String s)
    {
        return null;
    }

    @Override
    public Enumeration getAttributeNames()
    {
        return null;
    }

    @Override
    public String getCharacterEncoding()
    {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException
    {

    }

    @Override
    public int getContentLength()
    {
        return 0;
    }

    @Override
    public String getContentType()
    {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return null;
    }

    @Override
    public String getParameter(String s)
    {
        return null;
    }

    @Override
    public Enumeration getParameterNames()
    {
        return null;
    }

    @Override
    public String[] getParameterValues(String s)
    {
        return new String[0];
    }

    @Override
    public Map getParameterMap()
    {
        return null;
    }

    @Override
    public String getProtocol()
    {
        return null;
    }

    @Override
    public String getScheme()
    {
        return null;
    }

    @Override
    public String getServerName()
    {
        return null;
    }

    @Override
    public int getServerPort()
    {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        return null;
    }

    @Override
    public String getRemoteAddr()
    {
        return null;
    }

    @Override
    public String getRemoteHost()
    {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o)
    {

    }

    @Override
    public void removeAttribute(String s)
    {

    }

    @Override
    public Locale getLocale()
    {
        return null;
    }

    @Override
    public Enumeration getLocales()
    {
        return null;
    }

    @Override
    public boolean isSecure()
    {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s)
    {
        return null;
    }

    @Override
    public String getRealPath(String s)
    {
        return null;
    }

    @Override
    public int getRemotePort()
    {
        return 0;
    }

    @Override
    public String getLocalName()
    {
        return null;
    }

    @Override
    public String getLocalAddr()
    {
        return null;
    }

    @Override
    public int getLocalPort()
    {
        return 0;
    }
}
