package ships;

import board.ConsoleColors;
import board.Ocean;

import java.util.Random;

public class Submarine extends Ship {
    /**
     * Метод рандомной расстановки корабля внутри блока матрицы размерностью 6X2 либо 2X6.
     *
     * @param startX   - стартовая позиция по первой координате
     * @param startY   - стартовая позиция по второй координате
     * @param vertical - направления корабля
     */
    @Override
    public void deploy(int startX, int startY, boolean vertical) {
        Random rnd = new Random();
        if (vertical) {
            startX += rnd.nextInt(5);
            location = new int[1][];
            location[0] = new int[]{startX, startY};
        } else {
            startY += rnd.nextInt(5);
            location = new int[1][];
            location[0] = new int[]{startX, startY};

        }
    }

    /**
     * Метод расстановки последней подлодки на оставшемся незаполненном поле.
     *
     * @param topX     - стартовая точка заполнения по первой координате
     * @param topY     - стартовая точка заполнения по второй координате
     * @param vertical - было ли заполнения кораблей вертикальным или горизонтальным.
     */
    public void deployRandomly(int topX, int topY, boolean vertical) {
        Random rnd = new Random();
        int posX, posY;
        // Рандомим позицию корабля.
        if (!vertical) {
            posX = rnd.nextInt(Ocean.board.length);
            posY = topY + 1 + rnd.nextInt(Ocean.board[0].length - topY);
        } else {
            posX = topX + 1 + rnd.nextInt(Ocean.board.length - topX);
            posY = rnd.nextInt(Ocean.board[0].length);
        }

        // Костыль для плохого рандома.
        if (posX >= Ocean.board.length) {
            posX = Ocean.board.length - 1;
        }
        if (posY >= Ocean.board[0].length) {
            posY = Ocean.board[0].length - 1;
        }
        location = new int[1][];
        location[0] = new int[]{posX, posY};
    }

    /**
     * Метод, выводящий на поле местонахождение корабля.
     */
    @Override
    public void show() {
        for (int[] ints : location) {
            Ocean.board[ints[0]][ints[1]].status = 'U';
        }
    }

    /**
     * @return - возвращает информацию об уничтоженном корабле.
     */
    @Override
    public String toString() {
        return ConsoleColors.YELLOW + "You just have sunk a submarine" + ConsoleColors.RESET;
    }
}
