/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import MatricesDispersas.MatrizEnTripleta;
import MatricesDispersas.Tripleta;
import java.awt.Color;
import java.awt.Container;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Clase SubventanaMatriz: Contiene los campos para ingresar la matriz y elegir
 * su orden, además contiene el menú matriz, con sus botones de traspuesta,
 * determinante, adjunta, inversa, limpiar, llenar según lo especificado y
 * eliminar fila/columna.
 *
 * @author camilo
 */
public final class SubventanaMatriz extends javax.swing.JInternalFrame {

    private MatrizEnTripleta matriz;
    private JTextField valores[][];
    private JLabel indices[];
    private int numeroFilas, numeroColumnas, ierror, jerror, limitef, limitec;
    private Container miContenedor;
    private boolean lee, alerta, operando, alerta2;
    private JTextArea estado;
    private String nombre;
    private DecimalFormat df = new DecimalFormat("0.00");
    private Thread hilo;

    /**
     * Creates new form SubventanaMatriz.
     *
     * @param titulo Titulo de la matriz a crear.
     */
    public SubventanaMatriz(String titulo) {
        initComponents();
        TitledBorder borde = (TitledBorder) this.getBorder();
        nombre = titulo;
        borde.setTitle(nombre);
        actualizarComponentes();
        limitec = limitef = 10;
        alerta = operando = alerta2 = false;
    }

    /**
     * Se le envía el estado del padre.
     *
     * @param rotuloEstado Estado del padre.
     */
    public void asignarEstado(JTextArea rotuloEstado) {
        estado = rotuloEstado;
    }

    /**
     *
     * Cambia las dimensiones de la matriz.
     *
     * @param cantidadFilas Nuevo número de filas.
     * @param cantidadColumnas Nuevo número de columnas.
     */
    public void asignarDimensiones(int cantidadFilas, int cantidadColumnas) {
        numeroFilas = cantidadFilas;
        numeroColumnas = cantidadColumnas;
    }

    /**
     * Reescribe los componentes (vacíos) de la interfaz gráfica referente a la
     * matriz.
     *
     */
    public void actualizarComponentes() {
        int i, j;
        miContenedor = getContentPane();
        miContenedor.setLayout(null);
        valores = new JTextField[numeroFilas][numeroColumnas];
        indices = new JLabel[numeroFilas + numeroColumnas];
        //jPanel.setLayout(null);
        //miContenedor.add(new JLabel("A"));
        //jScrollPane.setLayout(null);
        for (i = 0; i < numeroFilas; i++) {
            for (j = 0; j < numeroColumnas; j++) {
                valores[i][j] = new JTextField("0");
                valores[i][j].setHorizontalAlignment(JTextField.CENTER);
                if (j == 0) {
                    indices[i] = new JLabel();
                    indices[i].setText(i + 1 + "");
                    indices[i].setVisible(true);
                    //indices[i].setBounds(j * 41 + 5, i * 26 + 30, 20, 25);
                    jPanel.add(indices[i + j], new org.netbeans.lib.awtextra.AbsoluteConstraints(j * 41 + 10, i * 26 + 30, 20, 25));
                }
                if (i == 0) {
                    indices[numeroFilas + j] = new JLabel();
                    indices[numeroFilas + j].setText(j + 1 + "");
                    indices[numeroFilas + j].setVisible(true);
                    //indices[numeroFilas + j].setBounds(j * 41 + 40, i * 26 + 5, 20, 25);
                    jPanel.add(indices[numeroFilas + j], new org.netbeans.lib.awtextra.AbsoluteConstraints(j * 41 + 45, i * 26 + 5, 20, 25));
                }
                valores[i][j].setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                //valores[i][j].setBounds(j * 41 + 23, i * 26 + 30, 40, 25);
                valores[i][j].setVisible(true);
                valores[i][j].setForeground(Color.GRAY);
                jPanel.add(valores[ i][ j], new org.netbeans.lib.awtextra.AbsoluteConstraints(j * 41 + 28, i * 26 + 30, 40, 25));
            }
        }
        jPanel.revalidate();
        jScrollPane.revalidate();
        //this.paintAll(this.getGraphics());
        jPanel.paintAll(jPanel.getGraphics());
        this.paintAll(this.getGraphics());
    }

    private boolean limiteExcedidoFila(int fila) {
        if (fila > limitef) {
            limitef = limitef + 10;
            return true;
        }
        return false;
    }

    private boolean limiteExcedidoColumna(int columna) {
        if (columna > limitec) {
            limitec = limitec + 10;
            return true;
        }
        return false;
    }

    /**
     * Reescribe la matriz, cambiando los valores por la matriz actual.
     *
     */
    public void reescribirMatriz() {
        int i, n = matriz.numeroTripletas(), f, c, val;
        double vald;
        Tripleta t;
        for (i = 1; i <= n; i++) {
            t = matriz.retornaTripleta(i);
            if (t == null) {
                continue;
            }
            f = t.retornaFila();
            c = t.retornaColumna();
            if (f > numeroFilas) {
                break;
            }
            if (c > numeroColumnas) {
                continue;
            }
            if (t.retornaValor() instanceof Integer) {
                if ((Integer) t.retornaValor() % 1 == 0) {
                    val = (Integer) t.retornaValor();
                    valores[f - 1][c - 1].setText(Integer.toString(val));
                } else {
                    valores[f - 1][c - 1].setText(Integer.toString((Integer) t.retornaValor()));
                }
            } else {
                if ((Double) t.retornaValor() % 1 == 0) {
                    vald = (double) t.retornaValor();
                    val = (int) vald;
                    valores[f - 1][c - 1].setText(Integer.toString(val));
                } else {
                    vald = (double) t.retornaValor();
                    valores[f - 1][c - 1].setText(df.format(vald));
                }
            }
            valores[f - 1][c - 1].setForeground(Color.black);
        }
    }

    /**
     * Lee la matriz y la retorna.
     *
     * @return Matriz gráfica, representada como tripletas.
     */
    public MatrizEnTripleta extraerMatriz() {
        leerDatos();
        if (lee) {
            return matriz;
        } else {
            return null;
        }
    }

    /**
     * Asigna la matriz. No reescribe la matriz, solo la guarda en la variable.
     *
     * @param matrizNueva Matriz nueva.
     */
    public void asignaMatriz(MatrizEnTripleta matrizNueva) {
        matriz = matrizNueva;
    }

    /**
     * Retorna la posición en la que dato es ingresado incorrectamente.
     *
     * @return Tripleta con fila y columna incorrecta.
     */
    public Tripleta posicionError() {
        Tripleta t = new Tripleta(ierror + 1, jerror + 1, !lee);
        return t;
    }

    /**
     * Reemplaza y reescribe la matriz.
     *
     * @param matrizNueva Matriz que sustituirá la actual.
     */
    public void reemplazarMatriz(MatrizEnTripleta matrizNueva) {
        asignaMatriz(matrizNueva);
        eliminarComponentes();
        numeroFilas = matriz.numeroFilas();
        numeroColumnas = matriz.numeroColumnas();
        actualizarComponentes();
        reescribirMatriz();
        tfFilas.setText(Integer.toString(matrizNueva.numeroFilas()));
        tfColumnas.setText(Integer.toString(matrizNueva.numeroColumnas()));
        jsFilas.setValue(matrizNueva.numeroFilas());
        jsColumnas.setValue(matrizNueva.numeroColumnas());
    }

    private void leerDatos() {
        lee = true;
        Tripleta t = new Tripleta(numeroFilas, numeroColumnas, 0);
        matriz = new MatrizEnTripleta(t);
        int i = 0, j = 0;
        double val;
        try {
            for (i = 0; i < numeroFilas; i++) {
                for (j = 0; j < numeroColumnas; j++) {
                    if ("".equals(valores[i][j].getText())) {
                        valores[i][j].setText("0");
                    }
                    val = Double.parseDouble(valores[i][j].getText());
                    if (valores[i][j].getForeground() != Color.GRAY) {
                        valores[i][j].setForeground(Color.GRAY);
                    }
                    if (val != 0) {
                        t = new Tripleta(i + 1, j + 1, val);
                        matriz.insertaTripleta(t);
                        valores[i][j].setForeground(Color.black);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            lee = false;
            //JOptionPane.showMessageDialog(rootPane, "Dato en (" + (i + 1) + ", " + (j + 1) + ") ingresado incorrectamente", "Error en la inserción de datos", JOptionPane.ERROR_MESSAGE);
            ierror = i;
            jerror = j;
            valores[i][j].setForeground(Color.red);
            while (i < numeroFilas) {
                for (j = 0; j < numeroColumnas; j++) {
                    try {
                        val = Double.parseDouble(valores[i][j].getText());
                        if (val != 0) {
                            valores[i][j].setForeground(Color.black);
                        }
                    } catch (NumberFormatException exc) {
                        valores[i][j].setForeground(Color.red);
                    }
                }
                i = i + 1;
            }
        }
    }

    /**
     * Retorna la matriz de esta interfaz.
     *
     * @return Matriz de la interfaz como tripletas.
     */
    public MatrizEnTripleta retornaMatriz() {
        return matriz;
    }

    private void eliminarComponentes() {
        //miContenedor = getContentPane();
        //miContenedor.setLayout(null);
        //miContenedor.remove(valores[0][0]);
        for (int i = 0; i < valores.length; i++) {
            jPanel.remove(indices[i]);
            for (int j = 0; j < valores[i].length; j++) {
                if (i == 0) {
                    jPanel.remove(indices[numeroFilas + j]);
                }
                jPanel.remove(valores[i][j]);
            }
        }
        this.paintAll(this.getGraphics());
    }

    /**
     * Retorna el número de filas.
     *
     * @return Número de filas.
     */
    public int retornaFilas() {
        return numeroFilas;
    }

    /**
     * Retorna el número de columnas.
     *
     * @return Número de columnas.
     */
    public int retornaColumnas() {
        return numeroColumnas;
    }

    /**
     * Habilita/deshabilita los componentes de filas o columnas.
     *
     * @param habilitado true para habilitar, false para deshabilitar.
     */
    public void habilitar(boolean habilitado) {
        jsFilas.setEnabled(habilitado);
        jsColumnas.setEnabled(habilitado);
        tfFilas.setEnabled(habilitado);
        tfColumnas.setEnabled(habilitado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfFilas = new javax.swing.JTextField();
        jsFilas = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        tfColumnas = new javax.swing.JTextField();
        jsColumnas = new javax.swing.JSlider();
        jScrollPane = new javax.swing.JScrollPane();
        jPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnTraspuesta = new javax.swing.JMenuItem();
        btnDeterminante = new javax.swing.JMenuItem();
        btnAdjunta = new javax.swing.JMenuItem();
        btnInversa = new javax.swing.JMenuItem();
        btnLimpiar = new javax.swing.JMenuItem();
        btnMultiplicarPorEscalar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnLlenarConNumero = new javax.swing.JMenuItem();
        btnLlenarAleatoriamente = new javax.swing.JMenuItem();
        btnIdentidad = new javax.swing.JMenuItem();
        btnEliminar = new javax.swing.JMenuItem();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Matriz", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BOTTOM));
        setPreferredSize(new java.awt.Dimension(500, 450));
        setVisible(true);
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Filas:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 10, 70, 20);

        tfFilas.setText("3");
        tfFilas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfFilasKeyReleased(evt);
            }
        });
        getContentPane().add(tfFilas);
        tfFilas.setBounds(110, 10, 38, 27);

        jsFilas.setMaximum(13);
        jsFilas.setMinimum(1);
        jsFilas.setSnapToTicks(true);
        jsFilas.setToolTipText("");
        jsFilas.setValue(3);
        jsFilas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jsFilasMouseReleased(evt);
            }
        });
        getContentPane().add(jsFilas);
        jsFilas.setBounds(150, 0, 280, 54);

        jLabel2.setText("Columnas:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 50, 80, 20);

        tfColumnas.setText("3");
        tfColumnas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfColumnasKeyReleased(evt);
            }
        });
        getContentPane().add(tfColumnas);
        tfColumnas.setBounds(110, 50, 38, 27);

        jsColumnas.setMaximum(9);
        jsColumnas.setMinimum(1);
        jsColumnas.setSnapToTicks(true);
        jsColumnas.setToolTipText("");
        jsColumnas.setValue(3);
        jsColumnas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jsColumnasMouseReleased(evt);
            }
        });
        getContentPane().add(jsColumnas);
        jsColumnas.setBounds(150, 40, 280, 54);

        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane.setViewportView(jPanel);

        getContentPane().add(jScrollPane);
        jScrollPane.setBounds(10, 100, 420, 400);

        jMenu1.setText("Matriz");

        btnTraspuesta.setText("Traspuesta");
        btnTraspuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraspuestaActionPerformed(evt);
            }
        });
        jMenu1.add(btnTraspuesta);

        btnDeterminante.setText("Determinante");
        btnDeterminante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeterminanteActionPerformed(evt);
            }
        });
        jMenu1.add(btnDeterminante);

        btnAdjunta.setText("Adjunta");
        btnAdjunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdjuntaActionPerformed(evt);
            }
        });
        jMenu1.add(btnAdjunta);

        btnInversa.setText("Inversa");
        btnInversa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInversaActionPerformed(evt);
            }
        });
        jMenu1.add(btnInversa);

        btnLimpiar.setText("Limpiar matriz");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jMenu1.add(btnLimpiar);

        btnMultiplicarPorEscalar.setText("Multiplicar por escalar");
        btnMultiplicarPorEscalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiplicarPorEscalarActionPerformed(evt);
            }
        });
        jMenu1.add(btnMultiplicarPorEscalar);

        jMenu2.setText("Llenar");

        btnLlenarConNumero.setText("Con número");
        btnLlenarConNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLlenarConNumeroActionPerformed(evt);
            }
        });
        jMenu2.add(btnLlenarConNumero);

        btnLlenarAleatoriamente.setText("Aleatoriamente");
        btnLlenarAleatoriamente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLlenarAleatoriamenteActionPerformed(evt);
            }
        });
        jMenu2.add(btnLlenarAleatoriamente);

        btnIdentidad.setText("Identidad");
        btnIdentidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentidadActionPerformed(evt);
            }
        });
        jMenu2.add(btnIdentidad);

        jMenu1.add(jMenu2);

        btnEliminar.setText("Eliminar fila/columna");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jMenu1.add(btnEliminar);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:
        actualizarComponentes();
    }//GEN-LAST:event_formAncestorAdded

    private void jsFilasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsFilasMouseReleased
        // TODO add your handling code here:
        int f = jsFilas.getValue();
        if (f == numeroFilas) {
            return;
        }
        if (f == jsFilas.getMaximum() && jsFilas.getMaximum() < 25) {
            jsFilas.setMaximum(f + 5);
        }

        if (((f > 12 && f == numeroColumnas) || f != numeroColumnas)) {
            if (!alerta2) {
                if (f > 12 && f == numeroColumnas) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados, tamaño muy grande de matriz.");
                } else {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados: "
                            + "\n     (Filas: " + f + " != Columnas: " + numeroColumnas + ") -> No cumple requisitos.");
                }
                btnDeterminante.setEnabled(false);
                btnInversa.setEnabled(false);
                btnAdjunta.setEnabled(false);
                alerta2 = true;
            }
        } else {
            if (alerta2) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados reactivados.");
                btnDeterminante.setEnabled(true);
                btnInversa.setEnabled(true);
                btnAdjunta.setEnabled(true);
                alerta2 = false;
            }
        }

        tfFilas.setText(Integer.toString(f));
        leerDatos();
        eliminarComponentes();
        numeroFilas = f;
        actualizarComponentes();
        reescribirMatriz();
        if (numeroColumnas > 6 && numeroFilas == numeroColumnas && numeroFilas > 6 && !alerta && numeroFilas < 13 && numeroColumnas < 13) {
            estado.setForeground(Color.orange);
            estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Calcular determinante o inversa bajo"
                    + "\nsu propio riesgo (Matriz grande).");
            alerta = true;
        } else if (alerta) {
            estado.setForeground(Color.white);
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Listo");
            alerta = false;
        }
    }//GEN-LAST:event_jsFilasMouseReleased

    private void tfColumnasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfColumnasKeyReleased
        // TODO add your handling code here:
        int c;
        try {
            c = Integer.parseInt(tfColumnas.getText());
            if (c > 30) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: No se redimensionó, tamaño muy grande de matriz en columnas.");
                estado.setForeground(Color.red);
                return;
            }
            if (c > jsColumnas.getMaximum() && c <= 30) {
                jsColumnas.setMaximum(c);
            }
            if (((c > 12 && c == numeroFilas) || c != numeroFilas)) {
                if (!alerta2) {
                    if (c > 12 && c == numeroFilas) {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados, tamaño muy grande de matriz.");
                    } else {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados: "
                                + "\n     (Filas: " + numeroFilas + " != Columnas: " + c + ") -> No cumple requisitos.");
                    }
                    btnDeterminante.setEnabled(false);
                    btnInversa.setEnabled(false);
                    btnAdjunta.setEnabled(false);
                    alerta2 = true;
                }
            } else {
                if (alerta2) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados reactivados.");
                    btnDeterminante.setEnabled(true);
                    btnInversa.setEnabled(true);
                    btnAdjunta.setEnabled(true);
                    alerta2 = false;
                }
            }

            jsColumnas.setValue(c);
            /*
             if (limiteExcedidoColumna(c)) {
             System.out.println("Debería de redimensionar.");
             this.setSize(limitec * 1000, this.getHeight());
             }
             */
        } catch (NumberFormatException ex) {
            estado.setText("(" + nombre + ") Error: Filas con formato incorrecto, asumiéndolo como 1");
            estado.setForeground(Color.RED);
            jsColumnas.setValue(1);
            c = 1;
        }
        leerDatos();
        eliminarComponentes();
        numeroColumnas = c;
        actualizarComponentes();
        reescribirMatriz();
        if (numeroColumnas > 6 && numeroFilas > 6 && numeroFilas == numeroColumnas && !alerta && numeroFilas < 13 && numeroColumnas < 13) {
            estado.setForeground(Color.orange);
            estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Calcular determinante o inversa bajo "
                    + "\nsu propio riesgo (Matriz grande).");
            alerta = true;
        } else if (alerta) {
            estado.setForeground(Color.white);
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Listo");
            alerta = false;
        }
    }//GEN-LAST:event_tfColumnasKeyReleased

    private void tfFilasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFilasKeyReleased
        // TODO add your handling code here:
        int f;
        try {
            f = Integer.parseInt(tfFilas.getText());
            if (f > 30) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: No se redimensionó, tamaño muy grande de matriz en Filas.");
                estado.setForeground(Color.red);
                return;
            }
            if (f > jsFilas.getMaximum() && f <= 30) {
                jsFilas.setMaximum(f);
            }

            if (((f > 12 && f == numeroColumnas) || f != numeroColumnas)) {
                if (!alerta2) {
                    if (f > 12 && f == numeroColumnas) {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados, tamaño muy grande de matriz.");
                    } else {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados: "
                                + "\n     (Filas: " + f + " != Columnas: " + numeroColumnas + ") -> No cumple requisitos.");
                    }
                    btnDeterminante.setEnabled(false);
                    btnInversa.setEnabled(false);
                    btnAdjunta.setEnabled(false);
                    alerta2 = true;
                }
            } else {
                if (alerta2) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados reactivados.");
                    btnDeterminante.setEnabled(true);
                    btnInversa.setEnabled(true);
                    btnAdjunta.setEnabled(true);
                    alerta2 = false;
                }
            }

            jsFilas.setValue(f);
            estado.setText("Estado: Listo.");
            estado.setForeground(Color.white);
        } catch (NumberFormatException ex) {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Filas con formato incorrecto, asumiéndolo como 1");
            estado.setForeground(Color.RED);
            jsFilas.setValue(1);
            f = 1;
        }
        leerDatos();
        eliminarComponentes();
        numeroFilas = f;
        actualizarComponentes();
        reescribirMatriz();
        if (numeroColumnas > 6 && numeroFilas > 6 && numeroFilas == numeroColumnas && !alerta && numeroFilas < 13 && numeroColumnas < 13) {
            estado.setForeground(Color.orange);
            estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Calcular determinante o inversa bajo "
                    + "\nsu propio riesgo (Matriz grande).");
            alerta = true;
        } else if (alerta) {
            estado.setForeground(Color.white);
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Listo");
            alerta = false;
        }
    }//GEN-LAST:event_tfFilasKeyReleased

    private void jsColumnasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsColumnasMouseReleased
        // TODO add your handling code here:
        int c = jsColumnas.getValue();
        if (c == numeroColumnas) {
            return;
        }
        if (c == jsColumnas.getMaximum() && jsColumnas.getMaximum() < 25) {
            jsColumnas.setMaximum(c + 5);
        }

        if (((c > 12 && c == numeroFilas) || c != numeroFilas)) {
            if (!alerta2) {
                if (c > 12 && c == numeroFilas) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados, tamaño muy grande de matriz.");
                } else {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados desactivados: "
                            + "\n     (Filas: " + numeroFilas + " != Columnas: " + c + ") -> No cumple requisitos.");
                }
                btnDeterminante.setEnabled(false);
                btnInversa.setEnabled(false);
                btnAdjunta.setEnabled(false);
                alerta2 = true;
            }
        } else {
            if (alerta2) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Determinante y derivados reactivados.");
                btnDeterminante.setEnabled(true);
                btnInversa.setEnabled(true);
                btnAdjunta.setEnabled(true);
                alerta2 = false;
            }
        }

        tfColumnas.setText(Integer.toString(c));
        leerDatos();
        eliminarComponentes();
        numeroColumnas = c;
        actualizarComponentes();
        reescribirMatriz();
        if (numeroColumnas > 6 && numeroFilas > 6 && numeroFilas == numeroColumnas && !alerta && numeroFilas < 13 && numeroColumnas < 13) {
            estado.setForeground(Color.orange);
            estado.setText(estado.getText() + "\n(" + nombre + ") Alerta: Calcular determinante o inversa bajo "
                    + "\nsu propio riesgo (Matriz grande).");
            alerta = true;
        } else if (alerta) {
            estado.setForeground(Color.white);
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Listo");
            alerta = false;
        }
    }//GEN-LAST:event_jsColumnasMouseReleased

    private void btnTraspuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraspuestaActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            if (numeroFilas < numeroColumnas) {
            }
            leerDatos();
            if (lee) {
                matriz = matriz.traspuesta();
                reemplazarMatriz(matriz);
                estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Traspuesta lista.");
                estado.setForeground(Color.white);
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnTraspuestaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            this.reemplazarMatriz(new MatrizEnTripleta(numeroFilas, numeroColumnas));
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Matriz limpiada éxitosamente.");
            estado.setForeground(Color.white);
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnLlenarConNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLlenarConNumeroActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            String s;
            double d;
            s = JOptionPane.showInputDialog("Ingrese el número: ");
            try {
                d = Double.parseDouble(s);
                rellenarCon(d);
                estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Matriz rellenada con " + d + " éxitosamente");
                estado.setForeground(Color.white);
            } catch (NumberFormatException ex) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: '" + s + "' no es un número.");
                estado.setForeground(Color.red);
            } catch (NullPointerException e) {
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnLlenarConNumeroActionPerformed

    private void btnLlenarAleatoriamenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLlenarAleatoriamenteActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            rellenarAleatoriamente();
            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Matriz rellenada éxitosamente.");
            estado.setForeground(Color.white);
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }

    }//GEN-LAST:event_btnLlenarAleatoriamenteActionPerformed

    private void btnIdentidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdentidadActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            if (numeroFilas == numeroColumnas) {
                convertirAIdentidad();
                estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Matriz convertida a identidad éxitosamente.");
                estado.setForeground(Color.white);
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: La identidad solo puede ser cuadrada.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnIdentidadActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            leerDatos();
            int i, j;
            if (lee) {
                try {
                    i = (Integer) Integer.parseInt(JOptionPane.showInputDialog("Ingrese fila: "));
                    j = (Integer) Integer.parseInt(JOptionPane.showInputDialog("Ingrese columna: "));
                    if (i > 0 && j > 0) {
                        matriz = matriz.extraerSinFilaColumna(i, j);
                        reemplazarMatriz(matriz);
                        estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Eliminación éxitosa.");
                        estado.setForeground(Color.white);
                    } else {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Error: No existe una fila o columna 0");
                        estado.setForeground(Color.red);
                    }
                } catch (NumberFormatException ex) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Fila o columna ingresada incorrectamente.");
                    estado.setForeground(Color.RED);
                } catch (NullPointerException e) {
                }
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed
    private void btnDeterminanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeterminanteActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            leerDatos();
            if (numeroFilas != numeroColumnas) {

                estado.setText(estado.getText() + "\n(" + nombre + ") Error: La matriz no es cuadrada (" + numeroFilas + "x" + numeroColumnas + "), no tiene determinante.");
                estado.setForeground(Color.RED);
                return;
            }
            if (lee) {
                hilo = new Thread("Determinante") {
                    @Override
                    public void run() {
                        double determinante;
                        int det;

                        operando = true;
                        determinante = matriz.calcularDeterminante();

                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SubventanaMatriz.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (determinante % 1 == 0) {
                            det = (int) determinante;
                            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Determinante: " + det + ".");
                        } else {
                            estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Determinante: " + determinante + ".");
                        }

                        estado.setForeground(Color.white);
                        operando = false;
                    }
                };
                hilo.start();
                estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Calculando determinante.");
                estado.setForeground(Color.white);
                /**
                 * try { determinante = matriz.calcularDeterminante();
                 * estado.setText(estado.getText() + "\n(" + nombre + ") Estado:
                 * Determinante: " + determinante + ".");
                 * estado.setForeground(Color.white); } catch
                 * (NumberFormatException ex) { estado.setText(estado.getText()
                 * + "\n(" + nombre + ") Error: Fila o columna ingresada
                 * incorrectamente."); estado.setForeground(Color.RED); }
                 */
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnDeterminanteActionPerformed

    private void btnMultiplicarPorEscalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiplicarPorEscalarActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            leerDatos();
            double i;
            if (lee) {
                try {
                    i = (Double) Double.parseDouble(JOptionPane.showInputDialog("Ingrese fila: "));
                    matriz.multiplicarPorEscalar(i);
                    reemplazarMatriz(matriz);
                    estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Multiplicación por " + i + " exitosa.");
                    estado.setForeground(Color.white);
                } catch (NumberFormatException ex) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Fila o columna ingresada incorrectamente.");
                    estado.setForeground(Color.RED);
                } catch (NullPointerException e) {
                }
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnMultiplicarPorEscalarActionPerformed

    private void btnInversaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInversaActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            if (numeroFilas != numeroColumnas) {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: La matriz no es cuadrada (" + numeroFilas + "x" + numeroColumnas + "), no tiene inversa.");
                estado.setForeground(Color.RED);
                return;
            }

            hilo = new Thread("Inversa") {
                @Override
                public void run() {
                    operando = true;
                    MatrizEnTripleta inv = matriz.inversa();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubventanaMatriz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (inv != null) {
                        reemplazarMatriz(inv);
                        estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Inversa éxitosa.");
                        estado.setForeground(Color.white);
                    } else {
                        estado.setText(estado.getText() + "\n(" + nombre + ") Error: Matriz no tiene inversa.");
                        estado.setForeground(Color.red);
                    }
                    operando = false;
                }
            };
            leerDatos();
            if (lee) {
                try {
                    hilo.start();
                    estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Realizando inversa.");
                    estado.setForeground(Color.white);
                } catch (NumberFormatException ex) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Fila o columna ingresada incorrectamente.");
                    estado.setForeground(Color.RED);
                } catch (NullPointerException e) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Retorna inversa nula (?).");
                    estado.setForeground(Color.RED);
                }
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnInversaActionPerformed

    private void btnAdjuntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdjuntaActionPerformed
        // TODO add your handling code here:
        if (!operando) {
            if (numeroFilas != numeroColumnas) {

                estado.setText(estado.getText() + "\n(" + nombre + ") Error: La matriz no es cuadrada (" + numeroFilas + "x" + numeroColumnas + "), no tiene adjunta.");
                estado.setForeground(Color.RED);
                return;
            }

            hilo = new Thread("Adjunta") {
                @Override
                public void run() {
                    operando = true;
                    matriz = matriz.matrizDeCofactores();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubventanaMatriz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    reemplazarMatriz(matriz);
                    estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Adjunta éxitosa.");
                    estado.setForeground(Color.white);
                    operando = false;
                }
            };
            leerDatos();
            if (lee) {
                try {
                    hilo.start();
                    estado.setText(estado.getText() + "\n(" + nombre + ") Estado: Calculando adjunta.");
                    estado.setForeground(Color.white);
                } catch (NumberFormatException ex) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Fila o columna ingresada incorrectamente.");
                    estado.setForeground(Color.RED);
                } catch (NullPointerException e) {
                    estado.setText(estado.getText() + "\n(" + nombre + ") Error: Retorna inversa nula (?).");
                    estado.setForeground(Color.RED);
                }
            } else {
                estado.setText(estado.getText() + "\n(" + nombre + ") Error: Uno o más datos ingresados incorrectamente.");
                estado.setForeground(Color.RED);
            }
        } else {
            estado.setText(estado.getText() + "\n(" + nombre + ") Error: Esperando a otro proceso.");
            estado.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btnAdjuntaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnAdjunta;
    private javax.swing.JMenuItem btnDeterminante;
    private javax.swing.JMenuItem btnEliminar;
    private javax.swing.JMenuItem btnIdentidad;
    private javax.swing.JMenuItem btnInversa;
    private javax.swing.JMenuItem btnLimpiar;
    private javax.swing.JMenuItem btnLlenarAleatoriamente;
    private javax.swing.JMenuItem btnLlenarConNumero;
    private javax.swing.JMenuItem btnMultiplicarPorEscalar;
    private javax.swing.JMenuItem btnTraspuesta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JSlider jsColumnas;
    private javax.swing.JSlider jsFilas;
    private javax.swing.JTextField tfColumnas;
    private javax.swing.JTextField tfFilas;
    // End of variables declaration//GEN-END:variables

    private void rellenarCon(double d) {
        matriz = new MatrizEnTripleta(numeroFilas, numeroColumnas);
        Tripleta t;
        int i, j;
        for (i = 0; i < numeroFilas; i++) {
            for (j = 0; j < numeroColumnas; j++) {
                t = new Tripleta(i + 1, j + 1, d);
                matriz.insertaTripleta(t);
                valores[i][j].setForeground(Color.BLUE);
            }
        }
        reemplazarMatriz(matriz);
    }

    private void rellenarAleatoriamente() {
        matriz = new MatrizEnTripleta(numeroFilas, numeroColumnas);
        Tripleta t;
        Random c = new Random();
        int i, j;
        for (i = 0; i < numeroFilas; i++) {
            for (j = 0; j < numeroColumnas; j++) {
                t = new Tripleta(i + 1, j + 1, c.nextInt(198) - 99);
                matriz.insertaTripleta(t);
                valores[i][j].setForeground(Color.BLUE);
            }
        }
        reemplazarMatriz(matriz);
    }

    private void convertirAIdentidad() {
        matriz = new MatrizEnTripleta(numeroFilas, numeroFilas);
        int i;
        Tripleta t;
        for (i = 0; i < numeroFilas; i++) {
            t = new Tripleta(i + 1, i + 1, 1);
            matriz.insertaTripleta(t);
            valores[i][i].setForeground(Color.BLUE);
        }
        reemplazarMatriz(matriz);
    }
}
