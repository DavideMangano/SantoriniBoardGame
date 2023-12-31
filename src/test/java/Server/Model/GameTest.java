package Server.Model;

import com.google.gson.Gson;
import it.polimi.ingsw.PSP19.Server.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    static Game game;

    @Before
    public void setUp() throws Exception {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void setNTurnsTest() {
        game.setNTurns(10);
        assertTrue(game.getNTurns() == 10);
    }

    @Test
    public void setNMovesTest() {
        game.setnMoves(10);
        assertSame(10, game.getnMoves());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNTurnsExceptionTest() {
        game.setNTurns(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNMovesExceptionTest() {
        game.setnMoves(-10);
    }

    @Test
    public void getNTurnsTest() {
        assertTrue(game.getNTurns() == 1);
    }

    @Test
    public void getCodGameTest() {
        assertEquals("01", game.getCodGame());
    }

    @Test
    public void setCodGame() {
        game.setCodGame("02");
        assertEquals("02", game.getCodGame());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCodGameExceptionTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Model model = Model.getModel();
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        model.addGame(new Game(1, "01", true, p1, g1, g2, null));
        game.setCodGame("01");
    }

    @Test
    public void getThreePlayersTest() {
        assertTrue(game.getThreePlayers() == false);
    }

    @Test
    public void setThreePlayersTest() {
        game.setThreePlayers(true);
        assertTrue(game.getThreePlayers() == true);
    }

    @Test
    public void getCurrentPlayerTest() {
        assertSame("Player1", game.getCurrentPlayer().getUsername());
    }

    @Test
    public void setCurrentPlayerTest() {
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);

        game.setCurrentPlayer(game.getPlayers().getPlayer(1));
        assertSame("Player2", game.getCurrentPlayer().getUsername());
    }

    @Test
    public void setWinnerTest() {
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);

        game.setWinner(game.getPlayers().getPlayer(1));
        assertEquals("Player2", game.getWinner().getUsername());
    }

    @Test
    public void getDisconnectedTest() {
        assertFalse(game.getDisconnected());
        game.setDisconnected();
        assertTrue(game.getDisconnected());
    }


    @Test(expected = IllegalArgumentException.class)
    public void setCurrentPlayerExceptionTest() {
        Player p3 = new Player("Player3", Divinity.ATLAS, Colour.BLUE);

        game.setCurrentPlayer(p3);
    }

    @Test
    public void getPlayersTest() {
        PlayerList players;
        players = game.getPlayers();
        assertSame("Player1", players.getPlayer(0).getUsername());
        assertSame("Player2", players.getPlayer(1).getUsername());

    }

    @Test
    public void setPlayersTest() {
        Player p3 = new Player("Player3", Divinity.HEPHAESTUS, Colour.PINK);
        game.setThreePlayers(true);
        game.setPlayers(p3);
        assertSame("Player3", game.getPlayers().getPlayer(2).getUsername());

    }

    @Test
    public void getOldGridTest() {
        Grid g1 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));

        g1 = game.getOldGrid();
        g1.setCells(c1, 3, 4);
        game.setOldGrid(g1);
        assertTrue(game.getOldGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getOldGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getOldGrid().getCells(3, 4).getTower().getLevel());
    }

    @Test
    public void setOldGridTest() {
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));
        Cell c2 = new Cell(new Tower(1, true), new Pawn(p2));

        g1 = game.getOldGrid();
        g1.setCells(c1, 3, 4);
        game.setOldGrid(g1);
        assertTrue(game.getOldGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getOldGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getOldGrid().getCells(3, 4).getTower().getLevel());

        g2.setCells(c2, 1, 2);
        game.setOldGrid(g2);
        assertTrue(game.getOldGrid().getCells(1, 2).getPawn().getOwner().getUsername().equals("Player2"));
        assertTrue(game.getOldGrid().getCells(1, 2).getTower().getIsDome());
        assertSame(1, game.getOldGrid().getCells(1, 2).getTower().getLevel());
    }

    @Test
    public void getNewGridTest() {
        Grid g1 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));

        g1 = game.getNewGrid();
        g1.setCells(c1, 3, 4);
        game.setNewGrid(g1);
        assertTrue(game.getNewGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getNewGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getNewGrid().getCells(3, 4).getTower().getLevel());
    }

    @Test
    public void setNewGridTest() {
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));
        Cell c2 = new Cell(new Tower(1, true), new Pawn(p2));

        g1 = game.getNewGrid();
        g1.setCells(c1, 3, 4);
        game.setNewGrid(g1);
        assertTrue(game.getNewGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getNewGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getNewGrid().getCells(3, 4).getTower().getLevel());

        g2.setCells(c2, 1, 2);
        game.setNewGrid(g2);
        assertTrue(game.getNewGrid().getCells(1, 2).getPawn().getOwner().getUsername().equals("Player2"));
        assertTrue(game.getNewGrid().getCells(1, 2).getTower().getIsDome());
        assertSame(1, game.getNewGrid().getCells(1, 2).getTower().getLevel());


    }

    @Test
    public void getNextMovesTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        MoveList m1;

        m1 = new MoveList();
        m1.addMove(new Move(new Pawn(p1)));
        game.setNextMoves(m1);
        assertTrue(game.getNextMoves().getMove(0).getToMove().getOwner().getUsername().equals("Player1"));

    }

    @Test
    public void setNextMovesTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p3 = new Player("Player3", Divinity.HEPHAESTUS, Colour.PINK);
        MoveList m1, m2;

        m1 = new MoveList();
        m1.addMove(new Move(new Pawn(p1)));
        game.setNextMoves(m1);
        assertTrue(game.getNextMoves().getMove(0).getToMove().getOwner().getUsername().equals("Player1"));

        m2 = new MoveList();
        m2.addMove(new Move(new Pawn(p3)));
        game.setNextMoves(m2);
        assertSame("Player3", game.getNextMoves().getMove(0).getToMove().getOwner().getUsername());
    }

    @Test
    public void getAvailableLevelDomesTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        assertEquals(18, game.getAvailableDomes());
        game.decreaseAvailableDomes();
        assertEquals(17, game.getAvailableDomes());
    }

    @Test
    public void getAvailableLevel1BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        assertEquals(22, game.getAvailableLevel1Buildings());
    }

    @Test
    public void decreaseAvailableLevel1BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel1Buildings();
        game.decreaseAvailableLevel1Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel1Buildings());
    }

    @Test
    public void getAvailableLevel2BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        assertEquals(18, game.getAvailableLevel2Buildings());
    }

    @Test
    public void decreaseAvailableLevel2BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel2Buildings();
        game.decreaseAvailableLevel2Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel2Buildings());
    }

    @Test
    public void getAvailableLevel3BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        game = new Game(1, "01", false, p1, g1, g2, null);
        assertEquals(14, game.getAvailableLevel3Buildings());
    }

    @Test
    public void decreaseAvailableLevel3BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel3Buildings();
        game.decreaseAvailableLevel3Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel3Buildings());
    }

    @Test
    public void getInGameDivinitesTest() {
        game.getInGameDivinities().addDivinity(Divinity.ATHENA);
        assertSame(game.getInGameDivinities().getDivinity(0), Divinity.ATHENA);
    }

    @Test
    public void getPossiblrDivinitesTest() {
        game.getPossibleDivinities().addDivinity(Divinity.ATHENA);
        assertSame(game.getPossibleDivinities().getDivinity(0), Divinity.ATHENA);
    }

    @Test
    public void addChosenColourTest() {
        game.addChosenColour(Colour.RED);
        game.addChosenColour(Colour.BLUE);
        assertSame(game.getAlreadyChosenColors().get(0), Colour.RED);
        assertSame(game.getAlreadyChosenColors().get(1), Colour.BLUE);
    }

    @Test
    public void copyGameTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        Game newGame = new Game(3, "0dd1", false, null, g1, g2, null);
        Player p2 = new Player("Player2", Divinity.PROMETHEUS, Colour.BLUE);
        newGame.getPlayers().addPlayer(p2);
        newGame.setCurrentPlayer(p2);
        Grid g3 = new Grid();
        Grid g4 = new Grid();
        game = new Game(3, "0d1", false, p2, g3, g4, null);
        game.getPlayers().addPlayer(p1);
        game.setCurrentPlayer(p1);

        game.copyGame(game);

        System.out.println(new Gson().toJson(game));
    }

    @Test
    public void setGameTurnTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        game = new Game(3, "0dd1", false, null, g1, g2, null);
        game.setGameTurn(new Turn(Divinity.ATHENA));
        assertEquals("{\"currDivinity\":\"ATHENA\",\"canSwap\":false,\"cantMoveBackHere\":{\"x\":0,\"y\":0,\"ifMove\":false},\"nPossibleMoves\":1,\"nMovesMade\":0,\"pawnMoved\":false,\"canBuildDomes\":false,\"cantBuildOnThisBlock\":{\"x\":0,\"y\":0,\"ifMove\":false},\"nPossibleBuildings\":1,\"nMadeBuildings\":0,\"canBuildOnLastPlacedBlock\":false,\"canMoveAndSwap\":false,\"victoryAfterDescent\":false,\"decidesToComeUp\":false,\"canBuildBeforeMove\":false}",
                new Gson().toJson(game.getGameTurn()));
    }

    @Test
    public void getCurrentPlayerIndexTest() {
        assertSame(-1,game.getCurrentPlayerIndex());
        game.increaseCurrentPlayerIndex();
        assertSame(0,game.getCurrentPlayerIndex());

        game.getPlayers().addPlayer(new Player("G1",null,null));
        game.getPlayers().addPlayer(new Player("G2",null,null));

        game.increaseCurrentPlayerIndex();
        game.increaseCurrentPlayerIndex();
        game.increaseCurrentPlayerIndex();
        game.increaseCurrentPlayerIndex();

        assertSame(0,game.getCurrentPlayerIndex());
    }

}
