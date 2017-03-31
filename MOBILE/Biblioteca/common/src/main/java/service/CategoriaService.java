package service;


import java.util.List;

import model.Categoria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriaService {

    @GET("categorias")
    Call<List<Categoria>> buscarTodos();

    @POST("categorias")
    Call<Categoria> salvarCategoria(@Body Categoria categoria);

}
