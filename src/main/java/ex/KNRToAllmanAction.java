package ex;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDocumentManager;

public class KNRToAllmanAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        if (editor == null || project == null) {
            Messages.showErrorDialog("No editor found!", "Error");
            return;
        }

        Document document = editor.getDocument();
        String text = document.getText();

        // Transform K&R braces to Allman style
        String reformattedText = reformatToAllman(text);

        // Replace the document content with reformatted text
        ApplicationManager.getApplication().runWriteAction(() -> {
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);
            document.setText(reformattedText);
        });

    }

    private String reformatToAllman(String text) {
        return text.replaceAll("\\{", "\n{");
    }

}
