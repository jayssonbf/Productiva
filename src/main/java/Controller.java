import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class Controller implements Initializable {


    @FXML
    Tab prodLineTab;

    @FXML
    Tab produceTab;

    @FXML
    Tab prodLogTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane anch1 = loader.load(getClass().getResource("Tab1.fxml"));
            prodLineTab.setContent(anch1);
        } catch (IOException iex) {
            System.out.println("unable to load ProdLineTab");
        }

        loader = new FXMLLoader();
        try {
            AnchorPane anch2 = loader.load(getClass().getResource("Tab2.fxml"));
            produceTab.setContent(anch2);
        } catch (IOException iex) {
            System.out.println("unable to load produceTab");
        }

        loader = new FXMLLoader();
        try {
            AnchorPane anch3 = loader.load(getClass().getResource("Tab3.fxml"));
            prodLogTab.setContent(anch3);
        } catch (IOException iex) {
            System.out.println("unable to load tab3");
        }

    }


}