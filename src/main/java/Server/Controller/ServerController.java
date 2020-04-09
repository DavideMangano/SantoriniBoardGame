package Server.Controller;

import Server.Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ServerController {


    private ArrayList<Divinity> inGameDivinities;


    /**
     *
     * @param length length of wanted random string
     * @return a random string
     */
    public static String randomString (int length) {
        Random rnd = new Random();
        char[] arr = new char[length];

        for (int i=0; i<length; i++) {
            int n = rnd.nextInt (36);
            arr[i] = (char) (n < 10 ? '0'+n : 'a'+n-10);
        }

        return new String (arr);
    }



    /**
     *
     * adds the player to the game
     * @param p player to add
     * @param if3 boolean which checks if the player wants to play in a 2 or a 3 players game
     * @return the Game to which the player is added
     */
    public Game addPlayerToModel(Player p, boolean if3){        //TODO SYNCHRONIZED
        String x;
        Model model = Model.getModel();

        if(model.getGames().size() == 0){       //starting case
            model.addGame(new Game(0,x = randomString(10),if3,null,null,null,null));
            model.searchID(x).getPlayers().addPlayer(p);
            return(model.searchID(x));
        }
        for(Game g : model.getGames()){
            if(!g.getThreePlayers() && !if3){           //if 2 players
                if(g.getPlayers().size() < 2){
                    g.getPlayers().addPlayer(p);
                    return g;
                }
            }
            else if(g.getThreePlayers() && if3){       //if 3 players
                if(g.getPlayers().size() < 3){
                    g.getPlayers().addPlayer(p);
                    return g;
                }
            }
            else {
                x = randomString(10);
                while(model.searchID(x) != null)
                {
                    x = randomString(10);
                }
                model.addGame(new Game(0,x,if3,null,null,null,null));
                model.searchID(x).getPlayers().addPlayer(p);
                return model.searchID(x);
            }
        }
        return null;
    }

    /**
     *
     * @param divinity the divinity to add to the inGameDivinities list
     */
    public void setInGameDivinities(Divinity divinity){
        inGameDivinities.add(divinity);
    }

    /**
     *
     * @return the list of Divinities
     */
    public ArrayList<Divinity> getInGameDivinities(){
        return inGameDivinities;
    }

    /**
     *
     * deletes a divinity from inGameDivinities
     * @param div the divinity to remove
     */
    public void deleteDivinity(Divinity div){
        inGameDivinities.remove(div);
    }

    /**
     *
     * @param grid grid on which to calculate the next possible move(s), based on the player's divinity
     * @return the possible MoveList
     */
    public MoveList calculateNextMove(Grid grid, Player p, String gameID, Move move, Turn turn){
        MoveList movelist = null;
        Model model = Model.getModel();
        Game game = model.searchID(gameID);

        /*Turn turn = new Turn(p.getDivinity());      //THING TO DO BEFORE CALL A INIZIO GIOCO!
        turn.startingTurn();   */                     //THING TO DO IMMEDIATELY BEFORE CALL

        if(move.getIfMove()) {              //IF MOVEMENT
            movelist = null;
            /*turn.canItComeUp(grid, move); //TODO: PUT OUTSIDE THIS FUNCTION, WHEN CALCULATENEXTMOVE() IS CALLED
            if (!turn.getCanComeUp()) {
                if (turn.getCanBuildBeforeMove()) {
                    move.setIfMove(false);
                    calculateNextMove(grid,p,gameID,move,turn);
                    move.setIfMove(true);
                    //TODO: BUILD BEFORE MOVE
                }
            }*/

            if (turn.getCanMoveAndSwap())        //MINOTAUR
            {

            }

            if (turn.getNPossibleMoves() > 0) {
                if (grid.getCells(move.getX() + 1, move.getY()).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() + 1, move.getY()).getTower().getIsDome()) {
                        if((grid.getCells(move.getX() + 1, move.getY()).getPawn() == null)) {
                            Move possMove = new Move(move.getToMove());

                            possMove.setIfMove(true);
                            possMove.setX(move.getX() + 1);
                            possMove.setY(move.getY());
                            movelist.addMove(possMove);
                        }
                        else if(turn.getCanSwap()){     //APOLLO EFFECT
                            Move possMove = new Move(move.getToMove());
                            Move consecMove = new Move(grid.getCells(move.getX() + 1,move.getY()).getPawn());
                            Pawn pawn = grid.getCells(move.getX() + 1,move.getY()).getPawn();

                            possMove.setIfMove(true);
                            possMove.setX(move.getX() + 1);
                            possMove.setY(move.getY());
                            movelist.addMove(possMove);

                            /*consecMove.setIfMove(true);     //TODO: MAKE IT IMMEDIATELY AFTER POSSMOVE
                            consecMove.setX(move.getX());
                            consecMove.setY(move.getY());
                            movelist.addMove(consecMove);*/

                        }

                    }
                }
                if (grid.getCells(move.getX() - 1, move.getY()).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() - 1, move.getY()).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX() - 1);
                        possMove.setY(move.getY());
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX() + 1, move.getY() + 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() + 1, move.getY() + 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX() + 1);
                        possMove.setY(move.getY() + 1);
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX() + 1, move.getY() - 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() + 1, move.getY() - 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX() + 1);
                        possMove.setY(move.getY() - 1);
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX() - 1, move.getY() + 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() - 1, move.getY() + 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX() - 1);
                        possMove.setY(move.getY() + 1);
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX() - 1, move.getY() - 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX() - 1, move.getY() - 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX() - 1);
                        possMove.setY(move.getY() - 1);
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX(), move.getY() + 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX(), move.getY() + 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX());
                        possMove.setY(move.getY() + 1);
                        movelist.addMove(possMove);

                    }
                }
                if (grid.getCells(move.getX(), move.getY() - 1).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                    if (!grid.getCells(move.getX(), move.getY() - 1).getTower().getIsDome()) {
                        Move possMove = new Move(move.getToMove());

                        possMove.setX(move.getX());
                        possMove.setY(move.getY() - 1);
                        movelist.addMove(possMove);

                    }
                }
                turn.setNPossibleMoves(turn.getNPossibleMoves() - 1);
                turn.setNMovesMade(turn.getNMovesMade() + 1);
            }
        }

        else if(!move.getIfMove()){             //BUILDING MOVE
            movelist = null;
            if(turn.getCanBuildDomes()){        //ATLAS EFFECT
                for(int i = 0; i <= 4; i++){
                    for(int j = 0; i <= 4; j++){
                        if(grid.getCells(i,j).getPawn() == null && !grid.getCells(i,j).getTower().getIsDome()){
                            if(game.getAvailableDomes() > 0) {
                                Move possMove = new Move(null);

                                possMove.setIfMove(false);
                                possMove.setX(i);
                                possMove.setY(j);
                                movelist.addMove(possMove);
                            }
                        }
                    }

                }
            }
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++ ){
                    if(grid.getCells(move.getX() + i, move.getY() + j).getPawn() == null && !grid.getCells(move.getX() + i, move.getY() + j).getTower().getIsDome()){
                        if(grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 0) {
                            if(game.getAvailableLevel1Buildings() > 0) {
                                Move possMove = new Move(null);

                                possMove.setIfMove(false);
                                possMove.setX(move.getX() + i);
                                possMove.setY(move.getY() + j);

                                movelist.addMove(possMove);

                            }
                        }
                        if(grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 1){
                            if(game.getAvailableLevel2Buildings() > 0) {
                                Move possMove = new Move(null);

                                possMove.setIfMove(false);
                                possMove.setX(move.getX() + i);
                                possMove.setY(move.getY() + j);

                                movelist.addMove(possMove);

                            }
                        }

                        if(grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 2){
                            if(game.getAvailableLevel3Buildings() > 0){
                                Move possMove = new Move(null);

                                possMove.setIfMove(false);
                                possMove.setX(move.getX() + i);
                                possMove.setY(move.getY() + j);

                                movelist.addMove(possMove);

                            }
                        }

                        if(grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 3){
                            if(game.getAvailableDomes() > 0){
                                Move possMove = new Move(null);

                                possMove.setIfMove(false);
                                possMove.setX(move.getX() + i);
                                possMove.setY(move.getY() + j);

                                movelist.addMove(possMove);

                            }
                        }
                    }

                }
            }
            turn.setNMadeBuildings(turn.getNMadeBuildings() + 1);
            turn.setNPossibleBuildings(turn.getNPossibleBuildings() - 1);
        }


        return movelist;
    }

    /**
     *
     * @param p player to add the divinity
     * @param div divinity to add to the player
     */
    public void addDivinityToPlayer(Player p, Divinity div){
        p.setDivinity(div);
        deleteDivinity(div);
    }

    /**
     *
     * @param grid new grid after the move
     * @param gameID the ID of the current game
     */
    public void updateModelGrid(Grid grid, String gameID){
        Model model = Model.getModel();
        Game game = model.searchID(gameID);
        game.setOldGrid(game.getNewGrid());
        game.setNewGrid(grid);          //TODO: SEND THE NEW GRID TO OTHER PLAYERS?
    }
}
