public abstract class ConcretePiece implements Piece {
    private final Player owner;
    private String type;
    protected ConcretePiece(Player owner, String type) {
        this.owner = owner;
        this.type = type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType(){
        return type;
    }
}