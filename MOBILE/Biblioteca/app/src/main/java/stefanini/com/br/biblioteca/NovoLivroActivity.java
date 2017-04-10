package stefanini.com.br.biblioteca;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.MyOptionsPickerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Http.HttpClientFactory;
import io.github.yuweiguocn.lib.squareloading.SquareLoading;
import model.Categoria;
import model.Editora;
import model.Livro;
import service.CategoriaService;
import service.EditoraService;
import service.LivroService;
import stefanini.com.br.biblioteca.picker.DatePickerFragment;
import stefanini.com.br.biblioteca.picker.OnStepDataRequestedListener;
import stefanini.com.br.biblioteca.picker.OnStepPickListener;
import stefanini.com.br.biblioteca.picker.TwoStepPickerDialog;

public class NovoLivroActivity extends AppCompatActivity {


    private boolean isCategoriaPicker = false;
    private boolean isNovoAutor = false;
    private boolean isNovoTitulo = false;
    List<Categoria> categorias = new ArrayList<>();
    List<Editora> editoras = new ArrayList<>();
    Livro novoLivro = new Livro();
    TextView categoriaTitulo;
    TextView editoraTitulo;
    TextView dataTitulo;
    TextView autorTitulo;
    TextView tituloLivro;
    TextView prefacio;
    ImageView btSalvar;
    TwoStepPickerDialog pickThing = null;
    DatePickerFragment datePicker;
    MyOptionsPickerView singlePicker;
    SquareLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_livro);
        categoriaTitulo = (TextView) findViewById(R.id.categoriaTxt);
        editoraTitulo = (TextView) findViewById(R.id.editoraTxt);
        dataTitulo = (TextView) findViewById(R.id.dataTxt);
        autorTitulo = (TextView) findViewById(R.id.autorTxt);
        tituloLivro = (TextView) findViewById(R.id.tituloTxt);
        prefacio = (TextView) findViewById(R.id.prefacioTxt);
        btSalvar = (ImageView)findViewById(R.id.btSalvar);

            singlePicker = new MyOptionsPickerView(NovoLivroActivity.this);
            datePicker = new DatePickerFragment();
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

    public void salvar(View v){
        novoLivro.setDataPublicacao(dataTitulo.getText().toString());
        if(isLivroPopulado()){
            loading.setVisibility(View.VISIBLE);
            new LivroConsumer().execute();
        }else{
            Toast.makeText(getApplicationContext(),"Preencha todos os filtros",Toast.LENGTH_LONG);
        }
    }

    private boolean isLivroPopulado() {
        return novoLivro.getCategoria() != null && novoLivro.getEditora() != null && novoLivro.getAutor() != null
                && novoLivro.getDataPublicacao() != null && novoLivro.getPrefacio() != null && novoLivro.getTitulo() != null;
    }

    public void modalNovoAutor(View v){
        this.isNovoAutor = true;
        criarModal();
    }
    public void modalNovoTitulo(View v){
        this.isNovoTitulo = true;
        criarModal();
    }
    public void modalNovoPrefacio(View v){
        criarModal();
    }

    private void criarModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = (View) inflater.inflate(R.layout.modal_novo_nome, null);
        TextView titulo = (TextView) view.findViewById(R.id.tituloModal);
        if(isNovoAutor){
            titulo.setText("Novo Autor");
        }else if(isNovoTitulo){
            titulo.setText("Novo Titulo");
        }else{
            titulo.setText("Novo Prefácio");
        }
        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       EditText conteudo = (EditText) view.findViewById(R.id.conteudoModal);
                        if(!(conteudo.getText() == null || conteudo.getText().equals(""))){
                            if(isNovoAutor){
                                TextView autor = (TextView)findViewById(R.id.autorTxt);
                                autor.setText(conteudo.getText());
                                autor.setTextColor(Color.BLACK);
                                isNovoAutor = false;
                                novoLivro.setAutor(conteudo.getText().toString());
                            }else if(isNovoTitulo){
                                TextView titulo = (TextView)findViewById(R.id.tituloTxt);
                                titulo.setText(conteudo.getText());
                                titulo.setTextColor(Color.BLACK);
                                isNovoTitulo = false;
                                novoLivro.setTitulo(conteudo.getText().toString());
                            }else{
                                TextView prefacio = (TextView)findViewById(R.id.prefacioTxt);
                                prefacio.setText(conteudo.getText());
                                prefacio.setTextColor(Color.BLACK);
                                novoLivro.setPrefacio(conteudo.getText().toString());
                            }
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isNovoAutor = false;
                        isNovoTitulo = false;
                    }
                });

        builder.create();
        builder.show();
    }

    public void selecionarData(View v){
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
    private class LivroConsumer extends AsyncTask<Object, Object, Void> {
        final LivroService livroService = HttpClientFactory.getHttpClient().create(LivroService.class);

        @Override
        protected Void doInBackground(Object... params) {
            try {
               livroService.salvarLivro(novoLivro).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.setVisibility(View.GONE);
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
    }
}
