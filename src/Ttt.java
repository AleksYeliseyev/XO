import java.util.Scanner;

public class Ttt {
    public static void main(String[] args) {
        String[] coordinates;
        String states;
        char line;
        char figure = 'X';
        char[] field = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};  //Создание массива игрового поля
        printField(field);              //Вызов метода вывода игрового поля в консоль
        //Игровой цикл * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        while (true) {
            //Ход игроков
            while (true) {
                System.out.println("Enter the coordinates: ");
                coordinates = playerMove();
                if (isBusy(field, coordinates)) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    insert(field, coordinates, figure);
                    if (figure == 'X'){
                        figure = 'O';
                    } else {
                        figure = 'X';
                    }
                    printField(field);
                    break;
                }
            }
            line = isLine(field);            //Вызов метода проверки на выигрыш
            if (line == 'D') {
                if (isEmpty(field)) {        //Вызов метода проверки на наличие пустых полей
                    continue;
                } else {
                    states = "Draw";
                    break;
                }
            } else if (line == 'X') {
                states = "X wins";
                break;
            } else if (line == 'O') {
                states = "O wins";
                break;
            }
        }
       //Конец игрового цикла * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        System.out.println(states);     //Вывод результата проверок в консоль **************************
    }

    //Метод вывода игрового поля в консоль
    private static void printField(char[] field) {
        int count = 0;
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(field[count] + " ");
                count++;
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    //Метод ввода координат игроком (вводятся два числа от 1 жо 3 включительно) ввод через пробел или ентер
    private static String[] playerMove() {
        String[] input = new String[2];
        Scanner scan = new Scanner(System.in);
        int i = 0;
        while (i < 2) {
            input[i] = scan.next();
            if (input[i].equals("1") || input[i].equals("2") || input[i].equals("3")) {
                i++;
            } else {
                i = 0;
                scan = new Scanner(System.in);
                if (input[i].matches("[4-9]")) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    System.out.println("You should enter numbers!");
                }
            }
        }
        return input;
    }

    //Метод проверки на занятое поле по координатам
    private static boolean isBusy(char[] field, String[] coordinates) {
        char[][] newField = new char[4][4];
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                newField[i + 1][3 - j] = field[count];
                count++;
            }
        }
        return newField[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] == 'X' ||
                newField[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] == 'O';
    }

    //Метод установки на поле по координатам Х
    private static char[] insert(char[] field, String[] coord, char figure) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (coord[0].equals(Integer.toString(i + 1)) && coord[1].equals(Integer.toString(3 - j))) {
                    field[count] = figure;
                }
                count++;
            }
        }
        return field;
    }

    //Метод проверки на пустые поля
    private static boolean isEmpty(char[] field) {
        boolean res = false;
        for (int i = 0; i < 9; i++) {
            if (field[i] == ' ') {
                res = true;
                break;
            }
        }
        return res;
    }

    //Метод проверки на выигрыш
    private static char isLine(char[] field) {
        char result = 'D';
        int flag = 0;
        //Проверка в столбцах
        for (int i = 0; i < 3; i++) {
            if (field[i] == 'X' && field[i + 3] == 'X' && field[i + 6] == 'X') {
                result = 'X';
                flag++;
            }
            if (field[i] == 'O' && field[i + 3] == 'O' && field[i + 6] == 'O') {
                result = 'O';
                flag++;
            }
        }
        //Проверка в строках
        for (int i = 0; i < 9; i = i + 3) {
            if (field[i] == 'X' && field[i + 1] == 'X' && field[i + 2] == 'X') {
                result = 'X';
                flag++;
            }
            if (field[i] == 'O' && field[i + 1] == 'O' && field[i + 2] == 'O') {
                result = 'O';
                flag++;
            }
        }
        //Проверка в диагоналях
        if (field[0] == 'X' && field[4] == 'X' && field[8] == 'X') {
            result = 'X';
            flag++;
        }
        if (field[0] == 'O' && field[4] == 'O' && field[8] == 'O') {
            result = 'O';
            flag++;
        }
        if (field[6] == 'X' && field[4] == 'X' && field[2] == 'X') {
            result = 'X';
            flag++;
        }
        if (field[6] == 'O' && field[4] == 'O' && field[2] == 'O') {
            result = 'O';
            flag++;
        }
        //Определение результата проверок
        if (flag == 0) {
            return result;      //нет выигрышных позиций: result == 'D' значение по умолчанию
        } else if (flag == 1) {
            return result;      //одна выигрышная позиция: result == 'X' or 'O'
        }
        return result;
    }
}