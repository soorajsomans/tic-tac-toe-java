package controllers;

import exceptions.BotCountException;
import exceptions.PlayerCountMisMatchException;
import exceptions.SymbolUsedException;
import models.Game;
import models.GameState;
import models.Player;
import startegies.winningStrategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int diamention,
                          List<Player> players,
                          WinningStrategy winningStrategy) throws SymbolUsedException, BotCountException, PlayerCountMisMatchException {
        return Game.getBuilder()
                .setDiamention(diamention)
                .setPlayers(players)
                .setWinningStrategy(winningStrategy)
                .build();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public void displayBoard(Game game){
        game.displayBoard();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public void undo(Game game){}
}
