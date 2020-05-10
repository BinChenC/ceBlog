package com.cb.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 所有异常的拦截和日志记录
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}",req.getRequestURL(),e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", req.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }

}
