package org.editor.command;

import org.editor.TextEditor;
import org.editor.exception.NoMatchedResultsException;
import org.editor.exception.NoSpecifiedQueryException;
import org.editor.searcher.Searcher;

import javax.swing.*;

public class NextMatchCommand extends Command {

    public NextMatchCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() throws NoSpecifiedQueryException, NoMatchedResultsException {
        Searcher searcher = textEditor.getSearcher();
        JTextArea textArea = textEditor.getTextArea();
        if (searcher.getMatchedCount() < 0 && textArea.getText().isEmpty()) {
            throw new NoSpecifiedQueryException("You should specify search query first");
        }
        if (searcher.getMatchedCount() <= 0) {
            throw new NoMatchedResultsException("No matched results for your query");
        }
        searcher.nextMatched();
        textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
        textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
        textArea.grabFocus();
    }
}
