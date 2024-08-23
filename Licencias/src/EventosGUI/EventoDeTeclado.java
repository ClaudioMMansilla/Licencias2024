package EventosGUI;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowStateListener;

public class EventoDeTeclado implements KeyListener {

	public int codigo;

	@Override
	public void keyPressed(KeyEvent e) { // para escuchar tecla al presionar
		codigo = e.getKeyCode();
		System.out.println(codigo);
	}

	@Override
	public void keyReleased(KeyEvent e) { // para escucha tecla al soltar

	}

	@Override
	public void keyTyped(KeyEvent e) { // para escuchar que tecla fue presionada y soltada
		char letra = e.getKeyChar();
		System.out.println(letra);
	}
}

class CambiaEstado implements WindowStateListener {
	@Override
	public void windowStateChanged(java.awt.event.WindowEvent e) {
		System.out.println("el estado de la ventana ha cambiado");
		System.out.println(e.getNewState());//recibo el c�digo que identifica el tipo de evento o�do
	}
}