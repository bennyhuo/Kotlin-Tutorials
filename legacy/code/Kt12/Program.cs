using System;

public class Program
{
	public static void Main(String[] args)
	{
		testGeneric<string>();
	}

    public static void testGeneric<T>(){
        Console.WriteLine(typeof(T));
    }
}
