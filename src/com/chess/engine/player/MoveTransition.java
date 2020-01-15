package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {
    private final Board transitionBoard;
    private final Board toBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Board toBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }

    public Board getToBoard() {
        return this.toBoard;
    }

    public Move getMove() {
        return this.move;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
