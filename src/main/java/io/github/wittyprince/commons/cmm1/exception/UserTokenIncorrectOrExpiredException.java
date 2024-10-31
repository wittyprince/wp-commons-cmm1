package io.github.wittyprince.commons.cmm1.exception;

/**
 * UserTokenIncorrectOrExpiredException
 *
 * @author wp
 * Created on 2024/10/22
 * @since 1.0
 */
public class UserTokenIncorrectOrExpiredException extends RuntimeException {

    public UserTokenIncorrectOrExpiredException() {
        super("token不正确或者已过期");
    }

    public UserTokenIncorrectOrExpiredException(String message) {
        super(message);
    }

}
