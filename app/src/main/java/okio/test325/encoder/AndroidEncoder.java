package okio.test325.encoder;

import android.util.Base64;

/**
 * Created by Pavel Remygailo on 15.11.2017.
 */

public class AndroidEncoder implements Encoder {
    @Override
    public String encode(byte[] arr) {
        return Base64.encodeToString(arr, Base64.NO_WRAP);
    }
}
