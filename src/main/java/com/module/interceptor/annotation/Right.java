package com.module.interceptor.annotation;

/**
 * @author 马锦峰
 * @version 1.0
 * @date 2016/8/22 11:11
 */
public enum Right {
    list(1), query(2), add(4), update(8), del(16), audit(32), unaudit(64), close(128), unclose(256), print(512);

    private int rightCode;

    Right(int idx) {
        this.rightCode = idx;
    }

    public int getRightCode() {
        return this.rightCode;
    }
}
