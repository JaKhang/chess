package ai;

import models.Board;

public class CompoundHeuristics implements Heuristics {
    private final CapturesHeuristics capturesHeuristics;
    private final EvalHeuristics evalHeuristics;

    public CompoundHeuristics() {
        this.evalHeuristics = new EvalHeuristics();
        this.capturesHeuristics = new CapturesHeuristics();
    }

    @Override
    public int get(Board state) {
        return capturesHeuristics.get(state) + evalHeuristics.get(state);
    }
}
