package org.editor.command;

import javax.swing.JTextArea;
import org.editor.exception.NoMatchedResultsException;
import org.editor.exception.NoSpecifiedQueryException;
import org.editor.searcher.Searcher;

public class PreviousMatchCommand implements EditorCommand {

    private final Searcher searcher;
    private final JTextArea textArea;

    public PreviousMatchCommand(final Searcher searcher,
                                final JTextArea textArea) {
        this.searcher = searcher;
        this.textArea = textArea;
    }

    @Override
    public void execute() throws NoSpecifiedQueryException, NoMatchedResultsException {
        if (searcher.getMatchedCount() < 0 && textArea.getText().isEmpty()) {
            throw new NoSpecifiedQueryException("You should specify search query first");
        }
        if (searcher.getMatchedCount() <= 0) {
            throw new NoMatchedResultsException("No matched results for your query");
        }
        searcher.previousMatched();
        textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
        textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
        textArea.grabFocus();
    }
}
