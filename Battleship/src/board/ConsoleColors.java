package board;
// Source:
// https://coderoad.ru/5762491/%D0%9A%D0%B0%D0%BA-%D0%BF%D0%B5%D1%87%D0%B0%D1%82%D0%B0%D1%82%D1%8C-%D1%86%D0%B2%D0%B5%D1%82-%D0%B2-%D0%BA%D0%BE%D0%BD%D1%81%D0%BE%D0%BB%D0%B8-%D1%81-%D0%BF%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%D1%8E-System-out-println
public class ConsoleColors {
    // Reset
    public static final String RESET = Game.colorless? "":"\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = Game.colorless? "":"\033[0;30m";   // BLACK
    public static final String RED = Game.colorless? "":"\033[0;31m";     // RED
    public static final String GREEN = Game.colorless? "":"\033[0;32m";   // GREEN
    public static final String YELLOW = Game.colorless? "":"\033[0;33m";  // YELLOW
    public static final String BLUE = Game.colorless? "":"\033[0;34m";    // BLUE
    public static final String PURPLE = Game.colorless? "":"\033[0;35m";  // PURPLE
    public static final String CYAN = Game.colorless? "":"\033[0;36m";    // CYAN
    public static final String WHITE = Game.colorless? "":"\033[0;37m";   // WHITE

    // Game.colorless? "":"\033[0m"; - такая конструкция нужна для обработки случая запуска из терминала,
    // так как в терминале коды цвета не распознаются и просто выводятся как \033[0m.
}
