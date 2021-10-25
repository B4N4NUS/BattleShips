package board;

public class Cell {
    // Статус клетки.
    public char status = 'N';

    /**
     * Дефолтный конструктор.
     */
    public Cell() {
    }

    /**
     * Метод для красивого вывода статуса клетки в консоль.
     */
    public void print() {
        String color;
        switch (status) {
            case 'H' -> {
                color = ConsoleColors.RED;
            }
            case 'S' -> {
                color = ConsoleColors.GREEN;
            }
            case 'M' -> {
                color = ConsoleColors.CYAN;
            }
            case 'N' -> {
                color = ConsoleColors.WHITE;
            }
            default -> {
                color = ConsoleColors.YELLOW;
            }
        }
        System.out.print(color + " " + status + " " + ConsoleColors.RESET);
    }
}
