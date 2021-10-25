package ships;

import board.ConsoleColors;
import board.Ocean;

public class Carrier extends Ship {
    /**
     * Метод рандомной расстановки корабля внутри блока матрицы размерностью 6X2 либо 2X6.
     *
     * @param startX   - стартовая позиция по первой координате
     * @param startY   - стартовая позиция по второй координате
     * @param vertical - направления корабля
     */
    @Override
    public void deploy(int startX, int startY, boolean vertical) {
        if (vertical) {
            location = new int[5][];
            location[0] = new int[]{startX, startY};
            location[1] = new int[]{startX + 1, startY};
            location[2] = new int[]{startX + 2, startY};
            location[3] = new int[]{startX + 3, startY};
            location[4] = new int[]{startX + 4, startY};
        } else {
            location = new int[5][];
            location[0] = new int[]{startX, startY};
            location[1] = new int[]{startX, startY + 1};
            location[2] = new int[]{startX, startY + 2};
            location[3] = new int[]{startX, startY + 3};
            location[4] = new int[]{startX, startY + 4};
        }
    }

    /**
     * Метод, выводящий на поле местонахождение корабля.
     */
    @Override
    public void show() {
        for (int[] ints : location) {
            Ocean.board[ints[0]][ints[1]].status = 'C';
        }
    }

    /**
     * @return - возвращает информацию об уничтоженном корабле.
     */
    @Override
    public String toString() {
        return ConsoleColors.YELLOW + "You just have sunk a carrier" + ConsoleColors.RESET;
    }
}
