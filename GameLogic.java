import java.util.*;

public class GameLogic implements PlayableLogic {
    private Player player1;
    private Player player2;
    private King king;
    private Piece[][] board = new Piece[11][11];
    private boolean isplayer2turn = true;
    Stack<Position> strides = new Stack<>();
    HashMap<String, ArrayList<Position>> firstplayer = new HashMap<>();
    HashMap<String, ArrayList<Position>> secondplayer = new HashMap<>();
    Map<String, Piece> player1kills = new HashMap<>();
    Map<String, Piece> player2kills = new HashMap<>();
    Stack<Piece> pieces = new Stack<>();
    String[] keys1 = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10",
            "D11", "D12", "D13"};
    String[] keys2 = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10",
            "A11", "A12", "A13", "A14", "A15", "A16", "A17", "A18",
            "A19", "A20", "A21", "A22", "A23", "A24"};

    public GameLogic() {
        this.player1 = new ConctretePlayer(true);
        this.king = new King(player1);
        this.player2 = new ConctretePlayer(false);
        newgame();
        // newboard();
        initHashMapForPlayer1();
        initHashMapForPlayer2();
    }

    private void initHashMapForPlayer2() {
        int[][] positions = {
                {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {5, 1}, {0, 3}, {10, 3},
                {0, 4}, {10, 4}, {0, 5}, {1, 5}, {9, 5}, {10, 5}, {0, 6}, {10, 6},
                {0, 7}, {10, 7}, {5, 9}, {3, 10}, {4, 10}, {5, 10}, {6, 10}, {7, 10}
        };

        for (int i = 0; i < keys2.length; i++) {
            String key = keys2[i];
            int x = positions[i][0];
            int y = positions[i][1];

            ArrayList<Position> positionsList = new ArrayList<>();
            positionsList.add(new Position(x, y));
            secondplayer.put(key, positionsList);
        }
    }

    private void initHashMapForPlayer1() {
        int[][] positions = {
                {5, 3}, {4, 4}, {5, 4}, {6, 4}, {3, 5}, {4, 5}, {5, 5},
                {6, 5}, {7, 5}, {4, 6}, {5, 6}, {6, 6}, {5, 7}
        };

        for (int i = 0; i < keys1.length; i++) {
            String key = keys1[i];
            int x = positions[i][0];
            int y = positions[i][1];

            ArrayList<Position> positionsList = new ArrayList<>();
            positionsList.add(new Position(x, y));
            firstplayer.put(key, positionsList);
        }
    }

    private void secondsave(Position a, Position b) {
        for (String s : keys2) {
            ArrayList<Position> positions = secondplayer.get(s);
            if (a.equals(positions.get(positions.size() - 1))) {
                positions.add(b);
            }
        }
    }

    private void firstsave(Position a, Position b) {
        for (String s : keys1) {
            ArrayList<Position> positions = firstplayer.get(s);
            if (a.equals(positions.get(positions.size() - 1))) {
                positions.add(b);
            }
        }
    }

//    private void countstrides(){
//       ArrayList<Map.Entry<String, Integer>> piecesmoves = new ArrayList<>();
//
//    }

    private void printgamestats1() {
        ArrayList<Map.Entry<String, ArrayList<Position>>> sort1 = new ArrayList<>(firstplayer.entrySet());
        ArrayList<Map.Entry<String, ArrayList<Position>>> sort2 = new ArrayList<>(secondplayer.entrySet());

        Comparator<Map.Entry<String, ArrayList<Position>>> entryComparator = new compare();

        sort1.sort(entryComparator);
        sort2.sort(entryComparator);

        if (isplayer2turn) {
            for (Map.Entry<String, ArrayList<Position>> entry : sort1) {
                String key = entry.getKey();
                ArrayList<Position> positions = entry.getValue();
                if (positions.size() > 1) {
                    System.out.println("" + key + ":" + positions + "");
                }
            }
        } else {
            for (Map.Entry<String, ArrayList<Position>> entry : sort2) {
                String key = entry.getKey();
                ArrayList<Position> positions = entry.getValue();
                if (positions.size() > 1) {
                    System.out.println("" + key + ":" + positions + "");
                }
            }
        }
    }

    private void printgamestats2() {
        Comparator<Map.Entry<String, Piece>> entryComparator = new comparekills();

        ArrayList<Map.Entry<String, Piece>> sortedKills1;
        ArrayList<Map.Entry<String, Piece>> sortedKills2;

        sortedKills1 = new ArrayList<>(player1kills.entrySet());
        sortedKills2 = new ArrayList<>(player2kills.entrySet());

        List<Map.Entry<String, Piece>> merge = new ArrayList<>(sortedKills1);
        merge.addAll(sortedKills2);
        merge.sort(entryComparator);

        int j;
        for (int i = 0; i < merge.size() - 1; i++) {
             j = i + 1;
             if(isplayer2turn) {
                 if (merge.get(i).getKey().startsWith("A") && merge.get(j).getKey().startsWith("D") && merge.get(i).getValue().getKills() == merge.get(j).getValue().getKills()){
                     Collections.swap(merge, i, j);
                 }
             }
             else {
                 if (merge.get(i).getKey().startsWith("D") && merge.get(j).getKey().startsWith("A") && merge.get(i).getValue().getKills() == merge.get(j).getValue().getKills()) {
                     Collections.swap(merge, j, i);
                 }
             }
        }
        for (Map.Entry<String, Piece> stringPieceEntry : merge) {
            if (stringPieceEntry.getValue().getKills() > 0) {
                System.out.println(stringPieceEntry.getKey() + ": " + stringPieceEntry.getValue().getKills() + " kills");
            }
        }
    }

    private void printgamestats3(){

    }

    private void newgame() {
        int[][] player2PawnPosition = {
                {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {5, 1}, {0, 3}, {10, 3},
                {0, 4}, {10, 4}, {0, 5}, {1, 5}, {9, 5}, {10, 5}, {0, 6}, {10, 6},
                {0, 7}, {10, 7}, {5, 9}, {3, 10}, {4, 10}, {5, 10}, {6, 10}, {7, 10}
        };

        int[][] player1PawnPosition = {
                {5, 3}, {4, 4}, {5, 4}, {6, 4}, {3, 5}, {4, 5}, {5, 5},
                {6, 5}, {7, 5}, {4, 6}, {5, 6}, {6, 6}, {5, 7}
        };

        int i = 0;
        int j = 0;
        for (int[] pos1 : player1PawnPosition) {
            board[pos1[0]][pos1[1]] = new Pawn(player1);
            while (i < keys1.length) {
                String key = keys1[i];
                player1kills.put(key, board[pos1[0]][pos1[1]]);
                i++;
                break;
            }
        }
        board[5][5] = king;
        for (int[] pos2 : player2PawnPosition) {
            board[pos2[1]][pos2[0]] = new Pawn(player2);
            while (j < keys2.length) {
                String key = keys2[j];
                player2kills.put(key, board[pos2[1]][pos2[0]]);
                j++;
                break;
            }
        }
    }

    public void move2(Position a, Position b) {
        pieces.push(getPieceAtPosition(a));
        strides.push(a);
        board[b.get_x()][b.get_y()] = getPieceAtPosition(a);
        board[a.get_x()][a.get_y()] = null;
    }

    public boolean validmove(Position a, Position b) {
        if (getPieceAtPosition(a) == getPieceAtPosition(b)) {
            return false;
        }
        if (((a.get_y() != b.get_y())) && (((a.get_x() != b.get_x())))) {
            return false;
        }
        if (!cornermove(a, b)) {
            return false;
        }
        return true;
    }

    public boolean nobstacle(Position a, Position b) {
        boolean ans = true;
        if (getPieceAtPosition(b) != null)
            ans = false;
        if (a.get_x() == b.get_x()) {
            if (b.get_y() > a.get_y()) {
                for (int i = a.get_y() + 1; i < b.get_y(); i++) {
                    if (board[a.get_x()][i] != null) {
                        ans = false;
                    }
                }
            } else {
                for (int i = a.get_y() - 1; i > b.get_y(); i--) {
                    if (board[a.get_x()][i] != null) {
                        ans = false;
                    }
                }
            }
        } else {
            if (a.get_y() == b.get_y()) {
                if (b.get_x() > a.get_x()) {
                    for (int i = a.get_x() + 1; i < b.get_x(); i++) {
                        if (board[i][a.get_y()] != null) {
                            ans = false;
                        }
                    }
                } else
                    for (int i = a.get_x() - 1; i > b.get_x(); i--) {
                        if (board[i][a.get_y()] != null) {
                            ans = false;
                        }
                    }
            }
        }
        return ans;
    }

    public boolean cornermove(Position a, Position b) {
        if ((b.get_x() == 10 && b.get_y() == 10) || (b.get_x() == 0 && b.get_y() == 0) ||
                (b.get_x() == 0 && b.get_y() == 10) || (b.get_x() == 10 && b.get_y() == 0)) {
            if (isplayer2turn) {
                return false;
            } else {
                if (getPieceAtPosition(a) == king) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private Piece[] neighbors(Piece p) {
        Position a = getpos(p);
        Piece[] neighboring = new Piece[4];
        if (a != null) {
            if (a.get_y() < 10) {
                neighboring[0] = board[a.get_x()][a.get_y() + 1];
            }
            if (a.get_x() < 10) {
                neighboring[1] = board[a.get_x() + 1][a.get_y()];
            }
            if (a.get_y() > 0) {
                neighboring[2] = board[a.get_x()][a.get_y() - 1];
            }
            if (a.get_x() > 0) {
                neighboring[3] = board[a.get_x() - 1][a.get_y()];
            }
        }
        return neighboring;
    }

    @Override
    public boolean move(Position a, Position b) {
        if (!validmove(a, b) || !nobstacle(a, b)) {
            return false;
        }
        if (isplayer2turn && board[a.get_x()][a.get_y()].getOwner() == player2) {
            secondsave(a, b);
        //    getmoves = Math.abs(a.get_x() - b.get_x()) + (a.get_y() - b.get_y());
            move2(a, b);
            inthecorner(a, b);
            kill(a, b);
            isplayer2turn = false;
        } else if (!isplayer2turn && board[a.get_x()][a.get_y()].getOwner() == player1) {
            firstsave(a, b);
        //    getmoves = Math.abs(a.get_x() - b.get_x()) + (a.get_y() - b.get_y());
            move2(a, b);
            inthecorner(a, b);
            kill(a, b);
            isplayer2turn = true;
        }
        return true;
    }

    private void kill(Position a, Position b) {
        if (!(a.equals(b))) {
            Piece p2 = getPieceAtPosition(b);
            Piece[] neighboring = neighbors(p2);
            for (int i = 0; i < neighboring.length; i++) {
                if (neighboring[i] != null && neighboring[i].getOwner() != p2.getOwner() && neighboring[i] != king) {
                    Piece tar = neighboring[i];
                    Piece[] nearby = neighbors(tar);
                    if (tar != king && nearby[1] != null && nearby[3] != null && nearby[1] != king && nearby[3] != king
                            && nearby[1].getOwner() != tar.getOwner() && nearby[3].getOwner() != tar.getOwner()) {
                        Position position = getpos(tar);
                        if (position != null) {
                            pieces.push(getPieceAtPosition(position));
                            strides.push(position);
                            nearby[1].addkills();
                            nearby[3].addkills();
                            board[position.get_x()][position.get_y()] = null;
                        }
                    }
                    if (tar != king && nearby[0] != null && nearby[2] != null && nearby[0] != king && nearby[2] != king
                            && nearby[0].getOwner() != tar.getOwner() && nearby[2].getOwner() != tar.getOwner()) {
                        Position position = getpos(tar);
                        if (position != null) {
                            pieces.push(getPieceAtPosition(position));
                            strides.push(position);
                            nearby[0].addkills();
                            nearby[2].addkills();
                            board[position.get_x()][position.get_y()] = null;
                        }
                    }
                }
            }
        }
    }

    private void inthecorner(Position a, Position b) {
        Piece[] neighboring = neighbors(getPieceAtPosition(b));
        Piece p2 = getPieceAtPosition(b);
        if (p2 != king) {
            strides.push(b);
            if (b.get_x() == 1) {
                if (neighboring[3] != null && neighboring[3].getOwner() != p2.getOwner() && neighboring[3] != king) {
                    p2.addkills();
                    board[b.get_x() - 1][b.get_y()] = null;
                }
            }
            if (b.get_x() == 9) {
                if (neighboring[1] != null && neighboring[1].getOwner() != p2.getOwner() && neighboring[1] != king) {
                    p2.addkills();
                    board[b.get_x() + 1][b.get_y()] = null;
                }
            }
            if (b.get_y() == 9) {
                if (neighboring[0] != null && neighboring[0].getOwner() != p2.getOwner() && neighboring[0] != king) {
                    p2.addkills();
                    board[b.get_x()][b.get_y() + 1] = null;
                }
            }
            if (b.get_y() == 1) {
                if (neighboring[2] != null && neighboring[2].getOwner() != p2.getOwner() && neighboring[2] != king) {
                    p2.addkills();
                    board[b.get_x()][b.get_y() - 1] = null;
                }
            }
        }
    }

    private boolean kingdead() {
        Piece[] neighboring = neighbors(king);
        for (int i = 0; i < neighboring.length; i++) {
            if (neighboring[i] == null || neighboring[i].getOwner() == player1) {
                break;
            }
            if (neighboring[i].getOwner() == player2) {
                if (i == 3) {
                    printgamestats1();
                    System.out.println("*******************************************************************");
                    printgamestats2();
                    reset();
                    player2.addWins();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean kingdead2() {
        Piece[] neighboring = neighbors(king);
        Position p = getpos(king);
        if (p != null) {
            int player2counter = 0;
            if (p.get_x() == 10 || p.get_y() == 10 || p.get_x() == 0 || p.get_y() == 0) {
                for (int i = 0; i < neighboring.length; i++) {
                    if (neighboring[i] != null && neighboring[i].getOwner() == player2) {
                        player2counter++;
                        if (player2counter == 3) {
                            printgamestats1();
                            System.out.println("*******************************************************************");
                            printgamestats2();
                            reset();
                            player2.addWins();
                            return true;
                        }
                    }
                }
            }
        } else {
            player2.addWins();
            printgamestats1();
            System.out.println("*******************************************************************");
            printgamestats2();
            reset();
            return true;
        }
        return false;
    }

    private boolean kingincorner() {
        Position p = getpos(king);
        if (p != null) {
            if ((p.get_x() == 10 && p.get_y() == 10) || (p.get_x() == 0 && p.get_y() == 0) ||
                    (p.get_x() == 0 && p.get_y() == 10) || (p.get_x() == 10 && p.get_y() == 0)) {
                printgamestats1();
                System.out.println("*******************************************************************");
                printgamestats2();
                reset();
                player1.addWins();
                return true;
            }
        } else {
            printgamestats1();
            System.out.println("*******************************************************************");
            printgamestats2();
            reset();
            player2.addWins();
            return true;
        }
        return false;
    }

    private Position getpos(Piece a) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].equals(a)) {
                        return new Position(i, j);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return board[position.get_x()][position.get_y()];
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public boolean isGameFinished() {
        return (kingdead() || kingdead2() || kingincorner());
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return isplayer2turn;
    }

    @Override
    public void reset() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                board[i][j] = null;
            }
        }
        newgame();
        isplayer2turn = true;
    }

    @Override
    public void undoLastMove() {
        if (!strides.isEmpty() && !pieces.isEmpty()) {
            Position lastPosition = strides.pop();
            Piece lastPiece = pieces.pop();
            board[lastPosition.get_x()][lastPosition.get_y()] = lastPiece;
            board[lastPosition.get_x()][lastPosition.get_y()] = null;
            isplayer2turn = !isplayer2turn;
        }
    }

    @Override
    public int getBoardSize() {
        return 11;
    }

    private void newboard() {
        Piece[] bluepieces = new Piece[14];
        Piece[] redpieces = new Piece[24];

        bluepieces[0] = new King(5, 5, player1, 7);
        board[5][5] = king;

        bluepieces[1] = new Pawn(5, 4, player1, 3, 0);
        board[5][4] = bluepieces[1];
        bluepieces[2] = new Pawn(5, 3, player1, 1, 0);
        board[5][3] = bluepieces[2];
        bluepieces[3] = new Pawn(4, 4, player1, 2, 0);
        board[4][4] = bluepieces[3];
        bluepieces[4] = new Pawn(6, 4, player1, 4, 0);
        board[6][4] = bluepieces[4];
        bluepieces[5] = new Pawn(6, 5, player1, 1, 0);
        board[6][5] = bluepieces[5];
        bluepieces[6] = new Pawn(7, 5, player1, 1, 0);
        board[7][5] = bluepieces[6];
        bluepieces[7] = new Pawn(4, 5, player1, 1, 0);
        board[4][5] = bluepieces[7];
        bluepieces[8] = new Pawn(3, 5, player1, 1, 0);
        board[3][5] = bluepieces[8];
        bluepieces[9] = new Pawn(6, 6, player1, 2, 0);
        board[6][6] = bluepieces[9];
        bluepieces[10] = new Pawn(4, 6, player1, 4, 0);
        board[4][6] = bluepieces[10];
        bluepieces[11] = new Pawn(5, 6, player1, 1, 0);
        board[5][6] = bluepieces[11];
        bluepieces[12] = new Pawn(7, 5, player1, 1, 0);
        board[7][5] = bluepieces[12];
        bluepieces[13] = new Pawn(5, 7, player1, 13, 0);
        board[5][7] = bluepieces[13];

        //representing the red pieces with an identification number
        redpieces[0] = new Pawn(3, 0, player2, 1, 0);
        board[3][0] = redpieces[0];
        redpieces[1] = new Pawn(4, 0, player2, 2, 0);
        board[4][0] = redpieces[1];
        redpieces[2] = new Pawn(5, 0, player2, 3, 0);
        board[5][0] = redpieces[2];
        redpieces[3] = new Pawn(6, 0, player2, 4, 0);
        board[6][0] = redpieces[3];
        redpieces[4] = new Pawn(7, 0, player2, 5, 0);
        board[7][0] = redpieces[4];
        redpieces[5] = new Pawn(5, 1, player2, 6, 0);
        board[5][1] = redpieces[5];

        redpieces[6] = new Pawn(0, 3, player2, 7, 0);
        board[0][3] = redpieces[6];
        redpieces[7] = new Pawn(0, 4, player2, 9, 0);
        board[0][4] = redpieces[7];
        redpieces[8] = new Pawn(0, 5, player2, 11, 0);
        board[0][5] = redpieces[8];
        redpieces[9] = new Pawn(0, 6, player2, 15, 0);
        board[0][6] = redpieces[9];
        redpieces[10] = new Pawn(0, 7, player2, 17, 0);
        board[0][7] = redpieces[10];
        redpieces[11] = new Pawn(1, 5, player2, 12, 0);
        board[1][5] = redpieces[11];

        redpieces[18] = new Pawn(10, 3, player2, 8, 0);
        board[10][3] = redpieces[18];
        redpieces[19] = new Pawn(10, 4, player2, 10, 0);
        board[10][4] = redpieces[19];
        redpieces[20] = new Pawn(10, 5, player2, 14, 0);
        board[10][5] = redpieces[20];
        redpieces[21] = new Pawn(10, 6, player2, 16, 0);
        board[10][6] = redpieces[21];
        redpieces[22] = new Pawn(10, 7, player2, 18, 0);
        board[10][7] = redpieces[22];
        redpieces[23] = new Pawn(9, 5, player2, 13, 0);
        board[9][5] = redpieces[23];

        redpieces[12] = new Pawn(3, 10, player2, 20, 0);
        board[3][10] = redpieces[12];
        redpieces[13] = new Pawn(4, 10, player2, 21, 0);
        board[4][10] = redpieces[13];
        redpieces[14] = new Pawn(5, 10, player2, 22, 0);
        board[5][10] = redpieces[14];
        redpieces[15] = new Pawn(6, 10, player2, 23, 0);
        board[6][10] = redpieces[15];
        redpieces[16] = new Pawn(7, 10, player2, 24, 0);
        board[7][10] = redpieces[16];
        redpieces[17] = new Pawn(5, 9, player2, 19, 0);
        board[5][9] = redpieces[17];
    }

    static class compare implements Comparator<Map.Entry<String, ArrayList<Position>>> {
        @Override
        public int compare(Map.Entry<String, ArrayList<Position>> a, Map.Entry<String, ArrayList<Position>> b) {
            return Integer.compare(a.getValue().size(), b.getValue().size());
        }
    }

    static class comparekills implements Comparator<Map.Entry<String, Piece>> {
        @Override
        public int compare(Map.Entry<String, Piece> o1, Map.Entry<String, Piece> o2) {
            int result = Integer.compare(o2.getValue().getKills(), o1.getValue().getKills());
            if (result != 0) {
                return result;
            }
            else {
                return new stringcomparator().compare(o1.getKey(),o2.getKey());
            }
        }
    }

    static class stringcomparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            StringBuilder a = new StringBuilder();
            StringBuilder b = new StringBuilder();
            for (char c : ((String) o1).toCharArray()) {
                if (Character.isDigit(c)) {
                    a.append(c);
                }
            }
            for (char c : ((String) o2).toCharArray()) {
                if (Character.isDigit(c)) {
                    b.append(c);
                }
            }
            int x = Integer.parseInt(a.toString());
            int y = Integer.parseInt(b.toString());
            return Integer.compare(x, y);
        }
    }

//    static class movingcomparator implements Comparator<Piece>{
//
//        @Override
//        public int compare(Piece o1, Piece o2) {
//            return Integer.compare()
//        }
//
//    }

}

