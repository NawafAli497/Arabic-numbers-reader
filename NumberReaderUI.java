import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class NumberReaderUI extends JFrame {

    // ── Palette ──────────────────────────────────────────────────────────────
    private static final Color PAGE_BG        = new Color(241, 245, 249);  // slate-100
    private static final Color CARD_BG        = Color.WHITE;
    private static final Color HEADER_FROM    = new Color(99,  102, 241);  // indigo-500
    private static final Color HEADER_TO      = new Color(79,  70,  229);  // indigo-600
    private static final Color ACCENT         = new Color(79,  70,  229);
    private static final Color ACCENT_HOVER   = new Color(67,  56,  202);
    private static final Color ACCENT_PRESS   = new Color(55,  48,  163);
    private static final Color TEXT_DARK      = new Color(15,  23,  42);
    private static final Color TEXT_MED       = new Color(100, 116, 139);
    private static final Color BORDER_COLOR   = new Color(203, 213, 225);  // slate-300
    private static final Color RESULT_BG      = new Color(238, 242, 255);  // indigo-50
    private static final Color RESULT_BORDER  = new Color(199, 210, 254);  // indigo-200
    private static final Color RESULT_TEXT    = new Color(55,  48,  163);  // indigo-700
    private static final Color ERROR_BG       = new Color(254, 242, 242);  // red-50
    private static final Color ERROR_BORDER   = new Color(252, 165, 165);  // red-300
    private static final Color ERROR_TEXT     = new Color(185, 28,  28);   // red-700
    private static final Color SUCCESS_COLOR  = new Color(16,  185, 129);  // emerald-500
    private static final Color HEADER_SUB     = new Color(199, 210, 254);  // indigo-200

    // ── Components ───────────────────────────────────────────────────────────
    private JTextField  numberInput;
    private JLabel      arabicTextLabel;
    private JLabel      statusLabel;
    private CardPanel   resultCard;
    private AppButton   readButton;
    private boolean     isPlaying = false;

    // ─────────────────────────────────────────────────────────────────────────
    public NumberReaderUI() {
        setTitle("قارئ الأرقام العربية");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 580);
        setMinimumSize(new Dimension(480, 520));
        setLocationRelativeTo(null);
        setBackground(PAGE_BG);
        setLayout(new BorderLayout());

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    // ── Header ───────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, HEADER_FROM, getWidth(), getHeight(), HEADER_TO));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(28, 24, 28, 24));

        JLabel title = label("قارئ الأرقام العربية", new Font("Arial", Font.BOLD, 26), Color.WHITE);
        JLabel sub   = label("أكتب رقماً بين 0 و 1,000,000", new Font("Segoe UI", Font.PLAIN, 13), HEADER_SUB);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(sub);
        return header;
    }

    // ── Content ──────────────────────────────────────────────────────────────
    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setBackground(PAGE_BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(32, 40, 32, 40));

        content.add(buildInputCard());
        content.add(Box.createVerticalStrut(20));
        content.add(buildResultCard());
        return content;
    }

    // ── Input card ───────────────────────────────────────────────────────────
    private JPanel buildInputCard() {
        CardPanel card = new CardPanel(16, CARD_BG, BORDER_COLOR);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 28, 28, 28));
        card.setAlignmentX(LEFT_ALIGNMENT);

        // Text field
        numberInput = new JTextField();
        numberInput.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        numberInput.setHorizontalAlignment(JTextField.CENTER);
        numberInput.setBackground(Color.WHITE);
        numberInput.setForeground(TEXT_DARK);
        numberInput.setCaretColor(ACCENT);
        numberInput.setBorder(new RoundedFieldBorder(BORDER_COLOR, 10, 12));
        numberInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        numberInput.addActionListener(e -> onRead());
        numberInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { numberInput.setBorder(new RoundedFieldBorder(ACCENT, 10, 12)); }
            public void focusLost(FocusEvent e)   { numberInput.setBorder(new RoundedFieldBorder(BORDER_COLOR, 10, 12)); }
        });

        // Button
        readButton = new AppButton("اقرأ الرقم ▶", ACCENT, ACCENT_HOVER, ACCENT_PRESS);
        readButton.setAlignmentX(CENTER_ALIGNMENT);
        readButton.addActionListener(e -> onRead());

        card.add(numberInput);
        card.add(Box.createVerticalStrut(18));
        card.add(readButton);
        return card;
    }

    // ── Result card ──────────────────────────────────────────────────────────
    private JPanel buildResultCard() {
        resultCard = new CardPanel(16, RESULT_BG, RESULT_BORDER);
        resultCard.setLayout(new BoxLayout(resultCard, BoxLayout.Y_AXIS));
        resultCard.setBorder(new EmptyBorder(28, 28, 28, 28));
        resultCard.setAlignmentX(LEFT_ALIGNMENT);
        resultCard.setVisible(false);

        arabicTextLabel = new JLabel(" ", SwingConstants.CENTER);
        arabicTextLabel.setFont(new Font("Arial", Font.BOLD, 26));
        arabicTextLabel.setForeground(RESULT_TEXT);
        arabicTextLabel.setAlignmentX(CENTER_ALIGNMENT);
        arabicTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(TEXT_MED);
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);

        resultCard.add(arabicTextLabel);
        resultCard.add(Box.createVerticalStrut(10));
        resultCard.add(statusLabel);
        return resultCard;
    }

    // ── Logic ────────────────────────────────────────────────────────────────
    private void onRead() {
        if (isPlaying) return;

        String input = numberInput.getText().trim();
        if (input.isEmpty()) return;

        String[] sounds = Main.process(input);
        if (sounds == null || sounds.length == 0) {
            showError("رقم غير صالح · يجب أن يكون بين 0 و 1,000,000");
            return;
        }

        String text = Main.toArabicText(sounds);
        showResult(text);
        setPlaying(true, "◉  جاري التشغيل...", ACCENT);

        new Thread(() -> {
            SoundPlayer.loadAllSounds(sounds);
            for (String s : sounds) if (s != null) SoundPlayer.play(s);
            SwingUtilities.invokeLater(() -> {
                setPlaying(false, "✓  اكتمل التشغيل", SUCCESS_COLOR);
            });
        }).start();
    }

    private void showResult(String text) {
        resultCard.setColors(RESULT_BG, RESULT_BORDER);
        arabicTextLabel.setForeground(RESULT_TEXT);
        arabicTextLabel.setText("<html><div style='text-align:center;'>" + text + "</div></html>");
        resultCard.setVisible(true);
        revalidate(); repaint();
    }

    private void showError(String msg) {
        resultCard.setColors(ERROR_BG, ERROR_BORDER);
        arabicTextLabel.setForeground(ERROR_TEXT);
        arabicTextLabel.setText("<html><div style='text-align:center;'>" + msg + "</div></html>");
        statusLabel.setText(" ");
        resultCard.setVisible(true);
        revalidate(); repaint();
    }

    private void setPlaying(boolean playing, String status, Color color) {
        isPlaying = playing;
        readButton.setEnabled(!playing);
        statusLabel.setText(status);
        statusLabel.setForeground(color);
    }

    // ── Utility ──────────────────────────────────────────────────────────────
    private static JLabel label(String text, Font font, Color color) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(font);
        l.setForeground(color);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    // ── Entry point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new NumberReaderUI().setVisible(true));
    }
}

// ─── Card panel (white/tinted, rounded, border) ───────────────────────────────
class CardPanel extends JPanel {
    private int   radius;
    private Color bg;
    private Color border;

    CardPanel(int radius, Color bg, Color border) {
        this.radius = radius; this.bg = bg; this.border = border;
        setOpaque(false);
    }

    void setColors(Color bg, Color border) { this.bg = bg; this.border = border; repaint(); }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Subtle shadow
        g2.setColor(new Color(0, 0, 0, 18));
        g2.fill(new RoundRectangle2D.Double(2, 3, getWidth()-3, getHeight()-3, radius, radius));

        // Background
        g2.setColor(bg);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-2, radius, radius));

        // Border
        g2.setColor(border);
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth()-2, getHeight()-3, radius, radius));

        g2.dispose();
        super.paintComponent(g);
    }
}

// ─── Custom button ────────────────────────────────────────────────────────────
class AppButton extends JButton {
    private final Color base, hover, press;
    private Color current;

    AppButton(String text, Color base, Color hover, Color press) {
        super(text);
        this.base = base; this.hover = hover; this.press = press; this.current = base;
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(220, 50));
        setMaximumSize(new Dimension(220, 50));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { if (isEnabled()) { current = hover; repaint(); } }
            public void mouseExited(MouseEvent e)   { current = isEnabled() ? base : new Color(156, 163, 175); repaint(); }
            public void mousePressed(MouseEvent e)  { if (isEnabled()) { current = press; repaint(); } }
            public void mouseReleased(MouseEvent e) { if (isEnabled()) { current = hover; repaint(); } }
        });
    }

    @Override public void setEnabled(boolean b) {
        super.setEnabled(b);
        current = b ? base : new Color(156, 163, 175);
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(current);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 12, 12));
        super.paintComponent(g2);
        g2.dispose();
    }
}

// ─── Rounded border for text field ───────────────────────────────────────────
class RoundedFieldBorder extends AbstractBorder {
    private final Color color;
    private final int   radius;
    private final int   pad;

    RoundedFieldBorder(Color color, int radius, int pad) {
        this.color = color; this.radius = radius; this.pad = pad;
    }

    @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.6f));
        g2.draw(new RoundRectangle2D.Double(x+1, y+1, w-3, h-3, radius, radius));
        g2.dispose();
    }

    @Override public Insets getBorderInsets(Component c)             { return new Insets(pad, pad+4, pad, pad+4); }
    @Override public Insets getBorderInsets(Component c, Insets i)  { i.set(pad, pad+4, pad, pad+4); return i; }
}
