import javax.swing.*;

public class WindowStart extends JFrame
{
	JTextField inputCountCriteria;
	JTextField inputCountAlternatives;
	public WindowStart()
	{
		setTitle("Ввод данных");
		/*inputCountCriteria = new JTextField();
		inputCountCriteria.setBounds(160,0,60,30);
		inputCountAlternatives = new JTextField();
		inputCountAlternatives.setBounds(160,40,60,30);*/
		/*JLabel text1 = new JLabel("Кол-во критериев");
		text1.setBounds(0, 0, 150, 30);
		JLabel text2 = new JLabel("Кол-во альтернатив");
		text2.setBounds(0, 40, 150, 30);*/
		inputCountAlternatives = new JTextField();
		inputCountAlternatives.setBounds(160,0,60,30);
		JLabel text2 = new JLabel("Кол-во альтернатив");
		text2.setBounds(0, 0, 150, 30);


		JButton button = new JButton("ввод");
		button.setBounds(0, 80, 120, 30);
		button.addActionListener(e -> { click(); });
		//add(inputCountCriteria);
		add(inputCountAlternatives);
		//add(text1);
		add(text2);
		add(button);
		setSize(250, 150);
		setResizable(false);
		setLayout(null);
		setVisible(true);
	}
	void click()
	{
		int Criteria = 6;
		int Alternatives = 0;
		try
		{
			//Criteria = Integer.parseInt(inputCountCriteria.getText());
			Alternatives = Integer.parseInt(inputCountAlternatives.getText());
		} catch (Exception e){}
		if(Criteria < 3 || Alternatives < 3)
		{
			JOptionPane.showMessageDialog(this,"Вы ввели неправельные значения!");
		}
		else
		{
			//String[] CriteriaT = new String[Criteria];
			String[] CriteriaT = new String[]
					{
							"Эффективность лечения",
							"Отношение персонала",
							"Качество оборудования",
							"Быстрота обслуживания",
							"Стоимость услуг",
							"Отзывы"
					};
			String[] AlternativesT = new String[Alternatives];
			/*for(int i = 0; i < Criteria; i++)
			{
				CriteriaT[i] = JOptionPane.showInputDialog(this, "Введите название критерия №" + (i+1));
			}*/
			for(int i = 0; i < Alternatives; i++)
			{
				AlternativesT[i] = JOptionPane.showInputDialog(this, "Введите название альтернативы №" + (i+1));
			}
			MatrixPairedAlternatives M = new MatrixPairedAlternatives(Alternatives, Criteria);
			M.getCriteria().setTitle(CriteriaT);
			for(int i = 0; i < Criteria; i++)
			{
				M.getAlternative(i).setTitle(AlternativesT);
			}
			new WindowMain(this, M);
			dispose();
		}
	}
}
