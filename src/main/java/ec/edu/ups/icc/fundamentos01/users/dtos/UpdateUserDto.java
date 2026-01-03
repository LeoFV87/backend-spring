package ec.edu.ups.icc.fundamentos01.users.dtos;

public class UpdateUserDto {
    public String name;
    public String email;
    public String password;

   // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}