package stefanini.com.br.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Editora;
import model.Livro;
import stefanini.com.br.biblioteca.adapter.LivrosAdapter;


public class ListActivity extends AppCompatActivity {
    MenuActivity menu;
    String nomeEscolhido;
    ArrayList<Categoria> categorias;
    ArrayList<Editora> editoras;
    ArrayList<Livro> livros;
    public static boolean isCategorias = false;
    public static boolean isVerLivros = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        if(menu == null){
            menu = new MenuActivity(this,this);
        }

        List<String> nomes = new ArrayList<>();

        categorias = (ArrayList<Categoria>) getIntent().getSerializableExtra("CATEGORIAS");
        editoras = (ArrayList<Editora>) getIntent().getSerializableExtra("EDITORAS");
        livros = (ArrayList<Livro>) getIntent().getSerializableExtra("LIVROS");

        if(isCategorias && !isVerLivros){
            nomes =  getNomesCategorias(categorias);
        }else if(!isCategorias && !isVerLivros){
            nomes = getNomesEditoras(editoras);
        }

        ListView listView = (ListView) findViewById(R.id.list);
        if(isVerLivros){
            LivrosAdapter adapter = new LivrosAdapter(this,livros);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(),NovoLivroActivity.class);
                    i.putExtra("LIVRO",(Livro)parent.getAdapter().getItem(position));
                    startActivity(i);
                }
            });

        }else{
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, nomes);
            listView.setAdapter(dataAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nomeEscolhido = (String) parent.getAdapter().getItem(position);
                    Toast.makeText(getApplicationContext(),nomeEscolhido,Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private Categoria getCategoriaSelecionada(String nome){
        Categoria categoriaSelecionada = new Categoria();
        for (Categoria c : categorias) {
            if(nomeEscolhido.equals(c.getNome())){
                categoriaSelecionada = c;
            }
        }
        return categoriaSelecionada;
    }

    private Editora getEditoraSelecionada(String nome){
        Editora editoraSelecionada = new Editora();
        for (Editora e : editoras) {
            if(nomeEscolhido.equals(e.getNome())){
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
