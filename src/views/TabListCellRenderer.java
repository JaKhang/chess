package views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.StringTokenizer;

public class TabListCellRenderer extends JLabel implements ListCellRenderer {
    protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    protected FontMetrics m_fm = null;

    public TabListCellRenderer() {
        super();
        setOpaque(true);
        setBorder(m_noFocusBorder);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        setBackground(isSelected ? list.getSelectionBackground() : list
                .getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list
                .getForeground());

        setFont(list.getFont());
        setBorder((cellHasFocus) ? UIManager
                .getBorder("List.focusCellHighlightBorder") : m_noFocusBorder);

        return this;
    }

    public void paint(Graphics g) {
        m_fm = g.getFontMetrics();

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g.setFont(getFont());
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top + m_fm.getAscent();

        StringTokenizer st = new StringTokenizer(getText(), "\t");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            g.drawString(str, x, y);
            //insert distance for each tab
            x += m_fm.stringWidth(str) + 50;

            if (!st.hasMoreTokens())
                break;
        }
    }
}