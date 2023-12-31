package Server.Model;

import it.polimi.ingsw.PSP19.Server.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnTest {

    static Turn turn;

    @Before
    public void setUp() {
        turn = new Turn(Divinity.ATHENA);
        turn.startingTurn(Divinity.ATHENA);
    }

    @After
    public void tearDown() {
        turn = null;
    }

    @Test
    public void getCurrDivinityTest() {
        assertSame(Divinity.ATHENA, turn.getCurrDivinity());
    }

    @Test
    public void getEnemyPawnTest() {
        Player owner = new Player("uName",Divinity.ATHENA, Colour.RED);
        Player owner2 = new Player("uName2",Divinity.PROMETHEUS,Colour.BLUE);

        Pawn p = new Pawn(owner);
        turn.setEnemyPawn1(p);
        assertEquals(p,turn.getEnemyPawn1());
        Pawn p2 = new Pawn(owner);
        turn.setEnemyPawn2(p2);
        assertEquals(p2,turn.getEnemyPawn2());
        Pawn p3 = new Pawn(owner2);
        turn.setEnemyPawn3(p3);
        assertEquals(p3,turn.getEnemyPawn3());
        Pawn p4 = new Pawn(owner2);
        turn.setEnemyPawn4(p4);
        assertEquals(p4,turn.getEnemyPawn4());
    }

    @Test
    public void getCanSwapTest() {
        turn.setCanSwap(true);
        assertSame(true, turn.getCanSwap());
    }


    @Test
    public void getLastPlacedBlock() {
        Move move = new Move(null);
        move.setX(0);
        move.setY(0);
        move.setIfMove(false);
        turn.setLastPlacedBlock(move);
        assertSame(0, turn.getLastPlacedBlock().getX());
        assertSame(0, turn.getLastPlacedBlock().getY());
        assertFalse(turn.getLastPlacedBlock().getIfMove());
    }

    @Test
    public void getPawnMovedTest() {
        turn.setPawnMoved(false);
        assertSame(false, turn.getPawnMoved());
    }

    @Test
    public void getCanBuildDomesTest() {
        turn.setCanBuildDomes(true);
        assertSame(true, turn.getCanBuildDomes());
    }

    @Test
    public void getCanBuildOnLastBlockTest() {
        turn.setCanBuildOnLastPlacedBlock(false);
        assertSame(false, turn.getCanBuildOnLastBlock());
    }

    @Test
    public void getCanMoveAndSwapTest() {
        turn.setCanMoveAndSwap(true);
        assertSame(true, turn.getCanMoveAndSwap());
    }

    @Test
    public void getDecidesToComeUpTest() {
        turn.setDecidesToComeUp(false);
        assertSame(false, turn.getDecidesToComeUp());
    }

    @Test
    public void getCanBuildBeforeMoveTest() {
        turn.setCanBuildBeforeMove(true);
        assertSame(true, turn.getCanBuildBeforeMove());
    }


    @Test
    public void getVictoryAfterDescent() {
        turn.setVictoryAfterDescent(false);
        assertSame(false, turn.getVictoryAfterDescent());
    }

    @Test
    public void getCantMoveBackThereTest() {
        Player owner = new Player("uName",Divinity.ATHENA,Colour.RED);
        Pawn p = new Pawn(owner);
        Move move = new Move(p);
        move.setX(0);
        move.setY(0);
        move.setIfMove(true);
        turn.setCantMoveBackHere(move);
        assertEquals(move,turn.getCantMoveBackHere());
    }

    @Test
    public void getCantBuildOnThisBlockTest() {
        Player owner = new Player("uName",Divinity.ATHENA,Colour.RED);
        Pawn p = new Pawn(owner);
        Move move = new Move(p);
        move.setX(0);
        move.setY(0);
        move.setIfMove(true);
        turn.setCantBuildOnThisBlock(move);
        assertEquals(move,turn.getCantBuildOnThisBlock());
    }

    @Test
    public void getNPossibleMovesTest() {
        turn.setNPossibleMoves(5);
        assertSame(5, turn.getNPossibleMoves());
    }

    @Test
    public void getNPossibleBuildingsTest() {
        turn.setNPossibleBuildings(5);
        assertSame(5, turn.getNPossibleBuildings());
    }

    @Test
    public void getNMadeMovesTest() {
        turn.setNMovesMade(5);
        assertSame(5, turn.getNMovesMade());
    }

    @Test
    public void getNMadeBuildingsTest() {
        turn.setNMadeBuildings(5);
        assertSame(5, turn.getNMadeBuildings());
    }

    @Test
    public void checkIfWinTest() {
        assertSame(false,turn.checkIfWin());
        turn.startingTurn(Divinity.PAN);
        assertSame(true,turn.checkIfWin());
    }


    @Test
    public void startingTurnTest(){
        turn.startingTurn(Divinity.APOLLO);
        assertSame(true,turn.getCanSwap());
        turn.startingTurn(Divinity.ARTEMIS);
        assertSame(2,turn.getNPossibleMoves());
        turn.startingTurn(Divinity.ATHENA);
        assertSame(false,turn.getPawnMoved());
        turn.startingTurn(Divinity.ATLAS);
        assertSame(true,turn.getCanBuildDomes());
        turn.startingTurn(Divinity.DEMETER);
        assertSame(2,turn.getNPossibleBuildings());
        turn.startingTurn(Divinity.HEPHAESTUS);
        assertSame(true,turn.getCanBuildOnLastBlock());
        turn.startingTurn(Divinity.MINOTAUR);
        assertSame(true,turn.getCanMoveAndSwap());
        turn.startingTurn(Divinity.PAN);
        assertSame(true,turn.getVictoryAfterDescent());
        turn.startingTurn(Divinity.PROMETHEUS);
        turn.setDecidesToComeUp(false);
        assertSame(false,turn.getCanBuildBeforeMove());
        turn.startingTurn(Divinity.PROMETHEUS);
        turn.setDecidesToComeUp(true);
        assertSame(true,turn.getCanBuildBeforeMove());
    }

    @Test(expected = NullPointerException.class)
    public void startingTurnExceptionTest(){
        turn.startingTurn(null);
    }


}