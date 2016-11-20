package game;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Alex Astakhov on 20.11.2016.
 */
public class PlaySudoku {
    private static final String txtFilePath = "Sudoku.txt";
    private static final String csvFilePath = "Sudoku.csv";

    public static void main(String[] args) throws IOException {
        Sudoku sudoku = new Sudoku();
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
                sudoku.generateSudoku(4);
                break;
            case 2:
                sudoku.generateSudoku(5);
                break;
            case 3:
                sudoku.generateSudoku(6);
                break;
            default:
                System.out.println("Неверный выбор!");
        }
        sudoku.printMatrix();
        sudoku.writeMatrixToFile(txtFilePath);
        sudoku.writeMatrixToFileCsv(csvFilePath);
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
                    correct = sudoku.validateSolution(sudoku.readCsv(csvFilePath));
                    break;
                case 2:
                    correct = sudoku.validateSolution(sudoku.readTxt(txtFilePath));
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
