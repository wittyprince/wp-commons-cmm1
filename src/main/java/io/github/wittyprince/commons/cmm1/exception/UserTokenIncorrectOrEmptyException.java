package io.github.wittyprince.commons.cmm1.exception;

/**
 * UserTokenIncorrectOrEmptyException
 *
 * @author WangChen
 * Created on 2024/10/22
 * @since 1.0
 */
public class UserTokenIncorrectOrEmptyException extends RuntimeException {

    public UserTokenIncorrectOrEmptyException() {
        super("token不正确或token为空");
    }

    public UserTokenIncorrectOrEmptyException(String message) {
        super(message);
    }

}
