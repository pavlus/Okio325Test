package okio.test325.encoder;

/**
 * Created by Pavel Remygailo on 15.11.2017.
 */

public class OkioEncoder implements Encoder {
    @Override
    public String encode(byte[] arr) {
        return okio.ByteString.of(arr, 0, arr.length).base64();
    }
}
