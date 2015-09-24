package Pong;

public class Couleur {

    /**
     * Valeur du rouge
     */
    private int red;

    /**
     * Valeur du vert
     */
    private int green;

    /**
     * Valeur du bleu
     */
    private int blue;

    /**
     * Constructeur par défaut
     */
    public Couleur() {
        this(0, 0, 0);
    }

    /**
     * Construit une nouvelle couleur à partir de 3 valeurs rouge/vert/bleu.
     *
     * @param red valeur du rouge
     * @param green valeur du vert
     * @param blue valeur du bleu
     */
    public Couleur(int red, int green, int blue) {
        this.setRGB(red, green, blue);
    }

    public void setRGB(int red, int green, int blue) {
        if (red >= 0 && red <= 255) {
            this.red = red;
        }
        if (green >= 0 && green <= 255) {
            this.green = green;
        }
        if (blue >= 0 && blue <= 255) {
            this.blue = blue;
        }
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

}
