package okio.test325;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Callable;

import okio.test325.encoder.Encoder;

/**
 * Test for Okio issue #325
 *
 * @author Pavel Remygailo
 */

@SuppressWarnings("WeakerAccess")
public class OkioTest {
    protected Encoder goldenEncoder;
    public final String goldenHash;
    private final byte[] arr = genArray();

    public OkioTest(Encoder golden) {
        this.goldenEncoder = golden;
        try {
            goldenHash = testHash(golden);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Golden Encoder is broken.", e);
        }
    }

    protected final byte[] getArray() {
        if (arr != null) {
            byte[] tmp = new byte[arr.length];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            return tmp;
        }
        return genArray();
    }

    protected byte[] genArray() {
        final int count = 65536;
        byte[] arr = new byte[count];
        int i = 0;
        while (i < count) {
            arr[i++] = (byte) (i >> 8 & 0xff);
            arr[i++] = (byte) (i & 0xff);
        }
        return arr;
    }

    protected String hash(String s) {
        return md5(s);
    }

    public String test(Encoder eut) throws Exception {
        final String hash = testHash(eut);
        String result = "Hash: " + hash + '\n';
        if (!hash.equals(goldenHash))
            result = result + "!!!!!!";
        System.out.println(eut.getClass().getSimpleName() + ": " + hash);
        return result;
    }

    protected String testHash(Encoder eut) {
        final String encoded = eut.encode(getArray());
        return hash(encoded);
    }

    private static String md5(String st) {
        MessageDigest messageDigest;
        byte[] digest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        BigInteger bigInt = new BigInteger(1, digest);

        String md5Hex = bigInt.toString(16);

        int padZeros = 32 - md5Hex.length();
        if (padZeros > 0)
            md5Hex = String.format("%" + padZeros + "s", md5Hex);
        return md5Hex;
    }
}
