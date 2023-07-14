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
    //Creacion de submenus
    JMenuItem menuItem11 = new JMenuItem("Abrir");
    JMenuItem menuItem12 = new JMenuItem("Compilar");
    JPanel panel = new JPanel(); // Opciones de compilacion desde archivos
    tblSimbolo tablaSimbolos = new tblSimbolo();
    pilaError PilaError = new pilaError();
    pilaBloques PilaBloques = new pilaBloques();
    
    
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
        menuItem11.addActionListener(this);
        menuItem12.addActionListener(this);
        // Creacion del Panel
        JLabel label = new JLabel("Seleccionar código fuente ");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(buscar);
        panel.add(compilar);
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
                // CLASIFICADOR DE LINEAS
                cl = new clasificadorLinea(lineas, t, this.tablaSimbolos, this.PilaError, PilaBloques, consola);
                cl.analisisSintactico();

                // Tabla de Simbolos
                String text ="";

                if(this.PilaError.estaVacia()){
                    ta2.setForeground(Color.GREEN);
                    text = "El programa se ejecutó sin errores\n"+consola.getConsola()+"\n";
                    consola.vaciar();
                    ta2.setText(text);
                    
                 
                } 
                else{
                    System.out.println("fs");
                    
                    text = "Errores en tiempo de compilación\n";      
                    ta2.setForeground(Color.YELLOW); 
                    while(!PilaError.estaVacia()){
                        Object nodo = PilaError.pop();
                        String l = ((nodoError) nodo).getLinea();
                        String D = ((nodoError) nodo).getDescripcion();
                        String C = ((nodoError) nodo).getCodigo();
                        text += "línea: " + l + " Descripción: " + D + " Código del error: " + C+ "\n";
                        
                    }
                    ta2.setText(text);
                    consola.vaciar();
                }
                //ta2.setText(text);
                limpiar();
                mostrarTabla();
            }
          
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