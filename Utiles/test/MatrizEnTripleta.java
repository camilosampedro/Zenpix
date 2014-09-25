
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import MatricesDispersas.Tripleta;

/**
 * MatrizEnTripleta dispersa representada en forma de tripletas.
 *
 * @author camilo
 */
public class MatrizEnTripleta {

    private Tripleta v[], aux[];

    public MatrizEnTripleta(Tripleta t) {
        int n;
        n = (Integer) t.retornaValor();
        v = new Tripleta[n + 2];
        v[0] = t;
    }

    public MatrizEnTripleta(int f, int c) {
        Tripleta t = new Tripleta(f, c, 0);
        v = new Tripleta[2];
        v[0] = t;
    }

    public boolean esVacia() {
        Tripleta t = retornaTripleta(0);
        if ((Integer) t.retornaValor() == 0) {
            return true;
        }
        return false;
    }

    public int numeroFilas() {
        return v[0].retornaFila();
    }

    public int numeroColumnas() {
        return v[0].retornaColumna();
    }

    public int numeroTripletas() {
        return (int) v[0].retornaValor();
    }

    public Tripleta retornaTripleta(int i) {
        if (i < v.length) {
            return v[i];
        }
        return null;
    }

    public void asignaNumeroTripletas(int datos) {
        int antiguo;
        antiguo = numeroTripletas();
        aux = v;
        Tripleta ti, tj;
        ti = aux[0];
        tj = new Tripleta(ti.retornaFila(), ti.retornaColumna(), datos);
        v = new Tripleta[datos + 2];
        v[0] = tj;
        for (int i = 1; i < datos; i++) {
            v[i] = aux[i];
        }

    }

    /**
     * Muestra todos los elementos de la matriz.
     */
    public void muestraMatriz() {
        int p, f, c, i;
        double val;
        p = numeroTripletas();
        f = v[0].retornaFila();
        c = v[0].retornaColumna();
        val = (int) v[0].retornaValor();
        System.out.println("\n\nCabeza: " + Integer.toString(f) + ", " + Integer.toString(c) + ", " + Double.toString(val));
        for (i = 1; i <= p; i++) {
            if (v[i] == null) {
                System.err.println("Nulo en i: " + i);
                return;
            }
            f = v[i].retornaFila();
            c = v[i].retornaColumna();
            val = (Double) v[i].retornaValor();
            //Necesaria modificación para GUI.
            System.out.println(Integer.toString(f) + ", " + Integer.toString(c) + ", " + Double.toString(val));
        }
    }

    public void asignaTripleta(Tripleta t, int i) {
        if (t == null) {
            System.err.println("Tripleta vacía.");
            return;
        }
        /*
         if (i > numeroTripletas()) {
         System.err.println("No existen tantas tripletas como: " + i + ", n = " + numeroTripletas());
         return;
         }
         */
        int c = t.retornaColumna();
        int f = t.retornaFila();
        Tripleta nt;
        if (t.retornaValor() instanceof Double) {
            double val = (Double) t.retornaValor();
            nt = new Tripleta(f, c, val);
        } else {
            nt = new Tripleta(f, c, (Integer) t.retornaValor());
        }
        v[i] = nt;
    }

    /**
     * Inserta la tripleta t en su lugar correspondiente. La matriz está
     * ordenada por filas y luego por columnas de menor a mayor.
     *
     * @param t Tripleta a insertar.
     */
    public void insertaTripleta(Tripleta ti) {
        int i, j, datos;
        Tripleta t, tx;
        tx = retornaTripleta(0);
        datos = (Integer) tx.retornaValor();
        i = 1;
        t = retornaTripleta(i);
        while (i <= datos && t.retornaFila() < ti.retornaFila()) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        while (i <= datos && t.retornaFila() == ti.retornaFila() && t.retornaColumna() < ti.retornaColumna()) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        j = datos;
        datos = datos + 1;
        asignaNumeroTripletas(datos);
        while (j >= i) {
            v[j + 1] = v[j];
            j = j - 1;
        }
        v[i] = ti;
    }

    /**
     * @deprecated @return
     */
    public MatrizEnTripleta traspuestaRapida() {
        int m, n, p, i, j, s[];
        Tripleta ti, tx;
        MatrizEnTripleta b;
        m = numeroFilas();
        n = numeroColumnas();
        p = numeroTripletas();
        tx = new Tripleta(n, m, p);
        b = new MatrizEnTripleta(tx);
        s = new int[n + 2];

        for (i = 1; i < n; i++) {
            s[i] = 0;
        }

        for (i = 1; i <= p; i++) {
            ti = retornaTripleta(i);
            s[ti.retornaColumna()] = s[ti.retornaColumna()] + 1;
        }

        s[n + 1] = p + 1;
        for (i = n; i > 0; i--) {
            s[i] = s[i + 1] - s[i];
        }
        for (i = 1; i <= p; i++) {
            ti = retornaTripleta(i);
            j = ti.retornaColumna();
            tx = new Tripleta(j, ti.retornaFila(), ti.retornaValor());
            b.asignaTripleta(tx, s[j]);
            s[j] = s[j] + 1;
        }
        return b;
    }

    /**
     * Calcula la traspuesta2 de la matriz. La traspuesta2 de una matriz A
     * consiste en intercambiar las filas por las columnas.
     *
     * @deprecated
     * @see #numeroColumnas() int numeroColumnas().
     * @see #numeroFilas() int numeroFilas().
     * @see #numeroTripletas() int numeroTripletas().
     * @return MatrizEnTripleta traspuesta2 de la matriz llamante.
     */
    public MatrizEnTripleta traspuesta2() {
        int m, n, i, j, p, s[];
        Tripleta ti, tx;
        MatrizEnTripleta b;
        m = numeroFilas();
        n = numeroColumnas();
        p = numeroTripletas();
        tx = new Tripleta(n, m, p);
        b = new MatrizEnTripleta(tx);
        s = new int[n + 2];
        for (i = 1; i <= n; i++) {
            s[i] = 0;
        }
        s[n + 1] = p + 1;
        for (i = n; i > 0; i--) {
            s[i] = s[i + 1] - s[i];
        }
        for (i = 1; i <= p; i++) {
            ti = retornaTripleta(i);
            j = ti.retornaColumna();
            tx = new Tripleta(j, ti.retornaFila(), (Double) ti.retornaValor());
            b.asignaTripleta(tx, s[j]);
            s[j] = s[j] + 1;
        }
        return b;
    }

    /**
     *
     * @return Matriz traspuesta de la matriz.
     */
    public MatrizEnTripleta traspuesta() {
        int i, p, f, c;
        double v;
        Tripleta ti;
        p = numeroTripletas();
        ti = new Tripleta(numeroColumnas(), numeroFilas(), 0);
        MatrizEnTripleta b = new MatrizEnTripleta(ti);
        i = 1;
        while (i <= p) {
            ti = retornaTripleta(i);
            f = ti.retornaColumna();
            c = ti.retornaFila();
            v = (Double) ti.retornaValor();
            ti = new Tripleta(f, c, v);
            b.insertaTripleta(ti);
            i = i + 1;
        }
        return b;
    }

    /**
     * Calcula la traspuesta2 de la matriz. La traspuesta2 de una matriz A
     * consiste en intercambiar las filas por las columnas.
     *
     * @deprecated Método largo para traspuesta2.
     * @return MatrizEnTripleta traspuesta2 de la matriz llamante.
     */
    public MatrizEnTripleta traspuestaForma2() {
        Tripleta t;
        MatrizEnTripleta b;
        int m, n, i, f, c;
        double p, val;
        t = retornaTripleta(0);
        m = t.retornaFila();
        n = t.retornaColumna();
        p = (Integer) t.retornaValor();
        t = new Tripleta(n, m, 0);
        b = new MatrizEnTripleta(t);
        for (i = 1; i <= p; i++) {
            t = retornaTripleta(i);
            f = t.retornaFila();
            c = t.retornaColumna();
            val = (Double) t.retornaValor();
            t = new Tripleta(c, f, val);
            b.insertaTripleta(t);
        }
        return b;
    }

    /**
     * Calcula la traspuesta2 de la matriz. La traspuesta2 de una matriz A
     * consiste en intercambiar las filas por las columnas.
     *
     * @deprecated Método aún muy extenso.
     * @return MatrizEnTripleta traspuesta2 de la matriz llamante.
     */
    public MatrizEnTripleta traspuestaForma3() {
        Tripleta t;
        int m, n, f, c, k, i, j, p;
        double val;
        MatrizEnTripleta b;
        t = retornaTripleta(0);
        m = t.retornaFila();
        n = t.retornaColumna();
        p = (Integer) t.retornaValor();
        t = new Tripleta(n, m, p);
        b = new MatrizEnTripleta(t);
        k = 0;
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= m; j++) {

                t = retornaTripleta(j);
                f = t.retornaFila();
                c = t.retornaColumna();
                val = (Double) t.retornaValor();
                if (c == i) {
                    t = new Tripleta(c, f, val);
                    k = k + 1;
                    b.asignaTripleta(t, k);
                }
            }
        }
        return b;
    }

    public MatrizEnTripleta suma(MatrizEnTripleta b) {
        int ma, na, mb, nb, p, q, i, j, k, fi, fj, ci, cj;
        double vi, vj, ss;
        Tripleta ti, tj, tx;
        ma = numeroFilas();
        na = numeroColumnas();
        mb = b.numeroFilas();
        nb = b.numeroColumnas();
        p = numeroTripletas();
        q = b.numeroTripletas();
        if (ma != mb || na != nb) {
            System.err.println("Error: Matrices de diferentes dimensiones no se pueden sumar");
            return null;
        }
        ti = new Tripleta(ma, na, 0);
        MatrizEnTripleta c = new MatrizEnTripleta(ti);
        i = 1;
        j = 1;
        k = 0;
        while (i <= p && j <= q) {
            ti = retornaTripleta(i);
            tj = b.retornaTripleta(j);
            fi = ti.retornaFila();
            fj = tj.retornaFila();
            k = k + 1;
            c.asignaNumeroTripletas(k);
            switch (comparar(fi, fj)) {
                case -1:
                    c.asignaTripleta(ti, k);
                    i = i + 1;
                    break;
                case 1:
                    c.asignaTripleta(tj, k);
                    j = j + 1;
                    break;
                case 0:
                    ci = ti.retornaColumna();
                    cj = tj.retornaColumna();
                    switch (comparar(ci, cj)) {
                        case -1:
                            c.asignaTripleta(ti, k);
                            i = i + 1;
                            break;
                        case 1:
                            c.asignaTripleta(tj, k);
                            j = j + 1;
                            break;
                        case 0:
                            vi = (Double) ti.retornaValor();
                            vj = (Double) tj.retornaValor();
                            ss = vi + vj;
                            if (ss != 0) {
                                tx = new Tripleta(fi, ci, ss);
                                c.asignaTripleta(tx, k);
                            } else {
                                k = k - 1;

                            }
                            i = i + 1;
                            j = j + 1;
                            break;
                    }
            }
        }
        while (i <= p) {
            ti = retornaTripleta(i);
            k = k + 1;
            c.asignaNumeroTripletas(k);
            c.asignaTripleta(ti, k);
            i = i + 1;
        }
        while (j <= q) {
            tj = b.retornaTripleta(j);
            k = k + 1;
            c.asignaNumeroTripletas(k);
            c.asignaTripleta(tj, k);
            j = j + 1;
        }
        
        return c;
    }

    public MatrizEnTripleta multiplicacion(MatrizEnTripleta b) {
        int m, n, na, p, nb, i, j, k, filaActual, columnaActual, inicioFilaActual;
        double sti, stj, suma;
        Tripleta ti, tj, tx;
        MatrizEnTripleta bt, c;
        m = numeroFilas();
        n = numeroColumnas();
        na = numeroTripletas();
        if (n != b.numeroFilas()) {
            System.err.println("No se pueden multiplicar las matrices.");
            return null;
        }
        p = b.numeroColumnas();
        nb = b.numeroTripletas();
        tx = new Tripleta(m + 1, 0, 0);
        asignaTripleta(tx, na + 1);
        tx = new Tripleta(m, p, 0);
        c = new MatrizEnTripleta(tx);
        if (numeroTripletas() == 0 || b.numeroTripletas() == 0) {
            return c;
        }
        bt = b.traspuesta();
        tx = new Tripleta(p + 1, 0, 0);
        bt.asignaTripleta(tx, nb + 1);
        i = 1;
        ti = retornaTripleta(i);
        filaActual = ti.retornaFila();
        inicioFilaActual = i;
        k = 0;
        suma = 0;
        while (i <= na) {
            j = 1;
            tj = bt.retornaTripleta(j);
            columnaActual = tj.retornaColumna();
            while (j <= nb + 1) {
                tj = bt.retornaTripleta(j);
                if (ti.retornaFila() != filaActual) {
                    if (suma != 0) {
                        k = k + 1;
                        c.asignaNumeroTripletas(k);
                        tx = new Tripleta(filaActual, columnaActual, suma);
                        c.asignaTripleta(tx, k);
                        suma = 0;
                    }
                    while (tj.retornaFila() == columnaActual) {
                        j = j + 1;
                        tj = bt.retornaTripleta(j);
                    }
                    columnaActual = tj.retornaFila();
                    i = inicioFilaActual;
                    ti = retornaTripleta(i);
                    continue;
                }
                if (tj.retornaFila() != columnaActual) {
                    if (suma != 0) {
                        k = k + 1;
                        c.asignaNumeroTripletas(k);
                        tx = new Tripleta(filaActual, columnaActual, suma);
                        c.asignaTripleta(tx, k);
                        suma = 0;
                    }
                    columnaActual = tj.retornaFila();
                    i = inicioFilaActual;
                    ti = retornaTripleta(i);
                    continue;
                }
                if (ti.retornaColumna() < tj.retornaColumna()) {
                    i = i + 1;
                    ti = retornaTripleta(i);
                    continue;
                }
                if (ti.retornaColumna() == tj.retornaColumna()) {
                    sti = (double) ti.retornaValor();
                    stj = (double) tj.retornaValor();
                    suma = suma + sti * stj;
                    i = i + 1;
                    j = j + 1;
                    ti = retornaTripleta(i);
                    //tj = bt.retornaTripleta(j);
                    continue;
                }
                j = j + 1;
            }
            while (ti.retornaFila() == filaActual) {
                i = i + 1;
                ti = retornaTripleta(i);
            }
            inicioFilaActual = i;
            filaActual = ti.retornaFila();
        }
        return c;
    }

    public void multiplicarPorEscalar(double escalar) {
        if (esVacia()) {
            return;
        }
        int i = 1;
        Tripleta ti;
        double dato;
        while (i < numeroTripletas()) {
            ti = retornaTripleta(i);
            dato = (Double) ti.retornaValor();
            ti = new Tripleta(ti.retornaFila(), ti.retornaColumna(), dato * escalar);
            asignaTripleta(ti, i);
            i++;
        }
    }

    public double calcularDeterminante() {
        double determinante = 0;
        double pos;
        Object posicion;
        if (esVacia()) {
            return determinante;
        }
        int filas = numeroFilas();
        int columnas = numeroColumnas();
        MatrizEnTripleta submatriz;
        Tripleta t;
        if (filas == 1 && columnas == 1) {
            t = retornaTripleta(1);
            if (t.retornaFila() == 1 && t.retornaColumna() == 1) {
                return (Double) t.retornaValor();
            }
            return 0;
        }
        int j = 1;
        int signo = 1, i = 1;
        while (i <= numeroTripletas()) {
            t = retornaTripleta(i);
            while (j < t.retornaColumna()) {
                signo *= -1;
                j++;
            }
            if (t.retornaFila() == 1) {
                submatriz = extraerSinFilaColumna(1, t.retornaColumna());
                posicion = posicion(1, t.retornaColumna());
                if (posicion == null) {
                    pos = 0;
                } else {
                    pos = (Double) posicion;
                }
                determinante = determinante + signo * pos * submatriz.calcularDeterminante();
                signo = -signo;
            } else {
                break;
            }
            j = t.retornaColumna() + 1;
            i = i + 1;
        }
        return determinante;
    }

    public MatrizEnTripleta extraerSinFilaColumna(int fila, int columna) {
        MatrizEnTripleta nueva = new MatrizEnTripleta(numeroFilas() - 1, numeroColumnas() - 1);
        int i = 1, c, f;
        Tripleta ti, tx;
        while (i <= numeroTripletas()) {
            ti = retornaTripleta(i);
            if (ti.retornaFila() != fila && ti.retornaColumna() != columna) {
                f = ti.retornaFila();
                c = ti.retornaColumna();
                if (f > fila) {
                    f = f - 1;
                }
                if (c > columna) {
                    c = c - 1;
                }
                tx = new Tripleta(f, c, ti.retornaValor());
                nueva.insertaTripleta(tx);
            }
            i = i + 1;
        }
        return nueva;
    }

    public Object posicion(int fila, int columna) {
        int i, datos;
        Tripleta t, tx;
        tx = retornaTripleta(0);
        datos = (Integer) tx.retornaValor();
        i = 1;
        t = retornaTripleta(i);
        while (i <= datos && t.retornaFila() < fila) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        while (i <= datos && t.retornaFila() == fila && t.retornaColumna() < columna) {
            i = i + 1;
            t = retornaTripleta(i);
        }
        if (v[i].retornaFila() == fila && v[i].retornaColumna() == columna) {
            return v[i].retornaValor();
        }
        return null;
    }

    

    private int comparar(int numero1, int numero2) {
        if (numero1 < numero2) {
            return -1;
        }
        if (numero1 > numero2) {
            return 1;
        }
        return 0;
    }
}
