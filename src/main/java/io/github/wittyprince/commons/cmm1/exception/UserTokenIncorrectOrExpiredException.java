package io.github.wittyprince.commons.cmm1.exception;

/**
 * UserTokenIncorrentOrExpiredException
 *
 * @author WangChen
 * Created on 2024/10/22
 * @since 1.0
 */
public class UserTokenIncorrectOrExpiredException extends RuntimeException {

    public UserTokenIncorrectOrExpiredException() {
        super("用户token不正确或已过期");
    }

    public UserTokenIncorrectOrExpiredException(String message) {
        super(message);
    }

}
