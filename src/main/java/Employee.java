public class Employee {

   StringBuilder name;
   String userName;
   String password;
   String email;

  public Employee(String fullName, String password){

    this.name = new StringBuilder(fullName);

    if (checkName(fullName)){
      setUserName(fullName);
      setEmail(fullName);
    }
    else{
      setUserName("default");
      setEmail("user@oracleacademy.Test");
    }
    if(isValidPassword(password)){
        this.password = password;
    }
    else{
      this.password = "pw";
    }
  }

  private void setUserName(String userName){

    if (!userName.contains(" ")){
      this.userName = userName;
    }
    else{
      this.userName = (userName.charAt(0) +
          userName.substring(userName.indexOf(' ') + 1)).toLowerCase();
    }

  }

  private boolean checkName(String name){

    return name.contains(" ");

  }

  private void setEmail(String email){

    if (!email.contains(" ")){
      this.email = email;
    }
    else{
      this.email = (email.replace(' ','.')).toLowerCase() + "@oracleacademy.Test";

    }
  }

  private boolean isValidPassword(String password){

    return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$");

  }

  @Override
  public String toString( ) {
    return "Employee Details\n" +
    "Name : " + name + "\n" +
    "Username : " + userName + "\n" +
    "Email : " + email + "\n" +
    "Initial Password : " + password;
  }
}
