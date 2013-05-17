package lucas.mayer.SometimesRedSometimesBlue;

import android.app.Activity;
import android.os.Bundle;

import java.util.Random;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Random random = new Random();
        int i = random.nextInt(2);

        if (i > 0)
            setContentView(R.layout.blue);
        else
            setContentView(R.layout.red);

    }
}
