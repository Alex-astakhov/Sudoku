package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Alex Astakhov on 23.06.2016.
 */
public class Sudoku {
    private char[][] matrix = new char[9][9];
    private char[][] ethalon = new char[9][9];


    private void generateString(int x, int begin){
        char[] mas = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < 9; i++) {
            matrix[x][i] = mas[begin];
            begin++;
        }
    }

    private void generateBaseMatrix(){
        generateString(0, 0);
        generateString(1, 3);
        generateString(2, 6);
        generateString(3, 1);
        generateString(4, 4);
        generateString(5, 7);
        generateString(6, 2);
        generateString(7, 5);
        generateString(8, 8);
    }

    void printMatrix(){
        System.out.println("-------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0 || j == 3 || j == 6)
                    System.out.print("¦");
                if (j == 2 || j == 5 || j == 8)
                    System.out.print(matrix[i][j]);
                else
                    System.out.print(matrix[i][j] + " ");
            }
            System.out.println("¦");
            if (i == 2 || i == 5 || i == 8)
                System.out.println("-------------------");
        }
    }

    void writeMatrixToFile(String filePath) throws IOException {
        List<String> toFile = new ArrayList<>();

        toFile.add("-------------------");
        toFile.add("");

        for (int i = 0; i < 9; i++) {
            int lastIndex = toFile.size() - 1;
            for (int j = 0; j < 9; j++) {
                if (j == 0 || j == 3 || j == 6)
                    toFile.set(lastIndex, toFile.get(lastIndex) + "¦");
                if (j == 2 || j == 5 || j == 8)
                    toFile.set(lastIndex, toFile.get(lastIndex) + String.valueOf(matrix[i][j]));
                else
                    toFile.set(lastIndex, toFile.get(lastIndex) + matrix[i][j] + " ");
            }
            toFile.set(lastIndex, toFile.get(lastIndex) + "¦");
            toFile.add("");
            lastIndex++;
            if (i == 2 || i == 5 || i == 8) {

                toFile.set(lastIndex, toFile.get(lastIndex) + "-------------------");
                toFile.add("");
            }
        }
        FileIO file = new FileIO();
        file.writeToFileLn(filePath, toFile);
    }

    void writeMatrixToFileCsv(String filePath) throws IOException {
        List<String> toFile = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            String temp = "";
            for (int j = 0; j < 9; j++) {
                temp += matrix[i][j] + ";";
            }
            toFile.add(temp);
        }
        FileIO file = new FileIO();
        file.writeToFileLn(filePath, toFile);
    }

    char[][] readTxt(String filepath){
        FileIO file = new FileIO();
        char[][] solution = new char[9][9];
        List<String> input = file.readFile(filepath);
        for (int i = 1; i < 4; i++) {
            input.get(i).replace("¦","").replace(" ","").toCharArray();
            solution[i-1] = input.get(i).replace("¦","").replace(" ","").toCharArray();
        }
        for (int i = 5; i < 8; i++) {
            input.get(i).replace("¦","").replace(" ","").toCharArray();
            solution[i-2] = input.get(i).replace("¦","").replace(" ","").toCharArray();
        }
        for (int i = 9; i < 12; i++) {
            input.get(i).replace("¦","").replace(" ","").toCharArray();
            solution[i-3] = input.get(i).replace("¦","").replace(" ","").toCharArray();
        }
        return solution;
    }

    char[][] readCsv(String filepath){
        FileIO file = new FileIO();
        char[][] solution = new char[9][9];
        List<String> input = file.readFile(filepath);
        for (int i = 0; i < 9; i++) {
            solution[i] = input.get(i).replace(";","").toCharArray();
        }
        return solution;
    }


    private void matrixTranspon(){
        char[][] buffer = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buffer[j][i] = matrix[i][j];
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = buffer[i][j];
            }
        }
    }

    private void swapStrings(){
        Random rn = new Random();
        int x;
        do {
           x = rn.nextInt(8);
        }while (x == 2 || x == 5);

        char swap;

        for (int i = 0; i < 9; i++) {
            swap = matrix[x][i];
            matrix[x][i] = matrix[x+1][i];
            matrix[x+1][i] = swap;
        }
    }

    private void swapCols(){
        Random rn = new Random();
        int y;
        do {
            y = rn.nextInt(8);
        }while (y == 2 || y == 5);
        char swap;

        for (int i = 0; i < 9; i++) {
            swap = matrix[i][y];
            matrix[i][y] = matrix[i][y+1];
            matrix[i][y+1] = swap;
        }
    }

    private void swapStringsArea(){
        Random rn = new Random();
        int x = rn.nextInt(2) * 3;
        char swap;
        for (int j = x; j < (x + 3); j++) {
            for (int i = 0; i < 9; i++) {
                swap = matrix[j][i];
                matrix[j][i] = matrix[j+3][i];
                matrix[j+3][i] = swap;
            }
        }
    }

    private void swapColsArea(){
        Random rn = new Random();
        int x = rn.nextInt(2) * 3;
        char swap;
        for (int j = x; j < (x + 3); j++) {
            for (int i = 0; i < 9; i++) {
                swap = matrix[i][j];
                matrix[i][j] = matrix[i][j+3];
                matrix[i][j+3] = swap;
            }
        }
    }

    private void swaper(int var){
        switch (var){
            case 0:
                matrixTranspon();
                break;
            case 1:
                swapStrings();
                break;
            case 2:
                swapCols();
                break;
            case 3:
                swapStringsArea();
                break;
            case 4:
                swapColsArea();
                break;
        }
    }

    void generateSudoku(int difficulty){
        generateBaseMatrix();
        Random rn = new Random();
        for (int i = 0; i < 10; i++) {
            int n = rn.nextInt(5);
            swaper(n);
        }
        saveEthalon();
        hide(difficulty);
    }

    private void saveEthalon(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ethalon[i][j] = matrix[i][j];
            }
        }
    }

    private void hide(int difficulty){
        Random rn = new Random();
        for (int i = 0; i < 8; i+=3) {
            for (int j = 0; j < 8; j+=3) {
                for (int k = 0; k < difficulty; k++) {
                    int x = rn.nextInt(3) + i;
                    int y = rn.nextInt(3) + j;
                    matrix[x][y] = ' ';
               }
            }
        }
    }

    boolean validateSolution(char[][] solution){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (solution[i][j] != ethalon[i][j]){
                    return false;
                }
            }
        }
        return true;
    }


}
