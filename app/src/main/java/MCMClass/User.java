package MCMClass;

public class User {
    private String  name, gmail, password;
    private byte[] avatar, background;

    public User(byte[] background, byte[] avatar, String gmail, String name, String password) {
        this.background = background;
        this.avatar = avatar;
        this.gmail = gmail;
        this.name = name;

        this.password = password;
    }

    public User() {
    }

    public byte[] getBackground() {
        return background;
    }

    public void setBackground(byte[] background) {
        this.background = background;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
