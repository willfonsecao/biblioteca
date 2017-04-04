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
import stefanini.com.br.biblioteca.picker.DatePickerFragment;
import stefanini.com.br.biblioteca.picker.OnStepDataRequestedListener;
import stefanini.com.br.biblioteca.picker.OnStepPickListener;
import stefanini.com.br.biblioteca.picker.TwoStepPickerDialog;

public class NovoLivroActivity extends AppCompatActivity {

    MyOptionsPickerView singlePicker;
    private boolean isCategoriaPicker = false;
    List<Categoria> categorias = new ArrayList<>();
    List<Editora> editoras = new ArrayList<>();
    Livro novoLivro = new Livro();
    SquareLoading loading;
    TextView categoriaTitulo;
    TextView editoraTitulo;
    TwoStepPickerDialog pickThing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_livro);
        categoriaTitulo = (TextView) findViewById(R.id.categoriaTxt);
        editoraTitulo = (TextView) findViewById(R.id.editoraTxt);
        singlePicker = new MyOptionsPickerView(NovoLivroActivity.this);
        loading = (SquareLoading) findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        categoriaTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCategoriaPicker = true;
                createPicker();
                pickThing.show();
            }
        });
        editoraTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCategoriaPicker = false;
                createPicker();
                pickThing.show();
            }
        });

        new CategoriasConsumer().execute();
    }

    public void selecionarData(View v){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.campoTxt = (TextView)findViewById(R.id.dataTxt);
        datePicker.show(getFragmentManager(),"Data de Publicação");
    }

    private void createPicker(){
        final ArrayList<String> items = new ArrayList<String>();
        List<String> nomes = new ArrayList<>();
        if(isCategoriaPicker){
            nomes = getNomesCategorias(new ArrayList<Categoria>(categorias));
        }else{
            nomes = getNomesEditoras(new ArrayList<Editora>(editoras));
        }
            pickThing = new TwoStepPickerDialog
                .Builder(this)
                .withBaseData(nomes)
                .withOkButton("OK")
                .withCancelButton("CANCELAR")
                .withBaseOnLeft(false)
                .withInitialBaseSelected(0)
                .withInitialStepSelected(0)
                    .withOnStepDataRequested(new OnStepDataRequestedListener() {
                        @Override
                        public List<String> onStepDataRequest(int baseDataPos) {
                            return null;
                        }
                    })
                .withDialogListener(new OnStepPickListener() {
                    @Override
                    public void onStepPicked(int step) {
                        if (isCategoriaPicker) {
                            Categoria c = categorias.get(step);
                            novoLivro.setCategoria(c);
                            categoriaTitulo.setText(c.getNome());
                            categoriaTitulo.setTextColor(Color.BLACK);
                            isCategoriaPicker = false;
                        } else {
                            Editora e = editoras.get(step);
                            novoLivro.setEditora(e);
                            editoraTitulo.setText(e.getNome());
                            editoraTitulo.setTextColor(Color.BLACK);
                        }
                    }

                    @Override
                    public void onDismissed() {
                    }
                })
                .build();
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
