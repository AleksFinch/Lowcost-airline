package com.finchuk.dto;


/**
 * Created by olexandr on 25.03.17.
 */
public enum Role {
    USER {
        @Override
        public String toString() {
            return "user";
        }
    },
    ADMIN {
        @Override
        public String toString() {
            return "admin";
        }
    };

    public static Role getRole(String role) {
        switch (role) {
            case "user":
                return USER;
            case "admin":
                return ADMIN;
            default:
                throw new IllegalArgumentException("This role doesn't exist");
        }
    }
}
