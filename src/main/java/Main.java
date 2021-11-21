import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {


//        ArrayList<String> list = new ArrayList<>(); //искать слова - передача аргументов из кода
//        list.add("к");
//        list.add("новый");
//        list.add("iphone");

        ArrayList<String> list = new ArrayList<>(Arrays.asList(args)); //искать слова - передача оргументов из коммандной строки

        Set<Map.Entry<String, Integer>> setWords = Crawler.searchWords(list);

        setWords.forEach(System.out::println);
    }
}