import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class model {
    public static void WriteResponse(String complaintID, String response) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        try (PrintWriter pw1 = new PrintWriter(new FileWriter("CSV/Responses.csv", true))) {
            pw1.write(complaintID + ",");
            pw1.write(response + ",");
            pw1.write(df.format(date) + ",");
            pw1.println();
            pw1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public static void UpdateStatus(String complaintID) {
        List<List<String>> fulltexts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CSV/Compliants.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                fulltexts.add(Arrays.asList(values));
            }
            PrintWriter pw = new PrintWriter("CSV/Compliants.csv");
            for (List<String> list : fulltexts){
                if (list.get(0).equals(complaintID))
                    list.set(5, "true");
                for (String text : list) {
                    pw.write(text + ",");
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

// สร้างฐานข้อมูล
    public static void WriteComplaint(String restauID, String compliant, String complType) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        try (PrintWriter pw1 = new PrintWriter(new FileWriter("CSV/Compliants.csv", true))) {
            String newComplaintID = "0";
            try (BufferedReader br = new BufferedReader(new FileReader("CSV/Compliants.csv"))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    newComplaintID = values[0];
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            newComplaintID = String.valueOf(Integer.parseInt(newComplaintID) + 1);
            pw1.write(newComplaintID + ",");
            pw1.write(restauID + ",");
            pw1.write(df.format(date) + ",");
            pw1.write(complType + ",");
            pw1.write(compliant + ",");
            pw1.write(String.valueOf(false) + ",");
            pw1.println();
            pw1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public static void WriteNewCanteen(String ID, String name, String spot) {
        try (PrintWriter pw1 = new PrintWriter(new FileWriter("CSV/Canteens.csv", true))) {
            pw1.write(ID + ",");
            pw1.write(name + ",");
            pw1.write(spot + ",");
            pw1.println();
            pw1.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public static void WriteNewStall(String ID, String name, String canteen) {
        try (PrintWriter pw1 = new PrintWriter(new FileWriter("CSV/Stalls.csv", true))) {
            pw1.write(ID + ",");
            pw1.write(name + ",");
            pw1.write(canteen + ",");
            pw1.println();
            pw1.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
