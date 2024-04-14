import controllers.GameController;
import exceptions.BotCountException;
import exceptions.PlayerCountMisMatchException;
import exceptions.SymbolUsedException;
import models.*;
import startegies.winningStrategies.OrderOneWinningStrategy;
import startegies.winningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SymbolUsedException, BotCountException, PlayerCountMisMatchException {
        GameController gameController = new GameController();
        System.out.println("Game is starting");

        Player player1 = new Player("Player1",1,new Symbol('X'), PlayerType.HUMAN);
        //Player player2 = new Player("Player2",2,new Symbol('O'), PlayerType.HUMAN);
        Player bot = new Bot("Bot Player", 0, new Symbol('B'), PlayerType.BOT, BotDifficultyLevel.EASY);

        Game game = gameController.startGame(3, Arrays.asList(player1,bot), new OrderOneWinningStrategy(3));
        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
            gameController.displayBoard(game);
            gameController.makeMove(game);
            //Undo Feature
            if(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
                gameController.undo(game);
            }
        }
        if(gameController.checkState(game).equals(GameState.SUCCESS)){
            System.out.println("Winner is "+ gameController.getWinner(game).getName());
        }else if(gameController.checkState(game).equals(GameState.DRAW)){
            System.out.println("Game is Draw");
        }
    }
}