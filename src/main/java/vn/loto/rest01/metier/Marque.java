package vn.loto.rest01.metier;

public class Marque {
    private Integer id;
    private String libelle;
    private Pays pays;
    private Fabricant fabricant;
    public Marque()
    {
        id=0;
        libelle = "";
        fabricant = new Fabricant();
        pays = new Pays();
    }

    public Marque(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
        this.pays = new Pays();
        this.fabricant = new Fabricant();
    }
    public Marque(Integer id, String libelle, Pays pays, Fabricant fabricant) {
        this.id = id;
        this.libelle = libelle;
        this.pays = pays;
        this.fabricant = fabricant;
    }
    public Marque(Integer id, String libelle, Pays pays) {
        this.id = id;
        this.libelle = libelle;
        this.pays = pays;
    }
    public Marque(Integer id, String libelle, Fabricant fabricant) {
        this.id = id;
        this.libelle = libelle;
        this.fabricant = fabricant;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public Pays getPays(){
        return pays;
    }
    public void setPays(Pays pays){
        this.pays = pays;
    }
    public Fabricant getFabricant(){
        return fabricant;
    }
    public void setFabricant(Fabricant fabricant) {
        this.fabricant = fabricant;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
