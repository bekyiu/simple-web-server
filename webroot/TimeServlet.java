import bekyiu.utils.ConnectorUtils;
import bekyiu.utils.HttpStatus;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

// 所有的servlet放在webroot下
public class TimeServlet implements Servlet
{
    @Override
    public void init(ServletConfig servletConfig) throws ServletException
    {

    }

    @Override
    public ServletConfig getServletConfig()
    {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException
    {
        Date now = new Date();
        PrintWriter writer = resp.getWriter();
        writer.print(ConnectorUtils.renderStatus(HttpStatus.OK));
        writer.println("now: " + now.toLocaleString());
    }

    @Override
    public String getServletInfo()
    {
        return null;
    }

    @Override
    public void destroy()
    {

    }
}
