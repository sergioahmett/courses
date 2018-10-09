package com.epam.daolayer.daoentity;

import com.epam.utils.PasswordDefender;

/**
 * The class corresponding to the user in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class DatabaseUser extends BaseEntity {
    private String login;
    private String mail;
    private String password;
    private String name;
    private String surname;
    private String role = "Student";
    private boolean block;

    private DatabaseUser() {
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isBlock() {
        return block;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public static Builder newBuilder() {
        return new DatabaseUser().new Builder();
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            DatabaseUser.this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            DatabaseUser.this.login = login;
            return this;
        }

        public Builder setMail(String mail) {
            DatabaseUser.this.mail = mail;
            return this;
        }

        public Builder setPassword(String password) {
            DatabaseUser.this.password = password;
            return this;
        }

        public Builder setName(String name) {
            DatabaseUser.this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            DatabaseUser.this.surname = surname;
            return this;
        }

        public Builder setRole(String role) {
            DatabaseUser.this.role = role;
            return this;
        }

        public Builder setBlock(boolean block) {
            DatabaseUser.this.block = block;
            return this;
        }
        
        public Builder encryptPassword() {
            DatabaseUser.this.password = PasswordDefender.getEncryptedPass(DatabaseUser.this.password, DatabaseUser.this.login);
            return this;
        }

        public DatabaseUser build() {
            return DatabaseUser.this;
        }
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", mail=" + mail + ", password=" + password + ", name=" + name
                + ", surname=" + surname + ", role=" + role + ", block=" + block + "]";
    }

}
