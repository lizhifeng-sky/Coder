package lzf.coder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FoldView foldView;
    private ImmediatelyView immediatelyView;
    private CheckBox checkBox;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * 折叠
        * */
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

        /*
        * 计数
        * */
        immediatelyView= (ImmediatelyView) findViewById(R.id.immediately);
        checkBox= (CheckBox) findViewById(R.id.checkbox);
        editText= (EditText) findViewById(R.id.input);
        TextView textView = (TextView) findViewById(R.id.change);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    immediatelyView.setCurrentNum(Integer.parseInt(editText.getText().toString().trim()));
                    immediatelyView.setFavor(checkBox.isChecked());
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this,"输入正常数字 不要逗我啊",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
