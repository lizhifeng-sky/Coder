package lzf.coder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FoldView foldView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foldView= (FoldView) findViewById(R.id.fold);
        TextView left = (TextView) findViewById(R.id.left);
        TextView top = (TextView) findViewById(R.id.top);
        TextView right = (TextView) findViewById(R.id.right);
        TextView bottom = (TextView) findViewById(R.id.bottom);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldView.left();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldView.right();
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldView.top();
            }
        });

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldView.bottom();
            }
        });
    }
}
