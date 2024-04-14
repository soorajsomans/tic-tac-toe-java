package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public Board(int diamention){
        this.size = diamention;
        board = new ArrayList<>();
        for(int i=0;i<diamention;i++){
            List<Cell> row = new ArrayList<>();
            for(int j=0;j<diamention;j++){
                Cell cell = new Cell();
                cell.setRow(i);
                cell.setColumn(j);
                cell.setCellState(CellState.EMPTY);
                row.add(cell);
            }
            board.add(row);
        }
    }
    private int size;
    private List<List<Cell>> board;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }

    public void displayBoard() {
        System.out.println("Tic Tac Toe");
        System.out.println("=======================================");
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                Cell cell = board.get(i).get(j);
                if(cell.getPlayer() == null){
                    System.out.print(" - ");
                }else{
                    System.out.print(" "+cell.getPlayer().getSymbol().getaChar()+" ");
                }
            }
            System.out.println();
        }
        System.out.println("=======================================");
        System.out.println();
    }
}
