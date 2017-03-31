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


public class ListActivity extends AppCompatActivity {
    MenuActivity menu;
    String nomeEscolhido;
    ArrayList<Categoria> categorias;
    ArrayList<Editora> editoras;
    public static boolean isNovoLivro = false;
    public static boolean isCategorias = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        menu = new MenuActivity(getApplicationContext(),this);

        List<String> nomes = new ArrayList<>();

        categorias = (ArrayList<Categoria>) getIntent().getSerializableExtra("CATEGORIAS");
        editoras = (ArrayList<Editora>) getIntent().getSerializableExtra("EDITORAS");

        if(isCategorias){
            nomes =  getNomesCategorias(categorias);
        }else{
            nomes = getNomesEditoras(editoras);
        }

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, nomes);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nomeEscolhido = (String) parent.getAdapter().getItem(position);
                if(isNovoLivro){
                    Intent i = new Intent(getApplicationContext(),NovoLivroActivity.class);
                    if(isCategorias){
                        Categoria categoriaSelecionada = getCategoriaSelecionada(nomeEscolhido);
                    }else if(!isCategorias){
                        Editora editoraSelecionada = getEditoraSelecionada(nomeEscolhido);
                    }
                    startActivity(i);
                }
                Toast.makeText(getApplicationContext(),nomeEscolhido,Toast.LENGTH_LONG).show();
            }
        });
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
