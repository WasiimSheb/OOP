public class Pawn extends ConcretePiece{

    private Player player;
    public static String isplayerone(Player x){
        if (x.isPlayerOne())
            return "\u2659";
        else
            return "\u265F";

    }
    public Pawn(Player player){
        super(player, isplayerone(player));
    }

}
