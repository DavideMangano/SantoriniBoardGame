package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyPage extends Page implements Initializable {

    @FXML
    public Pane lobbyPageContainer;

    @FXML
    Text nPlayersTextBox;

    @FXML
    Text codGameText;

    @FXML
    Text firstPlayerText;

    @FXML
    Text secondPlayerText;

    @FXML
    Text thirdPlayerText;

    @FXML
    ImageView playButton;

    public LobbyPage() {
    }

    public String getPageName() {
        return "Lobby";
    }

    public void setGame(Game g) {
        game = g;
        if(game != null){
            int nPlayers = game.getThreePlayers() ? 3 : 2;
            drawInitialLobbyPage();
            firstPlayerText.setText(game.getPlayers().getPlayer(0).getUsername());
            if(game.getPlayers().size() > 1){
                secondPlayerText.setText(game.getPlayers().getPlayer(1).getUsername());
            }
            if(game.getPlayers().size() > 2){
                thirdPlayerText.setText(game.getPlayers().getPlayer(2).getUsername());
            }
            if(game.getPlayers().size() == nPlayers){
                playButton.setImage(new Image("/Images/Lobby/LobbyPlayButtonActive.png"));
                playButton.setDisable(false);
            }
            nPlayersTextBox.setText(game.getPlayers().size() + "/" + nPlayers);
        }
    }

    public void drawInitialLobbyPage(){
        lobbyPageContainer.getStyleClass().clear();
        firstPlayerText.setText(client.getPlayerUsername());
        if (game.getThreePlayers()) {
            nPlayersTextBox.setText("1/3");
            lobbyPageContainer.getStyleClass().add("threePlayersBackground");
        } else {
            nPlayersTextBox.setText("1/2");
            lobbyPageContainer.getStyleClass().add("twoPlayersBackground");
        }
        playButton.setImage(new Image("/Images/Lobby/LobbyPlayButtonNONActive.png"));
        playButton.setDisable(true);
        codGameText.setText(game.getCodGame());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(game != null) {
            drawInitialLobbyPage();
        }
    }

    public void playBtnClicked(MouseEvent mouseEvent) throws IOException {
        client.setCurrentPage(new WaitingDivinitiesChoicePage());
    }
}

