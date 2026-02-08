import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class View_RestaurantDetails {
    static JFrame frame;
    static controller cont;

    public View_RestaurantDetails(controller c) {
        cont = c;
        frame = new JFrame("Stall List");
        frame.setSize(640, 640);
        frame.setLayout(null);

        JLabel lb1 = new JLabel("Stalls");
        lb1.setBounds(12, 0, 64, 24);
        frame.add(lb1);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayCompliants();
        frame.setVisible(true);
    }

    static void displayCompliants() {
        try (BufferedReader br = new BufferedReader(new FileReader("CSV/Stalls.csv"))) {
            String line;
            int n = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(n);
                String[] values = line.split(",");
                String fullLabel = "";
                for (int i = 0; i < values.length; i++) {
                    if (i == 2) {
                        try (BufferedReader br2 = new BufferedReader(new FileReader("CSV/Canteens.csv"))) {
                            while ((line = br2.readLine()) != null) {
                                String[] values2 = line.split(",");
                                if (values[1].equals(values2[0])) {
                                    fullLabel += values2[1];
                                    fullLabel += "    ";
                                }
                            }
                        }
                    } else {
                        fullLabel += values[i];
                        fullLabel += "    ";
                    }
                }
                int compliantcount = 0;
                try (BufferedReader br2 = new BufferedReader(new FileReader("CSV/Compliants.csv"))) {
                    while ((line = br2.readLine()) != null) {
                        String[] values2 = line.split(",");
                        if (values[0].equals(values2[1])) {
                            compliantcount += 1;
                        }
                    }
                }
                fullLabel += "compliants:" + compliantcount;
                fullLabel += "    ";

                JLabel lb = new JLabel(fullLabel);
                lb.setBounds(12, 60 + n * 42, 500, 32);
                JButton btn = new JButton("Return");
                btn.setBounds(500, 12, 100, 32);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cont.OpenCompliantList();
                        frame.dispose();
                    }
                });
                frame.add(lb);
                frame.add(btn);
                n += 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
