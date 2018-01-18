package autres;

public class Carte {

    private String nom;
    private String actionRobot;
    private int nbParametre;
    private String nomParametres[];
    private String parametres[];

    public Carte(String pNom, String pActionRobot, int pNbParametre, String[] pNomParametres){
        nom = pNom;
        actionRobot = pActionRobot;
        nbParametre = pNbParametre;
        parametres = new String[nbParametre];
        nomParametres = pNomParametres;
        for(int i = 0; i < nbParametre; i++) // init
            parametres[i] = "00";
    }

    public String getNom(){
        return nom;
    }

    public String getActionRobot(){
        return actionRobot;
    }

    public int getNbParametre(){
        return nbParametre;
    }

    public String[] getParametres(){
        return parametres;
    }

    public String getParametre(int i){
        return parametres[i];
    }

    public String getNomParametres(int i){ return nomParametres[i];}

    public void setParametre(int num, String parametre){
        if(parametre.length() == 1) parametres[num] = "0"+parametre;
        else parametres[num] = parametre;
    }
}
