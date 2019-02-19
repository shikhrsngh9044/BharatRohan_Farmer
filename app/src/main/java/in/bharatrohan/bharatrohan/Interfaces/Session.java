package in.bharatrohan.bharatrohan.Interfaces;

public interface Session {

    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void saveEmail(String email);

    String getEmail();

    void savePhone(String phone);

    String getPhone();

    void invalidate();
}
