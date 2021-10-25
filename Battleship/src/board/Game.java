package board;

import ships.*;
import sounds.SoundPlayer;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Game {
    // Отвечает за наличие цветного вывода в консоль.
    public static boolean colorless = false;
    // Океан.
    public static Ocean ocean;
    // Флот.
    public static Ship[] fleet;
    // Количество сделанных выстрелов.
    private int shotsCount = 0;
    // Оставшиеся торпеды.
    private int torpedoes = 0;
    // Наличие ship recovery mode.
    public static boolean recoveryMode = false;

    /**
     * Конструктор класса Game.
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
    public Game(int torp, boolean reco, int rows, int cols, int car, int bat, int cru, int des, int sub) {
        torpedoes = torp;
        recoveryMode = reco;
        ocean = new Ocean(rows, cols);
        fleet = new Ship[car + bat + cru + des + sub];

        // Заполнение массива с флотом.
        for (int i = 0; i < car; i++) {
            fleet[i] = new Carrier();
        }
        for (int i = car; i < car + bat; i++) {
            fleet[i] = new Battleship();
        }
        for (int i = car + bat; i < car + bat + cru; i++) {
            fleet[i] = new Cruiser();
        }
        for (int i = car + bat + cru; i < car + bat + cru + des; i++) {
            fleet[i] = new Destroyer();
        }
        for (int i = car + bat + cru + des; i < car + bat + cru + des + sub; i++) {
            fleet[i] = new Submarine();
        }

        // Пытаемся расставить флот на поле.
        if (placeFleet()) {
            play();
        } else {
            System.out.println(ConsoleColors.RED + "Can't place fleet" + ConsoleColors.RESET);
        }
    }

    /**
     * Метод, скрывающий корабли, если они были подбиты не до конца.
     *
     * @return - был ли хоть один корабль скрыт
     */
    public static boolean recovery() {
        // Отвечает за то, был ли корабль спрятан нижестоящим циклом.
        boolean wasHidden = false;
        for (int i = 0; i < Ocean.board.length; i++) {
            for (int j = 0; j < Ocean.board[i].length; j++) {
                if (Ocean.board[i][j].status == 'H') {
                    Ocean.board[i][j].status = 'N';
                    wasHidden = true;
                }
            }
        }
        return wasHidden;
    }

    /**
     * Метод расстановки кораблей на поле.
     *
     * @return - удалось ли расставить флот
     */
    private boolean placeFleet() {
        // Обработка случая, когда пользователь решил ввести пустой флот.
        if (fleet.length == 0) {
            return false;
        }
        int currentShip = 0;
        Random rnd = new Random();

        // Заполнение поля происходит при помощи дробления поля на кластеры 2x6 или 6x2 клеток.
        // Далее в каждый такой кластер рандомно ставиться корабль, пока все корабли не закончатся.
        // Последняя подлодка ставиться в рандомное свободное оставшееся место на поле.

        // Заполняем поле двумя разными способами.
        if (rnd.nextBoolean()) {
            for (int i = 0; i < Ocean.board.length; i += 2) {
                for (int j = 0; j < Ocean.board[i].length; j += 6) {
                    // Если сейчас расставляем последний корабль и это подлодка.
                    if (fleet[currentShip] instanceof Submarine && currentShip + 1 == fleet.length) {
                        ((Submarine) fleet[currentShip++]).deployRandomly(i, j, true);
                    }
                    // Если корабли кончились.
                    if (currentShip == fleet.length) {
                        ocean.draw();
                        return true;
                    }
                    // Если еще есть место для кластера.
                    if (j + 5 <= Ocean.board[i].length) {
                        fleet[currentShip++].deploy(i, j, false);
                    }
                }
            }
        } else {
            for (int j = 0; j < Ocean.board[0].length; j += 2) {
                for (int i = 0; i < Ocean.board.length; i += 6) {
                    // Если сейчас расставляем последний корабль и это подлодка.
                    if (fleet[currentShip] instanceof Submarine && currentShip + 1 == fleet.length) {
                        ((Submarine) fleet[currentShip++]).deployRandomly(i, j, false);
                    }
                    // Если корабли кончились.
                    if (currentShip == fleet.length) {
                        ocean.draw();
                        return true;
                    }
                    // Если еще есть место для кластера.
                    if (i + 5 <= Ocean.board[i].length) {
                        fleet[currentShip++].deploy(i, j, true);
                    }
                }
            }
        }
        // Перерисовываем игровое поле.
        ocean.draw();
        return currentShip == fleet.length;
    }

    /**
     * Обработчик хода игрока.
     *
     * @param torpedo - была ли использована торпеда
     * @param fireX   - первая координата выстрела
     * @param fireY   - вторая координата выстрела
     */
    public void turn(boolean torpedo, int fireX, int fireY) {
        // Если был выстрел торпедой, но они кончились.
        if (torpedo && torpedoes == 0) {
            System.out.println("No torpedoes available");
            return;
        }

        // Если выстрел был за границами игрового поля.
        if (fireX >= Ocean.board.length || fireY >= Ocean.board[0].length || fireX < 0 || fireY < 0) {
            System.out.println("Out of bounds");
            return;
        }

        // Если выстрел попал в уже открытую клетку.
        if (Ocean.board[fireX][fireY].status != 'N') {
            System.out.println("You have already fired at this cell");
            return;
        }

        // Обрабатываем попадание.
        for (int i = 0; i < fleet.length; i++) {
            // Если корабль был подбит.
            if (fleet[i].isHit(fireX, fireY)) {
                if (torpedo) {
                    torpedoes--;
                    fleet[i].torpedoHit(fireX, fireY);
                } else {
                    fleet[i].hit(fireX, fireY);
                }
                shotsCount++;
                break;
            }
        }
        // Если выстрел не попал в корабль.
        if (Ocean.board[fireX][fireY].status == 'N') {
            Ocean.board[fireX][fireY].status = 'M';
            System.out.println(ConsoleColors.YELLOW + "Miss" + ConsoleColors.RESET);
            if (torpedo) {
                SoundPlayer.PlaySound("torpedo-miss");
                torpedoes--;
            } else {
                if (recoveryMode && recovery()) {
                    SoundPlayer.PlaySound("recovery-miss");
                } else {
                    SoundPlayer.PlaySound("miss");
                }
            }
            shotsCount++;
        }
        ocean.draw();
    }

    /**
     * Метод, запускающий игровую сессию, состоящую из ходов (Turn)
     */
    public void play() {
        System.out.println("Game has started");
        Scanner in = new Scanner(System.in);
        String input;
        int fireX = 0, fireY = 0;
        boolean quit = false;

        System.out.println("Input 'x y' to fire at [x][y] cell\nYou can fire torpedoes at [x][y] cell using 't x y' command\nIf you give up, enter 'show' to display fleet or 'quit' to exit game.");
        // Основной игровой цикл.
        while (!quit) {
            System.out.println("Enter your turn");
            System.out.print("--> ");
            input = in.nextLine();

            // Обрабатываем ввод пользователя.
            switch (input) {
                case "quit" -> {
                    quit = true;
                }
                default -> {
                    try {
                        // Парсим ввод пользователя.
                        String[] raw = input.split(" ");
                        if (raw.length == 3) {
                            // Если выстрел был с использованием торпеды.
                            if (Objects.equals(raw[0], "t")) {
                                fireX = Integer.parseInt(raw[1]);
                                fireY = Integer.parseInt(raw[2]);
                                turn(true, fireX, fireY);
                            } else {
                                throw new Exception();
                            }
                        } else {
                            fireX = Integer.parseInt(raw[0]);
                            fireY = Integer.parseInt(raw[1]);
                            turn(false, fireX, fireY);
                        }
                    } catch (Exception exception) {
                        System.out.println("Incorrect input");
                    }
                }
                case "show" -> {
                    // Выводим флот и выходим из игры.
                    for (Ship ship : fleet) {
                        ship.show();
                    }
                    ocean.draw();
                    quit = true;
                }
            }
        }
        System.out.println("Game over\nTotal shots fired - " + ConsoleColors.CYAN + shotsCount + ConsoleColors.RESET);
    }
}
