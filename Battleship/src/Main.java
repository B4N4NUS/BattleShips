import board.ConsoleColors;
import board.Game;
import sounds.SoundPlayer;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    /**
     * Метод, опрашивающий пользователя о включении цветной отрисовки вывода.
     */
    private static void colorQuestion() {
        String input;
        System.out.println("Before we begin\nDo you want to add colors in your game?\n'y'/'n' \n(may not work with terminal)");
        System.out.print("--> ");
        Scanner s = new Scanner(System.in);
        input = s.nextLine();
        if (!Objects.equals(input, "y")) {
            Game.colorless = true;
        }
    }

    /**
     * Метод, приветствующий пользователя красивым ASCII артом.
     *
     * @throws InterruptedException - эксепшн может появиться из-за Thread.sleep
     */
    private static void introduction() throws InterruptedException {
        // Включаем звук запуска старой винды.
        SoundPlayer.PlaySound("o95");

        System.out.println(ConsoleColors.CYAN);
        String[] intro = {" Welcome to the BATTLESHIPS - Сhildhood favorite game filled with strategy and competitive spirit\n",
                "\n" + " /$$$$$$$              /$$     /$$     /$$                     /$$       /$$                    \n",
                "| $$__  $$            | $$    | $$    | $$                    | $$      |__/                    \n",
                "| $$  \\ $$  /$$$$$$  /$$$$$$ /$$$$$$  | $$  /$$$$$$   /$$$$$$$| $$$$$$$  /$$  /$$$$$$   /$$$$$$$\n",
                "| $$$$$$$  |____  $$|_  $$_/|_  $$_/  | $$ /$$__  $$ /$$_____/| $$__  $$| $$ /$$__  $$ /$$_____/\n",
                "| $$__  $$  /$$$$$$$  | $$    | $$    | $$| $$$$$$$$|  $$$$$$ | $$  \\ $$| $$| $$  \\ $$|  $$$$$$ \n",
                "| $$  \\ $$ /$$__  $$  | $$ /$$| $$ /$$| $$| $$_____/ \\____  $$| $$  | $$| $$| $$  | $$ \\____  $$\n",
                "| $$$$$$$/|  $$$$$$$  |  $$$$/|  $$$$/| $$|  $$$$$$$ /$$$$$$$/| $$  | $$| $$| $$$$$$$/ /$$$$$$$/\n",
                "|_______/  \\_______/   \\___/   \\___/  |__/ \\_______/|_______/ |__/  |__/|__/| $$____/ |_______/ \n",
                "                                                                            | $$                \n",
                "                                                                            | $$                \n",
                " Your goal is to find and destroy all enemy ships                           |__/                \n\n"};
        // Красиво выводим ASCII арт, пытаясь в ностальгию.
        for (int i = 0; i < intro.length; i++) {
            System.out.print(intro[i]);
            Thread.sleep(300);
        }
        System.out.println(ConsoleColors.RESET);
    }

    /**
     * Метод получения информации об игре через ввод пользователя в консоли.
     */
    public static void getGameInfoFromConsole() {
        Scanner info = new Scanner(System.in);
        int rows = 0, cols = 0, car = 0, bat = 0, cru = 0, des = 0, sub = 0, torp = 0;
        boolean incorrect = true, reco = false;

        System.out.println("First of all, let's create fleet and field");
        // Заставляем пользователя вводить данные, пока они не станут корректными.
        while (incorrect) {
            try {
                // Спрашиваем про размерность игрового поля.
                while (true) {
                    System.out.println("Input dimension " + ConsoleColors.CYAN + "'M' 'N'" + ConsoleColors.RESET + " of the field");
                    System.out.print("--> ");
                    rows = info.nextInt();
                    cols = info.nextInt();
                    if (rows < 6 || cols < 6 || cols > 30 || rows > 30) {
                        System.out.println("It is prohibited to make field this size");
                        continue;
                    }
                    break;

                }
                // Спрашиваем про корабли и торпеды.
                System.out.println("Input number of Carriers");
                System.out.print("--> ");
                car = info.nextInt();

                System.out.println("Input number of Battleships");
                System.out.print("--> ");
                bat = info.nextInt();

                System.out.println("Input number of Cruisers");
                System.out.print("--> ");
                cru = info.nextInt();

                System.out.println("Input number of Destroyers");
                System.out.print("--> ");
                des = info.nextInt();

                System.out.println("Input number of Submarines");
                System.out.print("--> ");
                sub = info.nextInt();

                System.out.println("Input number of torpedoes");
                System.out.print("--> ");
                torp = info.nextInt();

                // Если введено неправильное число торпед.
                if (torp < 0 || torp > car + bat + sub + cru + des) {
                    System.out.println("Torpedoes amount must be between 0 and fleet size");
                    throw new Exception();
                }
                // Если введено неправильное число кораблей.
                if (sub < 0 || des < 0 || cru < 0 || bat < 0 || car < 0) {
                    System.out.println("Fleet can't have negative quantity of ship types");
                    throw new Exception();
                }
                // Спрашиваем пользователя о корректности введенный данных.
                System.out.println("Computer's fleet will have " + car + " Carriers, " + bat + " Battleships, " + cru + " Cruisers, " + des + " Destroyers and " + sub + " Submarines");
                System.out.println("Is it correct?\n" + ConsoleColors.CYAN + "'y'/'n'" + ConsoleColors.RESET);
                System.out.print("--> ");
                // Костыль для получения стрингового инпута из консоли.
                String input = info.nextLine();
                input = info.nextLine();
                // Обрабатываем ответ пользователя.
                if (Objects.equals(input, "y")) {
                    incorrect = false;
                }
                // Спрашиваем про ship recovery mode.
                System.out.println("Do you want to enable ship recovery mode?\n" + ConsoleColors.CYAN + "'y'/'n'" + ConsoleColors.RESET);
                System.out.print("--> ");
                input = info.nextLine();
                // Обрабатываем ответ пользователя.
                if (Objects.equals(input, "y")) {
                    reco = true;
                }
            } catch (Exception exception) {
                String clearInput = info.nextLine();
                System.out.println("Incorrect Input\nPlease enter variables again");
            }
        }
        // Инициализируем игру.
        Game game = new Game(torp, reco, rows, cols, car, bat, cru, des, sub);
        replay(torp, reco, rows, cols, car, bat, cru, des, sub);
    }

    /**
     * Метод получения информации для игры через args.
     *
     * @param args - args из мейна
     */
    public static void getGameInfoFromArgs(String[] args) {
        // Инициализация строки со списком ошибок.
        String errorMessage = "ERROR\n";

        // Проверка на корректную длину входных аргументов.
        if (args.length != 9) {
            errorMessage += "Wrong args length\n";
        }
        int rows = 0, cols = 0, car = 0, bat = 0, cru = 0, des = 0, sub = 0, torp = 0;
        boolean reco = false;

        // Пытаемся распарсить размерность игрового поля.
        try {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
            if (rows < 6 || cols < 6 || cols > 30 || rows > 30) {
                errorMessage += "It is prohibited to make field this size\n";
            }
        } catch (Exception exception) {
            errorMessage += "Can't parse dimension of field\n";
        }

        // Пытаемся распарсить всю остальную инфу.
        try {
            car = Integer.parseInt(args[2]);
            bat = Integer.parseInt(args[3]);
            cru = Integer.parseInt(args[4]);
            des = Integer.parseInt(args[5]);
            sub = Integer.parseInt(args[6]);
            torp = Integer.parseInt(args[7]);
            if (Objects.equals(args[8], "y")) {
                reco = true;
            }
            if (torp < 0 || des + cru + bat + car < torp) {
                errorMessage += "Amount of torpedoes must be between 0 and fleet size";
                throw new Exception();
            }
            if (sub < 0 || des < 0 || cru < 0 || bat < 0 || car < 0) {
                errorMessage += "Fleet can't have negative quantity of ship types\n";
                throw new Exception();
            }
        } catch (Exception exception) {
            errorMessage += "Can't parse fleet\n";
        }

        // Если при парсе аргументов не было ошибок.
        if (errorMessage.equals("ERROR\n")) {
            // Выводим инфу о флоте и доп режимах.
            System.out.print("Computer's fleet will have " + car + " Carriers, " + bat + " Battleships, " + cru + " Cruisers, " + des + " Destroyers and " + sub + " Submarines\n" + torp + " torpedoes and");
            if (reco) {
                System.out.println(" ship recovery mode enabled");
            } else {
                System.out.println(" ship recovery mode disabled");
            }
            // Пускаем игрока к его игрушке.
            Game game = new Game(torp, reco, rows, cols, car, bat, cru, des, sub);
            replay(torp, reco, rows, cols, car, bat, cru, des, sub);
        } else {
            // Выводим ошибки и предлагаем ввести данные заново.
            System.out.println(errorMessage + "Would you like to input field and fleet again?\n'y'/'n'");
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            if (Objects.equals(input, "y")) {
                getGameInfoFromConsole();
            }
        }
    }

    /**
     * Метод, запускающийся после конца игры и позволяющий
     * сыграть заново как с новыми входными данными, так и со старыми.
     *
     * @param torp - количество торпед
     * @param reco - включен ли ship recovery mode
     * @param rows - количество строк на поле
     * @param cols - количество столбцов на поле
     * @param car  - количество carrier
     * @param bat  - количество battleship
     * @param cru  - количество cruiser
     * @param des  - количество destroyer
     * @param sub  - количество submarine
     */
    public static void replay(int torp, boolean reco, int rows, int cols, int car, int bat, int cru, int des, int sub) {
        // Спрашиваем игрока о готовности сыграть еще раз.
        System.out.println("Do you want to play again?\n" + ConsoleColors.CYAN + "'y'/'n'" + ConsoleColors.RESET);
        Scanner s = new Scanner(System.in);
        System.out.print("--> ");
        String input = s.nextLine();

        // Обрабатываем ответ.
        if (Objects.equals(input, "y")) {
            // Спрашиваем о сохранении прошлых входных данных.
            System.out.println("Do you want to play with your old variables\n" + ConsoleColors.CYAN + "'y'/'n'" + ConsoleColors.RESET);
            System.out.print("--> ");
            input = s.nextLine();

            // Обрабатываем ответ.
            if (Objects.equals(input, "y")) {
                Game game = new Game(torp, reco, rows, cols, car, bat, cru, des, sub);
                replay(torp, reco, rows, cols, car, bat, cru, des, sub);
            } else {
                getGameInfoFromConsole();
            }
        }
    }

    /**
     * main - точка входа в программу.
     *
     * @param args - входные аргументы
     * @throws InterruptedException - может появиться из-за использования Thread.sleep в introduction()
     */
    public static void main(String[] args) throws InterruptedException {
        // Проверяем, был ли запуск без args.
        if (args.length == 0) {
            colorQuestion();
            introduction();
            getGameInfoFromConsole();
        } else {
            Game.colorless = true;
            introduction();
            getGameInfoFromArgs(args);
        }
    }
}

