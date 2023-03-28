import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os tops filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse <String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFIlmes = parser.parse(body) ;
        
        // exibir e manipular os dados 
        for (Map<String,String> filme : listaDeFIlmes) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();


        }

    }
}
