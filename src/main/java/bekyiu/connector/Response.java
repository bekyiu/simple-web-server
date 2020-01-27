package bekyiu.connector;

import bekyiu.utils.ConnectorUtils;
import bekyiu.utils.HttpStatus;
import lombok.Setter;

import java.io.*;

public class Response
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
}
