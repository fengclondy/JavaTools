package utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description ����ѡ���ļ���
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-15 16:33
 */
public class ChooseFolder extends JFrame implements ActionListener{

    JPanel pnlMain;
    JTextField txtfile;
    JButton btnSelect;
    JFileChooser fc = new JFileChooser();

    public static String folderPath ;

    public ChooseFolder() {
        pnlMain = new JPanel();
        this.getContentPane().add(pnlMain);
        txtfile = new JTextField(10);
        btnSelect = new JButton("ѡ��");
        btnSelect.addActionListener(this);
        pnlMain.add(txtfile);
        pnlMain.add(btnSelect);
    }

    @Override
    public  void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelect) {
            /**
             * ������Ϊ��Ҫ�ġ���ΪJFileChooserĬ�ϵ���ѡ���ļ�������ҪѡĿ¼�� ��Ҫ��DIRECTORIES_ONLYװ��ģ��
             * ���⣬��ѡ���ļ���������˾�
             */
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int intRetVal = fc.showOpenDialog(this);
            if (intRetVal == JFileChooser.APPROVE_OPTION) {
                txtfile.setText(fc.getSelectedFile().getPath());
                System.out.println("�ļ��У�" + fc.getSelectedFile().getPath());
                folderPath = fc.getSelectedFile().getPath();
            }
        }
    }

    public static String getFolderPath(){
        return folderPath;
    }


    public static void main(String[] args) {
        JFrame f = new ChooseFolder();
        f.setSize(200, 300);
        f.setVisible(true);
    }
}
