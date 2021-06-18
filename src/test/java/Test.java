import java.io.FileInputStream;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		Compressor compressor = new Compressor();
		FileInputStream streamIn = new FileInputStream("input.txt");
		byte[] bytes = new byte[streamIn.available()];
		streamIn.read(bytes);
		compressor.compress(bytes);
	}
}
