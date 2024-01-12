public class ConctretePlayer implements Player{
    private int win;
    private boolean isplayerone;

    public ConctretePlayer(boolean isplayerone) {
        this.win = 0;
        this.isplayerone = isplayerone;
    }

    @Override
    public boolean isPlayerOne() {
        return isplayerone;
    }

    @Override
    public int getWins() {
        return win;
    }

    @Override
    public void addWins() {
        this.win ++;
    }
}
