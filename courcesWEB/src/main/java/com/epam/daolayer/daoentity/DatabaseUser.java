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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (block ? 1231 : 1237);
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((mail == null) ? 0 : mail.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DatabaseUser other = (DatabaseUser) obj;
        if (block != other.block)
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (mail == null) {
            if (other.mail != null)
                return false;
        } else if (!mail.equals(other.mail))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }

}
