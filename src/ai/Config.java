package ai;

import models.PieceType;

import java.util.HashMap;
import java.util.Map;

public class Config {

   public static final Map<PieceType, Integer> CAPTURED_SCORE = new HashMap<>();
   static {
       CAPTURED_SCORE.put(PieceType.KING, 1000000);
       CAPTURED_SCORE.put(PieceType.KNIGHT, 300);
       CAPTURED_SCORE.put(PieceType.BISHOP, 330);
       CAPTURED_SCORE.put(PieceType.ROOK, 500);
       CAPTURED_SCORE.put(PieceType.QUEEN, 900);
       CAPTURED_SCORE.put(PieceType.PAWN, 100);
   }
}
