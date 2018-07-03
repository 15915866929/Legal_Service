package com.core.exception;

/**
 * 事务异常基类
 */
public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 3088274881589704176L;
    
    public ResultException() {
    }
    
    public ResultException(String message) {
        super(message);
    }
    
    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ResultException(Throwable cause) {
        super(cause);
    }
}
