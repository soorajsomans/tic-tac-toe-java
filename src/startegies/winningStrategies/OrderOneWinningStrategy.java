package startegies.winningStrategies;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneWinningStrategy implements WinningStrategy{

    private List<Map<Symbol, Integer>> rows;
    private List<Map<Symbol, Integer>> cols;
    private Map<Symbol, Integer> diagonal;
    private Map<Symbol, Integer> reverse_diagonal;

    public  OrderOneWinningStrategy(int size){
        rows = new ArrayList<>();
        cols = new ArrayList<>();
        diagonal = new HashMap<>();
        reverse_diagonal = new HashMap<>();
        for(int i=0;i<size;i++){
            rows.add(new HashMap<>());
            cols.add(new HashMap<>());
        }
    }

    @Override
    public boolean checkWinner(Board board, Move move) {
        Cell cell = move.getCell();
        Player player = move.getPlayer();
        int row = cell.getRow();
        int col = cell.getColumn();

        Map<Symbol, Integer> rowMap = rows.get(row);
        rowMap.put(player.getSymbol(), rowMap.getOrDefault(player.getSymbol(), 0)+1);

        Map<Symbol, Integer> colMap = cols.get(col);
        colMap.put(player.getSymbol(), colMap.getOrDefault(player.getSymbol(), 0)+1);

        //Diagonal check
        if(row == col){
            diagonal.put(player.getSymbol(), diagonal.getOrDefault(player.getSymbol(), 0)+1);
        }
        //Reverse diagonal check

        if(row+col == board.getSize() -1){
            reverse_diagonal.put(player.getSymbol(), reverse_diagonal.getOrDefault(player.getSymbol(), 0)+1);
        }

        // check if player winning or not
        return  rowMap.get(player.getSymbol()) == board.getSize() ||
                colMap.get(player.getSymbol()) == board.getSize() ||
                diagonal.getOrDefault(player.getSymbol(),0) == board.getSize() ||
                reverse_diagonal.getOrDefault(player.getSymbol(),0) == board.getSize();
    }
}
