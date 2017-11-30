package autres;

public class Carte {

    private String nom;
    private String actionRobot;

    public Carte(String pNom, String pActionRobot){
        nom = pNom;
        actionRobot = pActionRobot;
    }

    public String getNom(){
        return nom;
    }

    public String getActionRobot(){
        return actionRobot;
    }
}
