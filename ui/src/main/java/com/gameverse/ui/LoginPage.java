package com.gameverse.ui;

import com.gameverse.player.Player;
import com.gameverse.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Cyberpunk-themed login page for GameVerse platform.
 * Split layout: login form on left, info panels on right.
 */
public class LoginPage extends JFrame {

    private JTextField emailField;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton checkPasswordButton;
    private JLabel errorMessageLabel;
    private JCheckBox rememberMeCheckBox;
    private PasswordFieldRow passwordRow;
    private PlayerManager playerManager;
    private LoginCallback loginCallback;

    // Colors
    private static final Color BG = new Color(12, 13, 22);
    private static final Color PANEL_BG = new Color(18, 20, 34);
    private static final Color CARD_BG = new Color(22, 24, 40);
    private static final Color CARD_BORDER = new Color(40, 44, 70);
    private static final Color ACCENT = new Color(0, 200, 255);
    private static final Color ACCENT2 = new Color(180, 80, 255);
    private static final Color PINK = new Color(255, 80, 180);
    private static final Color TEXT = new Color(220, 225, 245);
    private static final Color TEXT_DIM = new Color(120, 130, 160);
    private static final Color FIELD_BG = new Color(16, 18, 32);
    private static final Color GREEN = new Color(0, 220, 120);
    private static final Color ERROR_COLOR = new Color(255, 80, 100);
    private static final String EMAIL_PH = "CYBER_KNIGHT//operative@gamex.io";

    public interface LoginCallback {
        void onLoginSuccess(Player player);
        void onLoginFailed(String message);
        void onSignUp();
    }

    public LoginPage(LoginCallback callback) {
        this.loginCallback = callback;
        this.playerManager = PlayerManager.getInstance();
        initUI();
        setVisible(true);
    }

    private void initUI() {
        setTitle("GameVerse - Gateway Protocol v4.2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        setContentPane(root);

        root.add(createTopBar(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(20, 0));
        body.setBackground(BG);
        body.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        body.add(createLeftPanel(), BorderLayout.CENTER);
        body.add(createRightPanel(), BorderLayout.EAST);

        root.add(body, BorderLayout.CENTER);

        root.add(createFooter(), BorderLayout.SOUTH);
    }

    // ---- TOP BAR ----
    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(PANEL_BG);
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)));

        LogoComponent logo = new LogoComponent();
        logo.setPreferredSize(new Dimension(280, 50));

        JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightInfo.setOpaque(false);

        JLabel portal = new JLabel("GAMEX PORTAL");
        portal.setFont(new Font("Segoe UI", Font.BOLD, 11));
        portal.setForeground(TEXT_DIM);
        JLabel secured = new JLabel("SECURED NODE");
        secured.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        secured.setForeground(new Color(80, 90, 120));

        JPanel infoText = new JPanel();
        infoText.setOpaque(false);
        infoText.setLayout(new BoxLayout(infoText, BoxLayout.Y_AXIS));
        infoText.add(portal);
        infoText.add(secured);

        JLabel proto = new JLabel("● GATEWAY PROTOCOL V4.2");
        proto.setFont(new Font("Consolas", Font.BOLD, 11));
        proto.setForeground(GREEN);
        proto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 80, 60)),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        rightInfo.add(infoText);
        rightInfo.add(proto);

        bar.add(logo, BorderLayout.WEST);
        bar.add(rightInfo, BorderLayout.EAST);
        return bar;
    }

    // ---- LEFT PANEL (login form) ----
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("AUTHENTICATE YOUR RIG  ❤");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(6));

        JLabel subtitle = new JLabel("Enter your encrypted telemetry credentials or bridge your sovereign gaming ID to unlock the arena.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_DIM);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(18));

        // Social login buttons
        JPanel socialRow = new JPanel(new GridLayout(1, 4, 10, 0));
        socialRow.setOpaque(false);
        socialRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        socialRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        String[][] socials = {{"Discord","▣"},{"Steam","⬤"},{"Twitch","⬥"},{"Epic Games","⬦"}};
        String[] subLabels = {"Fast Auth","Sync Rig","Stream ID","Unreal Core"};
        for (int i = 0; i < 4; i++) {
            socialRow.add(createSocialButton(socials[i][0], subLabels[i]));
        }
        panel.add(socialRow);
        panel.add(Box.createVerticalStrut(14));

        // Divider
        JPanel divider = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        divider.setOpaque(false);
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel divL = new JLabel("───"); divL.setForeground(CARD_BORDER);
        JLabel divT = new JLabel(" OR SECURE ACCESS KEY "); divT.setFont(new Font("Consolas", Font.BOLD, 9)); divT.setForeground(TEXT_DIM);
        JLabel divR = new JLabel("───"); divR.setForeground(CARD_BORDER);
        divider.add(divL); divider.add(divT); divider.add(divR);
        panel.add(divider);
        panel.add(Box.createVerticalStrut(12));

        // Email field
        JPanel emailHeader = new JPanel(new BorderLayout());
        emailHeader.setOpaque(false);
        emailHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        emailHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel eLabel = new JLabel("OPERATIVE TAG / EMAIL  ★");
        eLabel.setFont(new Font("Consolas", Font.BOLD, 11));
        eLabel.setForeground(TEXT);
        JLabel eTag = new JLabel("[ID MATRIX]");
        eTag.setFont(new Font("Consolas", Font.PLAIN, 10));
        eTag.setForeground(TEXT_DIM);
        emailHeader.add(eLabel, BorderLayout.WEST);
        emailHeader.add(eTag, BorderLayout.EAST);
        panel.add(emailHeader);
        panel.add(Box.createVerticalStrut(4));

        emailField = createCyberField(EMAIL_PH);
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(14));

        // Password field
        JPanel passHeader = new JPanel(new BorderLayout());
        passHeader.setOpaque(false);
        passHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        passHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel pLabel = new JLabel("SECURITY PASSCODE  ★");
        pLabel.setFont(new Font("Consolas", Font.BOLD, 11));
        pLabel.setForeground(TEXT);
        JLabel pLink = new JLabel("Forgot Passcode?");
        pLink.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pLink.setForeground(ACCENT);
        pLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passHeader.add(pLabel, BorderLayout.WEST);
        passHeader.add(pLink, BorderLayout.EAST);
        panel.add(passHeader);
        panel.add(Box.createVerticalStrut(4));

        passwordRow = new PasswordFieldRow(ACCENT, new Color(0, 160, 200));
        passwordRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(passwordRow);
        panel.add(Box.createVerticalStrut(6));

        // Check password + Remember row
        JPanel checkRow = new JPanel(new BorderLayout());
        checkRow.setOpaque(false);
        checkRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        checkRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        rememberMeCheckBox = new JCheckBox("Remember this Rig for 30 cycles");
        rememberMeCheckBox.setBackground(BG);
        rememberMeCheckBox.setForeground(TEXT_DIM);
        rememberMeCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        rememberMeCheckBox.setFocusPainted(false);

        checkPasswordButton = new JButton("⚙ Password Check");
        checkPasswordButton.setFont(new Font("Consolas", Font.BOLD, 10));
        checkPasswordButton.setForeground(ACCENT2);
        checkPasswordButton.setContentAreaFilled(false);
        checkPasswordButton.setBorderPainted(false);
        checkPasswordButton.setFocusPainted(false);
        checkPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkPasswordButton.addActionListener(e ->
            PasswordFieldRow.showPasswordCheck(this, passwordRow.getPassword()));

        checkRow.add(rememberMeCheckBox, BorderLayout.WEST);
        checkRow.add(checkPasswordButton, BorderLayout.EAST);
        panel.add(checkRow);
        panel.add(Box.createVerticalStrut(16));

        // Error label
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        errorMessageLabel.setForeground(ERROR_COLOR);
        errorMessageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(errorMessageLabel);
        panel.add(Box.createVerticalStrut(8));

        // Login button (gradient)
        loginButton = new JButton("INITIALIZE SESSION // DEPLOY RIG  ⚡");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginButton.repaint(); }
            public void mouseExited(MouseEvent e) { loginButton.repaint(); }
        });
        loginButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = c.getWidth(), h = c.getHeight();
                g2.setPaint(new GradientPaint(0, 0, ACCENT, w, 0, PINK));
                g2.fillRoundRect(0, 0, w, h, 12, 12);
                g2.dispose();
                super.paint(g, c);
            }
        });
        loginButton.addActionListener(e -> handleLogin());
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(14));

        // Sign up link
        JPanel signUpRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        signUpRow.setOpaque(false);
        signUpRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        signUpRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel pre = new JLabel("New operative on the grid? ");
        pre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pre.setForeground(TEXT_DIM);
        signUpButton = new JButton("Create GameX Protocol Account (Earn +500 XP)");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signUpButton.setForeground(ACCENT);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(e -> {
            if (loginCallback != null) loginCallback.onSignUp();
            dispose();
        });
        signUpRow.add(pre);
        signUpRow.add(signUpButton);
        panel.add(signUpRow);

        // Wire Enter key
        emailField.addActionListener(e -> handleLogin());
        passwordRow.getField().addActionListener(e -> handleLogin());

        return panel;
    }

    // ---- RIGHT PANEL (info cards) ----
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Season card
        panel.add(createSeasonCard());
        panel.add(Box.createVerticalStrut(12));

        // Telemetry card
        panel.add(createTelemetryCard());
        panel.add(Box.createVerticalStrut(12));

        // Anti-cheat card
        panel.add(createAntiCheatCard());

        return panel;
    }

    private JPanel createSeasonCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel live = new JLabel("● MATCHMAKING MATRIX LIVE");
        live.setFont(new Font("Consolas", Font.BOLD, 10));
        live.setForeground(GREEN);
        JLabel season = new JLabel("SEASON 09");
        season.setFont(new Font("Consolas", Font.BOLD, 11));
        season.setForeground(ACCENT);
        topRow.add(live, BorderLayout.WEST);
        topRow.add(season, BorderLayout.EAST);
        card.add(topRow);
        card.add(Box.createVerticalStrut(8));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1, true));
        content.setBackground(new Color(16, 18, 30));
        JLabel dispatch = new JLabel("CURRENT ARENA DISPATCH");
        dispatch.setFont(new Font("Consolas", Font.BOLD, 9));
        dispatch.setForeground(TEXT_DIM);
        dispatch.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel event = new JLabel("Hyper-Velocity Championship Qualifier #14");
        event.setFont(new Font("Segoe UI", Font.BOLD, 13));
        event.setForeground(TEXT);
        event.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(dispatch);
        content.add(Box.createVerticalStrut(4));
        content.add(event);
        card.add(content);
        return card;
    }

    private JPanel createTelemetryCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel mesh = new JLabel("GLOBAL TELEMETRY MESH");
        mesh.setFont(new Font("Consolas", Font.BOLD, 10));
        mesh.setForeground(TEXT);
        JLabel green = new JLabel("ALL NODES GREEN");
        green.setFont(new Font("Consolas", Font.BOLD, 10));
        green.setForeground(GREEN);
        topRow.add(mesh, BorderLayout.WEST);
        topRow.add(green, BorderLayout.EAST);
        card.add(topRow);
        card.add(Box.createVerticalStrut(10));

        JPanel pings = new JPanel(new GridLayout(1, 3, 8, 0));
        pings.setOpaque(false);
        pings.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        String[][] nodes = {{"US-EAST","● 8ms"},{"EU-CENTRAL","● 14ms"},{"AP-PACIFIC","● 22ms"}};
        for (String[] n : nodes) {
            JPanel node = new JPanel();
            node.setOpaque(false);
            node.setLayout(new BoxLayout(node, BoxLayout.Y_AXIS));
            JLabel name = new JLabel(n[0]);
            name.setFont(new Font("Consolas", Font.PLAIN, 9));
            name.setForeground(TEXT_DIM);
            name.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel ms = new JLabel(n[1]);
            ms.setFont(new Font("Consolas", Font.BOLD, 12));
            ms.setForeground(GREEN);
            ms.setAlignmentX(Component.LEFT_ALIGNMENT);
            node.add(name);
            node.add(ms);
            pings.add(node);
        }
        card.add(pings);
        return card;
    }

    private JPanel createAntiCheatCard() {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel icon = new JLabel("◎");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        icon.setForeground(ACCENT);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("ANTI-CHEAT VANGUARD");
        title.setFont(new Font("Consolas", Font.BOLD, 11));
        title.setForeground(TEXT);
        JLabel sub = new JLabel("Kernel integrity check pass");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        sub.setForeground(TEXT_DIM);
        textPanel.add(title);
        textPanel.add(sub);

        JLabel armed = new JLabel("ARMED");
        armed.setFont(new Font("Consolas", Font.BOLD, 10));
        armed.setForeground(GREEN);
        armed.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 80, 60)),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)));

        card.add(icon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(armed, BorderLayout.EAST);
        return card;
    }

    // ---- FOOTER ----
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(PANEL_BG);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, CARD_BORDER),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)));

        JLabel copy = new JLabel("© 2025 GAMEX INC. TACTICAL CORE ARCHITECTURE  /  ALL RIGHTS RESERVED");
        copy.setFont(new Font("Consolas", Font.PLAIN, 9));
        copy.setForeground(new Color(70, 75, 100));

        JPanel links = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        links.setOpaque(false);
        for (String l : new String[]{"Privacy Protocol","EULA Mesh","Security Beacon","API Status"}) {
            JLabel lbl = new JLabel(l);
            lbl.setFont(new Font("Consolas", Font.PLAIN, 9));
            lbl.setForeground(TEXT_DIM);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            links.add(lbl);
        }

        footer.add(copy, BorderLayout.WEST);
        footer.add(links, BorderLayout.EAST);
        return footer;
    }

    // ---- HELPERS ----
    private JPanel createSocialButton(String name, String sub) {
        JPanel btn = new JPanel();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBackground(CARD_BG);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(10, 8, 8, 8)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(30, 33, 55)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(CARD_BG); }
        });

        JLabel icon = new JLabel(name.substring(0, 1));
        icon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        icon.setForeground(ACCENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel n = new JLabel(name, SwingConstants.CENTER);
        n.setFont(new Font("Segoe UI", Font.BOLD, 11));
        n.setForeground(TEXT);
        n.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel s = new JLabel(sub, SwingConstants.CENTER);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        s.setForeground(TEXT_DIM);
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.add(icon);
        btn.add(n);
        btn.add(s);
        return btn;
    }

    private JTextField createCyberField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Consolas", Font.PLAIN, 12));
        field.setBackground(FIELD_BG);
        field.setForeground(ACCENT);
        field.setCaretColor(ACCENT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setText(placeholder);
        field.setForeground(new Color(80, 90, 120));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(ACCENT);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(80, 90, 120));
                }
            }
        });
        return field;
    }

    // ---- LOGIN LOGIC ----
    private void handleLogin() {
        String email = emailField.getText().trim();
        if (email.isEmpty() || email.equals(EMAIL_PH)) email = "";
        String password = passwordRow.getPassword();

        errorMessageLabel.setText("");

        String err = LoginValidator.validateLoginCredentials(email, password);
        if (!err.isEmpty()) {
            errorMessageLabel.setText(err);
            if (LoginValidator.isValidEmail(email))
                passwordRow.getField().requestFocusInWindow();
            else
                emailField.requestFocusInWindow();
            return;
        }

        Player player = playerManager.getPlayer(email);
        if (player == null) {
            player = playerManager.createPlayer(email);
            if (player == null) {
                errorMessageLabel.setText("Email already registered");
                return;
            }
        }

        playerManager.setCurrentPlayer(email);
        if (loginCallback != null) loginCallback.onLoginSuccess(player);
        dispose();
    }

    public void showError(String message) {
        errorMessageLabel.setText(message);
    }

    public void clearFields() {
        emailField.setText(EMAIL_PH);
        emailField.setForeground(new Color(80, 90, 120));
        passwordRow.clear();
        errorMessageLabel.setText("");
    }

    public static void main(String[] args) {
        GameLauncher.main(args);
    }
}
