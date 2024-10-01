package models;

import ai.AI;
import ai.CompoundHeuristics;
import ai.Minimax;

public class Player {
    private String name;
    private Alliance alliance;
    private PlayerType type;
    private AI ai;

    public boolean is(Alliance alliance){
        return alliance.equals(this.alliance);
    }

    public Player(Alliance alliance, PlayerType playerType) {
        this.alliance = alliance;
        this.type = playerType;
        if(playerType == PlayerType.BOT){
            ai = new AI(new Minimax(alliance, 4, new CompoundHeuristics()));
        }
    }



    public boolean isHuman() {
        return type == PlayerType.HUMAN;
    }

    public boolean isBot() {return type == PlayerType.BOT;}
    public AI getAi() {
        return ai;
    }

    public Alliance getSet() {
        return alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public PlayerType getType() {
        return type;
    }
}
