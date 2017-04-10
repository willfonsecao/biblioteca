package stefanini.com.br.biblioteca;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Http.HttpClientFactory;
import io.github.yuweiguocn.lib.squareloading.SquareLoading;
import model.Categoria;
import model.Editora;
import service.CategoriaService;
import service.EditoraService;

public class MainActivity extends AppCompatActivity {

    SquareLoading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = (SquareLoading) findViewById(R.id.loading);
    }

    public void novaCategoria(View v){
        NovaCategoriaEditora.isCategoria = true;
        Intent i = new Intent(getApplicationContext(),NovaCategoriaEditora.class);
        startActivity(i);
    }

    public void novaEditora(View v){
        NovaCategoriaEditora.isCategoria = false;
        Intent i = new Intent(getApplicationContext(),NovaCategoriaEditora.class);
        startActivity(i);
    }

    public void novoLivro(View v){
        Intent i = new Intent(getApplicationContext(),NovoLivroActivity.class);
        startActivity(i);
    }

    public void verCategorias(View view){
        ListActivity.isCategorias = true;
        loading.setVisibility(View.VISIBLE);
        new CategoriasConsumer().execute();
    }

    public void verEditoras(View view){
        loading.setVisibility(View.VISIBLE);
        new EditorasConsumer().execute();
    }

    private class EditorasConsumer extends AsyncTask<Void, Void, List<Editora>> {
        final EditoraService editoraService = HttpClientFactory.getHttpClient().create(EditoraService.class);
        List<Editora> editoras = new ArrayList<>();

        @Override
        protected List<Editora> doInBackground(Void... params) {
            try {
                editoras = editoraService.buscarTodos().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return editoras;
        }

        @Override
        protected void onPostExecute(List<Editora> editoras) {
            super.onPostExecute(editoras);
            loading.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("EDITORAS", new ArrayList<Editora>(editoras));
            startActivity(intent);
        }
    }

    private class CategoriasConsumer extends AsyncTask<Void, Void, List<Categoria>> {
        final CategoriaService categoriaService = HttpClientFactory.getHttpClient().create(CategoriaService.class);
        List<Categoria> categorias = new ArrayList<>();

        @Override
        protected List<Categoria> doInBackground(Void... params) {
            try {
                categorias = categoriaService.buscarTodos().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return categorias;
        }

        @Override
        protected void onPostExecute(List<Categoria> categorias) {
            super.onPostExecute(categorias);
            loading.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("CATEGORIAS", new ArrayList<Categoria>(categorias));
            startActivity(intent);
        }
    }
}
