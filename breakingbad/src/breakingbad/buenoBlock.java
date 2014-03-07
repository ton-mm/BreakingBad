/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;

import java.awt.Image;

public class buenoBlock extends baseBlock{
    
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
         
        private static final String desaparece = "DESAPARECE";
        private static final String pausado = "PAUSADO";  
        
       /* public getpausado()
        {
            return pausado;
        }
        */
    
	public buenoBlock(int posX,int posY)
        {
		super(posX,posY);	
	}
        
        public boolean tiene (int posX,int posY)
        {
            return getPerimetro().contains(posX, posY);
        }
    
}
