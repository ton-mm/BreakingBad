/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.LinkedList;

/**
 *
 * @author santoscr92
 */
public class programa extends JFrame implements Runnable, KeyListener,MouseListener,MouseMotionListener {
 private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int direccion;    // Direccion del elefante
    private int velocidad,aceleracion; // determinado por vidas
    private final int MIN = -6;    //Minimo al generar un numero al azar.
    private final int MAX = 7;    //Maximo al generar un numero al azar.
    private Image dbImage;      // Imagen a proyectar  
    private Image gameover;
    private Graphics dbg;       // Objeto grafico
    private SoundClip yay;    // Objeto AudioClip
    private SoundClip buuu;    // Objeto AudioClip
    private SoundClip bomb;    //Objeto AudioClip
    private SoundClip teleport;
    private Bueno pelota;    // Objeto de la clase Bueno
    private Malo barra;    //Objeto de la clase Malo
    private bloques bloque; // bloques
    private int ancho;  //Ancho del elefante
    private int alto;   //Alto del elefante
    private ImageIcon elefante; // Imagen del elefante.
    private int x1; // posicion del mouse en x
    private int y1; // posicion del mouse en y
    private int x_pos;
    private int y_pos;
    private int vidas = 1;
    private int score = 0;
    private boolean pausa = false;
    private boolean clic = false; //para saber cuando hace clic
    private boolean up,down,right,left; //movimiento de teclado
    private boolean pchocox,pchocoy; // bool pelota choco
    private double angulo; // angulo de la pelota
    private double px,py; // posicion de la pelota con formula
    private int intentos;
    private int velocidadx,velocidady;
    private double gravedad = 9.8;
    private double tiempo;
    private boolean btiempo;
    private boolean instrucciones = false;
    private boolean inst = false;
    private boolean pclic = false;
    
    //lista de bloques
    private LinkedList lista;
    private LinkedList listaOsos;
    
    
    //variables para el manejo de archivos
    private Vector vec;    // Objeto vector para agregar el puntaje.
    private String nombreArchivo;    //Nombre del archivo.
    private String[] arr;    //Arreglo del archivo divido.
    
    private boolean guardar = false; //bool para saber si se quiere guardar el juego
    private boolean cargar = false; //bool para saber si se quiere cargar el juego
    
    
    //Variables de control de tiempo de la animación
    private long tiempoActual;
    private long tiempoInicial;
    
    private Animacion anim;
    
    private block block1; // bloques
    private block block2; // bloques
    private int posX;
    private int posY;
    
    public programa() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void init() {
        
        nombreArchivo = "Puntaje.txt";
        vec = new Vector();
        
        lista = new LinkedList();
        direccion = 0;
        this.setSize(1000, 700);
        URL eURL = this.getClass().getResource("Imagenes/Bomb1.png");
        int dposy = getHeight() / 2 + getHeight() / 8;
        pelota = new Bueno(getWidth()/2, getHeight() - 100, Toolkit.getDefaultToolkit().getImage(eURL));
        //pelota.setPosX((int) (getWidth()/2));
        //pelota.setPosY(getHeight());
        int posrX =  getWidth()/2 ;    //posision x es tres cuartos del applet
        int posrY =   getHeight() ;  //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("Imagenes/barra.png");
        barra = new Malo(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
        barra.setPosX(barra.getPosX() - barra.getAncho()/2);
        barra.setPosY(barra.getPosY() - barra.getAlto());
        setBackground(Color.white);
        addKeyListener(this);
        URL goURL = this.getClass().getResource("Imagenes/perder.png");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);
       
        //Se cargan los sonidos.
        yay = new SoundClip("Sonidos/yay.wav");
        buuu = new SoundClip("Sonidos/buuu.wav");
        teleport = new SoundClip("Sonidos/teleport.wav");
 
        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL));
        ancho = elefante.getIconWidth();
        alto = elefante.getIconHeight();
        //ancho2 = barra.getIconWidth();
        // alto2 = barra.getIconHeight();
        addMouseListener(this);
        addMouseMotionListener(this);
        
        listaOsos = new LinkedList();

        //block2 = new block (500,500);
        
        // lista de Osos
        for(int k = 0; k < 13; k++)
        {
            for(int i = 0; i < 7 ; i++)
            {
                block1 = new block(30 + 70 * k, i * 30 + 70);
                listaOsos.addLast(block1); 
            }
        }
        
    /* 
      
        // lista de bloques
        for(int k = 0; k < 13; k++)
        {
            for(int i = 0; i < 7 ; i++)
            {
               URL aURL = this.getClass().getResource("Imagenes/meth.png");
                bloque = new bloques(30 + 70 * k, i * 30 + 70, Toolkit.getDefaultToolkit().getImage(aURL));
                lista.addLast(bloque); 
            }
        }
        */
        
        //Se cargan las imágenes(cuadros) para la animación
	Image bomba1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb1.png"));
	Image bomba2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb2.png"));
	Image bomba3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb3.png"));
	Image bomba4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb4.png"));
	//Image bomba5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb4.png"));
	//Image bomba6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb3.png"));
	//Image bomba7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb2.png"));
	//Image bomba8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/Bomb1.png"));
        
        //Se crea la animación
	anim = new Animacion();
	anim.sumaCuadro(bomba1, 100);
        anim.sumaCuadro(bomba2, 100);
        anim.sumaCuadro(bomba3, 100);
        anim.sumaCuadro(bomba4, 100);
        anim.sumaCuadro(bomba4, 100);
        anim.sumaCuadro(bomba3, 100);
        anim.sumaCuadro(bomba2, 100);
        anim.sumaCuadro(bomba1, 100);
        
 
    }
 
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        //Guarda el tiempo actual del sistema 
        
        
         while (true) {
            if (!pausa) {
                actualiza();
                checaColision();
                repaint(); // Se actualiza el <code>Applet</code> repintando el contenido.
            }
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }

        }

    }
    
    public void actualiza() {
        //
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;
        
        
            //Actualiza la animación en base al tiempo transcurrido
        
        for(int x=0; x<listaOsos.size(); x++) {
            
            block block1 = (block) listaOsos.get(x);
            block1.actualizaAnimacion(tiempoActual);
            
        }
        
        // block1.actualizaAnimacion(tiempoActual);
        
        
         
        if(vidas == 0)
        {
            //lista.clear();
            listaOsos.clear();
            // lista de bloques
        for(int k = 0; k < 13; k++)
        {
            for(int i = 0; i < 7 ; i++)
            {
               //URL aURL = this.getClass().getResource("Imagenes/meth.png");
               //bloque = new bloques(30 + 70 * k, i * 30 + 70, Toolkit.getDefaultToolkit().getImage(aURL));
               //lista.addLast(bloque); 
                
                //URL aURL = this.getClass().getResource("Imagenes/meth.png");
                block1 = new block(30 + 70 * k, i * 30 + 70);
                listaOsos.addLast(block1); 
            }
        }
        vidas ++;
            
        }
        
        // tiempo de jframe
        if(btiempo)
        {
           tiempo += .020; 
        }
       
        if(intentos == 3)
        {
            vidas--;
            intentos = 0;
            velocidad += 1;
        }
        
        if(!pausa)
        {
            if(clic)
            {
                //movimiento de pelota en x
                velocidadx = 5;
                velocidady = -5;
                
            
                //boolean para tiempo
                btiempo = true;
                clic = false;
            } 
        }
        
        if(left)
        {
            barra.setPosX(barra.getPosX() - 10);
            left = false;
        }
        else if(right)
        {
            barra.setPosX(barra.getPosX() + 10);
            right = false;
        }
        
        //si choca con la ventana se invierte la velocidad en X
        if(pchocox)
        {
            velocidadx *= -1;
            pchocox = false;
        }
        
        if(pchocoy)
        {
            velocidady *= -1;
            pchocoy = false;
        }
        
        pelota.setPosX(pelota.getPosX() + velocidadx);
        pelota.setPosY(pelota.getPosY() + velocidady);
        
        
        
        
        //limita el movimiento de la barra 
        if(barra.getPosX() <= 0)
        {
            barra.setPosX(0);
        }
        if(barra.getPosX() + barra.getAncho() >= getWidth())
        {
            barra.setPosX(getWidth() - barra.getAncho());
        }
    
 }
        
 
    public void checaColision() {
        
        //checa colision con la parte de la izquierda
        if(pelota.getPosX() + pelota.getAncho() >= getWidth() || pelota.getPosX() <= 0){
            pchocox = true;
        }
        
        // checa colision con la parte de la derecha
        if (pelota.getPosX() + pelota.getAncho() >= getWidth()) {
            pchocox = true;
        }
        
        
        // checa colision con la parte de abajo
        if (pelota.getPosY() + pelota.getAlto() >= getHeight()) {
            pchocoy = true;  
            velocidadx = 0;
            velocidady = 0;
            pelota.setPosX(getWidth()/2);
            pelota.setPosY(getHeight() - 100);
            clic = false;
            vidas--;
        }
        
        //checa colision con la parte de arriba
        if (pelota.getPosY() <= 0) {
            pchocoy = true;
        }
        
         if (pelota.intersecta(barra) && (pelota.getPosY() + pelota.getAlto() - 5) <= barra.getPosY()) {
            if(pelota.getPosX() + pelota.getAncho()/2 < barra.getPosX() + barra.getAncho()/2 && velocidadx > 0)
            {
                pchocox = true;
            }
            else if(pelota.getPosX() + pelota.getAncho()/2 > barra.getPosX() + barra.getAncho()/2 && velocidadx < 0)
            {
                pchocox = true;
            }
             pchocoy = true;  
        }
         
         //checa colision con bloques
         for (int i = 0; i < lista.size(); i++) {
            bloques bloque = (bloques) lista.get(i);
            
                // si le pelota pega abajo del bloque se le suma 100 al score
                if (pelota.intersecta(bloque) && (bloque.getPosY() + bloque.getAlto() - 15) < pelota.getPosY())
                {
                    
                    pchocoy = true;
                    lista.remove(i);
                    //score += 100;
                    
                }
                // si el planeta intersecta el asteroide por un lado se le resta una vida y 
                // aumenta la velocidad
                else if(pelota.intersecta(bloque) && bloque.getPosY() + bloque.getAlto() - 15 >= pelota.getPosY())
                {
                    pchocox = true;
                    lista.remove(i);
                }
                
            
        }
   
    }
 
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) 
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        
        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
 
        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);
 
        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paint1(Graphics g) {
        if (vidas>0){
        if (pelota != null && barra != null && listaOsos != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(pelota.getImagenI(), pelota.getPosX(), pelota.getPosY(), this);
            g.drawImage(barra.getImagenI(), barra.getPosX(), barra.getPosY(), this);
            //g.drawImage(block2.getImagenI(), block2.getPosX(), block2.getPosY(), this);
            
            
            for (int i = 0; i < listaOsos.size(); i++) {
            block block1 = (block) listaOsos.get(i);
            g.drawImage(block1.getImagenI(), block1.getPosX(), block1.getPosY(), this);
        }
            
 
        } else {
            //Da un mensaje mientras se carga el dibujo
            g.drawString("No se cargo la imagen..", 20, 20);
        }
        }
        else{
            g.drawImage(gameover, 400, 150, this);
        }
   
        //
        g.drawImage(barra.getImagenI(), barra.getPosX(), barra.getPosY(), this);
        
     /*
        for (int i = 0; i < lista.size(); i++) {
            bloques bloque = (bloques) lista.get(i);
            g.drawImage(bloque.getImagenI(), bloque.getPosX(), bloque.getPosY(), this);
        }
      */
        
        
        
        
        g.setColor(Color.black);
        g.drawString("Vidas: " + vidas, 15, 50);
        g.setColor(Color.black);
        g.drawString("Score: " + score, 70, 50); 
        g.setColor(Color.black);
        g.drawString("Intentos: " + intentos, 140, 50);
        
        /*if (pausa) {
                    g.setColor(Color.white);
                    g.drawString(barra.getpausado(), barra.getPosX() + barra.getAncho() / 3, barra.getPosY() + barra.getAlto() / 2);
                }
        */
                if (instrucciones ) {
                
               g.setColor(Color.black);
               g.drawString("INSTRUCCIONES: Para empezar el juego dar click a la pelota. Intenta cachar", 200, 200); 
                    g.drawString(    "la pelota con la barra. Mueve la barra con las flechas IZQ y DER", 200, 212); 
                     g.drawString(   "Para pausar el juego presiona 'p' ", 200, 225); 
                     g.drawString(   "Para guardar el juego presiona 'g'", 200, 238); 
                    g.drawString(    "Para cargar el juego presiona 'c'  ", 200, 250); 
            }
 
    }
    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo()  {
          try
          {
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                } catch (FileNotFoundException e){
                        File archivo = new File(nombreArchivo);
                        PrintWriter fileOut = new PrintWriter(archivo);
                        fileOut.println("50.0,50.0,45.0,.02,5,.02,50,50,200,200");
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                String dato = fileIn.readLine();
                arr = dato.split (",");
                velocidadx = (Integer.parseInt(arr[0]));
                velocidady = (Integer.parseInt(arr[1]));
                angulo = (Double.parseDouble(arr[2]));
                tiempo = (Double.parseDouble(arr[3]));
                vidas = (Integer.parseInt(arr[4]));
                //dificultad = (Double.parseDouble(arr[5]));
                pelota.setPosX((Integer.parseInt(arr[5])));
                pelota.setPosY((Integer.parseInt(arr[6])));
                barra.setPosX((Integer.parseInt(arr[7])));
                barra.setPosY((Integer.parseInt(arr[8])));
                //perdida = (Integer.parseInt(arr[10]));
                //pico = (Boolean.parseBoolean(arr[11]));
                
                fileIn.close();
          }
          catch(IOException ioe) {
              velocidadx = 0;
              velocidady = 0;
              angulo = 0;
              tiempo = 0;
              vidas = 0;
              //dificultad = 0;
              pelota.setPosX(0);
              pelota.setPosY(0);
              barra.setPosX(0);
              barra.setPosY(0);
              //perdida = 0;
              //pico = false;
              
              
          }
        }
    
    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
        
        //if(guardar) {
            
            
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
            fileOut.println(""+velocidadx+","+velocidady+","+angulo+","+tiempo+","+vidas+","+pelota.getPosX()+","+pelota.getPosY()+","+barra.getPosX()+","+barra.getPosY());
            fileOut.close();
           
           // guardar = false;
        //}
   	
    }  
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) //Presiono tecla P
        {    
            pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_I) //Presiono tecla I
        {    
            instrucciones = !instrucciones;
            //pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_G) //Presiono tecla G
        {  
            try{
			//leeArchivo();    //lee el contenido del archivo
			//vec.add(new Puntaje(nombre,score));    //Agrega el contenido del nuevo puntaje al vector.
			grabaArchivo();    //Graba el vector en el archivo.
		}catch(IOException i){
			System.out.println("Error en " + i.toString());
		}
            //guardar = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_C) //Presiono tecla C
        {   
            leeArchivo();
        }
       
        
        if (e.getKeyCode() == KeyEvent.VK_UP) //Presiono flecha arriba
        {    
            up = true;
	} 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Presiono flecha abajo
                {    
                    down = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Presiono flecha izquierda
                {    
			left = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Presiono flecha derecha
                {    
			right = true;
		}
        
         
    }
 
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
     public void mouseClicked(MouseEvent e) {
       
         if(!pausa)
         {
             x1 = e.getX();
        y1 = e.getY();
        if(pelota.tiene(x1, y1))
        {
            clic = true;
        }
         }
        
        
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
        
    
}
