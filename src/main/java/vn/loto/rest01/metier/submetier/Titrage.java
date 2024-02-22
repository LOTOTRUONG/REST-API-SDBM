package vn.loto.rest01.metier.submetier;

public class Titrage {

    private float titrage;

    public Titrage(Float titrage){
        this.titrage = titrage;
    }

    public float getTitrage() {
        return titrage;
    }

    public void setTitrage(float titrage) {
        this.titrage = titrage;
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Titrage titrage1 = (Titrage) object;

        return Float.compare(titrage, titrage1.titrage) == 0;
    }

    @Override
    public int hashCode() {
        return (titrage != 0.0f ? Float.floatToIntBits(titrage) : 0);
    }

}
