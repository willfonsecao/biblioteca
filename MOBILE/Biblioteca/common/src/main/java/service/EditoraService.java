package service;

import java.util.List;

import model.Editora;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EditoraService {

    @GET("editoras")
    Call<List<Editora>> buscarTodos();

    @PUT("editoras")
    Call<Editora> salvarEditora(@Body Editora editora);
}
