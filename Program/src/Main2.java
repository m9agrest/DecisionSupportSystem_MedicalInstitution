import java.util.Arrays;

public class Main2
{
	public static void main(String[] args)
	{
		MatrixPairedAlternatives A = new MatrixPairedAlternatives(3, 6);
		MatrixPaired M = A.getCriteria();
		M.setMatrixPaired(0, 1, 1);
		M.setMatrixPaired(0, 2, 4);
		M.setMatrixPaired(0, 3, 2);
		M.setMatrixPaired(0, 4, 3);
		M.setMatrixPaired(0, 5, 2);
		M.setMatrixPaired(1, 2, 5);
		M.setMatrixPaired(1, 3, 2);
		M.setMatrixPaired(1, 4, 5);
		M.setMatrixPaired(1, 5, 3);
		M.setMatrixPaired(2, 3, 1);
		M.setMatrixPaired(2, 4, 3);
		M.setMatrixPaired(2, 5, 2);
		M.setMatrixPaired(3, 4, 3);
		M.setMatrixPaired(3, 5, 4);
		M.setMatrixPaired(5, 4, 4);

		print("Критерии", M);

		M = A.getAlternative(0);
		M.setMatrixPaired(0, 1, 4);
		M.setMatrixPaired(0, 2, 3);
		M.setMatrixPaired(1, 2, 1);
		print("Критерий 0", M);

		M = A.getAlternative(1);
		M.setMatrixPaired(0, 1, 2);
		M.setMatrixPaired(0, 2, 5);
		M.setMatrixPaired(1, 2, 4);
		print("Критерий 1", M);

		M = A.getAlternative(2);
		M.setMatrixPaired(0, 1, 2);
		M.setMatrixPaired(0, 2, 3);
		M.setMatrixPaired(1, 2, 2);
		print("Критерий 2", M);

		M = A.getAlternative(3);
		M.setMatrixPaired(0, 1, 1);
		M.setMatrixPaired(0, 2, 2);
		M.setMatrixPaired(1, 2, 3);
		print("Критерий 3", M);

		M = A.getAlternative(4);
		M.setMatrixPaired(0, 1, 3);
		M.setMatrixPaired(0, 2, 4);
		M.setMatrixPaired(1, 2, 2);
		print("Критерий 4", M);

		M = A.getAlternative(5);
		M.setMatrixPaired(0, 1, 2);
		M.setMatrixPaired(0, 2, 5);
		M.setMatrixPaired(1, 2, 5);
		print("Критерий 5", M);

		print("\n\nРезультаты", A.W());
	}

	static void print(String title, MatrixPaired matrix)
	{
		System.out.println(title);
		print("MatrixPaired", matrix.getMatrixPaired());
		print("MatrixNormalized", matrix.getMatrixNormalized());
		print("Wi", matrix.getWi());
		print("Nmax", matrix.getNmax());
		print("CI", matrix.getCI());
		print("RI", matrix.getRI());
		print("CR", matrix.getCR());
		System.out.println("\n");
	}
	static void print(String title, double[][] arr)
	{
		System.out.println(title);
		for(int i = 0; i < arr.length; i++)
		{
			for (int j = 0; j < arr[i].length; j++)
			{
				System.out.print(String.format("%.4f", arr[i][j]) + ' ');
			}
			System.out.println();
		}
	}
	static void print(String title, double[] arr)
	{
		System.out.print(title);
		for (int i = 0; i < arr.length; i++)
		{
			System.out.print(String.format(" %.4f", arr[i]));
		}
		System.out.println();
	}
	static void print(String title, double num)
	{
		System.out.print(title);
		System.out.println(String.format(" %.4f", num));
	}
}
