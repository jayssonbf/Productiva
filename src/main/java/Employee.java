/**
 * Represents the identity or profile of an employee.
 * An employee can create production orders.
 * @author Jaysson Balbuena
 */
public class Employee {

  StringBuilder name;
  String userName;
  String password;
  String email;

  /**
   * Class constructor creates an employee object by passing full name and password.
   * @param fullName The employee's first and last name.
   * @param password The employee's password.
   */
  public Employee(String fullName, String password) {

    this.name = new StringBuilder(fullName);

    if (checkName(fullName)) {
      setUserName(fullName);
      setEmail(fullName);
    } else {
      setUserName("default");
      setEmail("user@oracleacademy.Test");
    }
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Changes this employee's username.
   * @param userName A string containing the employee's username.
   */
  private void setUserName(String userName) {

    if (!userName.contains(" ")) {
      this.userName = userName;
    } else {
      this.userName = (userName.charAt(0)
                      + userName.substring(userName.indexOf(' ') + 1)).toLowerCase();
    }
  }

  /**
   * Checks if this employee's name contains a blank or a space between first and last name.
   * @param name A String containing this employee's first name.
   * @return A boolean representing whether this employee's name contains a space.
   */
  private boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * Changes this employee's email.
   * @param email A string containing this employee's email.
   */
  private void setEmail(String email) {

    if (!email.contains(" ")) {
      this.email = email;
    } else {
      this.email = (email.replace(' ', '.')).toLowerCase() + "@oracleacademy.Test";
    }
  }

  /**
   * validates this employee's password.
   * @param password A string containing this employee's password.
   * @return a boolean value containing the validation of this employees password.
   */
  private boolean isValidPassword(String password) {
    return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$");
  }

  /**
   * Overrides toString method to display this employee's info details.
   * @return this employee's info details.
   */
  @Override
  public String toString() {
    return "Employee Details\n"
        +  "Name : " + name + "\n"
        +  "Username : " + userName + "\n"
        +  "Email : " + email + "\n"
        +  "Initial Password : " + password;
  }
}
