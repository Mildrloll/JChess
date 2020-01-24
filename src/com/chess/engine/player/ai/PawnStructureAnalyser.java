package com.chess.engine.player.ai;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PawnStructureAnalyser {
    private static final PawnStructureAnalyser INSTANCE = new PawnStructureAnalyser();
    public static final int ISOLATED_PAWN_PENALTY = -25;
    public static final int DOUBLED_PAWN_PENALTY = -25;

    private PawnStructureAnalyser() {

    }

    public static PawnStructureAnalyser get() {
        return INSTANCE;
    }

    public int isolatedPawnPenalty(final Player player) {
        return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int doubledPawnPenalty(final Player player) {
        return calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)));
    }

    public int pawnStructureScore(final Player player) {
        final int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
        return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<Piece> calculatePlayerPawns(final Player player) {
        final List<Piece> playerPawnLocations = new ArrayList<>(8);
        for (final Piece piece : player.getActivePieces()) {
            if (piece.getPieceType().isPawn()) {
                playerPawnLocations.add(piece);
            }
        }
        return ImmutableList.copyOf(playerPawnLocations);
    }

    private static int calculatePawnColumnStack(final int[] pawnsOnColumnTable) {
        int pawnStackPenalty = 0;
        for (final int pawnStack : pawnsOnColumnTable) {
            if (pawnStack > 1) {
                pawnStackPenalty += pawnStack;
            }
        }
        return pawnStackPenalty + DOUBLED_PAWN_PENALTY;
    }

    private static int calculateIsolatedPawnPenalty(final int[] pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if (pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[0];
        }
        if (pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[7];
        }
        for (int i = 1; i < pawnsOnColumnTable.length - 1; i++) {
            if ((pawnsOnColumnTable[i - 1] == 0 && pawnsOnColumnTable[i + 1] == 0)) {
                numIsolatedPawns += pawnsOnColumnTable[i];
            }
        }
        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    private static int[] createPawnColumnTable(final Collection<Piece> playerPawns) {
        final int[] table = new int[8];
        for (final Piece playerPawn : playerPawns) {
            table[playerPawn.getPiecePosition() % 8]++;
        }
        return table;
    }
}
