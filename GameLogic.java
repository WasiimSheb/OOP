public class GameLogic implements PlayableLogic {
    private Player player1;
    private Player player2;
    private King king;
    private Piece[][] board = new Piece[11][11];
    private boolean isplayer2turn = true;

    public GameLogic() {
        this.player1 = new ConctretePlayer(true);
        this.king = new King(player1);
        this.player2 = new ConctretePlayer(false);
        newgame();
    }

    private void newgame() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (((i == 0) && ((j == 3) || (j == 4) || (j == 5) || (j == 6) || (j == 7))) ||
                        ((i == 1) && (j == 5)) ||
                        ((j == 0) && ((i == 3) || (i == 4) || (i == 5) || (i == 6) || (i == 7))) ||
                        ((i == 5) && (j == 1)) ||
                        ((i == 10) && ((j == 3) || (j == 4) || (j == 5) || (j == 6) || (j == 7))) ||
                        ((i == 9) && (j == 5)) ||
                        ((j == 10) && ((i == 3) || (i == 4) || (i == 5) || (i == 6) || (i == 7))) ||
                        ((j == 9) && (i == 5))) {
                    board[i][j] = new Pawn(player2);
                }
                if ((i == 5) && (j == 5)) {
                    board[i][j] = king;
                }
                if (((j == 5 && ((i == 3) || (i == 4) || (i == 6) || (i == 7)) ||
                        ((j == 4) && (i == 4 || i == 5 || i == 6) ||
                                ((j == 6) && ((i == 4) || (i == 5) || (i == 6)) ||
                                        ((j == 3) && ((i == 5))) ||
                                        ((j == 7) && (i == 5))))))) {
                    board[i][j] = new Pawn(player1);
                }
            }
        }
    }
    public void move2(Position a, Position b) {
        board[b._x][b._y] = getPieceAtPosition(a);
        board[a._x][a._y] = null;
    }

    public boolean validmove(Position a, Position b) {
        if (getPieceAtPosition(a) == getPieceAtPosition(b)) {
            return false;
        }
        if (((a._y != b._y)) && (((a._x != b._x)))) {
            return false;
        }
        if(!cornermove(a,b)){
            return false;
        }
        return true;
    }
    public boolean nobstacle(Position a, Position b) {
        boolean ans = true;
        if (getPieceAtPosition(b) != null)
            ans = false;
        if (a._x == b._x) {
            if (b._y > a._y) {
                for (int i = a._y + 1; i < b._y; i++) {
                    if (board[a._x][i] != null) {
                        ans = false;
                    }
                }
            }
            else {
                for (int i = a._y - 1; i > b._y; i--) {
                    if (board[a._x][i] != null) {
                        ans = false;
                    }
                }
            }
        }
        else {
            if (a._y == b._y) {
                if (b._x > a._x) {
                    for (int i = a._x + 1; i < b._x; i++) {
                        if (board[i][a._y] != null) {
                            ans = false;
                        }
                    }
                } else
                    for (int i = a._x - 1; i > b._x; i--) {
                        if (board[i][a._y] != null) {
                            ans = false;
                        }
                    }
            }
        }
        return ans;
    }

    public boolean cornermove(Position a, Position b){
        if ((b.get_x() == 10 && b.get_y() == 10) || (b.get_x() == 0 && b.get_y() == 0) ||
                (b.get_x() == 0 && b.get_y() == 10) || (b.get_x() == 10 && b.get_y() == 0)) {
            if (isplayer2turn) {
                return false;
            }
            else {
                if (getPieceAtPosition(a) == king){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }
    private Piece [] neighbors (Piece p){
        Position a = getpos(p);
        Piece [] neighboring = new Piece[4];
        if (a != null){
            if (a._y < 10) {
                neighboring[0] = board[a._x][a._y + 1];
            }
            if (a._x < 10) {
                neighboring[1] = board[a._x + 1][a._y];
            }
            if (a._y > 0) {
                neighboring[2] = board[a._x][a._y - 1];
            }
            if (a._x > 0) {
                neighboring[3] = board[a._x - 1][a._y];
            }
        }
        return neighboring;
    }

    @Override
    public boolean move(Position a, Position b) {
        if(!validmove(a, b) || !nobstacle(a, b)){return false;}
        if (isplayer2turn && board[a._x][a._y].getOwner() == player2) {
            move2(a, b);
            isplayer2turn = false;
            inthecorner(a,b);
            kill(a,b);
        } else if (!isplayer2turn && board [a._x][a._y].getOwner() == player1) {
            move2(a, b);
            isplayer2turn = true;
            inthecorner(a,b);
            kill(a,b);
        }
        return true;
    }


    private boolean inthemiddle(Position a, Position b){
        if (a.equals(b)){return false;}
        Piece [] neighbors = neighbors(getPieceAtPosition(b));
            for (int i = 0; i < neighbors.length; i++) {
                if (getPieceAtPosition(a).getOwner() != neighbors[i].getOwner()){
                    return false;
                }
            }
        return true;
    }
    private void kill(Position a, Position b){
        if (!(a.equals(b))){
            Piece p2 = getPieceAtPosition(b);
            Piece [] neighboring = neighbors(p2);
            for (int i = 0; i < neighboring.length; i++) {
                if (neighboring[i] != null && neighboring[i].getOwner() != p2.getOwner()) {
                    Piece tar= neighboring[i];
                    Piece [] nearby = neighbors(tar);
                    if (tar != king && nearby[1] != null && nearby[3] != null && nearby[1].getOwner() != tar.getOwner() && nearby[3].getOwner() != tar.getOwner()){
                        Position position = getpos(tar);
                        board[position._x][sandal._y] = null;
                    }
                    if (tar != king && nearby[0] != null && nearby[2] != null && nearby[0].getOwner() != tar.getOwner() && nearby[2].getOwner() != tar.getOwner()) {
                        Position position = getpos(tar);
                        board[sandal._x][sandal._y] = null;
                    }
                }
            }
        }
    }
     private void inthecorner(Position a, Position b){
            Piece [] neighboring = neighbors(getPieceAtPosition(b));
            Piece p2 = getPieceAtPosition(b);
            if (p2 != king) {
                if (b._x == 1) {
                    if (neighboring[3] != null && neighboring[3].getOwner() != p2.getOwner()) {
                        board[b._x - 1][b._y] = null;
                    }
                }
                if (b._x == 9) {
                    if (neighboring[1] != null && neighboring[1].getOwner() != p2.getOwner()) {
                        board[b._x + 1][b._y] = null;
                    }
                }
                if (b._y == 9) {
                    if (neighboring[0] != null && neighboring[0].getOwner() != p2.getOwner()) {
                        board[b._x][b._y + 1] = null;
                    }
                }
                if (b._y == 1) {
                    if (neighboring[2] != null && neighboring[2].getOwner() != p2.getOwner()) {
                        board[b._x][b._y - 1] = null;
                    }
                }
            }
        }
    
         private boolean kingdead(){
            Piece [] neighboring = neighbors(king);
            for (int i = 0; i < neighboring.length; i++) {
                if(neighboring[i] == null || neighboring[i].getOwner() == player1){
                    break;
                }
                if (neighboring[i].getOwner() == player2){
                    if (i == 3) {
                        reset();
                        player2.addWins();
                        return true;
                    }
                }
            }
            return false;
        }
    
    // private boolean kingdead(){
    //     Piece [] neighboring = neighbors(king);
    //     for (int i = 0; i < neighboring.length; i++) {
    //         if (neighboring[i] != null && neighboring[i].getOwner() == player2){
    //             if (i == 3) {
    //                 reset();
    //                 player2.addWins();
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }

    private boolean kingincorner(){
        Position p = getpos(king);
        if ((p._x == 10 && p._y == 10) || (p.get_x() == 0 && p.get_y() == 0) ||
                (p.get_x() == 0 && p.get_y() == 10) || (p.get_x() == 10 && p.get_y() == 0)){
            reset();
            player1.addWins();
            return true;
        }
        return false;
    }
    private Position getpos(Piece a){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
              if (board[i][j] != null && board[i][j].equals(a)){
                 return new Position(i,j);
              }
            }
        }
        return null;
    }

        @Override
        public Piece getPieceAtPosition (Position position){
        return board[position._x][position._y];
        }

        @Override
        public Player getFirstPlayer () {
            return player1;
        }

        @Override
        public Player getSecondPlayer () {
            return player2;
        }

        @Override
        public boolean isGameFinished () {
            return kingdead() || kingincorner();
        }

        @Override
        public boolean isSecondPlayerTurn () {
            return isplayer2turn;
        }

        @Override
        public void reset () {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    board[i][j] = null;
                }
            }
            newgame();
        }

        @Override
        public void undoLastMove () {

        }

        @Override
        public int getBoardSize () {
            return 11;
        }
}
