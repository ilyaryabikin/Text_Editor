package org.editor.command;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.editor.exception.NoSpecifiedQueryException;
import org.editor.searcher.Searcher;

public class StartSearchCommand implements EditorCommand {

    private final Searcher searcher;
    private final JTextArea textArea;
    private final JTextField searchField;

    public StartSearchCommand(final Searcher searcher,
                              final JTextArea textArea,
                              final JTextField searchField) {
        this.searcher = searcher;
        this.textArea = textArea;
        this.searchField = searchField;
    }

    @Override
    public void execute() throws NoSpecifiedQueryException {
        if (searchField.getText().isEmpty()) {
            throw new NoSpecifiedQueryException("You should specify search query first");
        }

        new SwingWorker<Searcher, Searcher>() {

            @Override
            protected Searcher doInBackground() {
                searcher.search(searchField.getText(), textArea.getText());
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
