package service;

import java.util.List;

import model.Livro;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LivroService {

    @GET("livros")
    Call<List<Livro>> buscarTodos();

    @PUT("livros")
    Call<Livro> salvarCategoria(@Body Livro livro);
}
