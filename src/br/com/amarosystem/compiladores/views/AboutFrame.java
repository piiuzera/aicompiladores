package br.com.amarosystem.compiladores.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import br.com.amarosystem.compiladores.util.StatusBar;

public class AboutFrame extends JDialog {
	private static final long serialVersionUID = 8381331176864779882L;

	private static AboutFrame instance;
	
	public static AboutFrame getInstance() {
		if(instance == null) {
			instance = new AboutFrame();
		}
		return instance;
	}
	
	/**
	 * Content Pane
	 */
	private JPanel pane;
	
	private JLabel labelInfo;
	
	private JLabel labelDisciplina;
	private JLabel labelCurso;
	private JLabel labelProfessor;
	private JLabel labelAlunos;
	
	private JTextArea textDisciplina;
	private JTextArea textCurso;
	private JTextArea textProfessor;
	private JTextArea textAlunos;
	
	/**
	 * Status Bar
	 */
	private StatusBar statusBar;
	
	private JLabel labelMessage;
	
	private JProgressBar progress;
	
	private AboutFrame() {
		super();
		setTitle(":: Quem Somos - Autoinstrucional - Compiladores");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setIconImage(new ImageIcon(this.getClass().getResource("/logo.png")).getImage());
		
		pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(pane);
		
		labelInfo = new JLabel("Atividade Autoinstrucional", SwingConstants.CENTER);
		labelInfo.setPreferredSize(new Dimension(350, 20));
		pane.add(labelInfo);
		
		labelDisciplina = new JLabel("Disciplina:");
		labelDisciplina.setPreferredSize(new Dimension(350, 20));
		pane.add(labelDisciplina);
		
		textDisciplina = new JTextArea("Compiladores");
		textDisciplina.setPreferredSize(new Dimension(350, 20));
		textDisciplina.setEditable(false);
		textDisciplina.setLineWrap(true);
		textDisciplina.setWrapStyleWord(true);
		textDisciplina.setBackground(this.getBackground());
		pane.add(textDisciplina);
		
		labelCurso = new JLabel("Curso:");
		labelCurso.setPreferredSize(new Dimension(350, 20));
		pane.add(labelCurso);
		
		textCurso = new JTextArea("Ciência da Computação");
		textCurso.setPreferredSize(new Dimension(350, 20));
		textCurso.setEditable(false);
		textCurso.setLineWrap(true);
		textCurso.setWrapStyleWord(true);
		textCurso.setBackground(this.getBackground());
		pane.add(textCurso);
		
		labelProfessor = new JLabel("Professor:");
		labelProfessor.setPreferredSize(new Dimension(350, 20));
		pane.add(labelProfessor);
		
		textProfessor = new JTextArea("Mateus José Ferreira");
		textProfessor.setPreferredSize(new Dimension(350, 20));
		textProfessor.setEditable(false);
		textProfessor.setLineWrap(true);
		textProfessor.setWrapStyleWord(true);
		textProfessor.setBackground(this.getBackground());
		pane.add(textProfessor);
		
		labelAlunos = new JLabel("Alunos:");
		labelAlunos.setPreferredSize(new Dimension(350, 20));
		pane.add(labelAlunos);
		
		textAlunos = new JTextArea("Matheus Amaro      \n" +
								   "Gabriel Barbosa    \n" +
								   "Frederico Brasil   \n" +
								   "Plinio José        \n" +
								   "Luiz Paulo da Silva\n");
		textAlunos.setPreferredSize(new Dimension(350, 100));
		textAlunos.setEditable(false);
		textAlunos.setLineWrap(true);
		textAlunos.setWrapStyleWord(true);
		textAlunos.setBackground(this.getBackground());
		pane.add(textAlunos);
		
		statusBar = new StatusBar();
		
		labelMessage = new JLabel();
		statusBar.setLeftComponent(labelMessage);
		
		progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);
		statusBar.setLeftComponent(progress);
		
		add(statusBar, BorderLayout.SOUTH);
	}

	public JPanel getPane() {
		return pane;
	}

	public JLabel getLabelInfo() {
		return labelInfo;
	}

	public JLabel getLabelDisciplina() {
		return labelDisciplina;
	}

	public JLabel getLabelCurso() {
		return labelCurso;
	}

	public JLabel getLabelProfessor() {
		return labelProfessor;
	}

	public JLabel getLabelAlunos() {
		return labelAlunos;
	}

	public JTextArea getTextDisciplina() {
		return textDisciplina;
	}

	public JTextArea getTextCurso() {
		return textCurso;
	}

	public JTextArea getTextProfessor() {
		return textProfessor;
	}

	public JTextArea getTextAlunos() {
		return textAlunos;
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
