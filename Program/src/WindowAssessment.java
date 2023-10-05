import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WindowAssessment extends JDialog
{
	int x, y;
	int n;
	WindowMatrix parent;
	JTextField input;
	public WindowAssessment(WindowMatrix parent, int x, int y, double n, String a, String b, String c)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		if(n < 1)
			n = -(1 / n);
		this.n = (int)n;
		this.x = x;
		this.y = y;
		this.parent = parent;
		setTitle("Сравнение");
		Creator(a, b, c);
		setVisible(true);
	}
	void Creator(String a, String b, String c)
	{
		setLocation(parent.getLocation().x + 20, parent.getLocation().y + 20);
		setResizable(false);
		setSize(250, 300);

		input = new JTextField();
		input.setSize(60, 30);
		JLabel text1;
		if(c != null)
			text1 = new JLabel("<html>Как вы расцениваете ("+a+"), по сравнению с (" + b + "), по критерию (" + c + ");</html>");
		else
			text1 = new JLabel("<html>Как вы расцениваете ("+a+"),<br>по сравнению с (" + b + ")</html>");
		text1.setSize(230, 80);
		JLabel text2 = new JLabel("<html>Введите от 1 до 9, где четные значения будут более компрамистным выбором. Отрицательное число, будет воспринято как (1/n)</html>");
		text2.setSize(226, 100);
		JButton button = new JButton("ввести");
		button.setSize(80, 30);


		button.addActionListener(e -> { check(); });

		setLayout(null);
		text1.setLocation(10,0);
		input.setLocation(40,90);
		button.setLocation(130,90);
		text2.setLocation(12,130);
		add(text1);
		add(input);
		add(button);
		add(text2);
	}
	void check()
	{
		String a = input.getText();
		char num = ' ';
		int b = 0;
		if(a.length() == 1)
		{
			b = 1;
			num = a.charAt(0);
		} else if(a.length() == 2 && a.charAt(0) == '-')
		{
			b = -1;
			num = a.charAt(1);
		}
		try
		{
			b = b * Integer.parseInt(num + "");
		}
		catch (Exception E)
		{
			b = 0;
		}
		if(b <= -1 && b >= -9 || b >= 1 && b <= 9)
		{
			set(b);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Вы ввели неправельное значение!");
		}
	}
	void set(int a)
	{
		double n = a > 0 ? a : 1 / (double)(-a);
		if(parent.set(x, y, n))
		{
			dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Произошла непредвиденная ошибка!");
		}
	}
}
