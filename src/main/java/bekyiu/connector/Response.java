package bekyiu.connector;

import bekyiu.utils.ConnectorUtils;
import bekyiu.utils.HttpStatus;
import lombok.Setter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse
{
    private static final int BUFFER_SIZE = 1024;

    private OutputStream outputStream;
    @Setter
    private Request req;

    public Response(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    public void sendStaticResource() throws IOException
    {
        File file = new File(ConnectorUtils.WEB_ROOT, req.getUri());
        try
        {
            write(file, HttpStatus.OK);
        }
        catch (Exception e)
        {
            write(new File(ConnectorUtils.WEB_ROOT, File.separator + "999.html"), HttpStatus.ERR);
        }
    }

    private void write(File file, HttpStatus status) throws IOException
    {
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(file);

            // 响应头
            outputStream.write(ConnectorUtils.renderStatus(status).getBytes());

            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }
        }
        finally
        {
            close(in);
        }
    }

    private void close(Closeable c)
    {
        if (c != null)
        {
            try
            {
                c.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        return new PrintWriter(outputStream, true);
    }

    @Override
    public String getCharacterEncoding()
    {
        return null;
    }

    @Override
    public String getContentType()
    {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s)
    {

    }

    @Override
    public void setContentLength(int i)
    {

    }

    @Override
    public void setContentType(String s)
    {

    }

    @Override
    public void setBufferSize(int i)
    {

    }

    @Override
    public int getBufferSize()
    {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException
    {

    }

    @Override
    public void resetBuffer()
    {

    }

    @Override
    public boolean isCommitted()
    {
        return false;
    }

    @Override
    public void reset()
    {

    }

    @Override
    public void setLocale(Locale locale)
    {

    }

    @Override
    public Locale getLocale()
    {
        return null;
    }
}
