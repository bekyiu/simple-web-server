package bekyiu.processor;

import bekyiu.connector.Request;
import bekyiu.connector.Response;
import bekyiu.utils.ConnectorUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ServletProcessor
{
    public void process(Request req, Response resp)
    {
        try
        {
            URLClassLoader servletLoader = getServletLoader();
            Servlet servlet = getServlet(servletLoader, req);
            servlet.service(req, resp);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (ServletException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 得到加载servlet的classloader
     * @return
     * @throws MalformedURLException
     */
    private URLClassLoader getServletLoader() throws MalformedURLException
    {
        File webroot = new File(ConnectorUtils.WEB_ROOT);
        // file:/D:/IDEAProjects/web-server/webroot/
        URL url = webroot.toURI().toURL();
        // 从给定url下加载类的classloader
        return new URLClassLoader(new URL[]{url});
    }

    /**
     * 用给定的classloader加载request中指定的servlet
     * @param loader
     * @param req
     * @return
     */
    private Servlet getServlet(URLClassLoader loader, Request req) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        // /servlet/xxx
        String uri = req.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        Class<?> servletClass = loader.loadClass(servletName);
        Servlet servlet = (Servlet) servletClass.newInstance();
        return servlet;
    }


}
