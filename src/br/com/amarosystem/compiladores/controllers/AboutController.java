package br.com.amarosystem.compiladores.controllers;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;

import br.com.amarosystem.compiladores.views.AboutFrame;

public class AboutController implements Runnable {
	
	private static AboutController instance;
	
	public static AboutController getInstance() {
		if(instance == null) {
			instance = new AboutController();
		} else {
			AboutFrame.getInstance().setVisible(true);	
		}
		
		return instance;
	}
	
	private AboutController() {
		AboutFrame.getInstance().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				aboutFrame_closing(evt.getSource());
			}
		});
		
		new Thread(this).start();
		
		AboutFrame.getInstance().setVisible(true);
	}
	
	protected void aboutFrame_closing(Object sender) {
		AboutFrame.getInstance().setVisible(false);
	}

	@Override
	public void run() {
		while(true) {
			try {
				if(!AboutFrame.getInstance().getLabelMessage().getText().equals("")) {
					Thread.sleep(5000);
					AboutFrame.getInstance().getLabelMessage().setText("");
					AboutFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
					AboutFrame.getInstance().getLabelMessage().setIcon(new ImageIcon());
				}
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				AboutFrame.getInstance().getLabelMessage().setText(ex.getMessage());
				AboutFrame.getInstance().getLabelMessage().setForeground(Color.RED);
				AboutFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));
			}
		}
	}

}
