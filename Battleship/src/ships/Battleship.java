package ships;

import board.ConsoleColors;
import board.Ocean;

import java.util.Random;

public class Battleship extends Ship {
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
            startX += rnd.nextInt(2);
            location = new int[4][];
            location[0] = new int[]{startX, startY};
            location[1] = new int[]{startX + 1, startY};
            location[2] = new int[]{startX + 2, startY};
            location[3] = new int[]{startX + 3, startY};
        } else {
            startY += rnd.nextInt(2);
            location = new int[4][];
            location[0] = new int[]{startX, startY};
            location[1] = new int[]{startX, startY + 1};
            location[2] = new int[]{startX, startY + 2};
            location[3] = new int[]{startX, startY + 3};


        }
    }

    /**
     * Метод, выводящий на поле местонахождение корабля.
     */
    @Override
    public void show() {
        for (int[] ints : location) {
            Ocean.board[ints[0]][ints[1]].status = 'B';
        }
    }

    /**
     * @return - возвращает информацию об уничтоженном корабле.
     */
    @Override
    public String toString() {
        return ConsoleColors.YELLOW + "You just have sunk a battleship" + ConsoleColors.RESET;
    }
}
