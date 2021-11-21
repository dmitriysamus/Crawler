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
    public static Set<Map.Entry<String, Integer>> searchWords(ArrayList<String> words) throws IOException {

        ArrayList<String> articles = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();

        Document doc = Jsoup.connect("https://news.yandex.ru/computers.html").get();
        //элементы, в которых была обнаружена необходимая информация
        Elements innerElements = doc.getElementsByAttributeValue("class", "mg-card__inner");
        Elements textElements = doc.getElementsByAttributeValue("class", "mg-card__text");
        Elements annotationElements = doc.getElementsByAttributeValue("class", "mg-card__annotation");

        innerElements.forEach(divElement -> {
            Element aElement = divElement.child(0);
            String title = aElement.child(0).text();

            articles.add(title);
        });

        textElements.forEach(divElement -> {
            Element aElement = divElement.child(0);
            String title = aElement.child(0).text();

            articles.add(title);
        });

        annotationElements.forEach(divElement -> {
            articles.add(divElement.text());
        });

        articles.forEach(article -> {
            tmp.addAll(Arrays.asList(article.split(" ")));
        });

        List<String> lowerWords = tmp.stream().map(String::toLowerCase).collect(Collectors.toList());

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

        Comparator<Map.Entry<String, Integer>> entryComparator = Map.Entry.comparingByValue();
        Set<Map.Entry<String, Integer>> setWords = new TreeSet<>(entryComparator);
        setWords.addAll(map.entrySet());

        return setWords;
    }

}
