import jdk.nashorn.internal.ir.Block;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowMatrix extends JDialog
{
	static int WidthBlock = 50;
	static int HeightBlock = 20;
	WindowMatrix This = this;
	private JLabel[][] Table;
	private JLabel[] Wi;
	private JLabel nmax;
	private JLabel cr;
	MatrixPaired Matrix;
	String criteria;


	public WindowMatrix(JFrame parent, String title, MatrixPaired matrix, String criteria)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		if(parent != null)
		{
			setLocation(parent.getLocation().x + 20, parent.getLocation().y + 20);
		}
		this.criteria = criteria;
		Matrix = matrix;
		Table = new JLabel[Matrix.getN()][Matrix.getN()];
		setTitle(title);
		setSize((Matrix.getN()+1/*4*/)*(WidthBlock) + 20, (Matrix.getN()+1)*HeightBlock + 80);
		Creator();
		setResizable(false);
		setVisible(true);
	}
	public boolean set(int x, int y, double n)
	{
		boolean r = Matrix.setMatrixPaired(x, y, n);
		if(r)
			update();
		return r;
	}


	private void calc()
	{
		Matrix.setCached(true);
		nmax.setText(Matrix.getNmax() + "");
		cr.setText(Matrix.getCR() + "");
		if(Matrix.isConsistent())
			cr.setForeground(Color.GREEN);
		else
			cr.setForeground(Color.RED);
		for (int i = 0; i < Table.length; i++)
			this.Wi[i].setText(Matrix.getWi()[i] + "");
		Matrix.setCached(false);
	}
	private void update()
	{
		nmax.setText("0");
		cr.setText("0");
		cr.setForeground(Color.BLACK);
		for(int i = 0; i < Table.length; i++)
		{
			Wi[i].setText("0");
			for (int j = 0; j < Table[i].length; j++)
				if(i != j)
					Table[i][j].setText(Matrix.getMatrixPaired()[i][j] + "");
		}
	}
	private void Creator()
	{
		setLayout(null);
		JLabel table = new JLabel();
		table.setBounds(0, 0, (Matrix.getN()+1)*WidthBlock, (Matrix.getN()+1)*HeightBlock);
		table.add(CreatorBlock("X"));
		for(int i = 0; i < Matrix.getN(); i++)
		{
			for(int j = 0; j < 2; j++)
			{
				JLabel block = CreatorBlock(Matrix.getTitle()[i]);
				block.addMouseListener(new MouseListenerA(block));
				if(j == 0)
					block.setLocation(0, (i+1) * HeightBlock);
				else if(j == 1)
					block.setLocation((i+1) * WidthBlock, 0);
				table.add(block);
			}
		}
		for(int i = 0; i < Matrix.getN(); i++)
		{
			for (int j = 0; j < Matrix.getN(); j++)
			{
				JLabel block = CreatorBlock("");
				block.setLocation((i+1) * WidthBlock, (j+1) * HeightBlock);
				if(i != j)
					block.addMouseListener(new MouseListenerB(i, j));
				else
					block.setText("1");
				Table[i][j] = block;
				table.add(block);
			}
		}


		JLabel Wi = CreatorBlock("Wi");
		Wi.setLocation((Matrix.getN()+2) * WidthBlock + 20 , 0);
		add(Wi);
		this.Wi = new JLabel[Matrix.getN()];
		for(int i = 0; i < Matrix.getN(); i++)
		{
			JLabel block = CreatorBlock("0");
			block.setLocation((Matrix.getN()+2)* WidthBlock + 20 , (i + 1) * HeightBlock);
			this.Wi[i] = block;
			add(block);
		}
		JButton buttonCalc = new JButton("расчитать");
		buttonCalc.addMouseListener(new MouseListenerC());
		buttonCalc.setSize(80, HeightBlock);
		buttonCalc.setLocation(10, (Matrix.getN() + 2) * HeightBlock);
		add(buttonCalc);

		JLabel nmaxT = CreatorBlock("Nmax");
		nmaxT.setLocation((Matrix.getN()+1) * WidthBlock + 20, (Matrix.getN() + 1) * HeightBlock);
		add(nmaxT);
		JLabel crT = CreatorBlock("CR");
		crT.setLocation((Matrix.getN()+1) * WidthBlock + 20, (Matrix.getN() + 2) * HeightBlock);
		add(crT);
		nmax = CreatorBlock("0");
		nmax.setLocation((Matrix.getN()+2) * WidthBlock + 20, (Matrix.getN() + 1) * HeightBlock);
		add(nmax);
		cr = CreatorBlock("0");
		cr.setLocation((Matrix.getN()+2) * WidthBlock + 20, (Matrix.getN() + 2) * HeightBlock);
		add(cr);

		update();
		add(table);



		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				if(cr.getForeground() != Color.GREEN || !Matrix.isFullMatrixPaired())
				{
					String text;
					if(cr.getForeground() != Color.GREEN)
					{
						text = "У вас несогласованная матрица! Вы хотите выйти?";
					}
					else
					{
						text = "У вас не заполнена матрица! Вы хотите выйти?";;
					}
					int confirmed = JOptionPane.showConfirmDialog(This, text, "Выход", JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION)
					{
						dispose();
					}
					else
					{
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}
				else
				{
					dispose();
				}
			}
		});
	}
	private JLabel CreatorBlock(String text)
	{
		JLabel r = new JLabel(text);
		r.setSize(WidthBlock, HeightBlock);
		r.setBorder(BorderFactory.createLineBorder(Color.black));
		r.setHorizontalAlignment(SwingConstants.CENTER);
		r.setVerticalAlignment(SwingConstants.CENTER);
		return r;
	}
	private void onMessage(String text)
	{
		JOptionPane.showMessageDialog(this,text);
	}
	class MouseListenerA implements MouseListener
	{
		JLabel l;
		public MouseListenerA(JLabel l)
		{
			this.l = l;
		}
		@Override
		public void mouseClicked(MouseEvent e)
		{
			onMessage(l.getText());
		}
		@Override public void mousePressed(MouseEvent e) { }
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
	}
	class MouseListenerB implements MouseListener
	{
		int x, y;
		public MouseListenerB(int x, int y)
		{
			this.y = y;
			this.x = x;
		}
		@Override
		public void mouseClicked(MouseEvent e)
		{
			 JDialog d = new WindowAssessment(This, x, y, Matrix.getMatrixPaired()[x][y], Matrix.getTitle()[y], Matrix.getTitle()[x], criteria);
		}
		@Override public void mousePressed(MouseEvent e) { }
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
	}
	class MouseListenerC implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(Matrix.isFullMatrixPaired())
			{
				calc();
			}
			else
			{
				int dialogButton = JOptionPane.showConfirmDialog (This, "Матрица не заполнена! Продолжить?", "Матрица не заполнена!", JOptionPane.YES_NO_OPTION);
				if(dialogButton == JOptionPane.YES_OPTION)
				{
					calc();
				}
			}
		}
		@Override public void mousePressed(MouseEvent e) { }
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
	}
}
