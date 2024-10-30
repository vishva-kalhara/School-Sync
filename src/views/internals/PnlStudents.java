/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.internals;

import com.formdev.flatlaf.FlatClientProperties;
import controllers.StudentContoller;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.AppConnection;
import views.dialogs.DlgStudent;
import views.layouts.AppLayout;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import models.Student;
import views.dialogs.DlgError;
import views.forms.FrmSplashScreen;

/**
 *
 * @author vishv
 */
public class PnlStudents extends javax.swing.JPanel {
    
    private HashMap<String, Integer> gradeMap = new HashMap();
    private HashMap<String, String> classesMap = new HashMap();

    /**
     * Creates new form PnlStudents
     */
    public PnlStudents() {
        initComponents();
        setDsign();
        
        loadGrades();

        fetchData("");
    }
    
    private void loadGrades() {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `grades`");

            Vector<String> data = new Vector();
            data.add("All Grades");

            while (rs.next()) {

                gradeMap.put("Grade " + rs.getString("value"), rs.getInt("id"));
                data.add("Grade " + rs.getString("value"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboGrade.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadClasses(int gradeId) {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `grades_has_classes` WHERE `grades_id` = '" + gradeId + "'");

            Vector<String> data = new Vector();
            data.add("All Classes");

            while (rs.next()) {

                classesMap.put("Class " + rs.getString("class"), rs.getString("class"));
                data.add("Class " + rs.getString("class"));
            }

            if (data.size() == 1) {
                data.remove(0);
                data.add("No Classes");
                cboClass.setEnabled(false);
            } else {
//                cboGrade.setEnabled(false);
                cboClass.setEnabled(true);
                cboClass.grabFocus();
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboClass.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void fetchData(String constraints) {

        jScrollPane1.setViewportView(new PnlFetching());
        btnPrint.setEnabled(false);
        btnReport.setEnabled(false);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadTableData(constraints);
            }
        }).start();
    }
    
    private void filterData() {

        StringBuilder constraints = new StringBuilder("");

        boolean hasSearch = !txtSearch.getText().isBlank();
        boolean hasGrade = cboGrade.getSelectedIndex() != 0;
        boolean hasClass = cboClass.getSelectedIndex() != 0;

        if (hasSearch || hasGrade || hasClass) {
            constraints.append(" WHERE ");
        }

        if (hasSearch) {
            constraints
                    .append(" `student`.`full_name` LIKE '%")
                    .append(txtSearch.getText())
                    .append("%' ");
        }

        if (hasGrade) {

            if (hasSearch) {
                constraints.append(" AND ");
            }

            constraints
                    .append(" `grades`.`value` = '")
                    .append(gradeMap.get(String.valueOf(cboGrade.getSelectedItem())))
                    .append("' ");
        }

        if (hasGrade && hasClass) {

            if (hasSearch || hasGrade) {
                constraints.append(" AND ");
            }

            constraints
                    .append(" `grades_has_classes`.`class`  = '")
                    .append(classesMap.get(String.valueOf(cboClass.getSelectedItem())))
                    .append("' ");
        }

        fetchData(constraints.toString());
    }

    private void loadTableData(String constraints) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            ResultSet rs = AppConnection.search(
                    "SELECT "
                            + "`student`.`id` AS `student_id`, "
                            + "`student`.`full_name` AS `student_name`, "
                            + "`student`.`mobile1` AS `mobile1`, "
                            + "`genders`.`value` AS `gender`, "
                            + "CONCAT(`grades`.`value`, ' - ', `grades_has_classes`.`class`) AS `class` "
                            + "FROM school_sync_v1.student "
                            + "INNER JOIN `grades_has_classes` ON `student`.`grades_has_classes_id` = `grades_has_classes`.`id` "
                            + "INNER JOIN `grades` ON `grades_has_classes`.`grades_id` = `grades`.`id` "
                            + "INNER JOIN `genders` ON `student`.`genders_id` = `genders`.`id` "
                            + constraints
                            + " ORDER BY CAST(SUBSTRING(student.id, 5) AS UNSIGNED);"
            );

            while (rs.next()) {
                Vector<String> data = new Vector<>();
                data.add(rs.getString("student_id"));
                data.add(rs.getString("student_name"));
                data.add(rs.getString("mobile1"));
                data.add(rs.getString("gender"));
                data.add(rs.getString("class"));

                model.addRow(data);
            }
            
            if (model.getRowCount() == 0) {
                jScrollPane1.setViewportView(new PnlNotFound());
            } else {
                jScrollPane1.setViewportView(this.table);
                btnPrint.setEnabled(true);
                btnReport.setEnabled(true);
            }

        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }

    }

    private void setDsign() {
        btnAdd.putClientProperty("JButton.buttonType", "borderless");
        btnReport.putClientProperty("JButton.buttonType", "borderless");
        btnPrint.putClientProperty("JButton.buttonType", "borderless");
        btnRefresh.putClientProperty("JButton.buttonType", "borderless");
        btnLogout.putClientProperty("JButton.buttonType", "borderless");
        btnAccount.putClientProperty("JButton.buttonType", "borderless");

        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name");

        pnlTable.putClientProperty(FlatClientProperties.STYLE, "arc: 13");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnAccount = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cboClass = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cboGrade = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1100, 830));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(102, 830));

        btnAdd.setBackground(new java.awt.Color(35, 34, 50));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/plus.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/file-text.png"))); // NOI18N

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/printer.png"))); // NOI18N

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/refresh-cw.png"))); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/log-out.png"))); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnAccount.setBackground(new java.awt.Color(244, 244, 244));
        btnAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 434, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        cboClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboClass.setEnabled(false);
        cboClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClassActionPerformed(evt);
            }
        });

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        pnlTable.setBackground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Full Name", "Mobile 1", "Gender", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(65);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        cboGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGradeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        AppLayout.appLayout.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        new DlgStudent(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fetchData("");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }

        int selectedRow = table.getSelectedRow();

        try {

            String stuId = String.valueOf(table.getValueAt(selectedRow, 0));

            Student student = new StudentContoller().getStudent(stuId);
            if (student == null) {
                new DlgError(AppLayout.appLayout, true, "Please refresh the table!", "Not Found").setVisible(true);
                return;
            }
            
            new DlgStudent(AppLayout.appLayout, true,student, stuId).setVisible(true);
            fetchData();

        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }

    }//GEN-LAST:event_tableMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        
        filterData();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cboGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGradeActionPerformed
        
        if(cboGrade.getSelectedIndex() == 0) {
            cboClass.setSelectedIndex(0);
            cboClass.setEnabled(false);
        }
        
        loadClasses(gradeMap.get(String.valueOf(cboGrade.getSelectedItem())));
        filterData();
    }//GEN-LAST:event_cboGradeActionPerformed

    private void cboClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClassActionPerformed
        
        filterData();
    }//GEN-LAST:event_cboClassActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox<String> cboClass;
    private javax.swing.JComboBox<String> cboGrade;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
