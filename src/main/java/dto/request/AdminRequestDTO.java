package dto.request;

public class AdminRequestDTO {
    private final String username;
    private final String password;

    public AdminRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
