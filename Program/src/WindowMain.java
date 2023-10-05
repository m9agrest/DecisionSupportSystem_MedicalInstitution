import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowMain extends JFrame
{
	MatrixPairedAlternatives matrix;
	public WindowMain(JFrame parent, MatrixPairedAlternatives Matrix)
	{
		matrix = Matrix;
		setTitle("Матрицы");
		setLocation(parent.getLocation().x, parent.getLocation().y);
		JButton button1 = new JButton("открыть");
		button1.addMouseListener(new MouseListenerA(-1));
		button1.setBounds(100, 10, 120, 30);
		add(button1);
		JLabel text1 = new JLabel("Критерии");
		text1.setBounds(10, 10, 80, 30);
		add(text1);
		for(int i = 0; i < matrix.getAlternativeLength(); i++)
		{
			JButton button = new JButton("открыть");
			button.setBounds(100, (i + 1) * 40 + 10, 120, 30);
			button.addMouseListener(new MouseListenerA(i));
			add(button);
			JLabel text = new JLabel(matrix.getCriteria().getTitle()[i]);
			text.setBounds(10, (i + 1) * 40 + 10, 80, 30);
			add(text);
		}
		setSize(250, (matrix.getAlternativeLength() + 3) * 40 + 10);
		addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { close(); }});
		JButton button2 = new JButton("Расчитать");
		button2.setBounds(10, (matrix.getAlternativeLength() + 1) * 40 + 10, 120, 30);
		button2.addActionListener(e -> { clickCalc(); });
		add(button2);
		setResizable(false);
		setLayout(null);
		setVisible(true);
	}
	void clickCalc()
	{
		if(matrix.isFull())
		{
			if(matrix.isConsistent())
			{
				calc();
			}
			else
			{
				int confirmed = JOptionPane.showConfirmDialog(this, "У вас несоглассована какая-то матрица. Продолжить?", "Предупреждение", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION)
				{
					calc();
				}
			}
		}
		else
		{
			int confirmed = JOptionPane.showConfirmDialog(this, "У вас незаполнена какая-то матрица. Продолжить?", "Предупреждение", JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION)
			{
				if(matrix.isConsistent())
				{
					calc();
				}
				else
				{
					int confirmed2 = JOptionPane.showConfirmDialog(this, "У вас так-же несоглассована какая-то матрица. Продолжить?", "Предупреждение", JOptionPane.YES_NO_OPTION);
					if (confirmed2 == JOptionPane.YES_OPTION)
					{
						calc();
					}
				}
			}
		}
	}
	void calc()
	{
		double[] r = matrix.W();
		new WindowResult(this, r, matrix.getAlternative(0).getTitle());
	}
	void close()
	{
		int confirmed = JOptionPane.showConfirmDialog(this, "Вы действительно хотите выйти?", "Выход", JOptionPane.YES_NO_OPTION);
		if (confirmed == JOptionPane.YES_OPTION)
		{
			dispose();
		}
		else
		{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}
	void click(int i)
	{
		if(i == -1)
		{
			new WindowMatrix(this, "Редактировании матрицы критериев", matrix.getCriteria(), null);
		}
		else
		{
			new WindowMatrix(this, "Редактировании матрицы алтернатив", matrix.getAlternative(i), matrix.getCriteria().getTitle()[i]);
		}
	}

	class MouseListenerA implements MouseListener
	{
		int i;
		public MouseListenerA(int i)
		{
			this.i = i;
		}
		@Override
		public void mouseClicked(MouseEvent e)
		{
			click(i);
		}
		@Override public void mousePressed(MouseEvent e) { }
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
	}
}
