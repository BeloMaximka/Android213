package itstep.learning.andrioid_213.game;

public class SavedState {
    private final long[][] tiles;
    private long score;
    private long bestScore;

    public SavedState(long[][] tiles, long score, long bestScore) {
        this.tiles = tiles;
        this.score = score;
        this.bestScore = bestScore;
    }

    public long[][] getTiles() {
        return tiles;
    }

    public long getScore() {
        return score;
    }

    public long getBestScore() {
        return bestScore;
    }
}
