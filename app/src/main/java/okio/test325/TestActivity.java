package okio.test325;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okio.test325.encoder.AndroidEncoder;
import okio.test325.encoder.Encoder;
import okio.test325.encoder.OkioEncoder;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                runTest();
            }
        });
    }

    private void runTest() {
        Encoder golden = new AndroidEncoder();
        Encoder underTest = new OkioEncoder();

        OkioTest run = new OkioTest(golden);
        List<Encoder> toTest = new ArrayList<>();
        toTest.add(golden);
        toTest.add(underTest);
        appendText("\nHash must be \"" + run.goldenHash + '\"');

        try {
            for (Encoder enc : toTest) {
                appendText("Encoder: " + enc.getClass().getSimpleName());
                String result = run.test(enc);
                appendText(result);
            }
            // todo: test in loop until exception?
        } catch (Exception e) {
            e.printStackTrace();
            appendText(e.getMessage());
        }
    }

    private void appendText(String result) {
        TextView view = findViewById(R.id.txt_result);
        String text = view.getText().toString() + "\n" + result;
        view.setText(text);
    }
}
