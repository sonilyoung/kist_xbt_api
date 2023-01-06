package egovframework.com.api.crypto;

import java.math.BigInteger;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/* Revision History
 * Author              Date             Description
 * ------------------  --------------   ------------------
 * jaehong hwang	   2022. 06. 20.     
 */
public class Base64Encoder implements BinaryEncoder, BinaryDecoder {

	static final int CHUNK_SIZE = 76;
	static final byte CHUNK_SEPARATOR[] = { 13, 10 };
	private static final byte STANDARD_ENCODE_TABLE[] = { 65, 66, 67, 68, 69,
			70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
			87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107,
			108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
			121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
	private static final byte URL_SAFE_ENCODE_TABLE[] = { 65, 66, 67, 68, 69,
			70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
			87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107,
			108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
			121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
	private static final byte DECODE_TABLE[] = { -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
			-1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
	private final byte encodeTable[];
	private final int lineLength;
	private final byte lineSeparator[];
	private final int decodeSize;
	private final int encodeSize;
	private byte buffer[];
	private int pos;
	private int readPos;
	private int currentLinePos;
	private int modulus;
	private boolean eof;
	private int x;

	public Base64Encoder() {
		this(false);
	}

	public Base64Encoder(boolean urlSafe) {
		this(76, CHUNK_SEPARATOR, urlSafe);
	}

	public Base64Encoder(int lineLength) {
		this(lineLength, CHUNK_SEPARATOR);
	}

	public Base64Encoder(int lineLength, byte lineSeparator[]) {
		this(lineLength, lineSeparator, false);
	}

	public Base64Encoder(int lineLength, byte lineSeparator[], boolean urlSafe) {
		if (lineSeparator == null) {
			lineLength = 0;
			lineSeparator = CHUNK_SEPARATOR;
		}
		this.lineLength = lineLength <= 0 ? 0 : (lineLength / 4) * 4;
		this.lineSeparator = new byte[lineSeparator.length];
		System.arraycopy(lineSeparator, 0, this.lineSeparator, 0,
				lineSeparator.length);
		if (lineLength > 0)
			encodeSize = 4 + lineSeparator.length;
		else
			encodeSize = 4;
		decodeSize = encodeSize - 1;
		if (containsBase64Byte(lineSeparator)) {
			String sep = StringUtils.newStringUtf8(lineSeparator);
			throw new IllegalArgumentException(
					"lineSeperator must not contain base64 characters: [" + sep
							+ "]");
		} else {
			encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE
					: STANDARD_ENCODE_TABLE;
			return;
		}
	}

	public boolean isUrlSafe() {
		return encodeTable == URL_SAFE_ENCODE_TABLE;
	}

	boolean hasData() {
		return buffer != null;
	}

	int avail() {
		return buffer == null ? 0 : pos - readPos;
	}

	private void resizeBuffer() {
		if (buffer == null) {
			buffer = new byte[8192];
			pos = 0;
			readPos = 0;
		} else {
			byte b[] = new byte[buffer.length * 2];
			System.arraycopy(buffer, 0, b, 0, buffer.length);
			buffer = b;
		}
	}

	int readResults(byte b[], int bPos, int bAvail) {
		if (buffer != null) {
			int len = Math.min(avail(), bAvail);
			if (buffer != b) {
				System.arraycopy(buffer, readPos, b, bPos, len);
				readPos += len;
				if (readPos >= pos)
					buffer = null;
			} else {
				buffer = null;
			}
			return len;
		} else {
			return eof ? -1 : 0;
		}
	}

	void setInitialBuffer(byte out[], int outPos, int outAvail) {
		if (out != null && out.length == outAvail) {
			buffer = out;
			pos = outPos;
			readPos = outPos;
		}
	}

	void encode(byte in[], int inPos, int inAvail) {
		if (eof)
			return;
		if (inAvail < 0) {
			eof = true;
			if (buffer == null || buffer.length - pos < encodeSize)
				resizeBuffer();
			switch (modulus) {
			case 1: // '\001'
				buffer[pos++] = encodeTable[x >> 2 & 63];
				buffer[pos++] = encodeTable[x << 4 & 63];
				if (encodeTable == STANDARD_ENCODE_TABLE) {
					buffer[pos++] = 61;
					buffer[pos++] = 61;
				}
				break;

			case 2: // '\002'
				buffer[pos++] = encodeTable[x >> 10 & 63];
				buffer[pos++] = encodeTable[x >> 4 & 63];
				buffer[pos++] = encodeTable[x << 2 & 63];
				if (encodeTable == STANDARD_ENCODE_TABLE)
					buffer[pos++] = 61;
				break;
			}
			if (lineLength > 0 && pos > 0) {
				System.arraycopy(lineSeparator, 0, buffer, pos,
						lineSeparator.length);
				pos += lineSeparator.length;
			}
		} else {
			for (int i = 0; i < inAvail; i++) {
				if (buffer == null || buffer.length - pos < encodeSize)
					resizeBuffer();
				modulus = ++modulus % 3;
				int b = in[inPos++];
				if (b < 0)
					b += 256;
				x = (x << 8) + b;
				if (0 != modulus)
					continue;
				buffer[pos++] = encodeTable[x >> 18 & 63];
				buffer[pos++] = encodeTable[x >> 12 & 63];
				buffer[pos++] = encodeTable[x >> 6 & 63];
				buffer[pos++] = encodeTable[x & 63];
				currentLinePos += 4;
				if (lineLength > 0 && lineLength <= currentLinePos) {
					System.arraycopy(lineSeparator, 0, buffer, pos,
							lineSeparator.length);
					pos += lineSeparator.length;
					currentLinePos = 0;
				}
			}

		}
	}

	void decode(byte in[], int inPos, int inAvail) {
		if (eof)
			return;
		if (inAvail < 0)
			eof = true;
		for (int i = 0; i < inAvail; i++) {
			if (buffer == null || buffer.length - pos < decodeSize)
				resizeBuffer();
			byte b = in[inPos++];
			if (b == 61) {
				eof = true;
				break;
			}
			if (b < 0 || b >= DECODE_TABLE.length)
				continue;
			int result = DECODE_TABLE[b];
			if (result < 0)
				continue;
			modulus = ++modulus % 4;
			x = (x << 6) + result;
			if (modulus == 0) {
				buffer[pos++] = (byte) (x >> 16 & 255);
				buffer[pos++] = (byte) (x >> 8 & 255);
				buffer[pos++] = (byte) (x & 255);
			}
		}

		if (eof && modulus != 0) {
			x = x << 6;
			switch (modulus) {
			case 2: // '\002'
				x = x << 6;
				buffer[pos++] = (byte) (x >> 16 & 255);
				break;

			case 3: // '\003'
				buffer[pos++] = (byte) (x >> 16 & 255);
				buffer[pos++] = (byte) (x >> 8 & 255);
				break;
			}
		}
	}

	public static boolean isBase64(byte octet) {
		return octet == 61 || octet >= 0 && octet < DECODE_TABLE.length
				&& DECODE_TABLE[octet] != -1;
	}

	public static boolean isArrayByteBase64(byte arrayOctet[]) {
		for (int i = 0; i < arrayOctet.length; i++)
			if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i]))
				return false;

		return true;
	}

	private static boolean containsBase64Byte(byte arrayOctet[]) {
		for (int i = 0; i < arrayOctet.length; i++)
			if (isBase64(arrayOctet[i]))
				return true;

		return false;
	}

	public static byte[] encodeBase64(byte binaryData[]) {
		return encodeBase64(binaryData, false);
	}

	public static String encodeBase64String(byte binaryData[]) {
		return StringUtils.newStringUtf8(encodeBase64(binaryData, true));
	}

	public static byte[] encodeBase64URLSafe(byte binaryData[]) {
		return encodeBase64(binaryData, false, true);
	}

	public static String encodeBase64URLSafeString(byte binaryData[]) {
		return StringUtils.newStringUtf8(encodeBase64(binaryData, false, true));
	}

	public static byte[] encodeBase64Chunked(byte binaryData[]) {
		return encodeBase64(binaryData, true);
	}

	public Object decode(Object pObject) throws DecoderException {
		if (pObject instanceof byte[])
			return decode((byte[]) (byte[]) pObject);
		if (pObject instanceof String)
			return decode((String) pObject);
		else
			throw new DecoderException(
					"Parameter supplied to Base64 decode is not a byte[] or a String");
	}

	public byte[] decode(String pArray) {
		return decode(StringUtils.getBytesUtf8(pArray));
	}

	public byte[] decode(byte pArray[]) {
		reset();
		if (pArray == null || pArray.length == 0) {
			return pArray;
		} else {
			long len = (pArray.length * 3) / 4;
			byte buf[] = new byte[(int) len];
			setInitialBuffer(buf, 0, buf.length);
			decode(pArray, 0, pArray.length);
			decode(pArray, 0, -1);
			byte result[] = new byte[pos];
			readResults(result, 0, result.length);
			return result;
		}
	}

	public static byte[] encodeBase64(byte binaryData[], boolean isChunked) {
		return encodeBase64(binaryData, isChunked, false);
	}

	public static byte[] encodeBase64(byte binaryData[], boolean isChunked,
			boolean urlSafe) {
		return encodeBase64(binaryData, isChunked, urlSafe, 2147483647);
	}

	public static byte[] encodeBase64(byte binaryData[], boolean isChunked,
			boolean urlSafe, int maxResultSize) {
		if (binaryData == null || binaryData.length == 0)
			return binaryData;
		long len = getEncodeLength(binaryData, 76, CHUNK_SEPARATOR);
		if (len > (long) maxResultSize) {
			throw new IllegalArgumentException(
					"Input array too big, the output array would be bigger ("
							+ len + ") than the specified maxium size of "
							+ maxResultSize);
		} else {
			Base64Encoder b64 = isChunked ? new Base64Encoder(urlSafe)
					: new Base64Encoder(0, CHUNK_SEPARATOR, urlSafe);
			return b64.encode(binaryData);
		}
	}

	public static byte[] decodeBase64(String base64String) {
		return (new Base64Encoder()).decode(base64String);
	}

	public static byte[] decodeBase64(byte base64Data[]) {
		return (new Base64Encoder()).decode(base64Data);
	}

	/**
	 * @deprecated Method discardWhitespace is deprecated
	 */
	static byte[] discardWhitespace(byte data[]) {
		byte groomedData[] = new byte[data.length];
		int bytesCopied = 0;
		int i = 0;
		do
			if (i < data.length) {
				switch (data[i]) {
				default:
					groomedData[bytesCopied++] = data[i];
					// fall through

				case 9: // '\t'
				case 10: // '\n'
				case 13: // '\r'
				case 32: // ' '
					i++;
					break;
				}
			} else {
				byte packedData[] = new byte[bytesCopied];
				System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
				return packedData;
			}
		while (true);
	}

	private static boolean isWhiteSpace(byte byteToCheck) {
		switch (byteToCheck) {
		case 9: // '\t'
		case 10: // '\n'
		case 13: // '\r'
		case 32: // ' '
			return true;
		}
		return false;
	}

	public Object encode(Object pObject) throws EncoderException {
		if (!(pObject instanceof byte[]))
			throw new EncoderException(
					"Parameter supplied to Base64 encode is not a byte[]");
		else
			return encode((byte[]) (byte[]) pObject);
	}

	public String encodeToString(byte pArray[]) {
		return StringUtils.newStringUtf8(encode(pArray));
	}

	public byte[] encode(byte pArray[]) {
		reset();
		if (pArray == null || pArray.length == 0)
			return pArray;
		long len = getEncodeLength(pArray, lineLength, lineSeparator);
		byte buf[] = new byte[(int) len];
		setInitialBuffer(buf, 0, buf.length);
		encode(pArray, 0, pArray.length);
		encode(pArray, 0, -1);
		if (buffer != buf)
			readResults(buf, 0, buf.length);
		if (isUrlSafe() && pos < buf.length) {
			byte smallerBuf[] = new byte[pos];
			System.arraycopy(buf, 0, smallerBuf, 0, pos);
			buf = smallerBuf;
		}
		return buf;
	}

	private static long getEncodeLength(byte pArray[], int chunkSize,
			byte chunkSeparator[]) {
		chunkSize = (chunkSize / 4) * 4;
		long len = (pArray.length * 4) / 3;
		long mod = len % 4L;
		if (mod != 0L)
			len += 4L - mod;
		if (chunkSize > 0) {
			boolean lenChunksPerfectly = len % (long) chunkSize == 0L;
			len += (len / (long) chunkSize) * (long) chunkSeparator.length;
			if (!lenChunksPerfectly)
				len += chunkSeparator.length;
		}
		return len;
	}

	public static BigInteger decodeInteger(byte pArray[]) {
		return new BigInteger(1, decodeBase64(pArray));
	}

	public static byte[] encodeInteger(BigInteger bigInt) {
		if (bigInt == null)
			throw new NullPointerException(
					"encodeInteger called with null parameter");
		else
			return encodeBase64(toIntegerBytes(bigInt), false);
	}

	static byte[] toIntegerBytes(BigInteger bigInt) {
		int bitlen = bigInt.bitLength();
		bitlen = (bitlen + 7 >> 3) << 3;
		byte bigBytes[] = bigInt.toByteArray();
		if (bigInt.bitLength() % 8 != 0
				&& bigInt.bitLength() / 8 + 1 == bitlen / 8)
			return bigBytes;
		int startSrc = 0;
		int len = bigBytes.length;
		if (bigInt.bitLength() % 8 == 0) {
			startSrc = 1;
			len--;
		}
		int startDst = bitlen / 8 - len;
		byte resizedBytes[] = new byte[bitlen / 8];
		System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
		return resizedBytes;
	}

	private void reset() {
		buffer = null;
		pos = 0;
		readPos = 0;
		currentLinePos = 0;
		modulus = 0;
		eof = false;
	}
}// end of Base64Encoder.java