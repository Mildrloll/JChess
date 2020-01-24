package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

public class JChess {
    public static void main(final String[] args) throws Exception {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table.get().show();
    }
}
