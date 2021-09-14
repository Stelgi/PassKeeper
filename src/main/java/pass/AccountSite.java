package pass;

public class AccountSite extends Site{
    private int id;
    private int idSite;
    private String login;
    private String pass;
    private String email;

    public AccountSite(int id, int idSite, String login, String pass, String email) {
        this.id = id;
        this.idSite = idSite;
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getLogin() + " " + this.getEmail();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
