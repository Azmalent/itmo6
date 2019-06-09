using System;
using System.Diagnostics;
using System.IO;
using System.IO.MemoryMappedFiles;
using System.Reflection;
using System.Threading;

namespace Boss
{
    class BossProcess
    {
        private static Mutex mutex;
        private static EventWaitHandle msgEvent;
        private static EventWaitHandle closeEvent;
        private static MemoryMappedFile file;
        private static MemoryMappedViewAccessor fileAccessor;
        
        private static readonly string SCOUT_EXE_PATH;

        private static volatile int numScout;

        static BossProcess()
        {
            Console.SetWindowSize(50, 25);

            mutex = new Mutex(false, "OS2_Mutex", out bool createdNew);
            if (!createdNew)
            {
                Console.WriteLine("Приложение уже запущено.");
                Console.ReadKey();
                Environment.Exit(1);
            }

            var path = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            SCOUT_EXE_PATH = Path.GetFullPath(Path.Combine(path, @"..\..\..\", @"scout\bin\Debug\scout.exe"));

            msgEvent = new EventWaitHandle(false, EventResetMode.ManualReset, "OS2_MessageEvent");
            closeEvent = new EventWaitHandle(false, EventResetMode.AutoReset, "OS2_CloseEvent");

            file = MemoryMappedFile.CreateNew("OS2_Message", 1024);
            fileAccessor = file.CreateViewAccessor(0, 2, MemoryMappedFileAccess.Read);
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Введите количество процессов Scout: ");
            numScout = InputNumber();
            var scoutCounter = new Thread(WatchNumScout);
            scoutCounter.Start();
            
            for (int i = 0; i < numScout; i++)
            {
                Process.Start(SCOUT_EXE_PATH);
            }
            
            while (numScout > 0)
            {
                if(!msgEvent.WaitOne(100)) continue;
                char c = fileAccessor.ReadChar(0);
                Console.Write(c);
                msgEvent.Reset();
            }

            file.Dispose();
            mutex.Close();
            msgEvent.Close();
            closeEvent.Close();
        }

        private static int InputNumber()
        {
            int result;
            do
            {
                string input = Console.ReadLine();
                int.TryParse(input, out result);
            } while (result <= 0);

            return result;
        }

        private static void WatchNumScout()
        {
            while(numScout > 0)
            {
                closeEvent.WaitOne();
                numScout--;
            }
        }
    }
}