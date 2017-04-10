package stefanini.com.br.biblioteca.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import model.Livro;
import stefanini.com.br.biblioteca.R;

public class LivrosAdapter extends ArrayAdapter<Livro> {

    private final Activity context;
    private List<Livro> livros;

    public LivrosAdapter(Activity context, List<Livro> livros) {
        super(context, R.layout.list_livros, livros);
        this.context = context;
        this.livros = livros;
    }
    Livro livro;

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(position < livros.size()){
            livro = livros.get(position);
            position++;
        }

        ViewHolder viewHolder;

        if (view == null) {
            viewHolder =  new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_livros, parent, false);

            viewHolder.titulo = (TextView) view.findViewById(R.id.titulo);
            viewHolder.editora = (TextView) view.findViewById(R.id.editora);
            viewHolder.categoria = (TextView) view.findViewById(R.id.categoria);
            viewHolder.prefacio = (TextView) view.findViewById(R.id.prefacio);
            viewHolder.autor = (TextView) view.findViewById(R.id.autor);
            viewHolder.data = (TextView) view.findViewById(R.id.data);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.titulo.setText(livro.getTitulo());
        viewHolder.editora.setText(livro.getEditora().getNome());
        viewHolder.categoria.setText(livro.getCategoria().getNome());
        viewHolder.prefacio.setText(livro.getPrefacio());
        viewHolder.autor.setText(livro.getAutor());
        viewHolder.data.setText(livro.getDataPublicacao());

        return view;
    }
    public static class ViewHolder {
        TextView titulo;
        TextView editora;
        TextView categoria;
        TextView prefacio;
        TextView autor;
        TextView data;
    }
}
