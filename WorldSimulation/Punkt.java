package pl.edu.pg.eti.ksg.po.project2;

public class Punkt {
    private int x;
    private int y;

    public Punkt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Punkt() {
        x = 0;
        y = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Punkt)) {
            return false;
        }

        Punkt objPunkt = (Punkt) obj;
        return Integer.compare(x, objPunkt.x) == 0
                && Integer.compare(y, objPunkt.y) == 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
};
