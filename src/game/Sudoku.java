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
    private static char[][] matrix = new char[9][9];
    private static char[][] ethalon = new char[9][9];
    private static final String txtFilePath = "Sudoku.txt";
    private static final String csvFilePath = "Sudoku.csv";

    private static void generateString(int x, int begin){
        char[] mas = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < 9; i++) {
            matrix[x][i] = mas[begin];
            begin++;
        }
    }

    private static void generateBaseMatrix(){
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

    private static void printMatrix(){
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

    private static void writeMatrixToFile(String filePath) throws IOException {
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

    private static void writeMatrixToFileCsv(String filePath) throws IOException {
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

    private static char[][] readTxt(String filepath){
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

    private static char[][] readCsv(String filepath){
        FileIO file = new FileIO();
        char[][] solution = new char[9][9];
        List<String> input = file.readFile(filepath);
        for (int i = 0; i < 9; i++) {
            solution[i] = input.get(i).replace(";","").toCharArray();
        }
        return solution;
    }


    private static void matrixTranspon(){
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

    private static void swapStrings(){
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

    private static void swapCols(){
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

    private static void swapStringsArea(){
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

    private static void swapColsArea(){
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

    private static void swaper(int var){
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

    private static void saveEthalon(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ethalon[i][j] = matrix[i][j];
            }
        }
    }

    private static void hide(int difficulty){
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

    private static boolean validateSolution(char[][] solution){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (solution[i][j] != ethalon[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        generateBaseMatrix();
        Random rn = new Random();
        for (int i = 0; i < 10; i++) {
            int n = rn.nextInt(5);
            swaper(n);
        }
        saveEthalon();
        //printMatrix();
        System.out.println("Выберите сложность:");
        System.out.println("1 - легкий");
        System.out.println("2 - средний");
        System.out.println("3 - сложный");
        System.out.println("0 - показать полностью");
        Scanner sc = new Scanner(System.in);
        int choise = sc.nextInt();
        switch (choise){
            case 0:
                break;
            case 1:
                hide(4);
                break;
            case 2:
                hide(5);
                break;
            case 3:
                hide(6);
                break;
            default:
                System.out.println("Неверный выбор!");
        }
        printMatrix();
        writeMatrixToFile(txtFilePath);
        writeMatrixToFileCsv(csvFilePath);
        System.out.println("Проверить правильность решения? (y/n): ");
        String answer = sc.next();
        if (answer.toLowerCase().equals("y")){
            System.out.println("1. CSV");
            System.out.println("2. TXT");
            System.out.println("Какой файл проверить? ");
            choise = sc.nextInt();
            boolean correct = false;
            switch (choise){
                case 1:
                    correct = validateSolution(readCsv(csvFilePath));
                    break;
                case 2:
                    correct = validateSolution(readTxt(txtFilePath));
                    break;
            }
            if (correct){
                System.out.println("РЕШЕНИЕ ВЕРНО!");
            }
            else{
                System.out.println("РЕШЕНИЕ НЕВЕРНО!");
            }
        }
    }
}
