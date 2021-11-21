import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

//        //искать слова - передача аргументов из кода
//        ArrayList<String> list = new ArrayList<>();
//        list.add("новый");
//        list.add("даже");
//        list.add("и");
//        list.add("теперь");
//        list.add("купить");

        //искать слова - передача аргументов из коммандной строки
        ArrayList<String> list = new ArrayList<>(Arrays.asList(args));

        //Поиск вхождений
        LinkedList<Map.Entry<String, Integer>> setWords = Crawler.searchWords(list);

        System.out.println("Searching results:");
        setWords.forEach(System.out::println);

    }
}