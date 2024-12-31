/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.internals;

import com.formdev.flatlaf.FlatClientProperties;
import controllers.UserController;
import enums.DialogAction;
import enums.LayoutPage;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import models.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import utils.AppConnection;
import views.dialogs.DlgConfirm;
import views.dialogs.DlgError;
import views.dialogs.DlgSystemUser;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author vishv
 */
public class PnlAuthentication extends javax.swing.JPanel {

    private HashMap<String, Integer> statusMap = new HashMap();
    private HashMap<String, Integer> rolesMap = new HashMap();

    /**
     * Creates new form PnlAuthentication
     */
    public PnlAuthentication() {
        initComponents();
        setDsign();

        loadRoles();
        loadStatus();

        fetchData("");
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

    private void loadTableData(String constraints) {

        try {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            ResultSet rs = AppConnection.search(
                    "SELECT "
                    + "users.id, "
                    + "users.full_name, "
                    + "mobile1, "
                    + "status.value AS status_value, "
                    + "user_roles.value AS role_value "
                    + "FROM school_sync_v1.users "
                    + "INNER JOIN `status` ON `users`.`status_id` = `status`.`id` "
                    + "INNER JOIN `user_roles` ON `users`.`user_roles_id` = `user_roles`.`id` "
                    + constraints
            );
            while (rs.next()) {

                Vector<String> data = new Vector<>();
                data.add(rs.getString("id"));
                data.add(rs.getString("full_name"));
                data.add(rs.getString("mobile1"));
                data.add(rs.getString("status_value"));
                data.add(rs.getString("role_value"));
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
        btnClearFilter.putClientProperty("JButton.buttonType", "borderless");

        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name");

        pnlTable.putClientProperty(FlatClientProperties.STYLE, "arc: 13");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
    }

    private void loadRoles() {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `user_roles`");

            Vector<String> data = new Vector<>();
            data.add("All Roles");

            while (rs.next()) {

                rolesMap.put(rs.getString("value"), rs.getInt("id"));
                data.add(rs.getString("value"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboRole.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadStatus() {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `status`");

            Vector<String> data = new Vector();
            data.add("All Statuses");

            while (rs.next()) {

                statusMap.put(rs.getString("value"), rs.getInt("id"));
                data.add(rs.getString("value"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboStatus.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void filterData() {

        StringBuilder constraints = new StringBuilder();

        boolean hasSearch = !txtSearch.getText().isBlank();
        boolean hasStatus = cboStatus.getSelectedIndex() != 0;
        boolean hasRole = cboRole.getSelectedIndex() != 0;

        if (hasSearch || hasStatus || hasRole) {
            constraints.append(" WHERE ");
        }

        if (hasSearch) {
            constraints
                    .append(" `full_name` LIKE '%")
                    .append(txtSearch.getText())
                    .append("%' ");
        }

        if (hasStatus) {

            if (hasSearch) {
                constraints.append(" AND ");
            }

            constraints
                    .append(" `status_id` = '")
                    .append(statusMap.get(String.valueOf(cboStatus.getSelectedItem())))
                    .append("' ");
        }

        if (hasRole) {

            if (hasSearch || hasStatus) {
                constraints.append(" AND ");
            }

            constraints
                    .append(" `user_roles_id` = '")
                    .append(rolesMap.get(String.valueOf(cboRole.getSelectedItem())))
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
        btnClearFilter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cboStatus = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cboRole = new javax.swing.JComboBox<>();

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

        btnClearFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/filter-x.png"))); // NOI18N
        btnClearFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(btnClearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 389, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
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
                "Id", "Full Name", "Mobile 1", "Role", "Status"
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
            table.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRoleActionPerformed(evt);
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
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        new DlgSystemUser(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

        filterData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked

        if (evt.getClickCount() != 2) {
            return;
        }

        int selectedRow = table.getSelectedRow();

        try {

            System.out.println(table.getValueAt(selectedRow, 0));

            String usrId = String.valueOf(table.getValueAt(selectedRow, 0));

            User user = new UserController().getSystemUser(usrId);
            if (user == null) {

                new DlgError(AppLayout.appLayout, true, "Please refresh the table!", "Not Found").setVisible(true);
                return;
            }

            new DlgSystemUser(AppLayout.appLayout, true, user, usrId).setVisible(true);

        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }

    }//GEN-LAST:event_tableMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased


    }//GEN-LAST:event_txtSearchKeyReleased

    private void cboRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRoleActionPerformed

        filterData();
    }//GEN-LAST:event_cboRoleActionPerformed

    private void cboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusActionPerformed

        filterData();
    }//GEN-LAST:event_cboStatusActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

        filterData();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed

        try {

            JasperViewer.viewReport(generateReport(), false);

        } catch (JRException e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(java.util.logging.Level.WARNING, e.getMessage(), e);
        }
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed

        try {

            DlgConfirm dialog = new DlgConfirm(AppLayout.appLayout, true, "Confirm Print!", "Sure you want to print the report.");
            dialog.setVisible(true);
            DialogAction action = dialog.getAction();

            if (action == DialogAction.CONFIRM) {
                JasperPrintManager.printReport(generateReport(), false);
            }

        } catch (JRException e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(java.util.logging.Level.WARNING, e.getMessage(), e);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnClearFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFilterActionPerformed

        AppLayout.appLayout.changeForm(LayoutPage.AUTHENTICATION);
    }//GEN-LAST:event_btnClearFilterActionPerformed

    private JasperPrint generateReport() throws JRException {

        InputStream inputStream = this.getClass().getResourceAsStream("/reports/school_sync_users.jasper");
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("PARAM_SEARCH", txtSearch.getText().isBlank() ? "All Users" : "\"" + txtSearch.getText() + "\"");
        params.put("PARAM_ROLE", cboRole.getSelectedIndex() == 0 ? "All Roles" : cboRole.getSelectedItem());
        params.put("PARAM_STATUS", cboStatus.getSelectedIndex() == 0 ? "All Statuses" : cboStatus.getSelectedItem());
        params.put("PARAM_GENERATED_BY", "Generated by " + AppLayout.loggedUserId);
        params.put("PARAM_DATE_TIME", "Generated at " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        JRTableModelDataSource dataSource = new JRTableModelDataSource(table.getModel());

        return JasperFillManager.fillReport(inputStream, params, dataSource);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClearFilter;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox<String> cboRole;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
