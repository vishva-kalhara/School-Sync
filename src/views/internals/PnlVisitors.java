/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.internals;

import com.formdev.flatlaf.FlatClientProperties;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.AppConnection;
import views.dialogs.DlgError;
import views.dialogs.DlgStudent;
import views.dialogs.DlgVisitor;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author vishv
 */
public class PnlVisitors extends javax.swing.JPanel {
    
    private HashMap<String, Integer> classMap = new HashMap();

    /**
     * Creates new form PnlVisitors
     */
    public PnlVisitors() {
        initComponents();
        setDsign();
        
        loadClasses();

        fetchData("");
    }
    
    private void loadClasses(){
        
        try {

            ResultSet rs = AppConnection.search(""
                    + "SELECT "
                    + "CONCAT(`grades_id`, ' - ', `class`) AS `class`, "
                    + "id "
                    + "FROM school_sync_v1.grades_has_classes "
                    + "ORDER BY `class` ASC;"
            );

            Vector<String> data = new Vector();
            data.add("All Classes");

            while (rs.next()) {

                classMap.put(rs.getString("class"), rs.getInt("id"));
                data.add(rs.getString("class"));
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
        boolean hasSort = cboStatus.getSelectedIndex() != 0;
        boolean hasTime = cboTime.getSelectedIndex() != 0;
        boolean hasClass = cboClass.getSelectedIndex() != 0;

        if (hasClass || hasSearch || hasSort || hasTime) {
            constraints.append(" WHERE ");
        }

        if (hasSearch) {
            constraints
                    .append(" `student`.`full_name` LIKE '%")
                    .append(txtSearch.getText())
                    .append("%' ");
        }

        if (hasSort) {

            if (hasSearch) {
                constraints.append(" AND ");
            }

            String value = "";
            switch (cboStatus.getSelectedItem().toString()) {
                case "Attented":
                    value = "1";
                    break;
                case "Not Attented":
                    value = "0";
                    break;
            }

            constraints
                    .append(" `visitor_details`.`has_attended` = '")
                    .append(value)
                    .append("' ");
        }

        if (hasTime) {

            if (hasSearch || hasSort) {
                constraints.append(" AND ");
            }

            String value = "";
            switch (cboTime.getSelectedItem().toString()) {
                case "Next 7 days":
                    value = " STR_TO_DATE(`date`, '%Y-%m-%d') >= '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND STR_TO_DATE(`date`, '%Y-%m-%d') <= '" + LocalDate.now().plusDays(7) + "' ";
                    break;
                case "Tommorrow":
                    value = "STR_TO_DATE(`date`, '%Y-%m-%d') = '" + LocalDate.now().plusDays(1) + "'";
                    break;
                case "Today":
                    value = " STR_TO_DATE(`date`, '%Y-%m-%d') = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'";
                    break;
                case "Yesterday":
                    value = "STR_TO_DATE(`date`, '%Y-%m-%d') = '" + LocalDate.now().minusDays(1) + "'";
                    break;
                case "Past 7 days":
                    value = " STR_TO_DATE(`date`, '%Y-%m-%d') <= '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND STR_TO_DATE(`date`, '%Y-%m-%d') >= '" + LocalDate.now().minusDays(7) + "' ";
                    break;
                case "Past 14 days":
                    value = " STR_TO_DATE(`date`, '%Y-%m-%d') <= '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND STR_TO_DATE(`date`, '%Y-%m-%d') >= '" + LocalDate.now().minusDays(14) + "' ";
                    break;
                case "Past 30 days":
                    value = " STR_TO_DATE(`date`, '%Y-%m-%d') <= '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND STR_TO_DATE(`date`, '%Y-%m-%d') >= '" + LocalDate.now().minusDays(30) + "' ";
                    break;
            }
            constraints.append(value);
        }
        
        if(hasClass){
            
            if ( hasTime || hasSearch || hasSort ) {
                constraints.append(" AND ");
            }
            
            constraints
                    .append(" `student`.`grades_has_classes_id` = '")
                    .append(classMap.get(String.valueOf(cboClass.getSelectedItem())))
                    .append("' ");
        }
        
        fetchData(constraints.toString());
    }

    private void loadTableData(String constraints) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            ResultSet rs = AppConnection.search(""
                    + "SELECT "
                    + "`visitor_details`.`date` AS `visit_date`, "
                    + "`student`.`full_name` AS `full_name`, "
                    + "`visitor_details`.`has_attended` AS `has_attented`, "
                    + "`student`.`mobile1` AS `mobile_1`, "
                    + "CONCAT(`grades_id`, ' - ', `grades_has_classes`.`class`) AS `class` "
                    + "FROM `visitor_details` "
                    + "INNER JOIN `student` ON `visitor_details`.`student_id` = `student`.`id`"
                    + "INNER JOIN `grades_has_classes` ON `student`.`grades_has_classes_id` = `grades_has_classes`.`id`"
                    + constraints
                    + "ORDER BY visit_date DESC;"
            );

            while (rs.next()) {
                Vector<String> data = new Vector<>();
                data.add(rs.getString("visit_date"));
                data.add(rs.getString("full_name"));
                data.add(rs.getString("mobile_1"));
                data.add(rs.getString("class"));
                data.add(rs.getBoolean("has_attented") ? "Yes" : "No");

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
        cboStatus = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cboTime = new javax.swing.JComboBox<>();
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 449, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Statuses", "Attented", "Not Attented" }));
        cboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatusActionPerformed(evt);
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
                "Date", "Student Name", "Mobile ", "Class", "Has Attented"
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
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        cboTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All time", "Next 7 days", "Tommorrow", "Today", "Yesterday", "Past 7 days", "Past 14 days", "Past 30 days" }));
        cboTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimeActionPerformed(evt);
            }
        });

        cboClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All time", "Next 7 days", "Tommorrow", "Today", "Yesterday", "Past 7 days", "Past 14 days", "Past 30 days" }));
        cboClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClassActionPerformed(evt);
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
                        .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                        .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        AppLayout.appLayout.closeApp();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        new DlgVisitor(AppLayout.appLayout, true).setVisible(true);

    }//GEN-LAST:event_btnAddActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

        filterData();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cboTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimeActionPerformed

        filterData();
    }//GEN-LAST:event_cboTimeActionPerformed

    private void cboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusActionPerformed
        
        filterData();
    }//GEN-LAST:event_cboStatusActionPerformed

    private void cboClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClassActionPerformed
        
        filterData();
    }//GEN-LAST:event_cboClassActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fetchData("");
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox<String> cboClass;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JComboBox<String> cboTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
