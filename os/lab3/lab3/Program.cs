using System;
using System.Threading;

namespace lab3
{
    class Program
    {
        static Semaphore mainWorkSemaphore = new Semaphore(0, 1);
        static Semaphore waitMultSemaphore = new Semaphore(0, 1);
        static volatile int[] numbers;
        
        static Thread work = new Thread(WorkThread);
        static Thread mult = new Thread(MultThread);

        static int InputInteger(bool mustBePositive = false)
        {
            int result;
            bool success;
            do
            {
                string input = Console.ReadLine();
                success = int.TryParse(input, out result);
            } while (!success || mustBePositive && result <= 0);
            
            return result;
        }

        static void WorkThread()
        {
            Console.WriteLine("[Work] Введите интервал ожидания в миллисекундах:");
            int waitInterval = InputInteger(true);

            for (int i = 0; i < numbers.Length; i++)
            {
                int min = int.MaxValue;
                int minIndex = i;
                for (int j = i; j < numbers.Length; j++)
                {
                    if(numbers[j] < min) 
                    {
                        min = numbers[j];
                        minIndex = j;
                    }
                }

                int temp = numbers[i];
                numbers[i] = numbers[minIndex];
                numbers[minIndex] = temp;

                Console.WriteLine("[Work] Новый элемент на позиции {0}: {1}", i, numbers[i]);
                mainWorkSemaphore.Release();
                Thread.Sleep(waitInterval);
            }
            
            waitMultSemaphore.Release();
        }
        
        static void MultThread()
        {
            waitMultSemaphore.WaitOne();
            
            int product = 1;
            for (int i = 0; i < numbers.Length; i++)
            {
                product *= numbers[i];
            }
            Console.WriteLine("[Mult] Произведение равно " + product);
        }

        static void Main(string[] args)
        {
            InputArray();
            Console.Clear();

            Console.WriteLine("[Main] Размерность массива: " + numbers.Length);
            Console.WriteLine("[Main] Элементы массива: " + string.Join(", ", numbers));

            work.Start();
            mult.Start();

            while (work.IsAlive && mult.IsAlive)
            {
                if (mainWorkSemaphore.WaitOne(1000))
                {
                    Console.WriteLine("[Main] Элементы массива: " + string.Join(", ", numbers));
                    Console.WriteLine();
                }
            }

            Console.WriteLine("Нажмите любую клавишу");
            Console.ReadKey();
        }

        private static void InputArray()
        {
            Console.WriteLine("[Main] Введите размерность массива:");
            int length = InputInteger(true);
            numbers = new int[length];

            Console.WriteLine("[Main] Введите элементы массива:");
            for (int i = 0; i < length; i++)
            {
                Console.Write("{0}) ", i + 1);
                numbers[i] = InputInteger();
            }
        }
    }
}
