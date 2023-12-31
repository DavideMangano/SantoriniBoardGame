package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Client.GUI.RequestHandler;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DivinitiesChoicePage extends Page implements Initializable {

    private URL layoutURL;

    private ArrayList<String> divinitiesChoice = new ArrayList<String>();
    private ImageView[] divButtonsArray;
    int players;
    final boolean[] active = {false};
    final int[] divChosed = {0};
    final boolean[] clicked = {false, false, false, false, false, false, false, false, false};

    @FXML
    private ImageView goBtn;

    @FXML
    private ImageView div1;

    @FXML
    private ImageView div2;

    @FXML
    private ImageView div3;

    @FXML
    private ImageView div4;

    @FXML
    private ImageView div5;

    @FXML
    private ImageView div6;

    @FXML
    private ImageView div7;

    @FXML
    private ImageView div8;

    @FXML
    private ImageView div9;

    public DivinitiesChoicePage() {

    }

    public String getPageName() {
        return "DivinitiesChoice";
    }

    public void setGame(Game g) {
        game = g;

        if (client.getThreePlayers()) {
            players = 3;
        } else {
            players = 2;
        }


        for (int i = 0; i < 9; i++) {
            int finalI = i;

            divButtonsArray[i].setOnMouseEntered(event -> {
                divButtonsArray[finalI].setLayoutX(divButtonsArray[finalI].getLayoutX()-5.0);
                divButtonsArray[finalI].setLayoutY(divButtonsArray[finalI].getLayoutY()-10.0);
                divButtonsArray[finalI].setFitHeight(divButtonsArray[finalI].getFitHeight()+20);
                divButtonsArray[finalI].setFitWidth(divButtonsArray[finalI].getFitWidth()+20);
            });

            divButtonsArray[i].setOnMouseExited(event -> {
                divButtonsArray[finalI].setLayoutX(divButtonsArray[finalI].getLayoutX()+5.0);
                divButtonsArray[finalI].setLayoutY(divButtonsArray[finalI].getLayoutY()+10.0);
                divButtonsArray[finalI].setFitHeight(300.0);
                divButtonsArray[finalI].setFitWidth(167.0);
            });

            goBtn.setOnMouseEntered(event -> {
                goBtn.setImage(new Image("/Images/DivChoice/Go_button_over.png"));
            });
            goBtn.setOnMouseExited(event -> {
                goBtn.setImage(new Image("/Images/DivChoice/Go_button.png"));
            });

            divButtonsArray[i].setOnMouseClicked(event -> {
                if ((!clicked[finalI]) && (divChosed[0] < players)) {
                    divButtonsArray[finalI].setImage(new Image("/Images/DivChoice/" + getDivinityStringByIndex(finalI) + "_chosen.png"));
                    divinitiesChoice.add(getDivinityStringByIndex(finalI).toUpperCase());
                    divChosed[0]++;
                    clicked[finalI] = true;
                    if (divChosed[0] == players) {
                        active[0] = true;

                        goBtn.setImage(new Image("/Images/DivChoice/Go_button.png"));

                        goBtn.setDisable(false);

                    }
                } else if ((clicked[finalI]) && (divChosed[0] <= players)) {
                    divButtonsArray[finalI].setImage(new Image("/Images/DivChoice/" + getDivinityStringByIndex(finalI) + ".png"));
                    divinitiesChoice.remove(getDivinityStringByIndex(finalI).toUpperCase());
                    divChosed[0]--;
                    clicked[finalI] = false;
                    active[0] = false;
                    goBtn.setImage(new Image("/Images/DivChoice/Go_button_NONactive.png"));
                    goBtn.setDisable(true);
                }
            });

        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        divButtonsArray = new ImageView[]{div1, div2, div3, div4, div5, div6, div7, div8, div9};

        goBtn.setDisable(true);

        goBtn.setOnMousePressed(e -> goBtn.setImage(new Image("/Images/DivChoice/Go_button_Pressed_over.png")));

        goBtn.setOnMouseReleased(e -> goBtn.setImage(new Image("/Images/DivChoice/Go_button.png")));


        goBtn.setOnMouseClicked(e -> {
            System.out.println(divinitiesChoice);
            String message = messageSerializer.serializeDivinities(CastingHelper.convertDivinityList(divinitiesChoice), "SendDivinities", game.getCodGame()).toString();
            RequestHandler.getRequestHandler().updateRequest(Commands.SEND_DIVINITIES, message);
        });
    }

    private String getDivinityStringByIndex(int i) {
        switch (i) {
            case 0:
                return "Apollo";
            case 1:
                return "Artemis";
            case 2:
                return "Athena";
            case 3:
                return "Atlas";
            case 4:
                return "Demeter";
            case 5:
                return "Hephaestus";
            case 6:
                return "Minotaur";
            case 7:
                return "Pan";
            case 8:
                return "Prometheus";
            default:
                return "null";
        }
    }

}
