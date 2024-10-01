package config;

import models.Alliance;

public class GameConfig {
    private Alliance firstMove;
    private boolean alphaBeta;

    public Alliance getFirstMove() {
        return firstMove;
    }

    public boolean isAlphaBeta() {
        return alphaBeta;
    }

    public void setAlphaBeta(boolean alphaBeta) {
        this.alphaBeta = alphaBeta;
    }

    public void setFirstMove(Alliance firstMove) {
        this.firstMove = firstMove;
    }

    public static GameConfig getConfig() {
        return config;
    }

    public static void setConfig(GameConfig config) {
        GameConfig.config = config;
    }

    private GameConfig(){
        // default
        firstMove = Alliance.WHITE;
        alphaBeta = true;
    }


    /*------------------
        Singleton
    --------------------*/
    private static GameConfig config ;

    public static GameConfig instance(){
        if (config == null)
            config = new GameConfig();
        return config;
    }
}
