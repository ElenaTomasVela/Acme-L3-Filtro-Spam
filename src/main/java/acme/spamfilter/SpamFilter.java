package acme.spamfilter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {

    String spamWords;
    double spamThreshold;

    public SpamFilter(String spamWords, double spamThreshold) {
        this.spamWords = spamWords;
        this.spamThreshold = spamThreshold;
    }

    public boolean isSpam(String message) {
        List<String> words = Arrays.asList(spamWords.split(","));

        int totalMatches = words.stream().mapToInt(word -> countWordMatches(message, word)).sum();
        int wordsInMessage = countWords(message);

        return spamThreshold < (double) totalMatches / wordsInMessage;
    }

    private static int countWordMatches(String message, String word) {
        Pattern pattern = Pattern.compile(word.replace(" ", "\\s+").toLowerCase());
        Matcher matcher = pattern.matcher(message.toLowerCase());
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static int countWords(String message) {
        return message.trim().split("\\s+").length;
    }

}
