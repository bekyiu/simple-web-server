package bekyiu.processor;

import bekyiu.connector.Request;
import bekyiu.connector.Response;

import java.io.IOException;

public class StaticProcessor
{
    public void process(Request req, Response resp)
    {
        try
        {
            resp.sendStaticResource();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
