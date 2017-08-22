package stefanini.com.br.biblioteca;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import Http.HttpClientFactory;
import model.Categoria;
import model.Editora;
import service.CategoriaService;
import service.EditoraService;

public class NovaCategoriaEditora extends AppCompatActivity {

    public static boolean isCategoria;
    EditText nomeEntidade;
    String origem;
    String nome;
    Categoria categoria = new Categoria();
    Editora editora = new Editora();
    MenuActivity menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_categoria_editora);
        menu = new MenuActivity(this,this);
        nomeEntidade = (EditText)findViewById(R.id.nomeEntidade);
        origem = (String) getIntent().getStringExtra("ORIGEM");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        if(!isCategoria){
            titulo.setText("Nova Editora");
        }
    }

    public void salvar(View v){
        nome = nomeEntidade.getText().toString();
        if(nome == null || nome.isEmpty()){
            Toast.makeText(getApplicationContext(),"Digite um nome",Toast.LENGTH_LONG).show();
        }else{
            if(isCategoria){
                categoria.setNome(nome);
                new CategoriasConsumer().execute();
            }else{
                editora.setNome(nome);
                new EditorasConsumer().execute();
            }
        }
    }

    private class CategoriasConsumer extends AsyncTask<Object, Object, Void> {
        final CategoriaService categoriaService = HttpClientFactory.getHttpClient().create(CategoriaService.class);

        @Override
        protected Void doInBackground(Object... params) {
            try {
                categoriaService.salvarCategoria(categoria).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
    }

    private class EditorasConsumer extends AsyncTask<Object, Object, Void> {
        final EditoraService editoraService = HttpClientFactory.getHttpClient().create(EditoraService.class);

        @Override
        protected Void doInBackground(Object... params) {
            try {
                editoraService.salvarEditora(editora).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
    }
}
