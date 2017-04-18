package stefanini.com.br.biblioteca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity  {

    ImageView image;

    public MenuActivity(final Context context, Activity activity){
        image = (ImageView) activity.findViewById(R.id.logoMenu);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });
    }
}
