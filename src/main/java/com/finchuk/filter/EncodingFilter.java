package com.finchuk.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding param")
})
public class EncodingFilter implements Filter {
    private static final String PARAM_ENCODING = "encoding";

    private String code;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter(PARAM_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                throws IOException, ServletException {

        String codeRequest = request.getCharacterEncoding();

        if(code != null && !code.equals(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
