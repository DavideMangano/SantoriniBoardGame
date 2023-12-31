package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ColorPage extends Page implements Initializable {

    private URL layoutURL;

    @FXML
    private ImageView pawnTaken1;

    @FXML
    private ImageView pawnTaken2;

    @FXML
    private ImageView pawnTaken3;

    @FXML
    private ImageView pawnTaken4;

    @FXML
    private ImageView pawnTaken5;

    @FXML
    private ImageView chooseColorBtn1;

    @FXML
    private ImageView chooseColorBtn2;

    @FXML
    private ImageView chooseColorBtn3;

    @FXML
    private ImageView chooseColorBtn4;

    @FXML
    private ImageView chooseColorBtn5;

    public ColorPage() {
    }

    public String getPageName() {
        return "Color";
    }

    public void setGame(Game g) {
        game = g;


        ImageView[] chooseButtonsArray = {chooseColorBtn1, chooseColorBtn2, chooseColorBtn3, chooseColorBtn4, chooseColorBtn5};
        ImageView[] pawnTakenArray = {pawnTaken1, pawnTaken2, pawnTaken3, pawnTaken4, pawnTaken5};

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Colour currentColor = getColorByIndex(i);

            chooseButtonsArray[i].setOnMousePressed(e -> {
                chooseButtonsArray[finalI].setImage(new Image("/Images/ColorChoice/btn_" + getColorStringByIndex(finalI) + "_pressed.png"));
            });

            chooseButtonsArray[i].setOnMouseReleased(e -> {
                chooseButtonsArray[finalI].setImage(new Image("/Images/ColorChoice/btn_" + getColorStringByIndex(finalI) + ".png"));
            });


            if (game.getAlreadyChosenColors().contains(currentColor)) {
                pawnTakenArray[i].setImage(new Image("/Images/ColorChoice/already_taken.png"));
                chooseButtonsArray[i].setDisable(true);
            } else {
                chooseButtonsArray[i].setOnMouseClicked(e -> {
                    System.out.println("Clicked " + getColorStringByIndex(finalI) + " Button");
                    client.setChosenColor(getColorByIndex(finalI));
                    try {
                        client.setCurrentPage(new GamePage(),"/Music/Game/music.mp3");
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    /**
     * returns the color string of the divinity at position i
     *
     * @param i index of the divinity to get the color string of
     *
     * @return the color string of the selected index
     */
    private String getColorStringByIndex(int i) {
        switch (i) {
            case 0:
                return "blue";
            case 1:
                return "coral";
            case 2:
                return "white";
            case 3:
                return "yellow";
            case 4:
                return "purple";
            default:
                return null;
        }
    }

    /**
     * returns the color of the divinity at position i
     *
     * @param i index of the divinity to get the color of
     *
     * @return the color of the selected index
     */
    private Colour getColorByIndex(int i) {
        switch (i) {
            case 0:
                return Colour.BLUE;
            case 1:
                return Colour.RED;
            case 2:
                return Colour.WHITE;
            case 3:
                return Colour.YELLOW;
            case 4:
                return Colour.PINK;
            default:
                return null;
        }
    }
}

