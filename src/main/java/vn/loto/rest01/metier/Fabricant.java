package vn.loto.rest01.metier;

import io.swagger.v3.oas.annotations.media.Schema;

public class Fabricant {
    private int id;
    private String nomFabricant;
   public Fabricant() {
       id = 0;
       nomFabricant = "";
   }
  public Fabricant(Integer id, String nomFabricant) {
       this.id = id;
       this.nomFabricant = nomFabricant;
  }
  public Integer getId() {
       return id;
  }
  public void setId(Integer id) {
       this.id = id;
    }
   public String getNomFabricant(){
       return nomFabricant;
   }
   public void setNomFabricant(String nomFabricant) {
       this.nomFabricant = nomFabricant;
   }

   @Override
    public String toString(){
       return nomFabricant;
   }
}
