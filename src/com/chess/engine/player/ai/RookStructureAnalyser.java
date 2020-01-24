package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.util.ArrayList;
import java.util.List;

public class RookStructureAnalyser {
    private static final RookStructureAnalyser INSTANCE = new RookStructureAnalyser();
    private static final List<List<Boolean>> BOARD_COLUMNS = initColumns();
    private static final int OPEN_COLUMN_ROOK_BONUS = 25;
    private static final int NO_BONUS = 0;

    private RookStructureAnalyser() {

    }

    public static RookStructureAnalyser get() {
        return INSTANCE;
    }

    private static List<List<Boolean>> initColumns() {
        final List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardUtils.INSTANCE.FIRST_COLUMN);
        columns.add(BoardUtils.INSTANCE.SECOND_COLUMN);
        columns.add(BoardUtils.INSTANCE.THIRD_COLUMN);
        columns.add(BoardUtils.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.EIGHTH_COLUMN);
        return ImmutableList.copyOf(columns);
    }

    public int rookStructureScore(final Board board,
                                  final Player player) {
        final List<Integer> rookLocations = calculateRookLocations(player);
        return calculateOpenFileRookBonus(board, rookLocations);
    }

    private static List<Integer> calculateRookLocations(final Player player) {
        final Builder<Integer> playerRookLocations = new Builder<>();
        for (final Piece piece : player.getActivePieces()) {
            if (piece.getPieceType().isRook()) {
                playerRookLocations.add(piece.getPiecePosition());
            }
        }
        return playerRookLocations.build();
    }

    private static int calculateOpenFileRookBonus(final Board board,
                                                  final List<Integer> rookLocations) {
        int bonus = NO_BONUS;
        for (final Integer rookLocation : rookLocations) {
            final int[] piecesOnColumn = createPiecesOnColumnTable(board);
            final int rookColumn = rookLocation / 8;
            for (int i = 0; i < piecesOnColumn.length; i++) {
                if (piecesOnColumn[i] == 1 && i == rookColumn) {
                    bonus += OPEN_COLUMN_ROOK_BONUS;
                }
            }
        }
        return bonus;
    }

    private static int[] createPiecesOnColumnTable(final Board board) {
        final int[] piecesOnColumnTable = new int[BOARD_COLUMNS.size()];
        for (final Piece piece : board.getAllPieces()) {
            for (int i = 0; i < BOARD_COLUMNS.size(); i++) {
                if (BOARD_COLUMNS.get(i).get(piece.getPiecePosition())) {
                    piecesOnColumnTable[i]++;
                }
            }
        }
        return piecesOnColumnTable;
    }
}
