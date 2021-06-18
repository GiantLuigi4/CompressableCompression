import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

public class Compressor {
	// if represented through a string, the compiler will error
	// doesn’t even matter if it’s a comment
	// https://onlinestringtools.com/convert-bytes-to-string
	// e2 80 9c 63 6f 6d 70 e2 80 9d 3b
	// TODO: hex string to byte array converter, as the above is hex (I believe)
	// or I could just write it to a file and use file input stream, I think
	private static final byte[] header = new byte[]{-128, 127, -123};
	
//	private static final BigInteger magicNumber = new BigInteger("" + (2048*2048*256+10));
	private static final BigInteger magicNumber = new BigInteger("" + (255));
	
	protected byte[] _$internCompress(String str) {
		HashMap<Integer, BigInteger> map = new HashMap<>();
		{
			BigInteger amt = new BigInteger(str);
			int i = 0;
			while (true) {
//				map.put(i, amt);
				if (amt.compareTo(new BigInteger("255")) < 0){
					map.put(i, amt);
					break;
				} else {
					map.put(i, magicNumber);
				}
				amt = amt.divide(magicNumber);
				i++;
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BigInteger checker = (map.get(map.size() - 1));
		for (int i = 0; i < map.keySet().size() - 1; i++) {
			BigInteger bi = map.get(i);
			checker = checker.multiply(bi);
		}
		System.out.println(checker.toString());
		System.out.println(str);
//		BigInteger src = new BigInteger(str);
/*		BigInteger lastAmt = new BigInteger("0");
		for (Integer integer : map.keySet()) {
			BigInteger num = map.get(integer);
			{
				BigInteger amt1 = new BigInteger(num.toString()).divide(magicNumber).multiply(magicNumber);
				int i = num.subtract(amt1).intValue();
				num = new BigInteger("" + (i + 1));
			}
//			System.out.println(num);
			checker = checker.multiply(magicNumber);
			checker = checker.multiply(num);
			checker = checker.add(num);
			lastAmt = num;
			out.write(num.intValue());
		}*/
//		checker = checker.multiply(lastAmt);
//		System.out.println(checker.toString());
//		System.out.println(str);
//		System.out.println(Arrays.toString(out.toByteArray()));
//		BigInteger src = new BigInteger(str);
//		BigInteger amt = new BigInteger(src.toString()).divide(magicNumber);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		if (str.length() > 3) {
//			try {
//				out.write(_$internCompress(amt.toString()));
//			} catch (Throwable ignored) {
//			}
//		} else {
//			for (BigInteger working = new BigInteger("0"); working.compareTo(amt) < 1; working = working.add(new BigInteger("1"))) { // I have never done a big integer for loop before
//				out.write((char) 0);
//			}
//		}
//		BigInteger amt1 = new BigInteger(src.toString()).divide(magicNumber).multiply(magicNumber);
//		int i = src.subtract(amt1).intValue();
//		if (i != 0) out.write((char) (i));
		return out.toByteArray();
	}
	
	protected String _toByteStr(String src) {
		byte[] bytes = src.getBytes();
		StringBuilder str = new StringBuilder();
		for (byte aByte : bytes) {
			StringBuilder str1 = new StringBuilder("" + (((int) aByte) + 128));
			while (str1.length() < 3) str1.insert(0, "0");
			str.append(str1);
		}
		return str.toString();
	}
	
	protected String _toByteStr(byte[] bytes) {
		StringBuilder str = new StringBuilder();
		for (byte aByte : bytes) {
			StringBuilder str1 = new StringBuilder("" + (((int) aByte) + 128));
			while (str1.length() < 3) str1.insert(0, "0");
			str.append(str1);
		}
		return str.toString();
	}
	
	protected byte[] _internCompress(String str) {
//		BigInteger src = new BigInteger(str);
//		BigInteger amt = new BigInteger(src.toString()).divide(magicNumber);
//		StringBuilder out = new StringBuilder();
//		out.append(_$internCompress(amt.toString()));
//		BigInteger amt1 = new BigInteger(src.toString()).divide(magicNumber).multiply(magicNumber);
//		out.append((char) (src.subtract(amt1).intValue()));
//		return out.toString();
		return _$internCompress(str);
	}
	
	protected String __internCompress(String str) {
		int num = 0;
		while (str.startsWith("" + (char)0)) {
			str = str.substring(1);
			num++;
		}
		str = ("" + num) + (char)0 + str;
		int lowest = 256;
		byte[] bytes = str.getBytes();
		for (byte aByte : bytes) {
			lowest = Math.min(lowest, aByte);
		}
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] - lowest);
		}
//		try {
//			ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
//			GZIPOutputStream stream = new GZIPOutputStream(stream1);
////			stream.write(str.getBytes());
//			stream.write((("" + (lowest + 128)) + (char)0 + new String(bytes)).getBytes());
//			stream.finish();
//			return new String(stream1.toByteArray());
//		}catch (Throwable ignored) {
//		}
		return ("" + (lowest + 128)) + (char)0 + new String(bytes);
//		return str;
//		BigInteger count = new BigInteger("0");
//		char firstC = str.charAt(0);
//		StringBuilder builder = new StringBuilder(new String(header));
//		boolean hasAppendedInt = false;
//		for (char c : str.toCharArray()) {
//			if (!hasAppendedInt && c == firstC) count = count.add(new BigInteger("1"));
//			else {
//				if (!hasAppendedInt) {
//					// TODO: append a total of (count / 255) instances of character 255 then append the remainder casted to a character
//					builder.append(count.toString());
//				}
//				hasAppendedInt = true;
//				builder.append(c);
//			}
//		}
//		if (!hasAppendedInt) builder.append(count.toString());
//		return builder.toString();
	}
	
	public String compress(String src) {
//		src = __internCompress(src);
		src = _toByteStr(src);
		String strC1 = new String(_internCompress(src));
		String strC2 = __internCompress(strC1);
//		return new String(header) + strC1;
		return strC1;
//		return strC2;
	}
	
	public byte[] compress(byte[] bytes) {
		String src = _toByteStr(bytes);
		String strC1 = new String(_internCompress(src));
		return strC1.getBytes();
	}
	
	// TODO: actual compression method which works on the entire string instead of just on a section of the string which is the series of bytes of the original string
	// TODO: test xz and gzip on _internCompress
	// TODO: test xz and gzip on __internCompress
	
	//  TODO: benchmarking compared to gzip and xz of both time and compression ratio
	public static void main(String[] args) {
		Compressor compressor = new Compressor();
//		long longVal = 52053064123032041064L;
//		System.out.println(longVal);
//		System.out.println((longVal / 255) * 255);
//		System.out.println(longVal - ((longVal / 255) * 255));
		{
			String str = "052053064123032041064";
			System.out.println(str.length());
			System.out.println(compressor._srcString(str));
			String strC1 = new String(compressor._internCompress(str));
			System.out.println(strC1);
			String strC2 = compressor.__internCompress(strC1);
			System.out.println(strC2.length());
			System.out.println(strC2);
		}
		System.out.println();
		{
			String str = "012 000 023 054 064 023 012 054 089 094 029 128 100 054 065 078 084 090 128 054".replace(" ", "");
			compressAndPrint(compressor._srcString(str), compressor);
//			System.out.println(str.length());
//			System.out.println(compressor._srcString(str));
//			String strC1 = compressor._internCompress(str);
//			System.out.println(strC1);
//			String strC2 = compressor.__internCompress(strC1);
//			System.out.println(strC2.length());
//			System.out.println(strC2);
		}
		System.out.println();
//		compressAndPrint("hello there", compressor);
		try {
//			String str = "hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?hello there, how are you doing on this fine day?";
			String str = "45";
//			str = str.replace("\n", "").replace("\t", "");
			FileOutputStream stream = new FileOutputStream("test.txt");
			System.out.println(Arrays.toString(str.getBytes()));
			stream.write(str.getBytes());
			stream.close();
			stream = new FileOutputStream("test.compressed");
			byte[] compressed = str.getBytes();
			String comp = compressAndPrint(str, compressor);
			stream.write(comp.getBytes());
			stream.close();
			System.out.println(compressor._decompress(comp));
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
//		{
//			String str = compressor._toByteStr("hello there");
//			System.out.println(str);
////			System.out.println(str);
//			System.out.println(str.length());
//			System.out.println(compressor._srcString(str.toString()));
//			String strC1 = compressor._internCompress(str.toString());
//			System.out.println(strC1);
//			String strC2 = compressor.__internCompress(strC1);
//			System.out.println(strC2.length());
//			System.out.println(strC2);
//			System.out.println(compressor._decompress(strC2));
//		}
//		System.out.println();
//		{
//			byte[] bytes = ("21\n" +
//					"45@{ )@\n" +
//					"ÿÿÿèqÌtb\u0085\u008D\u009F\n" +
//					"10\n" +
//					"-3èqÌtb\u0085\u008D\u009F\n" +
//					"60\n" +
//					"\f \u00176@\u0017\f6Y^\u001D�d6ANTZ�6\n" +
//					"ÿÿÿ\u0019\u007F\u0003¤\u0090k¦2<¬VZj~&à`ØHòFZ¸\u0086\n" +
//					"26\n" +
//					"-3\u0019\u007F\u0003¤\u0090k¦2<¬VZj~&à`ØHòFZ¸\u0086\n" +
//					"33\n" +
//					"hello there\n" +
//					"ÿ\u0005f\u001Fºü\u0086¿&³fuÂäì\n" +
//					"16\n" +
//					"-1\u0005f\u001Fºü\u0086¿&³fuÂäì").getBytes();
//			String str1 = compressAndPrint(new String(bytes), compressor);
//			compressAndPrint(str1, compressor);
//			StringBuilder str = new StringBuilder();
//			for (byte aByte : bytes) {
//				StringBuilder str1 = new StringBuilder("" + (((int) aByte) + 128));
//				while (str1.length() < 3) str1.insert(0, "0");
////				System.out.println(str1);
//				str.append(str1);
//			}
////			System.out.println(str);
//			System.out.println(str.length());
////			System.out.println(compressor._srcString(str.toString()));
//			String strC1 = compressor._internCompress(str.toString());
//			System.out.println(strC1);
//			String strC2 = compressor.__internCompress(strC1);
//			System.out.println(strC2.length());
//			System.out.println(strC2);
//		}
	}
	
	private String _decompress(String strC2) {
//		if (!strC2.startsWith(new String(header))) return strC2; // TODO: throw an exception
		String strc1 = strC2.substring(0);
		return new String(decompress(strc1.getBytes()));
	}
	
	protected String _srcString(String str) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		while (str.length() > 1) {
			String section = str.substring(0, 3);
			str = str.substring(3);
			stream.write((byte) (Integer.parseInt(section) - 128));
		}
		return new String(stream.toByteArray());
	}
	
	private static String compressAndPrint(String str, Compressor compressor) {
		System.out.println();
		System.out.println(str.getBytes().length + ": " + str);
		String compressed = compressor.compress(str);
		System.out.println();
		System.out.println(compressed.getBytes().length + ": " + compressed);
		return compressed;
	}
	
	public byte[] decompress(byte[] compressedBytes) {
		BigInteger num = new BigInteger("1");
		int i = 0;
		System.out.println(Arrays.toString(compressedBytes));
		for (byte b : compressedBytes) {
//			System.out.println(b);
//			num = num.add(new BigInteger("255").multiply(magicNumber).multiply(new BigInteger((i + 128) + "")));
			num = num.multiply(new BigInteger("" + (255)).multiply(magicNumber));
//			num = num.multiply(new BigInteger("" + (255)).multiply(magicNumber).multiply(new BigInteger((i + 1) + "")));
//			num = num.add(new BigInteger("" + (b + 128)));
			i++;
//			num = num.add(new BigInteger(""+(int)(b + 128)).multiply(magicNumber));
		}
//		System.out.println(num.toString());
//		return "".getBytes();
//		System.out.println(num.toString());
		return _toByteStr(num.toString()).getBytes();
	}
}

