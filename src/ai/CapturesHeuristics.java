package ai;

import models.Alliance;
import models.Board;
import models.Square;

public class CapturesHeuristics implements Heuristics{

    @Override
    public int get(Board state) {
        int score = 0;

        for (Square square : state){
            if (square.havePiece() && square.getPiece().is(state.getCurrent()))
                score += Config.CAPTURED_SCORE.get(square.getPiece().getPieceType());
        }

        for (Square square : state){
            if (square.havePiece() && square.getPiece().is(state.getCurrent().next()))
                score -= Config.CAPTURED_SCORE.get(square.getPiece().getPieceType());
        }


        return score;
    }
}
