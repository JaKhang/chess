package models;

public enum Alliance {
    BLACK,
    WHITE;

    public Alliance next(){
        return this.equals(BLACK) ? WHITE : BLACK;
    }
}
