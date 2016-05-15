package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;


/**
 * Controlador encarregat de gestionar els botons encarregats de reproduir la musica
 * (play/pausa, next/back)
 * 
 *
 */

public class ControladorReproductor implements MouseListener {
	
	private String opcio;
	private boolean play = false;
	private boolean playing = false;
	private String nom;
	private String nomReproduccio;
	private String artista;

	
	public ControladorReproductor(String opcio) {
		this.opcio = opcio;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Boolean algoSeleccionat = true;
		
		try{
			//mirem si estem a la taula de musica disponible
			nom = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 0);
			artista = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 3);

		}catch(Exception e1){
		}
		if(nom == null){
			try{
				//mirem si estem a la taula de llista musica following
				nom = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 0);
				artista = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 3);
			}catch(Exception e1){
			}
			if(nom == null){
				//mirem si ja hi havia algo reproduintse
				nom = nomReproduccio;
			}
			if(nom == null) algoSeleccionat = false;
		}

		if(algoSeleccionat){
			switch (opcio) {
			case "play":
				System.out.println("click play");
				System.out.println(nom + " " + artista);
				if(ControladorFinestres.getR().getSong().equals(nom + "_" + artista)) {
					ControladorFinestres.getR().pause();
				}
				else {
					if (ControladorFinestres.getR().isStart()) {
						ControladorFinestres.getR().endSong();
					}
					ControladorFinestres.restartReproductor();
					ControladorFinestres.getR().setPath(nom,artista);
					
	
					JTable taulaMusica = ControladorFinestres.obteTaulaMusica();
					
					//Enviem Request al servidor per tal que ens retorni la canço seleccionada
					try {
						
						ControladorFinestres.getServidor().peticio("requestCanco", nom + "/" + artista );
						ControladorFinestres.getR().start();
						playing = true;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("netejoclick--->" +nom);
				nom = null;
				System.out.println("limpiaoclick--->" +nom);
				break;
				
				
			case "next":
				System.out.println("click next");
				break;
			case "back":
				System.out.println("click back");
				break;
	
			default:
				break;
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		ImageIcon im = new ImageIcon("Images/"+opcio+"d.png");
		switch (opcio) {
		case "play":
			
			Boolean algoSeleccionat = true;
			try{
				//comprovem si havia seleccionat alguna canco
				nom = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 0);
				artista = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 3);
			}catch(Exception e1){}
			if(nom == null){
				try{
					nom = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 0);
					artista = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 3);
				}catch(Exception e1){}
				if(nom == null){
					nom = nomReproduccio;
				}
				if(nom == null) algoSeleccionat = false;
			}

			if(algoSeleccionat){
				//si havia seleccionat alguna mentre prem el boto donara efecte optic de premut

				if (ControladorFinestres.getR().isPlaying() && ControladorFinestres.getR().getSong().equals(nom + "_" + artista)) {
					ImageIcon impause = new ImageIcon("Images/paused.png");
					ImageIcon icp = new ImageIcon(impause.getImage().getScaledInstance(50, 51, Image.SCALE_DEFAULT));
					if (e.getSource() instanceof JLabel) {
						JLabel aux = (JLabel)e.getSource();
						aux.setForeground(new Color(164,164,164));
						aux.setFocusable(true);
						aux.setFocusTraversalKeysEnabled(true);
						aux.setIcon(icp);
					}
				}else{
					ImageIcon icp = new ImageIcon(im.getImage().getScaledInstance(50, 51, Image.SCALE_DEFAULT));
					if (e.getSource() instanceof JLabel) {
						JLabel aux = (JLabel)e.getSource();
						aux.setForeground(new Color(164,164,164));
						aux.setFocusable(true);
						aux.setFocusTraversalKeysEnabled(true);
						aux.setIcon(icp);
					}
				}
			}
			break;
		case "next":
			System.out.println("click next");
			ImageIcon icn = new ImageIcon(im.getImage().getScaledInstance(46, 40, Image.SCALE_DEFAULT));
			if (e.getSource() instanceof JLabel) {
				JLabel aux = (JLabel)e.getSource();
				aux.setForeground(new Color(164,164,164));
				aux.setFocusable(true);
				aux.setFocusTraversalKeysEnabled(true);
				aux.setIcon(icn);
			}
			break;
		case "back":
			System.out.println("click back");
			ImageIcon icb = new ImageIcon(im.getImage().getScaledInstance(46, 40, Image.SCALE_DEFAULT));
			if (e.getSource() instanceof JLabel) {
				JLabel aux = (JLabel)e.getSource();
				aux.setForeground(new Color(164,164,164));
				aux.setFocusable(true);
				aux.setFocusTraversalKeysEnabled(true);
				aux.setIcon(icb);
			}
			break;

		default:
			break;
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// desclic
		Boolean algoSeleccionat = true;
		Boolean esFollowing = false;
		
		try{
			//mirem si estem a la taula de musica disponible
			nom = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 0);
			artista = (String) ControladorFinestres.fReproduccio.getTaulaMusica().getValueAt(ControladorFinestres.fReproduccio.getTaulaMusica().getSelectedRow(), 3);
		}catch(Exception e1){
		}
		if(nom == null){
			try{
				//mirem si estem a la taula de llista musica disponible
				nom = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 0);
				artista = (String) ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getValueAt(ControladorFinestres.fReproduccio.getTaulaLlistaMusicaFollowing().getSelectedRow(), 3);
			}catch(Exception e1){				
			}
			if(nom == null){
				//mirem si ja hi havia algo reproduintse
				nom = nomReproduccio;
			}
			if(nom == null) algoSeleccionat = false;
		}

		if(algoSeleccionat){
			ImageIcon im = new ImageIcon("Images/"+opcio+".png");
			switch (opcio) {
			case "play":
				if (ControladorFinestres.getR().isPlaying() && ControladorFinestres.getR().getSong().equals(nom + "_" + artista)) {
					ImageIcon icp = new ImageIcon(im.getImage().getScaledInstance(50, 51, Image.SCALE_DEFAULT));
					if (e.getSource() instanceof JLabel) {
						JLabel aux = (JLabel)e.getSource();
						aux.setForeground(new Color(164,164,164));
						aux.setFocusable(true);
						aux.setFocusTraversalKeysEnabled(true);
						aux.setIcon(icp);
					}
				}else{
					ImageIcon impause = new ImageIcon("Images/pause.png");
					ImageIcon icp = new ImageIcon(impause.getImage().getScaledInstance(50, 51, Image.SCALE_DEFAULT));
					if (e.getSource() instanceof JLabel) {
						JLabel aux = (JLabel)e.getSource();
						aux.setForeground(new Color(164,164,164));
						aux.setFocusable(true);
						aux.setFocusTraversalKeysEnabled(true);
						aux.setIcon(icp);
					}
				}
				nom = null;
				break;
			case "back":
				ImageIcon icb = new ImageIcon(im.getImage().getScaledInstance(46, 40, Image.SCALE_DEFAULT));
				if (e.getSource() instanceof JLabel) {
					JLabel aux = (JLabel)e.getSource();
					aux.setForeground(new Color(164,164,164));
					aux.setIcon(icb);
				}
				break;
	
			default:
				break;
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
	
	

}
