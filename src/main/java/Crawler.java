import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс {@link Crawler} отвечает за поиск слов
 * на странице https://news.yandex.ru/computers.html
 */
public class Crawler {

    /**
     * Метод {@link Crawler#searchWords(ArrayList)}
     * ищет слова на странице https://news.yandex.ru/computers.html
     *
     * @param words - список слов.
     */
    public static LinkedList<Map.Entry<String, Integer>> searchWords(ArrayList<String> words) throws IOException {

        ArrayList<String> articles = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();

        words = words.stream().map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new));

        Document doc = Jsoup.connect("https://news.yandex.ru/computers.html").get();
        //элементы, в которых была обнаружена необходимая информация
        Elements innerElements = doc.getElementsByAttributeValue("class", "mg-card__inner"); //заголовок больших статей
        Elements textElements = doc.getElementsByAttributeValue("class", "mg-card__text"); //заголовок маленьких статей
        Elements annotationElements = doc.getElementsByAttributeValue("class", "mg-card__annotation"); //текст статей

        //считывание заголовков больших статей
        innerElements.forEach(divElement -> {
            Element aElement = divElement.child(0);
            String title = aElement.child(0).text();

            articles.add(title);
        });

        //считывание заголовков маленьких статей
        textElements.forEach(divElement -> {
            Element aElement = divElement.child(0);
            String title = aElement.child(0).text();

            articles.add(title);
        });

        //считывание теста статей
        annotationElements.forEach(divElement -> {
            articles.add(divElement.text());
        });

        //разбивка текстов на слова
        articles.forEach(article -> {
            tmp.addAll(Arrays.asList(article.split(" ")));
        });

        //все слова в lowerCase
        List<String> lowerWords = tmp.stream().map(String::toLowerCase).collect(Collectors.toList());

        //проверка слов на соответствие передаваемым в качестве агрумента и подсчет вхождений
        Map<String, Integer> map = new TreeMap<>();
        for (String word: lowerWords) {
            if (words.contains(word)) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }

        //сортировка значений по убыванию
        LinkedList<Map.Entry<String, Integer>> resultList = new LinkedList<>();
        resultList.addAll(map.entrySet());

        resultList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return resultList;
    }

}
