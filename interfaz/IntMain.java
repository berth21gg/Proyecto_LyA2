package interfaz;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Element;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import analizadores.lexico.alfabeto;
import analizadores.lexico.token;
import analizadores.sintaxis.clasificadorLinea;
import analizadores.sintaxis.pilaBloques;
import analizadores.sintaxis.consola;
import errores.nodoError;
import errores.pilaError;
import tblSimbolos.simbolo;
import tblSimbolos.tblSimbolo;

public class IntMain extends JFrame implements ActionListener { // Extension de Interfaz y Eventos
    //URL para cuando se abra un archivo
    String url = "";;
    // Declaracion de Atributos para la interfaz
    String rojo = "https://images.emojiterra.com/google/noto-emoji/v2.034/512px/1f534.png";
    String amarillo = "https://images.emojiterra.com/google/noto-emoji/v2.034/512px/1f7e1.png";
    String verde = "https://images.emojiterra.com/google/noto-emoji/v2.034/512px/1f7e2.png";
    clasificadorLinea cl;
    consola consola = new consola();    
    JFrame frame = new JFrame("Chat Frame");
    JTextField tf = new JTextField(30); // Longitud de 30 caracteres
    JButton compilar = new JButton("Compilar");
    JButton buscar = new JButton("Buscar");
    JTextArea lines;
    JScrollPane jsp =new JScrollPane();
    JTextArea ta = new JTextArea(); // Editor de codigo
    JTextArea ta2 = new JTextArea();
    DefaultTableModel modelT = new DefaultTableModel(); 
    JTable tabla = new JTable(modelT); 
    JMenuBar menuBar = new JMenuBar(); //Barra superior del menu
    JMenu menu1 = new JMenu("Archivo");
    JMenu menu2 = new JMenu("Compilador");
    JMenu menu3 = new JMenu("Ayuda");
    JMenu menu4 = new JMenu("Acerca de");
    //Creacion de submenus
    JMenuItem menuItem11 = new JMenuItem("Abrir");
    JMenuItem menuItem12 = new JMenuItem("Compilar");
    JMenuItem menuItem13 = new JMenuItem("Guardar");
    JMenuItem menuItem14 = new JMenuItem("Guardar como");
    JMenuItem menuItem15 = new JMenuItem("Eliminar");
    JMenuItem menuItem21 = new JMenuItem("Limpiar editor de texto");
    JMenuItem menuItem22 = new JMenuItem("Limpiar tabla de símbolos");
    JMenuItem menuItem23 = new JMenuItem("Limpiar consola");
    JMenuItem menuItem24 = new JMenuItem("Limpiar todo");
    JMenuItem menuItem31 = new JMenuItem("Análisis léxico y sintáctico");
    JMenuItem menuItem32 = new JMenuItem("Análisis sintáctico");
    JMenuItem menuItem33 = new JMenuItem("Análisis semántico");
    JMenuItem menuItem34 = new JMenuItem("Documentación compilador");
    JMenuItem menuItem41 = new JMenuItem("Acerca de");
    JPanel panel = new JPanel(); // Opciones de compilacion desde archivos
    tblSimbolo tablaSimbolos = new tblSimbolo();
    pilaError PilaError = new pilaError();
    pilaBloques PilaBloques = new pilaBloques();
    JLabel imaSemaforo1 = new JLabel("<html> "+
    "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/>" +
    "   </div>" +
    "   <div style='margin-left: 20;'>" +
    "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
    "   </div>" +
    "</html>");
    
    // Inicializacion de la interfaz grafica
    public void initGui(){
        ta.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
               int caretPosition = ta.getDocument().getLength();
               Element root = ta.getDocument().getDefaultRootElement();
               String text = "1" + System.getProperty("line.separator");
                  for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                     text += i + System.getProperty("line.separator");
                  }
               return text;
            }
            @Override
            public void changedUpdate(DocumentEvent de) {
               lines.setText(getText());
            }
            @Override
            public void insertUpdate(DocumentEvent de) {
               lines.setText(getText());
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
               lines.setText(getText());
            }
        });
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        tf.setEditable(false);
        lines.setEditable(false);
        ta2.setEditable(false);
        //abrirDoc(); // Abrir el archivo de ejemplo por default
        modelT.addColumn("Id");
        modelT.addColumn("No. Token");
        modelT.addColumn("Token");
        modelT.addColumn("Descripción");
        modelT.addColumn("Valor");
        ta2.setText("\n \n \n");
        // Configuracion del Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla Completa
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null); // Pantalla centrada
        // Agregacion de componentes a los menus
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menu1.add(menuItem11);
        menu1.add(menuItem12);
        menu1.add(menuItem13);
        menu1.add(menuItem14);
        menu1.add(menuItem15);
        menu2.add(menuItem21);
        menu2.add(menuItem22);
        menu2.add(menuItem23);
        menu2.add(menuItem24);
        menu3.add(menuItem31);
        menu3.add(menuItem32);
        menu3.add(menuItem33);
        menu3.add(menuItem34);
        menu4.add(menuItem41);
        menuItem11.addActionListener(this);
        menuItem12.addActionListener(this);
        menuItem13.addActionListener(this);
        menuItem14.addActionListener(this);
        menuItem15.addActionListener(this);
        menuItem21.addActionListener(this);
        menuItem22.addActionListener(this);
        menuItem23.addActionListener(this);
        menuItem24.addActionListener(this);
        menuItem31.addActionListener(this);
        menuItem32.addActionListener(this);
        menuItem33.addActionListener(this);
        menuItem34.addActionListener(this);
        menuItem41.addActionListener(this);
        //Agregacion del menu al frame
        frame.setJMenuBar(menuBar);
        // Creacion del Panel
        JLabel label = new JLabel("Seleccionar código fuente ");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(buscar);
        panel.add(compilar);
        panel.add(imaSemaforo1);
        // Configuracion del editor de texto
        // Configuracion del editor de texto
        Font font = new Font("Monospaced", Font.BOLD, 17);
        ta.setFont(font);
        lines.setFont(font);
        ta.setForeground(Color.CYAN); // Letra
        ta.setBackground(Color.BLACK); // Fondo
        ta.setCaretColor(Color.WHITE);
        jsp.getViewport().add(ta);
        jsp.setRowHeaderView(lines);
        jsp.setBounds(10,50,400,300);
        add(jsp);
        ta2.setEditable(false);
        ta2.setFont(font);
        ta2.setForeground(Color.BLACK); // Letra
        ta2.setBackground(Color.BLACK); // Fondo
        JScrollPane spr = new JScrollPane(ta2);
        spr.setMinimumSize(new DimensionUIResource(WIDTH, 300));// Scroll del editor
        //spr.setBounds(10,50,400,300);
        add(spr);
        // Se agregan los componentes al Frame en la posicion adecuada
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        //frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, jsp);
        frame.getContentPane().add(BorderLayout.SOUTH, spr);
        frame.getContentPane().add(BorderLayout.WEST,new JScrollPane(tabla));
        // Acciones de click para los botones
        compilar.addActionListener(this);
        buscar.addActionListener(this);
        frame.setVisible(true); // Se muestra la ventana
    }

    // Acciones para los botones
    @Override public void actionPerformed(ActionEvent e) { 
        // Control de Eventos (clicks del usuario)
        Object click = e.getSource();
        // Interfaz de la ventana desplegable para seleccionar un archivo
        if (click == buscar || click == menuItem11) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Seleccionar código fuente");
            jfc.setAcceptAllFileFilterUsed(false); // Limitar extensiones de los archivos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto plano", "txt", "e");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(null);
            // Cuando seleccionamos un archivo correcto
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                //System.out.println(jfc.getSelectedFile().getPath());
                String texto = "", full = "";
                try {
                    url = jfc.getSelectedFile().getPath();
                    tf.setText(url); // Localizacion
                    // Creamos un archivo FileReader que obtiene lo que tenga el archivo
                    FileReader lector = new FileReader(url);
                    //El contenido de lector se guarda en un BufferedReader
                    BufferedReader contenido = new BufferedReader(lector);
                    //Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
                    while((texto=contenido.readLine())!=null) { // Mediante saltos de linea
                        //System.out.println(texto);
                        full += texto+"\n"; // Concatena cada linea y agrega un salto
                    }
                    contenido.close();
                    ta.setText(full);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getCause(), "Exception", JOptionPane.PLAIN_MESSAGE);
                }
            }

        // Boton de compilar
        } else if(click == compilar || click == menuItem12){
            this.PilaError = new pilaError();
            this.PilaBloques = new pilaBloques();
            this.tablaSimbolos = new tblSimbolo();
            System.out.println("Compilando...");
            alfabeto alfa = new alfabeto();
            boolean bandAlf=true;
            //System.out.println("l " + lineas.length);
            String[] lineas = ta.getText().split("\n");
            int li;
            for( li=0;lineas.length>li&&bandAlf;li++)
               if(!alfa.validar(lineas[li]))
               bandAlf=false;
            if(bandAlf){
                token t = new token();
                //JOptionPane.showMessageDialog(null, "Análisis Léxico...", "Compilando", JOptionPane.PLAIN_MESSAGE);         
                // CLASIFICADOR DE LINEAS
                cl = new clasificadorLinea(lineas, t, this.tablaSimbolos, this.PilaError, PilaBloques, consola);
                cl.analisisSintactico();
                //System.out.println(t.variables);
                //JOptionPane.showMessageDialog(null, "Análisis Sintáctico...", "Compilando", JOptionPane.PLAIN_MESSAGE);
                // Tabla de Simbolos
                String text ="";
                /*  for(int i=0;lineas.length>i;i++){
                        String[] s = t.getListTokens(lineas[i]);
                        text +=(i+1)+": ";
                        for(int j=0;s.length>j;j++){
                            String[] d =  s[j].split(",");
                            this.tablaSimbolos.m_agreSimbolo(new simbolo(d));
                            text+= d[0] +" ";
                        }
                        text+= "\n";
                    }*/
                if(this.PilaError.estaVacia()){
                    ta2.setForeground(Color.GREEN);
                    text = "El programa se ejecutó sin errores\n"+consola.getConsola()+"\n";
                    consola.vaciar();
                    ta2.setText(text);
                    imaSemaforo1.setText("<html> "+
                    "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/>" +
                    "   </div>" +
                    "   <div style='margin-left: 20;'>" +
                    "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                    "   </div>" +
                    "</html>");
                    /* 
                    //Parametros asociados a la ventana
                    JFrame con = new JFrame();
                    con.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    con.setSize(800, 500);
                    con.setTitle("Consola");
                    //Creamos el panel y lo añadimos a las pestañas
                    JPanel panel1=new JPanel();
                    //Componentes del panel1
                    //panel1.add(lbl);
                    panel1.add(ta2);
                    con.add(panel1);
                    con.setVisible(true);
                    */
                } 
                else{
                    System.out.println("fs");
                    imaSemaforo1.setText("<html> "+
                    "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/>" +
                    "   </div>" +
                    "   <div style='margin-left: 20;'>" +
                    "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                    "   </div>" +
                    "</html>");
                    text = "Errores en tiempo de compilación\n";      
                    ta2.setForeground(Color.YELLOW); 
                    while(!PilaError.estaVacia()){
                        Object nodo = PilaError.pop();
                        String l = ((nodoError) nodo).getLinea();
                        String D = ((nodoError) nodo).getDescripcion();
                        String C = ((nodoError) nodo).getCodigo();
                        text += "línea: " + l + " Descripción: " + D + " Código del error: " + C+ "\n";
                        if((Integer.parseInt(C) >= 0) && (Integer.parseInt(C) < 100)){
                            imaSemaforo1.setText("<html> "+
                            "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + amarillo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/>" +
                            "   </div>" +
                            "   <div style='margin-left: 20;'>" +
                            "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                            "   </div>" +
                            "</html>");
                        }else if((Integer.parseInt(C) >= 100) && (Integer.parseInt(C) < 200)){
                            imaSemaforo1.setText("<html> "+
                            "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + amarillo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/>" +
                            "   </div>" +
                            "   <div style='margin-left: 20;'>" +
                            "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                            "   </div>" +
                            "</html>");
                        }else if((Integer.parseInt(C) >= 300) && (Integer.parseInt(C) < 400)){
                            imaSemaforo1.setText("<html> "+
                            "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + verde +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                            "       <img style='margin-left: 15;' width='20' height='20' src='" + amarillo +"'/>" +
                            "   </div>" +
                            "   <div style='margin-left: 20;'>" +
                            "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                            "   </div>" +
                            "</html>");
                        }
                    }
                    ta2.setText(text);
                    consola.vaciar();
                }
                //ta2.setText(text);
                limpiar();
                mostrarTabla();
            }
            else{
                imaSemaforo1.setText("<html> "+
                    "   <div style='margin-left: 20;'><label>&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>" +
                    "       <img style='margin-left: 15;' width='20' height='20' src='" + rojo +"'/>" +
                    "   </div>" +
                    "   <div style='margin-left: 20;'>" +
                    "       <pre>ANL-LE   ANL-SI   ANL-SE</pre>" +
                    "   </div>" +
                    "</html>");
                ta2.setForeground(Color.YELLOW); 
                ta2.setText("línea: "+li+" Descripción: Error de léxico, el token no esta declarado en el alfabeto Código del error: 0");
            }
        } else if(click == menuItem13){
            guardarArchivo();
            int tamanio = modelT.getRowCount();
                    for(int i=tamanio-1;i>=0;i--){
                        modelT.removeRow(i);
                    }
            tablaSimbolos.limpTabla();
            mostrarTabla();
            ta2.setText("");
        } else if(click == menuItem14){
            guardarComoArchivo();
            int tamanio = modelT.getRowCount();
                    for(int i=tamanio-1;i>=0;i--){
                        modelT.removeRow(i);
                    }
            tablaSimbolos.limpTabla();
            mostrarTabla();
            ta2.setText("");
        } else if(click == menuItem15){
            if(!new File(url).exists()){
                JOptionPane.showMessageDialog(null, "No se ha cargado ningún archivo para eliminar.", "Error eliminar", JOptionPane.WARNING_MESSAGE);
            }else{
                switch(createDialogEliminar(frame, url)){
                    case 0:
                        File file = new File(url);
                        System.out.println("URL:" + url);
                        file.delete();
                        JOptionPane.showMessageDialog(null, "Se ha eliminado con éxito el archivo " + url + ".", "Eliminado exitoso", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    default:
                        JOptionPane.showMessageDialog(null, "No se ha eliminado el archivo " + url + ".", "Eliminado incorrecto", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
        } else if(click == menuItem21){
            switch(createDialogLimpiar(frame, "el EDITOR DE TEXTO")){
                case 0:
                    ta.setText("");
                    createDialogLimpiado(frame, "el EDITOR DE TEXTO");
                break;
                default:
                    createDialogNoLimpiado(frame, "el EDITOR DE TEXTO");
                break;
            }
        } else if(click == menuItem22){
            switch(createDialogLimpiar(frame, "la TABLA DE SÍMBOLOS")){
                case 0:
                    int tamanio = modelT.getRowCount();
                    for(int i=tamanio-1;i>=0;i--){
                        modelT.removeRow(i);
                    }
                    tablaSimbolos.limpTabla();
                    mostrarTabla();
                    createDialogLimpiado(frame, "la TABLA DE SÍMBOLOS");
                break;
                default:
                    createDialogNoLimpiado(frame, "la TABLA DE SÍMBOLOS");
                break;
            }
        } else if(click == menuItem23){
            switch(createDialogLimpiar(frame, "la CONSOLA")){
                case 0:
                    ta2.setText("");
                    createDialogLimpiado(frame, "la CONSOLA");
                break;
                default:
                    createDialogNoLimpiado(frame, "la CONSOLA");
                break;
            }
        } else if(click == menuItem24){
            switch(createDialogLimpiar(frame, "el COMPILADOR COMPLETO")){
                case 0:
                    ta.setText("");
                    int tamanio = modelT.getRowCount();
                    for(int i=tamanio-1;i>=0;i--){
                        modelT.removeRow(i);
                    }
                    tablaSimbolos.limpTabla();
                    mostrarTabla();
                    ta2.setText("");
                    createDialogLimpiado(frame, "el COMPILADOR COMPLETO");
                break;
                default:
                    createDialogNoLimpiado(frame, "el COMPILADOR COMPLETO");
                break;
            }
        } else if(click == menuItem31){
            try {
                File path = new File ("recursos/PDFs/Actividad 4.pdf");
                Desktop.getDesktop().open(path);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(click == menuItem32){
            try {
                File path = new File ("recursos/PDFs/Actividad 5.pdf");
                Desktop.getDesktop().open(path);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(click == menuItem33){
            try {
                File path = new File ("recursos/PDFs/Actividad 6.pdf");
                Desktop.getDesktop().open(path);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(click == menuItem34){
            try {
                File path = new File ("recursos/PDFs/Documentacion compilador.pdf");
                Desktop.getDesktop().open(path);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(click == menuItem41){
            JDialog modelAcerca = createDialogAcerca(frame, panel);
            modelAcerca.setVisible(true);
        }
    }
    
    //Regresa e imprime la tabla de simbolos
    Object[] mostrarTabla(){
        //System.out.println("\nid      numToken         Token        Descripcion          valor");
        //System.out.println("---------------------------------------------------------");
        Object[] r = new simbolo[tablaSimbolos.tamanio()];
        for(int i=0;tablaSimbolos.tamanio()>i;i++){
            simbolo row = tablaSimbolos.getSimbolo(i);
            modelT.addRow(new Object[]{(i+1),row.getId(),row.getToken(),row.getDescripcion(),row.getValor()});
            //System.out.println((i+1)+ "   "+row.getId()+"     "+ row.getToken()+"     "+row.getDescripcion()+"         "+row.getValor());
        }
        return r;
    }

    void limpiar(){
       int i = modelT.getRowCount();
       for(int j=0;i>j;j++)
       modelT.removeRow(0);
    }

    //Creacion del modal Acerca de
    private static JDialog createDialogAcerca(JFrame frame, JPanel panel){
        String titulo, text;
        titulo = "Acerca de";
        text = "<html>"+
               "<body>" +
               "    <div style='text-align: center;'>"+
               "        <div style='display: flex; align-items: center; justify-content: center;'>" +
               "            <img width='100' height='100' src='https://sg.com.mx/sites/default/files/2018-04/LogoITCelaya.png'/>" +
               "        </div>" +
               "        <div style='display: flex; align-items: center; justify-content: center; margin-top: 5px;'>" +
               "            <label>~ Tecnológico Nacional de México en Celaya ~</label> <br>" +
               "            <label> Lenguajes y Autómatas ll</label> <br><br>" +
               "            <label> VERSIÓN: 1.00</label> <br>" +
               "            <label style=\"font-family: 'Roboto Medium', sans-serif; font-size:20px;\"> EQUIPO 3 </label> <br>" +
               "            <label> INTEGRANTES: </label> <br>" +
               "        </div>" +
               "        <div class='container' style='margin-top: 5px;'>" +
               "                <img width='80' height='80' src='https://i.ibb.co/QQvY9Rq/Foto-Cromo.jpg' style='margin-left: 70px;'/> <br>" +
               "                <label>- García Ramírez Luis David </label>" +
               "        </div>" +
               "        <div class='container' style='margin-top: 5px;'>" +
               "                <img width='80' height='80' src='https://i.ibb.co/fFvSMh2/Foto-Edu.png'/> <br>" +
               "                <label>- Pérez Cabrera José Eduardo </label>" +
               "        </div>" +
               "        <div class='container' style='margin-top: 5px;'>" +
               "                <img width='80' height='80' src='https://i.ibb.co/2vfvztF/Foto-Marco.jpg'/> <br>" +
               "                <label>- Ramírez García Marco Isaías </label>" +
               "        </div>" +
               "    </div>" +
               "</body></html> ";
        JDialog modal = new JDialog(frame, titulo, Dialog.ModalityType.DOCUMENT_MODAL);
        modal.setBounds(0,0,340,550);
        modal.setLocationRelativeTo(frame);
        Container container = modal.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new JLabel(text));
        JScrollPane scrollPane = new JScrollPane();
        modal.getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(new JLabel(text));
        return modal;
    }

    //Creacion del dialog para confirmar limpiar
    private int createDialogLimpiar(JFrame frame, String p_areaLimpiar){
        int input;
        input = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres limpiar " + p_areaLimpiar + "?", "Confirmación para limpiar", JOptionPane.YES_NO_OPTION);
        return input;
    }

    //Creacion del dialog para confirmacion de que se limpio
    private void createDialogLimpiado(JFrame frame, String p_areaLimpiar){
        JOptionPane.showMessageDialog(null, "Se ha limpiado con éxito " + p_areaLimpiar + ".", "Limpieza exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    //Creacion del dialog para no limpiado
    private void createDialogNoLimpiado(JFrame frame, String p_areaLimpiar){
        JOptionPane.showMessageDialog(null, "No se ha limpiado " + p_areaLimpiar + ".", "Limpieza incorrecta", JOptionPane.WARNING_MESSAGE);
    }

    //Creacion del dialog para guardado correcto
    private void createDialogGuardado(JFrame frame, String p_nombFichero){
        JOptionPane.showMessageDialog(null, "El fichero " + p_nombFichero + " se ha guardado exitosamente.", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    //Creacion del dialog para no no guardado
    private void createDialogNoGuardado(JFrame frame, String p_nombFichero){
        JOptionPane.showMessageDialog(null, "El fichero " + p_nombFichero + " no se ha guardado.", "Guardado incorrecto", JOptionPane.WARNING_MESSAGE);
    }

    //Creacion del dialog para eliminar
    private int createDialogEliminar(JFrame frame, String p_archivo){
        int input;
        input = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres eliminar el archivo " + p_archivo + "?", "Confirmación para eliminar", JOptionPane.YES_NO_OPTION);
        return input;
    }

    private void guardarArchivo(){
        try{
            if(new File(url).exists()){
                File file = new File(url);
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(ta.getText());
                bw.close();
                createDialogGuardado(frame, file + "");
            }
            else{
                guardarComoArchivo();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void guardarComoArchivo(){
        JFileChooser jF1 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        String ruta = "";
        try{
            if(jF1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                ruta = jF1.getSelectedFile().getAbsolutePath();
                System.out.println("RUTA:" + ruta);
                if(new File(ruta + ".txt").exists()){
                    File file = new File(ruta + ".txt");
                    if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this,"El fichero ya existe, ¿Deseas reemplazarlo?","Fichero ya existente",JOptionPane.YES_NO_OPTION)){
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(ta.getText());
                        bw.close();
                        createDialogGuardado(frame, file + "");
                    }else{
                        createDialogNoGuardado(frame, file + "");
                    }
                }else{
                    File file = new File(ruta);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(ta.getText());
                    bw.close();
                    createDialogGuardado(frame, file + "");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    void abrirDoc(){
        try {
            String texto = "", full = "";
            tf.setText(url); // Localizacion
            // Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader(url);
            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);
            //Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
            while((texto=contenido.readLine())!=null) { // Mediante saltos de linea
                //System.out.println(texto);
                full += texto+"\n"; // Concatena cada linea y agrega un salto
            }
            contenido.close();
            ta.setText(full);
        } catch (Exception ex) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, ex.getCause(), "Excepción", JOptionPane.PLAIN_MESSAGE);
        }
    }

}