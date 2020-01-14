package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MiniMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;

    public MiniMax() {
        this.boardEvaluator = null;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public Move execute(Board board, int depth) {
        return null;
    }
}
