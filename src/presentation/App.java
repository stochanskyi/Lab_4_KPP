package presentation;

import data.analizyng.AnalyzingConsumer;
import data.analizyng.AnalyzingData;
import data.execution.Executor;
import data.monitor.ThreadState;

import javax.swing.*;

public class App implements AnalyzingConsumer {

    private JPanel panelMain;
    private JButton performButton;
    private JTextArea resultText;
    private JScrollPane viewScroll;

    public static void main(String[] args) {
        JFrame ftame = new JFrame("Lab 4");
        var screen = new App();
        ftame.setContentPane(screen.panelMain);
        ftame.setSize(600, 600);
        screen.init();
        ftame.setVisible(true);
    }

    private boolean isRunning = false;

    public void init() {
        performButton.addActionListener((e) -> {
            if (isRunning) return;
            isRunning = true;
            setButtonEnabled(false);
            performAnalyze();
        });
    }

    private void performAnalyze() {
        resultText.setText("");
        new Executor().execute(this);
    }

    @Override
    public synchronized void onAnalyzingUpdate(ThreadState threadState) {
        resultText.append(threadState.toString());
        resultText.append("---------------------------------------------------\n");
        scrollDown();
    }

    @Override
    public synchronized void onAnalyzingCompleted(AnalyzingData analyzingData) {
        resultText.append(analyzingData.toString());
        resultText.append("---------------------------------------------------\n");
        scrollDown();
        setButtonEnabled(true);
        isRunning = false;
    }

    private void scrollDown() {
        JScrollBar vertical = viewScroll.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
    }

    private void setButtonEnabled(boolean isEnabled) {
        performButton.setEnabled(isEnabled);
    }
}
