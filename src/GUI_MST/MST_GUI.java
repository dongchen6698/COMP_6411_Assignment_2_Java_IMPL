package GUI_MST;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Kruskal_MST.Kruskal;

public class MST_GUI extends JFrame{
	private JPanel top,middle,bottom;
	private JComboBox<String> jc_count, jc_filename,jc_algorithm;
	private JButton start, step, clear;
	private JTextArea jta;
	private int currentReadIndex = 0;
	
	public MST_GUI() {
		this.setSize(600,400);
		this.setTitle("MST GUI Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		init();	
	}
	
	public void init(){
		// set top part of frame;
		String[] vertex = {"10","20","50","100","1000"};
		String[] file = {
				"AdjacencyMatrix_of_Graph_G_N_10.txt",
				"AdjacencyMatrix_of_Graph_G_N_20.txt",
				"AdjacencyMatrix_of_Graph_G_N_50.txt",
				"AdjacencyMatrix_of_Graph_G_N_100.txt",
				"AdjacencyMatrix_of_Graph_G_N_1000.txt"};
		top = new JPanel();
		top.setLayout(new FlowLayout());
		jc_count = new JComboBox<>(vertex);
		jc_filename = new JComboBox<>(file);
		jc_filename.setEditable(false);
		top.add(new Label("Vertex N: "));
		top.add(jc_count);
		top.add(new Label("Data File: "));
		top.add(jc_filename);
		
		// set middle part of frame
		middle = new JPanel();
		middle.setLayout(new BorderLayout());
		jta = new JTextArea();
		jta.setEditable(false);
		middle.add(new JScrollPane(jta), BorderLayout.CENTER);
		
		// set bottom part of frame
		String[] algorithm = {"Prim's algorithm","Kruskal algorithm","Boruvka algorithm", "Hybrid algorithm"};
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		bottom.add(new Label("Algorithm: "));
		jc_algorithm = new JComboBox<>(algorithm);
		start = new JButton("Start");
		step = new JButton("Step");
		clear = new JButton("Clear");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jc_count.getSelectedIndex() != jc_filename.getSelectedIndex()){
					JOptionPane.showMessageDialog(null, "Please select the proper vertex number and file!");
				}else{
					new Kruskal(jc_count.getSelectedItem().toString(), jc_filename.getSelectedItem().toString());
					jta.setText("Finish calculate"+"\n");
					currentReadIndex = 0;
				}
			}
		});
		step.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File f = new File("Result_of_Graph_G_N_"+jc_count.getSelectedItem().toString()+".txt");
					RandomAccessFile raf = new RandomAccessFile(f, "r");
					if(f.length() == 0){
						System.out.println("File is EMPTY !!");
					}else{
						raf.seek(currentReadIndex);
						String line = raf.readLine();
						if(line != null){
							jta.append(line+"\n");
							currentReadIndex += line.length()+1;
						}else{
							int n = JOptionPane.showConfirmDialog(
								    null,
								    "One more time?",
								    "Inform",
								    JOptionPane.YES_NO_OPTION);
							if(n == 0){
								jta.setText("");
								currentReadIndex = 0;
							}
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
		});
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jta.setText("");
				currentReadIndex = 0;	
			}
		});
		bottom.add(jc_algorithm);
		bottom.add(start);
		bottom.add(step);
		bottom.add(clear);
		
		// add top middle bottom to main frame
		this.add(top,BorderLayout.PAGE_START);
		this.add(middle, BorderLayout.CENTER);
		this.add(bottom,BorderLayout.PAGE_END);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MST_GUI();
	}
}
