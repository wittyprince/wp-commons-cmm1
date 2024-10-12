package io.github.wittyprince.commons.cmm1.holder;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * user holder
 *
 * @author WangChen
 * Created on 2023/5/11
 * @since 1.0
 */
@Slf4j
public class UserContextHolder {

    private static final ThreadLocal<UserInfo> currentUser = new ThreadLocal<>();

    public static UserInfo getUser() {
        return currentUser.get();
    }

    public static void setUser(String username, List<String> roles) {
        currentUser.set(new UserInfo(username, roles));
    }

    public static void setUser(String userId, String username, List<String> roles) {
        currentUser.set(new UserInfo(userId, username, roles));
    }

    public static void clear() {
        currentUser.remove();
    }

    public static class UserInfo {
        private String userBid;
        private String username;
        private List<String> roles;

        public UserInfo(String userBid, List<String> roles) {
            this.userBid = userBid;
            this.roles = roles;
        }

        public UserInfo(String userBid, String username, List<String> roles) {
            this.userBid = userBid;
            this.username = username;
            this.roles = roles;
        }

        public String getUserBid() {
            return userBid;
        }

        public void setUserBid(String userBid) {
            this.userBid = userBid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}
