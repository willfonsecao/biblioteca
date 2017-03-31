package stefanini.com.br.biblioteca;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.MyOptionsPickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Http.HttpClientFactory;
import io.github.yuweiguocn.lib.squareloading.SquareLoading;
import model.Categoria;
import model.Editora;
import model.Livro;
import service.CategoriaService;
import service.EditoraService;

public class NovoLivroActivity extends AppCompatActivity {

    MyOptionsPickerView singlePicker;
    private boolean isCategoriaPicker = false;
    List<Categoria> categorias = new ArrayList<>();
    List<Editora> editoras = new ArrayList<>();
    Livro novoLivro = new Livro();
    SquareLoading loading;
    TextView categoriaTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_livro);
        singlePicker = new MyOptionsPickerView(NovoLivroActivity.this);
        loading = (SquareLoading) findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        categoriaTitulo = (TextView)findViewById(R.id.categoria);
        new CategoriasConsumer().execute();
    }

    public void selecionarCategoria(View v){
        isCategoriaPicker = true;
        createPicker();
        singlePicker.show();
    }

    private void createPicker(){
        final ArrayList<String> items = new ArrayList<String>();
        List<String> nomes = new ArrayList<>();
        if(isCategoriaPicker){
            nomes = getNomesCategorias(new ArrayList<Categoria>(categorias));
        }else{
            nomes = getNomesEditoras(new ArrayList<Editora>(editoras));
        }
        items.addAll(nomes);
        singlePicker.setPicker(items);
        singlePicker.setTitle("Categorias");
        singlePicker.setCyclic(false);
        singlePicker.setSelectOptions(0);
        singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String nome = items.get(options1);
                if(isCategoriaPicker){
                    Categoria c = getCategoriaSelecionada(nome);
                    categoriaTitulo.setText(c.getNome());
                    categoriaTitulo.setTextColor(Color.BLACK);
                    novoLivro.setCategoria(c);
                }else{
                    Editora e = getEditoraSelecionada(nome);
                    novoLivro.setEditora(e);
                }
            }
        });
    }

    private class EditorasConsumer extends AsyncTask<Void, Void, List<Editora>> {
        final EditoraService editoraService = HttpClientFactory.getHttpClient().create(EditoraService.class);
        List<Editora> e = new ArrayList<>();

        @Override
        protected List<Editora> doInBackground(Void... params) {
            try {
                e = editoraService.buscarTodos().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return e;
        }

        @Override
        protected void onPostExecute(List<Editora> e) {
            super.onPostExecute(e);
            editoras = e;
            loading.setVisibility(View.GONE);
        }
    }

    private class CategoriasConsumer extends AsyncTask<Void, Void, List<Categoria>> {
        final CategoriaService categoriaService = HttpClientFactory.getHttpClient().create(CategoriaService.class);
        List<Categoria> c = new ArrayList<>();

        @Override
        protected List<Categoria> doInBackground(Void... params) {
            try {
                c = categoriaService.buscarTodos().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return c;
        }

        @Override
        protected void onPostExecute(List<Categoria> c) {
            super.onPostExecute(c);
            categorias = c;
            new EditorasConsumer().execute();
        }
    }

    private Categoria getCategoriaSelecionada(String nome){
        Categoria categoriaSelecionada = new Categoria();
        for (Categoria c : categorias) {
            if(nome.equals(c.getNome())){
                categoriaSelecionada = c;
            }
        }
        return categoriaSelecionada;
    }

    private Editora getEditoraSelecionada(String nome){
        Editora editoraSelecionada = new Editora();
        for (Editora e : editoras) {
            if(nome.equals(e.getNome())){
                editoraSelecionada = e;
            }
        }
        return editoraSelecionada;
    }

    private List<String> getNomesEditoras(ArrayList<Editora> editoras) {
        List<String> nomes = new ArrayList<>();
        for (Editora e : editoras) {
            nomes.add(e.getNome());
        }
        return nomes;
    }

    private List<String>  getNomesCategorias(ArrayList<Categoria> categorias) {
        List<String> nomes = new ArrayList<>();
        for (Categoria c : categorias) {
            nomes.add(c.getNome());
        }
        return nomes;
    }

}
