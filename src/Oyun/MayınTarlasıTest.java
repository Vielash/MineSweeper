package Oyun;



public class MayınTarlasıTest {

   private int [][] hiddenMatris = new int [16][16];
    private int [][] visibleMatris = new int [16][16];

    public static void main(String[] args) {

        MayınTarlası m = new MayınTarlası();

        m.setMines(1);
        m.showGame();







    }

}
