package org.csits.demo.domain;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private Integer id;
    private String identify = UUID.randomUUID().toString();
    private String userName = "testUser";

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", identify='" + identify + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
