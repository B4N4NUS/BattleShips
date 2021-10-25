package ships;

import board.ConsoleColors;
import board.Ocean;
import sounds.SoundPlayer;

public class Ship {
    // Содержит координаты корабля.
    public int[][] location;
    // Указывает на состаяние корабля (Живой/Утонул)
    public boolean sunk = false;
    // Указывает на количество попаданий в корабль.
    public boolean firstHit = true;

    /**
     * Метод рандомной расстановки корабля внутри блока матрицы размерностью 6X2 либо 2X6.
     *
     * @param startX   - стартовая позиция по первой координате
     * @param startY   - стартовая позиция по второй координате
     * @param vertical - направления корабля
     */
    public void deploy(int startX, int startY, boolean vertical) {
    }

    /**
     * Метод, нужный для получения информации о попадании снаряда в корабль.
     *
     * @param row - первая координата выстрела
     * @param col - вторая координата выстрела
     * @return - true если выстрел попал в корабль, иначе false
     */
    public boolean isHit(int row, int col) {
        for (int[] pos : location) {
            // Если игрок подбил корабль.
            if (pos[0] == row && pos[1] == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод, обрабатывающий попадание в корабль торпеды.
     *
     * @param row - первая координата выстрела
     * @param col - вторая координата выстрела
     */
    public void torpedoHit(int row, int col) {
        // Воспроизведение звука попадания торпеды.
        SoundPlayer.PlaySound("torpedo-hit");
        for (int[] value : location) {
            Ocean.board[value[0]][value[1]].status = 'S';
        }
        System.out.println(this.toString());
    }

    /**
     * Метод, обрабатывающий попадание в корабль.
     *
     * @param row - первая координата выстрела
     * @param col - вторая координата выстрела
     */
    public void hit(int row, int col) {
        // Переменная отвечает за звук.
        int whatSound = 1;
        // Если это первое попадание в корабль.
        if (firstHit) {
            firstHit = false;
            whatSound = 0;
        }

        Ocean.board[row][col].status = 'H';
        sunk = true;

        // Если есть еще неподбитые клетки.
        for (int[] value : location) {
            if (Ocean.board[value[0]][value[1]].status != 'H') {
                System.out.println(ConsoleColors.YELLOW + "Hit" + ConsoleColors.RESET);
                sunk = false;
                break;
            }
        }

        // Если корабль утонул.
        if (sunk) {
            // Меняем звук.
            whatSound = 2;
            // Перерисовываем корабль на игровом поле.
            for (int[] value : location) {
                Ocean.board[value[0]][value[1]].status = 'S';
            }
            System.out.println(this.toString());
        }

        // Воспроизводим звук.
        switch (whatSound) {
            // Если это было первое попадание и корабль не утонул.
            case 0 -> {
                SoundPlayer.PlaySound("hit-first");
            }
            // Если это было не первое попадание и корабль не утонул.
            case 1 -> {
                SoundPlayer.PlaySound("hit");
            }
            // Если корабль был уничтожен выстрелом.
            default -> {
                SoundPlayer.PlaySound("hit-final");
            }
        }
    }

    /**
     * Метод, выводящий на поле местонахождение корабля.
     */
    public void show() {
    }

    /**
     * @return - возвращает информацию об уничтоженном корабле.
     */
    @Override
    public String toString() {
        return "You just have sunk a ship";
    }
}
