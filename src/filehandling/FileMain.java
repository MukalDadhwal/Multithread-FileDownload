package multifiledownloader.src.filehandling;

//  SERVER -> THREAD 1, THREAD 2
//  EACH THREAD WILL DOWNLOAD DIFFERENT FILES

public class FileMain {

    public static void main(String[] args) throws InterruptedException {

        Runnable r1 = () -> {
            FileHandler handler = new FileHandler();
            System.out.println("Thread 1 started downloading file");

            handler.downloadFile("java_topics.txt");

            System.out.println("Thread 1 finished downloading file");
        };
        Runnable r2 = () -> {
            FileHandler handler = new FileHandler();
            System.out.println("Thread 2 started downloading file");
            handler.downloadFile("superman_pic.jpeg");
            System.out.println("Thread 2 finished downloading file");
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

    }
}
