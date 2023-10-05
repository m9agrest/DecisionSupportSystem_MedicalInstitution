import javax.swing.*;

public class WindowResult extends JDialog
{
	public WindowResult(JFrame parent, double[] result, String[] title)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		setTitle("Результаты");

		for(int i = 0; i < result.length; i++)
		{
			JLabel text1 = new JLabel(title[i] + ":");
			JLabel text2 = new JLabel(result[i] + "");
			text1.setBounds(10, (i+1) * 20, 150, 20);
			text2.setBounds(170, (i+1) * 20, 60, 20);
			add(text1);
			add(text2);
		}
		setSize(240, result.length * 20+80);
		setLocation(parent.getLocation().x + 20, parent.getLocation().y + 20);
		setLayout(null);
		setResizable(false);
		setVisible(true);
	}
}
