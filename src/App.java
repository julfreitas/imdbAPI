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
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFIlmes = parser.parse(body);

        // exibir e manipular os dados
        // exibir os 5 primeiros tops filmes
        System.out.println();
        System.out.println("Rating IMDb - Top Movies 🎬");
        System.out.println();
        for (int i = 0; i < 5; i++) {
            Map<String, String> filme = listaDeFIlmes.get(i);
            System.out.println("\u001b[38;5;208;255;1mTítulo >> \u001b[m\u001b[4m" + filme.get("title") + "\u001b[m");
            System.out.println("\u001b[38;5;208;255;1mUser rating >> \u001b[m" + filme.get("imDbRating"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelas = (int) classificacao;
            for (int n = 1; n <= numeroEstrelas;  n++) {
                System.out.print("⭐");
    
            }
            System.out.println();
            System.out.println("\u001b[38;5;208;255mImagem >> \u001b[m" + filme.get("image"));
            System.out.println("\n");
            


        }

    }
}
