import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class Tab1Controller implements Initializable
{
  @Override
  public void  initialize(URL location, ResourceBundle resources)
  {

  }

  public void buttonAction(ActionEvent event){
    System.out.println("Hello User, the product has been added!");
  }
}