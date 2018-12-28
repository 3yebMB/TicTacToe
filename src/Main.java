import java.util.Random;
import java.util.Scanner;

public class Main {
    private static char[][] map;
    private static int[][] pointMap;
    private static int maxPoint = 0;
    private static int SIZE = 3;

    private  static final char DOT_EMPTY = '*';
    private  static final char DOT_X = 'X';
    private  static final char DOT_O = 'O';

    private static final boolean SMART_MODE = true;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap(map);

        while (true){
            humanGame();
            if (TheGameIsEnd(DOT_X)){
                break;
            }

            compGame();
            if (TheGameIsEnd(DOT_O)){
                break;
            }
        }
        System.out.println("Игра закончена");
    }

    private static void initMap(){
        map = new char[SIZE][SIZE];
        pointMap = new  int[SIZE][SIZE];

        for (int i=0; i<SIZE; i++)
            for (int j=0; j<SIZE; j++) {
                map[i][j] = DOT_EMPTY;
                pointMap[i][j] = 1;
            }
        maxPoint = 1;
    }

    private static void printMap(char[][] arr){
        for (int i=0; i<=SIZE; i++)
            if (i!=0)
                System.out.print(i + " | ");
            else
                System.out.print("  | ");

        System.out.println("\n---------------");

        for (int i=0; i<SIZE; i++){
            System.out.print((i+1)+" | ");
            for (int j=0; j<SIZE; j++) {
                System.out.print(arr[i][j] + " | ");
            }
            System.out.println("\n---------------");
        }
    }
// тестово выводил оценночный массив
    private static void printMap(int[][] arr){
        for (int i=0; i<=SIZE; i++)
            if (i!=0)
                System.out.print(i + " | ");
            else
                System.out.print("  | ");

        System.out.println("\n---------------");

        for (int i=0; i<SIZE; i++){
            System.out.print((i+1)+" | ");
            for (int j=0; j<SIZE; j++) {
                System.out.print(arr[i][j] + " | ");
            }
            System.out.println("\n---------------");
        }
    }

    private static void humanGame(){
        int x, y;

        do {
            System.out.println("Введите координаты ячейки через пробел.");
            y = scanner.nextInt()-1;
            x = scanner.nextInt()-1;
        } while (!isCellValid(x, y));

        map[y][x] = DOT_X;
        recalculateMap();
    }

    private static boolean isCellValid(int x, int y){
        boolean result = true;

        if (x<0 || x>SIZE || y<0 || y>SIZE)
            result = false;

        if (map[y][x] != DOT_EMPTY)
            result = false;

        return result;
    }

    private static boolean TheGameIsEnd(char humanChar){
        boolean result = false;

        printMap(map);

        if (checkWin(humanChar)){
            System.out.println("Победили " + humanChar);
            result = true;
        }

        if (MapIsFull()){
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    private static boolean MapIsFull(){
        boolean result = true;

        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                if (map[i][j] == DOT_EMPTY)
                    result = false;
            }
        }
        return result;
    }

    private static boolean checkWin(char playerChar){
        boolean result = false;

        if (
           (map[0][0] == playerChar && map[0][1] == playerChar && map[0][2] == playerChar) ||
           (map[1][0] == playerChar && map[1][1] == playerChar && map[1][2] == playerChar) ||
           (map[2][0] == playerChar && map[2][1] == playerChar && map[2][2] == playerChar) ||
           (map[0][0] == playerChar && map[1][0] == playerChar && map[2][0] == playerChar) ||
           (map[0][1] == playerChar && map[1][1] == playerChar && map[2][1] == playerChar) ||
           (map[0][2] == playerChar && map[1][2] == playerChar && map[2][2] == playerChar) ||
           (map[0][0] == playerChar && map[1][1] == playerChar && map[2][2] == playerChar) ||
           (map[2][0] == playerChar && map[1][1] == playerChar && map[0][2] == playerChar)
        )
            result = true;

        return result;
    }

    public static void compGame(){
        int x = -1;
        int y = -1;

            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);


            } while (!isCellValid(x, y) && (pointMap[y][x] <= maxPoint));

        System.out.println("Компьютер выбрал ячейку "+(y+1)+" "+(x+1));
        map[y][x] = DOT_O;
        recalculateMap();
    }

    private static void recalculateMap(){
        int x = 0;
        for (int i=0; i<SIZE; i++){
            for (int y=0; y<SIZE; y++){
//                if (pointMap[i][y] > maxPoint)
//                    maxPoint = pointMap[i][y];
                if (map[i][y]==DOT_O || map[i][y]==DOT_EMPTY) {
                    pointMap[i][y]++;
                    if ((i-1)>=0)
                        pointMap[i-1][y]++;

                    if ((i+1)<SIZE)
                        pointMap[i+1][y]++;

                    if ((y-1)>=0)
                        pointMap[i][y-1]++;

                    if ((y+1)<SIZE)
                        pointMap[i][y+1]++;
                }
                else {
                    if ((i-1)>=0)
                        pointMap[i-1][y]--;

                    if ((i+1)<SIZE)
                        pointMap[i+1][y]--;

                    if ((y-1)>=0)
                        pointMap[i][y-1]--;

                    if ((y+1)<SIZE)
                        pointMap[i][y+1]--;
                }
                if (pointMap[i][y] > maxPoint)
                    maxPoint = pointMap[i][y];
            }
        }
    }
}
