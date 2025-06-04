package syntaxhighlighter;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.*;

// Gerçek zamanlı sözdizimi vurgulama için ana GUI sınıfı
public class SyntaxHighlighterGUI extends JFrame {
    private final JTextPane textPane = new JTextPane(); // Metin editör bileşeni
    private final JLabel statusLabel = new JLabel(" "); // Durum çubuğu
    private final LexicalAnalyzer lexer = new LexicalAnalyzer(); // Lexer nesnesi
    private final SimpleParser parser = new SimpleParser(); // Parser nesnesi
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // Arka plan işleri için
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // Zamanlanmış görevler
    private ScheduledFuture<?> highlightTask; // Vurgulama görevi referansı
    private static final int HIGHLIGHT_DELAY_MS = 300; // Vurgulama gecikmesi (ms)

    public SyntaxHighlighterGUI() {
        super("Real-Time Syntax Highlighter");
        initializeUI(); // Arayüzü başlat
    }

    // Arayüz bileşenlerini ayarlar
    private void initializeUI() {
        configureTextPane();
        configureStatusBar();
        setupWindow();
    }

    // Metin editörünü yapılandırır
    private void configureTextPane() {
        textPane.setFont(new Font("Consolas", Font.PLAIN, 16));
        // Metin değişikliklerini dinle
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { scheduleHighlight(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { scheduleHighlight(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { scheduleHighlight(); }
        });

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setRowHeaderView(createLineNumbers()); // Satır numaralarını ekle
        add(scrollPane, BorderLayout.CENTER);
    }

    // Satır numaraları bileşenini oluşturur
    private JComponent createLineNumbers() {
        JTextArea lineNumbers = new JTextArea("1");
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setEditable(false);
        lineNumbers.setFont(textPane.getFont());
        
        // Satır sayısı değişikliklerini güncelle
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateLineNumbers(lineNumbers); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateLineNumbers(lineNumbers); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateLineNumbers(lineNumbers); }
        });
        
        return new JScrollPane(lineNumbers);
    }

    // Satır numaralarını günceller
    private void updateLineNumbers(JTextArea lineNumbers) {
        SwingUtilities.invokeLater(() -> {
            int lines = textPane.getText().split("\n").length;
            StringBuilder numbers = new StringBuilder();
            for (int i = 1; i <= lines; i++) {
                numbers.append(i).append("\n");
            }
            lineNumbers.setText(numbers.toString());
        });
    }

    // Durum çubuğunu ayarlar
    private void configureStatusBar() {
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);
    }

    // Pencere ayarlarını yapar
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Sözdizimi vurgulamayı zamanlar
    private void scheduleHighlight() {
        if (highlightTask != null && !highlightTask.isDone()) {
            highlightTask.cancel(false);
        }
        highlightTask = scheduler.schedule(this::highlightSyntax, HIGHLIGHT_DELAY_MS, TimeUnit.MILLISECONDS);
    }

    // Sözdizimi vurgulama işlemini yapar
    private void highlightSyntax() {
        String text = textPane.getText();
        StyledDocument doc = textPane.getStyledDocument();
        
        // Arka planda token analizi yap
        executor.execute(() -> {
            List<Token> tokens = lexer.analyze(text);
            SwingUtilities.invokeLater(() -> {
                try {
                    // Önce varsayılan stile dön
                    doc.setCharacterAttributes(0, doc.getLength(), textPane.getStyle(StyleContext.DEFAULT_STYLE), true);
                    applyHighlighting(doc, tokens); // Vurgulamayı uygula
                    updateStatusBar(tokens); // Durum çubuğunu güncelle
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    // Token'lara göre renklendirme uygular
    private void applyHighlighting(StyledDocument doc, List<Token> tokens) throws BadLocationException {
        for (Token token : tokens) {
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setForeground(sas, Color.decode(token.getType().getColorHex()));
            doc.setCharacterAttributes(token.getStart(), token.getEnd() - token.getStart(), sas, false);
        }
    }

    // Durum çubuğunu günceller
    private void updateStatusBar(List<Token> tokens) {
        String[] lines = textPane.getText().split("\n");
        if (lines.length > 0) {
            String lastLine = lines[lines.length-1];
            if (!lastLine.trim().isEmpty()) {
                statusLabel.setText(parser.checkGrammar(lexer.analyze(lastLine)));
            }
        }
    }

    @Override
    public void dispose() {
        executor.shutdown(); // Executor'ı kapat
        scheduler.shutdown(); // Scheduler'ı kapat
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SyntaxHighlighterGUI());
    }
}