package it.polimi.ingsw.PSP19.Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Player;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import it.polimi.ingsw.PSP19.Server.Controller.ServerController;


public class ListenForPlayer extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageSerializer messageSerializer;
    private MessageDeserializer messageDeserializer;
    private ServerController controller;

    public ListenForPlayer(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        controller = new ServerController();
        messageSerializer = new MessageSerializer();
        messageDeserializer = new MessageDeserializer();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            String username = messageDeserializer.deserializeString(requestContent, "username");
            boolean nPlayers = messageDeserializer.deserializeBoolean(requestContent, "3players");

            Player player = new Player(username, null, null);
            Game game = controller.addPlayerToModel(player, nPlayers);
            String gameID = game.getCodGame();
            System.out.println("Added player " + username + " to game " + gameID);
            String response = messageSerializer.serializeJoinGame(username, nPlayers, gameID).toString();
            int nPlayersOnGame = game.getThreePlayers() ? 3 : 2;

            //if the Lobby is full,sets a random player to decide the inGameDivinities
            if (game.getPlayers().size() == nPlayersOnGame) {
                game.increaseCurrentPlayerIndex();
                game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));
            }

            output.writeObject(response);
        } catch (IllegalArgumentException e) {
            output.writeObject("The username you selected was already taken,try again with a different username");
        }
    }
}
