package board;

public class Ocean {
    // Матрица из клеток - игровое поле.
    public static Cell[][] board;

    /**
     * Конструктор, создающий игровое поле.
     * @param rows - количество строк.
     * @param cols - количество столбцов.
     */
    public Ocean(int rows, int cols){
        board = new Cell[rows][];
        for(int i = 0; i < rows; i++){
            board[i] = new Cell[cols];
            for(int j = 0; j < cols; j++){
                board[i][j] = new Cell();
            }
        }
    }

    /**
     * Метод форматирования текста.
     * @param input - строка с числом от 0 до 99
     * @return - строка из двух символов. Пример - " 0" или "99"
     */
    private String outFormatter(String input){
        if (input.length() == 1){
            return " "+input;
        }
        return input;
    }

    /**
     * Метод отрисовки игрового поля на экране пользователя.
     */
    public void draw(){
        // Заполняем заголовок.
        String header = "   N";
        for(int i = 0; i < board[0].length; i++){
            header+= outFormatter(i+"")+" ";
        }

        // Заполняем закрывающую матрицу часть заголовка.
        String lowerHeader = "---";
        lowerHeader = lowerHeader.repeat(board[0].length);
        header+="\n M *"+lowerHeader+"-*";
        System.out.println(header);

        // Выводим содержимое матрицы.
        for(int i = 0; i < board.length; i++){
            System.out.print(outFormatter(i+"")+" |");
            for(int j = 0; j < board[i].length; j++){
                board[i][j].print();
            }
            System.out.println(" |");
        }

        // Закрываем матрицу снизу.
        System.out.println("   *"+lowerHeader+"-*");
    }
}
