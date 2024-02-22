package vn.loto.rest01.metier;

public class Type {
    private Integer id;
    private String libelle;

    public Type()
    {
        id=0;
        libelle = "";
    }
    public Type(Integer id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
    }

    public Type(String libelle)
    {
        this.libelle = libelle;
    }



    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
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




    @Override
    public String toString()
    {
        return libelle;
    }
}
