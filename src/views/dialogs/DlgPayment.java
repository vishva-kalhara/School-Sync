/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import controllers.AdditionalFeeController;
import enums.DialogType;
import java.awt.Color;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import utils.AppConnection;
import utils.ErrorException;
import views.forms.FrmSplashScreen;
import views.layouts.AppLayout;

/**
 *
 * @author Chamod
 */
public class DlgPayment extends javax.swing.JDialog {

    private final HashMap<String, Integer> classMap;
    private final HashMap<String, String> studentsMap;
    private final HashMap<String, String> additionalFeesMap;

    /**
     * Creates new form DlgPayment
     */
    public DlgPayment(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDesign();

        this.classMap = new HashMap<>();
        this.studentsMap = new HashMap<>();
        this.additionalFeesMap = new HashMap<>();

        loadClasses();
        loadAditionalFees();
    }

    private void setDesign() {
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_BACKGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_FOREGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_CLOSE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_MAXIMIZE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICONIFFY, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_TITLE, false);

        btnSubmit.putClientProperty("JButton.buttonType", "borderless");
        btnClose.putClientProperty("JButton.buttonType", "borderless");

        cboClass.grabFocus();
    }

    private void loadClasses() {

        try {

            ResultSet rs = AppConnection.search(""
                    + "SELECT "
                    + "CONCAT(`grades_id`, ' - ', `class`) AS `class`, "
                    + "id "
                    + "FROM school_sync_v1.grades_has_classes "
                    + "ORDER BY `class` ASC;"
            );

            Vector<String> data = new Vector<>();
            data.add("Select Class");

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

    private void loadStudents(int classId) {

        try {

            ResultSet rs = AppConnection.search("SELECT `id`, CONCAT(`id`, ' - ', `full_name`) AS studentName FROM school_sync_v1.student WHERE `grades_has_classes_id` = " + classId);

            Vector<String> data = new Vector<>();
            data.add("Select Student");

            while (rs.next()) {

                studentsMap.put(rs.getString("studentName"), rs.getString("id"));
                data.add(rs.getString("studentName"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboStudent.setModel(model);

            cboStudent.setEnabled(true);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadAditionalFees() {

        try {

            ResultSet rs = AppConnection.search("SELECT `id`, CONCAT('Grade ', `grades_id`, ' - ', `title`, ' (LKR ',  price, ')') AS value FROM school_sync_v1.additional_fees");

            Vector<String> data = new Vector<>();
            data.add("Select Fee");

            while (rs.next()) {

                additionalFeesMap.put(rs.getString("value"), rs.getString("id"));
                data.add(rs.getString("value"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboPayment.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void generateReport() {
        try {

            String selectedStudent = cboStudent.getSelectedItem().toString();
            String studentId = studentsMap.get(selectedStudent);

            ResultSet rs = AppConnection.search(
                    "SELECT payments.id, payments.student_id, payments.paid_amoint, payments.paid_at, "
                    + "additional_fees.title, student.full_name "
                    + "FROM school_sync_v1.payments "
                    + "INNER JOIN school_sync_v1.additional_fees ON payments.additional_fees_id = additional_fees.id "
                    + "INNER JOIN school_sync_v1.student ON payments.student_id = student.id "
                    + "WHERE payments.student_id = '" + studentId + "'"
            );

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                String id = rs.getString("id");
                String stuId = rs.getString("student_id");
                String stuName = rs.getString("full_name");
                String additionalFee = rs.getString("title");
                String amount = rs.getString("paid_amoint");
                String paidAt = rs.getString("paid_at");

                HashMap<String, Object> params = new HashMap<>();
                params.put("PARAM_INVOICE_ID", id);
                params.put("PARAM_RECEIVED_BY", AppLayout.loggedUserId);
                params.put("PARAM_STU_ID", stuId);
                params.put("PARAM_STU_NAME", stuName);
                params.put("PARAM_ADDITIONAL_FEE", additionalFee);
                params.put("PARAM_AMOUNT", amount);
                params.put("PARAM_PAID_AT", paidAt);
                params.put("PARAM_TOTAL", amount);

                InputStream inputStream = this.getClass().getResourceAsStream("/reports/school_sync_payment_recipt.jasper");

                if (inputStream == null) {
                    new DlgError(AppLayout.appLayout, true, "Report file not found!", "Error", DialogType.ERROR).setVisible(true);
                    return;
                }

                JasperPrint report = JasperFillManager.fillReport(inputStream, params, new JREmptyDataSource());

                JasperViewer viewer = new JasperViewer(report, false);
                viewer.setVisible(true);
                viewer.toFront();
            }

            if (!hasData) {
                new DlgError(AppLayout.appLayout, true, "No payment records found for selected student!", "Error", DialogType.ERROR).setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtClass = new javax.swing.JLabel();
        txtStudent = new javax.swing.JLabel();
        cboClass = new javax.swing.JComboBox<>();
        cboStudent = new javax.swing.JComboBox<>();
        txtAditionalFee = new javax.swing.JLabel();
        cboPayment = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        btnSubmit = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 100));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 100));

        txtClass.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        txtClass.setForeground(new java.awt.Color(153, 153, 153));
        txtClass.setText("Class");

        txtStudent.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        txtStudent.setForeground(new java.awt.Color(153, 153, 153));
        txtStudent.setText("Student");

        cboClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 - A" }));
        cboClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClassActionPerformed(evt);
            }
        });

        cboStudent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        cboStudent.setEnabled(false);

        txtAditionalFee.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        txtAditionalFee.setForeground(new java.awt.Color(153, 153, 153));
        txtAditionalFee.setText("Aditional Fee");

        cboPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtClass, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(cboStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAditionalFee, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cboPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtClass, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAditionalFee, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(621, 120));

        btnSubmit.setBackground(new java.awt.Color(30, 30, 30));
        btnSubmit.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmit.setText("Pay");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setMinimumSize(new java.awt.Dimension(80, 80));
        jPanel5.setPreferredSize(new java.awt.Dimension(409, 90));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel1.setText("Make a Payment");

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/x.png"))); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 24, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void cboClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClassActionPerformed

        if (cboClass.getSelectedIndex() != 0)
            loadStudents(Integer.parseInt(classMap.get(cboClass.getSelectedItem()).toString()));
    }//GEN-LAST:event_cboClassActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed

        try {

            if (cboStudent.getSelectedIndex() == 0) {
                throw new ErrorException("Select a student!");
            }
            String studentId = studentsMap.get(cboStudent.getSelectedItem().toString());

            if (cboPayment.getSelectedIndex() == 0) {
                throw new ErrorException("Select an Fee!");
            }
            String feeId = additionalFeesMap.get(cboPayment.getSelectedItem().toString());

            new AdditionalFeeController().addPayment(studentId, feeId);

            new DlgError(AppLayout.appLayout, true, "Payment Success!", "Success", DialogType.SUCCESS).setVisible(true);
            this.dispose();
            generateReport();

        } catch (ErrorException e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage(), "Validation Error").setVisible(true);
        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(java.util.logging.Level.WARNING, e.getMessage(), e);
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> cboClass;
    private javax.swing.JComboBox<String> cboPayment;
    private javax.swing.JComboBox<String> cboStudent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel txtAditionalFee;
    private javax.swing.JLabel txtClass;
    private javax.swing.JLabel txtStudent;
    // End of variables declaration//GEN-END:variables
}
