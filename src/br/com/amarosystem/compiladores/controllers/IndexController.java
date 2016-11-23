package br.com.amarosystem.compiladores.controllers;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import br.com.amarosystem.compiladores.AsException;
import br.com.amarosystem.compiladores.Program;
import br.com.amarosystem.compiladores.enumtypes.Status;
import br.com.amarosystem.compiladores.models.Teste;
import br.com.amarosystem.compiladores.util.AsListItem;
import br.com.amarosystem.compiladores.util.AsUtil;
import br.com.amarosystem.compiladores.views.IndexFrame;

public class IndexController implements Runnable {
	private static IndexController instance;
	
	public static IndexController getInstance() {
		if(instance == null) {
			instance = new IndexController();
		} else {
			IndexFrame.getInstance().setVisible(true);	
		}
		
		return instance;
	}
	
	private IndexController() {
		IndexFrame.getInstance().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				indexFrame_closing(evt.getSource());
			}
		});
		
		IndexFrame.getInstance().getMenuItemQuemSomos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				menuItemQuemSomos_click(evt.getSource());
			}
		});
		
		IndexFrame.getInstance().getButtonValidarCodigo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				IndexFrame.getInstance().getLabelMessage().setText("");
				IndexFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
				IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon());
				
				String text = IndexFrame.getInstance().getButtonValidarCodigo().getText();
				
				IndexFrame.getInstance().getProgress().setVisible(true);
				
				IndexFrame.getInstance().getButtonValidarCodigo().setText("Validando...");
				IndexFrame.getInstance().getButtonValidarCodigo().setEnabled(false);
				
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						buttonValidarCodigo_click(evt.getSource());
						
						return null;
					}
					
					@Override
					protected void done() {
						IndexFrame.getInstance().getProgress().setVisible(false);
						
						IndexFrame.getInstance().getButtonValidarCodigo().setText(text);
						IndexFrame.getInstance().getButtonValidarCodigo().setEnabled(true);
					}
				}.execute();
			}
		});
		
		IndexFrame.getInstance().getTableMassaTeste().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(SwingUtilities.isRightMouseButton(evt)) {
					tableEmpresas_rightClick(evt.getSource(), evt);
				} else if(evt.getClickCount() == 2) {
					tableMassaTeste_doubleClick(evt.getSource());
				}
			}
		});
		
		IndexFrame.getInstance().getMenuItemExibirTeste().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				menuItemExibirTeste_click(evt.getSource());
			}
		});
		
		IndexFrame.getInstance().getMenuItemExcluirTeste().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				IndexFrame.getInstance().getProgress().setVisible(true);
				
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						menuItemExcluirTeste_click(evt.getSource());
						
						return null;
					}
					
					@Override
					protected void done() {
						IndexFrame.getInstance().getProgress().setVisible(false);
					}
				}.execute();
			}
		});
		
		IndexFrame.getInstance().getButtonExcluirTodosOsTestes().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				IndexFrame.getInstance().getProgress().setVisible(true);
				
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						buttonExcluirTodosOsTestes_click(evt.getSource());
						
						return null;
					}
					
					@Override
					protected void done() {
						IndexFrame.getInstance().getProgress().setVisible(false);
					}
				}.execute();
			}
		});
		
		preencheTableMassaTeste();
		
		new Thread(this).start();
		
		IndexFrame.getInstance().setVisible(true);
	}
	
	protected void indexFrame_closing(Object sender) {
		try {
			IndexFrame.getInstance().setVisible(false);
			
			AsUtil.getInstance().setFileObject(System.getenv("LOCALAPPDATA") + "\\ASAAI\\MassaTeste.aai", Program.getInstance().getListMassaTeste());
		} catch (AsException ex) {
			IndexFrame.getInstance().getLabelMessage().setText(ex.getMessage());
			IndexFrame.getInstance().getLabelMessage().setForeground(Color.RED);
			IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));
		}
		System.exit(0);
	}
	
	protected void menuItemQuemSomos_click(Object sender) {
		AboutController.getInstance();
	}
	
	protected void buttonValidarCodigo_click(Object sender) {
		try {
			
			if(AsUtil.getInstance().isNotEmpty(IndexFrame.getInstance().getTextCodigo())) {
				if( Program.getInstance().isCodigoValido(IndexFrame.getInstance().getTextCodigo().getText()) ) {
					
					if(Program.getInstance().getListMassaTeste().where(teste -> teste.getCodigo().equals(IndexFrame.getInstance().getTextCodigo().getText())).isEmpty()) {
						Teste teste = new Teste();
						teste.setStatus(Status.SUCCESS);
						teste.setCodigo(IndexFrame.getInstance().getTextCodigo().getText());
						
						Program.getInstance().getListMassaTeste().add(teste);
						
						preencheTableMassaTeste();
						
						IndexFrame.getInstance().getScrollMassaTeste().getVerticalScrollBar().setValue(IndexFrame.getInstance().getScrollMassaTeste().getVerticalScrollBar().getMaximum());
					}
					
					IndexFrame.getInstance().getLabelMessage().setText("Expressão válida");
					IndexFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
					IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-199-ok-small.png")));
				}
			} else {
				IndexFrame.getInstance().getLabelMessage().setText("Expressão não pode ser vazia");
				IndexFrame.getInstance().getLabelMessage().setForeground(Color.RED);
				IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));
			}
		} catch(AsException ex) {
			if(Program.getInstance().getListMassaTeste().where(teste -> teste.getCodigo().equals(IndexFrame.getInstance().getTextCodigo().getText())).isEmpty()) {
				Teste teste = new Teste();
				teste.setStatus(Status.ERROR);
				teste.setCodigo(IndexFrame.getInstance().getTextCodigo().getText());
				
				Program.getInstance().getListMassaTeste().add(teste);
				
				preencheTableMassaTeste();
				
				IndexFrame.getInstance().getScrollMassaTeste().getVerticalScrollBar().setValue(IndexFrame.getInstance().getScrollMassaTeste().getVerticalScrollBar().getMaximum());
			}
			
			IndexFrame.getInstance().getLabelMessage().setText(ex.getMessage());
			IndexFrame.getInstance().getLabelMessage().setForeground(Color.RED);
			IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));			
		}
		
		IndexFrame.getInstance().getTextCodigo().setText("");
	}
	
	protected void tableEmpresas_rightClick(Object sender, MouseEvent e) {
		Point p = e.getPoint();
		
		int rowNumber = IndexFrame.getInstance().getTableMassaTeste().rowAtPoint(p);
		
		ListSelectionModel model = IndexFrame.getInstance().getTableMassaTeste().getSelectionModel();
		
		model.setSelectionInterval(rowNumber, rowNumber);
	}
	
	protected void tableMassaTeste_doubleClick(Object sender) {
		int row = IndexFrame.getInstance().getTableMassaTeste().getSelectedRow();
		AsListItem item = (AsListItem)IndexFrame.getInstance().getTableMassaTeste().getValueAt(row, 1);
		
		IndexFrame.getInstance().getTextCodigo().setText(item.getValor());
		IndexFrame.getInstance().getTextCodigo().requestFocus();
	}
	
	protected void menuItemExibirTeste_click(Object sender) {
		int row = IndexFrame.getInstance().getTableMassaTeste().getSelectedRow();
		AsListItem item = (AsListItem)IndexFrame.getInstance().getTableMassaTeste().getValueAt(row, 1);
		
		IndexFrame.getInstance().getTextCodigo().setText(item.getValor());
		IndexFrame.getInstance().getTextCodigo().requestFocus();
	}
	
	protected void menuItemExcluirTeste_click(Object sender) {
		int row = IndexFrame.getInstance().getTableMassaTeste().getSelectedRow();
		AsListItem item = (AsListItem)IndexFrame.getInstance().getTableMassaTeste().getValueAt(row, 1);
		
		Teste teste = (Teste)item.getObject();
		if(JOptionPane.showConfirmDialog(null, "Você confirma excluir este teste ?\n\rCódigo: " + teste.getCodigo(),
				"Confirmação de Exclusão",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			Program.getInstance().getListMassaTeste().remove(teste);
			
			IndexFrame.getInstance().getLabelMessage().setText("Teste excluído com sucesso.");
			IndexFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
			IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-199-ok-small.png")));
			
			preencheTableMassaTeste();
		}
	}
	
	protected void buttonExcluirTodosOsTestes_click(Object sender) {
		if(JOptionPane.showConfirmDialog(null, "Você confirma excluir todos os testes ?",
				"Confirmação de Exclusão",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			
			while(!Program.getInstance().getListMassaTeste().isEmpty()) {
				Program.getInstance().getListMassaTeste().remove(0);
			}
			
			IndexFrame.getInstance().getLabelMessage().setText("Todos os testes foram excluídos com sucesso.");
			IndexFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
			IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-199-ok-small.png")));
			
			preencheTableMassaTeste();
		}
	}
	
	private void preencheTableMassaTeste() {
		((DefaultTableModel)IndexFrame.getInstance().getTableMassaTeste().getModel()).setNumRows(0);
		for(Teste teste : Program.getInstance().getListMassaTeste()) {
			((DefaultTableModel)IndexFrame.getInstance().getTableMassaTeste().getModel()).addRow(new Object[] {
				teste.getStatus().getIcon(),
				new AsListItem(teste.getCodigo(), teste.getCodigo(), teste)
			});
		}
		
		if(Program.getInstance().getListMassaTeste().isEmpty()) {
			IndexFrame.getInstance().getButtonExcluirTodosOsTestes().setEnabled(false);
		} else {
			IndexFrame.getInstance().getButtonExcluirTodosOsTestes().setEnabled(true);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				if(!IndexFrame.getInstance().getLabelMessage().getText().equals("")) {
					Thread.sleep(5000);
					IndexFrame.getInstance().getLabelMessage().setText("");
					IndexFrame.getInstance().getLabelMessage().setForeground(Color.BLACK);
					IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon());
				}
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				IndexFrame.getInstance().getLabelMessage().setText(ex.getMessage());
				IndexFrame.getInstance().getLabelMessage().setForeground(Color.RED);
				IndexFrame.getInstance().getLabelMessage().setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));
			}
		}
	}
}
