package com.core.base.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础控制器类
 */
public class BaseController {

    @Autowired(required=false)
    protected HttpServletRequest request;
    @Autowired(required=false)
    protected HttpServletResponse response;
    @Autowired(required=false)
    protected HttpSession session;


    Logger log = Logger.getLogger(BaseController.class);

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .parse(value));
                        } catch (Exception e) {
                            try {
                                setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                                        .parse(value));
                            } catch (ParseException e1) {
                                try{
                                    setValue(new SimpleDateFormat("yyyy-MM-dd")
                                            .parseObject(value));
                                }catch (ParseException e2) {
                                    setValue(null);
                                }
                            }
                        }
                    }

                    public String getAsText() {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format((Date) getValue());
                    }

                });

        dataBinder.registerCustomEditor(Long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(-1L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(0L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(Integer.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(null);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(int.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(0);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(Double.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value));
                        } catch (Exception e) {
                            setValue(null);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(double.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value));
                        } catch (Exception e) {
                            setValue(0);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });
    }

}
