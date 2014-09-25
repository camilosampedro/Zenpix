/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import MatricesDispersas.MatrizEnTripleta;
import MatricesDispersas.Tripleta;
import java.awt.Color;
import java.awt.Image;
import static java.awt.image.ImageObserver.ERROR;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * Clase InterfazContenedora: Es una clase gráfica que contiene las matrices a
 * operar, la barra de estado, los botones agregar, sumar, multiplicar,
 * intercambiar, tomar como 1 y 2.
 *
 * @author camilo
 */
public final class InterfazContenedora extends javax.swing.JFrame {

    private boolean operando;
    SubventanaMatriz m1, m2, resultado;
    static final int ESPERANDO = 0;
    static final int LISTO = 1;
    private boolean hayResultado;
    private ObjectOutputStream oos;
    private FileOutputStream fos;

    /**
     * Creates new form InterfazContenedora
     */
    public InterfazContenedora() {
        initComponents();

        m1 = new SubventanaMatriz("Matriz 1");
        m1.asignarEstado(jlEstado);
        m1.asignarDimensiones(3, 3);
        m1.setBounds(0, -28, 450, 565);
        m1.setVisible(true);

        jlEstado.setEditable(false);
        this.add(m1);
        jlEstado.setText("REGISTRO:\nEstado: Listo. \n(Se necesitan al menos 2 matrices para sumar o multiplicar)");
        operando = false;
        try {
            Image icon = new ImageIcon(getClass().getResource("/practica2/icono.png")).getImage();
            setIconImage(icon);
        } catch (NullPointerException ex) {
            System.err.println("Archivo de icono no encontrado.");
        }

        btnSumar.setBounds(btnSumar.getX() + 450, btnSumar.getY() + 30, btnSumar.getWidth(), btnSumar.getHeight());
        btnMultiplicar.setBounds(btnMultiplicar.getX() + 450, btnMultiplicar.getY() + 30, btnMultiplicar.getWidth(), btnMultiplicar.getHeight());
        btnIntercambiar.setBounds(btnSumar.getX() + btnSumar.getWidth() + 50, btnSumar.getY(), btnIntercambiar.getWidth(), btnIntercambiar.getHeight());
        btnIntercambiar1.setBounds(btnSumar.getX() + 450, btnSumar.getY(), btnIntercambiar1.getWidth(), btnIntercambiar1.getHeight());
        btnIntercambiar2.setBounds(btnMultiplicar.getX() + 425, btnMultiplicar.getY(), btnIntercambiar2.getWidth(), btnIntercambiar2.getHeight());
        btnIntercambiar.setVisible(false);
        btnIntercambiar1.setVisible(false);
        btnIntercambiar2.setVisible(false);
        btnMultiplicar.setVisible(false);
        btnSumar.setVisible(false);
        this.add(btnMultiplicar);
        this.add(btnSumar);
        this.add(btnIntercambiar);
        this.add(btnIntercambiar1);
        this.add(btnIntercambiar2);
        hayResultado = false;
    }

    /**
     * Agrega una nueva subventana matriz para permitir trabajar con dos
     * matrices.
     */
    public void agregarMatriz() {
        m2 = new SubventanaMatriz("Matriz 2");
        m2.asignarDimensiones(3, 3);
        m2.setBounds(m1.getX() + m1.getWidth(), -28, 450, 565);
        m2.asignarEstado(jlEstado);
        m2.setVisible(true);
        cambiarEstado(LISTO);
        this.add(m2);
        this.setSize(902, this.getHeight());
    }

    /**
     * Agrega una nueva subventana matriz en la cual se encuentra el resultado
     * de una operación entres dos matrices (multiplicación o suma).
     *
     */
    public void agregarResultado() {
        if (!hayResultado) {
            resultado = new SubventanaMatriz("Resultado");
            resultado.asignarDimensiones(3, 3);
            resultado.setBounds(m2.getX() + m2.getWidth(), -28, 450, 565);
            resultado.asignarEstado(jlEstado);
            resultado.setVisible(true);
            resultado.habilitar(false);
            cambiarEstado(LISTO);
            this.add(resultado);
            this.setSize(1353, this.getHeight());
            hayResultado = true;
            btnIntercambiar1.setVisible(true);
            btnIntercambiar2.setVisible(true);
        }
    }

    /**
     *
     * Agrega elementos al registro.
     * @param estado LISTO, ESPERANDO.
     */
    public void cambiarEstado(int estado) {
        switch (estado) {
            case LISTO:
                jlEstado.setText(jlEstado.getText() + "\nEstado: Listo.");
                break;
            case ESPERANDO:
                jlEstado.setText(jlEstado.getText() + "\nEstado: Esperando.");
                break;
        }
    }

    /**
     *
     * Agrega elementos al registro.
     * @param mensaje Mensaje a escribir.
     */
    public void cambiarEstado(String mensaje) {
        jlEstado.setText(jlEstado.getText() + "\n Estado: " + mensaje + ".");
    }

    /**
     * Agrega elementos al registro.
     *
     * @param estado LISTO, ESPERANDO.
     * @param mensaje Mensaje a escribir.
     */
    public void cambiarEstado(int estado, String mensaje) {
        switch (estado) {
            case ERROR:
                jlEstado.setForeground(Color.red);
                jlEstado.setText(jlEstado.getText() + "\nError: " + mensaje);
                break;
            case LISTO:
                jlEstado.setForeground(Color.white);
                jlEstado.setText(jlEstado.getText() + "\nEstado: " + mensaje);
                break;
            default:
                jlEstado.setForeground(Color.white);
                jlEstado.setText(jlEstado.getText() + "\nEstado: " + mensaje);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlEstado = new javax.swing.JTextArea();
        btnMultiplicar = new javax.swing.JButton();
        btnSumar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnIntercambiar = new javax.swing.JButton();
        btnIntercambiar1 = new javax.swing.JButton();
        btnIntercambiar2 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zempix v. 1.1.0");
        setPreferredSize(new java.awt.Dimension(451, 660));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(217, 217, 217));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 66, 90)));
        jPanel1.setName("Estado"); // NOI18N

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 42));

        jlEstado.setBackground(new java.awt.Color(1, 1, 1));
        jlEstado.setColumns(20);
        jlEstado.setForeground(new java.awt.Color(245, 245, 245));
        jlEstado.setRows(5);
        jScrollPane2.setViewportView(jlEstado);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnMultiplicar.setText("Multiplicar");
        btnMultiplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiplicarActionPerformed(evt);
            }
        });

        btnSumar.setText("Sumar");
        btnSumar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnIntercambiar.setText("Intercambiar");
        btnIntercambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntercambiarActionPerformed(evt);
            }
        });

        btnIntercambiar1.setText("Tomar como 1");
        btnIntercambiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntercambiar1ActionPerformed(evt);
            }
        });

        btnIntercambiar2.setText("Tomar como 2");
        btnIntercambiar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntercambiar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSumar)
                        .addGap(33, 33, 33)
                        .addComponent(btnIntercambiar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnIntercambiar1)
                        .addGap(40, 40, 40)
                        .addComponent(btnAgregar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIntercambiar2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMultiplicar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(455, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSumar)
                    .addComponent(btnMultiplicar)
                    .addComponent(btnIntercambiar))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIntercambiar1)
                    .addComponent(btnIntercambiar2)
                    .addComponent(btnAgregar))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMultiplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiplicarActionPerformed
        // TODO add your handling code here:
        MatrizEnTripleta matriz1 = m1.extraerMatriz();
        MatrizEnTripleta matriz2 = m2.extraerMatriz();
        Tripleta p;
        if (matriz1 == null) {
            p = m1.posicionError();
            if ((boolean) p.retornaValor()) {
                cambiarEstado(ERROR, "Dato en la posición (" + p.retornaFila() + ","
                        + p.retornaColumna() + "), de la matriz 1 con formato incorrecto.");
            }
            return;
        }
        if (matriz2 == null) {
            p = m2.posicionError();
            if ((boolean) p.retornaValor()) {
                cambiarEstado(ERROR, "Dato en la posición (" + p.retornaFila() + ","
                        + p.retornaColumna() + "), de la matriz 2 con formato incorrecto.");
            }
            return;
        }
        MatrizEnTripleta resul = matriz1.multiplicacion(matriz2);
        if (resul != null) {
            agregarResultado();
            resultado.reemplazarMatriz(resul);
            cambiarEstado(LISTO, "Matrices multiplicadas, resultado en Resultado.");
        } else {
            cambiarEstado(ERROR, "Las columnas de la Matriz 1 debe coincidir con las filas de la Matriz 2.");
        }
    }//GEN-LAST:event_btnMultiplicarActionPerformed

    private void btnSumarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumarActionPerformed
        // TODO add your handling code here:
        MatrizEnTripleta matriz1 = m1.extraerMatriz();
        MatrizEnTripleta matriz2 = m2.extraerMatriz();
        Tripleta p;
        if (matriz1 == null) {
            p = m1.posicionError();
            if ((boolean) p.retornaValor()) {
                cambiarEstado(ERROR, "Dato en la posición (" + p.retornaFila() + ","
                        + p.retornaColumna() + "), de la matriz 1 con formato incorrecto.");
            }
            return;
        }
        if (matriz2 == null) {
            p = m2.posicionError();
            if ((boolean) p.retornaValor()) {
                cambiarEstado(ERROR, "Dato en la posición (" + p.retornaFila() + ","
                        + p.retornaColumna() + "), de la matriz 2 con formato incorrecto.");
            }
            return;
        }
        MatrizEnTripleta resul = matriz1.suma(matriz2);
        if (resul != null) {
            agregarResultado();
            resultado.reemplazarMatriz(resul);
            //btnAgregarActionPerformed(evt);
            //btnAgregar.setSelected(false);
            cambiarEstado(LISTO, "Matrices sumadas, resultado en Resultado.");
        } else {
            cambiarEstado(ERROR, "Matrices de diferente dimensión no se pueden sumar, vuelva a intentarlo.");
        }
    }//GEN-LAST:event_btnSumarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            agregarMatriz();
            operando = true;
            btnIntercambiar.setVisible(true);
            btnSumar.setVisible(true);
            btnMultiplicar.setVisible(true);
            btnAgregar.setText("Quitar");
        } else {
            m2.dispose();
            if (hayResultado) {
                resultado.dispose();
            }
            hayResultado = false;
            this.setSize(451, this.getHeight());
            operando = false;
            btnSumar.setVisible(false);
            btnMultiplicar.setVisible(false);
            btnIntercambiar.setVisible(false);
            btnAgregar.setText("Agregar");
            jlEstado.setText(jlEstado.getText() + "\nEstado: Listo. \n(Se necesitan al menos 2 matrices para sumar o multiplicar)");
        }
        btnAgregar.setSelected(!operando);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnIntercambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntercambiarActionPerformed
        // TODO add your handling code here:
        MatrizEnTripleta matriz1 = m1.extraerMatriz();
        MatrizEnTripleta matriz2 = m2.extraerMatriz();
        m1.reemplazarMatriz(matriz2);
        m2.reemplazarMatriz(matriz1);
    }//GEN-LAST:event_btnIntercambiarActionPerformed

    private void btnIntercambiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntercambiar1ActionPerformed
        // TODO add your handling code here:
        MatrizEnTripleta resul = resultado.extraerMatriz();
        m1.reemplazarMatriz(resul);
        resultado.dispose();
        hayResultado = false;
        this.setSize(902, this.getHeight());
    }//GEN-LAST:event_btnIntercambiar1ActionPerformed

    private void btnIntercambiar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntercambiar2ActionPerformed
        // TODO add your handling code here:
        MatrizEnTripleta resul = resultado.extraerMatriz();
        m2.reemplazarMatriz(resul);
        resultado.dispose();
        hayResultado = false;
        this.setSize(902, this.getHeight());
    }//GEN-LAST:event_btnIntercambiar2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            FileWriter fw = new FileWriter("Registro/registro.log");
            BufferedWriter bw = new BufferedWriter(fw);
            try (PrintWriter salida = new PrintWriter(bw)) {
                salida.println(jlEstado.getText());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado");
            Logger.getLogger(InterfazContenedora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Error de lectura");
            Logger.getLogger(InterfazContenedora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazContenedora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazContenedora().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnIntercambiar;
    private javax.swing.JButton btnIntercambiar1;
    private javax.swing.JButton btnIntercambiar2;
    private javax.swing.JButton btnMultiplicar;
    private javax.swing.JButton btnSumar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jlEstado;
    // End of variables declaration//GEN-END:variables
}
