package itstep.learning.andrioid_213;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import itstep.learning.andrioid_213.game.TileStyle;
import itstep.learning.andrioid_213.game.TileStyles;

public class GameActivity extends AppCompatActivity {
    private final Random random = new Random();
    TextView tvScore;
    TextView tvBestScore;

    private long score = 0;
    private long bestScore = 0;
    private final int N = 4;
    private final long[][] tiles = new long[N][N];
    private final TextView[][] tvTiles = new TextView[N][N];

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("ClickableViewAccessVisibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game_layout_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout mainLayout = findViewById((R.id.game_layout_main));
        findViewById(R.id.game_new_btn).setOnClickListener(this::startNewGame);
        tvScore = findViewById(R.id.game_score);
        tvBestScore = findViewById(R.id.game_best_score);
        initTvTiles();
        startNewGame(null);

        mainLayout.post(() -> {

        });

        mainLayout.setOnTouchListener(
                new OnSwipeListener(GameActivity.this) {
                    @Override
                    public void onSwipeBottom() {
                        if(moveBottom()) {
                            spawnTile();
                        }
                        updateField();
                    }

                    @Override
                    public void onSwipeLeft() {
                        if(moveLeft()) {
                            spawnTile();
                        }
                        updateField();
                    }

                    @Override
                    public void onSwipeRight() {
                        if(moveRight()) {
                            spawnTile();
                        }
                        updateField();
                    }

                    @Override
                    public void onSwipeTop() {
                        if(moveUp()) {
                            spawnTile();
                        }
                        updateField();
                    }
                }
        );
    }

    // region Moves
    private boolean moveRight() {
        boolean res = shiftRight();
        for (int i = 0; i < N; i++) {
            for(int j = N - 1; j > 0; j--) {
                if(tiles[i][j] == tiles[i][j - 1] && tiles[i][j] != -1) {
                    tiles[i][j] = tiles[i][j] * 2;
                    tiles[i][j -1] = -1;
                    score += tiles[i][j];
                    res = true;
                }
            }
        }
        if(res) {
            shiftRight();
        }
        return res;
    }

    private boolean shiftRight() {
        boolean res = false;
        for (int i = 0; i < N; i++) {
            boolean wasReplace;
            do {
                wasReplace = false;
                for (int j = 0; j < N - 1; j++) {
                    if(tiles[i][j] != -1 && tiles[i][j+1] == -1) {
                        tiles[i][j+1] = tiles[i][j];
                        tiles[i][j] = -1;
                        wasReplace = true;
                        res = true;
                    }
                }
            } while (wasReplace);
        }
        return res;
    }

    private boolean moveLeft() {
        boolean res = shiftLeft();
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N - 1; j++) {
                if(tiles[i][j] == tiles[i][j + 1] && tiles[i][j] != -1) {
                    tiles[i][j] = tiles[i][j] * 2;
                    tiles[i][j + 1] = -1;
                    score += tiles[i][j];
                    res = true;
                }
            }
        }
        if(res) {
            shiftLeft();
        }
        return res;
    }

    private boolean shiftLeft() {
        boolean res = false;
        for (int i = 0; i < N; i++) {
            boolean wasReplace;
            do {
                wasReplace = false;
                for (int j = N - 1; j > 0; j--) {
                    if(tiles[i][j] != -1 && tiles[i][j - 1] == -1) {
                        tiles[i][j - 1] = tiles[i][j];
                        tiles[i][j] = -1;
                        wasReplace = true;
                        res = true;
                    }
                }
            } while (wasReplace);
        }
        return res;
    }

    private boolean moveBottom() {
        boolean res = shiftBottom();
        for (int i = 0; i < N; i++) {
            for(int j = N - 1; j > 0; j--) {
                if(tiles[j][i] == tiles[j - 1][i] && tiles[j][i] != -1) {
                    tiles[j][i] = tiles[j][i] * 2;
                    tiles[j - 1][i] = -1;
                    score += tiles[j][i];
                    res = true;
                }
            }
        }
        if(res) {
            shiftBottom();
        }
        return res;
    }

    private boolean shiftBottom() {
        boolean res = false;
        for (int i = 0; i < N; i++) {
            boolean wasReplace;
            do {
                wasReplace = false;
                for (int j = 0; j < N - 1; j++) {
                    if(tiles[j][i] != -1 && tiles[j + 1][i] == -1) {
                        tiles[j + 1][i] = tiles[j][i];
                        tiles[j][i] = -1;
                        wasReplace = true;
                        res = true;
                    }
                }
            } while (wasReplace);
        }
        return res;
    }

    private boolean moveUp() {
        boolean res = shiftUp();
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N - 1; j++) {
                if(tiles[j][i] == tiles[j + 1][i] && tiles[j][i] != -1) {
                    tiles[j][i] = tiles[j][i] * 2;
                    tiles[j + 1][i] = -1;
                    score += tiles[j][i];
                    res = true;
                }
            }
        }
        if(res) {
            shiftUp();
        }
        return res;
    }

    private boolean shiftUp() {
        boolean res = false;
        for (int i = 0; i < N; i++) {
            boolean wasReplace;
            do {
                wasReplace = false;
                for (int j = N - 1; j > 0; j--) {
                    if(tiles[j][i] != -1 && tiles[j - 1][i] == -1) {
                        tiles[j - 1][i] = tiles[j][i];
                        tiles[j][i] = -1;
                        wasReplace = true;
                        res = true;
                    }
                }
            } while (wasReplace);
        }
        return res;
    }

    // endregion

    private void updateTile(int x, int y, long value) {
        TileStyle style = TileStyles.tileStyles[Long.numberOfTrailingZeros(value)];
        tvTiles[x][y].setBackgroundTintList(ColorStateList.valueOf(style.getBackgroundColor()));
        tvTiles[x][y].setTextColor(style.getTextColor());
        tvTiles[x][y].setText(style.getText());
        tvTiles[x][y].setTextSize(TypedValue.COMPLEX_UNIT_SP, style.getTextSize());
    }

    private void updateField() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                updateTile(i, j, tiles[i][j]);
            }
        }
        updateScore();
    }

    private void initTvTiles() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tvTiles[i][j] = findViewById(getResources()
                        .getIdentifier("game_cell_" + i + j, "id", getPackageName()));
                tiles[i][j] = -1;
            }
        }
    }

    private boolean spawnTile() {
        List<Integer> freeTiles = new ArrayList<>(N * N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(tiles[i][j] == -1) {
                    freeTiles.add(N * i + j);
                }
            }
        }
        if(freeTiles.isEmpty()) {
            return false;
        }

        int k = freeTiles.get(random.nextInt(freeTiles.size()));
        int i = k / N;
        int j = k % N;
        tiles[i][j] = random.nextInt(10) == 0 ? 4 : 2;
        return true;
    }

    private void updateScore() {
        tvScore.setText(String.valueOf(score));
        if(score > bestScore) {
            bestScore = score;
            tvBestScore.setText(String.valueOf(bestScore));
        }
    }

    private void startNewGame(View view) {
        score = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = -1;
            }
        }
        spawnTile();
        spawnTile();
        updateField();
    }
}