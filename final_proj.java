import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class final_proj {
    public static void main(String[] args) throws IOException {
        String articles1 = fileContent("C:\\Users\\yuri_\\Documents\\math 3215\\final_project\\src\\reut2-000.sgm");
        String articles2 = fileContent("C:\\Users\\yuri_\\Documents\\math 3215\\final_project\\src\\reut2-001.sgm");
        String stopWordsString = fileContent("C:\\Users\\yuri_\\Documents\\math 3215\\final_project\\src\\stopWords");
        Set<String> stopWords = splittingStopWords(stopWordsString);

        List<String> articleBodies1 = new ArrayList<>();
        List<String> articleTitles1 = new ArrayList<>();
        List<String> articleBodies2 = new ArrayList<>();
        List<String> articleTitles2 = new ArrayList<>();
        splitIntoArticles(articles1, articleBodies1, articleTitles1);
        splitIntoArticles(articles2, articleBodies2, articleTitles2);


        List<String> allArtBodies = new ArrayList<String>();
        allArtBodies.addAll(articleBodies1);
        allArtBodies.addAll(articleBodies2);

        List<String> tempArtBod = new ArrayList<>();
        String individualBody = "";
        //replaces any non-alphanumeric character and number with a space
        for (int i = 0; i < allArtBodies.size(); i++) {
            individualBody = allArtBodies.get(i).replaceAll("\\W+", " ");
            individualBody = individualBody.replaceAll("\\d+", " ");
            tempArtBod.add(individualBody);
        }
        allArtBodies = tempArtBod;

        //Creates text file of array of article bodies
        createBodiesTxtFile(allArtBodies);

        List<String> allArtTitles = new ArrayList<String>();
        allArtTitles.addAll(articleTitles1);
        allArtTitles.addAll(articleTitles2);

        List<Set<String>> articlesSplitWords= splitIntoWords(allArtBodies, stopWords);

        //NO LONGER USED
        createMatrix(articlesSplitWords, allArtTitles, stopWords);
    }

    public static void createBodiesTxtFile(List<String> artBodies) throws IOException {
        PrintWriter writer = new PrintWriter("bodies_new.txt", "UTF-8");
        for (String s: artBodies) {
            writer.println(s);
        }
        writer.close();

    }

    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }

    public static Set<String> splittingStopWords(String stopWordsString) {
        String[] stopWordsTemp = stopWordsString.split("\n+");

        Set<String> stopWords = new HashSet<String>();
        for (int i = 0; i < stopWordsTemp.length; i++) {
            stopWords.add(stopWordsTemp[i].trim());
        }

        return stopWords;
    }

    public static void splitIntoArticles(String articles,
                                         List<String> articleBodies, List<String> articleTitles) {
        //find string between <...> and </...>
        articles = articles.replaceAll("\\*\\*\\*\\*\\*\\*<TITLE>(?s)(.*?)</TITLE>Blah blah blah.", "");
        Pattern bodyPattern = Pattern.compile("<BODY>(?s)(.*?)</BODY>");
        Pattern titlePattern = Pattern.compile("<TITLE>(?s)(.*?)</TITLE>");
        Matcher bodyMatcher = bodyPattern.matcher(articles);
        Matcher titleMatcher = titlePattern.matcher(articles);

        int i = 0;
        while (bodyMatcher.find()) {
            //lowercase
            articleBodies.add(bodyMatcher.group(1).toLowerCase());
            i++;
        }
        i = 0;
        while (titleMatcher.find()) {
            articleTitles.add(titleMatcher.group(1));
            i++;
        }


    }

    public static List<Set<String>> splitIntoWords(List<String> articleBodies, Set<String> stopWords) {

        List<Set<String>> articlesWsplitWords = new ArrayList<>();
        for (int j = 0; j < articleBodies.size(); j++) {
            String[] words = articleBodies.get(j).split("\\s+");
            Set<String> set = new HashSet<>(Arrays.asList(words));
            articlesWsplitWords.add(set);
            for (String s: set) {
                s = s.trim();
            }
        }
        int totalWords = 0;
        for (int l = 0; l < articlesWsplitWords.size(); l++) {
            totalWords = totalWords + articlesWsplitWords.get(l).size();
        }
        System.out.println("TOTAL WORDS before removing stop words: "+totalWords);

        for (int k = 0; k < articlesWsplitWords.size(); k++) {
            Iterator<String> iter = articlesWsplitWords.get(k).iterator();

            while (iter.hasNext()) {
                String str = iter.next();
                if (stopWords.contains(str))
                    iter.remove();
            }
        }

        totalWords = 0;
        for (int l = 0; l < articlesWsplitWords.size(); l++) {
            totalWords = totalWords + articlesWsplitWords.get(l).size();
        }
        System.out.println("TOTAL WORDS after removing stop words: "+totalWords);
        return articlesWsplitWords;
    }

    public static void createMatrix(List<Set<String>> articleBodies, List<String> articleTitles, Set<String> stopWords) throws IOException {
        String[] titles = new String[articleTitles.size()];
        String[] words = new String[12158];
        int[][] matrix = new int[1855][12158];
        int i = 0;
        //added titles to array
        for (String s: articleTitles) {
            titles[i] = s;
            i++;
        }
        int k = 0;
        for (int m = 0; m < articleBodies.size(); m++) {

            Iterator<String> iter = articleBodies.get(m).iterator();
            while (iter.hasNext()) {
                String str = iter.next();
                List<String> list = Arrays.asList(words);
                if (!list.contains(str)) {
                    words[k] = str;
                    k++;
                }
            }
        }
        for (int n = 0; n < titles.length; n++) {
            for (int j = 0; j < words.length; j++) {
                if (articleBodies.get(n).contains(words[j])) {
                    matrix[n][j] = 1;
                } else {
                    matrix[n][j] = 0;
                }
            }
        }


    }


}