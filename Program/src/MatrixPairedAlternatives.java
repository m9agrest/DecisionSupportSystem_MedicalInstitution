public class MatrixPairedAlternatives
{
	private MatrixPaired[] Alternatives;
	private MatrixPaired Criteria;

	/**
	 * Конструткор
	 * @param CountAlternatives количество альтернатив
	 * @param CountCriteries количество критериев
	 */
	public MatrixPairedAlternatives(int CountAlternatives, int CountCriteries)
	{
		Criteria = new MatrixPaired(CountCriteries);
		Alternatives = new MatrixPaired[CountCriteries];
		for(int i = 0; i < Alternatives.length; i++)
		{
			Alternatives[i] = new MatrixPaired(CountAlternatives);
		}
	}

	/**
	 * Количество матриц альтернатив, которое ровняется количеству критериев
	 */
	public int getAlternativeLength()
	{
		return Alternatives.length;
	}

	/**
	 * Количество альтернатив
	 */
	public int getAlternativeCount()
	{
		return Alternatives[0].getN();
	}

	/**
	 * Матрица альтернатив по определенному критерию
	 * @param index индекс критерия
	 * @return ссылку на матрицу
	 */
	public MatrixPaired getAlternative(int index)
	{
		if(index < 0 || index >= Alternatives.length)
			return null;
		return Alternatives[index];
	}

	/**
	 * Матрица критериев
	 * @return ссылку на матрицу
	 */
	public MatrixPaired getCriteria()
	{
		return Criteria;
	}

	/**
	 * Устанавливает значения в матрице критериев
	 * @see MatrixPaired#setMatrixPaired(int, int, double)
	 */
	public boolean setCriteria(int x, int y, double number)
	{
		return Criteria.setMatrixPaired(x, y, number);
	}

	/**
	 * Устанавливает значение в матрице альтернатив
	 * @param index индекс критерия
	 * @see MatrixPaired#setMatrixPaired(int, int, double)
	 */
	public boolean setAlternatives(int index, int x, int y, double number)
	{
		MatrixPaired a = getAlternative(index);
		if(a == null)
			return false;
		return a.setMatrixPaired(x, y, number);
	}

	/**
	 * Весовые коэфециенты для каждой альтернативы, по которым делается выбор
	 * @return массив
	 */
	public double[] W()
	{
		double[][] Wa = new double[Alternatives.length][];
		for(int i = 0; i < Wa.length; i++) Wa[i] = Alternatives[i].getWi();

		double[] Wc = Criteria.getWi();
		double[] W = new double[Alternatives[0].getN()];
		for(int i = 0; i < Wa.length; i++)
			for(int j = 0; j < Wa[i].length; j++)
				W[j] += Wa[i][j] * Wc[i];
		return W;
	}

	/**
	 * Являются ли все матрицы приемлемо согласованны
	 */
	public boolean isConsistent()
	{
		if(!Criteria.isConsistent())
			return false;
		for(int i = 0; i < Alternatives.length; i++)
			if(!Alternatives[i].isConsistent())
				return false;
		return true;
	}

	/**
	 * Являются ли все матрицы идеально согласованны
	 * @return
	 */
	public boolean isPerfectlyConsistent()
	{
		if(!Criteria.isPerfectlyConsistent())
			return false;
		for(int i = 0; i < Alternatives.length; i++)
			if(!Alternatives[i].isPerfectlyConsistent())
				return false;
		return true;
	}

	/**
	 * Заполнены ли все матрицы
	 * @return
	 */
	public boolean isFull()
	{
		if(!Criteria.isFullMatrixPaired())
			return false;
		for(int i = 0; i < Alternatives.length; i++)
			if(!Alternatives[i].isFullMatrixPaired())
				return false;
		return true;
	}
}
