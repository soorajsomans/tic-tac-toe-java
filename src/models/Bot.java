package models;

import factories.BotPlayingStrategyFactory;
import startegies.botPlayingStartegies.BotPlayingStrategy;

public class Bot extends Player{
    public Bot(String name, int id, Symbol symbol, PlayerType playerType, BotDifficultyLevel botDifficultyLevel) {
        super(name, id, symbol, playerType);
        this.botDifficultyLevel = botDifficultyLevel;
    }

    private BotDifficultyLevel botDifficultyLevel;

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }

    @Override
    public Move makeMove(Board board) {
        System.out.println("Its "+ getName() + "'s turn");
        BotPlayingStrategy strategy = BotPlayingStrategyFactory.getStrategy(botDifficultyLevel);
        Move move = strategy.makeMove(board, this);
        System.out.println(getName() + " made a move on row :"+move.getCell().getRow()+" col :"+move.getCell().getColumn());
        return move;
    }
}
