package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.UserDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.UserMapper;
import com.finchuk.entities.Role;
import com.finchuk.entities.User;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class UserJdbcDao implements UserDao {
    ConnectionManager cm;
    JdbcHelper helper;

    public UserJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }

    @Override
    public Long add(User user) {
        return helper.insert("INSERT INTO user " +
                        "(first_name," +
                        "last_name," +
                        "e_mail," +
                        "password," +
                        "role)" +
                        "VALUES(?,?,?,?,?)",
                user.getFirstName(),
                user.getLastName(),
                user.geteMail(),
                user.getPassword(),
                findRoleId(user.getRole()));
    }

    @Override
    public User find(Long id) {
        User user = helper.findObject("SELECT * FROM user " +
                "WHERE id=?", UserMapper::map, id);
        if (user != null) {
            user.setRole(readRole(user.getUserId()));
        }
        return user;
    }

    @Override
    public void update(User user) {

        helper.update("UPDATE user " +
                        "SET " +
                        "first_name = ?," +
                        "last_name = ?," +
                        "e_mail = ?," +
                        "password = ?," +
                        "role = ? " +
                        "WHERE id=?",
                user.getFirstName(),
                user.getLastName(),
                user.geteMail(),
                user.getPassword(),
                findRoleId(user.getRole()),
                user.getUserId());

    }

    @Override
    public void delete(Long id) {
        helper.remove("DELETE FROM user " +
                "WHERE id=?", id);
    }

    @Override
    public List<User> findAll() {
        List<User> list = helper.findObjects("SELECT * FROM user "
                , UserMapper::map);
        list.forEach(e -> e.setRole(readRole(e.getUserId())));
        return list;

    }

    @Override
    public Role readRole(Long userId) {
        return helper.findObject("SELECT role.role FROM role " +
                        "JOIN user ON user.role = role.id AND " +
                        "user.id=?",
                (rs) -> {
                    try {
                        return Role.getRole(rs.getString("role"));
                    } catch (SQLException e) {
                        LogManager.getLogger(this.getClass()).error("cant map object");
                        throw new IllegalStateException(e);
                    }
                },
                userId);
    }

    @Override
    public Long findRoleId(Role role) {
        //TODO:Make transaction here
        return helper.findObject("SELECT id FROM role " +
                        "WHERE role = ?",
                (rs) -> {
                    try {
                        return rs.getLong("id");
                    } catch (SQLException e) {
                        LogManager.getLogger(this.getClass()).error("cant map object");
                        throw new IllegalStateException(e);
                    }
                },
                role.toString());
    }

    @Override
    public User findByEMail(String eMail) {
        User user = helper.findObject("SELECT * FROM user WHERE e_mail=?", UserMapper::map, eMail);
        if (user != null) {
            user.setRole(readRole(user.getUserId()));
        }
        return user;
    }
}
