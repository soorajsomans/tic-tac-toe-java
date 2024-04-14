import controllers.GameController;
import exceptions.BotCountException;
import exceptions.PlayerCountMisMatchException;
import exceptions.SymbolUsedException;
import models.*;
import startegies.winningStrategies.OrderOneWinningStrategy;
import startegies.winningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SymbolUsedException, BotCountException, PlayerCountMisMatchException {
        GameController gameController = new GameController();
        System.out.println("Game is starting");

        int boardSize =0;
        int numberOfPlayers = 0;
        List<Player> players = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of players");
        numberOfPlayers = sc.nextInt();
        boardSize = numberOfPlayers+1;

        System.out.println("Do you need bot player (Y/N)");
        String isBot = sc.next();

        if(isBot.equals("Y")){
            Player bot = new Bot("Bot Player", 0, new Symbol('B'), PlayerType.BOT, BotDifficultyLevel.EASY);
            players.add(bot);
            numberOfPlayers  = numberOfPlayers > 1 ? numberOfPlayers--:1;
        }

        for(int i=0;i<numberOfPlayers;i++){
            System.out.println("Please enter player details");
            System.out.println("Name :");
            String name = sc.next();
            System.out.println("Symbol :");
            char c = sc.next().charAt(0);
            Player p = new Player(name, i++, new Symbol(c), PlayerType.HUMAN);
            players.add(p);
        }


        //Player player1 = new Player("Player1",1,new Symbol('X'), PlayerType.HUMAN);
        //Player player2 = new Player("Player2",2,new Symbol('O'), PlayerType.HUMAN);

        // TODO get bot difficulty level and winning strategy from user
        Game game = gameController.startGame(boardSize, players, new OrderOneWinningStrategy(boardSize));
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