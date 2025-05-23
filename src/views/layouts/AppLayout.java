/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.layouts;

import com.formdev.flatlaf.FlatClientProperties;
import enums.DialogAction;
import enums.LayoutPage;
import enums.UserRole;
import java.awt.Color;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.User;
import utils.AppConnection;
import views.dialogs.DlgConfirm;
import views.forms.FrmSplashScreen;
import views.internals.PnlAdditionalFees;
import views.internals.PnlAppointments;
import views.internals.PnlAttendance;
import views.internals.PnlAuthentication;
import views.internals.PnlDashboard;
import views.internals.PnlDisciplineRecords;
import views.internals.PnlResources;
import views.internals.PnlSMSEmail;
import views.internals.PnlSettings;
import views.internals.PnlStudents;
import views.internals.PnlVisitors;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import views.internals.PnlNoAccess;

/**
 *
 * @author vishv
 */
public class AppLayout extends javax.swing.JFrame {

    public static AppLayout appLayout;
    private JButton selectedButton;
    public static String loggedUserId;
    private UserRole loggedUserRole;

    /**
     * Creates new form AppLayout
     *
     * @param userId
     */
    public AppLayout(String userId) {
        initComponents();

        loggedUserId = userId;
        loadUserRole();

        appLayout = this;
        this.selectedButton = btnDashboard;

        formDesign();

        changeForm(LayoutPage.DASHBOARD);
    }

    private void loadUserRole() {
        try {

            ResultSet rs = AppConnection.search("SELECT `value` AS `role` FROM school_sync_v1.user_roles WHERE id IN (SELECT `user_roles_id` FROM `users` WHERE `id` = '" + loggedUserId + "');");
            rs.next();

            String role = rs.getString("role");
            switch (role) {
                case "Super Admin":
                    loggedUserRole = UserRole.SUPER_ADMIN;
                    break;
                case "Principal":
                    loggedUserRole = UserRole.PRINCIPAL;
                    break;
                case "Vice Principal":
                    loggedUserRole = UserRole.VICE_PRINCIPAL;
                    break;
                case "Sectional Head":
                    loggedUserRole = UserRole.SECTIONAL_HEAD;
                    break;
                case "Teacher":
                    loggedUserRole = UserRole.TEACHER;
                    break;
                case "ITU":
                    loggedUserRole = UserRole.ITU;
                    break;
                case "Securty Guard":
                    loggedUserRole = UserRole.SECURITY_GUARD;
                    break;
                case "Account dept.":
                    loggedUserRole = UserRole.ACCOUNT_DEPT;
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (Exception e) {

            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void changeForm(LayoutPage page) {
        switch (page) {
            case DASHBOARD:
                showForm(new PnlDashboard());
                changeSideButton(btnDashboard);
                break;
            case VISITORS:
                filterForm(new PnlVisitors(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL, UserRole.SECTIONAL_HEAD, UserRole.TEACHER});
                changeSideButton(btnVisitors);
                break;
            case ATTENDANCE:
                filterForm(new PnlAttendance(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL, UserRole.SECTIONAL_HEAD, UserRole.TEACHER});
                changeSideButton(btnAttendance);
                break;
            case SMS_EMAIL:
                filterForm(new PnlSMSEmail(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL, UserRole.SECTIONAL_HEAD, UserRole.TEACHER, UserRole.ITU, UserRole.ACCOUNT_DEPT});
                changeSideButton(btnSmsEmail);
                break;
            case ADDITIONAL_FEES:
                filterForm(new PnlAdditionalFees(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.ACCOUNT_DEPT});
                changeSideButton(btnAdditionalFees);
                break;
            case STUDENTS:
                filterForm(new PnlStudents(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL, UserRole.SECTIONAL_HEAD});
                changeSideButton(btnStudents);
                break;
            case RESOURCES:
                filterForm(new PnlResources(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL});
                changeSideButton(btnResources);
                break;
            case AUTHENTICATION:
                filterForm(new PnlAuthentication(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL});
                changeSideButton(btnAuth);
                break;
            case SETTINGS:
                filterForm(new PnlSettings(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL});
                changeSideButton(btnSettings);
                break;
            case DISCIPLINE_RECORDS:
                filterForm(new PnlDisciplineRecords(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL});
                changeSideButton(btnDisciplineRecords);
                break;
            case APPOINTMENTS:
                filterForm(new PnlAppointments(), new UserRole[]{UserRole.SUPER_ADMIN, UserRole.PRINCIPAL, UserRole.VICE_PRINCIPAL});
                changeSideButton(btnAppointments);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void formDesign() {
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_BACKGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_FOREGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_CLOSE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_MAXIMIZE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICONIFFY, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_TITLE, false);

        btnDashboard.putClientProperty("JButton.buttonType", "borderless");
        btnVisitors.putClientProperty("JButton.buttonType", "borderless");
        btnAttendance.putClientProperty("JButton.buttonType", "borderless");
        btnSmsEmail.putClientProperty("JButton.buttonType", "borderless");
        btnAdditionalFees.putClientProperty("JButton.buttonType", "borderless");
        btnStudents.putClientProperty("JButton.buttonType", "borderless");
        btnResources.putClientProperty("JButton.buttonType", "borderless");
        btnAuth.putClientProperty("JButton.buttonType", "borderless");
        btnSettings.putClientProperty("JButton.buttonType", "borderless");
        btnDisciplineRecords.putClientProperty("JButton.buttonType", "borderless");
        btnAppointments.putClientProperty("JButton.buttonType", "borderless");
    }

    private void changeSideButton(JButton newButton) {

        selectedButton.setBackground(new Color(255, 255, 255));
        selectedButton.setForeground(new Color(142, 142, 142));

        newButton.setBackground(new Color(229, 29, 84));
        newButton.setForeground(new Color(255, 255, 255));

        selectedButton = newButton;
        selectedButton.grabFocus();
    }

    private void filterForm(JPanel form, UserRole[] hasAccessRoles) {

        for (UserRole role : hasAccessRoles) {

            if (role == loggedUserRole) {
                showForm(form);
                return;
            }
        }

        showForm(new PnlNoAccess());
    }

    private void showForm(JPanel form) {
        pnlPlaceholder.removeAll();
        pnlPlaceholder.add(form, java.awt.BorderLayout.CENTER);
        pnlPlaceholder.repaint();
        pnlPlaceholder.revalidate();
    }

    public void closeApp() {
        try {

            DlgConfirm dialog = new DlgConfirm(AppLayout.appLayout, true, "Confirm Exit!", "Sure you want to sign out.");
            dialog.setVisible(true);
            DialogAction action = dialog.getAction();

            if (action == DialogAction.CONFIRM) {

                AppConnection.closeConnection();
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
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
        pnlSideBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnVisitors = new javax.swing.JButton();
        btnAttendance = new javax.swing.JButton();
        btnSmsEmail = new javax.swing.JButton();
        btnAdditionalFees = new javax.swing.JButton();
        btnStudents = new javax.swing.JButton();
        btnResources = new javax.swing.JButton();
        btnAuth = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnDisciplineRecords = new javax.swing.JButton();
        btnAppointments = new javax.swing.JButton();
        pnlPlaceholder = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        pnlSideBar.setBackground(new java.awt.Color(255, 255, 255));
        pnlSideBar.setMaximumSize(new java.awt.Dimension(0, 300));
        pnlSideBar.setPreferredSize(new java.awt.Dimension(268, 300));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/logo-right.png"))); // NOI18N

        btnDashboard.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(142, 142, 142));
        btnDashboard.setText("Dashboard");
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnVisitors.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnVisitors.setForeground(new java.awt.Color(142, 142, 142));
        btnVisitors.setText("Visitors");
        btnVisitors.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnVisitors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisitorsActionPerformed(evt);
            }
        });

        btnAttendance.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnAttendance.setForeground(new java.awt.Color(142, 142, 142));
        btnAttendance.setText("Attendance");
        btnAttendance.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttendanceActionPerformed(evt);
            }
        });

        btnSmsEmail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnSmsEmail.setForeground(new java.awt.Color(142, 142, 142));
        btnSmsEmail.setText("Notices");
        btnSmsEmail.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnSmsEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmsEmailActionPerformed(evt);
            }
        });

        btnAdditionalFees.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnAdditionalFees.setForeground(new java.awt.Color(142, 142, 142));
        btnAdditionalFees.setText("Additional Fees");
        btnAdditionalFees.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnAdditionalFees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdditionalFeesActionPerformed(evt);
            }
        });

        btnStudents.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnStudents.setForeground(new java.awt.Color(142, 142, 142));
        btnStudents.setText("Students");
        btnStudents.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnStudents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentsActionPerformed(evt);
            }
        });

        btnResources.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnResources.setForeground(new java.awt.Color(142, 142, 142));
        btnResources.setText("Resources");
        btnResources.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResourcesActionPerformed(evt);
            }
        });

        btnAuth.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnAuth.setForeground(new java.awt.Color(142, 142, 142));
        btnAuth.setText("Authentication");
        btnAuth.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAuthActionPerformed(evt);
            }
        });

        btnSettings.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(142, 142, 142));
        btnSettings.setText("Settings");
        btnSettings.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        btnDisciplineRecords.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnDisciplineRecords.setForeground(new java.awt.Color(142, 142, 142));
        btnDisciplineRecords.setText("Discipline Records");
        btnDisciplineRecords.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnDisciplineRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisciplineRecordsActionPerformed(evt);
            }
        });

        btnAppointments.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnAppointments.setForeground(new java.awt.Color(142, 142, 142));
        btnAppointments.setText("Appointments");
        btnAppointments.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSideBarLayout = new javax.swing.GroupLayout(pnlSideBar);
        pnlSideBar.setLayout(pnlSideBarLayout);
        pnlSideBarLayout.setHorizontalGroup(
            pnlSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideBarLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVisitors, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmsEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdditionalFees, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResources, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAuth, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisciplineRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnlSideBarLayout.setVerticalGroup(
            pnlSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideBarLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVisitors, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSmsEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdditionalFees, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisciplineRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResources, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAuth, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(pnlSideBar, java.awt.BorderLayout.WEST);

        pnlPlaceholder.setPreferredSize(new java.awt.Dimension(0, 0));
        pnlPlaceholder.setLayout(new java.awt.BorderLayout());
        jPanel1.add(pnlPlaceholder, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        changeForm(LayoutPage.DASHBOARD);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnVisitorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisitorsActionPerformed
        changeForm(LayoutPage.VISITORS);
    }//GEN-LAST:event_btnVisitorsActionPerformed

    private void btnAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttendanceActionPerformed
        changeForm(LayoutPage.ATTENDANCE);
    }//GEN-LAST:event_btnAttendanceActionPerformed

    private void btnSmsEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmsEmailActionPerformed
        changeForm(LayoutPage.SMS_EMAIL);
    }//GEN-LAST:event_btnSmsEmailActionPerformed

    private void btnAdditionalFeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdditionalFeesActionPerformed
        changeForm(LayoutPage.ADDITIONAL_FEES);
    }//GEN-LAST:event_btnAdditionalFeesActionPerformed

    private void btnStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentsActionPerformed
        changeForm(LayoutPage.STUDENTS);
    }//GEN-LAST:event_btnStudentsActionPerformed

    private void btnResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResourcesActionPerformed
        changeForm(LayoutPage.RESOURCES);
    }//GEN-LAST:event_btnResourcesActionPerformed

    private void btnAuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuthActionPerformed
        changeForm(LayoutPage.AUTHENTICATION);
    }//GEN-LAST:event_btnAuthActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        changeForm(LayoutPage.SETTINGS);
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnDisciplineRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisciplineRecordsActionPerformed

        changeForm(LayoutPage.DISCIPLINE_RECORDS);
    }//GEN-LAST:event_btnDisciplineRecordsActionPerformed

    private void btnAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentsActionPerformed

        changeForm(LayoutPage.APPOINTMENTS);
    }//GEN-LAST:event_btnAppointmentsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdditionalFees;
    private javax.swing.JButton btnAppointments;
    private javax.swing.JButton btnAttendance;
    private javax.swing.JButton btnAuth;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDisciplineRecords;
    private javax.swing.JButton btnResources;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnSmsEmail;
    private javax.swing.JButton btnStudents;
    private javax.swing.JButton btnVisitors;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlPlaceholder;
    private javax.swing.JPanel pnlSideBar;
    // End of variables declaration//GEN-END:variables
}
