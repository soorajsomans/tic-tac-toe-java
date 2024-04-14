package models;
import exceptions.BotCountException;
import exceptions.PlayerCountMisMatchException;
import exceptions.SymbolUsedException;
import startegies.winningStrategies.WinningStrategy;

import java.util.*;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private GameState gameState;

    private int currentPlayerIndex;
    private Player winner;

    private WinningStrategy winningStrategy;

    private Game(int diamention,
                 List<Player> players,
                 WinningStrategy winningStrategy){
        this.players = players;
        this.winningStrategy = winningStrategy;
        this.moves = new ArrayList<>();
        this.currentPlayerIndex =0;
        this.gameState = GameState.IN_PROGRESS;
        this.board = new Board(diamention);

    }

    public void displayBoard() {
        this.board.displayBoard();
    }

    public void makeMove() {
        // find the player to make a move
        Player currentPlayer = players.get(currentPlayerIndex);
        Move move = currentPlayer.makeMove(board);
        moves.add(move);

        if(winningStrategy.checkWinner(board, move)){
            setGameState(GameState.SUCCESS);
            setWinner(currentPlayer);
            return;
        }

        if(moves.size() == (board.getSize()* board.getSize())){
            // Game Drawn
            setGameState(GameState.DRAW);
            return;
        }

        currentPlayerIndex = (currentPlayerIndex+1) % players.size();


    }

    public static Builder getBuilder(){
        return  new Builder();
    }

    public void undo() {
        /*
        1. Remove last entry from moves array
        2. Update the board, cell state and player
        3. Update OrderOneWinning Strategy maps (winningStrategy.handleUndo)
        4. Toggle the player
        */

        Player player = players.get(currentPlayerIndex);
        // current player already switched to
        if(player.getPlayerType().equals(PlayerType.BOT)){
            Scanner sc = new Scanner(System.in);
            System.out.println("Do you want to undo the last move ? (Y/N)");
            String isUndo = sc.nextLine();
            if(isUndo.equals("Y")) {
                Move removedMove = moves.remove(moves.size() - 1);
                Cell cell = removedMove.getCell();
                cell.setCellState(CellState.EMPTY);
                cell.setPlayer(null);
                winningStrategy.handleUndo(board, removedMove);
                currentPlayerIndex = (currentPlayerIndex+1) % players.size();
            }
        }

    }


    //inner class to validate game object using builder design pattern
    public static class Builder{
        private int diamention;
        private List<Player> players;
        private WinningStrategy winningStrategy;

        public Builder setDiamention(int diamention) {
            this.diamention = diamention;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }

        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }

//        public Builder addWinningStrategy(WinningStrategy winningStrategy){
//            this.winningStrategies.add(winningStrategy);
//            return this;
//        }
        private void validate() throws BotCountException, PlayerCountMisMatchException, SymbolUsedException {
            // there should be a single bot player
            validateBotCount();
            // no of players should be diamention-1
            validatePlayerCount();
            // different symbol for every player
            validateSymbolCount();
        }

        private void validateSymbolCount() throws SymbolUsedException {
            Set<Character> usedSymbols = new HashSet<>();
            for(Player p : players){
                if(!usedSymbols.add(p.getSymbol().getaChar())){
                    throw new SymbolUsedException();
                }
            }
        }

        private void validatePlayerCount() throws PlayerCountMisMatchException {
            if(players.size() != diamention-1){
                throw new PlayerCountMisMatchException();
            }
        }

        private void validateBotCount() throws BotCountException {
            int botCount = 0;
            for(Player p : players){
                if(p.getPlayerType().equals(PlayerType.BOT)){
                    botCount++;
                }
            }
            if(botCount>1){
                throw new BotCountException();
            }
        }

        public Game build() throws SymbolUsedException, BotCountException, PlayerCountMisMatchException {
            validate();
            return new Game(
                    this.diamention,
                    this.players,
                    this.winningStrategy
            );
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }

    public void setWinningStrategies(WinningStrategy winningStrategy) {
        this.winningStrategy = winningStrategy;
    }
}
