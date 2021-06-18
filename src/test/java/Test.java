import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class Test {
	public static void main(String[] args) throws IOException {
		Compressor compressor = new Compressor();
		FileInputStream streamIn = new FileInputStream("test/input.txt");
		byte[] bytes = new byte[streamIn.available()];
		streamIn.read(bytes);
		byte[] compressedBytes = compressor.compress(bytes);
		OutputStream outputStream = new FileOutputStream("test/read.txt");
		outputStream.write(bytes); // write the read byte array as is
		outputStream.close();
		outputStream.flush();
		
		outputStream = new FileOutputStream("test/output.compressed");
		outputStream.write(compressedBytes); // write the read byte array but compressed
		outputStream.close();
		outputStream.flush();
		
		outputStream = new FileOutputStream("test/output.compressed.gz");
		outputStream = new GZIPOutputStream(outputStream);
		outputStream.write(compressedBytes); // write the read byte array but compressed
		outputStream.close();
		outputStream.flush();
		
		outputStream = new FileOutputStream("test/decompressed.txt");
		outputStream.write(compressor.decompress(compressedBytes)); // write the read byte array but compressed then decompressed
		outputStream.close();
		outputStream.flush();
	}
}
