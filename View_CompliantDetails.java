

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class View_CompliantDetails {
    static JFrame frame;
    static controller cont;
    public View_CompliantDetails(controller c, String ID) {
        cont = c;
        frame = new JFrame("Complaint Details");
        frame.setSize(640, 480);
        frame.setLayout(null);

        JLabel lb1 = new JLabel("Compliant Details");
        lb1.setBounds(12, 0, 128, 24);
        frame.add(lb1);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayCompliant(ID);
        frame.setVisible(true);
    }

    static void displayCompliant(String ID){
        try (BufferedReader br = new BufferedReader(new
        FileReader("CSV/Compliants.csv"))) {
            String line;
            String Labelinfo = "";
            String Labelcomp = "";
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!values[0].equals(ID)) continue;

                for (int i = 0; i < values.length; i++) {
                    if (i == 4) continue;
                    if (i == 1) {
                        try (BufferedReader br2 = new BufferedReader(new
                        FileReader("CSV/Stalls.csv"))) {
                            while ((line = br2.readLine()) != null) {
                                String[] values2 = line.split(",");
                                if (values[1].equals(values2[0])) {
                                    Labelinfo += values2[1];
                                    Labelinfo += "    ";
                                }
                            }
                        }
                    }
                    else if (i == 5) {
                        boolean b = Boolean.parseBoolean(values[5]);
                        if (b)
                            Labelinfo += "Answered";
                        else
                            Labelinfo += "Awaiting";
                        Labelinfo += "    ";
                    } 
                    else{
                        Labelinfo += values[i];
                        Labelinfo += "    ";
                    }
                }
                Labelcomp = values[4];
                break;
            }
            JLabel lb = new JLabel(Labelinfo);
            lb.setBounds(12, 60, 500, 32);
            JLabel lb2 = new JLabel(Labelcomp);
            lb2.setBounds(48, 86, 500, 128);

            try (BufferedReader br2 = new BufferedReader(new FileReader("CSV/Responses.csv"))) {
                String line2;
                int n = 0;
                while ((line2 = br2.readLine()) != null) {
                    String[] values = line2.split(",");
                    if (!ID.equals(values[0]))
                        continue;

                    String fullLabel = values[2] + "    " + values[1];
                    JLabel lbr = new JLabel(fullLabel);
                    lbr.setBounds(12, 200 + n * 42, 500, 32);
                    frame.add(lbr);
                    n += 1;
                }
            }
            
            JButton btn = new JButton("Respond");
            btn.setBounds(400, 360, 128, 48);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cont.OpenCompliantResponse(ID);
                    frame.dispose();
                }
            });
            JButton btn2 = new JButton("Return");
            btn2.setBounds(500, 12, 100, 32);
            btn2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cont.OpenCompliantList();
                    frame.dispose();
                }
            });
            frame.add(lb);
            frame.add(lb2);
            frame.add(btn);
            frame.add(btn2);
            
        }
        catch (FileNotFoundException e) {System.out.println("FileNotFoundException");} 
        catch (IOException e) {System.out.println("IOException");}
    }
}
