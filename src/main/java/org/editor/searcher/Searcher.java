package org.editor.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {

    private List<WordOccurrence> matchedList;
    private int cursor;
    private WordOccurrence currentWord;

    private boolean isRegex = false;

    public void search(String query, String text) {
        matchedList = new ArrayList<>();
        if (!isRegex) {
            query = Pattern.quote(query);
        }
        Pattern pattern = Pattern.compile(query);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matchedList.add(new WordOccurrence(matcher.start(), matcher.end() - matcher.start()));
        }
        cursor = 0;
        currentWord = matchedList.get(cursor);
    }

    public void nextMatched() {
        cursor++;
        if (cursor >= matchedList.size()) {
            cursor = 0;
        }
        currentWord = matchedList.get(cursor);
    }

    public void previousMatched() {
        cursor--;
        if (cursor < 0) {
            cursor = matchedList.size() - 1;
        }
        currentWord = matchedList.get(cursor);
    }

    public int getStartIndex() {
        return currentWord.startIndex;
    }

    public int getWordLength() {
        return currentWord.wordLength;
    }

    public int getMatchedCount() {
        if (matchedList == null) {
            return -1;
        } else {
            return matchedList.size();
        }
    }

    public boolean isRegex() {
        return isRegex;
    }

    public void setRegex(boolean regex) {
        isRegex = regex;
    }

    private static class WordOccurrence {

        private final int startIndex;
        private final int wordLength;

        private WordOccurrence(int startIndex, int wordLength) {
            this.startIndex = startIndex;
            this.wordLength = wordLength;
        }
    }
}
