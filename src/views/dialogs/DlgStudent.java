package views.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import models.Student;
import utils.AppConnection;
import utils.ErrorException;
import views.forms.FrmSplashScreen;
import controllers.StudentContoller;
import enums.DialogType;
import views.layouts.AppLayout;

/**
 *
 * @author Chamod
 */
public class DlgStudent extends javax.swing.JDialog {

    private HashMap<String, Integer> gendersMap = new HashMap();
    private HashMap<String, Integer> gradeMap = new HashMap<>();
    private HashMap<String, Integer> classMap = new HashMap<>();
    private HashMap<String, Integer> statusMap = new HashMap<>();

    private DialogType type = DialogType.CREATE;
    private String id;

    /**
     * Creates new form DlgStudent
     */
    public DlgStudent(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDesign();

        txtName.grabFocus();
        cboStatus.setVisible(false);
        lblStatus.setVisible(false);
        this.setSize(this.getWidth(), 689);

        loadGenders();
        loadGrades();
        loadStatus();

        btnEdit.setEnabled(false);
    }

    public DlgStudent(java.awt.Frame parent, boolean modal, Student student, String id) {

        this(parent, modal);

        type = DialogType.UPDATE;
        loadStudent(student);
        this.id = id;

        lblTitle.setText("Student Details");
        btnSubmit.setText("Save Changes");
        cboStatus.setVisible(true);
        lblStatus.setVisible(true);
        this.setSize(this.getWidth(), 731);

        changeEnabledStatus(false);

        btnEdit.setEnabled(true);
        btnReset.setEnabled(false);

        btnEdit.grabFocus();
        btnSubmit.setEnabled(false);

    }

    private void loadGenders() {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `genders`");

            Vector<String> data = new Vector();
            data.add("Select");

            while (rs.next()) {
                gendersMap.put(rs.getString("value"), rs.getInt("id"));
                data.add(rs.getString("value"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(data);
            cboGender.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadStatus() {

        try {

            ResultSet rs = AppConnection.search("SELECT * FROM `status`");

            Vector<String> data = new Vector();
            data.add("Select");

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

    private void loadGrades() {
        try {
            ResultSet rs = AppConnection.search("SELECT * FROM `grades`");

            Vector<String> data = new Vector<>();
            data.add("Select");

            while (rs.next()) {
                gradeMap.put(rs.getString("value"), rs.getInt("id"));
                data.add(rs.getString("value"));
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(data);
            cboGrade.setModel(model);

            cboGrade.addActionListener(e -> {
                String selectedGrade = (String) cboGrade.getSelectedItem();
                if (selectedGrade != null && !selectedGrade.equals("Select")) {
                    Integer gradeId = gradeMap.get(selectedGrade);
                    if (gradeId != null) {
                        loadClass(gradeId);
                    }
                }
            });

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadClass(int gradeId) {
        try {
            ResultSet rs = AppConnection.search("SELECT * FROM grades_has_classes WHERE grades_id = '" + gradeId + "' AND `is_active` = 1");

            Vector<String> data = new Vector<>();
            data.add("Select");

            // Populate classMap with available classes for the selected grade
            while (rs.next()) {
                classMap.put(rs.getString("class"), rs.getInt("id"));
                data.add(rs.getString("class"));
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(data);
            cboClass.setModel(model);

        } catch (Exception e) {
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void loadStudent(Student student) {
        txtName.setText(student.getFullName());
        txtEmail.setText(student.getEmail());
        txtGuardian1.setText(student.getGuardian1FullName());
        txtGuardian2.setText(student.getGuardian2FullName());
        txtMobile1.setText(student.getMobile1());
        txtMobile2.setText(student.getMobile2());
        cboGender.setSelectedItem(student.getGenderValue());
        cboGrade.setSelectedItem(student.getGradeId());
        cboClass.setSelectedItem(student.getClassValue());
        cboStatus.setSelectedItem(student.getStatusValue());
    }

    private void changeEnabledStatus(boolean state) {

        txtName.setEnabled(state);
        txtEmail.setEnabled(state);
        txtMobile1.setEnabled(state);
        txtMobile2.setEnabled(state);
        txtGuardian1.setEnabled(state);
        txtGuardian2.setEnabled(state);
        cboGender.setEnabled(false);
        cboGrade.setEnabled(state);
        cboClass.setEnabled(state);
        cboStatus.setEnabled(state);
    }

    private void setDesign() {
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_BACKGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_FOREGROUND, new Color(0, 0, 0));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_CLOSE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_MAXIMIZE, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICONIFFY, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_TITLE, false);

        txtName.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtEmail.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtGuardian1.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtGuardian2.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtMobile1.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtMobile2.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        btnSubmit.putClientProperty("JButton.buttonType", "borderless");
        btnClose.putClientProperty("JButton.buttonType", "borderless");
    }

    private Student loadFormData() throws ErrorException {

        Student student = new Student();

        if (txtName.getText().isBlank()) {
            throw new ErrorException("Name cannot be empty!");
        }
        student.setFullName(txtName.getText());

        if (txtEmail.getText().isBlank()) {
            throw new ErrorException("Email cannot be Empty!");
        }
        if (!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new ErrorException("Invalid Email Format!");
        }
        student.setEmail(txtEmail.getText());

        if (txtGuardian1.getText().isBlank()) {
            throw new ErrorException("Guardian name cannot be empty!");
        }
        student.setGuardian1FullName(txtGuardian1.getText());

        if (txtGuardian2.getText().isBlank()) {
            throw new ErrorException("Guardian name cannot be empty!");
        }
        student.setGuardian2FullName(txtGuardian2.getText());

        if (txtMobile1.getText().isBlank()) {
            throw new ErrorException("Mobile-1 cannot be empty!");
        }
        if (txtMobile1.getText().length() != 10) {
            throw new ErrorException("Mobile-1 is invalid!");
        }
        student.setMobile1(txtMobile1.getText());

        if (!txtMobile2.getText().isBlank() && txtMobile2.getText().length() != 10) {
            throw new ErrorException("Mobile-2 is invalid!");
        }
        student.setMobile2(txtMobile2.getText());

        if (cboGender.getSelectedIndex() == 0) {
            throw new ErrorException("Select a gender!");
        }
        student.setGenderId(gendersMap.get(cboGender.getSelectedItem()));

        if (cboGrade.getSelectedIndex() == 0) {
            throw new ErrorException("Select a Grade!");
        }

        if (cboClass.getSelectedIndex() == 0) {
            throw new ErrorException("Select a Class!");
        }
        
        return student;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtGuardian1 = new javax.swing.JTextField();
        txtGuardian2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMobile2 = new javax.swing.JTextField();
        txtMobile1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboGrade = new javax.swing.JComboBox<>();
        cboClass = new javax.swing.JComboBox<>();
        cboGender = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        btnSubmit = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 100));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Email:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Full Name:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Mobile (Optional)");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Guardian 2");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Guardian 1");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Mobile ");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Class");

        cboGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        cboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Gender");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Grade");

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblStatus.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(153, 153, 153));
        lblStatus.setText("Student Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtMobile1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                .addComponent(txtGuardian1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGuardian2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMobile2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGuardian1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGuardian2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMobile1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMobile2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addGap(26, 26, 26))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(621, 120));

        btnSubmit.setBackground(new java.awt.Color(30, 30, 30));
        btnSubmit.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmit.setText("Create Student");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/remove-formatting.png"))); // NOI18N
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/pen-line.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setMinimumSize(new java.awt.Dimension(80, 80));
        jPanel5.setPreferredSize(new java.awt.Dimension(409, 90));

        lblTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblTitle.setText("Create Student");

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
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        try {
            Student student = loadFormData();

            if (null == type) {
                new DlgError(AppLayout.appLayout, true, "Please contact the vendor!").setVisible(true);
                FrmSplashScreen.logger.log(Level.WARNING, "DlgSystemUser.java --> line 541");
            } else {
                switch (type) {
                    case CREATE:
                        int gradeId = cboGrade.getSelectedIndex();
                        String classValue = (String) cboClass.getSelectedItem();

                        new StudentContoller().createStudent(student, gradeId, classValue);
                        new DlgError(AppLayout.appLayout, true, "New Student created!", "Success", DialogType.SUCCESS).setVisible(true);
                        this.dispose();
                        break;
                    case UPDATE:
                        int UpdatedgradeId = cboGrade.getSelectedIndex();
                        String UpdatedclassValue = (String) cboClass.getSelectedItem();
                        int UpdatedStatusId = cboStatus.getSelectedIndex();

                        new StudentContoller().updateStudent(student, this.id, UpdatedgradeId, UpdatedclassValue, UpdatedStatusId );
                        new DlgError(AppLayout.appLayout, true, "Student updated!", "Success", DialogType.SUCCESS).setVisible(true);
                        this.dispose();
                        break;
                    default:
                        new DlgError(AppLayout.appLayout, true, "Please contact the vendor!").setVisible(true);
                        FrmSplashScreen.logger.log(Level.WARNING, "DlgSystemUser.java --> line 541");
                        break;
                }
            }

        } catch (ErrorException e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage(), "Validation Error").setVisible(true);
        } catch (Exception e) {
            new DlgError(AppLayout.appLayout, true, e.getMessage()).setVisible(true);
            FrmSplashScreen.logger.log(Level.WARNING, e.getMessage(), e);

        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        txtName.setText("");
        txtMobile1.setText("");
        txtMobile2.setText("");
        txtEmail.setText("");
        txtGuardian1.setText("");
        txtGuardian2.setText("");
        if (type == DialogType.CREATE) {
            cboGender.setSelectedIndex(0);
        }
        cboClass.setSelectedIndex(0);
        cboGrade.setSelectedIndex(0);
        cboStatus.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        changeEnabledStatus(true);
        btnEdit.setEnabled(false);
        btnReset.setEnabled(true);
        btnSubmit.setEnabled(true);
        txtName.grabFocus();
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> cboClass;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JComboBox<String> cboGrade;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGuardian1;
    private javax.swing.JTextField txtGuardian2;
    private javax.swing.JTextField txtMobile1;
    private javax.swing.JTextField txtMobile2;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
