package Trunk;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static Trunk.Constant.CURRENTUSER;

public class TextEdit extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JPanel bp;
    private JButton save;
    private JButton exit;

    private String name;
    private String text;


    public TextEdit(String text,String name) {

        this.text = text;
        this.name = name;

        Container cp = this.getContentPane();
        textArea = new JTextArea(15, 30);
        textArea.setLineWrap(true);
        textArea.setAutoscrolls(true);

        textArea.setLayout(new BorderLayout());
        textArea.setText(text);
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(textArea));
        cp.add(panel, BorderLayout.CENTER);
        save = new JButton("SAVE");
        exit = new JButton("EXIT");
        save.addActionListener(this);
        exit.addActionListener(this);
        this.setTitle(name);
        bp = new JPanel();
        bp.add(save);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        cp.add(bp, BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
    } //实现文本编辑器

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == save) {
            if (textArea.getText().length() > Constant.FILESIZE * Constant.FILE_MAX_BLOCK) {
                // 文本过长
                int x = Constant.FILESIZE * Constant.FILE_MAX_BLOCK;
                JOptionPane.showMessageDialog(null, "超出字数 (" + textArea.getText().length() + "/["
                        + x + "])！");

            } else {
                    if (textArea.getText().length() < 1){
                        CURRENTUSER.addMsg(0,name, " ");
                    }else {
                        CURRENTUSER.addMsg(0,name, textArea.getText());
                    }
                    this.dispose();
            }
        }

    } //监听save按键
}