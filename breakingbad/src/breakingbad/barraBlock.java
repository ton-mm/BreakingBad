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

public class barraBlock extends baseBlock {
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
         
        private static final String desaparece = "DESAPARECE";
        private static final String pausado = "PAUSADO";  
        private boolean powerup = false;
    
	public barraBlock(int posX,int posY)
        {
		super(posX,posY);	
                //
                Image bomba1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/barra.png"));
                Image bomba2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/barra.png"));
                Image bomba3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/barra.png"));
                Image bomba4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/barra.png"));
                
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

