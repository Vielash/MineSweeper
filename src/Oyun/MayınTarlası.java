package Oyun;

import javax.management.StringValueExp;
import java.util.Random;
import java.util.Scanner;

public class MayınTarlası {

    //olmasi gereken fonksiyonlar playMove(), checkWin(), checkHidden(), fixVisible(), startGame(), setMines(), putFlag(), removeFlag(), showGame(),

    Random randomMines = new Random();
    String[][] hiddenMatris = new String[10][10];
    String[][] visibleMatris = new String[11][11];

    int [] xAxisMine = new int[10];
    int [] yAxisMine = new int[10];
    boolean flag = true;


    public void startGame() {
        System.out.println("**************************MINESWAPPER*****************************");
        setMines(1);
        while(flag) {
            showGame();
            playMove();
            if (checkWin()) {
                System.out.println("************************* YOU LOSE ***************************");

                break;
            }

        }
    }

    public void  setMines( int bayrak) {

        Random randomMines = new Random();

        if (bayrak == 1) {

            for (int visibleColumn = 0; visibleColumn < 10; visibleColumn++) {  // yan tarafın 123456
                visibleMatris[0][visibleColumn] = visibleColumn +  " ";
            }

            for (int visibleLine = 1; visibleLine < 11; visibleLine++) {
                for (int visibleColumn = 0; visibleColumn < 11; visibleColumn++) {  // visiblenin - leri
                    visibleMatris[visibleLine][visibleColumn] = "- ";
                }
            }

            for (int visibleLine = 0; visibleLine < 10; visibleLine++) {   // üst tarafın 123456
                visibleMatris[visibleLine][0] = visibleLine + " ";
            }

            for (int hiddenLine = 0; hiddenLine < 10; hiddenLine++) {
                for (int hiddenColumn = 0; hiddenColumn < 10; hiddenColumn++) {
                    hiddenMatris[hiddenLine][hiddenColumn] = "  ";
                }
            }

            for (int i = 0; i < 10; i++) {
                int xAxis = randomMines.nextInt(1, 9);
                int yAxis = randomMines.nextInt(1, 9);
                if (hiddenMatris[xAxis][yAxis] == "* ") {
                    i--;
                }
                hiddenMatris[xAxis][yAxis] = "* ";
            }
            hiddenMatrisNumbers();

            for (int i = 0; i < 10 ; i++) {
                for (int j = 0; j < 10 ; j++) {
                    System.out.printf(hiddenMatris[i][j]);
                }
                System.out.println("");
            }
            return;
        }
        else
        {
            return;
        }
    }

    public void showGame() {
        for(int visibleLine = 0; visibleLine < 10; visibleLine++) {
            for(int visibleColumn = 0; visibleColumn < 10; visibleColumn++) {
                System.out.print(visibleMatris[visibleLine][visibleColumn]);
            }
            System.out.println("");
        }
        return;
    }

    public void playMove() {
        Scanner input = new Scanner(System.in);

        boolean selectionFLag = false;
        int option = 0;
        while (selectionFLag == false) {
            System.out.println(" 1. Put Flag(?) \n 2. Select Grid \n 3.Remove Flag ");
            option = input.nextInt();
            if (option == 1 || option == 2 || option == 3) {
                selectionFLag = true;
            }
        }

        boolean inputFlag = false;
        int xAxisInput = 0, yAxisInput = 0;
        while (inputFlag == false) {
            System.out.print("x Coordinate: ");
            yAxisInput = input.nextInt();
            System.out.print("y Coordinate: ");
            xAxisInput = input.nextInt();

            if (xAxisInput < 10 && yAxisInput < 10) {
                inputFlag = true;
            }
        }

        switch (option) {
            case 1:
                visibleMatris[xAxisInput][yAxisInput] = "? ";
                break;
            case 2:
                fixVisible(xAxisInput,yAxisInput);
                break;
            case 3:
                if(visibleMatris[xAxisInput][yAxisInput] != "? ") {
                    System.out.println("There is no flag so you can't remove anything");
                }
                else
                {
                    visibleMatris[xAxisInput][yAxisInput] = "- ";
                }
                break;
        }

    }

    public void hiddenMatrisNumbers() {
        int i = 0;
        for(int hiddenLine = 1; hiddenLine < 10; hiddenLine++) {
            for(int hiddenColumn = 1; hiddenColumn < 10; hiddenColumn++) {
                if(hiddenMatris[hiddenLine][hiddenColumn] == "* ") {
                    xAxisMine[i] = hiddenColumn;
                    yAxisMine[i] = hiddenLine;
                    i++;
                }
            }
        }

        for(int hiddenLine = 1; hiddenLine < 10; hiddenLine++) {
            for(int hiddenColumn = 1; hiddenColumn < 10; hiddenColumn++) {
                int numberCount = 0;
                for(int j = 0; j < 10; j++) {
                    if("* ".equals(hiddenMatris[hiddenLine][hiddenColumn])) {
                        continue;
                    }
                    if(((hiddenColumn - xAxisMine[j] < 2) && (hiddenColumn - xAxisMine[j] > -2)) && ((hiddenLine - yAxisMine[j] < 2) && (hiddenLine - yAxisMine[j] > -2))) {
                         numberCount++;
                         String s = String.valueOf(numberCount);
                         hiddenMatris[hiddenLine][hiddenColumn] = s + " ";

                    }
                }
            }
        }
    }

    public boolean checkWin() {
        int mineCount = 0;
        boolean loseFlag = false;
        for (int i = 0; i < 10 ; i++) {
            if("* ".equals(hiddenMatris[yAxisMine[i]][xAxisMine[i]])) {
                mineCount++;
            }
        }
        if(mineCount != 10) {
            loseFlag = true;
        }
        return loseFlag;
    }

    public int checkHidden(int xAxis, int yAxis) {  //bosluksa 1 dondurur, sayiysa 2 dondurur, bomba varsa 3 dondurur.
        if ("  ".equals(hiddenMatris[xAxis][yAxis])) {
            return 1;
        }
        else if ("1 ".equals(hiddenMatris[xAxis][yAxis]) || "2 ".equals(hiddenMatris[xAxis][yAxis]) || "3 ".equals(hiddenMatris[xAxis][yAxis]) || "4 ".equals(hiddenMatris[xAxis][yAxis])) {
            return 2;
        }
        else if ("* ".equals(hiddenMatris[xAxis][yAxis])) {
            return 3;
        }
        return xAxis;
    }

    public void fixVisible(int xAxis, int yAxis) {
        if(checkHidden(xAxis, yAxis) == 1) {
            fillBlankArea(xAxis, yAxis);
        }
        if(checkHidden(xAxis, yAxis) == 2) {
            visibleMatris[xAxis][yAxis] = hiddenMatris[xAxis][yAxis];
        }
        if (checkHidden(xAxis, yAxis) == 3) {
            hiddenMatris[xAxis][yAxis] = "  ";
        }

    }

    public void fillBlankArea(int xAxis, int yAxis) {
        if("  ".equals(visibleMatris[xAxis][yAxis])) {
            return;
        }
      if(xAxis > 9 || (yAxis > 9) || (xAxis < 1) || (yAxis < 1)) {
            return;
        }
        if(checkHidden(xAxis, yAxis) == 2) {
            visibleMatris[xAxis][yAxis] = hiddenMatris[xAxis][yAxis];
            return;
        }
        if (checkHidden(xAxis, yAxis) == 3) {
            return;
        }
        if(checkHidden(xAxis, yAxis) == 1) {
                visibleMatris[xAxis][yAxis] = "  ";
                fillBlankArea(xAxis + 1, yAxis);
               fillBlankArea(xAxis, yAxis + 1);
                fillBlankArea(xAxis - 1, yAxis);
                fillBlankArea(xAxis, yAxis - 1);
                return;
        }

    }
}







