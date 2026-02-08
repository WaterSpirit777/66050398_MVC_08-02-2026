

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class View_ComplaintList {
    static JFrame frame;
    static controller cont;
    public View_ComplaintList(controller c){
        cont = c;
        frame = new JFrame("Complaint List");
        frame.setSize(640, 640);
        frame.setLayout(null);

        JLabel lb1 = new JLabel("Compliants");
        lb1.setBounds(12, 0, 64, 24);
        frame.add(lb1);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayCompliants();
        frame.setVisible(true);
    }

    static void displayCompliants(){
        try (BufferedReader br = new BufferedReader(new
        FileReader("CSV/Compliants.csv"))) {
            String line;
            int n = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(n);
                String[] values = line.split(",");
                String fullLabel = "";
                for (int i = 0; i < values.length; i++) {
                    if (i == 4 || i == 0) continue;
                    if (i == 1) {
                        try (BufferedReader br2 = new BufferedReader(new FileReader("CSV/Stalls.csv"))) {
                            String name;
                            while ((line = br2.readLine()) != null) {
                                String[] values2 = line.split(",");
                                if (values[1].equals(values2[0])) {
                                    fullLabel += values2[1];
                                    fullLabel += "    ";
                                }
                            }
                        }
                    }
                    else if (i == 5) {
                        boolean b = Boolean.parseBoolean(values[5]);
                        if (b)
                            fullLabel += "Answered";
                        else
                            fullLabel += "Awaiting";
                        fullLabel += "    ";
                    }
                    else {
                        fullLabel += values[i];
                        fullLabel += "    ";
                    }
                }
                int responsecount = 0;
                try (BufferedReader br2 = new BufferedReader(new FileReader("CSV/Responses.csv"))) {
                    while ((line = br2.readLine()) != null) {
                        String[] values2 = line.split(",");
                        if (values[0].equals(values2[0])) {
                            responsecount += 1;
                        }
                    }
                }
                fullLabel += "response:" + responsecount;
                fullLabel += "    ";

                JLabel lb = new JLabel(fullLabel);
                lb.setBounds(12, 60 + n*42, 500, 32);
                JButton btn = new JButton("Details");
                btn.setBounds(472, 60 + n*42, 108, 32);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cont.OpenCompliantDetails(values[0]);
                        frame.dispose();
                    }
                });
                JButton btn2 = new JButton("Stall Lists");
                btn2.setBounds(500, 12, 140, 32);
                btn2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cont.OpenRestaurantDetail();
                        frame.dispose();
                    }
                });
                frame.add(lb);
                frame.add(btn);
                frame.add(btn2);
                n += 1;
            }
        }
        catch (FileNotFoundException e) {System.out.println("FileNotFoundException");} 
        catch (IOException e) {System.out.println("IOException");}
    }
}
