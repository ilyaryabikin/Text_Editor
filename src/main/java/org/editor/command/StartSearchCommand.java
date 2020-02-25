package org.editor.command;

import org.editor.TextEditor;
import org.editor.exception.NoSpecifiedQueryException;
import org.editor.searcher.Searcher;

import javax.swing.*;

public class StartSearchCommand extends Command {

    public StartSearchCommand(TextEditor textEditor) {
        super(textEditor);
    }

    @Override
    public void execute() throws NoSpecifiedQueryException {
        JTextField searchField = textEditor.getSearchField();
        if (searchField.getText().isEmpty()) {
            throw new NoSpecifiedQueryException("You should specify search query first");
        }
        Searcher searcher = textEditor.getSearcher();
        JTextArea textArea = textEditor.getTextArea();
        boolean isRegex = textEditor.isRegex();

        new SwingWorker<Searcher, Searcher>() {

            @Override
            protected Searcher doInBackground() {
                searcher.search(searchField.getText(), textArea.getText(), isRegex);
                return searcher;
            }

            @Override
            protected void done() {
                if (searcher.getMatchedCount() > 0) {
                    setFirstMatched(textArea, searcher);
                }
            }
        }.execute();
    }

    private void setFirstMatched(JTextArea textArea, Searcher searcher) {
        textArea.setCaretPosition(searcher.getStartIndex() + searcher.getWordLength());
        textArea.select(searcher.getStartIndex(), searcher.getStartIndex() + searcher.getWordLength());
        textArea.grabFocus();
    }
}
