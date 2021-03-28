package com.aprilsoft;

import java.awt.EventQueue;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PuhClient extends JFrame {

    private JPanel contentPane;
    private JTable table;


    private String doGet() {
        String res = "?";

        for (int i = 0; i < 10; i++) {
            res += ("tov" + (i + 1) + "=");

            int kol;
            try {
                String str = table.getValueAt(i, 3).toString().trim();
                kol = Integer.parseInt(str);
            } catch (Exception e) {
                kol = 0;
            }
            if (kol >= 0) {
                res += "" + kol + "&";
            } else res += "0&";

        }

        return res;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PuhClient frame = new PuhClient();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PuhClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 762, 544);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(77, 86, 594, 284);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 17));
        table.setForeground(Color.BLUE);
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                        {new Integer(1), "\u041C\u0435\u0434", null, null},
                        {new Integer(2), "\u041F\u0440\u043E\u043F\u043E\u043B\u0438\u0441", null, null},
                        {new Integer(3), "\u0412\u043E\u0441\u043A \u043F\u0447\u0435\u043B\u0438\u043D\u044B\u0439", null, null},
                        {new Integer(4), "\u041F\u0435\u0440\u0433\u0430\u0426\u0432\u0435\u0442\u043E\u0447\u043D\u0430\u044F \u043F\u044B\u043B\u044C\u0446\u0430", null, null},
                        {new Integer(5), "\u041F\u0435\u0440\u0433\u0430", null, null},
                        {new Integer(6), "\u041C\u0430\u0442\u043E\u0447\u043D\u043E\u0435 \u043C\u043E\u043B\u043E\u0447\u043A\u043E", null, null},
                        {new Integer(7), "\u0422\u0440\u0443\u0442\u043D\u0435\u0432\u043E\u0435 \u043C\u043E\u043B\u043E\u0447\u043A\u043E", null, null},
                        {new Integer(8), "\u041F\u0447\u0435\u043B\u0438\u043D\u044B\u0439 \u044F\u0434", null, null},
                        {new Integer(9), "\u041F\u0447\u0435\u043B\u0438\u043D\u0430\u044F \u043E\u0433\u043D\u0435\u0432\u043A\u0430", null, null},
                        {new Integer(10), "\u041F\u0447\u0435\u043B\u0438\u043D\u044B\u0439 \u043F\u043E\u0434\u043C\u043E\u0440", null, null},
                },
                new String[] {
                        "\u2116", "\u041D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435 \u0442\u043E\u0432\u0430\u0440\u0430", "\u041E\u0441\u0442\u0430\u0442\u043E\u043A \u043D\u0430 \u0441\u043A\u043B\u0430\u0434\u0435", "\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0441\u0442\u0443\u043F\u043B\u0435\u043D\u0438\u044F"
                }
        ) {
            Class[] columnTypes = new Class[] {
                    Integer.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                    true, true, false, true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(171);
        table.getColumnModel().getColumn(2).setPreferredWidth(136);
        table.getColumnModel().getColumn(3).setPreferredWidth(179);

        JButton btnNewButton = new JButton("\u0412\u044B\u043F\u043E\u043B\u043D\u0438\u0442\u044C");
        btnNewButton.setBackground(Color.GREEN);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String str = "http://puh/tovar.php" + doGet();
                System.out.println(str);
                boolean flag = false;
                try {
                    URL url = new URL(str);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine = in.readLine().trim();
                    System.out.println("input: " + inputLine);
                    if (inputLine.indexOf('~') >= 0) {
                        flag = true;
                        String[] mas = inputLine.split("~");

                        for (int i = 0; i < mas.length; i++) {
                            table.setValueAt(mas[i], i, 2);
                        }
                    }

                    in.close();
                    conn.disconnect();
                    conn = null;
                } catch (Exception e) {
                    System.out.println(e);
                    if (!flag) {
                        JOptionPane.showMessageDialog(null, "May be no Internet Connection", "Sending data error", 0);
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(298, 416, 202, 25);
        contentPane.add(btnNewButton);
    }
}