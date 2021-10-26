/*
*By: Caesar W. & Obinna A
*Class: JAVA 1
*Instructor: Dr.Primo
*Assignment: Group Project
*Date: 12/05/2018
*Due: 12/06/2018 @9:30AM
*/
//START
package crwh_mycalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

class CalendarDataManager {

    static final int CAL_WIDTH = 7;
    final static int CAL_HEIGHT = 6;
    int crwhCalendarDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
    int obiCalendarYear;
    int crwhCalendarMonth;
    int obiDaysOfTheMonth;
    final int crwhLastDayOfMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int obiLastDayToday;
    Calendar crwhPresentDay = Calendar.getInstance();
    Calendar obiCalendar;

    public CalendarDataManager() {
        setToday();
    }

    public void setToday() {
        obiCalendarYear = crwhPresentDay.get(Calendar.YEAR);
        crwhCalendarMonth = crwhPresentDay.get(Calendar.MONTH);
        obiDaysOfTheMonth = crwhPresentDay.get(Calendar.DAY_OF_MONTH);
        makeCalData(crwhPresentDay);
    }

    private void makeCalData(Calendar cal) {

        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
        if (crwhCalendarMonth == 1) {
            obiLastDayToday = crwhLastDayOfMonth[crwhCalendarMonth] + leapCheck(obiCalendarYear);
        } else {
            obiLastDayToday = crwhLastDayOfMonth[crwhCalendarMonth];
        }

        for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
                crwhCalendarDates[i][j] = 0;
            }
        }

        for (int i = 0, num = 1, k = 0; i < CAL_HEIGHT; i++) {
            if (i == 0) {
                k = calStartingPos;
            } else {
                k = 0;
            }
            for (int j = k; j < CAL_WIDTH; j++) {
                if (obiLastDayToday > num) {
                    crwhCalendarDates[i][j] = num++;
                } else {
                }
            }
        }
    }

    private int leapCheck(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void moveMonth(int mon) {
        crwhCalendarMonth += mon;
        if (crwhCalendarMonth > 11) {
            while (crwhCalendarMonth > 11) {
                obiCalendarYear++;
                crwhCalendarMonth -= 12;
            }
        } else if (crwhCalendarMonth < 0) {
            while (crwhCalendarMonth < 0) {
                obiCalendarYear--;
                crwhCalendarMonth += 12;
            }
        }
        obiCalendar = new GregorianCalendar(obiCalendarYear, crwhCalendarMonth, obiDaysOfTheMonth);
        makeCalData(obiCalendar);
    }
}

public class CRWH_MyCalendar extends CalendarDataManager {

    JFrame CRWHMain;

    JPanel ObiCalOPPanel;
    JButton crwhTodayBtn;
    JLabel ObiTodayLabs;
    JButton crwhYearBtn;
    JButton oniMonBtn;
    JLabel crwhMonthYearLab;
    JButton obiMonBtn;
    JButton crwhYearButton;
    ListenForCalOpButtons obiListenForCalOpBtns = new ListenForCalOpButtons();

    JPanel crwhCalPanel;
    JButton obiWeekDaysName[];
    JButton crwhDateBtns[][] = new JButton[6][7];
    listenForDateButs obiForDateBtns = new listenForDateButs();

    JPanel crwhInfo;
    JLabel obiClock;

    JPanel crwhMemoPanel;
    JLabel obiDate;
    JTextArea crwhMemoArea;
    JScrollPane obiMemoAreaScrPane;
    JPanel crwhMmemoSubPanel;
    JButton obiSaveBtn;
    JButton crwhDeleteBtn;
    JButton cobiClearOutBtn;

    JPanel crwhFrameBottomPanel;
    JLabel obiBottomInfo = new JLabel("Welcome to the Centurion Memo Calendar");

    final String WEEK_DAY_NAME[] = {"SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT"};
    final String crwhTitle = "CENTURION Memo v0.1";
    final String obiSaveBtnMsg1 = "MemoData";
    final String crwhSaveBtnMsg2 = "Something needs to be placed inside to be saved.";
    final String obiSaveBtnMsg3 = "ERROR";
    final String crwhDeleteBtnMsg1 = "MemoData Deleted.";
    final String obiDeleteBtnMsg2 = "There is no MemoData that needs to be deleted.";
    final String crwhDeleteBtnMsg3 = "ERROR";
    final String obiClearBtnMsg1 = "MemoData has now been cleared!";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CRWH_MyCalendar();
            }
        });
    }

    public CRWH_MyCalendar() {

        CRWHMain = new JFrame(crwhTitle);
        CRWHMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CRWHMain.setSize(700, 400);
        CRWHMain.setLocationRelativeTo(null);
        CRWHMain.setResizable(false);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(CRWHMain);
        } catch (Exception e) {
            obiBottomInfo.setText("ERROR: The 'Look And Feel' appeal setting, has failed");
        }

        ObiCalOPPanel = new JPanel();
        crwhTodayBtn = new JButton("Today");
        crwhTodayBtn.setToolTipText("Today");
        crwhTodayBtn.addActionListener(obiListenForCalOpBtns);
        ObiTodayLabs = new JLabel(crwhPresentDay.get(Calendar.MONTH) + 1 + "/" + crwhPresentDay.get(Calendar.DAY_OF_MONTH) + "/" + crwhPresentDay.get(Calendar.YEAR));
        crwhYearBtn = new JButton("<<");
        crwhYearBtn.setToolTipText("Previous Year");
        crwhYearBtn.addActionListener(obiListenForCalOpBtns);
        oniMonBtn = new JButton("<");
        oniMonBtn.setToolTipText("Previous Month");
        oniMonBtn.addActionListener(obiListenForCalOpBtns);
        crwhMonthYearLab = new JLabel("<html><table width=100><tr><th><font size=5>" + ((crwhCalendarMonth + 1) < 10 ? "&nbsp;" : "") + (crwhCalendarMonth + 1) + " / " + obiCalendarYear + "</th></tr></table></html>");
        obiMonBtn = new JButton(">");
        obiMonBtn.setToolTipText("Next Month");
        obiMonBtn.addActionListener(obiListenForCalOpBtns);
        crwhYearButton = new JButton(">>");
        crwhYearButton.setToolTipText("Next Year");
        crwhYearButton.addActionListener(obiListenForCalOpBtns);
        ObiCalOPPanel.setLayout(new GridBagLayout());
        GridBagConstraints calOpGC = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1;
        calOpGC.weighty = 1;
        calOpGC.insets = new Insets(5, 5, 0, 0);
        calOpGC.anchor = GridBagConstraints.WEST;
        calOpGC.fill = GridBagConstraints.NONE;
        ObiCalOPPanel.add(crwhTodayBtn, calOpGC);
        calOpGC.gridwidth = 3;
        calOpGC.gridx = 2;
        calOpGC.gridy = 1;
        ObiCalOPPanel.add(ObiTodayLabs, calOpGC);
        calOpGC.anchor = GridBagConstraints.CENTER;
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 1;
        calOpGC.gridy = 2;
        ObiCalOPPanel.add(crwhYearBtn, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 2;
        ObiCalOPPanel.add(oniMonBtn, calOpGC);
        calOpGC.gridwidth = 2;
        calOpGC.gridx = 3;
        calOpGC.gridy = 2;
        ObiCalOPPanel.add(crwhMonthYearLab, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 5;
        calOpGC.gridy = 2;
        ObiCalOPPanel.add(obiMonBtn, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 2;
        ObiCalOPPanel.add(crwhYearButton, calOpGC);

        crwhCalPanel = new JPanel();
        obiWeekDaysName = new JButton[7];
        for (int i = 0; i < CAL_WIDTH; i++) {
            obiWeekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
            obiWeekDaysName[i].setBorderPainted(false);
            obiWeekDaysName[i].setContentAreaFilled(false);
            obiWeekDaysName[i].setForeground(Color.WHITE);
            switch (i) {
                case 0:
                    obiWeekDaysName[i].setBackground(new Color(200, 50, 50));
                    break;
                case 6:
                    obiWeekDaysName[i].setBackground(new Color(50, 100, 200));
                    break;
                default:
                    obiWeekDaysName[i].setBackground(new Color(150, 150, 150));
                    break;
            }
            obiWeekDaysName[i].setOpaque(true);
            obiWeekDaysName[i].setFocusPainted(false);
            crwhCalPanel.add(obiWeekDaysName[i]);
        }
        for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
                crwhDateBtns[i][j] = new JButton();
                crwhDateBtns[i][j].setBorderPainted(false);
                crwhDateBtns[i][j].setContentAreaFilled(false);
                crwhDateBtns[i][j].setBackground(Color.WHITE);
                crwhDateBtns[i][j].setOpaque(true);
                crwhDateBtns[i][j].addActionListener(obiForDateBtns);
                crwhCalPanel.add(crwhDateBtns[i][j]);
            }
        }
        crwhCalPanel.setLayout(new GridLayout(0, 7, 2, 2));
        crwhCalPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        showCal();

        crwhInfo = new JPanel();
        crwhInfo.setLayout(new BorderLayout());
        obiClock = new JLabel("", SwingConstants.RIGHT);
        obiClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        crwhInfo.add(obiClock, BorderLayout.NORTH);
        obiDate = new JLabel("<Html><font size=3>" + (crwhPresentDay.get(Calendar.MONTH) + 1) + "/" + crwhPresentDay.get(Calendar.DAY_OF_MONTH) + "/" + crwhPresentDay.get(Calendar.YEAR) + "&nbsp;(Today)</html>", SwingConstants.LEFT);
        obiDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        crwhMemoPanel = new JPanel();
        crwhMemoPanel.setBorder(BorderFactory.createTitledBorder("C.R.W.H. Memo"));
        crwhMemoArea = new JTextArea();
        crwhMemoArea.setLineWrap(true);
        crwhMemoArea.setWrapStyleWord(true);
        obiMemoAreaScrPane = new JScrollPane(crwhMemoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readMemo();

        crwhMmemoSubPanel = new JPanel();
        obiSaveBtn = new JButton("Save");
        obiSaveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    File f = new File("MemoData");
                    if (!f.isDirectory()) {
                        f.mkdir();
                    }

                    String memo = crwhMemoArea.getText();
                    if (memo.length() > 0) {
                        BufferedWriter out = new BufferedWriter(new FileWriter("MemoData/" + obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (obiDaysOfTheMonth < 10 ? "0" : "") + obiDaysOfTheMonth + ".txt"));
                        String str = crwhMemoArea.getText();
                        out.write(str);
                        out.close();
                        obiBottomInfo.setText(obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (obiDaysOfTheMonth < 10 ? "0" : "") + obiDaysOfTheMonth + ".txt" + obiSaveBtnMsg1);
                    } else {
                        obiBottomInfo.setText(crwhSaveBtnMsg2);
                    }
                } catch (IOException e) {
                    obiBottomInfo.setText(obiSaveBtnMsg3);
                }
                showCal();
            }
        });
        crwhDeleteBtn = new JButton("Delete");
        crwhDeleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crwhMemoArea.setText("");
                File f = new File("MemoData/" + obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (obiDaysOfTheMonth < 10 ? "0" : "") + obiDaysOfTheMonth + ".txt");
                if (f.exists()) {
                    f.delete();
                    showCal();
                    obiBottomInfo.setText(crwhDeleteBtnMsg1);
                } else {
                    obiBottomInfo.setText(obiDeleteBtnMsg2);
                }
            }
        });
        cobiClearOutBtn = new JButton("Clear");
        cobiClearOutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                crwhMemoArea.setText(null);
                obiBottomInfo.setText(obiClearBtnMsg1);
            }
        });
        crwhMmemoSubPanel.add(obiSaveBtn);
        crwhMmemoSubPanel.add(crwhDeleteBtn);
        crwhMmemoSubPanel.add(cobiClearOutBtn);
        crwhMemoPanel.setLayout(new BorderLayout());
        crwhMemoPanel.add(obiDate, BorderLayout.NORTH);
        crwhMemoPanel.add(obiMemoAreaScrPane, BorderLayout.CENTER);
        crwhMemoPanel.add(crwhMmemoSubPanel, BorderLayout.SOUTH);

        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = ObiCalOPPanel.getPreferredSize();
        calOpPanelSize.height = 90;
        ObiCalOPPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(ObiCalOPPanel, BorderLayout.NORTH);
        frameSubPanelWest.add(crwhCalPanel, BorderLayout.CENTER);

        JPanel frameSubPanelEast = new JPanel();
        Dimension infoPanelSize = crwhInfo.getPreferredSize();
        infoPanelSize.height = 65;
        crwhInfo.setPreferredSize(infoPanelSize);
        frameSubPanelEast.setLayout(new BorderLayout());
        frameSubPanelEast.add(crwhInfo, BorderLayout.NORTH);
        frameSubPanelEast.add(crwhMemoPanel, BorderLayout.CENTER);

        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWestSize.width = 410;
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

        crwhFrameBottomPanel = new JPanel();
        crwhFrameBottomPanel.add(obiBottomInfo);

        CRWHMain.setLayout(new BorderLayout());
        CRWHMain.add(frameSubPanelWest, BorderLayout.WEST);
        CRWHMain.add(frameSubPanelEast, BorderLayout.CENTER);
        CRWHMain.add(crwhFrameBottomPanel, BorderLayout.SOUTH);
        CRWHMain.setVisible(true);

        focusToday();
        ThreadConrol threadCnl = new ThreadConrol();
        threadCnl.start();
    }

    private void focusToday() {
        if (crwhPresentDay.get(Calendar.DAY_OF_WEEK) == 1) {
            crwhDateBtns[crwhPresentDay.get(Calendar.WEEK_OF_MONTH)][crwhPresentDay.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
        } else {
            crwhDateBtns[crwhPresentDay.get(Calendar.WEEK_OF_MONTH) - 1][crwhPresentDay.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
        }
    }

    private void readMemo() {
        try {
            File f = new File("MemoData/" + obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (obiDaysOfTheMonth < 10 ? "0" : "") + obiDaysOfTheMonth + ".txt");
            if (f.exists()) {
                BufferedReader in = new BufferedReader(new FileReader("MemoData/" + obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (obiDaysOfTheMonth < 10 ? "0" : "") + obiDaysOfTheMonth + ".txt"));
                String memoAreaText = new String();
                while (true) {
                    String tempStr = in.readLine();
                    if (tempStr == null) {
                        break;
                    }
                    memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
                }
                crwhMemoArea.setText(memoAreaText);
                in.close();
            } else {
                crwhMemoArea.setText("");
            }
        } catch (IOException e) {
        }
    }

    private void showCal() {
        for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
                String fontColor = "black";
                if (j == 0) {
                    fontColor = "red";
                } else if (j == 6) {
                    fontColor = "blue";
                }

                File f = new File("MemoData/" + obiCalendarYear + ((crwhCalendarMonth + 1) < 10 ? "0" : "") + (crwhCalendarMonth + 1) + (crwhCalendarDates[i][j] < 10 ? "0" : "") + crwhCalendarDates[i][j] + ".txt");
                if (f.exists()) {
                    crwhDateBtns[i][j].setText("<html><b><font color=" + fontColor + ">" + crwhCalendarDates[i][j] + "</font></b></html>");
                } else {
                    crwhDateBtns[i][j].setText("<html><font color=" + fontColor + ">" + crwhCalendarDates[i][j] + "</font></html>");
                }

                JLabel todayMark = new JLabel("<html><font color=green>*</html>");
                crwhDateBtns[i][j].removeAll();
                if (crwhCalendarMonth == crwhPresentDay.get(Calendar.MONTH)
                        && obiCalendarYear == crwhPresentDay.get(Calendar.YEAR)
                        && crwhCalendarDates[i][j] == crwhPresentDay.get(Calendar.DAY_OF_MONTH)) {
                    crwhDateBtns[i][j].add(todayMark);
                    crwhDateBtns[i][j].setToolTipText("Today");
                }

                if (crwhCalendarDates[i][j] != 0) {
                    crwhDateBtns[i][j].setVisible(true);
                } else {
                    crwhDateBtns[i][j].setVisible(false);
                }
            }
        }
    }

    private class ListenForCalOpButtons implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == crwhTodayBtn) {
                setToday();
                obiForDateBtns.actionPerformed(e);
                focusToday();
            } else if (e.getSource() == crwhYearBtn) {
                moveMonth(-12);
            } else if (e.getSource() == oniMonBtn) {
                moveMonth(-1);
            } else if (e.getSource() == obiMonBtn) {
                moveMonth(1);
            } else if (e.getSource() == crwhYearButton) {
                moveMonth(12);
            }

            crwhMonthYearLab.setText("<html><table width=100><tr><th><font size=5>" + ((crwhCalendarMonth + 1) < 10 ? "&nbsp;" : "") + (crwhCalendarMonth + 1) + " / " + obiCalendarYear + "</th></tr></table></html>");
            showCal();
        }
    }

    private class listenForDateButs implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int k = 0, l = 0;
            for (int i = 0; i < CAL_HEIGHT; i++) {
                for (int j = 0; j < CAL_WIDTH; j++) {
                    if (e.getSource() == crwhDateBtns[i][j]) {
                        k = i;
                        l = j;
                    }
                }
            }

            if (!(k == 0 && l == 0)) {
                obiDaysOfTheMonth = crwhCalendarDates[k][l];
            }

            obiCalendar = new GregorianCalendar(obiCalendarYear, crwhCalendarMonth, obiDaysOfTheMonth);

            String dDayString = new String();
            int dDay = ((int) ((obiCalendar.getTimeInMillis() - crwhPresentDay.getTimeInMillis()) / 1000 / 60 / 60 / 24));
            if (dDay == 0 && (obiCalendar.get(Calendar.YEAR) == crwhPresentDay.get(Calendar.YEAR))
                    && (obiCalendar.get(Calendar.MONTH) == crwhPresentDay.get(Calendar.MONTH))
                    && (obiCalendar.get(Calendar.DAY_OF_MONTH) == crwhPresentDay.get(Calendar.DAY_OF_MONTH))) {
                dDayString = "Today";
            } else if (dDay >= 0) {
                dDayString = "D-" + (dDay + 1);
            } else if (dDay < 0) {
                dDayString = "D+" + (dDay) * (-1);
            }

            obiDate.setText("<Html><font size=3>" + (crwhCalendarMonth + 1) + "/" + obiDaysOfTheMonth + "/" + obiCalendarYear + "&nbsp;(" + dDayString + ")</html>");

            readMemo();
        }
    }

    private class ThreadConrol extends Thread {

        public void run() {
            boolean msgCntFlag = false;
            int num = 0;
            String curStr = new String();
            while (true) {
                try {
                    crwhPresentDay = Calendar.getInstance();
                    String amPm = (crwhPresentDay.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
                    String hour;
                    switch (crwhPresentDay.get(Calendar.HOUR)) {
                        case 0:
                            hour = "12";
                            break;
                        case 12:
                            hour = " 0";
                            break;
                        default:
                            hour = (crwhPresentDay.get(Calendar.HOUR) < 10 ? " " : "") + crwhPresentDay.get(Calendar.HOUR);
                            break;
                    }
                    String min = (crwhPresentDay.get(Calendar.MINUTE) < 10 ? "0" : "") + crwhPresentDay.get(Calendar.MINUTE);
                    String sec = (crwhPresentDay.get(Calendar.SECOND) < 10 ? "0" : "") + crwhPresentDay.get(Calendar.SECOND);
                    obiClock.setText(amPm + " " + hour + ":" + min + ":" + sec);

                    sleep(1000);
                    String infoStr = obiBottomInfo.getText();

                    if (!" ".equals(infoStr) && (msgCntFlag == false || (curStr == null ? infoStr != null : !curStr.equals(infoStr)))) {
                        num = 5;
                        msgCntFlag = true;
                        curStr = infoStr;
                    } else if (!" ".equals(infoStr) && msgCntFlag == true) {
                        if (num > 0) {
                            num--;
                        } else {
                            msgCntFlag = false;
                            obiBottomInfo.setText(" ");
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Thread:Error");
                }
            }
        }
    }
}
//END
