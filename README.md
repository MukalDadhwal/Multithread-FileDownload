## Agenda

Utilising multiple threads to enable file faster downloads by dividing the payload into different threads. After testing an increase of about 53.84 % is seen when the file is downloading over 4 threads than 1 thread.

## Testing

```
javac -d bin src/multithreadeddownload/MultithreadedFileDownloader.java src/multithreadeddownload/DownloadTask.java && java -cp bin MultithreadedFileDownloader https://raw.githubusercontent.com/litterinchina/large-file-download-test/master/70M.txt 4
```


```
javac -d bin src/multithreadeddownload/SingleThreadDownloader.java && java -cp bin  SingleThreadDownloader https://raw.githubusercontent.com/litterinchina/large-file-download-test/master/70M.txt 
```