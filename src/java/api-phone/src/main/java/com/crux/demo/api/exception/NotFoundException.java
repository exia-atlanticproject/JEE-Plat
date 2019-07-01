package com.crux.demo.api.exception;

/**
 * Created with IntelliJ IDEA.
 * User: aaron.stone
 * Date: 10/14/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(int code, String msg) {
        super(code, msg);
    }

}
