/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.internals;

import com.formdev.flatlaf.FlatClientProperties;
import enums.LayoutPage;
import java.awt.Dimension;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import utils.AppConnection;
import views.dialogs.DlgConfirm;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;
import java.sql.ResultSet;
import views.dialogs.DlgProfile;
import views.dialogs.DlgVisitorsAttendance;

/**
 *
 * @author vishv
 */
public class PnlDashboard extends javax.swing.JPanel {

    /**
     * Creates new form PnlDashboard
     */
    public PnlDashboard() {
        initComponents();
        setDsign();

        setUserName();
        setDisciplineRecordCount();
        setStudentAttendanceCount();
        setVisitortAttendanceCount();

    }

    private void setUserName() {

        try {

            ResultSet rs = AppConnection.search("SELECT `full_name` FROM users WHERE `id` = '" + AppLayout.loggedUserId + "'");
            rs.next();

            lblLoggedUserName.setText("<HTML>" + rs.getString("full_name") + "</HTML>");

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void setDisciplineRecordCount() {

        try {

            ResultSet rs = AppConnection.search("SELECT COUNT(*) AS row_count FROM decipline_records WHERE MONTH(issued_at) = MONTH(CURRENT_DATE()) AND YEAR(issued_at) = YEAR(CURRENT_DATE());");
            rs.next();

            lblDisciplineRecordCount.setText("<HTML>" + rs.getString("row_count") + " Records </HTML>");

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void setStudentAttendanceCount() {

        try {

            ResultSet rsAttended = AppConnection.search("SELECT COUNT(*) AS row_count FROM attendance WHERE DAY(makrd_at) = DAY(CURRENT_DATE()) AND MONTH(makrd_at) = MONTH(CURRENT_DATE()) AND YEAR(makrd_at) = YEAR(CURRENT_DATE());");
            rsAttended.next();

            ResultSet rsTotal = AppConnection.search("SELECT COUNT(`id`) AS `total` FROM school_sync_v1.student WHERE `status_id` = 1;");
            rsTotal.next();

            int percentage = (rsAttended.getInt("row_count") * 100) / (rsTotal.getInt("total"));

            lblStudentAttendance.setText("<HTML>" + percentage + "% Attended </HTML>");

            pnlAttended.setPreferredSize(new Dimension(getProgressWidth(percentage), 20));

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void setVisitortAttendanceCount() {

        try {

            ResultSet rsTotal = AppConnection.search("SELECT COUNT(`id`) AS `total` FROM school_sync_v1.visitor_details WHERE DAY(date) = DAY(CURRENT_DATE()) AND MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE());");
            rsTotal.next();

            if (rsTotal.getInt("total") == 0) {

                lblVisitorsPercentage.setText("<HTML>Not Assigned</HTML>");

                pnlVisitors.setPreferredSize(new Dimension(getProgressWidth(100), 20));
                
                return;
            }

            ResultSet rsAttended = AppConnection.search("SELECT COUNT(`id`) AS `total` FROM school_sync_v1.visitor_details WHERE DAY(date) = DAY(CURRENT_DATE()) AND MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE()) WHERE `has_attended` = 1;");
            rsAttended.next();

            int percentage = (rsAttended.getInt("total") * 100) / (rsTotal.getInt("total"));

            lblVisitorsPercentage.setText("<HTML>" + percentage + "% Attended </HTML>");

            pnlVisitors.setPreferredSize(new Dimension(getProgressWidth(percentage), 20));

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void setDsign() {
        btnLogout.putClientProperty("JButton.buttonType", "borderless");
        btnAccount.putClientProperty("JButton.buttonType", "borderless");

        pnlAttendanceOuter.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        pnlAttended.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        pnlVisitorsOuter.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        pnlVisitors.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        pnlCard1.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        pnlCard2.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        pnlCard3.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        pnlCard4.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        pnlMarkVisitors.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

    }

    private int getProgressWidth(int percentage) {

        int output = (int) (3.76 * percentage);
        return output;
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
        btnLogout = new javax.swing.JButton();
        btnAccount = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        pnlCard1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblLoggedUserName = new javax.swing.JLabel();
        pnlCard2 = new javax.swing.JPanel();
        lblDisciplineRecordCount = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnToDiscipleRecords = new javax.swing.JButton();
        pnlCard3 = new javax.swing.JPanel();
        lblStudentAttendance = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pnlAttendanceOuter = new javax.swing.JPanel();
        pnlAttended = new javax.swing.JPanel();
        pnlCard4 = new javax.swing.JPanel();
        pnlVisitorsOuter = new javax.swing.JPanel();
        pnlVisitors = new javax.swing.JPanel();
        lblVisitorsPercentage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnlMarkVisitors = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(102, 830));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(707, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        jPanel3.setBackground(new java.awt.Color(247, 247, 247));
        jPanel3.setLayout(new java.awt.GridLayout(2, 2, 24, 24));

        pnlCard1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Hello,");

        lblLoggedUserName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 28)); // NOI18N
        lblLoggedUserName.setText("<HTML>Wishva Chandrasekara</HTML>");
        lblLoggedUserName.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlCard1Layout = new javax.swing.GroupLayout(pnlCard1);
        pnlCard1.setLayout(pnlCard1Layout);
        pnlCard1Layout.setHorizontalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblLoggedUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        pnlCard1Layout.setVerticalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoggedUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jPanel3.add(pnlCard1);

        pnlCard2.setBackground(new java.awt.Color(255, 255, 255));

        lblDisciplineRecordCount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 28)); // NOI18N
        lblDisciplineRecordCount.setText("03 records");
        lblDisciplineRecordCount.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Discipline records (30d),");

        btnToDiscipleRecords.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnToDiscipleRecords.setText("View All");
        btnToDiscipleRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToDiscipleRecordsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCard2Layout = new javax.swing.GroupLayout(pnlCard2);
        pnlCard2.setLayout(pnlCard2Layout);
        pnlCard2Layout.setHorizontalGroup(
            pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard2Layout.createSequentialGroup()
                        .addComponent(btnToDiscipleRecords)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCard2Layout.createSequentialGroup()
                        .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(lblDisciplineRecordCount, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                        .addGap(32, 32, 32))))
        );
        pnlCard2Layout.setVerticalGroup(
            pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDisciplineRecordCount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnToDiscipleRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel3.add(pnlCard2);

        pnlCard3.setBackground(new java.awt.Color(255, 255, 255));

        lblStudentAttendance.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        lblStudentAttendance.setText("61% Attended");
        lblStudentAttendance.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Today Attendance");

        pnlAttendanceOuter.setPreferredSize(new java.awt.Dimension(151, 20));
        pnlAttendanceOuter.setLayout(new java.awt.BorderLayout());

        pnlAttended.setBackground(new java.awt.Color(229, 29, 84));
        pnlAttended.setPreferredSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout pnlAttendedLayout = new javax.swing.GroupLayout(pnlAttended);
        pnlAttended.setLayout(pnlAttendedLayout);
        pnlAttendedLayout.setHorizontalGroup(
            pnlAttendedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        pnlAttendedLayout.setVerticalGroup(
            pnlAttendedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlAttendanceOuter.add(pnlAttended, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout pnlCard3Layout = new javax.swing.GroupLayout(pnlCard3);
        pnlCard3.setLayout(pnlCard3Layout);
        pnlCard3Layout.setHorizontalGroup(
            pnlCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard3Layout.createSequentialGroup()
                        .addComponent(pnlAttendanceOuter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38))
                    .addGroup(pnlCard3Layout.createSequentialGroup()
                        .addGroup(pnlCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStudentAttendance, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                            .addGroup(pnlCard3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(31, 31, 31))))
        );
        pnlCard3Layout.setVerticalGroup(
            pnlCard3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStudentAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(pnlAttendanceOuter, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        jPanel3.add(pnlCard3);

        pnlCard4.setBackground(new java.awt.Color(255, 255, 255));

        pnlVisitorsOuter.setPreferredSize(new java.awt.Dimension(151, 20));
        pnlVisitorsOuter.setLayout(new java.awt.BorderLayout());

        pnlVisitors.setBackground(new java.awt.Color(229, 29, 84));
        pnlVisitors.setPreferredSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout pnlVisitorsLayout = new javax.swing.GroupLayout(pnlVisitors);
        pnlVisitors.setLayout(pnlVisitorsLayout);
        pnlVisitorsLayout.setHorizontalGroup(
            pnlVisitorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        pnlVisitorsLayout.setVerticalGroup(
            pnlVisitorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlVisitorsOuter.add(pnlVisitors, java.awt.BorderLayout.WEST);

        lblVisitorsPercentage.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        lblVisitorsPercentage.setText("80% Attended");
        lblVisitorsPercentage.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Visitors today");

        javax.swing.GroupLayout pnlCard4Layout = new javax.swing.GroupLayout(pnlCard4);
        pnlCard4.setLayout(pnlCard4Layout);
        pnlCard4Layout.setHorizontalGroup(
            pnlCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard4Layout.createSequentialGroup()
                        .addComponent(pnlVisitorsOuter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38))
                    .addGroup(pnlCard4Layout.createSequentialGroup()
                        .addGroup(pnlCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVisitorsPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                            .addGroup(pnlCard4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(31, 31, 31))))
        );
        pnlCard4Layout.setVerticalGroup(
            pnlCard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVisitorsPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(pnlVisitorsOuter, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        jPanel3.add(pnlCard4);

        pnlMarkVisitors.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Mark Visitor's Attendance");

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton2.setText("Mark Now");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMarkVisitorsLayout = new javax.swing.GroupLayout(pnlMarkVisitors);
        pnlMarkVisitors.setLayout(pnlMarkVisitorsLayout);
        pnlMarkVisitorsLayout.setHorizontalGroup(
            pnlMarkVisitorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMarkVisitorsLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMarkVisitorsLayout.setVerticalGroup(
            pnlMarkVisitorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMarkVisitorsLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlMarkVisitors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(pnlMarkVisitors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

        AppLayout.appLayout.closeApp();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnToDiscipleRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToDiscipleRecordsActionPerformed
        AppLayout.appLayout.changeForm(LayoutPage.DISCIPLINE_RECORDS);
    }//GEN-LAST:event_btnToDiscipleRecordsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        new DlgVisitorsAttendance(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountActionPerformed
        new DlgProfile(AppLayout.appLayout, true).setVisible(true);
    }//GEN-LAST:event_btnAccountActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnToDiscipleRecords;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblDisciplineRecordCount;
    private javax.swing.JLabel lblLoggedUserName;
    private javax.swing.JLabel lblStudentAttendance;
    private javax.swing.JLabel lblVisitorsPercentage;
    private javax.swing.JPanel pnlAttendanceOuter;
    private javax.swing.JPanel pnlAttended;
    private javax.swing.JPanel pnlCard1;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JPanel pnlCard3;
    private javax.swing.JPanel pnlCard4;
    private javax.swing.JPanel pnlMarkVisitors;
    private javax.swing.JPanel pnlVisitors;
    private javax.swing.JPanel pnlVisitorsOuter;
    // End of variables declaration//GEN-END:variables
}
