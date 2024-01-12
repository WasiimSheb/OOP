public class Position {
    public int _x;
    public int _y;
    public Position(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_x(int _x) {
        this._x = _y;
    }

    public void set_y(int _y) {
        this._y = _x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return _x == position._x && _y == position._y;
    }

}
