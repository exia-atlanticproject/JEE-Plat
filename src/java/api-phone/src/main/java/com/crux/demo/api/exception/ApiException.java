package com.crux.demo.api.exception;

/**
 * Created with IntelliJ IDEA.
 * User: aaron.stone
 * Date: 10/14/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiException extends Exception {

    private int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
