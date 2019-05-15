using System;
using System.Diagnostics;

namespace lab2
{
    public class Boss
    {
        static void Main(string[] args)
        {
            string input;
            Console.WriteLine("Введите количество процессов Parent: ");
            do
            {
                input = Console.ReadLine();
            } while (!int.TryParse(input, out int numParent));

            Console.WriteLine("Введите количество процессов Child: ");
            do
            {
                input = Console.ReadLine();
            } while (!int.TryParse(input, out int numChild));





        }
    }
}
