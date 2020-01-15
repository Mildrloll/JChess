package com.test.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBoard {
    @Test
    public void initialBoard() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        //assertTrue(board.currentPlayer().isKingSideCastleCapable());
        //assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        //assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        //assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        //assertTrue(board.whitePlayer().toString().equals("White"));
        //assertTrue(board.blackPlayer().toString().equals("Black"));
    }

    @Test
    public void testAI() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy moveStrategy = new MiniMax(4);
        final Move move = moveStrategy.execute(board);
        System.out.println(move);
    }

    @Test
    public void testFoolsMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                BoardUtils.getCoordinateAtPosition("g4")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveStrategy strategy = new MiniMax(4);
        final Move aiMove = strategy.execute(t3.getTransitionBoard());
        final Move bestMove = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4"));
        assertEquals(aiMove, bestMove);
    }
}
