package br.com.amarosystem.compiladores.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.com.amarosystem.compiladores.util.StatusBar;

public class IndexFrame extends JFrame {
	private static final long serialVersionUID = -5853050566560706891L;
	
	private static IndexFrame instance;
	
	public static IndexFrame getInstance() {
		if(instance == null) {
			instance = new IndexFrame();
		}
		return instance;
	}
	
	/**
	 * Content Pane
	 */
	private JPanel pane;
	
	/**
	 * Menu Bar
	 */
	private JMenuBar bar;
	
	private JMenu menuSobre;
	
	private JMenuItem menuItemQuemSomos;
	
	private JPanel panelMassaTeste;
	private JPanel panelCodigo;
	
	private JLabel labelInfoTableMassaTeste;
	
	private JTable tableMassaTeste;
	private JScrollPane scrollMassaTeste;
	
	private JButton buttonExcluirTodosOsTestes;
	
	private JPopupMenu popupMassaTeste;
	
	private JMenuItem menuItemExibirTeste;
	
	private JMenuItem menuItemExcluirTeste;
	
	private JLabel labelInfo;
	
	private JTextArea textCodigo;
	private JScrollPane scrollCodigo;
	
	private JPanel panelLegenda;
	private JPanel panelBotao;
	
	private JLabel labelLegendaErro;
	private JLabel labelLegendaSucesso;
	private JLabel labelLegendaDuploClick;
	private JLabel labelLegendaDireitoClick;
	
	private JButton buttonValidarCodigo;
	
	/**
	 * Status Bar
	 */
	private StatusBar statusBar;
	
	private JLabel labelMessage;
	
	private JProgressBar progress;
	
	private IndexFrame() {
		super("Autoinstrucional - Compiladores");
		setSize(600, 510);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setIconImage(new ImageIcon(this.getClass().getResource("/logo.png")).getImage());
		
		bar = new JMenuBar();
		setJMenuBar(bar);
		
		menuSobre = new JMenu("Sobre");
		bar.add(menuSobre);
		
		menuItemQuemSomos = new JMenuItem("Quem Somos");
		menuItemQuemSomos.setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-196-circle-info-small.png")));
		menuSobre.add(menuItemQuemSomos);
		
		pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(pane);
		
		panelMassaTeste = new JPanel();
		panelMassaTeste.setPreferredSize(new Dimension(210, 350));
		panelMassaTeste.setLayout(new FlowLayout(FlowLayout.CENTER));
		pane.add(panelMassaTeste);
		
		labelInfoTableMassaTeste = new JLabel("Massa de Teste:", SwingConstants.LEFT);
		labelInfoTableMassaTeste.setPreferredSize(new Dimension(210, 20));
		labelInfoTableMassaTeste.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelMassaTeste.add(labelInfoTableMassaTeste);
		
		tableMassaTeste = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return getValueAt(row, column) instanceof Boolean;
			}
			
			@Override
			public String getToolTipText(MouseEvent event) {
                java.awt.Point p = event.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                
                if(getValueAt(rowIndex, colIndex) instanceof ImageIcon) {
                	ImageIcon icon = (ImageIcon)getValueAt(rowIndex, colIndex);
                	if(icon.getDescription().contains("busy-status.png")) {
                		return "Expressão Inválida";
                	} else {
                		return "Expressão válida";
                	}
                }
                
                return getValueAt(rowIndex, colIndex).toString();
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				switch(column) {
				case 0:
					return ImageIcon.class;
				default:
					return String.class;
				}
			}
		};
		tableMassaTeste.getTableHeader().setReorderingAllowed(false);
		tableMassaTeste.getTableHeader().setResizingAllowed(false);
		
		((DefaultTableModel)tableMassaTeste.getModel()).addColumn("");
		((DefaultTableModel)tableMassaTeste.getModel()).addColumn("Código");
		
		tableMassaTeste.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableMassaTeste.getColumnModel().getColumn(1).setPreferredWidth(180);
		
		popupMassaTeste = new JPopupMenu();
		
		menuItemExibirTeste = new JMenuItem("Exibir Expressão");
		menuItemExibirTeste.setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-28-search-small.png")));
		popupMassaTeste.add(menuItemExibirTeste);
		
		popupMassaTeste.addSeparator();
		
		menuItemExcluirTeste = new JMenuItem("Excluir Expressão");
		menuItemExcluirTeste.setIcon(new ImageIcon(this.getClass().getResource("/glyphicons-193-circle-remove-small.png")));
		popupMassaTeste.add(menuItemExcluirTeste);
		
		tableMassaTeste.setComponentPopupMenu(popupMassaTeste);
		
		scrollMassaTeste = new JScrollPane(tableMassaTeste);
		scrollMassaTeste.setPreferredSize(new Dimension(210, 290));
		panelMassaTeste.add(scrollMassaTeste);
		
		buttonExcluirTodosOsTestes = new JButton("Excluir Todos");
		buttonExcluirTodosOsTestes.setPreferredSize(new Dimension(210, 20));
		panelMassaTeste.add(buttonExcluirTodosOsTestes);
		
		panelCodigo = new JPanel();
		panelCodigo.setPreferredSize(new Dimension(350, 350));
		panelCodigo.setLayout(new FlowLayout(FlowLayout.CENTER));
		pane.add(panelCodigo);
		
		labelInfo = new JLabel("Expressão:", SwingConstants.LEFT);
		labelInfo.setPreferredSize(new Dimension(350, 20));
		labelInfo.setBackground(this.getBackground());
		panelCodigo.add(labelInfo);
		
		textCodigo = new JTextArea("");
		textCodigo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		scrollCodigo = new JScrollPane(textCodigo);
		scrollCodigo.setPreferredSize(new Dimension(350, 316));
		panelCodigo.add(scrollCodigo);
		
		panelLegenda = new JPanel();
		panelLegenda.setPreferredSize(new Dimension(354, 70));
		panelLegenda.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(panelLegenda);
		
		labelLegendaErro = new JLabel("Expressão Inválida");
		labelLegendaErro.setIcon(new ImageIcon(this.getClass().getResource("/busy-status.png")));
		panelLegenda.add(labelLegendaErro);
		
		labelLegendaSucesso = new JLabel("Expressão válida");
		labelLegendaSucesso.setIcon(new ImageIcon(this.getClass().getResource("/online-status.png")));
		panelLegenda.add(labelLegendaSucesso);
		
		labelLegendaDuploClick = new JLabel("Duplo Click - Exibe a expressão", SwingConstants.LEFT);
		labelLegendaDuploClick.setPreferredSize(new Dimension(320, 20));
		labelLegendaDuploClick.setIcon(new ImageIcon(this.getClass().getResource("/offline-status.png")));
		labelLegendaDuploClick.setBackground(this.getBackground());
		panelLegenda.add(labelLegendaDuploClick);
		
		labelLegendaDireitoClick = new JLabel("Botão Direito - Menu de opções", SwingConstants.LEFT);
		labelLegendaDireitoClick.setPreferredSize(new Dimension(320, 20));
		labelLegendaDireitoClick.setIcon(new ImageIcon(this.getClass().getResource("/offline-status.png")));
		labelLegendaDireitoClick.setBackground(this.getBackground());
		panelLegenda.add(labelLegendaDireitoClick);
		
		panelBotao = new JPanel();
		panelBotao.setPreferredSize(new Dimension(214, 70));
		panelBotao.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pane.add(panelBotao);
		
		buttonValidarCodigo = new JButton("Validar");
		buttonValidarCodigo.setPreferredSize(new Dimension(150, 20));
		panelBotao.add(buttonValidarCodigo);
		
		statusBar = new StatusBar();
		
		labelMessage = new JLabel();
		statusBar.setLeftComponent(labelMessage);
		
		progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);
		statusBar.setLeftComponent(progress);
		
		add(statusBar, BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(buttonValidarCodigo);
	}

	public JPanel getPane() {
		return pane;
	}

	public JMenuBar getBar() {
		return bar;
	}

	public JMenu getMenuSobre() {
		return menuSobre;
	}

	public JMenuItem getMenuItemQuemSomos() {
		return menuItemQuemSomos;
	}

	public JPanel getPanelMassaTeste() {
		return panelMassaTeste;
	}

	public JPanel getPanelCodigo() {
		return panelCodigo;
	}

	public JLabel getLabelInfoTableMassaTeste() {
		return labelInfoTableMassaTeste;
	}

	public JTable getTableMassaTeste() {
		return tableMassaTeste;
	}

	public JScrollPane getScrollMassaTeste() {
		return scrollMassaTeste;
	}

	public JButton getButtonExcluirTodosOsTestes() {
		return buttonExcluirTodosOsTestes;
	}

	public JPopupMenu getPopupMassaTeste() {
		return popupMassaTeste;
	}

	public JMenuItem getMenuItemExibirTeste() {
		return menuItemExibirTeste;
	}

	public JMenuItem getMenuItemExcluirTeste() {
		return menuItemExcluirTeste;
	}

	public JLabel getLabelInfo() {
		return labelInfo;
	}

	public JTextArea getTextCodigo() {
		return textCodigo;
	}

	public JScrollPane getScrollCodigo() {
		return scrollCodigo;
	}

	public JPanel getPanelLegenda() {
		return panelLegenda;
	}

	public JPanel getPanelBotao() {
		return panelBotao;
	}

	public JLabel getLabelLegendaErro() {
		return labelLegendaErro;
	}

	public JLabel getLabelLegendaSucesso() {
		return labelLegendaSucesso;
	}

	public JLabel getLabelLegendaDuploClick() {
		return labelLegendaDuploClick;
	}

	public JLabel getLabelLegendaDireitoClick() {
		return labelLegendaDireitoClick;
	}

	public JButton getButtonValidarCodigo() {
		return buttonValidarCodigo;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public JLabel getLabelMessage() {
		return labelMessage;
	}

	public JProgressBar getProgress() {
		return progress;
	}
}
