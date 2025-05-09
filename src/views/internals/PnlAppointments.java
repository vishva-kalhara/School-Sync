/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.internals;

import com.formdev.flatlaf.FlatClientProperties;
import enums.DialogAction;
import enums.LayoutPage;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import utils.AppConnection;
import views.dialogs.DlgAppointment;
import views.dialogs.DlgConfirm;
import views.dialogs.DlgError;
import views.dialogs.DlgProfile;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author vishv
 */
public class PnlAppointments extends javax.swing.JPanel {

    private final HashMap<String, Integer> classesMap = new HashMap<>();

    /**
     * Creates new form PnlAppointments
     */
    public PnlAppointments() {
        initComponents();
        loadClasses();
        setDsign();
        loadTableData("");
    }

    private void setDsign() {
        btnAdd.putClientProperty("JButton.buttonType", "borderless");
        btnReport.putClientProperty("JButton.buttonType", "borderless");
        btnPrint.putClientProperty("JButton.buttonType", "borderless");
        btnRefresh.putClientProperty("JButton.buttonType", "borderless");
        btnLogout.putClientProperty("JButton.buttonType", "borderless");
        btnAccount.putClientProperty("JButton.buttonType", "borderless");
        btnClearFilters.putClientProperty("JButton.buttonType", "borderless");

        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name");

        pnlTable.putClientProperty(FlatClientProperties.STYLE, "arc: 13");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
    }

    private void loadClasses() {
        try {
            ResultSet rs = AppConnection.search("SELECT grades_has_classes.id AS Id, "
                    + "CONCAT(grades.value, '-', grades_has_classes.class) AS grade_class "
                    + "FROM school_sync_v1.grades_has_classes INNER JOIN grades "
                    + "ON grades_has_classes.grades_id = grades.id "
                    + "ORDER BY grades.value ASC, grades_has_classes.class ASC");

            Vector<String> data = new Vector();
            data.add("Select");

            while (rs.next()) {
                classesMap.put(rs.getString("grade_class"), rs.getInt("id"));
                data.add(rs.getString("grade_class"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboClass.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadTableData(String constraints) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            ResultSet rs = AppConnection.search("SELECT "
                    + "appointments.created_at AS created_at, "
                    + "appointments.student_id AS stuid, "
                    + "student.full_name AS stuname, "
                    + "appointments.title AS appointment, "
                    + "CONCAT"
                    + "(grades_has_classes.grades_id, ' - ', grades_has_classes.class) AS class "
                    + "FROM appointments INNER JOIN student ON "
                    + "appointments.student_id = student.id "
                    + "INNER JOIN grades_has_classes ON "
                    + "appointments.grades_has_classes_id = grades_has_classes.id "
                    + "INNER JOIN users ON appointments.users_id = users.id "
                    + "INNER JOIN grades ON grades_has_classes.grades_id = grades.id"
                    + constraints
                    + ";"
            );

            while (rs.next()) {
                Vector<String> data = new Vector<>();
                data.add(rs.getString("created_at"));
                data.add(rs.getString("stuid"));
                data.add(rs.getString("stuname"));
                data.add(rs.getString("class"));
                data.add(rs.getString("appointment"));

                model.addRow(data);
            }

            if (model.getRowCount() == 0) {
                jScrollPane1.setViewportView(new PnlNotFound());
                btnPrint.setEnabled(false);
                btnReport.setEnabled(false);
            } else {
                jScrollPane1.setViewportView(this.table);
            }

            btnRefresh.setEnabled(true);
            btnClearFilters.setEnabled(true);
        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }

    }

    private void fetchData(String constraints) {

        jScrollPane1.setViewportView(new PnlFetching());
        btnPrint.setEnabled(false);
        btnReport.setEnabled(false);
        btnRefresh.setEnabled(false);
        btnClearFilters.setEnabled(false);

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
        boolean hasClass = cboClass.getSelectedIndex() != 0;

        if (hasClass || hasSearch) {
            constraints.append(" WHERE ");
        }
        if (hasSearch) {
            constraints
                    .append(" student.full_name LIKE '%")
                    .append(txtSearch.getText())
                    .append("%' ");
        }
        if (hasClass) {

            if (hasSearch) {
                constraints.append(" AND ");
            }

            constraints
                    .append(" appointments.grades_has_classes_id = '")
                    .append(classesMap.get(String.valueOf(cboClass.getSelectedItem())))
                    .append("' ");
        }

        fetchData(constraints.toString());
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
        btnClearFilters = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cboClass = new javax.swing.JComboBox<>();

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
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/printer.png"))); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

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
        btnAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountActionPerformed(evt);
            }
        });

        btnClearFilters.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/filter-x.png"))); // NOI18N
        btnClearFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFiltersActionPerformed(evt);
            }
        });

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
                    .addComponent(btnAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearFilters, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 283, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

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
                "Created At", "Student Id", "Student Name", "Class", "Appointment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        cboClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Class" }));
        cboClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        new DlgAppointment(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        AppLayout.appLayout.closeApp();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        filterData();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cboClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClassActionPerformed
        filterData();
    }//GEN-LAST:event_cboClassActionPerformed

    private void btnClearFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFiltersActionPerformed

        AppLayout.appLayout.changeForm(LayoutPage.APPOINTMENTS);
    }//GEN-LAST:event_btnClearFiltersActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTableData("");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        try {

            JasperViewer.viewReport(generateReport(), false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            DlgConfirm dialog = new DlgConfirm(AppLayout.appLayout, true, "Confirm Print", "Are you sure to print report");
            dialog.setVisible(true);
            DialogAction action = dialog.getAction();

            if (action == DialogAction.CONFIRM) {
                JasperPrintManager.printReport(generateReport(), false);
            }
        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);

        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountActionPerformed
        new DlgProfile(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_btnAccountActionPerformed

    private JasperPrint generateReport() throws JRException {
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/school_sync_appoiments.jasper");

        HashMap<String, Object> params = new HashMap<>();
        params.put("PARAM_SEARCH", txtSearch.getText().isBlank() ? "All Students" : "\"" + txtSearch.getText() + "\"");
        params.put("PARAM_GRADE", cboClass.getSelectedIndex() == 0 ? "All Classes"
                : cboClass.getSelectedIndex() == 0 ? String.valueOf(cboClass.getSelectedItem()) + " All Classes"
                : String.valueOf(cboClass.getSelectedItem()) + " - " + String.valueOf(cboClass.getSelectedItem()));

        params.put("PARAM_GENERATED_BY", "Generated By: " + AppLayout.loggedUserId);
        params.put("PARAM_DATE_TIME", "Generated At: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));

        JRTableModelDataSource dataSource = new JRTableModelDataSource(table.getModel());
        return JasperFillManager.fillReport(inputStream, params, dataSource);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClearFilters;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox<String> cboClass;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
