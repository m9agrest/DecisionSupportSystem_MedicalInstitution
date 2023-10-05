/**
 * Объект для хранения и работы с Парной матрицей
 * @see #isCached()
 */
public class MatrixPaired
{
	private String[] Title;
	private double[][] MatrixPaired;
	private double[][] MatrixNormalized;
	private double[] Wi;
	private double Nmax = none;
	private boolean cache = false;
	private static short none = -1000;

	/**
	 * Конструктор
	 * @param n Размер матрицы
	 * @param cached Должны ли кэшироваться данные
	 * @see #isCached()
	 */
	public MatrixPaired(int n, boolean cached)
	{
		cache = cached;
		MatrixPaired = new double[n][n];
		Title = new String[n];
		for(int i =0; i < n; i++)
		{
			Title[i] = ""+i;
			MatrixPaired[i][i] = 1;
		}
	}

	/**
	 * Конструктор
	 * @param n Размер матрицы
	 */
	public MatrixPaired(int n)
	{
		this(n, false);
	}

	/**
	 * Функция возвращающая информацию, о кэшированнии данных
	 * @apiNote
	 * Кэширование по умолчанию не устанавливается.
	 * Если данное значение установлено на true, то используется минимум расчетов и все вычисленные данные сохраняются и не требуют перерасчетов,
	 * но пользователь может изменить кэшированные данные через получение ссылки на них с помощью методов:
	 * {@link #getMatrixNormalized()}, {@link #getWi()}.
	 * В противном случае, тратится вычислительная мощность, на перерасчет данных объектов.
	 * Так-же, метод {@link #getMatrixPaired()} дает ссылку на матрицу, которая всегда находится в кэше.
	 * @see #ClaerCache()
	 * @see #setCached(boolean)
	 * @see #setMatrixPaired(int, int, double)
	 * @see #MatrixPaired(int, boolean)
	 */
	public boolean isCached()
	{
		return cache;
	}

	/**
	 * Является ли матрица приемлемо согласованной
	 */
	public boolean isConsistent()
	{
		return getCR() <= 0.1;
	}

	/**
	 * Является ли матрица идеально согласованной
	 */
	public boolean isPerfectlyConsistent()
	{
		return getNmax() == getN();
	}

	/**
	 * Функция отчистки кэша
	 * @see #isCached()
	 */
	public void ClaerCache()
	{
		MatrixNormalized = null;
		Wi = null;
		Nmax = none;
	}

	/**
	 * Функция изменения статуса кэширования
	 * @param cached должны ли кэшироваться данные. В случае если не должны, то автоматически вызывается {@link #ClaerCache()}
	 * @see #isCached()
	 */
	public void setCached(boolean cached)
	{
		if(!cached)
		{
			ClaerCache();
		}
		this.cache = cached;
	}

	/**
	 * Проверяет, является ли парная матрица, полностью заполненой
	 * @return true если в ней нет значений с 0
	 * @see #setMatrixPaired(int, int, double)
	 */
	public boolean isFullMatrixPaired()
	{
		for(int i = 1; i < getN(); i++)
			for(int j = i; j < getN(); j++)
				if(MatrixPaired[i][j] == 0)
					return false;
		return true;
	}

	/**
	 * Функция по устанавления данных в матрицу MatrixPaired
	 * @param x позиция по x, которое должно быть от 1 до N-1
	 * @param y позиция по y, которое должно быть от 1 до N-1
	 * @param number устанавливаемое значение, которое должно быть от 1 до N
	 * @return установилось ли значение. Если установилось, то вызывается функция {@link #ClaerCache()}
	 * @<code>
	 *     MatrixPaired[x][y] = number;
	 *     <br>
	 *     MatrixPaired[y][x] = 1 / number;
	 * </code>
	 * @see #isCached()
	 */
	public boolean setMatrixPaired(int x, int y, double number)
	{
		if(x == y || x < 0 || y < 0 || x >= getN() || y >= getN() || number <= 0 || number > 9)
		{
			return false;
		}
		MatrixPaired[x][y] = number;
		MatrixPaired[y][x] = 1 / number;
		ClaerCache();
		return true;
	}

	/**
	 * Размер парной матрицы
	 * @return значение больше 0;
	 */
	public int getN()
	{
		return MatrixPaired.length;
	}

	/**
	 * Парная матрица
	 * @return ссылка на объект
	 */
	public double[][] getMatrixPaired()
	{
		return MatrixPaired;
	}

	/**
	 * Нормализованная матрица
	 * @return ссылка на объект
	 */
	public double[][] getMatrixNormalized()
	{
		if(MatrixNormalized != null)
		{
			return MatrixNormalized;
		}
		double[][] MatrixNormalized = MatrixNormalized(getN(), MatrixPaired);
		if(cache)
		{
			this.MatrixNormalized = MatrixNormalized;
		}
		return MatrixNormalized;
	}

	/**
	 * Wi - Среднее значение строк нормализованной матрицы, или относительный вес
	 * @return ссылка на объект
	 */
	public double[] getWi()
	{
		if(Wi != null)
		{
			return Wi;
		}
		double[][] MatrixNormalized = this.MatrixNormalized != null ?
				this.MatrixNormalized :
				MatrixNormalized(getN(), MatrixPaired);
		double[] Wi = Wi(getN(), MatrixNormalized);
		if(cache)
		{
			this.MatrixNormalized = MatrixNormalized;
			this.Wi = Wi;
		}
		return Wi;
	}

	/**
	 * Коэфициент Nmax
	 * @return числовое значение
	 */
	public double getNmax()
	{
		if(Nmax != none)
		{
			return Nmax;
		}
		double[][] MatrixNormalized = this.MatrixNormalized != null ?
				this.MatrixNormalized :
				MatrixNormalized(getN(), MatrixPaired);
		double[] Wi = this.Wi != null ?
				this.Wi :
				Wi(getN(), MatrixNormalized);
		double Nmax = nMax(getN(), MatrixPaired, Wi);
		if(cache)
		{
			this.MatrixNormalized = MatrixNormalized;
			this.Wi = Wi;
			this.Nmax = Nmax;
		}
		return Nmax;
	}

	/**
	 * Коэфициент согласованности матрицы
	 * @return числовое значение
	 */
	public double getRI()
	{
		return RI(getN());
	}

	/**
	 * Стохастический коэффициент согласованности матрицы
	 * @return числовое значение
	 */
	public double getCI()
	{
		return CI(getN(), getNmax());
	}

	/**
	 * Уровень согласованности матрицы
	 * @return числовое значение
	 */
	public double getCR()
	{
		return CR(getCI(), getRI());
	}

	public String[] getTitle()
	{
		return Title;
	}
	public boolean setTitle(String[] Title)
	{
		if(this.Title.length != Title.length)
			return false;
		this.Title = Title;
		return true;
	}
	public boolean setTitle(int index, String Title)
	{
		if(index < 0 || index >= this.Title.length)
			return false;
		this.Title[index] = Title;
		return true;
	}
	static double[][] MatrixNormalized(int n, double[][] MatrixPaired)
	{
		double[][] MatrixNormalized = new double[n][n];
		double[] MatrixPairedDivider = new double[n];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				MatrixPairedDivider[j] += MatrixPaired[i][j];
			}
		}
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				MatrixNormalized[i][j] = MatrixPaired[i][j] / MatrixPairedDivider[j];
			}
		}
		return MatrixNormalized;
	}
	static double[] Wi(int n, double[][] MatrixNormalized)
	{
		double[] Wi = new double[n];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				Wi[i] += MatrixNormalized[i][j];
			}
			Wi[i] /= n;
		}
		return Wi;
	}
	static double nMax(int n, double[][] MatrixPaired, double[] Wi)
	{
		double nMax = 0;
		double[] MatrixMultiply = new double[n];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				MatrixMultiply[i] += MatrixPaired[i][j] * Wi[j];
			}
		}
		for(int i = 0; i < n; i++)
		{
			nMax += MatrixMultiply[i];
		}
		return nMax;
	}
	static double CI(double n, double nMax)
	{
		return (nMax - n) / (n - 1);
	}
	static double RI(double n)
	{
		return 1.98 * (n - 2) / n;
	}
	static double CR(double CI, double RI)
	{
		return CI / RI;
	}
}