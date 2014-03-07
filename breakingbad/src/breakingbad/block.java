/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;

/**
 *
 * @author tonystark
 */
import java.awt.Image;
import java.awt.Toolkit;

public class block extends baseBlock {
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
         
        private static final String desaparece = "DESAPARECE";
        private static final String pausado = "PAUSADO";  
        private boolean powerup = false;
    
	public block(int posX,int posY)
        {
		super(posX,posY);	
                //
                Image bloque1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/potion.png"));
                Image bloque2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/potion2.png"));
                
                anim = new Animacion();
                anim.sumaCuadro(bloque1, 100);
                anim.sumaCuadro(bloque2, 100);
                anim.sumaCuadro(bloque2, 100);
                anim.sumaCuadro(bloque1, 100);
	}
        
        public void setPowerup(boolean x)
        {
            powerup = x;
        }
        
        public boolean getPowerup()
        {
            return powerup;
        }
        
        public boolean tiene (int posX,int posY)
        {
            return getPerimetro().contains(posX, posY);
        }
    

}

