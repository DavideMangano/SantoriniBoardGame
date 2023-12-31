package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Controller.ServerController;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import it.polimi.ingsw.PSP19.Server.Model.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForStartingPosition extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private ServerController serverController;
    private Model model;
    private Game game;


    public ListenForStartingPosition(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        serverController = new ServerController();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            Grid grid;
            String gameID;
            Colour colour;

            System.out.println("Received Send Starting Position Request");

            grid = messageDeserializer.deserializeObject(requestContent, "grid", Grid.class);
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            game = Model.getModel().searchID(gameID);
            colour = messageDeserializer.deserializeObject(requestContent, "colour", Colour.class);

            game.getCurrentPlayer().setColour(colour);
            game.addChosenColour(colour);
            // add updated grid to model
            game.setOldGrid(grid);
            game.setNewGrid(grid);
            //Select new current player
            Player randomPlayer = game.getPlayers().getRandomPlayer();
            PlayerList alreadyPlacedPlayers = serverController.getPlayersThatAlreadyPlaced(grid);
            int nPlayers = game.getThreePlayers() ? 3 : 2;

            if (alreadyPlacedPlayers != null && alreadyPlacedPlayers.size() > 0 && alreadyPlacedPlayers.size() < nPlayers) {      //Selects a new player to choose its current position
                game.increaseCurrentPlayerIndex();
                game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));
            } else {  //Every player has chosen their starting position,the game can start

                game.increaseCurrentPlayerIndex();
                game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));
                game.setGameTurn(new Turn(randomPlayer.getDivinity()));     // initializes game Turn
                game.getGameTurn().startingTurn(randomPlayer.getDivinity()); // starts turn

                System.out.println("Starting Game");
                game.setNTurns(1);
            }

            game.setnMoves(game.getnMoves() + 1);
            output.writeObject("Received Starting Position");

        } catch (
                ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
